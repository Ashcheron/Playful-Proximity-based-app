package com.example.rami.wifiexchange;

/**
 * Created by Z3R0M4K3R on 20.3.2015.
 */
public final class ItemComposition
{
    /* A collection of all the items in the game */
    static String m_itemNames[] =
    {
        "Knife",
        "Footprint",
        "Autopsy report",
        "Blood",
        "Baseball bat",
        "Lipstick mark",
        "Fingerprint",
        "Wad of hair",
        "Blurry photo"
    };

    /**
     * Gets the name of an item at selected position.
     * @param index The position to retrieve the name from.
     * @return Returns the name of an item.
     */
    public static final String getItem(int index)
    {
        /* Return a valid item name only if index is in boundary */
        if(m_itemNames.length >= index)
            return m_itemNames[index];
        else
            return "Undefined item";
    }

    /**
     * Gets the count of items.
     * @return Returns the number of items in the game.
     */
    public static final int getSize()
    {
        return m_itemNames.length;
    }
}
