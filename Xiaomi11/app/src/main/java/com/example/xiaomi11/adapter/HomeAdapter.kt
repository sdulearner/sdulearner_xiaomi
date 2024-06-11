package com.example.xiaomi11.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.xiaomi11.R
import com.example.xiaomi11.entity.HomeItem


class HomeAdapter(layoutResId: Int, data: MutableList<HomeItem>? = null) :
    BaseQuickAdapter<HomeItem, BaseViewHolder>(layoutResId, data), LoadMoreModule {

    constructor(layoutResId: Int) : this(layoutResId, null) // 调用上面的构造器作为默认构造器

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    override fun convert(holder: BaseViewHolder, item: HomeItem) {
        //加载更多1:实现LoadMoreModule
        val imageView: ImageView = holder.getView<ImageView>(R.id.big_icon)
        val textView: TextView = holder.getView<TextView>(R.id.big_text)
        Glide.with(imageView.context)
            .load(item.imageResource)
            .into(imageView)
        textView.text = item.title

        val big_like: TextView = holder.getView<TextView>(R.id.big_like)
        if (item.isLike) big_like.setBackgroundResource(R.drawable.like_fill) else big_like.setBackgroundResource(
            R.drawable.like
        )
    }
}
