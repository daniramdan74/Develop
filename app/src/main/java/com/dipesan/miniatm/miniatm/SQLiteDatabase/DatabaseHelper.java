package com.dipesan.miniatm.miniatm.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dani Ramdan on 04/11/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    public DatabaseHelper(Context context){
        super(context, "miniAtm.db",null,1);
        Log.d(LOGCAT,"CREATED");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //table purchase phonecredit
        String query="CREATE TABLE purchase_phonecredit(phonecreditId INTEGER PRIMARY KEY," +
                "providerName TEXT, nominal TEXT, telephoneNumber TEXT)";
        sqLiteDatabase.execSQL(query);
        Log.d(LOGCAT,"purchase_phonecredit CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int currentVersion) {
        String query;
        query = "DROP TABLE IF EXISTS purchase_phonecredit";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
        Log.d(LOGCAT,"DROP TABLE EXISTS purchase_phonecredit");
    }
    public void addPhoneCredit(HashMap<String, String >queryValues){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("providerName",queryValues.get("providerName"));
        values.put("nominal",queryValues.get("nominal"));
        values.put("telephoneNumber",queryValues.get("telephoneNumber"));
        sqLiteDatabase.insert("purchase_phonecredit",null,values);
        sqLiteDatabase.close();
    }

    public ArrayList<HashMap<String, String >>getAllPurchasePhoneCredit(){
        ArrayList<HashMap<String,String>>PurchasePhoneCreditList;
        PurchasePhoneCreditList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM purchase_phonecredit";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("phonecreditId",cursor.getString(0));
                map.put("providerName", cursor.getString(1));
                map.put("nominal",cursor.getString(2));
                map.put("telephoneNumber",cursor.getString(3));
                PurchasePhoneCreditList.add(map);
            }while (cursor.moveToNext());

        }
        return PurchasePhoneCreditList;
    }

}
