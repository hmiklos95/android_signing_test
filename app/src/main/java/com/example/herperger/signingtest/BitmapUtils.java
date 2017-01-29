package com.example.herperger.signingtest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by herperger on 1/29/17.
 */
public class BitmapUtils {
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    public static Bitmap resize(Bitmap toResize, int width, int height) {
        return Bitmap.createScaledBitmap(toResize, width, height, false);
    }
}
