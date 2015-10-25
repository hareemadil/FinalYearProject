package com.example.alimurtaza.parsetest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** parse code **/
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "P41DF2gmqCqpx4l130YCTKDmUKkr6qAiV12dzPH3", "b3Hyzg2x3iLBsIbRTAzAcnS49WqWQR1wHohWTyAS");


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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


    /** Called when the user clicks the Send button */
    public void pushData(View view) {
        ParseObject gameScore = new ParseObject("Product");
      //  gameScore.put("score", 1337);
        gameScore.put("Name", "Coffee");
        gameScore.put("Price", "133");
        gameScore.saveInBackground();
    }

    /** Called when the user clicks the Send button */
    public void pullData(View view) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        EditText Search =(EditText)findViewById(R.id.Search);
        query.whereStartsWith("Name", ""+Search.getText());
       // query.whereEqualTo("playerName","Sean Plott");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {

                if (e == null) {
                  //  System.out.print(scoreList.get(0).get("Price"));
                  // System.out.println("Name" + "Retrieved " + scoreList.size() + "Name");
                   // Log.getStackTraceString("Name");
                    EditText results = (EditText)findViewById(R.id.results);
                    String resultsStr = "";
                    for(int i=0; i<scoreList.size();i++){
                    //System.out.println("Price" + "" + scoreList.get(i).get("Price"));
                        resultsStr += scoreList.get(i).get("Name") + " : "+ scoreList.get(i).get("Price") + "\n";
                    }
                    results.setText(resultsStr);

                } else {
                    Log.d("Price", "Error: " + e.getMessage());
                }
            }
        });
    }
}
