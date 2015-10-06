package com.example.tyson.vendingmachine;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    public static final int VENDING_QUANTITY = 20;
    EditText mDepositText;
    TextView mChangeReturned;
    VendingMachine mVendingMachine;
    Spinner mSpinner;
    int mItemSelected;
    ArrayList<VendingItem> vendingItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDepositText = (EditText) findViewById(R.id.depositAmount);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        //arraylist to display vending item and price in spinner
        vendingItems = new ArrayList<>();
        //edittext to display change returned
        mChangeReturned = (TextView) findViewById(R.id.textView);
        //instantiate our Vending Machine object

        //get String Array Resources for display and vending item creation purposes
        String[] itemNames = getResources().getStringArray(R.array.item_arrays);
        String[] itemPrice = getResources().getStringArray(R.array.item_costs);
        //Local String array to help format spinner item display
        List<String> itemsDisplay = new ArrayList<>();

        //for loop to add to display item list and to add vending items to our Vending machine
        //also initializes price for each vending item
        for(int i = 0; i< itemNames.length; i++){
            itemsDisplay.add(itemNames[i] + " - " + itemPrice[i]);
            double price = Double.parseDouble(itemPrice[i]);
            vendingItems.add(new VendingItem(price, itemNames[i], VENDING_QUANTITY));
        }
        mVendingMachine = new VendingMachine(100, vendingItems);

        //sets spinner using arrayAdapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, itemsDisplay);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    public void purchaseItem(View view) {
        //Code to hide Keyboard on button Press
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        TextView textTotalChange = (TextView) findViewById(R.id.textTotalChange);

        //logic to process purchases
        //condition to verify if user has deposited money into the machine and if
        //amount deposited is enough to make purchase
        if(!TextUtils.isEmpty(mDepositText.getText().toString())) {
            double depositAmount = Double.parseDouble(mDepositText.getText().toString());
            if (depositAmount > 0) {
                if (mVendingMachine.purchaseItem(vendingItems.get(mItemSelected), depositAmount)) {
                    //inform user that item has been purchased
                    Toast.makeText(this, vendingItems.get(mItemSelected).mName + " Dispensed", Toast.LENGTH_LONG).show();
                    String returnChange = "";
                    for(int i = 0; i < mVendingMachine.mCoinsArrayList.size();i++){
                        returnChange += "1 " + mVendingMachine.mCoinsArrayList.get(i) +" ";
                    }
                    //displays returned change value
                    mChangeReturned.setText(returnChange);
                    textTotalChange.setText(String.valueOf(mVendingMachine.mReturnMoney));
                    mDepositText.setText("");
                } else {
                    Toast.makeText(this, "Please enter more money", Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(this,"Please deposit money before purchasing", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //global variable to get spinner selection position
        mItemSelected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
