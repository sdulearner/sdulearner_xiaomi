package com.example.xiaomi7;

import android.animation.TypeEvaluator;

public class MyCustomEvaluator implements TypeEvaluator<Object> {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        //简单的线性插值
        float startFloat = (Float) startValue;
        float endFloat = (Float) endValue;
        return (startFloat + (endFloat - startFloat) * fraction);
    }
}
