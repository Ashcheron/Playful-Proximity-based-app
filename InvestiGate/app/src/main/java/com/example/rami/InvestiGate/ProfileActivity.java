package com.example.rami.InvestiGate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Profile page
 */
public class ProfileActivity extends ActionBarActivity {

    TextView nameView;
    TextView bioView;
    EditText nameEdit;
    EditText bioEdit;

    MenuItem menuItemSave;
    MenuItem menuItemEdit;

    Boolean mEditing;
    String mSettingPlayerName;
    String mSettingPlayerBio;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mSettingPlayerName = getResources().getString(R.string.setting_keypair_playerName);
        mSettingPlayerBio = getResources().getString(R.string.setting_keypair_playerBio);

        prefs = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);

        getAllViews();

        disableEditing();

        getPlayerDetails();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        menuItemEdit = menu.findItem(R.id.profileEdit);

        menuItemSave = menu.findItem(R.id.profileSave);
        menuItemSave.setVisible(false);

        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileEdit:
                if (!mEditing) {
                    menuItemEdit.setTitle("Cancel");
                    enableEditing();
                    menuItemSave.setVisible(true);
                }
                else {
                    menuItemEdit.setTitle("Edit");
                    disableEditing();
                    menuItemSave.setVisible(false);
                }
                return true;
            case R.id.profileSave:
                savePlayerDetail();

                menuItemSave.setVisible(false);
                menuItemEdit.setTitle("Edit");
                disableEditing();
                return true;
            default:
                return true;
        }
    }

    /**
     * Retrieve and assign views
     */
    public void getAllViews() {

        nameView = (TextView) findViewById(R.id.playerName);
        bioView = (TextView) findViewById(R.id.biography);

        nameEdit = (EditText) findViewById(R.id.playerNameEdit);
        bioEdit = (EditText) findViewById(R.id.biographyEdit);

    }

    /**
     * Read player details from shared preferences
     */
    private void getPlayerDetails() {
        String playerName = prefs.getString(mSettingPlayerName,"PlayerName");
        String playerBiography = prefs.getString(mSettingPlayerBio,"...");

        nameView.setText(playerName);
        bioView.setText(playerBiography);

        nameEdit.setText(playerName);
        bioEdit.setText(playerBiography);
    }

    /**
     * Save edited player details to shared preferences
     */
    private void savePlayerDetail() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(mSettingPlayerName, nameEdit.getText().toString());
        editor.putString(mSettingPlayerBio, bioEdit.getText().toString());
        editor.commit();
    }

    /**
     * Hides textviews and shows edit texts
     */
    private void enableEditing() {
        mEditing = true;

        nameEdit.setVisibility(View.VISIBLE);
        nameEdit.setEnabled(true);

        bioEdit.setVisibility(View.VISIBLE);
        bioEdit.setEnabled(true);

        nameView.setVisibility(View.INVISIBLE);
        nameView.setEnabled(false);

        bioView.setVisibility(View.INVISIBLE);
        bioView.setEnabled(false);
    }

    /**
     * Shows textviews and hides edit texts
     */
    private void disableEditing() {
        mEditing = false;

        nameEdit.setVisibility(View.INVISIBLE);
        nameEdit.setEnabled(false);

        bioEdit.setVisibility(View.INVISIBLE);
        bioEdit.setEnabled(false);

        nameView.setVisibility(View.VISIBLE);
        nameView.setEnabled(true);

        bioView.setVisibility(View.VISIBLE);
        bioView.setEnabled(true);
    }


}
