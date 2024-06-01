package com.example.xiaomi1;

import android.app.Application;
import android.util.Log;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("tag", "onCreate: abc");
    }
}
