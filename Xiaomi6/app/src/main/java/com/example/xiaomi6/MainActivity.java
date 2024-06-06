package com.example.xiaomi6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.mylibrary.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mResult;
    private HomeFragment homeFragment;
    private MyPageFragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        // TODO: 2024/6/6 阿里巴巴代码命名规范
//        CircleImageView circleImageView;
//
//        ImageView imageView = findViewById(R.id.imageView);
//        Glide.with(this).load(R.drawable.genshin).into(imageView);
//
//        //注册EventBus
//        EventBus.getDefault().register(this);
//        //启动EventBusActivity
//        mResult = findViewById(R.id.textView);
//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, RefreshActivity.class);
//            startActivity(intent);
//        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        //动态添加HomeFragment
        homeFragment = new HomeFragment();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, homeFragment, null)
                .commit();

        // TODO: 2024/6/6 点击切换的时候改变textview的背景和字体颜色
        //设置点击切换页面，用show和hide可以实现切换时保留页面状态
        TextView home_text = findViewById(R.id.home_text);
        home_text.setOnClickListener(v -> {
            assert homeFragment != null;
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .show(homeFragment)
                    .commit();
            assert myPageFragment != null;
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .hide(myPageFragment)
                    .commit();
        });

        TextView my_page_text = findViewById(R.id.my_page_text);
        my_page_text.setOnClickListener(v -> {
            //动态添加MyPageFragment，如果第一次点击则建立一个新的，否则直接显示出来
            if (myPageFragment == null) {
                myPageFragment = new MyPageFragment();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_view, myPageFragment, null)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .show(myPageFragment)
                        .commit();
            }
            assert homeFragment != null;
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .hide(homeFragment)
                    .commit();
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // TODO: 2024/6/6 stiky粘性EventBus
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMsgEvent(MessageEvent message) {
//        Log.e(TAG, "onMsgEvent: " + message.getMessage());
//        runOnUiThread(() -> {
//            mResult.setText(message.getMessage());
//        });
//        mResult.setText(message.getMessage());
//    }
}