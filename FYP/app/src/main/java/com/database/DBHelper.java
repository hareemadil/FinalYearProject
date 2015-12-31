package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Products";
    private static final int DATABASE_VERSION = 2;
    private static final String Table1 = "CREATE TABLE product_barcode (barcode text primary key,PName text not null,weight text null);";//,fname text,lname text,ad text,com text);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

        db.execSQL("DROP TABLE IF EXISTS product_barcode");
        onCreate(db);

    }

}
