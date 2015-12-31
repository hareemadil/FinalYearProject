package com.database;

/**
 * Created by Qureshis on 11/21/2015.
 */



import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.SlideMenu.BaseActivity;
import com.aaa.fyp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Review extends BaseActivity{

    String name;
    String store;
    ListView listUI;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.reviewlayout);

        getLayoutInflater().inflate(R.layout.reviewlayout, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();
        String gmail = null;

        for(Account account: list)
        {
            if(account.type.equalsIgnoreCase("com.google"))
            {
                gmail = account.name;
                break;
            }
        }

        listUI=(ListView)findViewById(R.id.listSearchByName);
        EditText editEmail =(EditText)findViewById(R.id.editEmail);
        editEmail.setText(gmail);

        Bundle bundle = getIntent().getExtras();
        name = (String) bundle.get("com.aaa.fyp.ProductName");
        store = (String) bundle.get("com.aaa.fyp.ProductStore");
        System.out.println(name);
        System.out.println(store);


        TextView PName = (TextView) findViewById(R.id.Name);

        TextView PStore = (TextView) findViewById(R.id.Store2);
        PName.setText(name);
        PStore.setText(store);


        try{
            DB dbObject = new DB(this);
            //to change query please refer to the function getProducts
            ParseQuery<ParseObject> Products = dbObject.getReviews(name, store);

            Products.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    List<ParseObject> globalScoreList = scoreList;
                    if (e == null) {

                        if(scoreList.size() >= 1 &&  scoreList.size() < 100 ) {
                            String[] itemname = new String[scoreList.size()];
                            String[] Store = new String[scoreList.size()];
                            String[] reviews = new String[scoreList.size()];


                            //populates the list of products
                            for (int i = 0; i < scoreList.size(); i++) {

                                 Store[i] = scoreList.get(i).get("store") + "";
                                 itemname[i] = scoreList.get(i).get("Name") + "";
                                 reviews[i] = scoreList.get(i).get("Review") + "";


                            }

                            CustomListViewsReview adapter = new CustomListViewsReview(Review.this, itemname,reviews, Store);
                            listUI=(ListView)findViewById(R.id.listSearchByName);
                            listUI.setAdapter(adapter);


                        }
                        else{
                            Toast.makeText(Review.this, "No Reviews found", Toast.LENGTH_LONG).show();
                            System.out.println("Reviews not found!!");

                        }
                    } else {
                        System.out.println("Error: " + e.getMessage());

                    }


                }

            });
        }catch(Exception e)
        {e.printStackTrace();}


    }





    public void ReviewSubmit(View v)
    {
        ParseObject Review = new ParseObject("Review");

        EditText editEmail =(EditText)findViewById(R.id.editEmail);
        EditText editReview =(EditText)findViewById(R.id.editReview);

        String review= editReview.getText().toString();
        String email=  editEmail.getText().toString();

        Review.put("Email", email);
        Review.put("Review", review);
        Review.put("Name", name);
        Review.put("store", store);
        Review.saveInBackground();

        Toast.makeText(this, "Review Submitted!", Toast.LENGTH_LONG).show();
        // Toast.makeText(this, review, Toast.LENGTH_LONG).show();
        finish();
    }

}