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

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CheckInLocation extends AppCompatActivity {

    DatabaseHelper check_inLocation;
    String checkInLatitude;
    String checkInLongitude;
    String checkInAddress;

    EditText checkInName;
    TextView coordinates;
    TextView address;
    Button btnCheckIn;


    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
    String date = dateFormat.format(calendar.getTime());
    String time = timeFormat.format(calendar.getTime());

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

        //check_inLocation = new DatabaseHelper(this);
        btnCheckIn = findViewById(R.id.checkin_db);
    }

    /**
     * onClick function: Add coordinates, address, date, and time to database
     * */
    public void addData(View view){

        if(checkInName.getText().toString().length() != 0) {
            check_inLocation.insertData(checkInName.getText().toString(),Double.valueOf(checkInLatitude), Double.valueOf(checkInLongitude), checkInAddress, date, time);
            Toast.makeText(this, checkInName.getText().toString() + ": checked into database!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "Please enter a name for the location to check in.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method that checks current latitude, longitude coordinates against entries in Locations.db
     *  - Sets EditText to closest check-in
     *  - If a check-in does not exists within 30m, EditText remains the same
     * */
    class Load extends AsyncTask<String, String, String>{
        ProgressDialog progressDialog = new ProgressDialog(getBaseContext());
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... strings) {
            /*String myString = strings[0];

            for(int i = 0; i <= 100; i++){
                publishProgress("where we at boys: " + i);
            }
            return "This string is passed to onPostExecute";*/

            check_inLocation = new DatabaseHelper(getBaseContext());
            Cursor res = check_inLocation.getAllData();
            float thirtyMeters = (float) 30;
            float temp = thirtyMeters;

            String passOnPostExec = null;

            if(res.getCount() == 0){
                return "Nothing in the database";
            }

            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                Location checkInLocation = new Location("");
                checkInLocation.setLatitude(Double.valueOf(res.getString(2)));
                checkInLocation.setLongitude(Double.valueOf(res.getString(3)));

                Location currLocation = new Location("");
                currLocation.setLatitude(Double.valueOf(checkInLatitude));
                currLocation.setLongitude(Double.valueOf(checkInLongitude));

                float distanceInMeters = currLocation.distanceTo(checkInLocation);

                if(distanceInMeters < thirtyMeters){

                    if(temp != Math.min(temp, distanceInMeters)){
                        temp = Math.min(temp, distanceInMeters);
                        passOnPostExec = res.getString(1);
                    }
                    else{
                        temp = Math.min(temp, distanceInMeters);
                        passOnPostExec = res.getString(1);
                    }

                }
            }
            return passOnPostExec;
        }

        @Override
        protected void onPostExecute(String unused){
            super.onPostExecute(unused);
            if(unused != null) {
                checkInName.setText(unused);
            }
        }
    }
}
