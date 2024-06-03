package com.example.xiaomi3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DemoFragment extends Fragment {
    private static final String TAG = "DemoFragment";


    public DemoFragment() {
        super(R.layout.example_fragment2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        int someInt = requireArguments().getInt("some_int");
//        Log.d(TAG, "onViewCreated: " + someInt);
    }
}
