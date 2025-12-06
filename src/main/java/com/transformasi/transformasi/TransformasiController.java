package com.transformasi.transformasi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TransformasiController {
    private final TransformasiModel model;

    // Label untuk menampilkan nilai angka (Koordinat & Rotasi)
    private final Label valTx = new Label("0.0");
    private final Label valTy = new Label("0.0");
    private final Label valTz = new Label("0.0");
    private final Label valRx = new Label("0.0°");
    private final Label valRy = new Label("0.0°");
    private final Label valRz = new Label("0.0°");
    private final Label valScale = new Label("1.0x");

    public TransformasiController(TransformasiModel model) {
        this.model = model;
    }

    public VBox buatSidebar(Runnable applyCallback) {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.setStyle("-fx-background-color: #222;");
        sidebar.setPrefWidth(320);

        // Judul Bagian
        Label title = new Label("Kontrol Transformasi");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // --- TRANSLASI ---
        Slider tx = makeSlider(-300, 300, 0);
        Slider ty = makeSlider(-300, 300, 0);
        Slider tz = makeSlider(-300, 300, 0);

        // Listener Translasi (Update Model + Update Teks Label)
        tx.valueProperty().addListener((o, old, n) -> {
            model.tx = n.doubleValue();
            valTx.setText(format(model.tx));
            applyCallback.run();
        });
        ty.valueProperty().addListener((o, old, n) -> {
            model.ty = n.doubleValue();
            valTy.setText(format(model.ty));
            applyCallback.run();
        });
        tz.valueProperty().addListener((o, old, n) -> {
            model.tz = n.doubleValue();
            valTz.setText(format(model.tz));
            applyCallback.run();
        });

        // --- ROTASI ---
        Slider rx = makeSlider(-180, 180, 0);
        Slider ry = makeSlider(-180, 180, 0);
        Slider rz = makeSlider(-180, 180, 0);

        // Listener Rotasi
        rx.valueProperty().addListener((o, old, n) -> {
            model.rx = n.doubleValue();
            valRx.setText(format(model.rx) + "°");
            applyCallback.run();
        });
        ry.valueProperty().addListener((o, old, n) -> {
            model.ry = n.doubleValue();
            valRy.setText(format(model.ry) + "°");
            applyCallback.run();
        });
        rz.valueProperty().addListener((o, old, n) -> {
            model.rz = n.doubleValue();
            valRz.setText(format(model.rz) + "°");
            applyCallback.run();
        });

        // --- SKALA ---
        Slider scale = makeSlider(0.1, 3, 1);
        scale.valueProperty().addListener((o, old, n) -> {
            model.scale = n.doubleValue();
            valScale.setText(format(model.scale) + "x");
            applyCallback.run();
        });

        // --- REFLEKSI ---
        CheckBox reflYZ = createCheckBox("Refleksi YZ");
        reflYZ.selectedProperty().addListener((o, old, n) -> { model.reflYZ = n; applyCallback.run(); });

        CheckBox reflXZ = createCheckBox("Refleksi XZ");
        reflXZ.selectedProperty().addListener((o, old, n) -> { model.reflXZ = n; applyCallback.run(); });

        CheckBox reflXY = createCheckBox("Refleksi XY");
        reflXY.selectedProperty().addListener((o, old, n) -> { model.reflXY = n; applyCallback.run(); });

        // --- TOMBOL RESET ---
        Button reset = new Button("RESET SEMUA");
        reset.setMaxWidth(Double.MAX_VALUE); // Tombol lebar penuh
        reset.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        reset.setOnAction(e -> {
            // 1. Reset UI Sliders
            tx.setValue(0); ty.setValue(0); tz.setValue(0);
            rx.setValue(0); ry.setValue(0); rz.setValue(0);
            scale.setValue(1);
            reflYZ.setSelected(false); reflXZ.setSelected(false); reflXY.setSelected(false);

            // 2. Reset Text Labels (Penting agar angka kembali ke 0)
            valTx.setText("0.0"); valTy.setText("0.0"); valTz.setText("0.0");
            valRx.setText("0.0°"); valRy.setText("0.0°"); valRz.setText("0.0°");
            valScale.setText("1.0x");

            // 3. Reset Model
            model.tx=0; model.ty=0; model.tz=0;
            model.rx=0; model.ry=0; model.rz=0;
            model.scale=1;
            model.reflXY=false; model.reflYZ=false; model.reflXZ=false;

            // 4. Update View
            applyCallback.run();
        });

        // --- PENYUSUNAN LAYOUT ---
        // Kita menggunakan helper 'createControlGroup' agar rapi
        sidebar.getChildren().addAll(
                title,
                new Separator(),
                createSectionHeader("TRANSLASI (Posisi)"),
                createControlGroup("X:", tx, valTx),
                createControlGroup("Y:", ty, valTy),
                createControlGroup("Z:", tz, valTz),

                new Separator(),
                createSectionHeader("ROTASI (Sudut)"),
                createControlGroup("X:", rx, valRx),
                createControlGroup("Y:", ry, valRy),
                createControlGroup("Z:", rz, valRz),

                new Separator(),
                createSectionHeader("SKALA & REFLEKSI"),
                createControlGroup("Size:", scale, valScale),
                new VBox(5, reflYZ, reflXZ, reflXY),

                new Separator(),
                reset
        );

        return sidebar;
    }

    // --- HELPER METHODS ---

    // Membuat tampilan satu baris: [Judul] [Nilai Angka]
    //                              [Slider..............]
    private VBox createControlGroup(String labelText, Slider slider, Label valueLabel) {
        // Baris atas: Judul di kiri, Nilai di kanan
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label lblName = new Label(labelText);
        lblName.setTextFill(Color.web("#aaaaaa")); // Warna abu-abu muda

        valueLabel.setTextFill(Color.web("#4fc3f7")); // Warna biru muda untuk angka
        valueLabel.setStyle("-fx-font-weight: bold;");

        // Spacer agar nilai mentok kanan
        HBox spacer = new HBox();
        javafx.scene.layout.HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        header.getChildren().addAll(lblName, spacer, valueLabel);

        return new VBox(2, header, slider);
    }

    private Slider makeSlider(double min, double max, double val) {
        Slider s = new Slider(min, max, val);
        return s;
    }

    private CheckBox createCheckBox(String text) {
        CheckBox c = new CheckBox(text);
        c.setTextFill(Color.WHITE);
        return c;
    }

    private Label createSectionHeader(String text) {
        Label l = new Label(text);
        l.setTextFill(Color.YELLOW);
        l.setFont(Font.font("System", FontWeight.BOLD, 12));
        return l;
    }

    // Format double agar hanya 1 angka di belakang koma (misal: 12.5)
    private String format(double val) {
        return String.format("%.1f", val);
    }
}