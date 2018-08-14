package com.example.henrybender.testbillsplit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

//import com.example.henrybender.contacts.R;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {
    // The ListView
    ArrayList<Contact> listContacts;
    ContactsAdapter adapterContacts;
    ListView lvContacts, addedContacts;
    EditText nameContact;
    Button doneContacts;
    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            listContacts = new ContactFetcher(this).fetchAll();
            lvContacts = (ListView) findViewById(R.id.contact_list_view);
            final ContactsAdapter adapterContacts = new ContactsAdapter(this, listContacts);
            lvContacts.setAdapter(adapterContacts);
            lvContacts.setClickable(true);
            lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object selectedContact = lvContacts.getItemAtPosition(i);
                    Log.i("this item was clicked", selectedContact.toString());
                }
            });

            nameContact = findViewById(R.id.nameContact);
            if (nameContact.length() > 0) {
                nameContact.getText().clear();
            }
            nameContact.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Call back the Adapter with current character to Filter
                    adapterContacts.getFilter().filter(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        doneContacts = findViewById(R.id.doneContacts);
        addedContacts = findViewById(R.id.addedContacts);
        doneContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        }



    }}