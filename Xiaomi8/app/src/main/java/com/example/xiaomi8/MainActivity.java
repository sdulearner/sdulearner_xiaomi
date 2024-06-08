package com.example.xiaomi8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private GestureDetector mDetector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        CustomEditText customEditText = findViewById(R.id.edit);
//        customEditText.setText("雷总牛逼！！\n雷总牛逼！！雷总牛逼！！");
        List<String> t = new ArrayList<>();
        t.add("优秀的公司赚取利润");
        t.add("伟大的公司赢得人心");
        t.add("1999交个朋友");
        t.add("雷总牛逼！！！");
        t.add("Are you OK?");
        t.add("永远相信美好的事情即将发生");
        t.add("干翻友商！");
        t.add("小米2代！屌爆了！");
        ((TagCloud) findViewById(R.id.edit)).setTags(t);

//        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
//            @Override
//            public boolean onDown(@NonNull MotionEvent e) {
//                // 当用户按下屏幕时调用。它表示用户已经开始触摸屏幕。
//                Log.i(TAG, "onDown: " + e.getAction() + e.getX() + e.getY());
//                return false;
//            }
//
//            @Override
//            public void onShowPress(@NonNull MotionEvent e) {
//                // 当用户按下屏幕一段时间后，但尚未完成其他手势时调用。在长按之前调用。
//                Log.i(TAG, "onShowPress: ");
//            }
//
//            @Override
//            public boolean onSingleTapUp(@NonNull MotionEvent e) {
//                // 当用户轻触屏幕时调用，且未发生滑动或长按。用于处理单击事件。
//                Log.i(TAG, "onSingleTapUp: ");
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
//                // 当用户在屏幕上滑动时调用。提供了起始事件、当前事件以及滑动的距离。
//                Log.i(TAG, "onScroll: ");
//                return false;
//            }
//
//            @Override
//            public void onLongPress(@NonNull MotionEvent e) {
//                // 当用户长按屏幕时调用。用于处理长按事件。
//                Log.i(TAG, "onLongPress: ");
//            }
//
//            @Override
//            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
//                // 当用户快速滑动后松开时调用。提供了起始事件、当前事件以及滑动的速度。用于处理快速滑动事件。
//                Log.i(TAG, "onFling: ");
//                return false;
//            }
//        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean result = mDetector.onTouchEvent(event);
//        int action = event.getAction();
//        if (action == MotionEvent.ACTION_UP) {
//            Log.i(TAG, "onTouchEvent: ");
//        }
//        return result;
//    }
}