package com.example.xiaomi2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;


import androidx.annotation.Nullable;

public class CalculatorService extends Service {
    private final ICalculatorInterface.Stub mBinder = new ICalculatorInterface.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    };

    public CalculatorService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
