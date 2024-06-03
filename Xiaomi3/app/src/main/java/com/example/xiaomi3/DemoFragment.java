package com.example.xiaomi3;

import static com.example.xiaomi3.R.layout.example_fragment2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DemoFragment extends Fragment {
    public static final String POSITION = "position";
    private static final String TAG = "DemoFragment";

    public DemoFragment() {
        super(example_fragment2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Bundle传递消息
//        int someInt = requireArguments().getInt("some_int");
//        Log.d(TAG, "onViewCreated: " + someInt);
    }

    /**
     * 根据传入的ViewPager的position修改TextView的显示
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(example_fragment2, container, false);
        TextView textView = view.findViewById(R.id.textView2);
        int position = requireArguments().getInt(POSITION);
        if (position != 0) textView.setText("ViewPager" + position);
        return view;
    }
}
