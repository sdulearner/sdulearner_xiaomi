package com.example.xiaomi4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListActivity extends AppCompatActivity {

    private final List<GameBean> data = new ArrayList<>();
    private static final String TAG = "ListActivity";
    private GameRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //1.准备数据
        setData();
        //2.获取适配器对象
        adapter = new GameRecyclerAdapter(data);
        // 3.设置适配器
        recyclerView.setAdapter(adapter);
        // 4.设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//设置布局方向
        recyclerView.setLayoutManager(layoutManager);
        //5.设置分割线，主义分割线方向要与布局管理器一致
        DividerItemDecoration ddecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        ddecoration.setDrawable(getResources().getDrawable(R.drawable.divide_line));
        recyclerView.addItemDecoration(ddecoration);

        //设置添加和删除

        Button addItem = findViewById(R.id.add);
        Button removeItem = findViewById(R.id.delete);
        EditText editText = findViewById(R.id.editTextText);

        addItem.setOnClickListener(v -> {
            if (editText.getText().length() == 0) return;
            int position = Integer.parseInt(editText.getText().toString());
            int index = 0;
            if (position < 1) {
                Toast.makeText(this, "输入的数字小于1，已在第一项添加！", Toast.LENGTH_SHORT).show();
            } else if (position > (data.size())) {
                Toast.makeText(this, "输入的数字大于个数，已在最后一项添加！", Toast.LENGTH_SHORT).show();
                index = data.size() - 1;
            } else index = position - 1;
            GameBean addGameBean = new GameBean("原神启动！" + (index + 1), R.drawable.icon1, "added");
            data.add(index, addGameBean);
            updateData(index, data.size());//同步更新
            adapter.notifyItemInserted(index);//添加item
        });

        removeItem.setOnClickListener(v -> {
            if (editText.getText().length() == 0) return;
            int position = Integer.parseInt(editText.getText().toString());
            int index = 0;
            if (position < 1) {
                Toast.makeText(this, "输入的数字小于1，已删除第一项！", Toast.LENGTH_SHORT).show();
            } else if (position > (data.size())) {
                Toast.makeText(this, "输入的数字大于个数，已删除最后一项！", Toast.LENGTH_SHORT).show();
                index = data.size() - 1;
            } else index = position - 1;
            data.remove(index);
            Log.d(TAG, "index: " + index + " size" + data.size());
            updateData(index, data.size());//同步更新
            adapter.notifyItemRemoved(index);//添加item
        });
    }

    private void setData() {
        String[] gameStatus = new String[]{"开始游戏", "敬请期待", "更新", "预约"};
        int[] icons = new int[]{R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.genshin};
        for (int i = 0; i < 50; i++) {
            GameBean game = new GameBean();
            game.setGameName("原神启动！" + (i + 1));
            game.setGameStatus(gameStatus[i % 4]);
            game.setGameIcon(icons[i % 4]);
            data.add(game);
        }
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        private final int start;
        private final int count;

        public MyAsyncTask(int start, int count) {
            this.start = start;
            this.count = count;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // 执行耗时任务
            for (int i = start; i < count; i++) {
                Log.d(TAG, "updateData:index " + i);
                GameBean temp = data.get(i);
                GameBean game = new GameBean();
                game.setGameName("原神启动！" + 1);
                game.setGameStatus(temp.getGameStatus());
                game.setGameIcon(temp.getGameIcon());
                data.set(start, game);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // 异步任务执行完成后，通知RecyclerView数据已改变
            adapter.notifyItemChanged(start);
        }
    }

    // 使用AsyncTask
    //todo 同步更新List bug:应该使用异步任务在次方法调用结束之后再notifyItemRangeChanged
    private void updateData(int index, int size) {
//        new MyAsyncTask(index, size).execute();

        // 创建一个Executor
        Executor executor = Executors.newSingleThreadExecutor();
        // 创建一个Handler与主线程关联
        Handler mainHandler = new Handler(Looper.getMainLooper());
        // 提交任务到Executor
        executor.execute(() -> {
            // 执行耗时任务
            for (int i = index; i < size; i++) {
                Log.d(TAG, "updateData:index " + i);
                GameBean temp = data.get(i);
                GameBean game = new GameBean();
                game.setGameName("原神启动！" + (i+1));
                game.setGameStatus(temp.getGameStatus());
                game.setGameIcon(temp.getGameIcon());
                data.set(index, game);
            }
            // 当任务完成时，使用Handler在主线程更新UI
            mainHandler.post(() -> {
//                adapter.notifyItemRangeChanged(index, size);
                adapter.notifyDataSetChanged();
            });
        });
    }
}