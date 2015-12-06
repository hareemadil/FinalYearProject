package com.database;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.aaa.fyp.SimpleScannerActivity;
import com.aaa.fyp.R;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivityClass extends Activity {

    ListView list;
    final ArrayList<String> links  = new ArrayList<>();
    DB dbObject;
    ParseQuery<ParseObject> Products;
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
            //to change query please refer to the function pullData
            Products = dbObject.pullData(Barcode);
            Products.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {

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

                            imgid[i]= imgid2[webstores.indexOf(scoreList.get(i).get("Store"))];
                            links.add(scoreList.get(i).get("link").toString());
                            System.out.println("Product name test print----->"+scoreList.get(i).get("Name"));
                        }

                        CustomListView adapter=new CustomListView(ListActivityClass.this,itemname, imgid,Prices);
                        list=(ListView)findViewById(R.id.list);
                        list.setAdapter(adapter);
                        registerForContextMenu(list);




                       list.setOnItemClickListener(new OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                // When clicked, show a toast with the TextView text Game, Help, Home
                               // Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                                //        Toast.LENGTH_SHORT).show();
                            //    String sText = ((TextView) view).getText().toString();
                                Intent intent = null;
                            //   if (sText.contains("Review"))
                                    intent = new Intent(getBaseContext(),
                                            Review.class);

                                //else if(sText.equals("Help")) ..........

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
        switch (item.getItemId()) {
            case 1:
                //first ContextMenu option I picked this to start the  new activity
                Intent i = new Intent(ListActivityClass.this, Review.class);
              //  i.putExtra("str", longdesc);
                startActivity(i);
                break;
            case 2:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int index = info.position;
                System.out.println("+++test++++++++++++++++++whatttt***********)_((_(()(@@@"+links.get(index));
                String url = links.get(index); // You could have this at the top of the class as a constant, or pass it in as a method variable, if you wish to send to multiple websites
                Intent Browser = new Intent(Intent.ACTION_VIEW); // Create a new intent - stating you want to 'view something'
                Browser.setData(Uri.parse(url));  // Add the url data (allowing android to realise you want to open the browser)
                startActivity(Browser); // Go go go!
                break;
        }
        return super.onContextItemSelected(item);
    }

}




