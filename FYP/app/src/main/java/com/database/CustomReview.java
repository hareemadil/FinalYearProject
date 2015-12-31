package com.database;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aaa.fyp.R;

public class CustomReview extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] itemname;
    private final String[] Pname;


    public CustomReview(Activity context, String[] itemname, String[] Pname) {
        super(context, R.layout.activity_list_view_review, itemname);
        this.context=context;
        this.itemname=itemname;
        this.Pname = Pname;
    }
    @Override
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_view_review, null, true);

        TextView PriceTxt = (TextView) rowView.findViewById(R.id.itemHistory);
      //  ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView ProductNameTxt = (TextView) rowView.findViewById(R.id.textView1History);

        PriceTxt.setText("Name: "+ Pname[position]);
        ProductNameTxt.setText(itemname[position]);
        return rowView;

    };

}

