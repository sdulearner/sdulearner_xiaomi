package com.example.xiaomi4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class FormFragment extends Fragment {
    private static final String TAG = "FormFragment";
    private final boolean[] chooseLanguage = {false, false, false};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);

        StringBuilder stringBuilder = new StringBuilder();
        Button showName = view.findViewById(R.id.show_name);
        EditText inputNameEdit = view.findViewById(R.id.input_name_edit);
        //获取并显示EditText中的内容
        showName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
            }
        });
        //设置input_name_edit中只能输入大写字母
        EditText edit_name = view.findViewById(R.id.input_name_edit);
        EditText edit_phone = view.findViewById(R.id.input_phone_edit);

        Button button = view.findViewById(R.id.show_phone);
        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    button.setEnabled(false);
                } else {
                    // 判断未尾输入
                    if (s.charAt(s.length() - 1) < 'A' | s.charAt(s.length() - 1) > 'Z') {
                        Toast.makeText(view.getContext(), "删除非数字字符:" + s.charAt(s.length() - 1), Toast.LENGTH_SHORT).show();
                        s = s.subSequence(0, s.length() - 1);
                        edit_name.setText(s);//设置新内容
                        edit_name.setSelection(s.length());//设置光标

                    }
                    // 设置按钮样式
                    button.setEnabled(s.length() > 0);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //choose gender
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        final boolean[] flag = {true}; //默认为男
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                flag[0] = checkedId == R.id.btnMan;
                Toast.makeText(view.getContext(), "单选框，值为" + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        //choose age
        SeekBar age = view.findViewById(R.id.age);
        TextView ageText = view.findViewById(R.id.age_text);
        final int[] ageNumber = {0};
        age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageNumber[0] = progress;
                ageText.setText("请选择您的年龄:" + +progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //Checkbox选择语言
        CheckBox java = view.findViewById(R.id.java);
        java.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 选中状态的操作
                    chooseLanguage[0] = true;
                }
            }
        });
        CheckBox cpp = view.findViewById(R.id.cpp);
        cpp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 选中状态的操作
                    chooseLanguage[1] = true;
                }
            }
        });
        CheckBox python = view.findViewById(R.id.python);
        python.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 选中状态的操作
                    chooseLanguage[2] = true;
                }
            }
        });

        button.setOnClickListener(v -> {
            stringBuilder.append("用户名:").append(edit_name.getText()).append("\n");
            stringBuilder.append("电话:").append(edit_phone.getText()).append("\n");
            stringBuilder.append("性别:").append(flag[0] ? "男" : "女");
            stringBuilder.append("年龄:").append(ageNumber[0]).append("\n");
            stringBuilder.append("编程语言：").append(chooseLanguage[0] ? "java " : " ").append(chooseLanguage[1] ? "cpp " : " ").append(chooseLanguage[2] ? "python " : " ");
            String info = new String(stringBuilder);
            Log.d(TAG, "onCreateView: " + info);
        });
        return view;
    }
    // TODO: 2024/6/4 R.id.java?
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked) {
//            switch (buttonView.getId()) {
//                case R.id.java:
//                    chooseLanguage[0] = true;
//                case R.id.cpp:
//                    chooseLanguage[1] = true;
//                case R.id.python:
//                    chooseLanguage[2] = true;
//                default:
//                    break;
//            }
//        }
//    }

    public FormFragment() {
    }

    /**
     *
     */
    public static FormFragment newInstance(String param1, String param2) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}