package com.example.rami.wifiexchange;

/**
 * Created by Rami on 13.3.2015.
 */
public class Item {

    /* Item attributes. */
    private String ID;
    private String NAME;
    private ItemType TYPE;

     /* Constructor. */
    public Item () {
        setItemTYPE(0);
    }

    /* Setters and getters for attributes. */

    /**
     * Set the name for the item.
     * @param name Name of the item.
     */
    public void setName (String name) { NAME = name; }

    /**
     * Get the name of the item.
     * @return Returns name of the item in String.
     */
    public String getName() { return NAME; }

    /**
     * Set the id for the item.
     * !!! Will be changed once static IDs for items are created !!!
     * @param id ID string of the item.
     */
    public void setID (String id) { ID = id; }

    /**
     * Get the id of the item.
     *  !!! Will be changed once static IDs for items are created !!!
     * @return Returns ID String
     */
    public String getID () { return ID; }

    /**
     * Set item type, CLUE or EVIDENCE. Default set to CLUE.
     * !!! NOT COMPLETE !!!
     * @param type Integer; 0 for clue, 1 for evidence
     */
    public void setItemTYPE (int type) {
        if (type == 2)
            TYPE = ItemType.EVIDENCE;
        else
            TYPE = ItemType.CLUE;
    }

    /**
     * Gets items type as a String.
     * @return ItemType in readable format.
     */
    public String getItemTypeString () { return TYPE.toString(); }

}
