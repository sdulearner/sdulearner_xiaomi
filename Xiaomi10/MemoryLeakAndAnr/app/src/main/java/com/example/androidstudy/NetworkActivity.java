package com.example.androidstudy;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class NetworkActivity extends Activity {
    private static final String TAG = "NetworkActivity";
    private static final int MSG_UPDATE_TIME_TICKER = 1;

    // TODO: 2024/6/10 leak: 匿名内部类Handler持有外部类Activity的引用，
    //  Activity在sendMessageDelayed之前销毁就会造成内存泄漏，
    //  通过在onDestroy时调用removeCallbacksAndMessages()方法避免内存泄漏
    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_UPDATE_TIME_TICKER) {
                timeNumber = timeNumber - 1;
                updateTickerText();
                if (timeNumber >= 1) {
                    sendMessageDelayed(Message.obtain(this, 1), 1000);
                }
            }
        }
    };

    private TextView textBody;
    private TextView timeTicker;
    private int timeNumber = 120;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        findViewById(R.id.getWithCallback).setOnClickListener(v -> getWithCallback());
        findViewById(R.id.retrofitGet).setOnClickListener(v -> retrofitGet());
        textBody = findViewById(R.id.body);
        timeTicker = findViewById(R.id.timeTicker);
        updateTickerText();
        handler.sendMessageDelayed(Message.obtain(handler, MSG_UPDATE_TIME_TICKER), 1000);

    }

    private void updateTickerText() {
        timeTicker.setText("倒计时：" + timeNumber + "s");
    }

    private void retrofitGet() {
        retrofit2.Call<CommonData<GameItem>> commonDataCall = ServerApiManager.getInstance(this)
                .getApiService().queryGame("109");
        // TODO: 2024/6/10 leak:Callback中使用当前Activity的弱引用，避免Activity被销毁了Callback还在执行造成内存泄漏
//        WeakReference<NetworkActivity> activityWeakReference = new WeakReference<>(this);
        commonDataCall.enqueue(new retrofit2.Callback<CommonData<GameItem>>() {
            @Override
            public void onResponse(retrofit2.Call<CommonData<GameItem>> call, retrofit2.Response<CommonData<GameItem>> response) {
                // TODO: 2024/6/10 leak:
//                NetworkActivity networkActivity = activityWeakReference.get();
                CommonData<GameItem> body = response.body();
//                networkActivity.handler.post(() -> networkActivity.textBody.setText(body.toString()));
                handler.post(() -> textBody.setText(body.toString()));
            }

            @Override
            public void onFailure(retrofit2.Call<CommonData<GameItem>> call, Throwable t) {

            }
        });
    }

    private void getWithCallback() {
        Request request = new Request.Builder().get().url("https://hotfix-service-prod.g.mi.com/quick-game/game/109").build();
        Call call = ServerApiManager.getInstance(this).getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "get onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String string = response.body().string();
                fromJson(string);
                Log.d(TAG, "get body: " + string);
                Log.d(TAG, "get headers: " + response.headers().toMultimap());
                handler.post(() -> textBody.setText(string));
            }
        });

    }

    public void fromJson(String jsonStr) {
        Gson gson = ServerApiManager.getInstance(this).getGson();
        Data data = gson.fromJson(jsonStr, Data.class);
        Log.d(TAG, "fromJson: data " + data);
        CommonData<GameItem> commonData = gson.fromJson(jsonStr, new TypeToken<CommonData<GameItem>>() {
        });
        Log.d(TAG, "fromJson: commonData " + commonData);
    }
}