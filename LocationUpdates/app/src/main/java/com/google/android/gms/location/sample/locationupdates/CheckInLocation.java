package com.google.android.gms.location.sample.locationupdates;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckInLocation extends AppCompatActivity {

    DatabaseHelper check_inLocation;
    String checkInLatitude;
    String checkInLongitude;
    String checkInAddress;

    EditText checkInName;
    TextView coordinates;
    TextView address;
    Button btnCheckIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_location);
        new Load().execute("my string parameter");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.65));

        checkInLatitude = getIntent().getStringExtra("CHECK_IN_LAT");
        checkInLongitude = getIntent().getStringExtra("CHECK_IN_LONG");
        checkInAddress = getIntent().getStringExtra("CHECK_IN_ADDRESS");

        coordinates = findViewById(R.id.coordinates);
        coordinates.setText(checkInLatitude + ", " + checkInLongitude);
        address = findViewById(R.id.address);
        address.setText(checkInAddress);

        checkInName = findViewById(R.id.checkin_location);

        check_inLocation = new DatabaseHelper(this);
        btnCheckIn = findViewById(R.id.checkin_db);
    }

    /**
     * onClick function: Add coordinates, address, date, and time to database
     * */
    public void addData(View view){

        if(checkInName.getText().toString().length() != 0) {
            check_inLocation.insertData(Double.valueOf(checkInLatitude), Double.valueOf(checkInLongitude), checkInAddress);
            Toast.makeText(this, checkInName.getText().toString() + ": checked into database!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "Please enter a name for the location to check in.", Toast.LENGTH_SHORT).show();
        }
    }

    class Load extends AsyncTask<String, String, String>{
        ProgressDialog progressDialog = new ProgressDialog(getBaseContext());
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        /**
         * TODO: Make a function that will parse through entries
         *
         * TODO: (a) Compare latitude, longitude coordinates
         *
         * TODO: (b) Return minimum difference between current location and lat, long
         *
         * TODO: (c) IF within 30m, set and lock EditText to existing Check-in Location name,
         *           ELSE set EditText edit property
         * */
        @Override
        protected String doInBackground(String... strings) {
            /*String myString = strings[0];

            for(int i = 0; i <= 100; i++){
                publishProgress("where we at boys: " + i);
            }
            return "This string is passed to onPostExecute";*/


            Cursor res = check_inLocation.getAllData();

            return "This string is passed to onPostExecute";

        }

        @Override
        protected void onPostExecute(String unused){
            super.onPostExecute(unused);
            //checkInName.setText(unused);
        }
    }
}
