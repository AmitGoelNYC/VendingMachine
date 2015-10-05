package com.example.tyson.vendingmachine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    EditText mDepositText;
    TextView mChangeReturned;
    VendingMachine vendingMachine;
    Spinner spinner;
    int itemSelected;
    ArrayList<VendingItem> vendingItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDepositText = (EditText) findViewById(R.id.depositAmount);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        vendingItems = new ArrayList<>();

        mChangeReturned = (TextView) findViewById(R.id.textView);

        vendingMachine = new VendingMachine(100);
        String[] itemNames = getResources().getStringArray(R.array.item_arrays);
        String[] itemPrice = getResources().getStringArray(R.array.item_costs);
        for(int i = 0; i< itemNames.length; i++){
            double price = Double.parseDouble(itemPrice[i]);
            vendingItems.add(new VendingItem(price, itemNames[i], 20));
        }
        vendingMachine.addItems(vendingItems);
    }

    public void purchaseItem(View view) {
        TextView textTotalChange = (TextView) findViewById(R.id.textTotalChange);
        if(!TextUtils.isEmpty(mDepositText.getText().toString())) {
            double depositAmount = Double.parseDouble(mDepositText.getText().toString());
            if (depositAmount > 0) {
                if (vendingMachine.purchaseItem(vendingItems.get(itemSelected), depositAmount)) {
                    Toast.makeText(this, vendingItems.get(itemSelected).mName + " Dispensed", Toast.LENGTH_LONG).show();
                    String returnChange = "";
                    for(int i = 0; i < vendingMachine.coinsArrayList.size();i++){
                        returnChange += "1 " + vendingMachine.coinsArrayList.get(i) +" ";
                    }
                    mChangeReturned.setText(returnChange);
                    textTotalChange.setText(String.valueOf(vendingMachine.returnMoney));
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
        itemSelected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
