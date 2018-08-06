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
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class newCustomFoodItemAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<ArrayList<HashMap<String, String>>> itemList;
    private Context context;

    public newCustomFoodItemAdapter(ArrayList<ArrayList<HashMap<String, String>>> itemList, Context context) {
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
    public View getView(final int position, View personView, ViewGroup parent){
        View view = personView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.person_listview, null);
        }
        TextView listName = (TextView) view.findViewById(R.id.person_name);
        HashMap<String, String> personHash = itemList.get(position).get(0);
        String personName = personHash.get("name");
        listName.setText(personName);
        Log.i("Name is", listName.getText().toString());

        HashMap<String, String> items = itemList.get(position).get(1);
        ListView personItems = view.findViewById(R.id.person_items);
        ArrayList<HashMap<String, String>> listTheItems = new ArrayList<>(5);
        Iterator<String> itemKeySetIterator = items.keySet().iterator();
        while(itemKeySetIterator.hasNext()){
            String itemName = itemKeySetIterator.next();
            String itemPrice = items.get(itemName);
            HashMap<String, String> itemSet = new HashMap<>();
            itemSet.put("Item Name", itemName);
            itemSet.put("Item Price", itemPrice);
            listTheItems.add(itemSet);
        }


        nestedItemAdapter nestedItemAdapter = new nestedItemAdapter(listTheItems, context);
        personItems.setAdapter(nestedItemAdapter);




        return view;

    }
}

