package com.database;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.aaa.fyp.R;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

public class searchResult extends Activity {

    ListView list;
    final ArrayList<String> links  = new ArrayList<>();
    DB dbObject;
    ParseQuery<ParseObject> Products;
    List<ParseObject> globalScoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity_class);

        Intent myIntent = getIntent(); // gets the previously created intent
        Intent nextScreen = new Intent("com.aaa.fyp.ScanResultScreen");
        nextScreen.putExtra("barcode",myIntent.getStringExtra("barcode"));
        nextScreen.putExtra("format", myIntent.getStringExtra("format"));
        //finish();
        startActivity(nextScreen);
        String Barcode = myIntent.getStringExtra("Product");
        System.out.println("in list view ---- >" + Barcode);

        try{
            dbObject=  new DB(this);
            //to change query please refer to the function getProducts
            Products = dbObject.getProducts(Barcode);
            System.out.println("SUCCESSSSS DAMN &@!#$%^&*(**&^%$## s");
            Products.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    globalScoreList = scoreList;
                    if (e == null) {
                        // ArrayList<String> results = new ArrayList<>();
                        String[] itemname = new String[scoreList.size()];
                        String[] Prices = new String[scoreList.size()];
                        //will have to work on how to make these logo dynamic
                        Integer[] imgid2={
                                R.mipmap.qne_logo,//qne
                                R.mipmap.gomart_logo,//gomart
                                R.mipmap.aaramshop,
                                R.mipmap.doorstep,
                                R.mipmap.rashan_lelo,
                                R.mipmap.shoprex_logo,
                                R.mipmap.cartpk,
                                R.mipmap.qne_logo//qne
                        };
                        ArrayList<String> webstores = new ArrayList<String>();
                        webstores.add("qne.com.pk");
                        webstores.add("gomart.com");
                        webstores.add("aaramshop.pk");
                        webstores.add("doorstep.pk");
                        webstores.add("rashanlelo.pk");
                        webstores.add("shoprex.com");
                        webstores.add("cartpk.com");
                        webstores.add("qne.pk");


                        Integer[] imgid= new Integer[scoreList.size()];

                        //populates the list of products
                        for (int i = 0; i < scoreList.size(); i++) {

                            //System.out.println("Price" + "" + scoreList.get(i).get("Price"));
                            //results.add(scoreList.get(i).get("Name") + " : " + scoreList.get(i).get("Price")); itemname[i] = scoreList.get(i).get("Name")+"";
                            Prices[i] = scoreList.get(i).get("Price")+"";
                            itemname[i] = scoreList.get(i).get("Name")+"";
                            imgid[i]= imgid2[webstores.indexOf(scoreList.get(i).get("Store"))];
                            links.add(scoreList.get(i).get("link").toString());
                            System.out.println("Product name test print----->"+scoreList.get(i).get("Name"));
                        }

                        ListViewItems adapter=new ListViewItems(searchResult.this,itemname, imgid,Prices);
                        list=(ListView)findViewById(R.id.list);
                        list.setAdapter(adapter);
                        registerForContextMenu(list);
                        System.out.println("reached here 123");


                    } else {
                        System.out.println("Error: " + e.getMessage());

                    }


                }

            });
 }catch(Exception e)
        {e.printStackTrace();}


    }


    @Override
//Context menu display krnay ka code.
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        // We know that each row in the adapter is a Map

        //
        // HashMap map =  (HashMap) list.getItemAtPosition(aInfo.position);

      //  menu.setHeaderTitle("Options for " + map.get("itemname"));
        System.out.println("something happened here i guess");

        menu.add(1, 1, 1, "Review");
        menu.add(1, 2, 2, "Go to link");
        menu.add(1, 3, 3, "View Similar Products");
    }

//Context Menu mein item clicked tou new activity khulnay ka code.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case 1:
                Intent myIntent = new Intent(getApplicationContext(), Review.class);
                ParseObject product = globalScoreList.get(index);
                String name =  (String)product.get("Name");
                String store = (String)product.get("Store");
                System.out.println("name : " + name + ", store : " + store);

                myIntent.putExtra("com.aaa.fyp.ProductName", name);
                myIntent.putExtra("com.aaa.fyp.ProductStore", store);
                startActivity(myIntent);
                break;
            case 2:
                String url = links.get(index); // You could have this at the top of the class as a constant, or pass it in as a method variable, if you wish to send to multiple websites
                Intent Browser = new Intent(Intent.ACTION_VIEW); // Create a new intent - stating you want to 'view something'
                Browser.setData(Uri.parse(url));  // Add the url data (allowing android to realise you want to open the browser)
                startActivity(Browser); // Go go go!
                break;
            case 3:
                Intent similarProductsActivity = new Intent(getApplicationContext(), similarProducts.class); // Create a new intent - stating you want to 'view something'
                similarProductsActivity.putExtra("productName",dbObject.pName);  // Add the url data (allowing android to realise you want to open the browser)
                startActivity(similarProductsActivity); // Go go go!
                break;
        }
        return super.onContextItemSelected(item);
    }

}




