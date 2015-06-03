package com.example.rami.InvestiGate;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;

/**
 * Created by Rami on 18.5.2015.
 *
 *
 Suspects: 16
 Richard - 7
 Janet - 6
 Marc - 3

 Locations: 15
 Computer Room: 4
 Workshop: 7
 Smoking Area:  4

 Murder Weapons: 10
 Laptop: 2
 Robots Right Arm: 3
 Cracked Broom: 2
 Hammer: 2
 Sports Award: 1

 */
public class StaticItemList {

    ArrayList<Token> tokenList;
    ArrayList<Clue> clueList;

    static int ID = 0;
    static int TITLE_INDEX = 1;
    static int LONG_DESC_INDEX = 2;
    static int SHORT_DESC_INDEX = 3;
    static int TOKENS_START_INDEX = 4;

    /**
     * Creates static item list. Meant to be used for comparison.
     * @param mContext Context
     */
    public StaticItemList(Context mContext) {
        tokenList = new ArrayList<>();
        clueList = new ArrayList<>();
        fillEvidenceList(mContext);
        fillClues(mContext);
    }

    private void fillEvidenceList(Context context) {
        // CHANGE TO RESOURCES FILE!!!
        tokenList.add(new Token(0,context.getResources().getString(R.string.suspect_richard),0));
        tokenList.add(new Token(1,context.getResources().getString(R.string.suspect_janet),0));
        tokenList.add(new Token(2,context.getResources().getString(R.string.suspect_marc),0));

        tokenList.add(new Token(3,context.getResources().getString(R.string.location_computer_room),2));
        tokenList.add(new Token(4,context.getResources().getString(R.string.location_workshop),2));
        tokenList.add(new Token(5,context.getResources().getString(R.string.location_smoking_area),2));

        tokenList.add(new Token(6,context.getResources().getString(R.string.weapon_laptop),1));
        tokenList.add(new Token(7,context.getResources().getString(R.string.weapon_robot_arm),1));
        tokenList.add(new Token(8,context.getResources().getString(R.string.weapon_cracked_broom),1));
        tokenList.add(new Token(9,context.getResources().getString(R.string.weapon_hammer), 1));
        tokenList.add(new Token(10,context.getResources().getString(R.string.weapon_award), 1));

    }

    /**
     * Retrieve clues from resource file and add them to a arraylist
     * @param mContext
     */
    private void fillClues(Context mContext) {
        TypedArray ta = mContext.getResources().obtainTypedArray(R.array.demo_clues_index);
        int arrayLength = ta.length();
        int arrayId;

        for (int i = 0; i < arrayLength; i++) {
            arrayId = ta.getResourceId(i, -1);
            clueList.add(parseCluesToObject(mContext.getResources().getStringArray(arrayId)));
        }
    }

    /**
     * Used to parse clues to proper objects
     * @param values
     * @return Clue object
     */
    private Clue parseCluesToObject(String[] values) {
        String id = values[ID];
        String title = values[TITLE_INDEX];
        String descLong = values[LONG_DESC_INDEX];
        String descShort = values[SHORT_DESC_INDEX];
        ArrayList<Token> tokens = new ArrayList<>();

        for (int i = TOKENS_START_INDEX; i < values.length; i++) {
            String temp = values[i];
            for (int j = 0; j < tokenList.size(); j++) {
                if(temp.equals(tokenList.get(j).getName())) {
                    tokens.add(tokenList.get(j));
                    break;
                }
            }
        }

        Clue clue = new Clue(id, title, descLong, descShort, tokens);
        return clue;
    }

    /**
     *
     * @return Arraylist <Token>
     */
    public ArrayList<Token> getTokenList() {
        return tokenList;
    }

    /**
     *
     * @return Arraylist <Clue>
     */
    public ArrayList<Clue> getClueList() {
        return clueList;
    }

    /**
     *
     * @param id String id of clue
     * @return Clue object
     */
    public Clue findClueById(String id) {
        Clue clue = null;
        for (int i = 0; i < clueList.size(); i++) {
            if (clueList.get(i).getClueId().equals(id)) {
                clue = clueList.get(i);
            }
        }

        return clue;
    }

    /**
     * Retrieves tokens with category
     * @param category
     * @return ArrayList<Token>
     */
    public ArrayList<Token> getTokensWithCategory(int category) {
        ArrayList<Token> temp = new ArrayList<>();

        for (int i = 0; i < tokenList.size(); i++) {
            if (tokenList.get(i).getTokenTYPE().equals(category))
                temp.add(tokenList.get(i));
        }
        return temp;
    }

    public Token findTokenByName(String name) {
        Token token = null;
        for (int i = 0; i < tokenList.size(); i ++) {
            if (tokenList.get(i).getName().equals(name)) {
                token = tokenList.get(i);
            }
        }
        return token;
    }
}
