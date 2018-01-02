package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.MyOrderAdapter;
import com.example.nativeMall.Bean.OrderBean;
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

public class MyOrderActivity extends InitActivity {
    @BindView(R.id.tb_orders)
    TabLayout mTbOrders;
    @BindView(R.id.rv_orders)
    XRecyclerView mRvOrders;
    @BindView(R.id.text_empty)
    TextView mTextEmpty;
    private SharedPreferences preferences;
    private SubscriberOnNextListener<JSONObject> orderListOnNext;
    private MyOrderAdapter couponsAdapter;
    private int page = 1;
    private String status = "";
    private Gson gson = new Gson();
    private List<OrderBean.ResultBean> mGoodsBeanList = new ArrayList<>();

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
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        couponsAdapter = new MyOrderAdapter(MyOrderActivity.this, mGoodsBeanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrderActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvOrders.setLayoutManager(layoutManager);
        mRvOrders.setEmptyView(mTextEmpty);
        mRvOrders.setAdapter(couponsAdapter);

        mTbOrders.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    status = "";
                } else {
                    status = tab.getPosition() - 1 + "";
                }
                mGoodsBeanList.clear();
                couponsAdapter.notifyDataSetChanged();
                page = 1;
                getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        orderListOnNext = jsonObject -> {
            mRvOrders.refreshComplete();
            mRvOrders.loadMoreComplete();
            if (jsonObject.getInt("statusCode") == 1) {
                OrderBean orderBean = gson.fromJson(jsonObject.toString(), OrderBean.class);
                mGoodsBeanList.addAll(orderBean.getResult());
                couponsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MyOrderActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };

        mRvOrders.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mGoodsBeanList.clear();
                couponsAdapter.notifyDataSetChanged();
                page = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                page += 1;
                getData();
            }
        });
        if (getIntent().getIntExtra("id", 0)==0) {
            getData();
        }else {
            mTbOrders.getTabAt(getIntent().getIntExtra("id", 0)).select();
        }
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
        if (!"".equals(status)) {
            sign = sign + "status=" + status + "&";
        }
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().orderList(
                new ProgressSubscriber(orderListOnNext, MyOrderActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), page + "", status, "10", sign, time);
    }

}
