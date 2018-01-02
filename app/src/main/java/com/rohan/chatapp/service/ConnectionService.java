package com.rohan.chatapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rohan.chatapp.ApplicationController;
import com.rohan.chatapp.chat.ConnectionManager;
import com.rohan.chatapp.ui.ChatActivity;
import com.rohan.chatapp.util.Constants;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;

/**
 * Created by rohan on 02/01/18.
 */

public class ConnectionService extends IntentService {

    public ConnectionService() {
        super("ConnectionService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String username = intent.getStringExtra(Constants.USERNAME);
        String password = intent.getStringExtra(Constants.PASSWORD);
        String chatWith = intent.getStringExtra(Constants.CHAT_WITH);

        try {
            ConnectionManager.setupConnection(username, password);
        } catch (IOException | InterruptedException | XMPPException | SmackException e) {
            Log.v(getClass().getName(), "Exception: " + e.getMessage());
        }

        if (ApplicationController.connection.isAuthenticated()) {
            Intent i = new Intent(this, ChatActivity.class);
            i.putExtra(Constants.USERNAME, username);
            i.putExtra(Constants.PASSWORD, password);
            i.putExtra(Constants.CHAT_WITH, chatWith);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
}
