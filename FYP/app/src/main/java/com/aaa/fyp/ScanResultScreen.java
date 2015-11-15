package com.aaa.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ScanResultScreen extends SimpleScannerActivity {

    View scanned;
    TextView bc;
    TextView f;
    String Barcode;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.scan_screen_with_button);

        Intent prevScreen = getIntent(); // gets the previously created intent

        Barcode=prevScreen.getStringExtra("barcode");
        bc= (TextView)findViewById(R.id.barcode_label);
        bc.setText(Barcode);

        f=(TextView) findViewById(R.id.format_label);
        f.setText( prevScreen.getStringExtra("format"));

        d=(TextView)findViewById(R.id.date_label);
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        d.setText(formattedDate);

    }


    //pcall
    public void displayResults(View view){
        Intent newScreen = new Intent("com.database.ListActivityClass");
        newScreen.putExtra("Product", Barcode);
        finish();
        startActivity(newScreen);
    }

}
