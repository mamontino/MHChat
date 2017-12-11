package com.medhelp2.mhchat.utils.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

import com.medhelp2.mhchat.utils.main.AppConstants;
import com.squareup.picasso.Transformation;

public class AvatarRounded implements Transformation
{
    @Override
    public Bitmap transform(Bitmap source)
    {
        int radius = (int) (source.getHeight() / AppConstants.RADIUS_ROUND_AVATAR);
        int diam = radius << 1;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Bitmap scaledBitmap = scaleTo(source, diam);
        final Shader shader = new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        Bitmap targetBitmap = Bitmap.createBitmap(diam, diam, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);

        canvas.drawCircle(radius, radius, radius, paint);
        source.recycle();
        //        targetBitmap.recycle();
        return targetBitmap;
    }

    public static Bitmap scaleTo(Bitmap source, int size)
    {
        int destWidth = source.getWidth();
        int destHeight = source.getHeight();

        destHeight = destHeight * size / destWidth;
        destWidth = size;

        if (destHeight < size)
        {
            destWidth = destWidth * size / destHeight;
            destHeight = size;
        }

        Bitmap destBitmap = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(destBitmap);
        canvas.drawBitmap(source, new Rect(0, 0, source.getWidth(), source.getHeight()), new Rect(0, 0, destWidth, destHeight), new Paint(Paint.ANTI_ALIAS_FLAG));
        return destBitmap;
    }

    @Override
    public String key()
    {
        return "square()";
    }
}