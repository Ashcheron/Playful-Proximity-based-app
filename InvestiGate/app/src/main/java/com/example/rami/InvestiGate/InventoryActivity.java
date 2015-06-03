package com.example.rami.InvestiGate;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Inventory page with custom adapter
 *
 * THINGS TO DO:
 * Change to menu -> Buttons for different inventories (suspects, locations etc.)
 * Empty screen for inventory
 *
 * CATEGORIES:
 * -1 - NO CATEGORY
 * 0 - SUSPECT
 * 1 - WEAPON
 * 2 - LOCATION
 * 3 - MISCELLANEOUS
 */

public class InventoryActivity extends ListActivity {
    int mCategory;
    InventoryAdapterClue mClueAdapter;
    InventoryAdapterToken mTokenAdapter;
    StaticItemList create;
    PlayerInventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        create = new StaticItemList(this);
        inventory = new PlayerInventory(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCategory = extras.getInt(getResources().getString(R.string.inventory_category));
            Log.d("Activity extras", String.valueOf(mCategory));

        }
        else {
            mCategory = -1;
            Log.d("Activity extras", "No extras");
        }

        createUi();
    }

    private void createUi () {
        if (mCategory < 0) {
            ArrayList<Clue> items = inventory.getPlayerClues();
            //ArrayList<Clue> staticItems = create.getClueList();
            mClueAdapter = new InventoryAdapterClue(this, items);
            setListAdapter(mClueAdapter);
        }
        else {
            ArrayList<Token> items = inventory.getPlayerTokensWithCategory(mCategory);
            //ArrayList<Token> staticItems = create.getTokensWithCategory(mCategory);
            mTokenAdapter = new InventoryAdapterToken(this, items, mCategory);
            setListAdapter(mTokenAdapter);
        }


    }

}
