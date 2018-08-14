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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Button addBillBtn;
    EditText billName;
    ListView historyListView;
    List<Map<String, String>> listViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log.i("MainActivityCreate", "I'm created");
        Log.i("BSTrack", ""+getFragmentManager().getBackStackEntryCount());
        addBillBtn = findViewById(R.id.newBill);
        billName = findViewById(R.id.BillName);
        historyListView = findViewById(R.id.history);


        listViewData = new ArrayList<Map<String,String>>();


        addBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PersonTypeActivity.class);
                Bundle data = new Bundle();
                data.putString("billName", billName.getText().toString());
                intent.putExtras(data);
                startActivity(intent);
            }
        });

        // Get Realm Results, set Result's BillName as ListViewData
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmResults<Bill> listOfBills = realm.where(Bill.class)
                    .findAll();
            for(Bill currentBill : listOfBills) {
                HashMap<String, String> currentListViewRow = new HashMap<>();
                currentListViewRow.put("Bill Name", currentBill.billName);
                currentListViewRow.put("Bill Date", currentBill.dateDined.toString());
                currentListViewRow.put("Bill Total", currentBill.totalBillCost.toString());
                listViewData.add(currentListViewRow);
            }
        }


        final SimpleAdapter historyListViewAdapter = new SimpleAdapter(this, (List<? extends Map<String, ?>>) listViewData,
                R.layout.person_cost_listview,
                new String[]{"Bill Name", "Bill Date", "Bill Total"},
                new int[]{R.id.bill_person, R.id.bill_person_price, R.id.original_price});

        historyListView.setAdapter(historyListViewAdapter);
    }

}
