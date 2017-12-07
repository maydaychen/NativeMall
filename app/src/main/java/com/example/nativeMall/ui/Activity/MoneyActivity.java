package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;

import com.example.nativeMall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyActivity extends InitActivity {

    @BindView(R.id.tb_coupons)
    TabLayout mTbCoupons;
    @BindView(R.id.rv_coupons)
    RecyclerView mRvCoupons;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_money);
        ButterKnife.bind(this);
        mTbCoupons.setTabMode(TabLayout.MODE_FIXED);
        mTbCoupons.addTab(mTbCoupons.newTab().setText("待使用"));
        mTbCoupons.addTab(mTbCoupons.newTab().setText("已使用"));
        mTbCoupons.addTab(mTbCoupons.newTab().setText("已过期"));
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onViewClicked() {
        finish();
    }
}
