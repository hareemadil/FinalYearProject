package com.database;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.SlideMenu.BaseActivity;
import com.aaa.fyp.MainActivity;
import com.aaa.fyp.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asif on 12/24/2015.
 */
public class searchByName extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private Spinner Brandspinner;
    private Spinner Productspinner;
    DB dbObject;
    ParseQuery<ParseObject> brand;
    List<ParseObject> globalScoreList;
    private static final String[]paths = {
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.search_by_name);

        getLayoutInflater().inflate(R.layout.search_by_name, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        Brandspinner = (Spinner)findViewById(R.id.brand);
        Productspinner = (Spinner)findViewById(R.id.product);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(searchByName.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Brandspinner.setAdapter(adapter);
        Brandspinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                List<String> list = new ArrayList<String>();

                brand=ParseQuery.getQuery("MobileProd");
                brand.whereMatches("brand","("+"Acer"+")","i");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter.notifyDataSetChanged();
                Productspinner.setAdapter(dataAdapter);
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

            // and so on. case for all the values upar walay dropdown main jo hain
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
