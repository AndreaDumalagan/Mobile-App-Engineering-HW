package com.google.android.gms.location.sample.locationupdates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Locations.db";
    public static final String CHECKIN_LOCATIONS = "checkin_locations";
    public static final String MAP_LOCATIONS = "map_locations";
    public static final String DAILYPATH_LOCATIONS = "dailypath_locations";
    public static final String checkinName = "NAME";
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
        db.execSQL("create table " + CHECKIN_LOCATIONS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME LONGTEXT,LATITUDE DOUBLE, LONGITUDE DOUBLE, ADDRESS LONGTEXT, DATE TEXT, TIME TEXT)");
        db.execSQL("create table " + MAP_LOCATIONS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, LATITUDE DOUBLE, LONGITUDE DOUBLE, DATE TEXT, TIME TEXT)");
        db.execSQL("create table " + DAILYPATH_LOCATIONS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, LATITUDE DOUBLE, LONGITUDE DOUBLE, ADDRESS LONGTEXT, DATE TEXT, TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CHECKIN_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + MAP_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + DAILYPATH_LOCATIONS);

        onCreate(db);
    }

    public boolean insertData(String checkinLocation, double latitude, double longitude, String address, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //put data into columns, takes 2 arguments: column name and value
        contentValues.put(checkinName, checkinLocation);
        contentValues.put(COL_2, latitude);
        contentValues.put(COL_3, longitude);
        contentValues.put(COL_4, address);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, time);

        //takes 3 arguments: table name, null, contentValues
        long result = db.insert(CHECKIN_LOCATIONS, null, contentValues);

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

    public boolean insertDailyPath(double latitude, double longitude, String address,String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, latitude);
        contentValues.put(COL_3, longitude);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, time);

        long result = db.insert(DAILYPATH_LOCATIONS, null, contentValues);

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

    public boolean insertMapLocation(double latitude, double longitude, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, latitude);
        contentValues.put(COL_3, longitude);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, time);

        long result = db.insert(MAP_LOCATIONS, null, contentValues);

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

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + CHECKIN_LOCATIONS, null);
        return res;
    }

    public Cursor getMapLocationData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MAP_LOCATIONS, null);
        return res;
    }


}
