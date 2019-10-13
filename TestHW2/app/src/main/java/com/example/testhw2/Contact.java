package com.example.testhw2;

import java.util.ArrayList;

public class Contact {
    private String name, number;
    private boolean isSelected;

    public Contact(String name, String number, boolean isSelected){
        this.name = name;
        this.number = number;
        this.isSelected = isSelected;
    }

    public String getName(){return name;}
    public String getNumber(){return number;}
    public boolean getSelected(){return isSelected;}

    public void setName(String name){this.name = name;}
    public void setNumber(String number){this.number = number;}
    public void setSelected(boolean selected){isSelected = selected;}
}
