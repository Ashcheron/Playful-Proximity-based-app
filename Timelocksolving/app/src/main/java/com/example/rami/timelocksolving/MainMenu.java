package com.example.rami.timelocksolving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainMenu extends Activity {

    Button solveButton;
    Button profileButton;
    Button inventoryButton;
    Handler mHandler;


    boolean mIsRunning;
    int timer;

    final static int SOLVE_TIME_IN_SECONDS = 10;
    final static int TICK_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        mHandler = new Handler();
        timer = 0;

        prepareActivities();

    }

    private void prepareActivities() {
        // Buttons here
        solveButton = (Button) findViewById(R.id.solve);
        profileButton = (Button) findViewById(R.id.profile);
        inventoryButton = (Button) findViewById(R.id.inventory);

        // Intents here
        final Intent intentProfile;
        final Intent intentInventory = new Intent(MainMenu.this,Inventory.class);

        // Listeners here
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isEnabled()) {
                    Toast.makeText(MainMenu.this, "Resetting", Toast.LENGTH_SHORT).show();

                    v.setEnabled(false);
                    startTimer();

                    Log.d("Button", "Disabled");
                }
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "WIP", Toast.LENGTH_SHORT).show();
            }
        });

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentInventory);
            }
        });

    }

    public void enableSolving() {
        Toast.makeText(MainMenu.this,"You can solve now!", Toast.LENGTH_SHORT).show();
        solveButton.setEnabled(true);
        stopTimer();

        Log.d("Button","Enabled");
    }

    public void stopTimer() {
        mIsRunning = false;
        mHandler.removeCallbacks(mRunnable);
    }

    public void startTimer() {
        mIsRunning = true;
        timer = SOLVE_TIME_IN_SECONDS;
        mRunnable.run();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mIsRunning) {
                return;
            }

            doWork();

            Log.d("Handlers", "Called");

            // Repeat this runnable code block again every second
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

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
