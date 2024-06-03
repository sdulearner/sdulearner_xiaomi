package com.example.xiaomi3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TopFragment.TopPageActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //用Bundle传递信息
        Bundle bundle = new Bundle();
        bundle.putInt("some_int", 3);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //动态添加Fragment
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, DemoFragment.class, bundle)
                .commit();

        /*
        3秒后remove DemoFragment
         */
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                    fragmentManager.beginTransaction()
//                            .setReorderingAllowed(true)
//                            .remove(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.fragment_container_view)))
//                            .commit();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        thread.start();

        //replace Fragment1
        Button button = findViewById(R.id.my_button);

        button.setOnClickListener(v -> {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.go_in, R.anim.go_out, R.anim.go_in, R.anim.go_out)//添加动画
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view_static, DemoFragment2.class, null)
                    .addToBackStack(null)//将ExampleFragment(被replace的Fragment)压栈
                    .commit();
        });

        //BottomFragment
//        fragmentManager.beginTransaction()
//                .setReorderingAllowed(true)
//                .add(R.id.bottom_fragment, BottomFragment.class, bundle)
//                .commit();

        //TopFragment
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.top_fragment, TopFragment.class, bundle)
                .commit();

        //构建滑动视图
        DemoCollectionPagerAdapter demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(fragmentManager);
        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(demoCollectionPagerAdapter);

    }

    // 接口通信
    @Override
    public void onTopPageAction() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.top_fragment, BottomFragment.class, null)
                .commitNow();
    }
}