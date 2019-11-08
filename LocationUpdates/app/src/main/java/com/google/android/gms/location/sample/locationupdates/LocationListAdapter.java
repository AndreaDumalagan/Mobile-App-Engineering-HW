package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class LocationListAdapter extends ArrayAdapter<Location> {

    private static final String TAG = "LocationListAdapter";

    private Context mContext;
    int mResource;

    public LocationListAdapter(Context context, int resource, ArrayList<Location> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String checkinName = getItem(position).getCheckInName();
        double latitude = getItem(position).getLatitude();
        double longitude = getItem(position).getLongitude();
        String address = getItem(position).getAddress();
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();

        Location location = new Location(checkinName, latitude,longitude, address, date, time);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tv_checkin = convertView.findViewById(R.id.check_inName);
        TextView tv_coordinates = convertView.findViewById(R.id.coordinates);
        TextView tv_address = convertView.findViewById(R.id.address);
        TextView tv_date_time = convertView.findViewById(R.id.date_time);

        tv_checkin.setText(checkinName);
        tv_coordinates.setText(latitude + ", " + longitude);
        tv_address.setText(address);
        tv_date_time.setText(date + ", " + time);

        return convertView;
    }
}
