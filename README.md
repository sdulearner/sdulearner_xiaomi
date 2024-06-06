# Day6

##### 1、正常写出UI图

* **通过Fragment的切换实现首页、我的页两个页面**

  * **新建布局文件home.xml和my_page.xml，新建对应的HomeFragment和MyPageFragment**

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

##### 2、老师目前我还差点赞功能没写好，请稍等片刻马上提交最终作业



![小米账号帮助中心](./images/Day6/Screenrecording_20240605_223022.gif "小米账号帮助中心")

<img src="./images/Day6/Screenshot_20240605_223538.jpg" alt="过度绘制截图" title="过度绘制截图" style="zoom:50%;" />
