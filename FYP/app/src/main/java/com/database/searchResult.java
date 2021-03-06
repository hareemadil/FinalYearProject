package com.database;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.SlideMenu.BaseActivity;
import com.aaa.fyp.R;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

public class searchResult extends BaseActivity {

    ListView list;
    final ArrayList<String> links  = new ArrayList<>();
    DB dbObject;
    ParseQuery<ParseObject> Products;
    List<ParseObject> globalScoreList;
    TextView textview;
    ProgressBar progresBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_list_activity_class);
        getLayoutInflater().inflate(R.layout.activity_list_activity_class, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle("Search result");
        dbObject=  new DB(this);
        //
        textview = (TextView) findViewById(R.id.waitingText);
        progresBar = (ProgressBar)findViewById(R.id.progressBar);
        list=(ListView)findViewById(R.id.list);

        textview.setVisibility(View.VISIBLE);
        progresBar.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);

        //Progress LoadingScreen = new Progress();
        //LoadingScreen.execute();
        //region SearchResult Population
        Intent myIntent = getIntent(); // gets the previously created intent
        String Barcode = myIntent.getStringExtra("Product");
        System.out.println("in list view ---- >" + Barcode);

        try{

            //to change query please refer to the function getProducts
            //region Prepare List from parse data
            Products = dbObject.getProducts(Barcode);

            Products.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    globalScoreList = scoreList;
                    if (e == null) {
                        if(scoreList.size()>=1) {  //if product was found

                            //region Arrays for Custom List View
                            String[] itemname = new String[scoreList.size()];
                            String[] Prices = new String[scoreList.size()];
                            //will have to work on how to make these logo dynamic
                            Integer[] imgid2 = {
                                    R.mipmap.qne_logo,//qne
                                    R.mipmap.gomart_logo,//gomart
                                    R.mipmap.aaramshop,
                                    R.mipmap.doorstep,
                                    R.mipmap.rashan_lelo,
                                    R.mipmap.shoprex_logo,
                                    R.mipmap.cartpk,
                                    R.mipmap.qne_logo,//qne
                                    R.mipmap.aaramshop,
                                    R.mipmap.upcs
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
                            webstores.add("AaramShop.pk");
                            webstores.add("upcdatabase");


                            Integer[] imgid = new Integer[scoreList.size()];
                            //endregion

                            //populates the list of products
                            for (int i = 0; i < scoreList.size(); i++) {

                                //System.out.println("Price" + "" + scoreList.get(i).get("Price"));
                                //results.add(scoreList.get(i).get("Name") + " : " + scoreList.get(i).get("Price")); itemname[i] = scoreList.get(i).get("Name")+"";
                                Prices[i] = scoreList.get(i).get("price") + "";
                                itemname[i] = scoreList.get(i).get("pname") + "";
                                try {
                                    imgid[i] = imgid2[webstores.indexOf(scoreList.get(i).get("store"))];
                                } catch (Exception ex) {
                                    System.out.println("error: i = " + i + " store = " + scoreList.get(i).get("store"));
                                    imgid[i] = imgid2[0];
                                }
                                links.add(scoreList.get(i).get("link1").toString());
                                System.out.println("Product name test print----->" + scoreList.get(i).get("pname"));
                            }

                            ListViewItems adapter = new ListViewItems(searchResult.this, itemname, imgid, Prices);

                            list.setAdapter(adapter);
                            registerForContextMenu(list);
                            dbObject.sqliteClose();
                            textview.setVisibility(View.GONE);
                            progresBar.setVisibility(View.GONE);
                            list.setVisibility(View.VISIBLE);
                        }else{ //if nothing was found

                            textview.setText("NOTHING FOUND");
                            progresBar.setVisibility(View.GONE);
                        }
                    } else {
                        System.out.println("Error: " + e.getMessage());

                    }


                }

            });

            //endregion

        }catch(Exception e){e.printStackTrace();}

        //endregion


    }

    @Override
//Context menu display
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 1, 1, "Review");
        menu.add(1, 2, 2, "Go to link");
        menu.add(1, 3, 3, "View Similar Products");
    }

    //Context Menu mein item clicked
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case 1:
                Intent myIntent = new Intent(getApplicationContext(), Review.class);
                ParseObject product = globalScoreList.get(index);
                String name =  (String)product.get("pname");
                String store = (String)product.get("store");
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
                ParseObject productName = globalScoreList.get(index);
                String ProductName =  (String)productName.get("pname");
                Intent similarProductsActivity = new Intent(getApplicationContext(), similarProducts.class); // Create a new intent - stating you want to 'view something'
                similarProductsActivity.putExtra("productName",ProductName);  // Add the url data (allowing android to realise you want to open the browser)
                startActivity(similarProductsActivity); // Go go go!
                break;
        }
        return super.onContextItemSelected(item);
    }




}




