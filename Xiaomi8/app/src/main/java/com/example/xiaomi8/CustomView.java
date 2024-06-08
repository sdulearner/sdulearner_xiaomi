package com.example.xiaomi8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomView extends View {
    private Paint mPaint;

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(40);
        canvas.drawText("雷总牛逼！！！", 100, 100, mPaint);

        canvas.save();//保存画布状态
        mPaint.setStyle(Paint.Style.FILL);//设置实心
        canvas.clipRect(new Rect(100, 550, 300, 750));//裁剪出一个矩形区域
        canvas.drawColor(Color.LTGRAY);//设置画布颜色
        canvas.drawCircle(150, 600, 100, mPaint);//在裁剪区域内画圆
        canvas.restore();//恢复画布状态

        //使用Path绘制
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        Path path = new Path();
        path.moveTo(400, 300);
        path.lineTo(1000, 350);

        path.moveTo(400, 300);
        path.quadTo(650, 400, 400, 600);

        path.moveTo(400, 600);
        path.cubicTo(400, 700, 500, 550, 600, 900);

        path.moveTo(600, 1000);
        RectF mRecF = new RectF(400, 900, 600, 1100);
        path.arcTo(mRecF, 0, 180);
        canvas.drawPath(path, mPaint);

        // 按照坐标绘制文字
        mPaint.setTextSize(32);
        mPaint.setStrokeWidth(1);
        canvas.drawPosText("Hello world", new float[]{20, 20,/*第一个字母在坐标*/20, 20, 30, 30,
                40, 40, 50, 50, 60, 60, 70, 70, 80, 60, 90, 50, 100, 40, 110, 30, 120, 20}, mPaint);

        // 沿着路径绘制文字
        Path path2 = new Path();
        path2.moveTo(400, 300);
        path2.lineTo(1000, 200);
        canvas.drawTextOnPath("1591008610010", path2, 50, -30, mPaint);

    }
}
