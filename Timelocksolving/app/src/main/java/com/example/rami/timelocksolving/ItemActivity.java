package com.example.rami.timelocksolving;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class ItemActivity extends ActionBarActivity {

    Clue clue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        findItem();
        createUi();
    }

    private void findItem() {
        StaticItemList staticItemList = new StaticItemList(this);
        String id = getIntent().getStringExtra(getResources().getString(R.string.setting_keypair_clueId));
        if (!id.isEmpty())
            clue = staticItemList.findClueById(id);
    }

    private void createUi() {
        TextView nameView = (TextView) findViewById(R.id.detailedItemLabel);
        TextView descriptionView = (TextView) findViewById(R.id.detailedItemDescription);
        TextView tokensView = (TextView) findViewById(R.id.detailedItemTokens);

        nameView.setText(clue.getClueTitle());
        descriptionView.setText(clue.getClueDescriptionLong());

        tokensView.setText(clue.getTokenString());


    }
}
