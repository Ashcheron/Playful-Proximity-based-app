package com.example.rami.wifiexchange;

/**
 * Created by Z3R0M4K3R on 21.3.2015.
 */

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;

/**
 * An interface-callback for the activity to listen to interaction
 * events.
 */
public interface DeviceActionListener
{
    void showDetails(WifiP2pDevice device);

    void cancelDisconnect();

    void connect(WifiP2pConfig config);

    void disconnect();
}