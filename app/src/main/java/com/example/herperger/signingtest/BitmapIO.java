package com.example.herperger.signingtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by herperger on 1/29/17.
 */
public class BitmapIO {
    private final Activity activity;

    public BitmapIO(Activity activity) {
        this.activity = activity;
    }

    public Bitmap loadBitmap(int resourceId) {
        InputStream inputStream = activity.getResources().openRawResource(resourceId);

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    public Bitmap loadBitmap(String fileName) throws FileNotFoundException {
        Bitmap bitmap = BitmapFactory.decodeStream(activity.openFileInput(fileName));
        return bitmap;
    }

    public void saveBitmap(String fileName, Bitmap bitmap) throws IOException {

        FileOutputStream localFileOutputStream = new FileOutputStream(new File(activity.getFilesDir(), fileName));

        bitmap.compress(Bitmap.CompressFormat.PNG, 90, localFileOutputStream);
        localFileOutputStream.flush();
        localFileOutputStream.close();

        bitmap.recycle();
    }
}
