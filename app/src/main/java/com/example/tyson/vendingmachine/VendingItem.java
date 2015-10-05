package com.example.tyson.vendingmachine;

public class VendingItem {

    public double mPrice;
    public String mName;
    public int mQuantity;

    public VendingItem(double price, String name, int quantity) {
        mPrice = price;
        mName = name;
        mQuantity = quantity;
    }
}
