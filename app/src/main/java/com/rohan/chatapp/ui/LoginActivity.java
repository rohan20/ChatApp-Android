package com.rohan.chatapp.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rohan.chatapp.R;
import com.rohan.chatapp.databinding.ActivityLoginBinding;
import com.rohan.chatapp.service.ConnectionService;
import com.rohan.chatapp.util.Constants;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mBinding.bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ConnectionService.class);
                i.putExtra(Constants.USERNAME, mBinding.etEmail.getText().toString().trim());
                i.putExtra(Constants.PASSWORD, mBinding.etPassword.getText().toString().trim());
                i.putExtra(Constants.CHAT_WITH, mBinding.etChatWith.getText().toString().trim());
                startService(i);
            }
        });

    }
}
