package com.database;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.fyp.R;

public class CustomListViewsSarchByName extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] itemname;
    private final String[] Price,Store;
    private final Integer[] imgid;

    public CustomListViewsSarchByName(Activity context, String[] itemname, Integer[] imgid, String[] Prices,String[] Store) {
        super(context, R.layout.activity_list_view_searchbyname, itemname);
        this.context=context;
        this.itemname=itemname;
        this.Price = Prices;
        this.imgid=imgid;
        this.Store= Store;
    }
    @Override
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_view_searchbyname, null, true);

        TextView PriceTxt = (TextView) rowView.findViewById(R.id.Review);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView ProductNameTxt = (TextView) rowView.findViewById(R.id.pname);
        TextView StoreNameTxt = (TextView) rowView.findViewById(R.id.Store);

        PriceTxt.setText( Price[position]);
        imageView.setImageResource(imgid[position]);
        ProductNameTxt.setText(itemname[position]);
        StoreNameTxt.setText(Store[position]);
        return rowView;

    };

}

