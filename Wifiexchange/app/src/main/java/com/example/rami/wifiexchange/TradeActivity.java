package com.example.rami.wifiexchange;

import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Z3R0M4K3R on 20.3.2015.
 */

public class TradeActivity extends ActionBarActivity implements PeerListListener,
                                                                OnItemClickListener
{
    /* A Wi-Fi direct connection handler */
    private ConnectionHandler m_connHandler;

    /* The list view which will show the peers */
    private ListView m_peerListView;

    /* An adapter which holds available peers */
    private ArrayAdapter<String> m_peerListAdapter;

    /* Item to be traded */
    private String m_itemToTrade;

    /* List of available peers */
    WifiP2pDeviceList m_peerConnList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        /* Get the values passed from the calling activity */
        Bundle extras = getIntent().getExtras();

        /* Get the name of the item to be traded */
        if(extras != null)
            m_itemToTrade = extras.getString("ITEM_TO_TRADE");

        /* Get the list view control */
        m_peerListView = (ListView)findViewById(R.id.peer_list);

        /* Setups the Wi-Fi direct connection handler */
        m_connHandler = new ConnectionHandler(this);
        m_connHandler.setupWifiDirect();
        m_connHandler.registerReceiver();

        /* Discovers peers for trade activity */
        m_connHandler.discoverPeers();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        /* Register the broadcast receiver */
        m_connHandler.registerReceiver();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        /* Unregister the broadcast receiver */
        m_connHandler.unregisterReceiver();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers)
    {
        m_peerConnList = peers;

        /* Create a list for the peers */
        ArrayList<String> peerList = new ArrayList<>();

        /* Loop through the devices and save the peer names */
        for (WifiP2pDevice peer : peers.getDeviceList())
            peerList.add(peer.deviceName);

        /* Update the array adapter */
        m_peerListAdapter = new ArrayAdapter<>(this, R.layout.peer_list_row_item, peerList);

        /* Update the peer list view */
        m_peerListView.setAdapter(m_peerListAdapter);

        /* Set a listener */
        m_peerListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        /* Disconnect a previous session */
        m_connHandler.disconnect();

        /* Start constructing a new peer connection */
        WifiP2pConfig config = new WifiP2pConfig();

        /* Retrieve the address for the peer */
        for(WifiP2pDevice peer : m_peerConnList.getDeviceList())
            if(peer.deviceName == m_peerListAdapter.getItem(position))
                config.deviceAddress = peer.deviceAddress;

        config.wps.setup = WpsInfo.PBC;

        /* Try to connect to a peer */
        m_connHandler.connect(config);
    }
}
