package com.google.android.gms.location.sample.locationupdates;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMapLocation extends AppCompatActivity {

    DatabaseHelper addMapLocation;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map_location);

        addMapLocation = new DatabaseHelper(this);


        String mapLatitude = getIntent().getStringExtra("ADD_LOCATION_LAT");
        String mapLongitude = getIntent().getStringExtra("ADD_LOCATION_LONG");
        tv = findViewById(R.id.mapLocation);
        tv.setText(mapLatitude + ", " + mapLongitude);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.65));
    }



}
