package com.test.aoner.fanow.test.util_flower;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil_flower {

    private final ExecutorService cachedThreadPool;
    private final Handler handler;

    private static class Inner {
        private static final ThreadUtil_flower instance = new ThreadUtil_flower();
    }

    private ThreadUtil_flower(){
        cachedThreadPool = Executors.newCachedThreadPool();
        handler = new Handler(Looper.getMainLooper());
    }

    public static ThreadUtil_flower getInstance(){
        return Inner.instance;
    }

    public void runOnChildThread(Runnable runnable){
        cachedThreadPool.execute(runnable);
    }

    public void runOnUiThread(Runnable runnable){
        handler.post(runnable);
    }

    public void postDelay(Runnable runnable, long delayMillis){
        handler.postDelayed(() -> {
            if (Looper.myLooper()!=Looper.getMainLooper()) runOnChildThread(runnable);
            else runnable.run();
        },delayMillis);
    }

}
