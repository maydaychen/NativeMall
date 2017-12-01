package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.nativeMall.Adapter.MyShoucangAdapter;
import com.example.nativeMall.Bean.ShoucangBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressErrorSubscriber;
import com.example.nativeMall.http.SubscriberOnNextAndErrorListener;
import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyShoucangActivity extends InitActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    @BindView(R.id.rv_my_shoucang)
    PullLoadMoreRecyclerView mRvMyShoucang;

    private SharedPreferences preferences;
    private SubscriberOnNextAndErrorListener<JSONObject> shoucangOnNext;
    private Gson mGson = new Gson();
    private MyShoucangAdapter mMyShoucangAdapter;
    private int page = 1;

    @Override
    protected void onResume() {
        super.onResume();
        setRefresh();
        initShoucang();
    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onClick() {
        finish();
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_shoucang);
        ButterKnife.bind(this);

        RecyclerView recyclerView = mRvMyShoucang.getRecyclerView();
        recyclerView.setVerticalScrollBarEnabled(true);
        mRvMyShoucang.setRefreshing(false);
        mRvMyShoucang.setPullRefreshEnable(true);
        mRvMyShoucang.setPushRefreshEnable(true);
        mRvMyShoucang.setFooterViewText("正在加载，请稍后");
        mRvMyShoucang.setFooterViewTextColor(R.color.font_gray);
        mRvMyShoucang.setFooterViewBackgroundColor(R.color.ccccc);
        mRvMyShoucang.setLinearLayout();

        mRvMyShoucang.setOnPullLoadMoreListener(this);
        mRvMyShoucang.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_order, null));
        mMyShoucangAdapter = new MyShoucangAdapter(this);
        mRvMyShoucang.setAdapter(mMyShoucangAdapter);

    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        shoucangOnNext = new SubscriberOnNextAndErrorListener<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) throws JSONException {
                if (jsonObject.getInt("statusCode") == 1) {
                    ShoucangBean indexBean = mGson.fromJson(jsonObject.toString(), ShoucangBean.class);
                    mRvMyShoucang.setVisibility(View.VISIBLE);
                    try {
                        mRvMyShoucang.setPullLoadMoreCompleted();
                        mMyShoucangAdapter.addAllData(indexBean.getResult());
                        mMyShoucangAdapter.notifyDataSetChanged();
                        mMyShoucangAdapter.setOnItemClickListener((view, data) -> {
                            Intent intent = new Intent(MyShoucangActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("id", indexBean.getResult().get(data).getId());
                            startActivity(intent);
                        });
                    } catch (NullPointerException e) {
                        if (page != 0) {
                            Toast.makeText(MyShoucangActivity.this, "已经加载完毕！", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(MyShoucangActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                mRvMyShoucang.setVisibility(View.VISIBLE);
                mRvMyShoucang.setPullLoadMoreCompleted();
            }
        };
    }

    @Override
    public void onRefresh() {
        setRefresh();
        initShoucang();
    }

    private void setRefresh() {
        mMyShoucangAdapter.clearData();
        mMyShoucangAdapter.notifyDataSetChanged();
        page = 1;
        mRvMyShoucang.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        initShoucang();
    }

    private void initShoucang() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "fields=id,thumb,title,productprice,marketprice&";
        sign = sign + "page=" + page + "&";
        sign = sign + "psize=" + 10 + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().shoucang_list(
                new ProgressErrorSubscriber<>(shoucangOnNext, MyShoucangActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
                page, sign, time);
    }
}
