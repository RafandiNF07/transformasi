package com.transformasi.transformasi;

import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DuaDimensi {

    // Variabel transformasi
    private double transX = 0;
    private double transY = 0;
    private double rotateDegree = 0;
    private double scaleValue = 1.0;

    private Canvas canvas;

    public Parent tampilkan() {
        BorderPane root = new BorderPane();

        // Canvas untuk menggambar
        canvas = new Canvas(550, 600);
        root.setCenter(canvas);

        // Panel kontrol di kanan
        VBox controlPanel = new VBox(12);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setPrefWidth(250);

        // Slider translasi X
        Label translasiX = new Label("Translasi X");
        Slider sTranslasiX = new Slider(-200, 200, 0);
        sTranslasiX.valueProperty().addListener((obs, o, n) -> {
            transX = n.doubleValue();
            draw();
        });

        // Slider translasi Y
        Label translasiY = new Label("Translasi Y");
        Slider sTranslasiY = new Slider(-200, 200, 0);
        sTranslasiY.valueProperty().addListener((obs, o, n) -> {
            transY = n.doubleValue();
            draw();
        });

        // Slider rotasi
        Label rotasi = new Label("Rotasi (derajat)");
        Slider sRotasi = new Slider(0, 360, 0);
        sRotasi.valueProperty().addListener((obs, o, n) -> {
            rotateDegree = n.doubleValue();
            draw();
        });

        // Slider skala
        Label skalar = new Label("Skala");
        Slider sSkalar = new Slider(10, 300, 100);
        sSkalar.valueProperty().addListener((obs, o, n) -> {
            scaleValue = n.doubleValue() / 100.0;
            draw();
        });

        // Tombol reset
        Button reset = new Button("Reset Posisi");
        reset.setOnAction(e -> {
            sTranslasiX.setValue(0);
            sTranslasiY.setValue(0);
            sRotasi.setValue(0);
            sSkalar.setValue(100);
        });

        controlPanel.getChildren().addAll(
                translasiX, sTranslasiX,
                translasiY, sTranslasiY,
                rotasi, sRotasi,
                skalar, sSkalar,
                reset
        );
        root.setRight(controlPanel);
        draw();
        return root;
    }

    private void draw() {
        GraphicsContext g2 = canvas.getGraphicsContext2D();
        g2.setFill(Color.WHITE);
        g2.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        g2.save();

        // Pusat canvas
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        // Transformasi
        g2.translate(centerX, centerY);
        g2.translate(transX, - transY);
        g2.rotate(rotateDegree);
        g2.scale(scaleValue, scaleValue);

        // dot merah dalam objek(titik tengah)
        int rectSize = 100;
        int x = -rectSize / 2;
        int y = -rectSize / 2;

        g2.setFill(Color.DODGERBLUE);
        g2.fillRect(x, y, rectSize, rectSize);

        g2.setStroke(Color.BLACK);
        g2.setLineWidth(3);
        g2.strokeRect(x, y, rectSize, rectSize);

        // Titik pusat
        g2.setFill(Color.RED);
        g2.fillOval(-3, -3, 6, 6);

        g2.restore();

        // Info text
        g2.setFill(Color.DARKGRAY);
        g2.fillText("Posisi: X=" + transX + ", Y=" + transY, 10, 20);
        g2.fillText("Rotasi: " + rotateDegree + "°", 10, 40);
        g2.fillText("Skala: " + scaleValue + "x", 10, 60);
    }
}