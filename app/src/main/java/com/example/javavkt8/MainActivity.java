package com.example.javavkt8;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    Context context = null;

    TextView text;
    TextView moneytext;
    SeekBar seekBar;
    Spinner spinner;

    BottleDispenser db = BottleDispenser.getInstance();
    Bottle bought_bottle = new Bottle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        System.out.println("Tiedoston sijainti: " + context.getFilesDir()); //tiedoston löytämistä varten

        text = findViewById(R.id.textView2);
        moneytext = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        spinner = findViewById(R.id.spinner);

        List<Bottle> bottleList = BottleDispenser.getInstance().getList();

        ArrayAdapter<Bottle> adapter= new ArrayAdapter<Bottle>(this, android.R.layout.simple_spinner_item, bottleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                moneytext.setText("Amount to be inserted: " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    //below are methods that connect different activities to buttons on the interface

    public void insertMoneyButton(View v){ //method for add coins button
        int moneyamount = seekBar.getProgress();
        db.addMoney(text, moneyamount);
        seekBar.setProgress(0);
    }

    public void buyBottleButton(View v){ //method for buy button
        int pos = spinner.getSelectedItemPosition();

        db.listBottles(text);

        Bottle pullo = db.buyBottle(text, pos);
        String bottlename = pullo.getName();
        Double bottleprice = pullo.getPrice();
        Double bottlesize = pullo.getSize();
        bought_bottle.setSize(bottlesize);
        bought_bottle.setName(bottlename);
        bought_bottle.setPrice(bottleprice);

        seekBar.setProgress(0);

        return;
    }

    public void cashOutButton(View v){ //method for cash out button
        double amount = db.getMoney();
        db.returnMoney(amount, text);
        seekBar.setProgress(0);
    }

    public void receiptButton(View v){ //method for receipt button
        //here something when time comes
        seekBar.setProgress(0);


        //write information to a receipt file

        try{
            String receipt = "receipt";
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput(receipt, Context.MODE_PRIVATE));

            int index = spinner.getSelectedItemPosition();
            String s = String.valueOf("name: " + bought_bottle.getName() + "\nsize: " + bought_bottle.getSize() + "\nprice: " + bought_bottle.getPrice());


            ows.write(s);
            ows.close();
        } catch (IOException e){
            Log.e("IOException", "Syötteessä oli virhe:(");
        } finally {
            text.setText("Your receipt is waiting for you :D");
            System.out.println("Homma kirjotettu");
        }
    }

    public void getSelectedItem(View v){
        Bottle bottle = (Bottle) spinner.getSelectedItem();
    }

}