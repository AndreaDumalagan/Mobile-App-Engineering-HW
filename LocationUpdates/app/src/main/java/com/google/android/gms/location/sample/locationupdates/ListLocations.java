package com.google.android.gms.location.sample.locationupdates;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListLocations extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<Location> checkinLocations;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_locations);

        ListView mListView = findViewById(R.id.locationListView);

        db = new DatabaseHelper(this);
        checkinLocations = new ArrayList<>();
        res = db.getAllData();

        if(res.getCount() == 0){
            //Show message
            showMessage("Error", "No Check-in Locations Found.");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            checkinLocations.add(new Location(res.getString(1), Double.valueOf(res.getString(2)), Double.valueOf(res.getString(3)), res.getString(4), res.getString(5), res.getString(6)));
        }

        LocationListAdapter adapter = new LocationListAdapter(this, R.layout.adapter_view_layout, checkinLocations);
        mListView.setAdapter(adapter);

    }
    private void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
