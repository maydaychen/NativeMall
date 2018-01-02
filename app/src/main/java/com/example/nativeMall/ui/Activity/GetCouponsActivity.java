package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.nativeMall.Adapter.CouponsAdapter;
import com.example.nativeMall.Bean.CouponsBean;
import com.example.nativeMall.Bean.CouponsTitleBean;
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

public class GetCouponsActivity extends InitActivity {
    @BindView(R.id.tb_coupons)
    TabLayout mTbCoupons;
    @BindView(R.id.rv_coupons)
    XRecyclerView mRvCoupons;
    private SubscriberOnNextListener<JSONObject> couponsTitleOnNext;
    private SubscriberOnNextListener<JSONObject> couponsListOnNext;
    private SharedPreferences preferences;
    private CouponsTitleBean mCouponsTitleBean;
    private Gson gson = new Gson();
    private int page = 0;
    private int status = 0;
    private CouponsAdapter couponsAdapter;
    private List<CouponsBean.ResultBean> mGoodsBeanList = new ArrayList<>();
    private List<Integer> mIntegerArrayList = new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_get_coupons);
        ButterKnife.bind(this);

        couponsTitleOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                mCouponsTitleBean = gson.fromJson(jsonObject.toString(), CouponsTitleBean.class);
                for (CouponsTitleBean.ResultBean resultBean : mCouponsTitleBean.getResult()) {
                    mTbCoupons.addTab(mTbCoupons.newTab().setText(resultBean.getName()));
                    mIntegerArrayList.add(resultBean.getId());
                }
            } else {
                Toast.makeText(GetCouponsActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(GetCouponsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponsAdapter = new CouponsAdapter(GetCouponsActivity.this, mGoodsBeanList);
        mRvCoupons.setLayoutManager(layoutManager);
        mRvCoupons.setAdapter(couponsAdapter);

        couponsListOnNext = jsonObject -> {
            mRvCoupons.refreshComplete();
            mRvCoupons.loadMoreComplete();
            if (jsonObject.getInt("statusCode") == 1) {
                CouponsBean couponsBean = gson.fromJson(jsonObject.toString(), CouponsBean.class);
                mGoodsBeanList.addAll(couponsBean.getResult());
                couponsAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent(GetCouponsActivity.this, CouponDetailActivity.class);
                    intent.putExtra("id", couponsBean.getResult().get(data).getId());
                    startActivity(intent);
                });
            } else {
                Toast.makeText(GetCouponsActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        mTbCoupons.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                status = mIntegerArrayList.get(tab.getPosition());
                mGoodsBeanList.clear();
                couponsAdapter.notifyDataSetChanged();
                page = 0;
                getCoupinsDetail();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mRvCoupons.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                couponsAdapter.clearData();
                couponsAdapter.notifyDataSetChanged();
                page = 0;
                getCoupinsDetail();
            }

            @Override
            public void onLoadMore() {
                page += 1;
                getCoupinsDetail();
            }
        });
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        getCoupinsTitle();
        getCoupinsDetail();
    }

    private void getCoupinsTitle() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().couponTitle(
                new ProgressSubscriber(couponsTitleOnNext, GetCouponsActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), sign, time);
    }

    private void getCoupinsDetail() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "catid=" + status + "&";
        sign = sign + "page=" + page + "&";
        sign = sign + "psize=10&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().usefulCouponList(
                new ProgressSubscriber(couponsListOnNext, GetCouponsActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), page + "", status + "", "10", sign, time);
    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onViewClicked() {
        finish();
    }
}
