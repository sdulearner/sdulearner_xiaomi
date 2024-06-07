package com.example.xiaomi7;

import android.animation.TimeInterpolator;

public class MyCustomInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return input * input; // 使动画开始时慢，结束时快
    }
}