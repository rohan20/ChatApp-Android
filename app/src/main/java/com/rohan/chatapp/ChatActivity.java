package com.rohan.chatapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rohan.chatapp.databinding.ActivityChatBinding;
import com.rohan.chatapp.util.Constants;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.DomainpartJid;
import org.jxmpp.stringprep.XmppStringprepException;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding mBinding;
    private String mUsername;
    private String mPassword;

    private String getActivityName() {
        return getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        Intent i = getIntent();
        mUsername = i.getStringExtra(Constants.USERNAME);
        mPassword = i.getStringExtra(Constants.PASSWORD);

        try {
            initUI();
        } catch (XmppStringprepException e) {
            Log.v(getClass().getName(), "Exception: " + e.getMessage());
        }
    }

    private void initUI() throws XmppStringprepException {

        XMPPTCPConnectionConfiguration.Builder configuration = XMPPTCPConnectionConfiguration.builder();
        configuration.setUsernameAndPassword(mUsername, mPassword);
        configuration.setHost(Constants.HOSTNAME);
        configuration.setPort(Constants.PORT);
        configuration.setXmppDomain("@" + Constants.SERVICE_NAME);
        configuration.setSendPresence(false);
        configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configuration.setDebuggerEnabled(true);

        ApplicationController.connection = new XMPPTCPConnection(configuration.build());
        ApplicationController.connection.addConnectionListener(new ConnectionListener() {
            @Override
            public void connected(XMPPConnection connection) {
                Log.v(getActivityName(), "connected");
            }

            @Override
            public void authenticated(XMPPConnection connection, boolean resumed) {
                Log.v(getActivityName(), "authenticated");
            }

            @Override
            public void connectionClosed() {
                Log.v(getActivityName(), "connectionClosed");
            }

            @Override
            public void connectionClosedOnError(Exception e) {
                Log.v(getActivityName(), "connectionClosedOnError");
            }

            @Override
            public void reconnectionSuccessful() {
                Log.v(getActivityName(), "reconnectionSuccessful");
            }

            @Override
            public void reconnectingIn(int seconds) {
                Log.v(getActivityName(), "reconnectingIn");
            }

            @Override
            public void reconnectionFailed(Exception e) {
                Log.v(getActivityName(), "reconnectionFailed");
            }
        });

    }
}
