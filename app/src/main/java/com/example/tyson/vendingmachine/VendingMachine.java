package com.example.tyson.vendingmachine;

import java.util.ArrayList;

public class VendingMachine implements VendingMachineState {

    public static double mTotalMoney;
    ArrayList<VendingItem> mItems;
    public static ArrayList<Coins> mCoinsArrayList;
    double mReturnMoney = 0;

    public VendingMachine(double money, ArrayList<VendingItem> items) {
        this.mTotalMoney = money;
        this.mItems = items;
    }

    //enum class to represent our various coins
    public enum Coins {
        NICKEL(.05), DIME(.10), QUARTER(.25), DOLLARCOIN(1.00), PENNY(.01);
        public double value;

        Coins(double value) {
            this.value = value;
        }
    }

    public boolean purchaseItem(VendingItem item, double amtDeposited) {
        if (amtDeposited > item.mPrice) {
            //check to see if stock is running low, if so replenish stock
            if (item.mQuantity < 4)
                restock(item);
            double cost = item.mPrice;
            mTotalMoney -= cost;
            mReturnMoney = amtDeposited - cost;
            mReturnMoney *= 100;
            mReturnMoney = Math.round(mReturnMoney);
            mReturnMoney /= 100;
            dispenseItem(item, mReturnMoney);
            return true;
        }
        return false;
    }

    private void dispenseItem(VendingItem item, double returnMoney) {
        item.mQuantity--;
        mCoinsArrayList = new ArrayList<>();

        //logic to return coins by adding them to a list
        while (returnMoney >= Coins.DOLLARCOIN.value) {
            mCoinsArrayList.add(Coins.DOLLARCOIN);
            returnMoney -= Coins.DOLLARCOIN.value;
            //rounding error, so multiply a 100 and use MAth.round function
            //then divide by 100 to attempt to get correct results
            returnMoney *= 100;
            returnMoney = Math.round(returnMoney);
            returnMoney /= 100;
        }
        while (returnMoney >= Coins.QUARTER.value) {
            mCoinsArrayList.add(Coins.QUARTER);
            returnMoney -= Coins.QUARTER.value;
            returnMoney *= 100;
            returnMoney = Math.round(returnMoney);
            returnMoney /= 100;
        }
        while (returnMoney >= Coins.DIME.value) {
            mCoinsArrayList.add(Coins.DIME);
            returnMoney -= Coins.DIME.value;
            returnMoney *= 100;
            returnMoney = Math.round(returnMoney);
            returnMoney /= 100;
        }
        while (returnMoney >= Coins.NICKEL.value) {
            mCoinsArrayList.add(Coins.NICKEL);
            returnMoney -= Coins.NICKEL.value;
            returnMoney *= 100;
            returnMoney = Math.round(returnMoney);
            returnMoney /= 100;
        }
        while (returnMoney >= Coins.PENNY.value) {
            mCoinsArrayList.add(Coins.PENNY);
            returnMoney -= Coins.PENNY.value;
            returnMoney *= 100;
            returnMoney = Math.round(returnMoney);
            returnMoney /= 100;
        }
    }

    public void restock(VendingItem item) {
        item.mQuantity += 10;
    }
}
