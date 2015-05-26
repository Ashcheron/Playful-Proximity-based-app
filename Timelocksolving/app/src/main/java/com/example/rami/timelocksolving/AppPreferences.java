package com.example.rami.timelocksolving;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Rami on 26.5.2015.
 */
public class AppPreferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }
}
