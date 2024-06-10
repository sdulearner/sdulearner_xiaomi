package com.example.androidstudy;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

// TODO: 2024/6/10 leak:改为弱引用
public class CommonParamInterceptor implements Interceptor {
    private final WeakReference<Context> contextWeakReference;

    public CommonParamInterceptor(Context contextWeakReference) {
        this.contextWeakReference = new WeakReference<>(contextWeakReference);
    }

    private int getAppVersionCode() {
        try {
            Context context = contextWeakReference.get();
            if (context == null) {
                throw new IllegalStateException("Context is null");
            }
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        HttpUrl.Builder builder = chain.request().url().newBuilder();
        builder.addQueryParameter("appVersionCode", Integer.toString(getAppVersionCode()));
        return chain.proceed(chain.request());
    }
}
