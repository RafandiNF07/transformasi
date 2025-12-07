package com.transformasi.transformasi;

import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

public class DuaDimensi {

    // Variabel transformasi
    private double transX = 0;
    private double transY = 0;
    private double rotateDegree = 0;
    private double scaleValue = 1.0;
    private double shearX = 0.0;
    private double shearY = 0.0;
    private boolean reflectX = false;
    private boolean reflectY = false;
    private boolean projX = false;
    private boolean projY = false;

    private Canvas canvas;
    private Text matrixText;

    public Parent tampilkan() {
        BorderPane root = new BorderPane();

        // Canvas untuk menggambar
        canvas = new Canvas(550, 600);
        root.setCenter(canvas);

        // Panel kontrol di kanan
        VBox controlPanel = new VBox(12);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setPrefWidth(250);

        // Slider + TextField Translasi X
        Label translasiX = new Label("Translasi X");
        Slider sTranslasiX = new Slider(-200, 200, 0);
        TextField tfTranslasiX = new TextField("0");
        styleTextField(tfTranslasiX);
        sTranslasiX.valueProperty().addListener((obs, o, n) -> {
            transX = n.doubleValue();
            tfTranslasiX.setText(String.format("%.0f", n.doubleValue()));
            draw();
        });
        tfTranslasiX.setOnAction(e -> {
            try {
                double val = Double.parseDouble(tfTranslasiX.getText());
                sTranslasiX.setValue(val);
            } catch (NumberFormatException ex) {
                tfTranslasiX.setText(String.format("%.0f", sTranslasiX.getValue()));
            }
        });
        HBox translasiXBox = new HBox(5, translasiX, tfTranslasiX);

        // Translasi Y
        Label translasiY = new Label("Translasi Y");
        Slider sTranslasiY = new Slider(-200, 200, 0);
        TextField tfTranslasiY = new TextField("0");
        styleTextField(tfTranslasiY);
        sTranslasiY.valueProperty().addListener((obs, o, n) -> {
            transY = n.doubleValue();
            tfTranslasiY.setText(String.format("%.0f", n.doubleValue()));
            draw();
        });
        tfTranslasiY.setOnAction(e -> {
            try {
                double val = Double.parseDouble(tfTranslasiY.getText());
                sTranslasiY.setValue(val);
            } catch (NumberFormatException ex) {
                tfTranslasiY.setText(String.format("%.0f", sTranslasiY.getValue()));
            }
        });
        HBox translasiYBox = new HBox(5, translasiY, tfTranslasiY);

        // Rotasi
        Label rotasi = new Label("Rotasi (derajat)");
        Slider sRotasi = new Slider(-360, 360, 0);
        TextField tfRotasi = new TextField("0");
        styleTextField(tfRotasi);
        sRotasi.valueProperty().addListener((obs, o, n) -> {
            rotateDegree = n.doubleValue();
            tfRotasi.setText(String.format("%.0f", rotateDegree));
            draw();
        });
        tfRotasi.setOnAction(e -> {
            try {
                double val = Double.parseDouble(tfRotasi.getText());
                sRotasi.setValue(val);
            } catch (NumberFormatException ex) {
                tfRotasi.setText(String.format("%.0f", sRotasi.getValue()));
            }
        });
        HBox rotasiBox = new HBox(5, rotasi, tfRotasi);

        // Skala
        Label skalar = new Label("Skala");
        Slider sSkalar = new Slider(0.1, 4, 1);
        TextField tfSkalar = new TextField("1");
        styleTextField(tfSkalar);
        sSkalar.valueProperty().addListener((obs, o, n) -> {
            scaleValue = n.doubleValue();
            tfSkalar.setText(String.format("%.2f", scaleValue));
            draw();
        });
        tfSkalar.setOnAction(e -> {
            try {
                double val = Double.parseDouble(tfSkalar.getText());
                sSkalar.setValue(val);
            } catch (NumberFormatException ex) {
                tfSkalar.setText(String.format("%.2f", sSkalar.getValue()));
            }
        });
        HBox skalarBox = new HBox(5, skalar, tfSkalar);

        // Shear X
        Label shearXLabel = new Label("Shear X");
        Slider sShearX = new Slider(-1.0, 1.0, 0);
        TextField tfShearX = new TextField("0");
        styleTextField(tfShearX);
        sShearX.valueProperty().addListener((obs, o, n) -> {
            shearX = n.doubleValue();
            tfShearX.setText(String.format("%.2f", shearX));
            draw();
        });
        tfShearX.setOnAction(e -> {
            try {
                double val = Double.parseDouble(tfShearX.getText());
                sShearX.setValue(val);
            } catch (NumberFormatException ex) {
                tfShearX.setText(String.format("%.2f", sShearX.getValue()));
            }
        });
        HBox shearXBox = new HBox(5, shearXLabel, tfShearX);

        // Shear Y
        Label shearYLabel = new Label("Shear Y");
        Slider sShearY = new Slider(-1.0, 1.0, 0);
        TextField tfShearY = new TextField("0");
        styleTextField(tfShearY);
        sShearY.valueProperty().addListener((obs, o, n) -> {
            shearY = n.doubleValue();
            tfShearY.setText(String.format("%.2f", shearY));
            draw();
        });
        tfShearY.setOnAction(e -> {
            try {
                double val = Double.parseDouble(tfShearY.getText());
                sShearY.setValue(val);
            } catch (NumberFormatException ex) {
                tfShearY.setText(String.format("%.2f", sShearY.getValue()));
            }
        });
        HBox shearYBox = new HBox(5, shearYLabel, tfShearY);

        // Checkbox refleksi & proyeksi
        CheckBox cbReflectX = new CheckBox("Refleksi terhadap sumbu X");
        cbReflectX.selectedProperty().addListener((obs, o, n) -> { reflectX = n; draw(); });
        CheckBox cbReflectY = new CheckBox("Refleksi terhadap sumbu Y");
        cbReflectY.selectedProperty().addListener((obs, o, n) -> { reflectY = n; draw(); });
        CheckBox cbProjX = new CheckBox("Proyeksi ke sumbu X");
        cbProjX.selectedProperty().addListener((obs, o, n) -> { projX = n; draw(); });
        CheckBox cbProjY = new CheckBox("Proyeksi ke sumbu Y");
        cbProjY.selectedProperty().addListener((obs, o, n) -> { projY = n; draw(); });

        // Tombol reset
        Button reset = new Button("Reset Posisi");
        reset.setOnAction(e -> {
            sTranslasiX.setValue(0);
            sTranslasiY.setValue(0);
            sRotasi.setValue(0);
            sSkalar.setValue(1);
            sShearX.setValue(0);
            sShearY.setValue(0);
            cbReflectX.setSelected(false);
            cbReflectY.setSelected(false);
            cbProjX.setSelected(false);
            cbProjY.setSelected(false);
        });

        // Text untuk menampilkan matriks
        matrixText = new Text();
        matrixText.setFill(Color.BLACK);

        controlPanel.setPrefWidth(300); // atau lebih besar, misalnya 350
        controlPanel.setMaxWidth(Double.MAX_VALUE); // biar bisa melebar penuh
        controlPanel.getChildren().addAll(
                translasiXBox, sTranslasiX,
                translasiYBox, sTranslasiY,
                rotasiBox, sRotasi,
                skalarBox, sSkalar,
                shearXBox, sShearX,
                shearYBox, sShearY,
                cbReflectX, cbReflectY,
                cbProjX, cbProjY,
                reset,
                new Label("Matriks Transformasi:"),
                matrixText
        );

        // Bungkus dengan ScrollPane
        ScrollPane scrollPane = new ScrollPane(controlPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefViewportWidth(300); // viewport lebar default


        root.setRight(scrollPane);
        draw();
        return root;
    }

    private void styleTextField(TextField tf) {
        tf.setPrefWidth(60);
        tf.setStyle("-fx-background-color: #f9f9f9;" +
                "-fx-border-color: #888;" +
                "-fx-border-radius: 4;" +
                "-fx-padding: 3;" +
                "-fx-font-size: 12px;");
    }


    private void draw() {
        GraphicsContext g2 = canvas.getGraphicsContext2D();
        g2.setFill(Color.WHITE);
        g2.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Gambar grid 2D
        drawGrid(g2);

        // Buat matriks transformasi homogen
        Affine affine = new Affine();
        if (reflectX) affine.appendScale(1, -1);
        if (reflectY) affine.appendScale(-1, 1);
        // Proyeksi
        if (projX) affine.append(new Affine(1,0,0, 0,0,0)); // proyeksi ke X
        if (projY) affine.append(new Affine(0,0,0, 0,1,0)); // proyeksi ke Y
        affine.appendRotation(-rotateDegree);
        affine.appendScale(scaleValue, scaleValue);
        affine.appendShear(shearX, shearY);
        affine.appendTranslation(transX, -transY);

        // Hitung posisi bayangan dengan affine langsung
        javafx.geometry.Point2D p = affine.transform(0.0, 0.0); // pusat objek (0,0)
        double xPrime = p.getX();        // JavaFX X (kanan positif)
        double yPrime = -p.getY();       // balik tanda Y agar sesuai konvensi matematis (Y ke atas)

// Ambil elemen matriks untuk display
        double mxx = affine.getMxx();
        double mxy = affine.getMxy();
        double tx = affine.getTx();
        double myx = affine.getMyx();
        double myy = affine.getMyy();
        double ty = -affine.getTy(); // tampilkan Y-up version

// Format teks matriks
        String matrixStr = String.format(
                """
                [ %5.2f  %5.2f  %5.2f ]
                [ %5.2f  %5.2f  %5.2f ]
                [ 0.00   0.00   1.00  ]
           \s
                Posisi Pusat Bayangan: (%.2f , %.2f)
                Shear X = %.2f , Shear Y = %.2f
                Refleksi X = %b Refleksi Y = %b\s
                Proyeksi X = %b Proyeksi Y = %b
               \s""",
                mxx, mxy, tx,
                myx, myy, ty,
                xPrime, yPrime,
                shearX, shearY,
                reflectX, reflectY,
                projX, projY
        );
        matrixText.setText(matrixStr);

        g2.save();

        // Pusat canvas
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        // Terapkan transformasi ke objek
        g2.translate(centerX, centerY);
        if (reflectX) g2.scale(1, -1);
        if (reflectY) g2.scale(-1, 1);
        // Proyeksi
        if (projX) g2.transform(new Affine(1,0,0, 0,0,0));
        if (projY) g2.transform(new Affine(0,0,0, 0,1,0));
        g2.rotate(-rotateDegree);
        g2.scale(scaleValue, scaleValue);
        g2.transform(new Affine(1, shearX, 0, shearY, 1, 0));
        g2.translate(transX, -transY);


        // Gambar kotak
        int rectSize = 50;
        int x = -rectSize / 2;
        int y = -rectSize / 2;

        g2.setFill(Color.DODGERBLUE);
        g2.fillRect(x, y, rectSize, rectSize);

        g2.setStroke(Color.BLACK);
        g2.setLineWidth(3);
        g2.strokeRect(x, y, rectSize, rectSize);

        // Titik pusat kotak
        g2.setFill(Color.RED);
        g2.fillOval(-3, -3, 6, 6);

        g2.restore();
        // Tambahkan garis bayangan proyeksi agar terlihat jelas
        g2.setStroke(Color.RED);
        g2.setLineWidth(2);
        double cx = centerX + xPrime;
        double cy = centerY - yPrime;

        if (projX) g2.strokeLine(cx - 25, centerY, cx + 25, centerY);
        if (projY) g2.strokeLine(centerX, cy - 25, centerX, cy + 25);

        // Info text di canvas
        g2.setFill(Color.DARKGRAY);
        g2.fillText("Posisi input: X=" + transX + ", Y=" + transY, 10, 20);
        g2.fillText("Rotasi: " + rotateDegree + "°", 10, 40);
        g2.fillText("Skala: " + scaleValue + "x", 10, 60);
        g2.fillText("ShearX: " + shearX + " , ShearY: " + shearY, 10, 80);
        g2.fillText("Posisi bayangan: (" + String.format("%.2f", xPrime) + " , " + String.format("%.2f", yPrime) + ")", 10, 100);
        g2.fillText("Refleksi X: " + reflectX + " , Refleksi Y: " + reflectY, 10, 120);
        g2.fillText("Proyeksi X: " + projX + " , Proyeksi Y: " + projY, 10, 140);
    }

    // Menggambar grid 2D: latar putih, sumbu hitam, grid abu tipis
    private void drawGrid(GraphicsContext g2) {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        double cx = w / 2.0;
        double cy = h / 2.0;

        int step = 25;
        Color gridColor = Color.rgb(0, 0, 0, 0.12);
        g2.setStroke(gridColor);
        g2.setLineWidth(1.0);

        for (int x = 0; x <= w; x += step) {
            g2.strokeLine(x, 0, x, h);
        }
        for (int y = 0; y <= h; y += step) {
            g2.strokeLine(0, y, w, y);
        }
        // Sumbu X dan Y (hitam tebal)
        g2.setStroke(Color.BLACK);
        g2.setLineWidth(2.5);
        // Sumbu X (melalui centerY)
        g2.strokeLine(0, cy, w, cy);
        // Sumbu Y (melalui centerX)
        g2.strokeLine(cx, 0, cx, h);

        // Panah kecil di ujung sumbu
        drawAxisArrows(g2, cx, cy, w, h);

        // Label angka pada sumbu (kelipatan step)
        g2.setFill(Color.BLACK);
        int labelStep = step * 2;
        // Label sumbu X (kanan-kiri dari pusat)
        for (int x = (int)cx + labelStep; x < w; x += labelStep) {
            int val = (x - (int)cx);
            g2.fillText(String.valueOf(val), x + 2, cy - 6);
        }
        for (int x = (int)cx - labelStep; x > 0; x -= labelStep) {
            int val = (x - (int)cx);
            g2.fillText(String.valueOf(val), x + 2, cy - 6);
        }
        // Label sumbu Y (atas-bawah dari pusat, ingat JavaFX Y+ ke bawah)
        for (int y = (int)cy + labelStep; y < h; y += labelStep) {
            int val = (int)(-(y - cy)); // tampilkan dengan konvensi matematis (atas positif)
            g2.fillText(String.valueOf(val), cx + 6, y - 2);
        }
        for (int y = (int)cy - labelStep; y > 0; y -= labelStep) {
            int val = (int)(-(y - cy));
            g2.fillText(String.valueOf(val), cx + 6, y - 2);
        }
    }

    private void drawAxisArrows(GraphicsContext g2, double cx, double cy, double w, double h) {
        // Panah sumbu X (ujung kiri dan kanan)
        g2.setFill(Color.BLACK);
        // kanan
        g2.fillPolygon(
                new double[]{w - 10, w - 10, w - 2},
                new double[]{cy - 6, cy + 6, cy},
                3
        );
        // kiri
        g2.fillPolygon(
                new double[]{10, 10, 2},
                new double[]{cy - 6, cy + 6, cy},
                3
        );
        // Panah sumbu Y (ujung atas dan bawah)
        // atas
        g2.fillPolygon(
                new double[]{cx - 6, cx + 6, cx},
                new double[]{10, 10, 2},
                3
        );
        // bawah
        g2.fillPolygon(
                new double[]{cx - 6, cx + 6, cx},
                new double[]{h - 10, h - 10, h - 2},
                3
        );
    }
}