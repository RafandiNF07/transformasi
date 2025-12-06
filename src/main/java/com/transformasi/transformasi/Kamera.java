package com.transformasi.transformasi;

import javafx.scene.Camera;
import javafx.scene.SubScene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Kamera {
    private final Camera kamera;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;

    private final Rotate rotateX = new Rotate(-25, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    private final Translate cameraPosition = new Translate(0, -50, -1000);

    public Kamera(Camera camera) {
        this.kamera = camera;
        camera.getTransforms().addAll(
                rotateX,
                rotateY,
                cameraPosition
        );
    }

    public void attachControl(SubScene scene) {
        scene.setOnMousePressed(e -> {
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
            anchorAngleX = rotateX.getAngle();
            anchorAngleY = rotateY.getAngle();
        });

        scene.setOnMouseDragged(e -> {
            double dx = e.getSceneX() - anchorX;
            double dy = e.getSceneY() - anchorY;

            if (e.isPrimaryButtonDown()) {
                rotateY.setAngle(anchorAngleY + dx * 0.3);
                rotateX.setAngle(anchorAngleX - dy * 0.3);
            }

            if (e.isSecondaryButtonDown()) {
                cameraPosition.setX(Math.max(-500, Math.min(500, cameraPosition.getX()  - dx * 0.5)));
                cameraPosition.setY(Math.max(-500, Math.min(500, cameraPosition.getY()  - dy * 0.5)));

            }
        });

        scene.setOnScroll(e -> {
            double zoom = e.getDeltaY();
            cameraPosition.setZ(cameraPosition.getZ() + zoom * 0.5);
        });
    }
}
