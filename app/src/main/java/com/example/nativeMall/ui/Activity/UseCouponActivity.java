package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
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

public class UseCouponActivity extends InitActivity {

    @BindView(R.id.rv_item_coupons)
    XRecyclerView mRvItemCoupons;
    @BindView(R.id.text_empty)
    TextView mTextEmpty;
    private CouponsAdapter couponsAdapter;
    private SharedPreferences preferences;
    private SubscriberOnNextListener<JSONObject> couponsListOnNext;
    private List<CouponsBean.ResultBean> mGoodsBeanList = new ArrayList<>();
    private Gson gson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_use_coupon);
        ButterKnife.bind(this);

        couponsListOnNext = jsonObject -> {
            mRvItemCoupons.refreshComplete();
            if (jsonObject.getInt("statusCode") == 1) {
                CouponsBean couponsBean = gson.fromJson(jsonObject.toString(), CouponsBean.class);
                mGoodsBeanList.addAll(couponsBean.getResult());
                couponsAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("objs", couponsBean.getResult().get(data));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                });
            } else if (jsonObject.getInt("statusCode") == 10008) {
            } else {
                Toast.makeText(UseCouponActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        mRvItemCoupons.setEmptyView(mTextEmpty);
        mRvItemCoupons.setLoadingMoreEnabled(false);
        mRvItemCoupons.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                couponsAdapter.clearData();
                couponsAdapter.notifyDataSetChanged();
                couponList();
            }

            @Override
            public void onLoadMore() {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(UseCouponActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponsAdapter = new CouponsAdapter(UseCouponActivity.this, mGoodsBeanList);
        mRvItemCoupons.setLayoutManager(layoutManager);
        mRvItemCoupons.setAdapter(couponsAdapter);
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        couponList();
    }

    private void couponList() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "goods=" + getIntent().getStringExtra("goods") + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().useCouponList(
                new ProgressSubscriber(couponsListOnNext, UseCouponActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), getIntent().getStringExtra("goods"), sign, time);
    }

    @OnClick(R.id.iv_choose_coupon_back)
    public void onViewClicked() {
        finish();
    }

}
