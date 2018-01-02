package com.rohan.chatapp.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.offline.OfflineMessageManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        mBinding.bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApplicationController.connection.isAuthenticated()) {
                    try {
                        ChatManager chatManager = ChatManager.getInstanceFor(ApplicationController.connection);

                        chatManager.addIncomingListener(new IncomingChatMessageListener() {
                            @Override
                            public void newIncomingMessage(final EntityBareJid from, final Message message, Chat chat) {
                                Log.v("Chat msg received", message + " localPart: " + from.getLocalpart() + " domain: " + from.getDomain());
                            }
                        });

                        Chat chat = chatManager.chatWith(JidCreate.entityBareFrom(getIntent().getStringExtra(Constants.CHAT_WITH)));
                        chat.send(mBinding.etMessage.getText().toString().trim());

                    } catch (XmppStringprepException | SmackException.NotConnectedException | InterruptedException e) {
                        Log.v("Send chat", "Exception: " + e.getMessage());
                    }
                }
            }
        });
    }


}
