# Day6

#### 效果展示

![瀑布流](../images/Day6/Screenrecording_20240606_235422.gif "瀑布流")

##### “EventBus实现手指点击后改变点赞状态”未实现，在HomeFragment中接收到message后调用mAdapter.notifyDataSetChanged()方法无法更新，尝试了许多其他方法还是无果，请问老师这里应该怎么写才能更新？

##### 1、**通过Fragment的切换实现首页、我的页两个页面**

* * **新建布局文件home.xml和my_page.xml，新建对应的HomeFragment和MyPageFragment**

    * **home.xml**

      ```xml
      <?xml version="1.0" encoding="utf-8"?>
      <androidx.swiperefreshlayout.widget.SwipeRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:app="http://schemas.android.com/apk/res-auto"
          xmlns:tools="http://schemas.android.com/tools"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:id="@+id/swipe_layout"
          tools:context=".RefreshActivity">
          <androidx.recyclerview.widget.RecyclerView
              android:id="@+id/recyclerview"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              app:layout_constraintEnd_toEndOf="parent"
              app:layout_constraintStart_toStartOf="parent" />
      
      </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
      ```

  * **MainActivity中实现布局和点击切换Fragment**

    * **activity_main.xml**

      ```xml
      <!--FramLayout加载瀑布流-->
      <FrameLayout
          android:id="@+id/fragment_container_view"
          android:layout_width="match_parent"
          android:layout_height="0dp"
          app:layout_constraintBottom_toTopOf="@id/navigation"
          app:layout_constraintEnd_toEndOf="parent"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintTop_toTopOf="parent" />
      
      <androidx.constraintlayout.widget.ConstraintLayout
          android:layout_width="0dp"
          android:layout_height="50dp"
          app:layout_constraintBottom_toBottomOf="parent"
          app:layout_constraintEnd_toEndOf="parent"
          app:layout_constraintStart_toStartOf="parent">
      
          <TextView
              android:id="@+id/home_text"
              android:layout_width="0dp"
              android:layout_height="match_parent"
              android:gravity="center"
              android:background="@color/white"
              android:text="首页"
              app:layout_constraintBottom_toBottomOf="parent"
              app:layout_constraintEnd_toStartOf="@+id/divide_line"
              app:layout_constraintStart_toStartOf="parent" />
      
          <!--中间一条黑色的分割线-->
          <TextView
              android:id="@+id/divide_line"
              android:layout_width="1px"
              android:layout_height="match_parent"
              android:background="@color/black"
              app:layout_constraintEnd_toStartOf="@id/my_page_text"
              app:layout_constraintStart_toEndOf="@id/home_text" />
      
      
          <TextView
              android:id="@+id/my_page_text"
              android:layout_width="0dp"
              android:layout_height="match_parent"
              android:gravity="center"
              android:text="我的"
              android:background="@color/white"
              app:layout_constraintEnd_toEndOf="parent"
              app:layout_constraintHorizontal_bias="0.72"
              app:layout_constraintStart_toEndOf="@id/divide_line"
              tools:layout_editor_absoluteY="0dp" />
      </androidx.constraintlayout.widget.ConstraintLayout>
      ```

    * **MainActivity中实现加载和切换Fragment的代码**

      ```java
      FragmentManager fragmentManager = getSupportFragmentManager();
      
      //动态添加HomeFragment
      homeFragment = new HomeFragment();
      fragmentManager.beginTransaction()
              .setReorderingAllowed(true)
              .add(R.id.fragment_container_view, homeFragment, null)
              .commit();
      
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
      ```

    * 

##### 2、**显示瀑布流**

* HomeAdapter.java

  ```java
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
          //加载更多1:实现LoadMoreModule
          ImageView imageView = baseViewHolder.getView(R.id.big_icon);
          TextView textView = baseViewHolder.getView(R.id.big_text);
          Glide.with(imageView.getContext())
                  .load(homeItem.getImageResource())
                  .into(imageView);
          textView.setText(homeItem.getTitle());
      }
  }
  ```

