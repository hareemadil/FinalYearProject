package com.aaa.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Asif on 11/13/2015.
 */
public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
        public ZXingScannerView mScannerView ;
        private static final String TAG = "CameraPreview";
        TextView bc;
        TextView f;
        TextView d;
        Button proceed;
        BarcodeFormat r;
    View  memecontentView ;
    @Override
        public void onCreate(Bundle state) {
            super.onCreate(state);
            mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
            setContentView(mScannerView);                // Set the scanner view as the content view
           memecontentView= findViewById(R.id.camera_preview);

    }

        @Override
        public void onResume() {
            super.onResume();
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        }

        @Override
        public void onPause() {
            super.onPause();
            mScannerView.stopCamera();           // Stop camera on pause
        }

        @Override
        public void handleResult(Result rawResult) {
            // Do something with the result here
            Log.v(TAG, rawResult.getText()); // Prints scan results
            Toast.makeText(SimpleScannerActivity.this, rawResult.toString() + "  WOW scanned", Toast.LENGTH_LONG).show();
            Toast.makeText(SimpleScannerActivity.this, rawResult.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
            Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
            //Intent scanScreenResult= new Intent("com.aaa.fyp.ScanResultScreen");
            Intent nextScreen = new Intent("com.aaa.fyp.ScanResultScreen");
            nextScreen.putExtra("barcode",rawResult.toString());
            nextScreen.putExtra("format", rawResult.getBarcodeFormat().toString());
           finish();
            startActivity(nextScreen);

        }


    }

