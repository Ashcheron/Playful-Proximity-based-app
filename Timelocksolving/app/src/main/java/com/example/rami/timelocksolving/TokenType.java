package com.example.rami.timelocksolving;

/**
 * Created by Rami on 18.5.2015.
 */
public enum TokenType {
    SUSPECT("Suspect", 0),
    WEAPON("Weapon", 1),
    LOCATION("Location",2),
    MISC("Miscellaneous",3);

    private String stringValue;
    private int intValue;

    /* Constructor */
    private TokenType(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    /**
     * Returns ItemType in readable format.
     * @return Returns item type in String.
     */
    @Override
    public String toString() {
        return stringValue;
    }

    /**
     * Returns TokenType in numeral format
     * @return Returns token type in Integer
     */
    public Integer toInteger() { return intValue; }
}

