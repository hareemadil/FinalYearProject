package com.aaa.fyp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Asif on 11/14/2015.
 */
public class ScanResultScreen extends SimpleScannerActivity {

    View scanned;
    TextView bc;
    TextView f;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.scan_screen_with_button);


    }
    public void ScanResultScreen(View v, String barcode, String format){
      //scanned= (View) findViewById(R.id.view);
        //scanned=v;
        bc= (TextView)findViewById(R.id.barcode_label);
        bc.setText(barcode);
        f=(TextView) findViewById(R.id.format_label);
        f.setText(format);

    }
}
