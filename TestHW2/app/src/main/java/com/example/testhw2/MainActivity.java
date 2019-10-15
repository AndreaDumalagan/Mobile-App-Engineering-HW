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
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
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

        Contact first = new Contact("Andrea Dumalagan", "2243996303", false);
        Contact second = new Contact("Christian Remolado", "7326475642", false);
        Contact third = new Contact("Andy Wang","3479514558", false);

        contactList.add(first);
        contactList.add(second);
        contactList.add(third);

        //SORT contactList ALPHABETICALLY
        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        customAdapter = new CustomAdapter(this, contactList);
        lv.setAdapter(customAdapter);



        /*
        * onClick IMPLEMENTATION
        * */
        //Delete Button (onClick)
        btnDelete = (Button) findViewById(R.id.delete);
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteContacts();
            }
        });
        //Add Button (onClick)
        btnAdd = findViewById(R.id.add);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openAddContact();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                String returnString = data.getStringExtra("keyName");

                // Set text view with string
                Toast.makeText(getBaseContext(), returnString, Toast.LENGTH_LONG).show();
            }
        }
    }


    /*
    * BUTTON FUNCTIONS
    * */

    //Delete Button (function): Deletes all contact names whose checkbox boolean = true
    public void deleteContacts(){
        for (int i = 0; i < contactList.size(); i++){
            if(contactList.get(i).getSelected()){
                contactList.remove(i);
            }
        }
        customAdapter.notifyDataSetChanged();
    }

    //Add Button (function): Opens addContact activity
    public void openAddContact(){
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        startActivity(intent);
    }
}
