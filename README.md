# Day7

#### 效果展示

![Animation](./images/Day7/Screenrecording_20240606_235422.gif "Animation流")

##### 1、使用补间动画，基于当前 vew 中心点放大1.5倍，同时逆时针旋转720度，由不透明变为透明度0.8，持续2000ms，并且重复动画3次。(要求:任选XML或Java方式实现，动画开始时打印日志“animation start”，动画重复时打印“animation repeat”以及重复了2次，动画结束时打印日志“animation end”)

##### 2、实现属性动画，使用 AnimatorSet，先是当前 View围绕X轴旋转 360 度，持续1000ms；然后向右移动 120px，持续1000ms;最后从不透明变成透明度0.5，持续500ms。(要求:使用Java方式实现，需要有2个基础动画同时执行，有1个顺序执行，且实现至少2种不同效果的自定义插值器与估值器)

# Day6 Bug Fix

##### 3、**显示瀑布流**

##### 今天发现了问题出在没有在HomeAdapter中添加界面资源的设置，导致Adatper只能更新一个布尔值，而不能更新界面显示。

~~感觉昨晚像脑子抽了一样，明明是一个简单的小Bug却苦苦思索接近两个小时😓~~

* HomeAdapter.java

  ```java
  package com.example.xiaomi6;
  
  import ...
  
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
          TextView big_like = baseViewHolder.getView(R.id.big_like);
  		// todo:bug修复完毕
  		if (homeItem.isLike())
     			 big_like.setBackgroundResource(R.drawable.like_fill);
  		else big_like.setBackgroundResource(R.drawable.like);
      }
  }
  ```
  
* HomeFragment.java

  ```java
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
  ```
  
* **最终实现效果**

  ![Day6](./images/Day7/Screenrecording_20240606_235422.gif "Day6")
  
  
  
* **打包apk**

  [sdulearner-Day6.apk](./Xiaomi6/app/release/sdulearner-Day6.apk "sdulearner-Day6.apk")
  
  
  
  
