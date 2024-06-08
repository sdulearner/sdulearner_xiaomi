package com.example.xiaomi8;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TagCloud extends FrameLayout {
    private static final String TAG = "TagCloud";
    private float mHorizontalMargin = 10;
    private float mVerticalMargin = 10;
    private List<String> mTags = null;
    private GestureDetector mDetector;
    private View mSelectView;

    public TagCloud(@NonNull Context context) {
        super(context);
    }

    public TagCloud(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TagCloud(@NonNull Context context, @Nullable AttributeSet attrs,
                    int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TagCloud(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagCloud);
        mHorizontalMargin = a.getDimension(R.styleable.TagCloud_hMargin, 20);
        mVerticalMargin = a.getDimension(R.styleable.TagCloud_vMargin, 10);

        mDetector = new GestureDetector(this.getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                Log.i(TAG, "onTouchEvent: ACTION_DOWN");
                int actionIndex = e.getActionIndex(); // 获取当前动作的索引
                int pointerId = e.getPointerId(actionIndex); // 获取当前动作的指针ID
                activePointerId = pointerId;
                activePointerX = e.getX(actionIndex);
                activePointerY = e.getY(actionIndex);
                RectF r = new RectF();
                for (int i = 0; i < getChildCount(); i++) {
                    View view = getChildAt(i);
                    r.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                    if (r.contains(e.getX(), e.getY())) {
                        select(view, i);
                        break;
                    }
                }
                return true;
            }

            @Override
            public void onShowPress(@NonNull MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }


            @Override
            public void onLongPress(@NonNull MotionEvent e) {
            }

            @Override
            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                release();
                return true;
            }
        });
    }

    // 加载tags列表中的信息
    public void setTags(List<String> tags) {
        if (this.mTags == tags) {
            Log.i(TAG, "setTags: " + tags.toString());
            this.removeAllViews();
        }
        mTags = tags;
        int tagCount = mTags != null ? mTags.size() : 0;//获取总标签数
        int childCount = getChildCount();// 获取现有的子View数
        if (tagCount > childCount) {// 总标签数大于子View数，向后追加
            for (int i = childCount; i < tagCount; i++) {
                TextView child = new TextView(getContext());
                child.setTextSize(14);//字体大小
                child.setMaxLines(1);//最多一行
                child.setEllipsize(TextUtils.TruncateAt.END);//截断方式
                child.setBackgroundColor(R.color.sky_blue);//背景色
                addView(child, i);//添加子View
            }
        } else if (tagCount < childCount) {//标签数量小于子View数量，移出
            for (int i = childCount; i > tagCount; i--) {
                removeViewAt(childCount);
            }
        }
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setText(mTags.get(i));//设置text
        }
    }

    // 测量每个子View的位置
    List<Location> locations;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  传入宽度测量模式和尺寸
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        //  传入高度的测量模式和尺寸
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //  设置子View的宽度
        int childWidthSpec;
        switch (widthSpecMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                // 计算需要传给子View的 MeasureSpec
                childWidthSpec = MeasureSpec.makeMeasureSpec(widthSpecSize - ((int) mHorizontalMargin * 2), MeasureSpec.AT_MOST);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                childWidthSpec = widthMeasureSpec;
                break;
        }
        //  设置子View的高度
        int childHeightSpec;
        switch (heightSpecMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                // 计算需要传给子View的 MeasureSpec
                childHeightSpec = MeasureSpec.makeMeasureSpec(heightSpecSize - ((int) mVerticalMargin * 2), MeasureSpec.AT_MOST);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                childHeightSpec = heightMeasureSpec;
                break;
        }

        //  计算l,t,r,b
        int height = 0; // 当前高度坐标 Y轴
        int remainWidth = 0; // 保持宽度坐标 X轴
        int top = 0; // 顶部 Y轴
        locations = new ArrayList<>();
        //遍历全部子View
        for (int i = 0; i < getChildCount(); i++) {
            // 将测量要求传给子View
            View child = getChildAt(i);
            child.measure(childWidthSpec, childHeightSpec);
            // left, top, right, bottom
            int l, t, r, b;
            // 第一行:height=0
            // 新起一行
            if (height == 0 || remainWidth + mHorizontalMargin + child.getMeasuredWidth() > widthSpecSize) {
                // locations添加第一个
//                if (height == 0)
//                    locations.add(new Location(0, 0,
//                            ((int) mHorizontalMargin + child.getMeasuredWidth()) / 2,
//                            (int) mVerticalMargin + child.getMeasuredHeight()));
                t = height + (int) mVerticalMargin;
                top = t;

                height += mVerticalMargin + child.getMeasuredHeight();
                b = height;

                remainWidth = (int) mHorizontalMargin;
                l = remainWidth;

                remainWidth += child.getMeasuredWidth();
                r = remainWidth;


            } else {
                //每行的后几个
                t = top;//复用刚才记录下来的 top
                b = top + child.getMeasuredHeight();//根据top计算底部坐标
                l = remainWidth + (int) mHorizontalMargin;//剩余宽度+margin 等于左侧左边
                remainWidth += mHorizontalMargin + child.getMeasuredWidth();//剩余宽度+margin+子view宽度=剩余宽度坐标
                r = remainWidth;
            }
            // 记录下在当前子View里面，等下用
            Location location = new Location(l, t, r, b);
            locations.add(new Location(l, t, (r + l) / 2, b));
            child.setTag(location);
        }
        setMeasuredDimension(widthSpecSize, heightSpecSize);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // 遍历全部的子View，获取位置分发layout
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Location location = (Location) child.getTag();
            child.layout(location.getL(), location.getT(), location.getR(), location.getB());
        }
    }


    // 如果松开手指则调用release()方法，其他的情况根据mDetector的监听器执行
    private float activePointerX;
    private float activePointerY;
    private int activePointerId = -1; // 初始化为-1，表示没有手指在拖动
    private int nextPointerId = -1; //两个手指同时按下时的第二个手指

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 要实现这个效果，需要跟踪每个手指的按下和抬起事件，并维护一个状态来记录当前可以拖动的手指。
        // 在这段代码中，我们维护了一个activePointerId变量来跟踪当前正在拖动的手指的ID。
        // 当有新手指按下时将其设为下一个激活的手指(nextPointerId)，
        // 当之前按下的手指抬起时将nextPointerId对应的手指设为激活状态
        // 这样，只有第一个按下的手指能够拖动视图，直到它松开后，第二个按下的手指才能开始拖动
        int actionIndex = event.getActionIndex(); // 获取当前动作的索引
        int action = event.getActionMasked(); // 获取动作类型
        int pointerId = event.getPointerId(actionIndex); // 获取当前动作的指针ID

        switch (action) {
            // 第二个手指按下
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "onTouchEvent: ACTION_POINTER_DOWN");
                nextPointerId = pointerId;
                return true;

            case MotionEvent.ACTION_MOVE:
                if (activePointerId != -1 && activePointerId == pointerId) {
                    // 只有当当前手指是激活的手指时，才允许移动
                    float deltaX = activePointerX - event.getX(actionIndex);
                    float deltaY = activePointerY - event.getY(actionIndex);
                    // 移动视图
                    if (mSelectView != null) {
                        scrollSelectView(deltaX, deltaY);
                        // 更新激活手指的位置
                        activePointerX = event.getX(actionIndex);
                        activePointerY = event.getY(actionIndex);
                        return true;
                    } else {
                        return false;
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (mSelectView != null) {
                    release();
                    return true;
                }
                return true;
            // 其中的一个手指抬起，将nextPointerId对应的手指设为激活状态
            case MotionEvent.ACTION_POINTER_UP:
                if (activePointerId == pointerId) {
                    // 如果激活的手指抬起了，激活另一个手指
                    Log.i(TAG, "onTouchEvent: ACTION_POINTER_UP");
                    activePointerId = nextPointerId;
                }
                return true;
        }
        return mDetector.onTouchEvent(event);
    }

    // 手指按下时播放动画
    private void select(View view, int index) {
        mSelectView = view;
        i_select = index;//当前被选中iew的id
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.2f,
                1f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }

    // 手指滑动时，需要除以缩放比例
    private void scrollSelectView(float distanceX, float distanceY) {
        mSelectView.setTranslationX(mSelectView.getTranslationX() - distanceX * 5 / 6);
        mSelectView.setTranslationY(mSelectView.getTranslationY() - distanceY * 5 / 6);
    }

    // 手指松开时取消动画并复原位置
    private float l_select;
    private float t_select;
    private float r_select;
    private float b_select;
    private int i_select;//当前被选中iew的id

    private void release() {
        if (mSelectView != null) {
            mSelectView.clearAnimation();
//            //
//            TagCloud release方法
//            判断换位置
            Log.i(TAG, "release: " + locations.toString());
            for (int i = 0; i < locations.size(); i++) {
                Location location = locations.get(i);
                Log.i(TAG, "release: location" + location.toString());
                if (location.getL() <= activePointerX && activePointerX < location.getR() && location.getT() <= activePointerY && activePointerY < location.getB()) {
                    String temp = mTags.get(i_select);
                    mTags.remove(i_select);
                    mTags.add(i, temp);
                    Log.i(TAG, "release: swap");
                    setTags(mTags);
//                    requestLayout();
                    break;
                }
            }

//            // da
//            for (int i = 0; i < getChildCount() - 1; i++) {
//                View view_front = getChildAt(i);
//                View view_back = getChildAt(i + 1);
//                float l = view_front.getLeft();
//                float r = view_back.getRight();
//                float t = view_front.getTop();
//                float b = view_front.getBottom();
//                if (l <= activePointerX && activePointerX < r
//                        && t <= activePointerY && activePointerY < b) {
//                    Log.d(TAG, "swap");
//                    removeView(mSelectView);
//                    for (int j = i_select + 1; j < i + 1; j++) {
//                        View view_change = getChildAt(j);
//                        view_change.setId(j - 1);
//                    }
//                    addView(mSelectView, i);
//                    mSelectView.setId(i);
//                    // 请求重新布局子视图
//                    requestLayout();
//                    break; //
//                }
//            }
            activePointerId = -1;
            nextPointerId = -1;
            mSelectView.setTranslationX(0);
            mSelectView.setTranslationY(0);
        }
//        setTags(mTags);
        mSelectView = null;//将选中的子View设为null，避免滑动空白处也能移动
    }

}