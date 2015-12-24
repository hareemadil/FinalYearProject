package com.database;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.aaa.fyp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ListActivityClassHistory extends Activity {

    ListView list;
    final ArrayList<String> links  = new ArrayList<>();
    historyDB HistoryDbObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity_class_history);

        try{
            HistoryDbObject=  new historyDB(this);
            //to change query please refer to the function getProducts
            Cursor history = HistoryDbObject.getHistory();
            String[] itemname = new String[history.getCount()],
                     Pname= new String[history.getCount()];
            history.moveToFirst();
            for (int i=0;i< history.getCount();i++){

                 itemname[i]=history.getString(0);
                 Pname[i] = history.getString(1);
                 history.moveToNext();
            }
            CustomListViewHistory adapter=new CustomListViewHistory(ListActivityClassHistory.this,itemname,Pname);
            list=(ListView)findViewById(R.id.listHistory);
            list.setAdapter(adapter);
            //registerForContextMenu(list);

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
                Intent i = new Intent(ListActivityClassHistory.this, Review.class);
                AdapterView.AdapterContextMenuInfo infoReview = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int indexReview = infoReview.position;
                System.out.println("Review"+links.get(indexReview));

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




