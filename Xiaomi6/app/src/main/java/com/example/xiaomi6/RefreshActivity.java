package com.example.xiaomi6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class RefreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        RecyclerView recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<HomeItem> data = new ArrayList<>();
        data.add(new HomeItem("原神", R.drawable.genshin));
        data.add(new HomeItem("王者荣耀", R.drawable.wangzhe));
        data.add(new HomeItem("广告", R.drawable.ad));
        HomeAdapter mAdapter = new HomeAdapter(R.layout.big_button, data);

        //加载更多2:在setAdapter之前 loadMore
        mAdapter.getLoadMoreModule().setAutoLoadMore(true);
        mAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                loadMore();
            }
        });
        recyclerView.setAdapter(mAdapter);
        //3.监听刷新事件
//        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
//        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//            }
//        });


        SwipeRefreshLayout swipeRefreshView = findViewById(R.id.swipe_layout2);
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);
                //这里是主线程
                //一些比较耗时的操作，比如联网获取数据，需要放到子线程执行
                new Handler().postDelayed(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        data.add(0, new HomeItem("新数据", R.drawable.steam));
                        mAdapter.notifyDataSetChanged();// 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefreshView.setRefreshing(false);
                    }
                }, 200);
            }
        });


    }
}