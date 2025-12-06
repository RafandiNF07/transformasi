package com.transformasi.transformasi;

import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class TransformasiModel {
    public double tx, ty, tz;
    public double rx, ry, rz;
    public double scale = 1;
    public boolean reflXY, reflYZ, reflXZ;

    public Affine generateAffine() {
        Affine a = new Affine();

        if (reflYZ) a.append(new Scale(-1, 1, 1));
        if (reflXZ) a.append(new Scale(1, -1, 1));
        if (reflXY) a.append(new Scale(1, 1, -1));

        a.append(new Translate(tx, -ty, tz));
        a.append(new Rotate(rx, Rotate.X_AXIS));
        a.append(new Rotate(ry, Rotate.Y_AXIS));
        a.append(new Rotate(rz, Rotate.Z_AXIS));
        a.append(new Scale(scale, scale, scale));

        return a;
    }
}
