package com.example.rami.wifiexchange;

/**
 * Created by Rami on 27.3.2015.
 */
public enum ItemType {
    CLUE("Clue", 0),
    EVIDENCE("Evidence", 1);

    private String stringValue;
    private int intValue;

    /* Constructor */
    private ItemType(String toString, int value) {
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
}
