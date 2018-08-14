package com.example.henrybender.testbillsplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PersonTypeActivity extends AppCompatActivity {
Button addByName, addByContact;
Bundle fromAddBillName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_type);
    addByName = findViewById(R.id.addByName);
    addByContact = findViewById(R.id.addByContact);
    fromAddBillName = getIntent().getExtras();

    addByContact.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
            Intent intent = new Intent(PersonTypeActivity.this, AddContactActivity.class);
            intent.putExtras(fromAddBillName);
            startActivity(intent);
            }
            catch (Exception e){
                Log.e("add not working", e.toString());
            }
    }});
    addByName.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PersonTypeActivity.this, AddPersonActivity.class);
            intent.putExtras(fromAddBillName);
            startActivity(intent);
        }
    });}
}
