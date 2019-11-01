package com.google.android.gms.location.sample.locationupdates;

import android.content.Intent;
import android.location.Location;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                }else{
                    Toast.makeText(MapsActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
