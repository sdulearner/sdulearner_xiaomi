package com.example.xiaomi6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeAdapter mAdapter;
    private List<HomeItem> data;
    private boolean mIsLoadEnd = false;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //         Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        //添加初始数据
        data = new ArrayList<>();
        data.add(new HomeItem("原神", R.drawable.genshin));
        data.add(new HomeItem("王者荣耀", R.drawable.wangzhe));
        data.add(new HomeItem("广告", R.drawable.ad));
        mAdapter = new HomeAdapter(R.layout.big_button, data);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        //设置组件的点击事件
        //先注册子控件的id
        mAdapter.addChildClickViewIds(R.id.big_icon, R.id.big_text, R.id.big_like);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                Log.e(TAG, "onItemChildClick: " + position);
//                bundle.putInt("imageResource", R.drawable.genshin);
//                bundle.putString("title", "？？");
//                bundle.putBoolean("like", false);
                int viewId = view.getId();
                HomeItem click_data = data.get(position);
                if (viewId == R.id.big_icon) {//点击图片
                    bundle.putInt("imageResource", click_data.getImageResource());
                    bundle.putBoolean("like", click_data.isLike());
                    Intent intent = new Intent(view.getContext(), ImageActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (viewId == R.id.big_text) {//点击文字
                    bundle.putString("title", click_data.getTitle());
                    bundle.putBoolean("like", click_data.isLike());
                    Intent intent = new Intent(view.getContext(), ImageActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (viewId == R.id.big_like) {//点赞
                    if(click_data.isLike()) {//如果点击之前是喜欢则设为不喜欢
                        view.setBackground("R.drawable.like");
                    }
                } else Log.e(TAG, "viewid:" + viewId);
            }
        });
        //加载更多2:在setAdapter之前 loadMore
        mAdapter.getLoadMoreModule().setAutoLoadMore(true);
        mAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        //3.监听刷新事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        return view;
    }

    //上拉加载
    private void loadMore() {
        Log.e(TAG, "loadMore");
        if (mIsLoadEnd) {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            mRecyclerView.postDelayed(() -> {
                Log.e(TAG, "loadMore success");
                data.add(new HomeItem("广告", R.drawable.ad));
                mAdapter.notifyDataSetChanged();
                mIsLoadEnd = true;
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.getLoadMoreModule().loadMoreComplete();
            }, 1000);
        }
    }

    //下拉刷新
    private void refreshData() {
        //开始刷新，设置当前为刷新状态
        swipeRefreshLayout.setRefreshing(true);
        //这里是主线程
        //一些比较耗时的操作，比如联网获取数据，需要放到子线程执行
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                data.add(0, new HomeItem("Steam游戏助手", R.drawable.steam));
                mAdapter.notifyDataSetChanged();// 加载完数据设置为不刷新状态，将下拉进度收起来
            }
        }, 200);
    }
}