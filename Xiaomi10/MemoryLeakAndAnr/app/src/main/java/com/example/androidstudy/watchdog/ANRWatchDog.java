package com.example.androidstudy.watchdog;

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Salomon BRYS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * A watchdog timer thread that detects when the UI thread has frozen.
 */
@SuppressWarnings("UnusedReturnValue")
public class ANRWatchDog extends Thread {

    public ANRWatchDog(int defaultHeartbeatInterval, int defaultAnrThresholdCount) {

    }

    public interface ANRListener {
        /**
         * Called when an ANR is detected.
         *
         * @param error The error describing the ANR.
         */
        void onAppNotResponding(ANRError error);
    }

    public interface ANRInterceptor {
        /**
         * Called when main thread has froze more time than defined by the timeout.
         *
         * @param duration The minimum time (in ms) the main thread has been frozen (may be more).
         * @return 0 or negative if the ANR should be reported immediately. A positive number of ms to postpone the reporting.
         */
        long intercept(long duration);
    }

    public interface InterruptionListener {
        void onInterrupted(InterruptedException exception);
    }

    //    private static final int DEFAULT_ANR_TIMEOUT = 5000;
    private static final int DEFAULT_ANR_THRESHOLD_COUNT = 5; // 新增的连续未响应次数阈值

    private static final int DEFAULT_HEARTBEAT_INTERVAL = 1000; // 心跳间隔设为1秒
    private static final ANRListener DEFAULT_ANR_LISTENER = new ANRListener() {
        @Override
        public void onAppNotResponding(ANRError error) {
            throw error;
        }
    };

    private static final ANRInterceptor DEFAULT_ANR_INTERCEPTOR = new ANRInterceptor() {
        @Override
        public long intercept(long duration) {
            return 0;
        }
    };

    private static final InterruptionListener DEFAULT_INTERRUPTION_LISTENER = new InterruptionListener() {
        @Override
        public void onInterrupted(InterruptedException exception) {
            Log.w("ANRWatchdog", "Interrupted: " + exception.getMessage());
        }
    };

    private ANRListener _anrListener = DEFAULT_ANR_LISTENER;
    private ANRInterceptor _anrInterceptor = DEFAULT_ANR_INTERCEPTOR;
    private InterruptionListener _interruptionListener = DEFAULT_INTERRUPTION_LISTENER;

    private final Handler _uiHandler = new Handler(Looper.getMainLooper());
    //    private final int _timeoutInterval;
    private final int _thresholdCount = DEFAULT_ANR_THRESHOLD_COUNT;
    private final Runnable _ticker = new Runnable() {

        @Override

        public void run() {

            if (_uiHandler.hasMessages(0)) {

                _unrespondedCount++; // 增加计数

            } else {

                _unrespondedCount = 0; // 清零

            }

        }

    };
    private int _heartbeatInterval;

    private int _unrespondedCount; // 新增计数器
    private String _namePrefix = "";
    private boolean _logThreadsWithoutStackTrace = false;
    private boolean _ignoreDebugger = false;

    private volatile long _tick = 0;
    private volatile boolean _reported = false;

    /**
     * Constructs a watchdog
     */
    public ANRWatchDog() {
        this(DEFAULT_HEARTBEAT_INTERVAL, DEFAULT_ANR_THRESHOLD_COUNT);
    }


    /**
     * Sets an interface for when an ANR is detected.
     * If not set, the default behavior is to throw an error and crash the application.
     *
     * @param listener The new listener or null
     * @return itself for chaining.
     */
    public ANRWatchDog setANRListener(ANRListener listener) {
        if (listener == null) {
            _anrListener = DEFAULT_ANR_LISTENER;
        } else {
            _anrListener = listener;
        }
        return this;
    }

    /**
     * Sets an interface to intercept ANRs before they are reported.
     * If set, you can define if, given the current duration of the detected ANR and external context, it is necessary to report the ANR.
     *
     * @param interceptor The new interceptor or null
     * @return itself for chaining.
     */
    public ANRWatchDog setANRInterceptor(ANRInterceptor interceptor) {
        if (interceptor == null) {
            _anrInterceptor = DEFAULT_ANR_INTERCEPTOR;
        } else {
            _anrInterceptor = interceptor;
        }
        return this;
    }

    /**
     * Sets an interface for when the watchdog thread is interrupted.
     * If not set, the default behavior is to just log the interruption message.
     *
     * @param listener The new listener or null.
     * @return itself for chaining.
     */
    public ANRWatchDog setInterruptionListener(InterruptionListener listener) {
        if (listener == null) {
            _interruptionListener = DEFAULT_INTERRUPTION_LISTENER;
        } else {
            _interruptionListener = listener;
        }
        return this;
    }

    /**
     * Set the prefix that a thread's name must have for the thread to be reported.
     * Note that the main thread is always reported.
     * Default "".
     *
     * @param prefix The thread name's prefix for a thread to be reported.
     * @return itself for chaining.
     */
    public ANRWatchDog setReportThreadNamePrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        _namePrefix = prefix;
        return this;
    }

    /**
     * Set that only the main thread will be reported.
     *
     * @return itself for chaining.
     */
    public ANRWatchDog setReportMainThreadOnly() {
        _namePrefix = null;
        return this;
    }

    /**
     * Set that all threads will be reported (default behaviour).
     *
     * @return itself for chaining.
     */
    public ANRWatchDog setReportAllThreads() {
        _namePrefix = "";
        return this;
    }

    /**
     * Set that all running threads will be reported,
     * even those from which no stack trace could be extracted.
     * Default false.
     *
     * @param logThreadsWithoutStackTrace Whether or not all running threads should be reported
     * @return itself for chaining.
     */
    public ANRWatchDog setLogThreadsWithoutStackTrace(boolean logThreadsWithoutStackTrace) {
        _logThreadsWithoutStackTrace = logThreadsWithoutStackTrace;
        return this;
    }

    /**
     * Set whether to ignore the debugger when detecting ANRs.
     * When ignoring the debugger, ANRWatchdog will detect ANRs even if the debugger is connected.
     * By default, it does not, to avoid interpreting debugging pauses as ANRs.
     * Default false.
     *
     * @param ignoreDebugger Whether to ignore the debugger.
     * @return itself for chaining.
     */
    public ANRWatchDog setIgnoreDebugger(boolean ignoreDebugger) {
        _ignoreDebugger = ignoreDebugger;
        return this;
    }

    @SuppressWarnings("NonAtomicOperationOnVolatileField")
    @Override
    public void run() {
        setName("|Optimized-ANR-WatchDog|");
        while (!isInterrupted()) {
            _tick += _heartbeatInterval;
            _uiHandler.post(_ticker);
            try {
                Thread.sleep(_heartbeatInterval);
            } catch (InterruptedException e) {
                _interruptionListener.onInterrupted(e);
                return;
            }
            // 检查是否达到未响应阈值
            if (_tick >= _heartbeatInterval * _thresholdCount && !_reported) {
                // 检查是否忽略调试器影响
                if (!_ignoreDebugger && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())) {
                    Log.w("OptimizedANRWatchdog", "An ANR was detected but ignored due to debugger connection.");
                    _reported = true;
                    continue;
                }
                // 拦截断检测
                long interval = _anrInterceptor.intercept(_tick);
                if (interval <= 0) {
                    final ANRError error = ANRError.New(_tick, _namePrefix, _logThreadsWithoutStackTrace);
                    _anrListener.onAppNotResponding(error);
                    _reported = true;
                    _tick = 0;
                    _unrespondedCount = 0; // 重置计数器
                } else {
                    continue;
                }
            }
        }
    }

}
