package com.example.xiaomi6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

public class TextActivity extends AppCompatActivity {
    private boolean like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        //根据Bundle的数据显示
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        TextView big_text = findViewById(R.id.big_text);
        big_text.setText(extras.getString("title"));
        TextView big_like = findViewById(R.id.big_like);
        like = extras.getBoolean("like");
        if (like)
            big_like.setBackground(ContextCompat.getDrawable(TextActivity.this, R.drawable.like_fill));
        else
            big_like.setBackground(ContextCompat.getDrawable(TextActivity.this, R.drawable.like));
        //设置点赞的监听事件
        big_like.setOnClickListener(v -> {
            if (like) //如果点击之前是喜欢则设为不喜欢
                big_like.setBackground(ContextCompat.getDrawable(TextActivity.this, R.drawable.like));
            else
                big_like.setBackground(ContextCompat.getDrawable(TextActivity.this, R.drawable.like_fill));
            //将like反转后传递给HomeFragment
            like = !like;
            EventBus.getDefault().post(new MessageEvent(extras.getInt("position"), like));
        });
    }
}