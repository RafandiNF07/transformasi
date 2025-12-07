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
    // Shear
    public double shXY, shXZ, shYX, shYZ, shZX, shZY;

    // Proyeksi
    public boolean projXY, projYZ, projXZ;

    public Affine generateAffine() {
        Affine a = new Affine();

        if (reflYZ) a.append(new Scale(-1, 1, 1));
        if (reflXZ) a.append(new Scale(1, -1, 1));
        if (reflXY) a.append(new Scale(1, 1, -1));
        // Proyeksi
        if (projXY) a.append(new Affine(
                1,0,0,0,
                0,1,0,0,
                0,0,0,0
        ));
        if (projYZ) a.append(new Affine(
                0,0,0,0,
                0,1,0,0,
                0,0,1,0
        ));
        if (projXZ) a.append(new Affine(
                1,0,0,0,
                0,0,0,0,
                0,0,1,0
        ));

        a.append(new Rotate(-rx, Rotate.X_AXIS));
        a.append(new Rotate(-ry, Rotate.Y_AXIS));
        a.append(new Rotate(-rz, Rotate.Z_AXIS));
        a.append(new Scale(scale, scale, scale));

        // Shear (manual matrix)
        Affine shear = new Affine(
                1, shXY, shXZ, 0,
                shYX, 1, shYZ, 0,
                shZX, shZY, 1, 0
        );
        a.append(shear);

        a.append(new Translate(tx, -ty, tz));


        return a;
    }
}
