package com.example.henrybender.testbillsplit;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PersonItems extends RealmObject {
    public String personName;
    public RealmList<Item> listOfItems;
    public Double totalPaid;

    public void setPersonName(String personName) {
        this.personName = personName;
    }
    public String getPersonName() {
        return this.personName;
    }
    public void setListOfItems(RealmList<Item> listOfItems) {
        this.listOfItems = listOfItems;
    }
    public RealmList<Item> getListOfItems() {
        return this.listOfItems;
    }
}
