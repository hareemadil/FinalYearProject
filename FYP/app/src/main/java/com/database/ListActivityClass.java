package com.database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.aaa.fyp.SimpleScannerActivity;
import com.aaa.fyp.R;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListActivityClass extends Activity {

    ListView list;
    String[] itemname ={
            "Aquafina",
            "Milo"
    };
    ArrayList<String> item2 = new ArrayList<>();
    Integer[] imgid={
            R.mipmap.qne_logo,
            R.mipmap.gomart_logo
    };
    DB dbObject;
    ParseQuery<ParseObject> Products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity_class);

        Intent myIntent = getIntent(); // gets the previously created intent
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
                                R.mipmap.cartpk
                        };
                        ArrayList<String> webstores = new ArrayList<String>();
                        webstores.add("qne.com.pk");
                        webstores.add("gomart.com");
                        webstores.add("aaramshop.pk");
                        webstores.add("doorstep.pk");
                        webstores.add("rashanlelo.pk");
                        webstores.add("shoprex.com");
                        webstores.add("cartpk.com");


                        Integer[] imgid= new Integer[scoreList.size()];

                        //populates the list of products
                        for (int i = 0; i < scoreList.size(); i++) {

                            //System.out.println("Price" + "" + scoreList.get(i).get("Price"));
                            //results.add(scoreList.get(i).get("Name") + " : " + scoreList.get(i).get("Price"));
                            itemname[i] = scoreList.get(i).get("Name")+"";
                            Prices[i] = scoreList.get(i).get("Price")+"";

                            imgid[i]= imgid2[webstores.indexOf(scoreList.get(i).get("Store"))];
                            System.out.println("Product name test print----->"+scoreList.get(i).get("Name"));
                        }

                        CustomListView adapter=new CustomListView(ListActivityClass.this,itemname, imgid,Prices);
                        list=(ListView)findViewById(R.id.list);
                        list.setAdapter(adapter);
                        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                // TODO Auto-generated method stub
                                String Slecteditem = itemname[+position];
                                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

                            }
                        });*/

                    } else {
                        System.out.println("Error: " + e.getMessage());

                    }
                }
            });
            System.out.println("----------------------------------------------------------------------");
        }catch(Exception e)
        {e.printStackTrace();}


    }
}
