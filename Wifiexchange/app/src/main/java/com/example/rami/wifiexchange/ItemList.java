package com.example.rami.wifiexchange;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class ItemList extends ActionBarActivity {

    ArrayList <Item> list;
    String names[] = {"Knife","Footprint","Autopsy report","Blood","Baseball bat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        list = new ArrayList<Item>();

        createItemList();
    }

    // Just add some items to array
    private void createItemList() {

        for (int i = 0; i < 5; i++) {
            Item temp = new Item();
            temp.setName(names[i]);

            list.add(temp);
        }
    }

    private void buildInterface() {


    }

}
