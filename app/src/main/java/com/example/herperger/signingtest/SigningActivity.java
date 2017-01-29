
package com.example.herperger.signingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SigningActivity extends AppCompatActivity {

    public static final String REQUEST_FILE_NAME_TO_SIGN = "fileNameToSign";

    public static final String REQUEST_FILE_ID_TO_SIGN = "requestFileIdToSign";

    public static final String RESULT_SAVED_FILE_NAME = "savedFileName";

    private static final String SIGNED_PREFIX = "signed_";

    private SignaturePad signaturePad;
    private ImageView documentView;
    private Button saveButton;

    private Bitmap documentBitmap;

    private final BitmapIO bitmapIO;

    private String documentPath;
    private int documentId;

    public SigningActivity() {
        bitmapIO = new BitmapIO(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing);

        loadIntentData();
        initLayout();
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        documentPath = intent.getStringExtra(REQUEST_FILE_NAME_TO_SIGN);
        documentId = intent.getIntExtra(REQUEST_FILE_ID_TO_SIGN, -1);
    }

    private void initLayout() {
        signaturePad = (SignaturePad) findViewById(R.id.signature_pad);

        documentView = (ImageView) findViewById(R.id.document);

        documentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                documentView.setImageBitmap(documentBitmap = loadDocumentBitmap(documentView.getWidth(), documentView.getHeight()));
            }
        });
    }

    public void saveSignedDocument(View view) {
        if(documentBitmap != null) {
            Bitmap signedDocumentBitmap = createSignedBitmap();

            try {
                bitmapIO.saveBitmap(SIGNED_PREFIX + "munkalap.jpg", signedDocumentBitmap);
                onSuccess(SIGNED_PREFIX + "munkalap.jpg");
            } catch (IOException e) {
                onFailure();
            }
        }

        onFailure();
    }

    private void onSuccess(String savedFilename) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_SAVED_FILE_NAME, savedFilename);

        setResult(RESULT_OK, intent);

        finish();
    }

    private void onFailure() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private Bitmap createSignedBitmap() {
        return BitmapUtils.overlay(documentBitmap, signaturePad.getTransparentSignatureBitmap());
    }

    private Bitmap loadDocumentBitmap(int resizedWidth, int resizedHeight) {
        Bitmap bitmap = null;
        bitmap = bitmapIO.loadBitmap(documentId);

        return BitmapUtils.resize(bitmap, resizedWidth, resizedHeight);
    }

}
