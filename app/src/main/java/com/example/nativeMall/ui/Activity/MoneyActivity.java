package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.nativeMall.Adapter.CouponsAdapter;
import com.example.nativeMall.Bean.CouponsBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyActivity extends InitActivity {

    @BindView(R.id.tb_coupons)
    TabLayout mTbCoupons;
    @BindView(R.id.rv_coupons)
    XRecyclerView mRvCoupons;
    private SharedPreferences preferences;
    private SubscriberOnNextListener<JSONObject> couponsListOnNext;
    private int page = 0;
    private int status = 0;
    private Gson gson = new Gson();
    private CouponsAdapter couponsAdapter;
    private boolean IS_REFRESH = false;
    private boolean IS_LOAD = false;
    private List<CouponsBean.ResultBean> mGoodsBeanList = new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_money);
        ButterKnife.bind(this);
        mTbCoupons.setTabMode(TabLayout.MODE_FIXED);
        mTbCoupons.addTab(mTbCoupons.newTab().setText("待使用"));
        mTbCoupons.addTab(mTbCoupons.newTab().setText("已使用"));
        mTbCoupons.addTab(mTbCoupons.newTab().setText("已过期"));

        View header = LayoutInflater.from(this).inflate(R.layout.item_money_head, findViewById(android.R.id.content), false);
        mRvCoupons.addHeaderView(header);
        header.setOnClickListener(view -> startActivity(new Intent(MoneyActivity.this, GetCouponsActivity.class)));

        mTbCoupons.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                status = tab.getPosition();
                mGoodsBeanList.clear();
                couponsAdapter.notifyDataSetChanged();
                page = 0;
                getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MoneyActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponsAdapter = new CouponsAdapter(MoneyActivity.this,mGoodsBeanList);
        mRvCoupons.setLayoutManager(layoutManager);
        mRvCoupons.setAdapter(couponsAdapter);

        couponsListOnNext = jsonObject -> {
            mRvCoupons.refreshComplete();
            mRvCoupons.loadMoreComplete();
            Log.i("chenyi", "initData: " + jsonObject);
            if (jsonObject.getInt("statusCode") == 1) {
                CouponsBean couponsBean = gson.fromJson(jsonObject.toString(), CouponsBean.class);
                mGoodsBeanList.addAll(couponsBean.getResult());
                couponsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MoneyActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };

        mRvCoupons.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                IS_REFRESH = true;
                couponsAdapter.clearData();
                couponsAdapter.notifyDataSetChanged();
                page = 0;
                getData();
            }

            @Override
            public void onLoadMore() {
                IS_LOAD = true;
                page += 1;
                getData();
            }
        });
        getData();
    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onViewClicked() {
        finish();
    }

    private void getData() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "page=" + page + "&";
        sign = sign + "psize=10&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "status=" + status + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().couponList(
                new ProgressSubscriber(couponsListOnNext, MoneyActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), page + "", status + "", "10", sign, time);
    }
}
