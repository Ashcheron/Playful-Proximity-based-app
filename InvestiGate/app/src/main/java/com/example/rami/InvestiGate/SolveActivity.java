package com.example.rami.InvestiGate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SolveActivity extends Activity {
    // View objects
    TextView mSuspect;
    TextView mWeapon;
    TextView mLocation;
    Button mPresent;

    // Variables
    String mSuspectName;
    String mWeaponName;
    String mLocationName;
    String inventoryCategory;
    int mSuspectId;
    int mWeaponId;
    int mLocationId;
    boolean mSuspectSelected;
    boolean mWeaponSelected;
    boolean mLocationSelected;

    Context mContext;

    // Static variables
    static final int REQUEST_SUSPECT = 1;
    static final int REQUEST_WEAPON = 2;
    static final int REQUEST_LOCATION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        mSuspectSelected = false;
        mWeaponSelected = false;
        mLocationSelected = false;

        inventoryCategory = getResources().getString(R.string.inventory_category);
        mContext = this;

        prepareSolving();
    }

    /**
     * Prepares View functionalities
     */
    private void prepareSolving() {
        // Find views
        mSuspect = (TextView) findViewById(R.id.suspect);
        mWeapon = (TextView) findViewById(R.id.weapon);
        mLocation = (TextView) findViewById(R.id.location);
        mPresent = (Button) findViewById(R.id.presentEvidence);

        // Define Intent
        final Intent inventoryActivity = new Intent(SolveActivity.this, InventoryActivity.class);
        final Intent endingActivity = new Intent(SolveActivity.this, EndingActivity.class);

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

                // Confirm dialog to confirm users intention
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                AlertDialog alert = builder.create();

                alert.setMessage("Are you sure?");
                alert.setTitle("Confirm");

                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                        Log.d("Confirm dialog", "Cancelled");
                    }
                });

                alert.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // If all of the categories are selected, user is allowed to see ending
                        if (mSuspectSelected && mWeaponSelected && mLocationSelected) {
                            Log.d("Confirm dialog", "Confirmed");
                            int endId = endingHandler();
                            endingActivity.putExtra("ENDING", endId);

                            startActivity(endingActivity);
                        }
                        // Otherwise cancel dialog
                        else {
                            dialog.cancel();
                            Log.e("Confirm dialog", "Missing token category");
                            Toast.makeText(mContext,"Missing token in one or several categories!",Toast.LENGTH_LONG);
                        }
                    }
                });

                alert.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title = null;
        int tokenId = -1;

        if (data != null) {
            title = data.getStringExtra("TITLE");
            tokenId = data.getIntExtra(getResources().getString(R.string.setting_keypair_tokenId), -1);
        }

        switch (requestCode){
            case REQUEST_SUSPECT:
                mSuspect.setText(title);
                mSuspectName = title;
                mSuspectId = tokenId;
                mSuspectSelected = true;
                break;
            case REQUEST_WEAPON:
                mWeapon.setText(title);
                mWeaponName = title;
                mWeaponId = tokenId;
                mWeaponSelected = true;
                break;
            case REQUEST_LOCATION:
                mLocation.setText(title);
                mLocationName = title;
                mLocationId = tokenId;
                mLocationSelected = true;
                break;

        }
    }

    /**
     * Checks what ending is displayed for the tokens selected
     * @return Integer
     */
    private int endingHandler() {
        String suspect = getResources().getString(R.string.demo_suspect);
        String location = getResources().getString(R.string.demo_location);
        if (goodEnding(suspect, location)) {
            return 0;
        }
        else if (notEnding(suspect, location)) {
            return 1;
        }
        else if (badEnding(suspect, location)) {
            return 2;
        }
        else
            return -1;
    }

    private boolean goodEnding(String suspect, String location) {
        if (suspect.equals(mSuspectName) && location.equals(mLocationName))
            return true;
        else
            return false;
    }

    private boolean notEnding(String suspect, String location) {
        if (suspect.equals(mSuspectName) && !location.equals(mLocationName))
            return true;
        else
            return false;
    }

    private boolean badEnding(String suspect, String location) {
       if (!suspect.equals(mSuspectName))
            return true;
       else
            return false;
    }

}
