package com.transformasi.transformasi;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TransformasiController {
    private final TransformasiModel model;

    public TransformasiController(TransformasiModel model) {
        this.model = model;
    }

    public VBox buatSidebar(Runnable applyCallback) {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #222;");
        sidebar.setPrefWidth(300);

        // Buat Slider dengan Label
        Slider tx = makeSlider(-300, 300, 0);
        Slider ty = makeSlider(-300, 300, 0);
        Slider tz = makeSlider(-300, 300, 0);
        Slider rx = makeSlider(-180, 180, 0);
        Slider ry = makeSlider(-180, 180, 0);
        Slider rz = makeSlider(-180, 180, 0);
        Slider scale = makeSlider(0.1, 3, 1);

        // Listener
        tx.valueProperty().addListener((o, old, n) -> { model.tx = n.doubleValue(); applyCallback.run(); });
        ty.valueProperty().addListener((o, old, n) -> { model.ty = n.doubleValue(); applyCallback.run(); });
        tz.valueProperty().addListener((o, old, n) -> { model.tz = n.doubleValue(); applyCallback.run(); });
        rx.valueProperty().addListener((o, old, n) -> { model.rx = n.doubleValue(); applyCallback.run(); });
        ry.valueProperty().addListener((o, old, n) -> { model.ry = n.doubleValue(); applyCallback.run(); });
        rz.valueProperty().addListener((o, old, n) -> { model.rz = n.doubleValue(); applyCallback.run(); });
        scale.valueProperty().addListener((o, old, n) -> { model.scale = n.doubleValue(); applyCallback.run(); });

        CheckBox reflYZ = new CheckBox("Refleksi YZ"); reflYZ.setTextFill(Color.WHITE);
        reflYZ.selectedProperty().addListener((o, old, n) -> { model.reflYZ = n; applyCallback.run(); });

        CheckBox reflXZ = new CheckBox("Refleksi XZ"); reflXZ.setTextFill(Color.WHITE);
        reflXZ.selectedProperty().addListener((o, old, n) -> { model.reflXZ = n; applyCallback.run(); });

        CheckBox reflXY = new CheckBox("Refleksi XY"); reflXY.setTextFill(Color.WHITE);
        reflXY.selectedProperty().addListener((o, old, n) -> { model.reflXY = n; applyCallback.run(); });

        Button reset = new Button("Reset Transformasi");
        reset.setStyle("-fx-background-color: #FF6464; -fx-text-fill: white;");
        reset.setOnAction(e -> {
            // Reset UI
            tx.setValue(0); ty.setValue(0); tz.setValue(0);
            rx.setValue(0); ry.setValue(0); rz.setValue(0);
            scale.setValue(1);
            reflYZ.setSelected(false); reflXZ.setSelected(false); reflXY.setSelected(false);

            // Reset Model
            model.tx=0; model.ty=0; model.tz=0;
            model.rx=0; model.ry=0; model.rz=0;
            model.scale=1;
            model.reflXY=false; model.reflYZ=false; model.reflXZ=false;

            applyCallback.run();
        });

        sidebar.getChildren().addAll(
                createLabel("Translasi X", tx), createLabel("Translasi Y", ty), createLabel("Translasi Z", tz),
                createLabel("Rotasi X", rx), createLabel("Rotasi Y", ry), createLabel("Rotasi Z", rz),
                createLabel("Skala", scale),
                reflYZ, reflXZ, reflXY, reset
        );
        return sidebar;
    }

    private Slider makeSlider(double min, double max, double val) {
        Slider s = new Slider(min, max, val);
        s.setShowTickLabels(true); s.setShowTickMarks(true);
        return s;
    }

    private VBox createLabel(String text, Slider s) {
        Label l = new Label(text); l.setTextFill(Color.WHITE);
        return new VBox(5, l, s);
    }
}