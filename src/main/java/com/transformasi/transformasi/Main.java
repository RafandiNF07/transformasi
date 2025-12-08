package com.transformasi.transformasi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // 1. Setup TabPane (Seperti sebelumnya)
        TabPane tabs = new TabPane();

        // Pastikan class duadimensi dan TigaDimensiView sudah benar
        Tab tab2D = new Tab("2 Dimensi", new DuaDimensi().tampilkan());
        Tab tab3D = new Tab("3 Dimensi", new TigaDimensi().tampilkan());

        // Agar user tidak tidak sengaja menutup tab
        tab2D.setClosable(false);
        tab3D.setClosable(false);

        tabs.getTabs().addAll(tab2D, tab3D);

        // 2. MEMBUAT TOMBOL KELUAR (Solusi Masalah Teman Anda)
        HBox bottomBar = getHBox();

        // 3. Setup Layout Utama (BorderPane)
        // Kita bungkus TabPane dan Tombol Exit dalam satu layout
        BorderPane root = new BorderPane();
        root.setCenter(tabs);      // TabPane di tengah
        root.setBottom(bottomBar); // Tombol Exit di bawah

        // 4. Setup Scene & Stage
        // Pastikan TIDAK ADA baris: stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 1280, 700);

        // Listener tambahan: Pastikan aplikasi mati total saat tombol X window ditekan
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setScene(scene);
        stage.setTitle("Transformasi Linear - Teknik Informatika");
        stage.show();
    }

    private static HBox getHBox() {
        Button btnExit = new Button("KELUAR APLIKASI");
        btnExit.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");

        // Aksi saat tombol ditekan: Matikan JavaFX Thread
        btnExit.setOnAction(e -> {
            Platform.exit(); // Cara menutup aplikasi JavaFX yang benar
            System.exit(0);  // Memastikan semua thread background mati total
        });

        // Masukkan tombol ke dalam HBox agar posisinya di tengah bawah
        HBox bottomBar = new HBox(btnExit);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setStyle("-fx-background-color: #333;"); // Warna footer gelap
        return bottomBar;
    }
}