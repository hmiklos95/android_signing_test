package com.example.herperger.signingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private static final int SIGNING_REQUEST = 1;

    private final BitmapIO bitmapIO;

    private ImageView signedDocumentView;

    public MainActivity() {
        bitmapIO = new BitmapIO(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    private void initLayout() {
        signedDocumentView = (ImageView) findViewById(R.id.test_view);
    }

    private String getDocumentFileName() {
        TypedValue value = new TypedValue();
        getResources().getValue(R.raw.munkalap, value, true);

        return value.string.toString();
    }

    public void openSigningActivity(View view) {
        Intent intent = new Intent(this, SigningActivity.class);

        //tent.putExtra(SigningActivity.REQUEST_FILE_NAME_TO_SIGN, getDocumentFileName());
        intent.putExtra(SigningActivity.REQUEST_FILE_ID_TO_SIGN, R.raw.munkalap);

        startActivityForResult(intent, SIGNING_REQUEST);
    }

    private void fillWithSignedDocumentImage(String fileName) {
        Bitmap bitmap = null;
        try {
            bitmap = bitmapIO.loadBitmap(fileName);

            signedDocumentView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.w("signature", "could not load document signed image");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SIGNING_REQUEST) {
            if (resultCode == RESULT_OK) {
                fillWithSignedDocumentImage(data.getStringExtra(SigningActivity.RESULT_SAVED_FILE_NAME));
            } else {
                Toast.makeText(this, "Failure while signing document", Toast.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
