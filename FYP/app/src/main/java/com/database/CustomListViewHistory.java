package com.database;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.aaa.fyp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomListViewHistory extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] itemname;
    private final String[] Pname;

    public CustomListViewHistory(Activity context, String[] itemname, String[] Pname) {
        super(context, R.layout.activity_list_view_history, itemname);
        this.context=context;
        this.itemname=itemname;
        this.Pname = Pname;
    }
    @Override
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_view_history, null, true);

        TextView PriceTxt = (TextView) rowView.findViewById(R.id.itemHistory);
      //  ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView ProductNameTxt = (TextView) rowView.findViewById(R.id.textView1History);
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        TextView date = (TextView) rowView.findViewById(R.id.Store);
        PriceTxt.setText("Name: "+ Pname[position]);
        ProductNameTxt.setText(itemname[position]);
        date.setText(formattedDate);
        return rowView;

    };

}

