package com.example.rami.InvestiGate;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Main menu where you can access all of the screens
 */
public class MainMenuActivity extends ActionBarActivity {

    // Buttons for the menu
    Button solveButton;
    Button profileButton;
    Button inventoryButton;
    Button startButton;
    Button resetButton;

    // Handler to use timed lock
    Handler mHandler;

    // Variables
    boolean mIsRunning;
    int timer;
    int[] clueTimer;
    int generatedIndex;
    int clueTimerIndex;
    int currentTimeSeconds;
    int currentTimeMinutes;
    int currentTimeHours;
    Integer[] generatedClueOrder;
    SharedPreferences prefs;
    PlayerInventory mInventory;
    StaticItemList staticItemList;

    // Static variables

    // Settings for solve timer, alter these if you want
    final static int SOLVE_TIME_IN_HOURS = 1;
    final static int SOLVE_TIME_IN_MINUTES = 20;
    final static int SOLVE_TIME_IN_SECONDS = 0;

    // Converts previous values to seconds  !!!! DO NOT TOUCH THESE !!!!
    final static int SOLVE_TIME = SOLVE_TIME_IN_HOURS * 3600 + SOLVE_TIME_IN_MINUTES * 60 + SOLVE_TIME_IN_SECONDS;

    // Tick interval in ms
    final static int TICK_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        prefs = PreferenceManager.getDefaultSharedPreferences(MainMenuActivity.this);
        mInventory = new PlayerInventory(this);
        staticItemList = new StaticItemList(this);

        mHandler = new Handler();
        timer = 0;

        firstTimePlaying();
        prepareActivities();

        generatedIndex = 0;
        clueTimerIndex = 0;

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
            // Just a greeting with usersname
            String playerName = prefs.getString("playerName", "PlayerName");
            Toast.makeText(this,"Welcome back " + playerName,Toast.LENGTH_SHORT).show();
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
        resetButton = (Button) findViewById(R.id.resetButton);

        // Intents here
        final Intent intentProfile = new Intent(MainMenuActivity.this, ProfileActivity.class);
        final Intent intentInventory = new Intent(MainMenuActivity.this, InventoryActivity.class);
        final Intent intentSolve = new Intent(MainMenuActivity.this, SolveActivity.class);

        // Disable and hide solve
        solveButton.setEnabled(false);
        // Check for game completion
        isGameCompleted();

        // Click listeners for each button

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
                }
            }
        });

        // For Start
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isEnabled()) {
                    Log.d("Start button", "Pressed");

                    timer = 0;
                    generatedIndex = 0;
                    clueTimerIndex = 0;

                    generateClueFindOrder();
                    generateTimingArrayForClues();

                    startTimer(SOLVE_TIME);
                    startButton.setVisibility(View.INVISIBLE);
                    startButton.setEnabled(false);
                    solveButton.setEnabled(false);
                }

            }
        });

        // For reset
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset player inventory etc.

                generatedIndex = 0;
                clueTimerIndex = 0;

                generateClueFindOrder();
                generateTimingArrayForClues();

                mInventory.removeInventory();

                resetButton.setEnabled(false);
                resetButton.setVisibility(View.INVISIBLE);

                startButton.setEnabled(true);
                startButton.setVisibility(View.VISIBLE);

                // Change completed value from preferences
                prefs.edit().putBoolean(getResources().getString(R.string.setting_keypair_completed),false).commit();
            }
        });

        // Trading etc... WIP
    }

    /**
     * Checks settings if the game has been completed.
     * Used to enable reset button and disable start button.
     */
    private void isGameCompleted() {
        Boolean completed = prefs.getBoolean(getResources().getString(R.string.setting_keypair_completed), false);

        if (completed) {
            startButton.setEnabled(false);
            startButton.setVisibility(View.INVISIBLE);
            resetButton.setEnabled(true);
            resetButton.setVisibility(View.VISIBLE);
        }
        else {
            startButton.setEnabled(true);
            startButton.setVisibility(View.VISIBLE);
            resetButton.setEnabled(false);
            resetButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Enables solve button and stops timer to conserve resources
     */
    public void enableSolving() {
        Toast.makeText(MainMenuActivity.this,"You can solve now!", Toast.LENGTH_SHORT).show();
        solveButton.setEnabled(true);
        solveButton.setVisibility(View.VISIBLE);
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
            if (timer == clueTimer[clueTimerIndex]) {
                addClue();
                clueTimerIndex++;
            }
            timer--;

            if (timer >= 3600) {
                currentTimeHours = timer / 3600;
                solveButton.setText("Solve in: " + currentTimeHours + "h");
            }
            else if (timer >= 60) {
                currentTimeMinutes = timer / 60;
                solveButton.setText("Solve in: " + currentTimeMinutes + "min");
            }
            else {
                currentTimeSeconds = timer;
                solveButton.setText("Solve in: " + currentTimeSeconds + "sec");
            }
        }
    }

    /**
     * Creates timing table for timer to give out clues
     *                  !!! DANGER !!!
     * WITH FEW SECONDS LONG TIMER CAN CAUSE UNFORSEEN PROBLEMS
     *
     * INTENDED TO BE USED FOR LONGER TIMES THAN [CLUE COUNT * TICK INTERVAL] SECONDS
     */
    private void generateTimingArrayForClues() {
        int a = SOLVE_TIME / staticItemList.getClueList().size();
        int count = 0;
        clueTimer = new int[staticItemList.getClueList().size() + 1];
        for (int i = staticItemList.getClueList().size(); i >= 0 ; i--) {
            clueTimer[i] = count;
            count += a;
        }
    }

    /**
     * Generates random order in which the clues are received
     */
    private void generateClueFindOrder() {
        Random rng = new Random();
        Set<Integer> generated = new LinkedHashSet<Integer>();

        while (generated.size() < staticItemList.getClueList().size()) {
            Integer next = rng.nextInt(staticItemList.getClueList().size());

            generated.add(next);
            Log.d("Generated Clue order", next.toString());

        }
        generatedClueOrder = new Integer[generated.size()];
        generated.toArray(generatedClueOrder);
    }

    /**
     * Adds clue to players inventory and prompts with toast
     */
    private void addClue() {
        Clue clue = staticItemList.getClueList().get(generatedClueOrder[generatedIndex]);
        Toast.makeText(MainMenuActivity.this,"You have received a clue",Toast.LENGTH_SHORT).show();
        mInventory.addClue(clue);
        generatedIndex++;
    }

}
