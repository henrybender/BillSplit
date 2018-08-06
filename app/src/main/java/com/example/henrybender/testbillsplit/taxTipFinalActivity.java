package com.example.henrybender.testbillsplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

public class taxTipFinalActivity extends AppCompatActivity {
    EditText taxEditView, tipEditView;
    Button tipAndTaxDoneButton, exitButton;
    ListView splitCostListView;
    Bundle bundleFromFoodActivity;
    HashMap<String, String> items;
    HashMap<String, HashMap<String, String>> peopleItems = null;
    List<Map<String, String>> listViewData;
    HashMap<String, Double> peopleCosts = null;
    Double totalCost = 0.0, beforetaxtip = 0.0;
    Double tax, tip;
    HashMap<String, Double> peopleTotal = null;
    String billName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_tip_final);
        // Setting local variables
        Log.i("TTFinalActivityCreate", "I'm created");

        taxEditView = (EditText) findViewById(R.id.taxEditView);
        tipEditView = (EditText) findViewById(R.id.tipEditView);
        tipAndTaxDoneButton = (Button) findViewById(R.id.tipAndTaxDoneButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        splitCostListView = (ListView) findViewById(R.id.splitCostListView);
        listViewData = new ArrayList<Map<String, String>>();
        peopleCosts = new HashMap<String, Double>(1);

        bundleFromFoodActivity = getIntent().getExtras();

        if(bundleFromFoodActivity != null) {
            peopleItems = (HashMap<String, HashMap<String, String>>) bundleFromFoodActivity.getSerializable("peopleItems");
            billName = bundleFromFoodActivity.getString("billName");
        }
        else{
            //display toast error
        }
        if (peopleItems != null) {
            final SimpleAdapter personItemListViewAdapter = new SimpleAdapter(this, (List<? extends Map<String, ?>>) listViewData,
                    R.layout.person_cost_listview,
                    new String[]{"Name", "Total Cost", "Before Tax Tip"},
                    new int[]{R.id.bill_person, R.id.bill_person_price, R.id.original_price});
            // Add initial people and cost to list view.
            Iterator<String> peopleKeySetIterator = peopleItems.keySet().iterator();
            Iterator<String> itemKeySetIterator;
            Double totalPersonCost;
            String currentItemName;
            Double currentItemPrice;
            // Should be refactored later on...
            while (peopleKeySetIterator.hasNext()) {
                String currentName = peopleKeySetIterator.next();
                totalPersonCost = 0.0;
                beforetaxtip = 0.0;
                items = peopleItems.get(currentName);
                itemKeySetIterator = items.keySet().iterator();
                while (itemKeySetIterator.hasNext()) {
                    currentItemName = itemKeySetIterator.next();
                    currentItemPrice = Double.parseDouble(items.get(currentItemName));
                    totalPersonCost += currentItemPrice;
                    beforetaxtip += currentItemPrice;
                }
                totalCost += totalPersonCost;

                HashMap<String, String> personCostListViewItem = new HashMap<String, String>(1);
                personCostListViewItem.put("Name", currentName);
                personCostListViewItem.put("Total Cost", totalPersonCost.toString());
                personCostListViewItem.put("Before Tax Tip", beforetaxtip.toString());
                listViewData.add(personCostListViewItem);
                peopleCosts.put(currentName, totalPersonCost);
            }
            tipAndTaxDoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //toast for adding tax/tip
                    calculateTaxTip();
                    personItemListViewAdapter.notifyDataSetChanged();
                }
            });
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int returnCode = updateBillInStorage();
                    if(returnCode == 0) {
                        // Redirect to main page
                        Intent intent = new Intent(taxTipFinalActivity.this, MainActivity.class);
//                        intent.putStringArrayListExtra("personList", name);
                        startActivity(intent);
                    } else {
                        // Make a complaint depending on the type of error perhaps.
                        if(returnCode == 1) {
                            // Missing tips or tax
                        } else if (returnCode == -1) {
                            //fatal error in storage or something.
                        }
                    }
                }
            });

            splitCostListView.setAdapter(personItemListViewAdapter);
        }}
        public void calculateTaxTip(){
            try {
//                HashMap<String, String> personCostListViewItem;
                peopleTotal = new HashMap<String, Double>();
                tax = Double.parseDouble(taxEditView.getText().toString()) / totalCost;
                tip = Double.parseDouble(tipEditView.getText().toString());
                Iterator<String> costIterator = peopleCosts.keySet().iterator();
                while (costIterator.hasNext()) {
//                    personCostListViewItem = new HashMap<String, String>(1);

                    String currentName = costIterator.next();
                    Double price = peopleCosts.get(currentName);
                    price *= (1 + tax + tip / 100);
                    Log.i("price", price.toString());
//

                    for(Map<String, String> currentHash : listViewData) {
                        if(currentHash.get("Name").equals(currentName)) {
                            currentHash.put("Total Cost", price.toString());
                            peopleTotal.put(currentName, price);
                            break;
                        }
                    }

                }
            } catch (Exception e) {
                Log.e("Error Updating TT", e.toString());
            }
        }
        public Integer updateBillInStorage () {
            Integer result = -1;
            Realm realm = Realm.getDefaultInstance();
//            try (Realm realm = Realm.getDefaultInstance()) { // Try with resourcesautocloses Realm Instance
            try {
                    realm.beginTransaction();
                    Bill currentBill = realm.createObject(Bill.class);
                    currentBill.billName = billName;
                    currentBill.dateDined = Calendar.getInstance().getTime();
                    currentBill.listOfPersonItems = new RealmList<PersonItems>();
                    Double totalBillCost = 0.0;

                    Iterator<String> peopleKeySetIterator = peopleItems.keySet().iterator();
                    Iterator<String> itemKeySetIterator;
                    String currentItemName;
                    Double currentItemPrice;
                    // Should be refactored later on...
                    while (peopleKeySetIterator.hasNext()) {
                        String currentName = peopleKeySetIterator.next();
                        Double currentTotalCost = peopleTotal.get(currentName);
                        PersonItems currentPersonItems = new PersonItems();

                        currentPersonItems.personName = currentName;
                        currentPersonItems.totalPaid = currentTotalCost;
                        currentPersonItems.listOfItems = new RealmList<Item>();

                        items = peopleItems.get(currentName);
                        itemKeySetIterator = items.keySet().iterator();
                        while (itemKeySetIterator.hasNext()) {
                            currentItemName = itemKeySetIterator.next();
                            currentItemPrice = Double.parseDouble(items.get(currentItemName));
                            Item currentItem = new Item();
                            currentItem.itemName = currentItemName;
                            currentItem.itemCost = currentItemPrice;
                            currentPersonItems.listOfItems.add(currentItem);
                        }
                        currentBill.listOfPersonItems.add(currentPersonItems);
                        totalBillCost += currentTotalCost;
                    }
                    currentBill.totalBillCost = totalBillCost;
//                realm.beginTransaction();

//                Bill currentRealmBill = realm.copyToRealm(currentBill);

                realm.commitTransaction();
                result = 0;

            } catch (Exception e) {
//                realm.cancelTransaction();
                Log.e("AddingToRealm", e.toString());
            }
            realm.close();
            return result;
        }

}





























