package com.example.xiaomi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class ServiceBindActivity extends AppCompatActivity {
    private static final String TAG = "ServiceBindActivity";
    private boolean mBind = false;

    private BindService mBindService;
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindService.LocalBinder localBinder = (BindService.LocalBinder) service;
            mBindService = localBinder.getService();
            mBind = true;
            Log.d(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBind = false;
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_bind);
        Button bindButton = findViewById(R.id.bindBtn);
        Button unbindButton = findViewById(R.id.unbindBtn);
        Button getButton = findViewById(R.id.getBtn);

        bindButton.setOnClickListener(v -> {
            Intent intent  = new Intent(ServiceBindActivity.this, BindService.class);
            bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        });

        unbindButton.setOnClickListener(v -> {
            unbindService(mConnection);
            mBind=false; 
        });
        getButton.setOnClickListener(v -> {
            if(!mBind) return;
            String bindText = mBindService.getBindText();
            Toast.makeText(mBindService, bindText, Toast.LENGTH_SHORT).show();
        });
    }

}