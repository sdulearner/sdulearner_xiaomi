package com.example.xiaomi11.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.xiaomi11.R
import com.example.xiaomi11.fragment.HomeFragment
import com.example.xiaomi11.fragment.MyPageFragment
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    // TODO: 使用懒加载委托
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val myPageFragment: MyPageFragment by lazy { MyPageFragment() }
    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        //动态添加HomeFragment
//        homeFragment = HomeFragment()
        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container_view, homeFragment, null)
            .commit()

        // TODO: 2024/6/6 点击切换的时候改变textview的背景和字体颜色
        //设置点击切换页面，用show和hide可以实现切换时保留页面状态
        val home_text = findViewById<TextView>(R.id.home_text)
        home_text.setOnClickListener { v: View? ->
            fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .show(homeFragment)
                .commit()
            myPageFragment.let {
                fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .hide(it)
                    .commit()
            }
        }

        val my_page_text = findViewById<TextView>(R.id.my_page_text)
        my_page_text.setOnClickListener { v: View? ->
            //动态添加MyPageFragment，如果第一次点击则建立一个新的，否则直接显示出来
            fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .show(myPageFragment)
                .commit()
            fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .hide(homeFragment)
                .commit()
        }

    }
}