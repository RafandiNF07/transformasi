package com.transformasi.transformasi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TransformasiController {
    private final TransformasiModel model;

    // Label Output
    private final Label posBayanganLabel = new Label("Posisi Pusat: (0,0,0)");
    private final Label matrixLabel = new Label("[Matriks Identity]");

    public TransformasiController(TransformasiModel model) {
        this.model = model;

        // --- PERBAIKAN: STYLE LABEL LEBIH BESAR ---
        posBayanganLabel.setTextFill(Color.CYAN);
        posBayanganLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;"); // Diperbesar

        matrixLabel.setTextFill(Color.LIME);
        // Font Monospaced lebih besar dan tebal
        matrixLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
        matrixLabel.setStyle("-fx-border-color: #555; -fx-padding: 8; -fx-background-color: #000;");
    }

    public Label getPosBayanganLabel() { return posBayanganLabel; }
    public Label getMatrixLabel() { return matrixLabel; }

    public ScrollPane buatSidebar(Runnable applyCallback) {
        VBox content = new VBox(10);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #222;");
        content.setPrefWidth(380); // Diperlebar agar matriks besar muat

        // JUDUL
        Label title = new Label("KONTROL 3D LENGKAP");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("System", FontWeight.BOLD, 18));

        // MONITOR STATUS
        VBox monitor = new VBox(5, new Label("INFO MATRIKS:"), matrixLabel, posBayanganLabel);
        monitor.getChildren().get(0).setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // --- DEFINISI SLIDER & CHECKBOX (Local Variables agar bisa di-Reset) ---

        // 1. Translasi
        SliderWrapper tx = createSliderWrapper("X:", -300, 300, 0, (v)->model.tx=v, applyCallback);
        SliderWrapper ty = createSliderWrapper("Y:", -300, 300, 0, (v)->model.ty=v, applyCallback);
        SliderWrapper tz = createSliderWrapper("Z:", -300, 300, 0, (v)->model.tz=v, applyCallback);
        VBox groupTrans = new VBox(5, createHeader("TRANSLASI"), tx.box, ty.box, tz.box);

        // 2. Rotasi
        SliderWrapper rx = createSliderWrapper("X:", -180, 180, 0, (v)->model.rx=v, applyCallback);
        SliderWrapper ry = createSliderWrapper("Y:", -180, 180, 0, (v)->model.ry=v, applyCallback);
        SliderWrapper rz = createSliderWrapper("Z:", -180, 180, 0, (v)->model.rz=v, applyCallback);
        VBox groupRot = new VBox(5, createHeader("ROTASI"), rx.box, ry.box, rz.box);

        // 3. Skala
        SliderWrapper scale = createSliderWrapper("All:", 0.1, 3, 1, (v)->model.scale=v, applyCallback);
        VBox groupScale = new VBox(5, createHeader("SKALA"), scale.box);

        // 4. Shear
        SliderWrapper shXY = createSliderWrapper("Sh XY:", -2, 2, 0, (v)->model.shXY=v, applyCallback);
        SliderWrapper shXZ = createSliderWrapper("Sh XZ:", -2, 2, 0, (v)->model.shXZ=v, applyCallback);
        SliderWrapper shYX = createSliderWrapper("Sh YX:", -2, 2, 0, (v)->model.shYX=v, applyCallback);
        SliderWrapper shYZ = createSliderWrapper("Sh YZ:", -2, 2, 0, (v)->model.shYZ=v, applyCallback);
        SliderWrapper shZX = createSliderWrapper("Sh ZX:", -2, 2, 0, (v)->model.shZX=v, applyCallback);
        SliderWrapper shZY = createSliderWrapper("Sh ZY:", -2, 2, 0, (v)->model.shZY=v, applyCallback);
        VBox groupShear = new VBox(5, createHeader("SHEAR / GESERAN"), shXY.box, shXZ.box, shYX.box, shYZ.box, shZX.box, shZY.box);

        // 5. Refleksi
        CheckBox reflYZ = createCheckBox("Refleksi YZ (Flip X)");
        CheckBox reflXZ = createCheckBox("Refleksi XZ (Flip Y)");
        CheckBox reflXY = createCheckBox("Refleksi XY (Flip Z)");
        reflYZ.selectedProperty().addListener((o,old,n) -> { model.reflYZ = n; applyCallback.run(); });
        reflXZ.selectedProperty().addListener((o,old,n) -> { model.reflXZ = n; applyCallback.run(); });
        reflXY.selectedProperty().addListener((o,old,n) -> { model.reflXY = n; applyCallback.run(); });
        VBox groupRefl = new VBox(5, createHeader("REFLEKSI"), reflYZ, reflXZ, reflXY);

        // 6. Proyeksi
        CheckBox projXY = createCheckBox("Proyeksi ke XY (Z=0)");
        CheckBox projXZ = createCheckBox("Proyeksi ke XZ (Y=0)");
        CheckBox projYZ = createCheckBox("Proyeksi ke YZ (X=0)");
        projXY.selectedProperty().addListener((o,old,n) -> { model.projXY = n; applyCallback.run(); });
        projXZ.selectedProperty().addListener((o,old,n) -> { model.projXZ = n; applyCallback.run(); });
        projYZ.selectedProperty().addListener((o,old,n) -> { model.projYZ = n; applyCallback.run(); });
        VBox groupProj = new VBox(5, createHeader("PROYEKSI (GEPENG)"), projXY, projXZ, projYZ);

        // --- TOMBOL RESET (DIBUAT SANGAT JELAS) ---
        Button reset = new Button("RESET SEMUA KE AWAL");
        reset.setMaxWidth(Double.MAX_VALUE);
        reset.setPrefHeight(40); // Tombol lebih tinggi
        reset.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");

        reset.setOnAction(e -> {
            // 1. Reset Model Data
            model.tx=0; model.ty=0; model.tz=0;
            model.rx=0; model.ry=0; model.rz=0;
            model.scale=1;
            model.shXY=0; model.shXZ=0; model.shYX=0; model.shYZ=0; model.shZX=0; model.shZY=0;
            model.reflYZ=false; model.reflXZ=false; model.reflXY=false;
            model.projXY=false; model.projXZ=false; model.projYZ=false;

            // 2. Reset Visual Slider (Penting!)
            tx.slider.setValue(0); ty.slider.setValue(0); tz.slider.setValue(0);
            rx.slider.setValue(0); ry.slider.setValue(0); rz.slider.setValue(0);
            scale.slider.setValue(1);
            shXY.slider.setValue(0); shXZ.slider.setValue(0);
            shYX.slider.setValue(0); shYZ.slider.setValue(0);
            shZX.slider.setValue(0); shZY.slider.setValue(0);

            // 3. Reset Visual Checkbox
            reflYZ.setSelected(false); reflXZ.setSelected(false); reflXY.setSelected(false);
            projXY.setSelected(false); projXZ.setSelected(false); projYZ.setSelected(false);

            applyCallback.run();
        });

        // Menyusun UI ke VBox
        content.getChildren().addAll(
                title, monitor, new Separator(),
                groupTrans, new Separator(),
                groupRot, new Separator(),
                groupScale, new Separator(),
                groupShear, new Separator(),
                groupRefl, new Separator(),
                groupProj, new Separator(),
                reset // Tombol reset pasti masuk
        );
        Label spacer = new Label("");
        spacer.setMinHeight(100);
        content.getChildren().add(spacer);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #222; -fx-border-color: #222;");

        // Opsional: Hilangkan border fokus agar lebih bersih
        scroll.setFocusTraversable(false);

        return scroll;
    }

    // --- CLASS HELPER UNTUK SLIDER + TEXTFIELD ---
    // (Digunakan agar kita bisa menyimpan referensi slider untuk di-reset)
    private static class SliderWrapper {
        Slider slider;
        TextField textField;
        VBox box; // Container UI
    }

    interface ModelSetter { void set(double val); }

    private SliderWrapper createSliderWrapper(String label, double min, double max, double initVal, ModelSetter setter, Runnable callback) {
        SliderWrapper wrapper = new SliderWrapper();

        // 1. Label
        Label l = new Label(label);
        l.setTextFill(Color.WHITE);
        l.setPrefWidth(60);

        // 2. TextField
        TextField tf = new TextField(String.format("%.1f", initVal));
        tf.setPrefWidth(60);
        tf.setStyle("-fx-font-size: 11px;");
        wrapper.textField = tf;

        // 3. Slider
        Slider s = new Slider(min, max, initVal);
        s.setShowTickMarks(true);
        HBox.setHgrow(s, Priority.ALWAYS);
        wrapper.slider = s;

        // Logic Sinkronisasi
        s.valueProperty().addListener((o, oldVal, newVal) -> {
            setter.set(newVal.doubleValue());
            if (!tf.isFocused()) {
                tf.setText(String.format("%.1f", newVal.doubleValue()));
            }
            callback.run();
        });

        tf.setOnAction(e -> {
            try {
                double val = Double.parseDouble(tf.getText());
                if (val < min) val = min; if (val > max) val = max;
                s.setValue(val);
            } catch (NumberFormatException ex) {
                tf.setText(String.format("%.1f", s.getValue()));
            }
        });

        // Layout baris
        HBox row = new HBox(5, l, s, tf);
        row.setAlignment(Pos.CENTER_LEFT);

        wrapper.box = new VBox(row);
        return wrapper;
    }

    private Label createHeader(String text) {
        Label l = new Label(text);
        l.setTextFill(Color.YELLOW);
        l.setFont(Font.font("System", FontWeight.BOLD, 12));
        return l;
    }

    private CheckBox createCheckBox(String text) {
        CheckBox c = new CheckBox(text);
        c.setTextFill(Color.WHITE);
        return c;
    }
}