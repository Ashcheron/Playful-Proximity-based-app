package com.example.rami.timelocksolving;

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
public class Profile extends ActionBarActivity {

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

        mSettingPlayerName = getResources().getString(R.string.setting_value_playerName);
        mSettingPlayerBio = getResources().getString(R.string.setting_value_playerBio);

        prefs = PreferenceManager.getDefaultSharedPreferences(Profile.this);

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

    public void getAllViews() {

        nameView = (TextView) findViewById(R.id.playerName);
        bioView = (TextView) findViewById(R.id.biography);

        nameEdit = (EditText) findViewById(R.id.playerNameEdit);
        bioEdit = (EditText) findViewById(R.id.biographyEdit);

    }

    private void getPlayerDetails() {
        String playerName = prefs.getString(mSettingPlayerName,"PlayerName");
        String playerBiography = prefs.getString(mSettingPlayerBio,"...");

        nameView.setText(playerName);
        bioView.setText(playerBiography);

        nameEdit.setText(playerName);
        bioEdit.setText(playerBiography);
    }

    private void savePlayerDetail() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(mSettingPlayerName,nameEdit.getText().toString());
        editor.putString(mSettingPlayerBio, bioEdit.getText().toString());
        editor.commit();
    }

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
