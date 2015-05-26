package com.example.rami.timelocksolving;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Ending extends ActionBarActivity {

    SharedPreferences mPrefs;
    final static int TEXT_INDEX = 1;
    final static int TITLE_INDEX = 0;
    int mEndingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mEndingId = extras.getInt("ENDING");
            createUi();
        }


    }

    private void createUi() {
        TextView title = (TextView) findViewById(R.id.endingTitle);
        TextView text = (TextView) findViewById(R.id.endingText);

        TypedArray ta = getResources().obtainTypedArray(R.array.demo_ending_index);
        int arrayId;

        arrayId = ta.getResourceId(mEndingId, -1);
        if (arrayId >= 0) {
            String[] ending = getResources().getStringArray(arrayId);

            title.setText(ending[TITLE_INDEX]);
            text.setText(ending[TEXT_INDEX]);
        }
    }

}
