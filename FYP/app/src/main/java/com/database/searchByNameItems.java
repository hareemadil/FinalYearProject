package com.database;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.fyp.R;

public class searchByNameItems extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] itemname;
    private final String[] Prices;


    public searchByNameItems(Activity context, String[] itemname,  String[] Prices) {
        super(context, R.layout.activity_list_view, itemname);
        this.context=context;
        this.itemname=itemname;

        this.Prices = Prices;
    }
    @Override
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_view, null, true);

        TextView PriceTxt = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView ProductNameTxt = (TextView) rowView.findViewById(R.id.textView1);

        PriceTxt.setText("Rs. "+Prices[position]);

        ProductNameTxt.setText(itemname[position]);
        return rowView;

    };
}