package com.google.android.gms.location.sample.locationupdates;

public class Location {
    private String checkInName;
    private double latitude;
    private double longitude;
    private String address;
    private String date;
    private String time;

    public Location(String checkInName, double latitude, double longitude, String address, String date, String time){
        this.checkInName = checkInName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.date = date;
        this.time = time;
    }
    public String getCheckInName(){
        return checkInName;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getAddress(){
        return address;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }


}
