package com.example.xiaomi2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.security.Provider;
import java.util.List;
import java.util.Map;

public class BindService extends Service {
    private static final String TAG = "BindService";
    private final LocalBinder mBinder = new LocalBinder();


    public BindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind:");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    public String getBindText() {
        return "BindText:Hello World:)";
    }

    protected class LocalBinder extends Binder {
        BindService getService() {
            return BindService.this;
        }
    }
}
