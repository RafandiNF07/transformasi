package com.transformasi.transformasi;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Dinding {

    public Group buatDinding() {
        Group g = new Group();
        g.getChildren().add(buatAlas());
        g.getChildren().add(buatBelakang());
        g.getChildren().add(buatKiri());
        return g;
    }

    private Group buatAlas() {
        Group grid = new Group();
        // Alas Grid
        Box backgroundPlane = new Box(1200, 1, 1200);
        backgroundPlane.setMaterial(new PhongMaterial(Color.web("#011875", 0.1)));

        grid.getChildren().add(backgroundPlane);
        grid.getChildren().add(drawGrid3D()); // Panggil helper grid bawah
        return grid;
    }

    private Group buatBelakang() {
        Group grid = new Group();
        // Dinding Belakang
        Box frontWall = new Box(1200, 1200, 1);
        frontWall.setMaterial(new PhongMaterial(Color.web("#FF6347", 0.1)));
        frontWall.setTranslateZ(600);

        grid.getChildren().add(frontWall);
        grid.getChildren().add(drawFrontGrid());
        return grid;
    }

    private Group buatKiri() {
        Group grid = new Group();
        // Dinding Kiri
        Box leftWall = new Box(1, 1200, 1200);
        leftWall.setMaterial(new PhongMaterial(Color.web("#32CD32", 0.1)));
        leftWall.setTranslateX(-600);

        grid.getChildren().add(leftWall);
        grid.getChildren().add(drawLeftGrid());
        return grid;
    }

    // --- HELPER METHODS UNTUK GAMBAR GARIS ---

    private Group drawGrid3D() {
        Group grid = new Group();
        int size = 600; int step = 50; double lineRadius = 0.6;
        PhongMaterial gridMat = new PhongMaterial(Color.web("#87CEFA"));

        for (int i = -size; i <= size; i += step) {
            Cylinder line1 = create3DLine(i, 0, -size, i, 0, size, lineRadius); line1.setMaterial(gridMat);
            Cylinder line2 = create3DLine(-size, 0, i, size, 0, i, lineRadius); line2.setMaterial(gridMat);
            grid.getChildren().addAll(line1, line2);
        }
        // Axes
        grid.getChildren().addAll(
                createColoredLine(-600, 0, 0, 600, 0, 0, 2, Color.RED),   // X
                createColoredLine(0, -600, 0, 0, 600, 0, 2, Color.BLUE),  // Y
                createColoredLine(0, 0, -600, 0, 0, 600, 2, Color.YELLOW) // Z
        );
        return grid;
    }

    private Group drawFrontGrid() {
        Group grid = new Group();
        int size = 600; int step = 50; double lineRadius = 0.6;
        PhongMaterial gridMat = new PhongMaterial(Color.web("#FF6347"));
        for (int i = -size; i <= size; i += step) {
            Cylinder lineX = create3DLine(-size, -i, 600, size, -i, 600, lineRadius); lineX.setMaterial(gridMat);
            Cylinder lineY = create3DLine(i, -size, 600, i, size, 600, lineRadius); lineY.setMaterial(gridMat);
            grid.getChildren().addAll(lineX, lineY);
        }
        return grid;
    }

    private Group drawLeftGrid() {
        Group grid = new Group();
        int size = 600; int step = 50; double lineRadius = 0.6;
        PhongMaterial gridMat = new PhongMaterial(Color.web("#32CD32"));
        for (int i = -size; i <= size; i += step) {
            Cylinder lineZ = create3DLine(-600, -i, -size, -600, -i, size, lineRadius); lineZ.setMaterial(gridMat);
            Cylinder lineY = create3DLine(-600, -size, i, -600, size, i, lineRadius); lineY.setMaterial(gridMat);
            grid.getChildren().addAll(lineZ, lineY);
        }
        return grid;
    }

    private Cylinder createColoredLine(double x1, double y1, double z1, double x2, double y2, double z2, double r, Color c) {
        Cylinder cyl = create3DLine(x1, y1, z1, x2, y2, z2, r);
        cyl.setMaterial(new PhongMaterial(c));
        return cyl;
    }

    private Cylinder create3DLine(double x1, double y1, double z1, double x2, double y2, double z2, double radius) {
        double dx = x2 - x1; double dy = y2 - y1; double dz = z2 - z1;
        double len = Math.sqrt(dx*dx + dy*dy + dz*dz);
        if (len == 0) len = 0.0001;
        Cylinder c = new Cylinder(radius, len);
        double mx = (x1 + x2) / 2.0; double my = (y1 + y2) / 2.0; double mz = (z1 + z2) / 2.0;
        c.getTransforms().add(new Translate(mx, my, mz));
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D dir = new Point3D(dx / len, dy / len, dz / len);
        Point3D axis = yAxis.crossProduct(dir);
        if (axis.magnitude() < 1e-6) {
            if (yAxis.dotProduct(dir) < 0) c.getTransforms().add(new Rotate(180, new Point3D(1, 0, 0)));
        } else {
            double angle = Math.toDegrees(Math.acos(yAxis.dotProduct(dir)));
            c.getTransforms().add(new Rotate(angle, axis));
        }
        return c;
    }
}