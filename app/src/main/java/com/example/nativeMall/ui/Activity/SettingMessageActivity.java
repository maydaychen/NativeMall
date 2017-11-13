package com.example.nativeMall.ui.Activity;

import android.os.Bundle;

import com.example.nativeMall.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingMessageActivity extends InitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @OnClick(R.id.iv_setting_back)
    public void onClick() {
        finish();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
