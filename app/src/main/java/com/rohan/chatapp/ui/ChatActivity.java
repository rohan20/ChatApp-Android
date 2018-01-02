package com.rohan.chatapp.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rohan.chatapp.ApplicationController;
import com.rohan.chatapp.R;
import com.rohan.chatapp.databinding.ActivityChatBinding;
import com.rohan.chatapp.util.Constants;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding mBinding;
    private String mUsername;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        try {
            setupConnection(getIntent().getStringExtra(Constants.USERNAME), getIntent().getStringExtra(Constants.PASSWORD));
        } catch (IOException | InterruptedException | XMPPException | SmackException e) {
            Log.v(getClass().getName(), "Exception: " + e.getMessage());
        }

        mBinding.bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApplicationController.connection.isConnected()) {
                    try {
                        Chat chat = ChatManager.getInstanceFor(ApplicationController.connection).chatWith(JidCreate.entityBareFrom("rohan2@sportsstart.local"));
                        chat.send(mBinding.etMessage.getText().toString().trim());
                    } catch (XmppStringprepException | SmackException.NotConnectedException | InterruptedException e) {
                        Log.v("Send chat", "Exception: " + e.getMessage());
                    }
                }
            }
        });
    }

    public static void setupConnection(String username, String password) throws IOException, InterruptedException, XMPPException, SmackException {

        Log.v("Chat", "inside setupConnection");

        XMPPTCPConnectionConfiguration.Builder configuration = XMPPTCPConnectionConfiguration.builder();
        configuration.setUsernameAndPassword(username, password);
//        configuration.setHost(Constants.HOSTNAME);
        configuration.setHostAddress(InetAddress.getByName(Constants.HOSTNAME));
        configuration.setPort(Constants.PORT);
        configuration.setXmppDomain("@" + Constants.SERVICE_NAME);
        configuration.setSendPresence(false);
        configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configuration.setDebuggerEnabled(true);

        ApplicationController.connection = new XMPPTCPConnection(configuration.build());

        ApplicationController.connection.addConnectionListener(new ConnectionListener() {
            @Override
            public void connected(XMPPConnection connection) {
                Log.v("Chat", "connected");
            }

            @Override
            public void authenticated(XMPPConnection connection, boolean resumed) {
                Log.v("Chat", "authenticated");

            }

            @Override
            public void connectionClosed() {
                Log.v("Chat", "connectionClosed");
            }

            @Override
            public void connectionClosedOnError(Exception e) {
                Log.v("Chat", "connectionClosedOnError");
            }

            @Override
            public void reconnectionSuccessful() {
                Log.v("Chat", "reconnectionSuccessful");
            }

            @Override
            public void reconnectingIn(int seconds) {
                Log.v("Chat", "reconnectingIn");
            }

            @Override
            public void reconnectionFailed(Exception e) {
                Log.v("Chat", "reconnectionFailed");
            }
        });


        if (!ApplicationController.connection.isConnected()) {
            ApplicationController.connection.connect();
        }
        ApplicationController.connection.login();

    }
}
