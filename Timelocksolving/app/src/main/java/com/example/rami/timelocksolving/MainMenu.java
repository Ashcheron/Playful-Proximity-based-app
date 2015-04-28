package com.example.rami.timelocksolving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainMenu extends Activity {

    Button solveButton;
    Button profileButton;
    Button inventoryButton;
    private Timer mTimer;

    final static int SOLVE_TIME_IN_SECONDS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        mTimer = null;

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
                    reload();
                }
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentInventory);
            }
        });


    }

    public void reload() {
        resetTimer();

        mTimer = new Timer();

        checkTime();
    }

    public void resetTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
    }

    public void checkTime() {
        mTimer.schedule(new TimerTask() {


            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainMenu.this, "You can solve now!", Toast.LENGTH_LONG).show();
                        solveButton.setEnabled(true);
                    }
                });
            }
        }, SOLVE_TIME_IN_SECONDS * 1000);
    }

}
