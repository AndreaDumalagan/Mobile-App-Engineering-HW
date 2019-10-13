package com.example.testhw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent intent = getIntent();
        TextView textName = findViewById(R.id.textName);
        TextView textPhone = findViewById(R.id.textPhone);

        String currName = intent.getExtras().getString("contactName");
        String currPhone = intent.getExtras().getString("contactPhone");

        textName.setText(currName);
        textPhone.setText(currPhone);
    }
}
