package com.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.aaa.fyp.R;

public class MainActivityFrag extends Fragment implements View.OnClickListener {

    ImageButton scan;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.home_screen, container, false);
        // setContentView(R.layout.home_screen);
        scan=(ImageButton)v.findViewById(R.id.scanBtn);
        scan.setOnClickListener(this);

        return v;
    }


    public void ScanBarcode(View view) {
        Intent newScreen = new Intent("com.aaa.fyp.SimpleScannerActivity");
        // finish();

        startActivity(newScreen);
        //  finish();
    }


    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
         return true;
     }
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.scanBtn:
                Intent newScreen = new Intent("com.aaa.fyp.SimpleScannerActivity");
                startActivity(newScreen);
                break;
        }
    }

}
