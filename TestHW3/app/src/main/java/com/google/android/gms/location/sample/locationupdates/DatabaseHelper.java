package com.google.android.gms.location.sample.locationupdates;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "test_locations";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "LATITUDE";
    public static final String COL_3 = "LONGITUDE";
    public static final String COL_4 = "ADDRESS";
    public static final String COL_5 = "DATE";
    public static final String COL_6 = "TIME";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, LATITUDE DOUBLE, LONGITUDE DOUBLE, ADDRESS LONGTEXT, DATE TEXT, TIME, TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }
    public boolean insertData(double latitude, double longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //put data into columns, takes 2 arguments: column name and value
        contentValues.put(COL_2, latitude);
        contentValues.put(COL_3, longitude);

        //takes 3 arguments: table name, null, contentValues
        long result = db.insert(TABLE_NAME, null, contentValues);


        //how to know if values are inserted to table or not
        //db.insert() will return -1 if not successfully inserted
        //otherwise, return row of newly inserted data

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
