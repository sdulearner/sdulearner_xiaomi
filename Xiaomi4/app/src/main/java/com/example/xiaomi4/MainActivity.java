package com.example.xiaomi4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textview1);
        // TODO: 2024/6/4 跑马灯
        textView.requestFocus();
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);

        button1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "onLongClick", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        /*
        触摸事件
         */
//        button1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(MainActivity.this, "onTouch", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
        // TODO: 2024/6/4 text富文本
//        textView.setText(R.string.hello_world);// 设置文字，显示内容
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);//设智文大
//        textView.setTextColor(getResources().getColor(R.color.red));// 设置文字颜色
//        textView.setTypeface(null, Typeface.BOLD_ITALIC);//设置文字样式


        // TODO: 2024/6/4 toggleButton Switch

        //跳转到InputName
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputName.class);
                startActivity(intent);
            }
        });

        //启动ImageActivity
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });

        //启动ListActivity
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // TODO: 2024/6/4 R.id.button1?
//            case ???:
//                Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
    }
}