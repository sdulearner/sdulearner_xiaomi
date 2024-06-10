package com.example.xiaomi9.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.xiaomi9.R;
import com.github.anrwatchdog.ANRWatchDog;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity extends AppCompatActivity {
    private static final String TAG = "PermissionActivity";
    private static final List<Activity> leaks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
//        leaks.add(this);
        new ANRWatchDog().start();
        int temp = 0;
        for (int i = 0; ; i++) {
            temp += 1;
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
        }
    }
}