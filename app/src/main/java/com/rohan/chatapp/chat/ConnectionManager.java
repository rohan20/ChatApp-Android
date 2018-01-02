package com.rohan.chatapp.chat;

import android.util.Log;

import com.rohan.chatapp.ApplicationController;
import com.rohan.chatapp.util.Constants;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * Created by rohan on 02/01/18.
 */

public class ConnectionManager {

    public static void setupConnection(String username, String password) throws IOException, InterruptedException, XMPPException, SmackException {

        Log.v("Chat", "inside setupConnection");

        XMPPTCPConnectionConfiguration.Builder configuration = XMPPTCPConnectionConfiguration.builder();
        configuration.setUsernameAndPassword(username, password);
        configuration.setHostAddress(InetAddress.getByName(Constants.HOSTNAME));
        configuration.setPort(Constants.PORT);
        configuration.setSendPresence(true);
        configuration.setXmppDomain("@" + Constants.SERVICE_NAME);
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

        OfflineMessageManager offlineMessageManager = new OfflineMessageManager(ApplicationController.connection);
        int size = offlineMessageManager.getMessageCount();
        List<Message> messageList = offlineMessageManager.getMessages();
        Log.v("Offline messages (" + size + "): ", messageList.toString());
    }
}
