package com.example.rami.timelocksolving;

/**
 * Created by Rami on 18.5.2015.
 */
public class Token {
    /* Token attributes. */
    private Integer ID;
    private String NAME;
    private TokenType TYPE;

    /**
     *
     * @param id String id
     * @param name String mName
     * @param type Integer type
     */
    public Token (Integer id, String name, int type) {
        ID = id;
        NAME = name;
        if (type >= 0 && type < 3)
            setTokenTYPE(type);
        else
            setTokenTYPE(3);
    }

    /* Setters and getters for attributes. */

    /**
     * Set the mName for the token.
     * @param name Name of the token.
     */
    public void setName (String name) { NAME = name; }

    /**
     * Get the mName of the token.
     * @return Returns mName of the token in String.
     */
    public String getName() { return NAME; }

    /**
     * Set the id for the token.
     * !!! Will be changed once static IDs for tokens are created !!!
     * @param id ID string of the token.
     */
    public void setID (Integer id) { ID = id; }

    /**
     * Get the id of the token.
     *  !!! Will be changed once static IDs for tokens are created !!!
     * @return Returns ID String
     */
    public Integer getID () { return ID; }

    /**
     * Set token type, CLUE or EVIDENCE. Default set to CLUE.
     * !!! NOT COMPLETE !!!
     * @param type Integer; 0 for clue, 1 for evidence
     */
    public void setTokenTYPE (int type) {
        switch (type) {
            case 0: TYPE = TokenType.SUSPECT;
                    break;
            case 1: TYPE = TokenType.WEAPON;
                    break;
            case 2: TYPE = TokenType.LOCATION;
                    break;
            default: TYPE = TokenType.MISC;
        }

    }

    /**
     *
     * @return Returns token type as Integer value
     */
    public Integer getTokenTYPE () {
        return TYPE.toInteger();
    }

    /**
     * Gets tokens type as a String.
     * @return TokenType in readable format.
     */
    public String getTokenTypeString () { return TYPE.toString(); }

}
