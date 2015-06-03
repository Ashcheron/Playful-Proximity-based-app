package com.example.rami.InvestiGate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EndingActivity extends ActionBarActivity {

    SharedPreferences mPrefs;
    final static int TEXT_INDEX = 1;
    final static int TITLE_INDEX = 0;
    int mEndingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(EndingActivity.this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mEndingId = extras.getInt("ENDING");
            createUi();
        }

    }

    private void createUi() {
        TextView title = (TextView) findViewById(R.id.endingTitle);
        TextView text = (TextView) findViewById(R.id.endingText);
        Button reset = (Button) findViewById(R.id.returnReset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndingActivity.this,MainMenuActivity.class);
                startActivity(intent);
            }
        });

        TypedArray ta = getResources().obtainTypedArray(R.array.demo_ending_index);
        int arrayId;

        arrayId = ta.getResourceId(mEndingId, -1);
        if (arrayId >= 0) {
            String[] ending = getResources().getStringArray(arrayId);

            title.setText(ending[TITLE_INDEX]);
            text.setText(ending[TEXT_INDEX]);
        }

        mPrefs.edit().putBoolean(getResources().getString(R.string.setting_keypair_completed),true).commit();
    }

}
