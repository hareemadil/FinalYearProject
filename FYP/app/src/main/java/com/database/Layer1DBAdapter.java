package com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Layer1DBAdapter {
    private static final String DATABASE_TABLE = "product_barcode";
    public static final String Barcode = "barcode";
    public static final String ProductName = "PName";
    public static final String Weight = "weight";
    public static final String KEY_Pass = "pass";
    public static final String KEY_fName = "fname";
    public static final String KEY_lName = "lname";
    public static final String KEY_accDetils = "ad";
    public static final String KEY_Comments = "com";


    SQLiteDatabase mDb;
    Context mCtx;
    DBHelper mDbHelper;

    public Layer1DBAdapter(Context context) {
        this.mCtx = context;
    }

    public Layer1DBAdapter open() throws SQLException {
        mDbHelper = new DBHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createTask(String barcode, String pName,String weight){//, String fname, String lname, String ad, String com) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Barcode, barcode);
        initialValues.put(ProductName, pName);
        initialValues.put(Weight, weight);
        //  initialValues.put(KEY_fName, fname);
        // initialValues.put(KEY_lName, lname);
        // initialValues.put(KEY_accDetils, ad);
        // initialValues.put(KEY_Comments, com);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteProduct(long id) {
        return mDb.delete(DATABASE_TABLE, Barcode + " = " + id, null) > 0;
    }

    public boolean updateProduct( String barcode, String pName,String weight){//, String fname, String lname, String ad, String com) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Barcode, barcode);
        initialValues.put(ProductName, pName);
        initialValues.put(Weight, weight);
        return mDb.update(DATABASE_TABLE, initialValues, Barcode + " = " + ProductName, null) > 0;
    }

    public Cursor fetchBarcode() {
        return mDb.query(DATABASE_TABLE, new String[]{ProductName}, null, null, null, null, null);
    }

    public Cursor fetchPass() {
        return mDb.query(DATABASE_TABLE, new String[]{KEY_Pass}, null, null, null, null, null);
    }

    public Cursor fetchAllProducts() {
        return mDb.query(DATABASE_TABLE, new String[]{Barcode, ProductName,Weight}, null, null, null, null, null);
    }

    public Cursor getItemByBarcode(String barcode) {
        String[] columns = new String[]{Barcode};

        Cursor cursor = mDb.query(DATABASE_TABLE, columns, Barcode + " = '" + barcode + "'", null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getProductName(String barcode) {
        String[] columns = new String[]{ProductName,Weight};

        Cursor cursor = mDb.query(DATABASE_TABLE, columns, Barcode + " = '" + barcode + "'", null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor fetchUser(long id) {
        Cursor c = mDb.query(DATABASE_TABLE, new String[]{Barcode, ProductName, KEY_Pass}, Barcode + " = " + id, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor GetCount() {
        //String[] columns = new String[]{" count(*) "};

        Cursor cursor = mDb.query(DATABASE_TABLE,null , null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
    public String getData() {
        // TOD.O Auto-generated method stub
        String[] columns = new String[]{Barcode, ProductName, Weight, KEY_fName, KEY_lName, KEY_accDetils, KEY_Comments};
        Cursor c = mDb.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(Barcode);
        int iName = c.getColumnIndex(ProductName);
        int iPAss = c.getColumnIndex(KEY_Pass);
        int ifname = c.getColumnIndex(KEY_fName);
        int ilname = c.getColumnIndex(KEY_lName);
        int iaccD = c.getColumnIndex(KEY_accDetils);
        int icom = c.getColumnIndex(KEY_Comments);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(iRow) + " . " + c.getString(iName) + " ... Pass : " + c.getString(iPAss) + "\n Name : " + c.getString(ifname) + " " + c.getString(ilname)
                    + " \n Account Details : " + c.getString(iaccD) + " \n Comments : " + c.getString(icom) + "\n--------------\n";
        }

        return result;
    }

}
