package com.database;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.SlideMenu.BaseActivity;
import com.aaa.fyp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class searchByName extends BaseActivity implements AdapterView.OnItemSelectedListener {

    //region Custom Variables
    private Spinner Brandspinner;
    private Spinner Productspinner;
    ListView list;
    List<ParseObject> globalScoreList;
    final ArrayList<String> mobileName = new ArrayList<>();
    final ArrayList<String> links  = new ArrayList<>();
    private static final String[] BrandsArray = {
            "Brand Name",
            "Acer",
            "Alcatel",
            "Apple",
            "Asus",
            "BlackBerry",
            "Calme",
            "Club",
            "DANY",
            "Dell",
            "Galaxy",
            "GFive",
            "Gigabyte",
            "GRight",
            "Haier",
            "HP",
            "HTC",
            "Huawei",
            "iPad",
            "Lenovo",
            "LG",
            "Megagate",
            "Micromax",
            "Microsoft",
            "Motorola",
            "Nokia",
            "OPhone",
            "Oppo",
            "Optimus",
            "Panasonic",
            "QMobile",
            "Rivo",
            "Samsung",
            "Sony",
            "Spice",
            "Toshiba",
            "Vodafone",
            "Voice",
            "Xiaomi"
            };

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.search_by_name);

        getLayoutInflater().inflate(R.layout.search_by_name, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        //region Variable Initialization
        mobileName.add("Mobile Name");
        Brandspinner = (Spinner)findViewById(R.id.brand);
        Productspinner = (Spinner)findViewById(R.id.product);
        list=(ListView)findViewById(R.id.listSearchByName);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(searchByName.this,
                android.R.layout.simple_spinner_item, BrandsArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Brandspinner.setAdapter(adapter);
        Brandspinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mobileName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        Productspinner.setAdapter(dataAdapter);
        Productspinner.setOnItemSelectedListener(new productListListner());
        //endregion

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                if (position ==0) return;


                ParseQuery<ParseObject> query = ParseQuery.getQuery("MobileProd");
                query.whereMatches("brand", "(" + BrandsArray[position] + ")", "i");
                getProducts(query);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, mobileName);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter.notifyDataSetChanged();
                Productspinner.setAdapter(dataAdapter);
        }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    private ArrayList<String> getProducts(ParseQuery<ParseObject> query) {


        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                globalScoreList = scoreList;
                if (e == null) {

                    mobileName.clear();
                    mobileName.add("Mobile Name");
                    for (int i = 0; i < scoreList.size(); i++) {

                        //System.out.println("Price" + "" + scoreList.get(i).get("Price"));
                        //results.add(scoreList.get(i).get("Name") + " : " + scoreList.get(i).get("Price")); itemname[i] = scoreList.get(i).get("Name")+"";
                        mobileName.add(scoreList.get(i).get("pname")+"");

                    }

                } else {
                    System.out.println("Error: " + e.getMessage());

                }


            }

        });
        return mobileName;

    }


    public class productListListner extends Activity implements
            AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            if (pos==0) {return;}
            ParseQuery<ParseObject> query = ParseQuery.getQuery("MobileProd");
            query.whereMatches("pname", "(" + mobileName.get(pos) + ")", "i");

            //region Create List from Parse data
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    globalScoreList = scoreList;
                    if (e == null) {
                        //region Arrays declaration for List View
                        String[] itemname = new String[scoreList.size()];
                        String[] Prices = new String[scoreList.size()];
                        Integer[] imgid= new Integer[scoreList.size()];
                        String[] Store = new String[scoreList.size()];
                        links.clear();
                        Integer[] imgid2={
                                R.mipmap.shoprex_logo,
                                R.mipmap.shoprex_logo,
                                R.mipmap.pakmobile,
                                R.mipmap.shoprex_logo
                        };
                        ArrayList<String> webstores = new ArrayList<String>();
                        webstores.add("WhatMobile");
                        webstores.add("shoprex.com");
                        webstores.add("pakmobileprice.com");
                        webstores.add("mobilephone.pk");
                        //endregion
                        //region store data from parse in Arrays
                        for (int i = 0; i < scoreList.size(); i++) {
                            System.out.println(scoreList.get(i).get("pname") + " /// "
                                    + scoreList.get(i).get("price")+ " ///"
                                    +scoreList.get(i).get("store") + " /// ");

                            Prices[i] = scoreList.get(i).get("price")+"";
                            itemname[i] = scoreList.get(i).get("pname")+"";
                            imgid[i]= imgid2[webstores.indexOf(scoreList.get(i).get("store"))];
                            Store[i] = scoreList.get(i).get("store")+"";
                            links.add(scoreList.get(i).get("link").toString());

                        }
                        //endregion
                        //region Create listView Adapter using custom arrays
                        CustomListViewsSarchByName adapter=new CustomListViewsSarchByName(searchByName.this,itemname, imgid,Prices,Store);
                        list.setAdapter(adapter);
                        //endregion

                        registerForContextMenu(list);

                    } else {
                        System.out.println("Error: " + e.getMessage());
                    }
                 }

            });
            //endregion

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }


        @Override
        //Context menu display.
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);

            menu.add(1, 1, 1, "Review");
            menu.add(1, 2, 2, "Go to link");
            System.out.println("somethting");

        }

        //Context Menu mein item clicked
        @Override
        public boolean onContextItemSelected(MenuItem item) {
            System.out.println("item id ++"+item.getItemId());
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            System.out.println("item id ++"+item.getItemId());
            switch (item.getItemId()) {
                case 1:

                    break;
                case 2:
                    System.out.println("wowwwwwww success bht epic");
                    String url = links.get(index); // You could have this at the top of the class as a constant, or pass it in as a method variable, if you wish to send to multiple websites
                    Intent Browser = new Intent(Intent.ACTION_VIEW); // Create a new intent - stating you want to 'view something'
                    Browser.setData(Uri.parse(url));  // Add the url data (allowing android to realise you want to open the browser)
                    startActivity(Browser); // Go go go!
                    break;
            }
            return super.onContextItemSelected(item);
        }

    }


}
