package com.example.rami.wifiexchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Z3R0M4K3R on 20.3.2015.
 */
public class ConnectionHandler implements ChannelListener,
                                          DeviceActionListener
{
    /* These are used to establish Wi-Fi direct connection */
    private WifiP2pManager m_manager;
    private WifiP2pManager.Channel m_channel;
    private BroadcastReceiver m_receiver;

    /* This will monitor changes in Wi-Fi direct actions */
    private IntentFilter m_intentFilter;

    /* Holds the context using this handler */
    private Context m_context;

    /**
     *  Saves the context of the calling activity.
     * @param context Context of the calling activity.
     */
    public ConnectionHandler(Context context)
    {
        m_context = context;
    }

    /**
     * Sets Wi-Fi direct options and initializes listeners.
     */
    public void setupWifiDirect()
    {
        /* Get the Wi-Fi direct service and initialize it */
        m_manager = (WifiP2pManager)m_context.getSystemService(m_context.WIFI_P2P_SERVICE);
        m_channel = m_manager.initialize(m_context, m_context.getMainLooper(), null);

        /* Add listeners to broadcast intents */
        m_intentFilter = new IntentFilter();
        m_intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        m_intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        m_intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        m_intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    /**
     * Registers the broadcast receiver.
     */
    public void registerReceiver()
    {
         m_receiver = new WifiDirectHandler(m_manager, m_channel, m_context);
         m_context.registerReceiver(m_receiver, m_intentFilter);
    }

    /**
     * Unregisters the broadcast receiver.
     */
    public void unregisterReceiver()
    {
         m_context.unregisterReceiver(m_receiver);
    }

    /**
     * Tries to discover Wi-Fi direct peers.
     */
    public void discoverPeers()
    {
        m_manager.discoverPeers(m_channel, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess()
            {
                Log.d("ConnectionHandler.java", "Discovering peers was successful!");
            }

            @Override
            public void onFailure(int reasonCode)
            {
                Log.d("ConnectionHandler.java", "Discovering peers failed!");
            }
        });
    }

    @Override
    public void onChannelDisconnected()
    {

    }

    @Override
    public void showDetails(WifiP2pDevice device)
    {

    }

    @Override
    public void cancelDisconnect()
    {

    }

    /**
     * Tries to connect to a peer through Wi-Fi direct.
     * @param config Connection configuration object.
     */
    @Override
    public void connect(WifiP2pConfig config)
    {
        if(WifiDirectHandler.isWifiDirectEnabled())
        {
            m_manager.connect(m_channel, config, new ActionListener()
            {
                @Override
                public void onSuccess()
                {
                    Toast.makeText(m_context, "Connection successful.",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int reason)
                {
                    Toast.makeText(m_context, "Connection failed. Retry.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Disconnects a already existing connection.
     */
    @Override
    public void disconnect()
    {
        m_manager.removeGroup(m_channel, new ActionListener()
        {
            @Override
            public void onSuccess()
            {
                Toast.makeText(m_context, "Disconnection successful.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reasonCode)
            {

            }
        });
    }
}
