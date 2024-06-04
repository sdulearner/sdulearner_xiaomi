package com.example.xiaomi4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.ViewHolder> {
    private final List<GameBean> data;

    //1、定义回调接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //2、定义接口类型的变量存储数据
    private OnItemClickListener onItemClickListener;

    //3、定义回调方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GameRecyclerAdapter(List<GameBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public GameRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        //创建viewHolder实例并返回
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameRecyclerAdapter.ViewHolder holder, int position) {
        GameBean game = data.get(position);
        holder.mGameIcon.setImageResource(game.getGameIcon());
        holder.mGameName.setText(game.getGameName());
        holder.mGameButton.setText(game.getGameStatus());
        //4、调用回调方法
        if (onItemClickListener != null) {
            holder.mGameButton.setOnClickListener(v -> onItemClickListener.onItemClick(v, position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mGameIcon;
        TextView mGameName;
        Button mGameButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // itemView是RecyclerView的子项布局，通过它来获取控件实例
            mGameIcon = itemView.findViewById(R.id.game_icon);
            mGameName = itemView.findViewById(R.id.game_name);
            mGameButton = itemView.findViewById(R.id.game_button);
        }
    }
}
