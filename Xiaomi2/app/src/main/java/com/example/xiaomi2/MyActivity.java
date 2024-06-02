package com.example.xiaomi2;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        Button button = findViewById(R.id.my_button2);
        button.setOnClickListener(v -> {
//            Intent intent=new Intent(MyActivity.this, MyActivity.class);
//            startActivity(intent);
            //隐式intent
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is extra data");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
        //启动服务
        Button start_service = findViewById(R.id.start_service);
        start_service.setOnClickListener(v -> {
            Intent intent = new Intent(MyActivity.this, FirstService.class);
            startService(intent);
        });


        Log.d(TAG, "onCreate: 1");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 3");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: 4");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 5");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: 6");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 7");
    }
}