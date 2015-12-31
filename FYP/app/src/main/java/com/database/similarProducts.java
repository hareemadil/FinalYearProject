package com.database;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.SlideMenu.BaseActivity;
import com.aaa.fyp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class similarProducts extends BaseActivity {

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
        setTitle("Similar Products");
        textview = (TextView) findViewById(R.id.waitingText);
        progresBar = (ProgressBar)findViewById(R.id.progressBar);
        list=(ListView)findViewById(R.id.list);

        textview.setVisibility(View.VISIBLE);
        progresBar.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        Intent myIntent = getIntent(); // gets the previously created intent
        String productName = myIntent.getStringExtra("productName");

        try{
            dbObject=  new DB(this);
            //to change query please refer to the function getProducts
            Products = dbObject.getSimilar(productName);

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
                                R.mipmap.qne_logo,//qne
                                R.mipmap.aaramshop
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


                        Integer[] imgid= new Integer[scoreList.size()];

                        //populates the list of products
                        for (int i = 0; i < scoreList.size(); i++) {

                            System.out.println("Name" + "" + scoreList.get(i).get("pname"));
                            //results.add(scoreList.get(i).get("Name") + " : " + scoreList.get(i).get("Price")); itemname[i] = scoreList.get(i).get("Name")+"";
                            Prices[i] = scoreList.get(i).get("price")+"";
                            itemname[i] = scoreList.get(i).get("pname")+"";
                            imgid[i]= imgid2[webstores.indexOf(scoreList.get(i).get("store"))];
                            links.add(scoreList.get(i).get("link1").toString());

                        }

                        ListViewItems adapter=new ListViewItems(similarProducts.this,itemname, imgid,Prices);
                        list=(ListView)findViewById(R.id.list);
                        list.setAdapter(adapter);
                        registerForContextMenu(list);
                        textview.setVisibility(View.GONE);
                        progresBar.setVisibility(View.GONE);
                        list.setVisibility(View.VISIBLE);


                        list.setOnItemClickListener(new OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                // When clicked, show a toast with the TextView text Game, Help, Home
                               // Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                                //        Toast.LENGTH_SHORT).show();
                            //    String sText = ((TextView) view).getText().toString();
                                Intent intent = null;

                                    intent = new Intent(getBaseContext(),            Review.class);


                                if (intent != null)
                                    startActivity(intent);
                            }
                        });

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

        menu.add(1, 1, 1, "Review");
        menu.add(1, 2, 2, "Go to link");
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
        }
        return super.onContextItemSelected(item);
    }

}




