package com.transformasi.transformasi;

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

    // Objek 3D yang akan dimanipulasi
    private Box objekBox;
    private TransformasiModel model;
    private TransformasiController controller;
    private Affine affineTransform;

    public Parent tampilkan() {
        BorderPane root = new BorderPane();
        Group world3D = new Group();

        // 1. Inisialisasi Model & Affine
        model = new TransformasiModel();
        affineTransform = new Affine();

        // 2. Buat Objek Box (Kuning)
        objekBox = new Box(100, 50, 70);
        objekBox.setMaterial(new PhongMaterial(Color.YELLOW));
        objekBox.getTransforms().add(affineTransform); // Bind transformasi ke box

        // 3. Buat Environment (Grid & Dinding) dari kelas Dinding
        Dinding lingkungan = new Dinding();
        world3D.getChildren().add(lingkungan.buatDinding());
        world3D.getChildren().add(objekBox);

        // 4. Setup Kamera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(4000);
        Kamera cameraControl = new Kamera(camera);

        // 5. Buat SubScene (Layar 3D)
        SubScene subScene = new SubScene(world3D, 950, 750, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(40, 40, 40));
        subScene.setCamera(camera);
        cameraControl.attachControl(subScene);

        // 6. Setup Controller (Sidebar)
        controller = new TransformasiController(model);

        // Callback: Apa yang terjadi saat slider digeser? Update Affine!
        Runnable updateAction = () -> {
            Affine baru = model.generateAffine();
            affineTransform.setToTransform(baru);
        };

        // Pasang layout
        root.setCenter(subScene);
        root.setLeft(controller.buatSidebar(updateAction));

        return root;
    }
}