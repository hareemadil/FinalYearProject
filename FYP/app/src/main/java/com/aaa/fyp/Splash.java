package com.aaa.fyp;

/**
 * Created by Jahaanzaib on 26-Dec-15.
 */



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.SlideMenu.BaseActivity;
import com.database.DB;
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
        Toast.makeText(this, "Preparing LocalDB(will take few minutes when running the app for the First time)",
                Toast.LENGTH_LONG).show();
        Thread timer = new Thread() {
            public void run() {
                try{
                    sleep(3000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    try{

                        DB dbob= new DB(Splash.this);
                        dbob.popuateSampleData();
                        dbob.sqliteClose();
                    }catch(Exception e){e.printStackTrace();}
                    Intent landing = new Intent("com.aaa.fyp.MainActivity");
                    startActivity(landing);
                }

            }
        };
        timer.start();
    }

}
