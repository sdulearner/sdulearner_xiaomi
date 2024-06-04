package com.example.xiaomi4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "ImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Log.d(TAG, "onCreate: ");
        //两个背景+一个图片
        SpannableString spanString = new SpannableString("我爱玩原神！！!");
        //设置背景色
        spanString.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.red)), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //设置背景色
        spanString.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.green)), 1, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 设置前景色
        spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //设置点击事件
        spanString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(ImageActivity.this, "原神启动！！！", Toast.LENGTH_SHORT).show();
            }
        }, 3, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //在点击链接时，有执行的动作，都必须设置 MovementMethod对象
        TextView textView = findViewById(R.id.span_text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //设置图片
//        Drawable drawable = getResources().getDrawable(R.drawable.genshin);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        spanString.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);

        //SeekBar
        SeekBar rotateBar = findViewById(R.id.seekBar);
        ImageView genshin = findViewById(R.id.genshin);
        TextView rotateText = findViewById(R.id.rotateText);
        rotateBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                genshin.setPivotX(genshin.getWidth() / 2);
                genshin.setPivotY(genshin.getHeight() / 2);
                //设置支点在图片中心
                float alpha = progress / 100.0f;
                float rotate = alpha * 360.0f;
                genshin.setRotation(rotate);

                genshin.setAlpha(alpha);
                rotateText.setText("设置旋转角度:" + rotate + "设置透明度:" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}