package com.google.android.gms.location.sample.locationupdates;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private DatabaseHelper db;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //TEST:
    Location deviceLocation = new Location("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new DatabaseHelper(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        //Add markers from check-in table
        checkInMarkers();
        //Get user device location and see it on Google Maps
        getDeviceLocation();
        //Provides simple way to display a device's location on the map--does not provide data
        mMap.setMyLocationEnabled(true);
        //Zoom in on user device location
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        /**
         * On short click in Google Maps, add a marker
         * On long click on existing marker, allows user to drag marker
         * TODO: Pass latitude, longitude coordinates to Locations.db -> maps_locations
         * */
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(latLng)
                        .title(latLng.latitude + " " + latLng.longitude)
                        .snippet("Your marker snippet"))
                        .setDraggable(true);
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng newTitle = marker.getPosition();
                marker.setTitle(newTitle.latitude + " " + newTitle.longitude);
                marker.setDraggable(false);
                Intent intent = new Intent(getBaseContext(), AddMapLocation.class);
                intent.putExtra("ADD_LOCATION_LAT", Double.toString(newTitle.latitude));
                intent.putExtra("ADD_LOCATION_LONG", Double.toString(newTitle.longitude));
                startActivity(intent);

                //startActivity(new Intent(MapsActivity.this, AddMapLocation.class));
            }
        });
    }

    /**
     * Get device location on Google Maps
     * */
    private void getDeviceLocation(){

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        final Task location = mFusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Location currentLocation = (Location) task.getResult();

                    LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

                    /*TEST:*/
                    deviceLocation.setLatitude(userLocation.latitude);
                    deviceLocation.setLongitude(userLocation.longitude);
                    new Load().execute("help");



                    //Toast.makeText(MapsActivity.this, deviceLocation.getLatitude() + ", " + deviceLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MapsActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkInMarkers(){
        Cursor res = db.getAllData();

        if(res.getCount() == 0){
            //Show message
            showMessage("Error", "No Check-in Locations Found.");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            LatLng latLng = new LatLng(Double.valueOf(res.getString(2)), Double.valueOf(res.getString(3)));
            mMap.addMarker(new MarkerOptions().position(latLng).title("Check-in Location: " + res.getString(1)).snippet(latLng.latitude + ", " + latLng.longitude));
            //buffer.append("Latitude: " + res.getString(1) +"\n");
            //buffer.append("Longitude: " + res.getString(2) + "\n");
        }
    }

    private void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    //HARD TEST:
    class Load extends AsyncTask<String, String, String> {
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

            db = new DatabaseHelper(getBaseContext());
            Cursor res = db.getAllData();
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
                currLocation.setLatitude(deviceLocation.getLatitude());
                currLocation.setLongitude(deviceLocation.getLongitude());

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
                showMessage("Closest To", unused);
            }
        }
    }
}
