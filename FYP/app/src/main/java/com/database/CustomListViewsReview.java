package com.database;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aaa.fyp.R;

public class CustomListViewsReview extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] itemname;
    private final String[] reviews,Store;
    public CustomListViewsReview(Activity context, String[] itemname,  String[] Reviews, String[] Store) {
        super(context, R.layout.activity_list_view_reviews, itemname);
        this.context=context;
        this.itemname=itemname;
        this.reviews = Reviews;
        this.Store= Store;
    }
    @Override
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_view_reviews, null, true);

        TextView Reviews = (TextView) rowView.findViewById(R.id.Review);

        TextView ProductNameTxt = (TextView) rowView.findViewById(R.id.pname);
        TextView StoreNameTxt = (TextView) rowView.findViewById(R.id.Store);

        Reviews.setText("REVIEW: "+reviews[position]);

        ProductNameTxt.setText("PRODUCT: "+itemname[position]);
        StoreNameTxt.setText("STORE: "+Store[position]);
        return rowView;

    };

}

