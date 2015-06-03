package com.example.rami.InvestiGate;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * For future use
 */
public class AppPreferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }
}
