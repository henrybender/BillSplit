package com.example.henrybender.testbillsplit;

import io.realm.RealmObject;

public class Item extends RealmObject {
    public String itemName;
    public Double itemCost;

    public String getItemName() {
        return this.itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public Double getItemCost() {
        return this.itemCost;
    }
    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }
}
