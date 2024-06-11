package com.example.xiaomi11.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.example.xiaomi11.R
import com.example.xiaomi11.activity.ImageActivity
import com.example.xiaomi11.activity.TextActivity
import com.example.xiaomi11.adapter.HomeAdapter
import com.example.xiaomi11.entity.HomeItem
import com.example.xiaomi11.entity.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeFragment : Fragment() {
    // 使用扩展函数
    // 在控制台打印带有类名的日志信息
    fun Any.logInfo(message: String) {
        Log.i(javaClass.simpleName, message)
    }

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var mAdapter: HomeAdapter? = null
    private var data: MutableList<HomeItem>? = null
    private var mIsLoadEnd = false
    private var mRecyclerView: RecyclerView? = null

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //         Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.home, container, false)
        mRecyclerView = view.findViewById(R.id.recyclerview)
        // TODO: complete  使用高阶表达式let添加非空断言
        mRecyclerView?.let { recyclerView ->
            recyclerView.layoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.VERTICAL
            )
            //添加初始数据
            data = ArrayList<HomeItem>()
            data!!.add(HomeItem("原神", R.drawable.genshin, false))
            data!!.add(HomeItem("王者荣耀", R.drawable.wangzhe, false))
            data!!.add(HomeItem("广告", R.drawable.ad, false))
            mAdapter = HomeAdapter(R.layout.big_button, data)
            swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_layout)

            mAdapter!!.loadMoreModule.isAutoLoadMore = true
            mAdapter!!.loadMoreModule.isEnableLoadMoreIfNotFullPage = true
            mAdapter!!.loadMoreModule.setOnLoadMoreListener { loadMore() }
            mRecyclerView!!.setAdapter(mAdapter)

            //3.监听刷新事件
            swipeRefreshLayout!!.setOnRefreshListener(OnRefreshListener { refreshData() })

            //设置组件的点击事件
            //先注册子控件的id
            mAdapter!!.addChildClickViewIds(R.id.big_icon, R.id.big_text, R.id.big_like)
            mAdapter!!.setOnItemChildClickListener(OnItemChildClickListener { adapter, view, position ->
                val bundle = Bundle()
                bundle.putInt("position", position)
                // TODO: 使用扩展函数打印日志
                logInfo("onItemChildClick: $position")
                val viewId = view.id
                val click_data: HomeItem = data!![position]
                if (viewId == R.id.big_icon) { //点击图片
                    bundle.putInt("imageResource", click_data.imageResource)
                    bundle.putBoolean("like", click_data.isLike)
                    val intent = Intent(
                        view.context,
                        ImageActivity::class.java
                    )
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else if (viewId == R.id.big_text) { //点击文字
                    bundle.putString("title", click_data.title)
                    bundle.putBoolean("like", click_data.isLike)
                    val intent = Intent(
                        view.context,
                        TextActivity::class.java
                    )
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else if (viewId == R.id.big_like) { //点赞
                    if (click_data.isLike) { //如果点击之前是喜欢则设为不喜欢
                        data!![position] =
                            HomeItem(click_data.title, click_data.imageResource, false)
                    } else {
                        data!![position] =
                            HomeItem(click_data.title, click_data.imageResource, true)
                    }
                    Log.e(
                        Companion.TAG,
                        "data.get(position).isLike(): " + data!![position].isLike
                    )
                    mAdapter!!.notifyDataSetChanged()
                } else Log.e(Companion.TAG, "viewid:$viewId")
            })
        }
        return view
    }

    //上拉加载
    private fun loadMore() {
        Log.e(Companion.TAG, "loadMore")
        if (mIsLoadEnd) {
            mAdapter!!.loadMoreModule.loadMoreEnd(true)
        } else {
            mRecyclerView!!.postDelayed({
                Log.e(Companion.TAG, "loadMore success")
                data!!.add(HomeItem("广告", R.drawable.ad, false))
                mAdapter!!.notifyDataSetChanged()
                mIsLoadEnd = true
                swipeRefreshLayout!!.isRefreshing = false
                mAdapter!!.loadMoreModule.loadMoreComplete()
            }, 1000)
        }
    }

    //下拉刷新
    private fun refreshData() {
        //开始刷新，设置当前为刷新状态
        swipeRefreshLayout!!.isRefreshing = true
        //这里是主线程
        //一些比较耗时的操作，比如联网获取数据，需要放到子线程执行
        Handler().postDelayed({ // 加载完数据设置为不刷新状态，将下拉进度收起来
            swipeRefreshLayout!!.isRefreshing = false
            data!!.add(0, HomeItem("Steam游戏助手", R.drawable.steam, false))
            mAdapter!!.notifyDataSetChanged()
        }, 200)
    }

    //注册和取消注册EventBus
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    //响应EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMsgEvent(message: MessageEvent) {
        Log.e(Companion.TAG, "onMsgEvent: " + message.position + message.like)
        val click_data: HomeItem = data!![message.position]
        data!![message.position] =
            HomeItem(click_data.title, click_data.imageResource, message.like)
        mAdapter?.notifyDataSetChanged()
    }
}
