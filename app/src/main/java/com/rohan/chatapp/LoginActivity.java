package com.rohan.chatapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rohan.chatapp.databinding.ActivityLoginBinding;
import com.rohan.chatapp.util.Constants;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Intent i = new Intent(LoginActivity.this, ChatActivity.class);
        i.putExtra(Constants.USERNAME, mBinding.etEmail.getText().toString().trim());
        i.putExtra(Constants.PASSWORD, mBinding.etPassword.getText().toString().trim());
        startActivity(i);

    }
}
