package com.example.testhw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private TextView tv;
    private ArrayList<Contact> contactList = new ArrayList<Contact>();
    private ArrayList<String> displayContact;
    private CustomAdapter customAdapter;
    private Button btnAdd, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.add);
        btnDelete = findViewById(R.id.delete);

        Contact first = new Contact("John Doe", "1234567890", false);
        Contact second = new Contact("Jane Doe", "9876543210", false);
        contactList.add(first);
        contactList.add(second);

        customAdapter = new CustomAdapter(this, contactList);
        lv.setAdapter(customAdapter);

        btnDelete = (Button) findViewById(R.id.delete);
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteContacts();
            }
        });
    }

    public void deleteContacts(){
        for (int i = 0; i < contactList.size(); i++){
            if(contactList.get(i).getSelected()){
                contactList.remove(i);
            }
        }
        customAdapter.notifyDataSetChanged();
    }
}
