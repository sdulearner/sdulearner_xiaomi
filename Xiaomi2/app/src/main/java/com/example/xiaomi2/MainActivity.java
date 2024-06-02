package com.example.xiaomi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.my_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });

        //计算服务
        Button CalculatorServiceActivity = findViewById(R.id.CalculatorServiceActivity);
        CalculatorServiceActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculatorServiceActivity.class);
            startActivity(intent);
        });

        //通讯录
        Button ContactsActivity = findViewById(R.id.ContactsActivity);
        ContactsActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
        });


    }

}