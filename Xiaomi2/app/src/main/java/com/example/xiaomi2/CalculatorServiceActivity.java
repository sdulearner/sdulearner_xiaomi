package com.example.xiaomi2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class CalculatorServiceActivity extends AppCompatActivity {

    private static final String TAG = "CalculatorServiceActivity";
    private boolean mBind = false;

    private ICalculatorInterface mBindService;
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBindService = ICalculatorInterface.Stub.asInterface(service);
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
        setContentView(R.layout.activity_calculator_service);
        // TODO: 2024/6/2 Expecting binder but got null!
        Intent intent = new Intent(this, CalculatorService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        Button calculateBtn = findViewById(R.id.btn_calculate);
        calculateBtn.setOnClickListener(v -> {
            if (mBind) return;
            try {
                // TODO: 2024/6/2 NullPointerException
                int sum = mBindService.add(1, 2);
                Toast.makeText(CalculatorServiceActivity.this, "result=" + sum, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                Log.d(TAG, "error:", e);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mBind = false;
    }
}