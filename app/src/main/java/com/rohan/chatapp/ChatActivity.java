package com.rohan.chatapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rohan.chatapp.databinding.ActivityChatBinding;
import com.rohan.chatapp.util.Constants;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding mBinding;
    private String mUsername;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        Intent i = getIntent();
        mUsername = i.getStringExtra(Constants.USERNAME);
        mPassword = i.getStringExtra(Constants.PASSWORD);

        initUI();
    }

    private void initUI() {

    }
}
