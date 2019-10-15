package com.example.testhw2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Contact implements Parcelable {
    private String name, number;
    private boolean isSelected;

    public Contact(String name, String number, boolean isSelected){
        this.name = name;
        this.number = number;
        this.isSelected = isSelected;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        number = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(number);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getName(){return name;}
    public String getNumber(){return number;}
    public boolean getSelected(){return isSelected;}

    public void setName(String name){this.name = name;}
    public void setNumber(String number){this.number = number;}
    public void setSelected(boolean selected){isSelected = selected;}
}
