package com.example.tyson.vendingmachine;

import java.util.ArrayList;

public class VendingMachine implements VendingMachineState{

    public static double mTotalMoney;
    ArrayList<VendingItem> mItems;
    public static ArrayList<Coins> coinsArrayList;
    double returnMoney = 0;

    public VendingMachine(double money) {
        this.mTotalMoney = money;
        mItems = new ArrayList<>();
    }

    public void addItems(ArrayList<VendingItem> item) {
        mItems = item;
    }

    public enum Coins{
        NICKEL(.05), DIME(.10), QUARTER(.25), DOLLARCOIN(1.00);
        public double value;
        Coins(double value){
            this.value = value;
        }
    }

    public boolean purchaseItem(VendingItem item, double amtDeposited) {
        if (amtDeposited > item.mPrice) {
            if (item.mQuantity < 4)
                restock(item);
            double cost = item.mPrice;
            mTotalMoney -= cost;
            returnMoney = amtDeposited - cost;
            returnMoney *= 100;
            returnMoney = Math.round(returnMoney);
            returnMoney /= 100;
            dispenseItem(item, returnMoney);
            return true;
        }
        return false;
    }

    private void dispenseItem(VendingItem item, double returnMoney) {
        item.mQuantity--;
        coinsArrayList = new ArrayList<>();
        if(returnMoney >= Coins.DOLLARCOIN.value){
            while(returnMoney >= Coins.DOLLARCOIN.value) {
                coinsArrayList.add(Coins.DOLLARCOIN);
                returnMoney -= Coins.DOLLARCOIN.value;
                returnMoney *= 100;
                returnMoney = Math.round(returnMoney);
                returnMoney /= 100;
            }
        }
        if(returnMoney >= Coins.QUARTER.value ){
            while(returnMoney >= Coins.QUARTER.value) {
                coinsArrayList.add(Coins.QUARTER);
                returnMoney -= Coins.QUARTER.value;
                returnMoney *= 100;
                returnMoney = Math.round(returnMoney);
                returnMoney /= 100;
            }
        }
        if(returnMoney >= Coins.DIME.value) {
            while(returnMoney >= Coins.DIME.value) {
                coinsArrayList.add(Coins.DIME);
                returnMoney -= Coins.DIME.value;
                returnMoney *= 100;
                returnMoney = Math.round(returnMoney);
                returnMoney /= 100;
            }
        }
        if(returnMoney >= Coins.NICKEL.value){
            while(returnMoney >=  Coins.NICKEL.value) {
                coinsArrayList.add(Coins.NICKEL);
                returnMoney -= Coins.NICKEL.value;
                returnMoney *= 100;
                returnMoney = Math.round(returnMoney);
                returnMoney /= 100;
            }
        }
    }

    public void restock(VendingItem item) {
        item.mQuantity += 10;
    }
}
