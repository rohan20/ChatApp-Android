package com.rohan.chatapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by rohan on 02/01/18.
 */

public class ApplicationController extends Application {

    private static Context mAppContext;

    public Context getInstance() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this.getApplicationContext();
    }
}
