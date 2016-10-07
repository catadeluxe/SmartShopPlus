package ca.qc.bdeb.p55.smartshopplus.bd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by 1379708 on 2016-10-07.
 */
public class DbBitmapUtility {


    /**
     * Convertit un bitmap en tableau de bytes
     *
     * @param bitmap le bitmap à convertir
     * @return le résultat de la conversion en tableau de bytes
     */
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     * Convertit un tableau de bytes en bitmap
     *
     * @param image le tableau de bytes à convertir
     * @return le résultat de la conversion en bitmap
     */
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
