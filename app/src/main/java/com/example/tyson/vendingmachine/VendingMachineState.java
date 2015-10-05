package com.example.tyson.vendingmachine;

public interface VendingMachineState {

    boolean purchaseItem(VendingItem item, double amtDeposited);

    void restock(VendingItem item);
}
