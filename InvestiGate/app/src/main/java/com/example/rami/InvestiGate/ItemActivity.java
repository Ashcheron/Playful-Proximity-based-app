package com.example.rami.InvestiGate;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ItemActivity extends ActionBarActivity {

    Clue clue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        findItem();
        createUi();
    }

    /**
     * Find item from static list
     */
    private void findItem() {
        StaticItemList staticItemList = new StaticItemList(this);
        String id = getIntent().getStringExtra(getResources().getString(R.string.setting_keypair_clueId));
        if (!id.isEmpty())
            clue = staticItemList.findClueById(id);
    }

    /**
     * Find views and set texts
     */
    private void createUi() {
        TextView nameView = (TextView) findViewById(R.id.detailedItemLabel);
        TextView descriptionView = (TextView) findViewById(R.id.detailedItemDescription);
        TextView tokensView = (TextView) findViewById(R.id.detailedItemTokens);

        nameView.setText(clue.getClueTitle());
        descriptionView.setText(clue.getClueDescriptionLong());

        tokensView.setText(clue.getTokenString());


    }
}
