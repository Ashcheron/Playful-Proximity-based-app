package com.example.rami.InvestiGate;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class handling work related to Players inventory
 * All clues are handled with IDs
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

    /**
     * Adds clue from parameter to the player inventory.
     * @param clue Clue to be added to the players inventory
     */
    public void addClue(Clue clue) {
        // Using set causes the player to be able to have only unique clues
        Set <String> clues;
        clues = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerInventory),new HashSet<String>());
        String temp;

        //Workaround for bug in saving sets in sharedprefs
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerInventory));
        editor.commit();
        temp = clue.getClueId();

        // Add clue to SharedPrefs and save it
        clues.add(temp);
        mPrefs.edit().putStringSet(mContext.getResources().getString(R.string.setting_keypair_playerInventory), clues).commit();

        // Add token from the clue
        // Using set for tokens is advisable as they are only unique entries
        Set <String> tokens = mPrefs.getStringSet(mContext.getResources().getString(R.string.setting_keypair_playerTokens), new HashSet<String>());

        //Workaround for bug in saving sets in sharedprefs
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerTokens));
        editor.commit();

        for (int i = 0; i < clue.getTokens().size(); i++) {
            tokens.add(clue.getTokens().get(i).getName());
        }

        mPrefs.edit().putStringSet(mContext.getResources().getString(R.string.setting_keypair_playerTokens), tokens).commit();

    }

    /**
     * Retrieves players clues
     * @return Clue object arraylist
     */
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

    /**
     * Retrieves players tokens
     * @return Token object arraylist
     */
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

    /**
     * Searches players tokens according to the given category and returns matched as a list
     * @param category
     * @return Filtered token object arraylist
     */
    public ArrayList<Token> getPlayerTokensWithCategory(int category) {
        ArrayList<Token> playerTokens = getPlayerTokens();
        ArrayList<Token> filteredTokens = new ArrayList<>();

        for (int i = 0; i < playerTokens.size(); i++) {
            if (playerTokens.get(i).getTokenTYPE().equals(category))
                filteredTokens.add(playerTokens.get(i));
        }
        return filteredTokens;
    }

    /**
     * Clears players clues and tokens
     */
    public void removeInventory() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerInventory));
        editor.remove(mContext.getResources().getString(R.string.setting_keypair_playerTokens));
        editor.commit();
    }

    public boolean doesClueExist(Clue clue) {
        ArrayList<Clue> clues = getPlayerClues();
        Boolean exits = false;
        for (int i = 0; i < clues.size(); i++) {
            if (clues.get(i).equals(clue));
                exits = true;
        }

        return exits;
    }
}
