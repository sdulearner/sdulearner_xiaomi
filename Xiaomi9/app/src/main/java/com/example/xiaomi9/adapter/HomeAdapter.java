package com.example.xiaomi9.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.xiaomi9.R;
import com.example.xiaomi9.entity.HomeItem;

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
        //加载更多1:实现LoadMoreModule
        // 加载游戏图标
        ImageView game_icon = baseViewHolder.getView(R.id.game_icon);
        Glide.with(game_icon.getContext())
                .load(homeItem.getIcon())
                .into(game_icon);

        // 加载中间的游戏名和评分
        TextView gameName = baseViewHolder.getView(R.id.gameName);
        // TODO: 2024/6/9 用富文本调整评分的字体（字体调小，变成蓝色）
        gameName.setText(homeItem.getGameName() + "★" + homeItem.getScore());
        // 加载简介
        TextView brief = baseViewHolder.getView(R.id.brief);
        brief.setText(homeItem.getBrief());

        // 为安装按钮设置点击事件
        Button game_button = baseViewHolder.getView(R.id.game_button);
        game_button.setOnClickListener(v -> {
            // 创建Intent意图，动作是ACTION_VIEW，数据是网址的Uri
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(homeItem.getApkUrl()));
            baseViewHolder.itemView.getContext().startActivity(intent);
            // 确保有应用能处理这个Intent
//            if (intent.resolveActivity(baseViewHolder.itemView.getContext().getPackageManager()) != null) {
//                // 启动Intent，这将打开系统的默认浏览器并加载指定的网址
//                baseViewHolder.itemView.getContext().startActivity(intent);
//            } else {
//                // 如果没有应用能处理这个Intent，可以提示用户
//                Toast.makeText(baseViewHolder.itemView.getContext(), "出错了！无法加载指定的网址", Toast.LENGTH_SHORT).show();
//            }
        });
    }
}
