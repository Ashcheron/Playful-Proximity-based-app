package com.example.rami.rssi;

/**
 * Created by Rami on 24.2.2015.
 */
public class info {
    private String NAME;
    private int QUALITY;

    public info(String name, int quality) {
        NAME = name;
        QUALITY = quality;
    }

    public void setName(String name) {
        NAME = name;
    }
    public String getName() {
        return NAME;
    }

    public void setQuality(int quality) {
        QUALITY = quality;
    }
    public int getQUALITY() {
        return QUALITY;
    }
}
