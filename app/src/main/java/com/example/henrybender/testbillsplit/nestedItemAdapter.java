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
import java.util.HashMap;

public class nestedItemAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<HashMap<String, String>> itemList;
    private Context context;

    public nestedItemAdapter(ArrayList<HashMap<String, String>> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int pos) {
        return itemList.get(pos);
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
            view = inflater.inflate(R.layout.person_item_listview, null);
        }
        TextView itemNameView = view.findViewById(R.id.item_name);
        TextView itemPriceView = view.findViewById(R.id.item_price);

        itemNameView.setText(itemList.get(position).get("Item Name"));
        itemPriceView.setText(itemList.get(position).get("Item Price"));


        Button deletebutton = (Button) view.findViewById(R.id.item_delete);

        deletebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               itemList.remove(position);
               notifyDataSetChanged();
            }});


        return view;

    }
}



