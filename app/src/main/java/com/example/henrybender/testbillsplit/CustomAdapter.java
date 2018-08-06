package com.example.henrybender.testbillsplit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<String> name;
    private Context context;

    public CustomAdapter(ArrayList<String> nameList, Context context) {
        this.name = nameList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return name.size();
}

    @Override
    public Object getItem(int pos) {
        return name.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View passedView, ViewGroup parent){
        View view = passedView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.person_row1, null);
        }
        TextView listName = (TextView) view.findViewById(R.id.textView);
        listName.setText(name.get(position));


        Button deletebutton = (Button) view.findViewById(R.id.deletebutton);
        //Button editBtn = (Button) view.findViewById(R.id.editBtn);

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.remove(position);
                notifyDataSetChanged();
            }});


        return view;

        }
    }



