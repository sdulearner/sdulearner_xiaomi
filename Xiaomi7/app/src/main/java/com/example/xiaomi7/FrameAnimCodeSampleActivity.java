package com.example.xiaomi7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class FrameAnimCodeSampleActivity extends AppCompatActivity {
    private static final String TAG = "FrameAnimCodeSampleActivity";
    private int repeatCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_anim_code_sample);

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);

        //通过资源文件方式展示帧动画
//        imageView.setImageResource(R.drawable.drawable_run_anim);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
//        animationDrawable.start();

        //通过代码方式展示帧动画
//        AnimationDrawable animationDrawable = new AnimationDrawable();
//        animationDrawable.setOneShot(false);
//        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.run1), 300);
//        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.run2), 300);
//        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.run3), 300);
//        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.run4), 300);
//        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.run5), 300);
//        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.run6), 300);
//        animationDrawable.addFrame(ContextCompat.getDrawable(this, R.drawable.run7), 300);
//        imageView.setImageDrawable(animationDrawable);
//        animationDrawable.start();

        //补间动画
        // todo:通过资源文件方式展示平移动画

        //通过代码方式展示平移动画
//        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 9f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 9f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        translateAnimation.setDuration(4000);
//        imageView.startAnimation(translateAnimation);

        //通过资源文件方式展示动画
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
//        imageView.startAnimation(animation);
        //
//        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(4000);
//        imageView.startAnimation(scaleAnimation);

        //旋转动画
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotation);
//        imageView.startAnimation(animation);

//        RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(1000);
//        imageView.startAnimation(rotateAnimation);

        //透明动画
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
//        imageView.startAnimation(animation);

//        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
//        alphaAnimation.setDuration(2000);
//        imageView.startAnimation(alphaAnimation);

        //组合动画
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_set);
//        imageView.startAnimation(animation);
//        AnimationSet animationSet = new AnimationSet(true);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
//        animationSet.addAnimation(scaleAnimation);
//        animationSet.addAnimation(rotateAnimation);
//        animationSet.setDuration(2000);
//        imageView.startAnimation(animationSet);
//        animationSet.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                Log.e(TAG, "onAnimationStart: ");
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Log.e(TAG, "onAnimationEnd: ");
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                Log.e(TAG, "onAnimationRepeat: ");
//            }
//        });
        //属性动画
//        ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_rotate_x);
//        objectAnimator.setTarget(textView);
//        objectAnimator.start();

//        //代码方式
//      ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(textView, View.ROTATION_X,0f,360f);
        //组合属性动画
//        PropertyValuesHolder   rotationXHolder=PropertyValuesHolder.ofFloat(View.ROTATION_X,0f,360f);
//        PropertyValuesHolder translateXHolder=PropertyValuesHolder.ofFloat(View.TRANSLATION_X,0,100);
//        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(textView,rotationXHolder,translateXHolder);
//
//        objectAnimator.setDuration(1000);
//        objectAnimator.start();
        //animatorSet
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator rotationXAnimator = ObjectAnimator.ofFloat(textView, View.ROTATION_X, 0f, 360f);
//        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(textView, View.TRANSLATION_X, 0f, 100f);
////        animatorSet.playTogether(rotationXAnimator,translateXAnimator);
//        animatorSet.play(rotationXAnimator).after(translateXAnimator);
//        animatorSet.setDuration(2000);
//        animatorSet.start();

        //valueAnimator
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
//                float currentValue = (float) animation.getAnimatedFraction();
////                Log.e(TAG, "onAnimationUpdate: " + animation.getAnimatedFraction());
//                textView.setRotationX(currentValue * 360f);
//                textView.setTranslationX(currentValue * 100f);
//            }
//        });
//        valueAnimator.setDuration(10000);
//        valueAnimator.start();

        //ViewPropertyAnimator
//        textView.animate()
//                .rotationX(360)
//                .translationX(100)
//                .setDuration(2000)
//                .setStartDelay(1000)
//                .start();

        //Evaluator
//        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(textView, "backgroundColor", Color.parseColor("#009688"), Color.RED);
//        //平滑过渡背景色
//        objectAnimator.setEvaluator(new ArgbEvaluator());
//        objectAnimator.setDuration(10000);
//        objectAnimator.start();

        //1、基于当前 vew 中心点放大1.5倍，同时逆时针旋转720度，由不透明变为透明度0.8，持续2000ms，并且重复动画3次
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_set);
        repeatCount = 1;
        animationSet.setDuration(2000);
        //重复播放动画3次，由于AnimationSet本身并不直接支持setRepeatCount()方法，所以为每个子动画设置重复次数
        for (Animation anim : animationSet.getAnimations()) {
            anim.setRepeatCount(3);
            anim.setRepeatMode(Animation.RESTART); // 每次重复重新播放动画
        }
        imageView.startAnimation(animationSet);
        //打印日志
        animationSet.getAnimations().get(0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.e(TAG, "onAnimationRepeat:" + (repeatCount += 1));
            }
        });
        //实现属性动画，使用 AnimatorSet，先是当前 View围绕X轴旋转 360 度，持续1000ms；然后向右移动120px，持续1000ms;
        // 最后从不透明变成透明度0.5，持续500ms。(要求:使用Java方式实现，需要有2个基础动画同时执行，有1个顺序执行，
        // 且实现至少2种不同效果的自定义插值器与估值器)

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator rotationXAnimator = ObjectAnimator.ofFloat(textView, View.ROTATION_X, 0f, 360f);
        //设置自定义的Evaluator
        rotationXAnimator.setDuration(1000).setEvaluator(new MyCustomEvaluator());

        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(textView, View.TRANSLATION_X, 0f, 120f);
        translateXAnimator.setDuration(1000);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(textView, View.ALPHA, 1.0f, 0.5f);
        alphaAnimator.setDuration(500);
        //设置旋转和平移同时执行
        animatorSet.playTogether(rotationXAnimator, translateXAnimator);
        //设置透明度变化在平移后执行
        animatorSet.play(alphaAnimator).after(translateXAnimator);
        //设置自定义的Interpolator
        animatorSet.setInterpolator(new MyCustomInterpolator());
        animatorSet.start();

    }
}