package com.example.xiaomi3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TopFragment extends Fragment {
    public interface TopPageActionListener {
        void onTopPageAction();
    }

    private TopPageActionListener topPageActionListener;

    public TopFragment() {
        super(R.layout.top_fragment);
    }

    /**
    为TopFragment的按钮添加点击事件，其具体实现在MainActivity
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment, container, false);
        view.findViewById(R.id.top_button).setOnClickListener(v -> {
            if (topPageActionListener != null) topPageActionListener.onTopPageAction();
        });
        return view;
    }

    /**
    onAttach时指定topPageActionListener
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TopPageActionListener) {
            topPageActionListener = (TopPageActionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        topPageActionListener = null;
    }
}
