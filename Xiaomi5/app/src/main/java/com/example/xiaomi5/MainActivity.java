package com.example.xiaomi5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ViewStub 动态加载布局
//        ViewStub viewStub = findViewById(R.id.viewstub);
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewStub.inflate();
//            }
//        });

        // TODO: 2024/6/5 baseline 
        // TODO: 2024/6/5 include

        //装载语言选择Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.language_array,
                R.layout.my_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.language);
        spinner.setAdapter(adapter);

        //设置大按钮的icon和text
        String[] texts = new String[]{"重置密码", "账号申诉", "冻结账号", "解冻账号", "解封账号", "注销账号"};
        for (int i = 1; i <= 6; i++) {
            String name = "button" + i;
            String icon_name = "icon" + i;
            int id = getResources().getIdentifier(name, "id", getPackageName());  //获取大按钮的标识符
            int icon_id = getResources().getIdentifier(icon_name, "drawable", getPackageName());  //获取icon标识符
            Log.d(TAG, "icon_id:" + icon_id);
            View button = findViewById(id);
            ImageView icon = button.findViewById(R.id.big_icon);
            icon.setImageResource(icon_id);
            TextView text = button.findViewById(R.id.big_text);
            text.setText(texts[i - 1]);
        }

        //设置"更多"的点击事件
        TextView more = findViewById(R.id.more);
        more.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "请访问小米官方客服中心获取更多信息", Toast.LENGTH_SHORT).show();
        });
    }
}