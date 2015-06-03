package com.example.rami.InvestiGate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Acts as a start screen
 */

public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startApp();
    }

    private void startApp()
    {
        Button startButton = (Button) findViewById(R.id.startButton);
        Button creditButton = (Button) findViewById(R.id.creditsButton);

        final Intent startIntent = new Intent(this,MainMenuActivity.class);
        final Intent creditsIntent = new Intent(this,CreditsActivity.class);

        startButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(startIntent);
            }
        });
        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(creditsIntent);
            }
        });
    }
}