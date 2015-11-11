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
import com.aaa.barcode.MainActivity;
import com.aaa.barcode.R;
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
            Products = dbObject.pullData(Barcode);
            Products.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {

                    if (e == null) {
                        // ArrayList<String> results = new ArrayList<>();
                        String[] itemname = new String[2];

                        Integer[] imgid={
                                R.mipmap.qne_logo,
                                R.mipmap.gomart_logo
                        };
                        for (int i = 0; i < scoreList.size(); i++) {
                            //System.out.println("Price" + "" + scoreList.get(i).get("Price"));
                            //results.add(scoreList.get(i).get("Name") + " : " + scoreList.get(i).get("Price"));
                            itemname[0] = scoreList.get(i).get("Name")+" "+scoreList.get(i).get("Price");
                            itemname[1] = scoreList.get(i).get("Name")+" "+scoreList.get(i).get("Price");
                            System.out.println("------------------------>"+scoreList.get(i).get("Name"));
                        }

                        CustomListView adapter=new CustomListView(ListActivityClass.this,itemname, imgid);
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
