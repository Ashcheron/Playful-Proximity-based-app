package com.example.rami.rssi;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    private ArrayList <info> storage;
    TextView rssi_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (BTAdapter == null)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Your device does not support bluetooth! This program will not work.");
            builder1.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            this.finish();
        }

        else {
            registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

            Button button = (Button) findViewById(R.id.button);
            Button clear = (Button) findViewById(R.id.button2);
            rssi_msg = (TextView) findViewById(R.id.textView1);

            storage = new ArrayList<info>();

            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (!BTAdapter.isEnabled()) {
                        BTAdapter.enable();
                    }
                    BTAdapter.startDiscovery();
                }
            });

            clear.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    rssi_msg.setText("");
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);

                /*
                for (int i = 0; i < storage.size(); i++) {
                    if (storage.get(i).getName() != name) {
                        storage.add(new info(name,rssi));
                    }
                    else if (storage.get(i).getName() == name && storage.get(i).getQUALITY() != rssi) {
                        storage.get(i).setQuality(rssi);
                    }

                    rssi_msg.setText(rssi_msg.getText() + storage.get(i).getName() + " => " + storage.get(i).getQUALITY() + "dBm\n");
                }
                */
                TextView rssi_msg = (TextView) findViewById(R.id.textView1);
                rssi_msg.setText(rssi_msg.getText() + name + " => " + rssi + "dBm\n");
            }
        }
    };
}