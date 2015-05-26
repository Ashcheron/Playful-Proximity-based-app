package com.example.rami.timelocksolving;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rami on 20.5.2015.
 */
public class PlayerInventory {
    SharedPreferences mPrefs;
    Context mContext;
    StaticItemList items;

    public PlayerInventory(Context context) {
        mContext = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        items = new StaticItemList(mContext);
    }

    public void addClue(Clue clue) {
        Set <String> clues = new HashSet<>();
        clues = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerInventory),null);
        String temp;

        //Workaround for bug in saving sets in sharedprefs
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerInventory));
        editor.commit();
        temp = clue.getClueId();

        clues.add(temp);

        mPrefs.edit().putStringSet(mContext.getResources().getString(R.string.setting_keypair_playerInventory), clues).commit();

        Set <String> tokens = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerTokens), null);

        //Workaround for bug in saving sets in sharedprefs
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerTokens));
        editor.commit();

        for (int i = 0; i < clue.getTokens().size(); i++) {
            tokens.add(clue.getTokens().get(i).getName());
        }

        mPrefs.edit().putStringSet(mContext.getResources().getString(R.string.setting_keypair_playerTokens), tokens).commit();

    }

    public ArrayList<Clue> getPlayerClues() {
        Set<String> temp;
        ArrayList<Clue> playerClues = null;
        Clue clue = null;
        String[] clues = null;

        temp = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerInventory),null);
        if (temp == null)
            return null;
        temp.toArray(clues);

        if (clues != null) {
            for (int i = 0; i < clues.length; i++) {
                clue = items.findClueById(clues[i]);
                if (clue != null) {
                    playerClues.add(clue);
                }
            }
        }
        return playerClues;

    }

    public ArrayList<Token> getPlayerTokens() {
        Set<String> temp;
        ArrayList<Token> playerTokens = null;
        Token token = null;
        String[] tokens = null;

        temp = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerTokens), null);

        if (temp == null)
            return null;

        temp.toArray(tokens);

        if (tokens != null) {
            for (int i = 0; i < tokens.length; i++) {
                token = items.findTokenByName(tokens[i]);
                if (token != null) {
                    playerTokens.add(token);
                }
            }
        }
        return playerTokens;

    }

    public ArrayList<Token> getPlayerTokensWithCategory(int category) {
        ArrayList<Token> playerTokens = getPlayerTokens();
        ArrayList<Token> filteredTokens = new ArrayList<>();

        for (int i = 0; i < playerTokens.size(); i++) {
            if (playerTokens.get(i).getTokenTYPE().equals(category))
                filteredTokens.add(playerTokens.get(i));
        }
        return filteredTokens;
    }
}
