package com.example.rami.timelocksolving;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Main menu where you can access all of the screens
 *
 * NOTES:
 * SOLVE SCREEN, PRESS TO OPEN CATEGORY INVETORY -> UPDATE VIEW
 * CLUES -> LORE, CLUE HAS TOKENS, TOKENS USED TO CAST MURDERER
 */
public class MainMenuActivity extends ActionBarActivity {

    // Buttons for the menu
    Button solveButton;
    Button profileButton;
    Button inventoryButton;
    Button startButton;

    // Handler to use timed lock
    Handler mHandler;

    //
    boolean mIsRunning;
    int timer;
    SharedPreferences prefs;

    // Static variables
    final static int SOLVE_TIME_IN_SECONDS = 10;
    final static int TICK_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        prefs = PreferenceManager.getDefaultSharedPreferences(MainMenuActivity.this);
        firstTimePlaying();

        mHandler = new Handler();
        timer = 0;

        prepareActivities();
    }

    /**
     * Checks if player has started the game for first time and asks for username.
     */
    private void firstTimePlaying() {
        Boolean firstTime = prefs.getBoolean(getResources().getString(R.string.setting_keypair_firstTime), true);

        if (firstTime) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            final EditText edittext= new EditText(this);
            alert.setMessage("Enter your player name");
            alert.setTitle("Enter name");

            alert.setView(edittext);

            alert.setNegativeButton("Use Default", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    prefs.edit().putString(getResources().getString(R.string.setting_keypair_playerName), "Neew Player").commit();
                    prefs.edit().putBoolean(getResources().getString(R.string.setting_keypair_firstTime),false).commit();
                }
            });

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    String YouEditTextValue = edittext.getText().toString();
                    prefs.edit().putString(getResources().getString(R.string.setting_keypair_playerName), YouEditTextValue).commit();
                    prefs.edit().putBoolean(getResources().getString(R.string.setting_keypair_firstTime), false).commit();

                }
            });

            alert.show();


        }
        else {
            String playerName = prefs.getString("playerName", "PlayerName");
        }
    }

    /**
     * Handles creating buttons, intents and onClickListeners to change between screens
     */
    private void prepareActivities() {
        // Buttons here
        solveButton = (Button) findViewById(R.id.solveButton);
        profileButton = (Button) findViewById(R.id.profileButton);
        inventoryButton = (Button) findViewById(R.id.inventoryButton);
        startButton = (Button) findViewById(R.id.startButton);

        // Intents here
        final Intent intentProfile = new Intent(MainMenuActivity.this, ProfileActivity.class);
        final Intent intentInventory = new Intent(MainMenuActivity.this, InventoryActivity.class);
        final Intent intentSolve = new Intent(MainMenuActivity.this, SolveActivity.class);

        // Passing variables
        //intentInventory.putExtra(getResources().getString(R.string.inventory_category),99);

        // Disable solve button
        //solveButton.setEnabled(false);

        // OnClickListerners for each button

        // For profile
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Profile button", "Pressed");
                startActivity(intentProfile);
            }
        });

        // For inventory
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Inventory button", "Pressed");
                startActivity(intentInventory);
            }
        });

        // For solve
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isEnabled()) {
                    Log.d("Solve button", "Pressed");
                    startActivity(intentSolve);
                    startButton.setEnabled(true);
                    startButton.setVisibility(View.VISIBLE);
                }
            }
        });

        // For Start
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isEnabled()) {
                    Log.d("Start button", "Pressed");
                    startTimer(SOLVE_TIME_IN_SECONDS);
                    startButton.setEnabled(false);
                    startButton.setVisibility(View.INVISIBLE);
                }

            }
        });

        // Trading etc... WIP
    }

    /**
     * Enables solve button and stops timer to conserve resources
     */
    public void enableSolving() {
        Toast.makeText(MainMenuActivity.this,"You can solve now!", Toast.LENGTH_SHORT).show();
        solveButton.setEnabled(true);
        stopTimer();

        Log.d("Button", "Enabled");
    }

    /**
     * Stops timer by removing callbacks
     */
    public void stopTimer() {
        mIsRunning = false;
        mHandler.removeCallbacks(mRunnable);
    }

    /**
     * Starts timer for defined duration.
     * @param timeBeforeSolve Seconds required to pass for solve button enabling
     */
    public void startTimer(int timeBeforeSolve) {
        mIsRunning = true;
        timer = timeBeforeSolve;
        mRunnable.run();
    }


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // If return is not invoked, the timer will keep ticking
            if (!mIsRunning) {
                return;
            }

            doWork();

            Log.d("Handlers", "Called");

            // Repeat this runnable code block again every second
            mHandler.postDelayed(mRunnable, TICK_INTERVAL);
        }
    };


    /**
     * This method is invoked every time the timer "ticks"
     */
    public void doWork() {
        if (timer <= 0) {
            enableSolving();
            solveButton.setText(getString(R.string.solver));
        }

        else {
            timer--;
            solveButton.setText("Solve in: " + timer);
        }
    }

}
