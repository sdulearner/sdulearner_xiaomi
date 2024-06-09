package com.example.xiaomi9.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xiaomi9.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scanSdWithCheckPermission();
                Intent intent = new Intent(MainActivity.this, NetworkActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(intent);
    }

    private void startCameraWithCheckPermission() {
        int flag = checkSelfPermission(android.Manifest.permission.CAMERA);
        if (flag == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 200);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
                Toast.makeText(this, "申请权限成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "申请权限失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 写sd卡 todo:没调用
    private void scanSd() {
        File file = Environment.getExternalStorageDirectory();
        File[] files = file.listFiles();
        Log.i(TAG, "sd files: " + Arrays.toString(files));
        File text = new File(file, "aaaaa.txt");
        if (text.exists()) {
            boolean delete = text.delete();
            Log.i(TAG, "delete file:" + delete);
            try {
                FileWriter fileWriter = new FileWriter(text);
                fileWriter.write("hello sd card!!!");
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void scanSdWithCheckPermission() {
        int flag = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (flag == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "有sd卡写权限", Toast.LENGTH_SHORT).show();
            scanSd();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
        }
    }
}