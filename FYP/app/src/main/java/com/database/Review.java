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
import android.widget.TextView;
import android.widget.Toast;

import com.SlideMenu.BaseActivity;
import com.aaa.fyp.R;
import com.parse.ParseObject;

public class Review extends BaseActivity{

    String name;
    String store;


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
        EditText editEmail =(EditText)findViewById(R.id.editEmail);
        editEmail.setText(gmail);

        Bundle bundle = getIntent().getExtras();
        name = (String) bundle.get("com.aaa.fyp.ProductName");
        store = (String) bundle.get("com.aaa.fyp.ProductStore");
        System.out.println(name);
        System.out.println(store);


        TextView PName = (TextView) findViewById(R.id.Name);

        TextView PStore = (TextView) findViewById(R.id.Store);
        PName.setText(name);
        PStore.setText(store);



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