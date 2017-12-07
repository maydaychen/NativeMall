package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;

import com.example.nativeMall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends InitActivity {
    @BindView(R.id.tb_orders)
    TabLayout mTbOrders;
    @BindView(R.id.rv_orders)
    RecyclerView mRvOrders;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        mTbOrders.setTabMode(TabLayout.MODE_FIXED);
        mTbOrders.addTab(mTbOrders.newTab().setText("全部"));
        mTbOrders.addTab(mTbOrders.newTab().setText("待付款"));
        mTbOrders.addTab(mTbOrders.newTab().setText("待发货"));
        mTbOrders.addTab(mTbOrders.newTab().setText("待收货"));
        mTbOrders.addTab(mTbOrders.newTab().setText("已完成"));
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onViewClicked() {
    }
}
