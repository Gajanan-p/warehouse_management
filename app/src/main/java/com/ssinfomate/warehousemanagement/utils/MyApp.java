package com.ssinfomate.warehousemanagement.utils;

import android.app.Application;

import com.ssinfomate.warehousemanagement.utils.LogOutListener;

import java.util.Timer;
import java.util.TimerTask;


public class MyApp extends Application {


    private LogOutListener listener;
    private Timer timer;

    public void startUserSession() {

        cancelTime();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                listener.onSessionLogOut();
            }
        },420000);//300000
    }

    public void cancelTime() {
        if (timer != null)
            timer.cancel();
    }

    public void registerSessionListener(LogOutListener listener) {
        this.listener = listener;
    }

    public void onUserInteracted() {
        startUserSession();
    }

}
