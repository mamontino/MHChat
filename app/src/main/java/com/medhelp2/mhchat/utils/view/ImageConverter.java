package com.medhelp2.mhchat.utils.view;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageConverter
{
    public static byte[] getByteArrayfromBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    public static Bitmap getBitmapfromByteArray(byte[] bitmap)
    {
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }


    public static Bitmap convertBase64StringToBitmap(String source)
    {
        byte[] rawBitmap = Base64.decode(source.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(rawBitmap, 0, rawBitmap.length);
    }
}
