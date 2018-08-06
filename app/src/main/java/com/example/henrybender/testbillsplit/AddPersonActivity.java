package com.example.henrybender.testbillsplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPersonActivity extends AppCompatActivity {

    ArrayList<String> name;

    Button addButton, doneButton;
    EditText person1;
    Bundle fromAddBillName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_person);
        Log.i("AddPActivityCreate", "I'm created");

        addButton = findViewById(R.id.addButton);
        person1 = findViewById(R.id.person1);
        name = new ArrayList<String>();
        fromAddBillName = getIntent().getExtras();
        name.add("Me");
        doneButton = findViewById(R.id.doneButton);
        final CustomAdapter adapter = new CustomAdapter(name, this);
        ListView lview = findViewById(R.id.my_listview);
        lview.setAdapter(adapter);
        Log.i("adapter is", lview.getAdapter().toString());


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String peopleText = person1.getText().toString();
                final boolean personBlank = peopleText.length() == 0;

                if (personBlank) {
                    Toast.makeText(AddPersonActivity.this, "Please type a person name", Toast.LENGTH_SHORT).show();
                }
                else {
                    name.add(person1.getText().toString());
                    person1.setText(new String());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPersonActivity.this, foodActivity.class);
                fromAddBillName.putStringArrayList("personList", name);
                intent.putExtras(fromAddBillName);
                startActivity(intent);
            }

        });
    }}


