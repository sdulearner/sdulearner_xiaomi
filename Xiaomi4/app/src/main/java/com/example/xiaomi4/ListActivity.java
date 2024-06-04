package com.example.xiaomi4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private final List<GameBean> data = new ArrayList<>();

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
        GameBean addGameBean = new GameBean("addItem", R.drawable.icon1, "added");
        Button addItem = findViewById(R.id.add);
        Button removeItem = findViewById(R.id.delete);
        EditText editText = findViewById(R.id.editTextText);

        addItem.setOnClickListener(v -> {
//            if (editText.getText().length() == 0) return;
//            int position = Integer.parseInt(editText.getText().tostring())
            int position = 1;
            data.add(position, addGameBean);
            adapter.notifyItemInserted(position);//添加item
            adapter.notifyItemRangeChanged(position, data.size());
        });

        removeItem.setOnClickListener(v -> {
//            if (editText.getText().length() == 0) return;
//            int position = Integer.parseInt(editText.getText().tostring())
            int position = 1;
            data.remove(position);
            adapter.notifyItemInserted(position);//添加item
            adapter.notifyItemRangeChanged(position, data.size());
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
}