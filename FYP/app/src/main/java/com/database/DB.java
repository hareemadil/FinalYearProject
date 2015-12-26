package com.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Region;
import android.os.AsyncTask;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;


public class DB {
    String barcode;
    String pName = "nothing";
    Layer1DBAdapter mDbAdapter;
    Context mainContext;
    Long mRowId;
    private String parsedHtmlNode = null;

    historyDB HistoryDbObject;

    public DB(Context mainCtx){

        this.mainContext =mainCtx;
        mDbAdapter = new Layer1DBAdapter(mainContext);
        mDbAdapter.open();

        HistoryDbObject=  new historyDB(mainCtx);

       // Parse.enableLocalDatastore(mainContext);
       // Parse.initialize(mainContext, "P41DF2gmqCqpx4l130YCTKDmUKkr6qAiV12dzPH3", "b3Hyzg2x3iLBsIbRTAzAcnS49WqWQR1wHohWTyAS");
        popuateSampleData();
    }

    private void getProductName(String barcode) {
       //* this commented code will be our layer 1 of local database
        boolean previousLayerFailed = true;

        try {
            Cursor c = mDbAdapter.getItemByBarcode(barcode);
            if (c.getCount() == 0) {
                //Toast.makeText(mainContext.getApplicationContext(), "Wrong barcode",
                  //      Toast.LENGTH_LONG).show();

            } else {
                c = mDbAdapter.getProductName(barcode);

                if (c.getCount() == 0) {

                    //Toast.makeText(mainContext.getApplicationContext(), "Some Error",
                      //      Toast.LENGTH_LONG).show();
                } else {
                   // Toast.makeText(mainContext.getApplicationContext(), "Product Found",
                    //        Toast.LENGTH_LONG).show();

                    pName = c.getString(0);
                    previousLayerFailed = false;
                    return;
                }

            }
        }catch(Exception e){e.printStackTrace();}


        // -----------Layer2
        if(previousLayerFailed) {

            UPCDatabase upcLayer2 = new UPCDatabase();
            upcLayer2.barcode = barcode;
            upcLayer2.execute();

            try {
                upcLayer2.get();
                pName = parsedHtmlNode;
                previousLayerFailed = false;
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();previousLayerFailed = true;
            } catch (ExecutionException e) {
                e.printStackTrace();previousLayerFailed = true;
            }
            // -----------Layer2 end
        }
        if(previousLayerFailed){}


    }

    private void popuateSampleData(){
        Cursor c = mDbAdapter.GetCount();
        if(c.getCount() <= 7){

            //we will add hardcoded barcodes and product name here
            //barcodeProducts.add(new String[]{"0012000811395","Milo"});

            // registerProduct("012000014338", "Aquafina");

            registerProduct("8961014106305", "Aquafina");
            registerProduct("8964000101957", "Slice Mango");
            registerProduct("8712561315906", "Dove natural touch");
            registerProduct("8414135625748", "Nike woman");
            registerProduct("671866116817", "Potato Sticks");
            registerProduct("6281036113009", "Lays salted");
           // registerProduct("695240101428", "Milo");
            registerProduct("695240101428", "Dolar ink ");
            registerProduct("3800020456323", "Kitkat Chunky");

            System.out.println("succesfully populated local database ");

            registerProduct("8961008208114", "Milo 200ml");

            System.out.println("---------------------->added ");

        }

    }

    private void registerProduct(String barcode,String pName) {

        long id = mDbAdapter.createTask(barcode, pName);
        if (id > 0) {
            mRowId = id;

        } else {
            mDbAdapter.updateProduct(barcode, pName);

        }

    }

    public void saveDataInParse() {
        ParseObject gameScore = new ParseObject("Product");
        //  gameScore.put("score", 1337);
        gameScore.put("Name", "Coffee");
        gameScore.put("Price", "133");
        gameScore.saveInBackground();
    }


    public ParseQuery<ParseObject> getProducts(String barcode) {
        getProductName(barcode);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
     //   ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Product");
        //query.whereStartsWith("Name", "" + pName);
        query.whereMatches("Name", "(" + pName + ")", "i");
        //query2.whereMatchesKeyInQuery("Subcategory", "Subcategory", query.whereMatches("Name", "(" + pName + ")", "i"));
        if(pName.compareTo("nothing") == 0 ){
            Toast.makeText(mainContext.getApplicationContext(), "Nothing found for this barcode!!",
                Toast.LENGTH_LONG).show();
        }else{
               try{
                   HistoryDbObject.saveSearchRecord(barcode,pName);
               }catch(SQLiteConstraintException e){
                   Toast.makeText(mainContext.getApplicationContext(), "barcode already in the history database!!",
                           Toast.LENGTH_LONG).show();
                   System.out.println("+++++++++++++__(*^%$@#$%^&*()_=== barcode already in the history database!!");
                   return query;
               }catch(Exception ex){
                   return query;

               }
        }
        /*
         final ArrayList<String> results = new ArrayList<>();
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


    public ParseQuery<ParseObject> getSimilar(String pName) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Product");
        //query.whereStartsWith("Name", "" + pName);
        //query.whereMatches("Name", "(" + pName + ")", "i");
        query2.whereMatchesKeyInQuery("Subcategory","Subcategory", query.whereMatches("Name", "(" + pName + ")", "i"));


        return query;
    }


    class UPCDatabase extends AsyncTask<Void, Void, Void> {

        private Document htmlDocument;
        private String htmlPageUrl = "http://www.upcdatabase.com/item/";
        private String productName;
        public String barcode;// = "012000014338";
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
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mainContext.getApplicationContext(), "Failed to find barcode in upcDatabase!!",
                        Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            parsedHtmlNode = productName;
        }
    }


}
