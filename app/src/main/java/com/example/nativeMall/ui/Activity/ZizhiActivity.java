package com.example.nativeMall.ui.Activity;

import android.os.Bundle;

import com.example.nativeMall.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZizhiActivity extends InitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zizhi);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onClick() {
        finish();
    }
}
