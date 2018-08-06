package com.example.henrybender.testbillsplit;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Bill extends RealmObject {
    public String billName;
    public Date dateDined;
    public RealmList<PersonItems> listOfPersonItems;
    public Double totalBillCost;
//    public void setBillName (String billName) {
//        this.billName = billName;
//    }
//    public String getBillName () {
//        return this.billName;
//    }
//    public void setListOfPersonItems (RealmList<PersonItems> listOfPersonItems) {
//        this.listOfPersonItems = listOfPersonItems;
//    }
//    public RealmList<PersonItems> getListOfPersonItems () {
//        return this.listOfPersonItems;
//    }
//    public void setTotalBillCost (Double totalBillCost) {
//        this.totalBillCost = totalBillCost;
//    }
//    public Double getTotalBillCost () {
//        return this.totalBillCost;
//    }
//    public void
//    public Double getTotalCost () {
//        // Traverse listOfPersonItems, get Sum of all items and
//        return 0.0;
//    }// end getTotalCost
}


