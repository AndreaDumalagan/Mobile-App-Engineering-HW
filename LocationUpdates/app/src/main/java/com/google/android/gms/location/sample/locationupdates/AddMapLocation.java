package com.google.android.gms.location.sample.locationupdates;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMapLocation extends AppCompatActivity {

    DatabaseHelper addMapLocation;
    TextView tv;
    Button btnAddMapLocation;
    String mLat;
    String mLong;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map_location);

        addMapLocation = new DatabaseHelper(this);

        String mapLatitude = getIntent().getStringExtra("ADD_LOCATION_LAT");
        String mapLongitude = getIntent().getStringExtra("ADD_LOCATION_LONG");
        mLat = mapLatitude;
        mLong = mapLongitude;
        tv = findViewById(R.id.mapLocation);
        tv.setText(mapLatitude + ", " + mapLongitude);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.5));

        btnAddMapLocation = findViewById(R.id.button5);
        et = findViewById(R.id.textView2);

    }

    public void addData(View view){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());


        if(et.getText().toString().length() != 0) {
            addMapLocation.insertMapLocation(Double.valueOf(mLat), Double.valueOf(mLong), date, time);
            Toast.makeText(this, et.getText().toString() + ": checked into database!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "Please enter a name for the location to check in.", Toast.LENGTH_SHORT).show();
        }
    }
}
