package com.example.henrybender.testbillsplit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class foodActivity extends AppCompatActivity {


    EditText addItemNameView;
    EditText addItemPriceView;
    Float addItemPrice;
    Spinner personSpinner;
    Button addItems, doneItems;
    ArrayList<String> name;
    ListView displayItemsView;
    HashMap<String, String> items;
    HashMap<String, HashMap<String, String>> peopleItems;
    String currentItemPerson = "";
    String currentItemName = "";
    String currentItemPrice = "";
    Bundle bundleFromAddPersonActivity;
    final ArrayList<ArrayList<HashMap<String, String>>> listViewData = new ArrayList<ArrayList<HashMap<String, String>>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Log.i("FoodActivityCreate", "I'm created");
        peopleItems = new HashMap<String, HashMap<String, String>>(10);
        personSpinner = (Spinner) findViewById(R.id.personSpinner);
        addItemNameView = (EditText) findViewById(R.id.addItemName);
        addItemPriceView = (EditText) findViewById(R.id.addItemPrice);
        doneItems = (Button) findViewById(R.id.doneItems);
        displayItemsView = (ListView) findViewById(R.id.displayItems);
        addItems = (Button) findViewById(R.id.addItems);
        bundleFromAddPersonActivity = getIntent().getExtras();
        name = bundleFromAddPersonActivity.getStringArrayList("personList");


        final CustomSpinnerAdapter spinnerArrayAdapter = new CustomSpinnerAdapter(name, this);
        personSpinner.setAdapter(spinnerArrayAdapter);


        final newCustomFoodItemAdapter personItemViewAdapter = new newCustomFoodItemAdapter(listViewData, this);
        displayItemsView.setAdapter(personItemViewAdapter);

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> listViewItem = new HashMap<String, String>(2);
                currentItemPerson = personSpinner.getSelectedItem().toString();
                currentItemName = addItemNameView.getText().toString();
                currentItemPrice = addItemPriceView.getText().toString();

                boolean itemNameBlank = currentItemName.length() == 0;
                boolean itemPriceBlank = currentItemPrice.length() == 0;
                boolean personBlank = currentItemPerson == null;

                if (itemNameBlank && itemPriceBlank && personBlank) {
                    Toast.makeText(foodActivity.this, "Please enter an item, price, and person", Toast.LENGTH_SHORT).show();
                } else if (itemNameBlank && itemPriceBlank) {
                    Toast.makeText(foodActivity.this, "Please enter an item and price", Toast.LENGTH_SHORT).show();
                } else if (itemNameBlank && personBlank) {
                    Toast.makeText(foodActivity.this, "Please enter an item and person", Toast.LENGTH_SHORT).show();
                } else if (itemPriceBlank && personBlank) {
                    Toast.makeText(foodActivity.this, "Please enter a price and person", Toast.LENGTH_SHORT).show();
                } else if (itemNameBlank) {
                    Toast.makeText(foodActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                } else if (itemPriceBlank) {
                    Toast.makeText(foodActivity.this, "Please enter a price", Toast.LENGTH_SHORT).show();
                } else if (personBlank) {
                    Toast.makeText(foodActivity.this, "Please select a person", Toast.LENGTH_SHORT).show();
                } else {

                    items = peopleItems.get(currentItemPerson);
                    if (items == null) {
                        items = new HashMap<String, String>(1);
                    }
                    // categorize sub items under same person
                    items.put(currentItemName, currentItemPrice);
                    peopleItems.put(currentItemPerson, items);
                    addItemNameView.setText(new String());
                    addItemPriceView.setText(new String());
                    addNewItemInArrayList(items, currentItemPerson);
//                ArrayList<HashMap<String, String>> personItems = new ArrayList<>(2);
//                HashMap<String, String> personHash = new HashMap<>();
//                personHash.put("name", currentItemPerson);
//                personItems.add(personHash);
//                personItems.add(items);
//
//
//
//                listViewData.add(personItems);


                    personItemViewAdapter.notifyDataSetChanged();

//                personItemListViewAdapter.notifyDataSetChanged();
                }
            }

            private void addNewItemInArrayList(HashMap<String, String> items, String personName) {
                try {
                    int currentIndex = -1;
                    for (ArrayList<HashMap<String, String>> innerList : listViewData) {
                        if (innerList.get(0).get("name").equals(personName)) {
                            currentIndex = listViewData.lastIndexOf(innerList);
                            listViewData.remove(innerList);
                            break;
                        }
                    }
                    ArrayList<HashMap<String, String>> personItems = new ArrayList<>(2);
                    HashMap<String, String> personHash = new HashMap<>();
                    personHash.put("name", currentItemPerson);
                    personItems.add(personHash);
                    personItems.add(items);
                    if(currentIndex == -1) {
                        listViewData.add(personItems);
                    } else {
                        listViewData.add(currentIndex, personItems);
                    }
                } catch (Exception e) {
                    Log.e("Error in adding new", e.toString());
                }
            }
        });
        doneItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(foodActivity.this, taxTipFinalActivity.class);
                Bundle extras = new Bundle();
                extras.putString("billName", bundleFromAddPersonActivity.getString("billName"));

                extras.putSerializable("peopleItems", peopleItems);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        }
    }