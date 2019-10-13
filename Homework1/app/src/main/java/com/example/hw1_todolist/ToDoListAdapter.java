package com.example.hw1_todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/*
 *
 * CUSTOM ADAPTER CLASS
 *
 */

public class ToDoListAdapter extends ArrayAdapter<ToDoList> {

    private static final String TAG = "ToDoListAdapter";
    private Context mContext;
    int mRes;

    public ToDoListAdapter(Context context, int res, ArrayList<ToDoList> objects){
        super(context, res, objects);
        mContext = context;
        mRes = res;
    }

    @NonNull
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        String title = getItem(pos).getTitle();
        String descrip = getItem(pos).getDescrip();

        ToDoList task = new ToDoList(title, descrip);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRes, parent, false);

        TextView tvTitle =  convertView.findViewById(R.id.itemTitle);
        TextView tvDescrip =  convertView.findViewById(R.id.itemDescription);

        tvTitle.setText(title);
        tvDescrip.setText(descrip);

        return convertView;
    }

}
