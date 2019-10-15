package com.example.testhw2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    private EditText inputName;
    private EditText inputNum;
    private Button addContact;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addContact = findViewById(R.id.add_contact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("message_return", "This data is returned when user click button in target activity.");
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    public void addContact(View v){
        Intent intent  = new Intent();
        intent.putExtra("keyName", "help");
        setResult(RESULT_OK, intent);
        finish();
    }
    /*
    @Override
    public void onBackPressed(){
        inputName = findViewById(R.id.input_name);
        inputNum = findViewById(R.id.input_num);

        String newName = inputName.getText().toString();
        String newNum = inputNum.getText().toString();

        data = new Intent();
        data.putExtra("test", "please for the love of god work");
        setResult(Activity.RESULT_OK, data);
        finish();
        //Toast.makeText(getBaseContext(), newName + ": " + newNum, Toast.LENGTH_LONG).show();
    }*/
}
