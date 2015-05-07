package com.example.rami.timelocksolving;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Inventory page with custom adapter
 */

public class Inventory extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = new String[] { "Knife", "Fingerprint", "Autopsy" };
        InventoryAdapter adapter = new InventoryAdapter(this,items);
        setListAdapter(adapter);
    }
}
