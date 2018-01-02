package com.rohan.chatapp;

import android.app.Application;
import android.content.Context;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.chat2.Chat;

/**
 * Created by rohan on 02/01/18.
 */

public class ApplicationController extends Application {

    private static Context mAppContext;
    public static AbstractXMPPConnection connection;

    public static Context getInstance() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this.getApplicationContext();
    }
}
