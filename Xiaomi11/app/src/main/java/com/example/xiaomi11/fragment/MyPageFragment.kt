package com.example.xiaomi11.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xiaomi11.R

class MyPageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //         Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_page, container, false)
    }
}