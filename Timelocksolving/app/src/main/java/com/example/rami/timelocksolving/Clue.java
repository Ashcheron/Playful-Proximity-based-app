package com.example.rami.timelocksolving;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rami on 19.5.2015.
 */
public class Clue {
    String clueId;
    String clueTitle;
    String clueDescriptionLong;
    String clueDescriptionShort;
    ArrayList<Token> clueTokens;

    /**
     *
     * @param id String id
     * @param title Name/Title
     * @param descLong Long text
     * @param descShort Short, one line worth of text
     * @param tokens Tokens
     */
    public Clue (String id, String title, String descLong, String descShort, ArrayList<Token> tokens) {
        clueId = id;
        clueTitle = title;
        clueDescriptionLong = descLong;
        clueDescriptionShort = descShort;
        clueTokens = tokens;
    }

    /**
     * Retrieve clues String id
     * @return Id
     */
    public String getClueId() {
        return clueId;
    }

    /**
     * Retrieve clues name/title
     * @return Title
     */
    public String getClueTitle() {
        return clueTitle;
    }

    public String getGetClueDescriptionShort() {
        return clueDescriptionShort;
    }

    public String getClueDescriptionLong() {
        return clueDescriptionLong;
    }

    public ArrayList<Token> getTokens() {
        return clueTokens;
    }

    /**
     * Retrieves clues tokens as string array
     * @return String array
     */
    public String getTokenString() {
        String tokenString = "";

        for (int i = 0; i < clueTokens.size(); i++) {
            tokenString += clueTokens.get(i).getName();
            if (i < clueTokens.size() - 1)
                tokenString += ", ";
        }

        return tokenString;
    }
}
