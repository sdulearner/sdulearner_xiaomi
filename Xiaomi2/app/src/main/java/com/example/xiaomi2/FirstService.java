package com.example.xiaomi2;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class FirstService extends IntentService {

    private static final String TAG = "FirstService";

    public FirstService() {
        super("FirstService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Log.d(TAG, "onHandleIntent: service started");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e(TAG, "error", e);
        }
    }
}
