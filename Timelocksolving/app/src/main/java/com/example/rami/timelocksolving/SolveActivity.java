package com.example.rami.timelocksolving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SolveActivity extends Activity {

    TextView mSuspect;
    TextView mWeapon;
    TextView mLocation;
    Button mPresent;

    static final int REQUEST_SUSPECT = 1;
    static final int REQUEST_WEAPON = 2;
    static final int REQUEST_LOCATION = 3;

    String inventoryCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        inventoryCategory = getResources().getString(R.string.inventory_category);

        prepareSolving();
    }

    private void prepareSolving() {
        // Find views
        mSuspect = (TextView) findViewById(R.id.suspect);
        mWeapon = (TextView) findViewById(R.id.weapon);
        mLocation = (TextView) findViewById(R.id.location);
        mPresent = (Button) findViewById(R.id.presentEvidence);

        // Define Intent
        final Intent inventoryActivity = new Intent(SolveActivity.this, InventoryActivity.class);
        final Intent endingActivity;

        // Click listeners

        // Suspect with mCategory
        mSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Suspect", "Clicked");
                inventoryActivity.putExtra(inventoryCategory, 0);
                startActivityForResult(inventoryActivity, REQUEST_SUSPECT);
            }
        });

        // Weapon with mCategory
        mWeapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Weapon", "Clicked");
                inventoryActivity.putExtra(inventoryCategory, 1);
                startActivityForResult(inventoryActivity, REQUEST_WEAPON);
            }
        });

        // Location with mCategory
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Location", "Clicked");
                inventoryActivity.putExtra(inventoryCategory, 2);
                startActivityForResult(inventoryActivity, REQUEST_LOCATION);
            }
        });

        // Present Evidence
        mPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Present evidence", "Clicked");

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name = null;

        if (data != null) {
            name = data.getStringExtra("TITLE");
        }

        switch (requestCode){
            case REQUEST_SUSPECT:
                mSuspect.setText(name);
                break;
            case REQUEST_WEAPON:
                mWeapon.setText(name);
                break;
            case REQUEST_LOCATION:
                mLocation.setText(name);
                break;

        }
    }
}