* HomeFragment.java

  ```java
  package com.example.xiaomi6;
  
  import android.annotation.SuppressLint;
  import android.content.Intent;
  import android.os.Bundle;
  import android.os.Handler;
  import android.os.Looper;
  import android.util.Log;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.TextView;
  
  import androidx.annotation.NonNull;
  import androidx.annotation.Nullable;
  import androidx.core.content.ContextCompat;
  import androidx.fragment.app.Fragment;
  import androidx.recyclerview.widget.RecyclerView;
  import androidx.recyclerview.widget.StaggeredGridLayoutManager;
  import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
  
  import com.chad.library.adapter.base.BaseQuickAdapter;
  import com.chad.library.adapter.base.listener.OnItemChildClickListener;
  import com.chad.library.adapter.base.listener.OnItemClickListener;
  import com.chad.library.adapter.base.listener.OnLoadMoreListener;
  
  import org.greenrobot.eventbus.EventBus;
  import org.greenrobot.eventbus.Subscribe;
  import org.greenrobot.eventbus.ThreadMode;
  
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
          mAdapter.addChildClickViewIds(R.id.big_icon, R.id.big_text, R.id.big_like);
          mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
              @Override
              public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
  
                  Bundle bundle = new Bundle();
                  bundle.putInt("position", position);
                  Log.e(TAG, "onItemChildClick: " + position);
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
                      Intent intent = new Intent(view.getContext(), TextActivity.class);
                      intent.putExtras(bundle);
                      startActivity(intent);
                  } else if (viewId == R.id.big_like) {//点赞
                      if (click_data.isLike()) {//如果点击之前是喜欢则设为不喜欢
                          view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.like));
                          data.set(position, new HomeItem(click_data.getTitle(), click_data.getImageResource(), false));
                      } else {
                          view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.like_fill));
                          data.set(position, new HomeItem(click_data.getTitle(), click_data.getImageResource(), true));
                      }
                      Log.e(TAG, "data.get(position).isLike(): " + data.get(position).isLike());
                      // TODO: 2024/6/6 下面这行代码没有效果 why
                      mAdapter.notifyDataSetChanged();
                  } else Log.e(TAG, "viewid:" + viewId);
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
                  // 加载完数据设置为不刷新状态，将下拉进度收起来
                  swipeRefreshLayout.setRefreshing(false);
                  data.add(0, new HomeItem("Steam游戏助手", R.drawable.steam));
                  mAdapter.notifyDataSetChanged();
              }
          }, 200);
      }
  
      //注册和取消注册EventBus
      @Override
      public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          EventBus.getDefault().register(this);
      }
  
      @Override
      public void onDestroy() {
          super.onDestroy();
          EventBus.getDefault().unregister(this);
      }
  
      //响应EventBus
      @Subscribe(threadMode = ThreadMode.MAIN)
      public void onMsgEvent(MessageEvent message) {
          Log.e(TAG, "onMsgEvent: " + message.getPosition() + message.isLike());
          HomeItem click_data = data.get(message.getPosition());
          data.set(message.getPosition(), new HomeItem(click_data.getTitle(), click_data.getImageResource(), message.isLike()));
          getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  mAdapter.notifyDataSetChanged();
              }
          });
      }
  }
  ```

* ImageActivity.java

  ```java
  package com.example.xiaomi6;
  
  import androidx.appcompat.app.AppCompatActivity;
  import androidx.core.content.ContextCompat;
  
  import android.content.Intent;
  import android.os.Bundle;
  import android.widget.ImageView;
  import android.widget.TextView;
  
  import org.greenrobot.eventbus.EventBus;
  
  public class ImageActivity extends AppCompatActivity {
      private boolean like;
  
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_image);
          //根据Bundle的数据显示
          Intent intent = getIntent();
          Bundle extras = intent.getExtras();
          ImageView big_icon = findViewById(R.id.big_icon);
          big_icon.setImageResource(extras.getInt("imageResource"));
          TextView big_like = findViewById(R.id.big_like);
          like = extras.getBoolean("like");
          if (like)
              big_like.setBackground(ContextCompat.getDrawable(ImageActivity.this, R.drawable.like_fill));
          else
              big_like.setBackground(ContextCompat.getDrawable(ImageActivity.this, R.drawable.like));
          //设置点赞的监听事件
          big_like.setOnClickListener(v -> {
              if (like) //如果点击之前是喜欢则设为不喜欢
                  big_like.setBackground(ContextCompat.getDrawable(ImageActivity.this, R.drawable.like));
              else
                  big_like.setBackground(ContextCompat.getDrawable(ImageActivity.this, R.drawable.like_fill));
              //将like反转后传递给HomeFragment
              like=!like;
              EventBus.getDefault().post(new MessageEvent(extras.getInt("position"), like));
          });
      }
  }
  ```

* TextActivity.java同理

##### 3、打包apk(未完成)
