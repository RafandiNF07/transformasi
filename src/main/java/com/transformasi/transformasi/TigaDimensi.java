package com.transformasi.transformasi;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Affine;

public class TigaDimensi {

    public Parent tampilkan() {
        BorderPane root = new BorderPane();
        Group world3D = new Group();

        // Model & Affine
        TransformasiModel model = new TransformasiModel();
        Affine affineTransform = new Affine();

        // Objek Box
        Box objekBox = new Box(100, 50, 70);
        objekBox.setMaterial(new PhongMaterial(Color.YELLOW));
        objekBox.getTransforms().add(affineTransform);

        // Environment
        Dinding lingkungan = new Dinding();
        world3D.getChildren().add(lingkungan.buatDinding());
        world3D.getChildren().add(objekBox);

        // Kamera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(4000);
        Kamera cameraControl = new Kamera(camera);

        // SubScene
        SubScene subScene = new SubScene(world3D, 950, 750, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(40, 40, 40));
        subScene.setCamera(camera);
        cameraControl.attachControl(subScene);

        // Controller
        TransformasiController controller = new TransformasiController(model);

        // Callback Update Visual & Teks
        Runnable updateAction = () -> {
            Affine baru = model.generateAffine();
            affineTransform.setToTransform(baru);

            // Update Text Posisi
            Point3D p = baru.transform(0, 0, 0);
            controller.getPosBayanganLabel().setText(
                    String.format("Posisi Pusat: (%.1f, %.1f, %.1f)", p.getX(), p.getY(), p.getZ())
            );

            // Update Text Matriks (Lengkap)
            String matrixStr = String.format(
                    "[ %5.2f %5.2f %5.2f %5.2f ]\n" +
                            "[ %5.2f %5.2f %5.2f %5.2f ]\n" +
                            "[ %5.2f %5.2f %5.2f %5.2f ]\n" +
                            "[ 0.00  0.00  0.00  1.00 ]",
                    baru.getMxx(), baru.getMxy(), baru.getMxz(), baru.getTx(),
                    baru.getMyx(), baru.getMyy(), baru.getMyz(), baru.getTy(),
                    baru.getMzx(), baru.getMzy(), baru.getMzz(), baru.getTz()
            );
            controller.getMatrixLabel().setText(matrixStr);
        };

        // Init pertama kali
        updateAction.run();

        // Layouting
        root.setCenter(subScene);
        root.setLeft(controller.buatSidebar(updateAction)); // Ambil sidebar yang sudah di-scroll

        return root;
    }
}