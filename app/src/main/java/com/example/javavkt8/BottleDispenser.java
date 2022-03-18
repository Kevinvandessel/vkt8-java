package com.example.javavkt8;

import android.view.View;
import android.widget.TextView;

import java.util.*;
import java.text.*;

public class BottleDispenser {

    private int bottles;
    private double money;
    private ArrayList<Bottle> bottle_array;

    private static BottleDispenser bd = new BottleDispenser();

    private BottleDispenser() {
        bottles = 5;
        money = 0;
        bottle_array = new ArrayList();
        bottle_array.add(new Bottle("Pepsi Max", 0.5, 1.8));
        bottle_array.add(new Bottle("Pepsi Max", 1.5, 2.2));
        bottle_array.add(new Bottle("Coca-Cola Zero", 0.5, 2.0));
        bottle_array.add(new Bottle("Coca-Cola Zero", 1.5, 2.5));
        bottle_array.add(new Bottle("Fanta Zero", 0.5, 1.95));
    }

    public static BottleDispenser getInstance(){
        return bd;
    }

    public int getBottles() {
        return bottles;
    }

    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(TextView text, int moneyamount) {
        money = money + moneyamount;
        text.setText("Klink! Added " + moneyamount + "!" + " Total amount: " + money);
    }

    public Bottle buyBottle(TextView text, int pos) {
        if (bottles>0) {
            if (money > bottle_array.get(pos).getPrice()) {
                text.setText("KACHUNK! "+bottle_array.get(pos).getName()+" came out of the dispenser!");
                money -= bottle_array.get(pos).getPrice();
                bottles -=1;
                bottle_array.remove(pos);
            } else {
                text.setText("Add money first!");
            }
        }else{
            text.setText("Out of bottles!");
        }
        return bottle_array.get(0);
    }

    public void returnMoney(double cash, TextView text) {
        DecimalFormat df = new DecimalFormat("0.00");
        text.setText("Klink klink. Money came out! You got "+df.format(cash)+"€ back");
        money = 0;
    }

    public void listBottles(TextView text){
        int i = 1;
        for(Bottle b : bottle_array){
            text.setText(i+". Name: "+b.getName());
            text.setText("\tSize: "+b.getSize()+"\tPrice: "+b.getPrice());
            i++;
        }
    }

    public List<Bottle> getList(){
        return bottle_array;
    }

}
