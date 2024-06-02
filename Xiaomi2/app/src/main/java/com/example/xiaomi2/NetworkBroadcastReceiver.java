package com.example.xiaomi2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action= intent.getAction();
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            Toast.makeText(context, "network changed", Toast.LENGTH_SHORT).show();
        }
    }
}