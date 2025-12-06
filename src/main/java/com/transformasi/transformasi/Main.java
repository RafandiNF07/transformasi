package com.transformasi.transformasi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        TabPane tabs = new TabPane();

        Tab tab2D = new Tab("2 Dimensi", new Duadimensi().tampilkan());
        Tab tab3D = new Tab("3 Dimensi", new TigaDimensi().tampilkan());

        tab2D.setClosable(false);
        tab3D.setClosable(false);

        tabs.getTabs().addAll(tab2D, tab3D);

        stage.setScene(new Scene(tabs, 1200, 800));
        stage.setTitle("Transformasi Linear 2D & 3D");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
