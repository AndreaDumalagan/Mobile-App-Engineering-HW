package com.example.hw1_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText inputTitle;
    private EditText inputDescrip;
    private static final String TAG = "MainActivity";
    private ArrayList<ToDoList> taskList;
    private ListView mListView;
    private ToDoListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started.");

        mListView = findViewById(R.id.todoList);

        ToDoList first = new ToDoList("Add Task", "Click 'Add' to append to list");
        ToDoList second = new ToDoList("Remove Task", "Long click on item you wish to remove");


        taskList = new ArrayList<>();
        taskList.add(first);
        taskList.add(second);

        adapter = new ToDoListAdapter(this, R.layout.activity_list, taskList);
        mListView.setAdapter(adapter);

        setupListViewListener();

    }

    //Function attached to onClick button
        //Adds user-input item at bottom of ListView
    public void addItem(View v){

        inputTitle = findViewById(R.id.enterTask);
        inputDescrip = findViewById(R.id.enterDescrip);

        String newTitle = inputTitle.getText().toString();
        String newDescrip = inputDescrip.getText().toString();

        ToDoList newTask = new ToDoList(newTitle, newDescrip);
        taskList.add(newTask);
        adapter.notifyDataSetChanged();

        writeFile();
        inputTitle.setText("");
        inputDescrip.setText("");
    }

    //Removes item when user long-clicks item
    private void setupListViewListener(){
        mListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        taskList.remove(position);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
    }

    //Write items in ToDoList as .txt file
        //.txt file located in:
        // Device File Explorer --> data --> user --> 0 -->.com.example.hw1_todolist --> files --> ToDo_List-txt
    public void writeFile(){

        String writeTitle;
        String writeDescrip;
        String writeToDoList = "";
        String baseFolder = getBaseContext().getFilesDir().getAbsolutePath();

        //Grabs each ToDoList object from taskList ArrayList and appending each object to the file
        for(int i = 0; i < taskList.size(); i++){

            writeTitle = taskList.get(i).getTitle();
            writeDescrip = taskList.get(i).getDescrip();
            writeToDoList = writeToDoList + "Task Title " + (i+1) + ": " + writeTitle + "\n" + "Task Description " + (i+1) +": " + writeDescrip + "\n" + "\n";

        }

        try{
            FileOutputStream fileOutputStream = openFileOutput("ToDo_List.txt", MODE_PRIVATE);
            fileOutputStream.write(writeToDoList.getBytes());
            fileOutputStream.close();

            Toast.makeText(getApplicationContext(), "ToDo List saved!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "ToDo_List.text Saved To:" + "\n\nDevice File Explorer --> "+ baseFolder, Toast.LENGTH_SHORT).show();

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
