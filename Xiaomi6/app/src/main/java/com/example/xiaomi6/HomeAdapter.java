package com.example.xiaomi6;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> implements LoadMoreModule {
    public HomeAdapter(int layoutResId, @Nullable List<HomeItem> data) {
        super(layoutResId, data);
    }

    public HomeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        super.onBindViewHolder(holder, position);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, HomeItem homeItem) {
//        baseViewHolder.setText(R.id.big_text, homeItem.getTitle());
//        baseViewHolder.setImageResource(R.id.big_icon, homeItem.getImageResource());
        //加载更多1:实现LoadMoreModule
        ImageView imageView = baseViewHolder.getView(R.id.big_icon);
        TextView textView = baseViewHolder.getView(R.id.big_text);
        Glide.with(imageView.getContext())
                .load(homeItem.getImageResource())
                .into(imageView);
        textView.setText(homeItem.getTitle());
        TextView big_like = baseViewHolder.getView(R.id.big_like);
        if (homeItem.isLike())
            big_like.setBackgroundResource(R.drawable.like_fill);
        else big_like.setBackgroundResource(R.drawable.like);
    }
}
