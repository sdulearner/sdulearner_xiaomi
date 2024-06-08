package com.example.xiaomi8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends EditText {
    private final Rect mRect;
    private final Paint mPaint;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = getLineCount();

        Rect rect = mRect;
        Paint paint = mPaint;
        for (int i = 0; i < count; i++) {
            int baseLine = getLineBounds(i, rect);
            canvas.drawLine(rect.left, baseLine + 1, rect.right, baseLine + 1, paint);
        }
        super.onDraw(canvas);
    }
}
