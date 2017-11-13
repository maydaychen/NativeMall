package com.example.nativeMall.ui.Activity;

import android.os.Bundle;

import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.MallTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TieziMessageActivity extends InitActivity {

    @BindView(R.id.rl_my_order_title)
    MallTitle mRlMyOrderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiezi_message);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @Override
    public void initView() {
        mRlMyOrderTitle.setLeftRightImgClickListener(new MallTitle.LeftRightImgClickListener() {
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
