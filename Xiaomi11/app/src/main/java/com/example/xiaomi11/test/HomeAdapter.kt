//package com.example.xiaomi11.test
//
//import android.app.DownloadManager
//import android.content.Context
//import android.net.Uri
//import android.os.Environment
//import android.view.View
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import com.bumptech.glide.Glide
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.chad.library.adapter.base.module.LoadMoreModule
//import com.chad.library.adapter.base.viewholder.BaseViewHolder
//import com.example.xiaomi11.R
//import com.example.xiaomi11.entity.HomeItem
//
//class HomeAdapter : BaseQuickAdapter<HomeItem, BaseViewHolder>, LoadMoreModule {
//    constructor(layoutResId: Int, data: List<HomeItem?>?) : super(layoutResId, data)
//    constructor(layoutResId: Int) : super(layoutResId)
//
//    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        super.onBindViewHolder(holder, position)
//    }
//
//    override fun convert(baseViewHolder: BaseViewHolder, homeItem: HomeItem) {
//        //加载更多1:实现LoadMoreModule
//        // 加载游戏图标
//        val game_icon = baseViewHolder.getView<ImageView>(R.id.game_icon)
//        Glide.with(game_icon.context)
//            .load(homeItem.icon)
//            .into(game_icon)
//
//        // 加载中间的游戏名和评分
//        val gameName = baseViewHolder.getView<TextView>(R.id.gameName)
//        // TODO: 2024/6/9 用富文本调整评分的字体（字体调小，变成蓝色）
//        gameName.text = homeItem.gameName + "★" + homeItem.score
//        // 加载简介
//        val brief = baseViewHolder.getView<TextView>(R.id.brief)
//        brief.text = homeItem.brief
//
//        // 为安装按钮设置点击事件
//        val game_button = baseViewHolder.getView<Button>(R.id.game_button)
//        game_button.setOnClickListener { v: View? ->
//            // 创建Intent意图，动作是ACTION_VIEW，数据是网址的Uri
////            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(homeItem.getApkUrl()));
////            baseViewHolder.itemView.getContext().startActivity(intent);
//
//            // 调用此方法开始下载
//            val downloadManager =
//                baseViewHolder.itemView.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//            val request = DownloadManager.Request(Uri.parse(homeItem.apkUrl))
//
//            // 设置下载目录、文件名（可选）、通知栏显示信息等
//            request.setDestinationInExternalPublicDir(
//                Environment.DIRECTORY_DOWNLOADS,
//                homeItem.gameName + ".apk"
//            ) // 需要根据实际情况设定文件名和扩展名
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            request.setTitle("下载中")
//
//            // enqueue把请求放入下载队列，系统会处理下载过程
//            downloadManager.enqueue(request)
//        }
//    }
//}
