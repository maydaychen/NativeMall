package com.example.nativeMall.ui.Activity;

import android.os.Bundle;

import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.MallTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemMessageActivity extends InitActivity {

    @BindView(R.id.title_add_address)
    MallTitle mTitleAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @Override
    public void initView() {
        mTitleAddAddress.setLeftRightImgClickListener(new MallTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
    }

    @Override
    public void initData() {

    }
}
