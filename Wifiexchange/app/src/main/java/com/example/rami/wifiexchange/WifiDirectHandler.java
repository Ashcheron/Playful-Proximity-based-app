/**
 * Created by Z3R0M4K3R on 8.3.2015.
 */

package com.example.rami.wifiexchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;

public class WifiDirectHandler extends BroadcastReceiver
{
    /* These are used to establish Wi-Fi direct connection */
    private WifiP2pManager m_manager;
    private Channel m_channel;
    private Context m_activity;

    /* ConnectionHandler may check if Wi-Fi direct is supported and enabled */
    private static boolean m_wifiDirectEnabled;

    /**
     * Constructs a handler for Wi-Fi direct.
     * @param manager Manages connections.
     * @param channel Manages channels.
     * @param activity The context of the calling activity.
     */
    public WifiDirectHandler(WifiP2pManager manager, Channel channel, Context activity)
    {
        super();
        m_manager = manager;
        m_channel = channel;
        m_activity = activity;
    }

    /**
     * The value retrieved is polled when trying to access Wi-Fi direct features.
     * @return Returns the status of Wi-Fi direct.
     */
    public static boolean isWifiDirectEnabled()
    {
        return m_wifiDirectEnabled;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
        {
            /* Check if WIFI direct is supported */
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
            {
                /* Wifi direct is supported */
                m_wifiDirectEnabled = true;
                Log.d("WifiDirectHandler.java", "Wi-Fi direct is supported!");
            }
            else
            {
                /* Wifi direct is not supported */
                m_wifiDirectEnabled = false;
                Log.d("WifiDirectHandler.java", "Wi-Fi direct is not supported!");
            }
        }
        else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
        {
            /* Try to retrieve Wi-Fi direct peers */
            if(m_manager != null)
                m_manager.requestPeers(m_channel, (PeerListListener)m_activity);
        }
        else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
        {
            // Respond to new connection or disconnections
        }
        else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {
            // Respond to this device's wifi state changing
        }
    }
}
