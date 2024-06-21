package com.example.xiaomi11.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.xiaomi11.R
import com.example.xiaomi11.entity.MessageEvent
import org.greenrobot.eventbus.EventBus

class TextActivity : AppCompatActivity() {
    private var like = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        val intent = intent
        val extras = intent.extras
        val big_text = findViewById<TextView>(R.id.big_text)
        big_text.text = extras!!.getString("title")
        val big_like = findViewById<TextView>(R.id.big_like)
        like = extras!!.getBoolean("like")
        if (like) big_like.background = ContextCompat.getDrawable(
            this@TextActivity,
            R.drawable.like_fill
        ) else big_like.background =
            ContextCompat.getDrawable(this@TextActivity, R.drawable.like)
        //设置点赞的监听事件
        //设置点赞的监听事件
        big_like.setOnClickListener { v: View? ->
            if (like) //如果点击之前是喜欢则设为不喜欢
                big_like.background = ContextCompat.getDrawable(
                    this@TextActivity,
                    R.drawable.like
                ) else big_like.background = ContextCompat.getDrawable(
                this@TextActivity,
                R.drawable.like_fill
            )
            //将like反转后传递给HomeFragment
            like = !like
            EventBus.getDefault()
                .post(MessageEvent(extras.getInt("position"), like))
        }
    }
}