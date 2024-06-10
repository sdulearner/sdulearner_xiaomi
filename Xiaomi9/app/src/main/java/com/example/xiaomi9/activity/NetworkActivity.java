package com.example.xiaomi9.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.xiaomi9.R;
import com.example.xiaomi9.adapter.HomeAdapter;
import com.example.xiaomi9.entity.CommonData;
import com.example.xiaomi9.entity.DataItem;
import com.example.xiaomi9.entity.GameItem;
import com.example.xiaomi9.entity.HomeItem;
import com.github.anrwatchdog.ANRWatchDog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class NetworkActivity extends AppCompatActivity {
    // TODO: 2024/6/9 编写注释
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    private static final MediaType jsonType = MediaType.parse("application/json;charset=utf-8");
    private final Gson gson = new Gson();
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
//            if (msg.what == 1) textBody.setText((CharSequence) msg.obj);
        }
    };
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://hotfix-service-prod.g.mi.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public interface ApiService {
        // 根据Id查找游戏
        @GET("/quick-game/game/{id}")
        retrofit2.Call<CommonData<GameItem>> queryGame(@Path("id") String id);

        // 搜索游戏
        @GET("/quick-game/game/search")
        @Headers("Content-Type: application/json")
        retrofit2.Call<CommonData<DataItem>> queryGames(
                @Query("search") String search, // 搜索内容
                @Query("current") int current, // 当前页，默认1
                @Query("size") int size // 当前页大小，默认10
        );
    }

    private final ApiService apiService = retrofit.create(ApiService.class);

    private static final String TAG = "NetworkActivity";
    private TextView textBody;
    private String search;// 搜索内容
    private int current = 1;// 当前页，默认1
    private int size = 10;// 当前页大小，默认10

    // 下面的变量用于更新RecyclerView
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeAdapter mAdapter;
    private List<HomeItem> data;
    private boolean mIsLoadEnd = false;
    private RecyclerView mRecyclerView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
//        okHttpClient = new OkHttpClient();
//        get();
//        postByFormData("19862139549");
//        textBody = findViewById(R.id.textBody);

        // 为SearchView添加监听
        SearchView searchView = findViewById(R.id.searchView);
        setupSearchViewListener(searchView);

        // 加载数据显示在RecyclerView中
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1 // 显示一列
                , StaggeredGridLayoutManager.VERTICAL));// 垂直布局
        data = new ArrayList<>();
        mAdapter = new HomeAdapter(R.layout.big_button, data);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);

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

        //设置组件的点击事件
        //先注册子控件的id
