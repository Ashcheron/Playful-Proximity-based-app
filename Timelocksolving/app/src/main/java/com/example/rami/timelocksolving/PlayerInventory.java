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
    StaticItemList staticItems;

    public PlayerInventory(Context context) {
        mContext = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        staticItems = new StaticItemList(mContext);
    }

    public void addClue(Clue clue) {
        Set <String> clues;
        clues = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerInventory),new HashSet<String>());
        String temp;

        //Workaround for bug in saving sets in sharedprefs
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerInventory));
        editor.commit();
        temp = clue.getClueId();

        clues.add(temp);

        mPrefs.edit().putStringSet(mContext.getResources().getString(R.string.setting_keypair_playerInventory), clues).commit();

        Set <String> tokens = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerTokens), new HashSet<String>());

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
        ArrayList<Clue> playerClues = new ArrayList<Clue>();
        Clue clue;

        temp = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerInventory),new HashSet<String>());

        if (temp == null)
            return null;

        String[] clues = new String[temp.size()];
        temp.toArray(clues);

        if (clues != null) {
            for (int i = 0; i < clues.length; i++) {
                clue = staticItems.findClueById(clues[i]);
                if (clue != null) {
                    playerClues.add(clue);
                }
            }
        }
        return playerClues;

    }

    public ArrayList<Token> getPlayerTokens() {
        Set<String> temp;
        ArrayList<Token> playerTokens = new ArrayList<Token>();
        Token token;

        temp = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerTokens), new HashSet<String>());

        if (temp == null)
            return null;

        String[] tokens = new String[temp.size()];
        temp.toArray(tokens);

        if (tokens != null) {
            for (int i = 0; i < tokens.length; i++) {
                token = staticItems.findTokenByName(tokens[i]);
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

    public void removeInventory() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerInventory));
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerTokens));
        editor.commit();
    }
}
