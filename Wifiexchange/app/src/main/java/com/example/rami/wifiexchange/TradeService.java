package com.example.rami.wifiexchange;

import android.app.IntentService;
import android.content.Intent;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Z3R0M4K3R on 21.3.2015.
 */
public class TradeService extends IntentService
{
    private final static int SOCK_TIMEOUT = 5000;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TradeService(String name)
    {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        /* Retrieve connection information passed */
        String tradeItem = intent.getExtras().getString("ITEM_TO_TRADE");
        String hostAddress = intent.getExtras().getString("HOST_IP");
        int hostPort = intent.getExtras().getInt("HOST_PORT");
        String tradeAddress = intent.getExtras().getString("TRADE_IP");

        Socket socket = new Socket();

        try
        {
            socket.bind(null);
            socket.connect(new InetSocketAddress(hostAddress, hostPort), SOCK_TIMEOUT);

            /* TODO: Finish sending data */
        }
        catch (Exception e)
        {

        }
    }
}
