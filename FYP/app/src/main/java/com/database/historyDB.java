package com.database;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class historyDB {
    String barcode;
    String pName;
    HistoryDBAdapter mDbAdapter;
    Context mainContext;
    Long mRowId;
    private String parsedHtmlNode = null;

    public historyDB(Context mainCtx){

        this.mainContext =mainCtx;
        mDbAdapter = new HistoryDBAdapter(mainContext);
        mDbAdapter.open();
        try{popuateSampleData();}catch(Exception e){e.printStackTrace();}
    }

    public Cursor getHistory() {
        //* this commented code will be our layer 1 of local database
        ArrayList<String> listProduct = new ArrayList<>();
        try {
            Cursor o = mDbAdapter.fetchAllProducts();
            return o;
        }catch(Exception e){e.printStackTrace();return null;}

    }

    private void popuateSampleData(){
        Cursor c = mDbAdapter.GetCount();
        if(c.getCount() <= 2){

            //saveSearchRecord("012000014338", "Aquafina");
            saveSearchRecord("8964000101957", "Slice Mango");
            saveSearchRecord("8712561315906", "Dove natural touch");

            // saveSearchRecord("695240101428", "Milo");

        }

    }

    public void saveSearchRecord(String barcode, String pName) {
        long id = mDbAdapter.insertProduct(barcode, pName);
        if (id > 0) {
            mRowId = id;

        } else {
            mDbAdapter.updateProduct(barcode, pName);

        }

    }

    class UPCDatabase extends AsyncTask<Void, Void, Void> {

        private Document htmlDocument;
        private String htmlPageUrl = "http://www.upcdatabase.com/item/";
        private String productName;
        public String barcode = "012000014338";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String charset = "UTF-8";
                String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
                String url;
                //System.out.println(google + URLEncoder.encode(searchString, charset));
                //  System.setProperty("http.proxyHost", "10.1.20.18");
                // System.setProperty("http.proxyPort", "9090");
                Document doc = Jsoup.connect(htmlPageUrl + URLEncoder.encode(barcode, charset)).userAgent(userAgent).get();
                Element searchResultCenterContent = doc.select(".data").select("tr").get(2).select("td").get(2);
                System.out.println(searchResultCenterContent.text());
                productName = searchResultCenterContent.text();
                parsedHtmlNode = productName;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            parsedHtmlNode = productName;
        }
    }


}