//        mAdapter.addChildClickViewIds(R.id.big_icon, R.id.big_text, R.id.big_like);
//        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//
//                Bundle bundle = new Bundle();
//                bundle.putInt("position", position);
//                Log.e(TAG, "onItemChildClick: " + position);
//                int viewId = view.getId();
//                HomeItem click_data = data.get(position);
//                if (viewId == R.id.big_icon) {//点击图片
//                    bundle.putInt("imageResource", click_data.getImageResource());
//                    bundle.putBoolean("like", click_data.isLike());
//                    Intent intent = new Intent(view.getContext(), ImageActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                } else if (viewId == R.id.big_text) {//点击文字
//                    bundle.putString("title", click_data.getTitle());
//                    bundle.putBoolean("like", click_data.isLike());
//                    Intent intent = new Intent(view.getContext(), TextActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                } else if (viewId == R.id.big_like) {//点赞
//                    if (click_data.isLike()) {//如果点击之前是喜欢则设为不喜欢
//                        data.set(position, new HomeItem(click_data.getTitle(), click_data.getImageResource(), false));
//                    } else {
//                        data.set(position, new HomeItem(click_data.getTitle(), click_data.getImageResource(), true));
//                    }
//                    Log.e(TAG, "data.get(position).isLike(): " + data.get(position).isLike());
//                    mAdapter.notifyDataSetChanged();
//                } else Log.e(TAG, "viewid:" + viewId);
//            }
//        });
    }

    private void setupSearchViewListener(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 用户提交了搜索请求，query参数即为搜索关键词
                // 在这里执行搜索逻辑
                search = query;
                retrofitGet();
                return true; // 返回true表示你已经处理了提交的查询，不会再有默认行为
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 当搜索文本改变时触发，可以在这里实现实时搜索建议等功能
                // newText即为当前搜索框中的文本
                // 实时搜索逻辑
                return false; // 返回false表示继续允许默认的文本变化处理
            }
        });
    }

    // 搜索和刷新时调用，会先清空data再加进去
    private void retrofitGet() {
//         根据Id查找游戏
//        retrofit2.Call<CommonData<GameItem>> queryGameCall = apiService.queryGame("109");
//        queryGameCall.enqueue(new retrofit2.Callback<CommonData<GameItem>>() {
//            @Override
//            public void onResponse(retrofit2.Call<CommonData<GameItem>> call, retrofit2.Response<CommonData<GameItem>> response) {
//                CommonData<GameItem> body = response.body();
//                // 使用Handler可以实现异步更新View
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
////                        textBody.setText(body.toString());
//                        Log.i(TAG, "queryGameById: " + body);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<CommonData<GameItem>> call, Throwable t) {
//                Log.e(TAG, "retrofitGet onFailure.");
//            }
//        });

        // 搜索游戏
        //开始刷新，设置当前为刷新状态
        swipeRefreshLayout.setRefreshing(true);
        data = new ArrayList<>();
        mAdapter = new HomeAdapter(R.layout.big_button, data);
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
        //这里是主线程
        //一些比较耗时的操作，比如联网获取数据，需要放到子线程执行
        mAdapter.getLoadMoreModule().setAutoLoadMore(true);
        // 重置上拉刷新和current
        mIsLoadEnd = false;
        current = 1;
        retrofit2.Call<CommonData<DataItem>> queryGamesCall = apiService.queryGames(search, current, size);
        // 搜索游戏
        queryGamesCall.enqueue(new retrofit2.Callback<CommonData<DataItem>>() {
            @Override
            public void onResponse(retrofit2.Call<CommonData<DataItem>> call, retrofit2.Response<CommonData<DataItem>> response) {
                CommonData<DataItem> body = response.body();
                // 使用Handler可以实现异步更新View
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        textBody.setText(body.toString());
//                        Log.i(TAG, "queryGames: " + body);
                        // 获取data字段，data中含有游戏列表records
                        DataItem dataItem = body.getData();
                        if (body != null) {
                            List<GameItem> gameItems = dataItem.getRecords();
//                            Log.i(TAG, "gameItems: " + gameItems);
                            data.clear();//清空data
                            if (gameItems.size() == 0)
                                Toast.makeText(NetworkActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
                            // 将列表需要展示的信息加入HomeItem
                            for (int i = 0; i < gameItems.size(); i++) {
                                GameItem gameItem = gameItems.get(i);
                                data.add(new HomeItem(gameItem.getIcon(), gameItem.getGameName(), gameItem.getScore(), gameItem.getBrief(), gameItem.getApkUrl()));
                            }

                        } else {
                            Toast.makeText(NetworkActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
                        }
                        // 更新列表
                        mAdapter.notifyDataSetChanged();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(retrofit2.Call<CommonData<DataItem>> call, Throwable t) {
                Log.e(TAG, "retrofitGet onFailure.");
            }
        });
    }

    // 加载更多时调用
    private void retrofitAdd() {
//         根据Id查找游戏
//        retrofit2.Call<CommonData<GameItem>> queryGameCall = apiService.queryGame("109");
//        queryGameCall.enqueue(new retrofit2.Callback<CommonData<GameItem>>() {
//            @Override
//            public void onResponse(retrofit2.Call<CommonData<GameItem>> call, retrofit2.Response<CommonData<GameItem>> response) {
//                CommonData<GameItem> body = response.body();
//                // 使用Handler可以实现异步更新View
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
////                        textBody.setText(body.toString());
//                        Log.i(TAG, "queryGameById: " + body);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<CommonData<GameItem>> call, Throwable t) {
//                Log.e(TAG, "retrofitGet onFailure.");
//            }
//        });

        // 搜索游戏
        retrofit2.Call<CommonData<DataItem>> queryGamesCall = apiService.queryGames(search, ++current, size);

        // 加载更多
        queryGamesCall.enqueue(new retrofit2.Callback<CommonData<DataItem>>() {
            @Override
            public void onResponse(retrofit2.Call<CommonData<DataItem>> call, retrofit2.Response<CommonData<DataItem>> response) {
                CommonData<DataItem> body = response.body();
                // 使用Handler可以实现异步更新View
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 获取data字段，data中含有游戏列表records
                        DataItem dataItem = body.getData();
                        List<GameItem> gameItems = dataItem.getRecords();
                        // 将列表需要展示的信息加入HomeItem
                        for (int i = 0; i < gameItems.size(); i++) {
                            GameItem gameItem = gameItems.get(i);
                            data.add(new HomeItem(gameItem.getIcon(), gameItem.getGameName(), gameItem.getScore(), gameItem.getBrief(), gameItem.getApkUrl()));
                        }
                        // 更新列表
                        mAdapter.notifyDataSetChanged();
                        // 全部加载完则停止上拉加载
                        if (dataItem.getTotal() < current * size)
                            mIsLoadEnd = true;
                    }
                });
            }

            @Override
            public void onFailure(retrofit2.Call<CommonData<DataItem>> call, Throwable t) {
                Log.e(TAG, "retrofitAdd onFailure.");
            }
        });

    }

    //上拉加载
    private void loadMore() {
        Log.e(TAG, "loadMore");
        if (mIsLoadEnd) {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        } else {
            mRecyclerView.postDelayed(() -> {
                Log.e(TAG, "loadMore success");
                retrofitAdd();
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.getLoadMoreModule().loadMoreComplete();
            }, 500);
        }
    }

    //下拉刷新
    private void refreshData() {
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                retrofitGet();
            }
        }, 200);
    }

    // OkHttp get请求
    private void get() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .get()
                        .url("https://hotfix-service-prod.g.mi.com/quick-game/game/109")
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    Log.i(TAG, "get: " + response.body().string());
                } catch (IOException e) {
                    Log.e(TAG, "error: ", e);
                }
            }
        }).start();
    }

    // OkHttp post请求
    public void postByFormData(String phone) {
        FormBody requestBody = new FormBody.Builder()
                .add("phone", phone)
                .build();

        Request request = new Request.Builder().post(requestBody)
                .addHeader("content-type", "application/json")
                .url("https://hotfix-service-prod.g.mi.com/quick-game/api/auth/sendCode")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

//                Log.i(TAG, "onResponse: " + response.body().string());
                // 直接调用会报错
                // android.view.ViewRootImpl$CalledFromWrongThreadException:
                // Only the original thread that created a view hierarchy can touch its views.
                textBody.setText(response.body().string());
            }
        });
    }
}