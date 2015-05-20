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

    public Clue (String id, String title, String descLong, String descShort, ArrayList<Token> tokens) {
        clueId = id;
        clueTitle = title;
        clueDescriptionLong = descLong;
        clueDescriptionShort = descShort;
        clueTokens = tokens;
    }

    public String getClueId() {
        return clueId;
    }

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
