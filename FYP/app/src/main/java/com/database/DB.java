package com.database;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class DB {
    String barcode;
    String pName;
    DBAdapterLogin mDbAdapter;
    Context mainContext;
    Long mRowId;

    public DB(Context mainCtx){

        this.mainContext =mainCtx;
        System.out.println("initialized DB successfully ma shaa Allah");
        mDbAdapter = new DBAdapterLogin(mainContext);
        mDbAdapter.open();
        Parse.enableLocalDatastore(mainContext);
        Parse.initialize(mainContext, "P41DF2gmqCqpx4l130YCTKDmUKkr6qAiV12dzPH3", "b3Hyzg2x3iLBsIbRTAzAcnS49WqWQR1wHohWTyAS");
        popuateSampleData();
    }

    private void getProductName(String barcode) {
        try {
            Cursor c = mDbAdapter.getItemByBarcode(barcode);
            if (c.getCount() == 0) {
                Toast.makeText(mainContext.getApplicationContext(), "Wrong barcode",
                        Toast.LENGTH_LONG).show();
            } else {
                c = mDbAdapter.getProductName(barcode);

                if (c.getCount() == 0) {

                    Toast.makeText(mainContext.getApplicationContext(), "Some Error",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mainContext.getApplicationContext(), "Product Found",
                            Toast.LENGTH_LONG).show();

                    pName = c.getString(0);
                }

            }
        }catch(Exception e){e.printStackTrace();}
    }

    private void popuateSampleData(){
        Cursor c = mDbAdapter.GetCount();
        if(c.getCount() <= 0){
            ArrayList<String[]> barcodeProducts = new ArrayList<>();
            barcodeProducts.add(new String[]{"8961014106305","Milo"});
            //we will add hardcoded barcodes and product name here
            //barcodeProducts.add(new String[]{"0012000811395","Milo"});


            registerProduct("8961014106305", "Milo");
            System.out.println("---------------------->added ");
        }

    }

    private void registerProduct(String barcode,String pName) {
        Toast.makeText(mainContext.getApplicationContext(), "in reg",
                Toast.LENGTH_LONG).show();

        Log.d("data", "in reg");
        long id = mDbAdapter.createTask(barcode, pName);
        if (id > 0) {
            mRowId = id;
            Toast.makeText(mainContext.getApplicationContext(), "Registered ^_^",
                       Toast.LENGTH_LONG).show();
        }
        else {
            mDbAdapter.updateProduct(barcode, pName);
            Toast.makeText(mainContext.getApplicationContext(), "Updated !!",
                       Toast.LENGTH_LONG).show();
        }

    }

    public void saveDataInParse() {
        ParseObject gameScore = new ParseObject("Product");
        //  gameScore.put("score", 1337);
        gameScore.put("Name", "Coffee");
        gameScore.put("Price", "133");
        gameScore.saveInBackground();
    }


    public ParseQuery<ParseObject> pullData(String barcode) {
       // Toast.makeText(mainContext.getApplicationContext(), "ok o acha",Toast.LENGTH_LONG).show();
        getProductName(barcode);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.whereStartsWith("Name", "" + pName);
       final ArrayList<String> results = new ArrayList<>();
        // query.whereEqualTo("playerName","Sean Plott");
        /*
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {

                if (e == null) {
                   // ArrayList<String> results = new ArrayList<>();

                    for (int i = 0; i < scoreList.size(); i++) {
                        //System.out.println("Price" + "" + scoreList.get(i).get("Price"));
                        results.add(scoreList.get(i).get("Name") + " : " + scoreList.get(i).get("Price"));
                        System.out.println("------------------------>"+results.get(i));
                    }


                } else {
                    System.out.println("Error: " + e.getMessage());

                }
            }
        });*/
        return query;
    }


}
