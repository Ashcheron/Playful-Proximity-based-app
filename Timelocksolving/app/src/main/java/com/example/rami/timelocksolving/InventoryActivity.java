package com.example.rami.timelocksolving;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

/**
 * Inventory page with custom adapter
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
    InventoryAdapter mAdapter;
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

        //if (inventory.getPlayerClues() != null || inventory.getPlayerTokens() != null) {
            createUi();
        //}
    }

    private void createUi () {
        if (mCategory < 0) {
            ArrayList<Clue> items = create.getClueList();
            mAdapter = new InventoryAdapter(this, items);
        }
        else {
            ArrayList<Token> items = create.getTokensWithCategory(mCategory);
            mAdapter = new InventoryAdapter(this, items, mCategory);
        }

        setListAdapter(mAdapter);
    }

}
