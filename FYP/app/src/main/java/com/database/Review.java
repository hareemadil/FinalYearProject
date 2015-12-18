package com.database;

/**
 * Created by Qureshis on 11/21/2015.
 */



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.SlideMenu.BaseActivity;
import com.aaa.fyp.R;

public class Review extends BaseActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // you have to create game.xml
        //setContentView(R.layout.reviewlayout);
        getLayoutInflater().inflate(R.layout.reviewlayout, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        //  TextView texts = (TextView) findViewById(R.id.textView1);
      //  Intent i = getIntent();
      ///  Intent i2 = getIntent();
      //  String text1 =i2.getStringExtra("str");
       // texts.setText(text1);
    }
}