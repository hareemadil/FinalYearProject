package com.aaa.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.SlideMenu.BaseActivity;
import com.aaa.fyp.R;
import com.database.DB;
import com.parse.Parse;
//import com.slide_menu.AndroidMenuMainActivity;
//import com.slidingmenu_tabhostviewpager.SlideMainActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.home_screen);
        getLayoutInflater().inflate(R.layout.home_screen, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

    }


    public void ScanBarcode(View view){
        Intent newScreen = new Intent("com.aaa.fyp.SimpleScannerActivity");
        // finish();

        startActivity(newScreen);
        finish();
    }




}
