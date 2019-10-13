package com.example.testhw2;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Contact> contactList;

    public CustomAdapter(Context context, ArrayList<Contact> contactList){
        this.context = context;
        this.contactList = contactList;
    }

    private class ViewHolder{
        protected CheckBox checkBox;
        private TextView contactName;
    }

    @Override
    public int getCount(){return contactList.size();}
    @Override
    public Object getItem(int position){return contactList.get(position);}
    @Override
    public long getItemId(int position){return 0;}
    @Override
    public int getViewTypeCount(){return getCount();}
    @Override
    public int getItemViewType(int position){return position;}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_contacts, null, false);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.contactName = (TextView) convertView.findViewById(R.id.contact_name);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }


        holder.contactName.setText(contactList.get(position).getName());
        holder.checkBox.setChecked(contactList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer) holder.checkBox.getTag();

                if(contactList.get(pos).getSelected()){
                    contactList.get(pos).setSelected(false);
                }
                else{
                    contactList.get(pos).setSelected(true);
                }

            }
        });

        holder.contactName.setOnClickListener(new View.OnClickListener(){
            MainActivity test = new MainActivity();
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, ContactDetails.class);
                intent.putExtra("contactName", contactList.get(position).getName());
                intent.putExtra("contactPhone", contactList.get(position).getNumber());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
