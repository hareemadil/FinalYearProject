package com.aaa.fyp;

/**
 * Created by Jahaanzaib on 26-Dec-15.
 */



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.SlideMenu.BaseActivity;
import com.parse.Parse;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "P41DF2gmqCqpx4l130YCTKDmUKkr6qAiV12dzPH3", "b3Hyzg2x3iLBsIbRTAzAcnS49WqWQR1wHohWTyAS");

        Thread timer = new Thread() {
            public void run() {
                try{
                    sleep(4000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    Intent landing = new Intent("com.aaa.fyp.MainActivity");
                    startActivity(landing);
                }

            }
        };
        timer.start();
    }

}
