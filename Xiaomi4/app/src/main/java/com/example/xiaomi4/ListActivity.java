package com.example.xiaomi4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private final List<GameBean> data = new ArrayList<>();
    private static final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //1.准备数据
        setData();
        //2.获取适配器对象
        GameRecyclerAdapter adapter = new GameRecyclerAdapter(data);
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
            GameBean addGameBean = new GameBean("原神启动！"+(index+1), R.drawable.icon1, "added");
            data.add(index, addGameBean);
//            updateData(index, data.size());//同步更新
            adapter.notifyItemInserted(index);//添加item
            adapter.notifyItemRangeChanged(position, data.size());
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
//            updateData(index, data.size());//同步更新
            adapter.notifyItemRemoved(index);//添加item
            adapter.notifyItemRangeChanged(index, data.size());
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

    //todo 同步更新List bug:应该使用异步任务在次方法调用结束之后再notifyItemRangeChanged
//    private void updateData(int index, int size) {
//        for (int i = index; i < size; i++) {
//            Log.d(TAG, "updateData:index "+i);
//            GameBean temp = data.get(index);
//            GameBean game = new GameBean();
//            game.setGameName("原神启动！" + 1);
//            game.setGameStatus(temp.getGameStatus());
//            game.setGameIcon(temp.getGameIcon());
//            data.set(index, game);
//        }
//    }
}