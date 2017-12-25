package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.CouponDetailBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponDetailActivity extends InitActivity {
    @BindView(R.id.tv_coupon_name)
    TextView mTvCouponName;
    @BindView(R.id.tv_coupon_date)
    TextView mTvCouponDate;
    @BindView(R.id.tv_coupon_lijian)
    TextView mTvCouponLijian;
    @BindView(R.id.tv_coupon_available_num)
    TextView mTvCouponAvailableNum;
    @BindView(R.id.tv_coupon_detail)
    TextView mTvCouponDetail;
    private SharedPreferences preferences;
    private SubscriberOnNextListener<JSONObject> couponsDetailOnNext;
    private Gson gson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_coupon_detail);
        ButterKnife.bind(this);

        couponsDetailOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                CouponDetailBean couponDetailBean = gson.fromJson(jsonObject.toString(), CouponDetailBean.class);
                mTvCouponName.setText(couponDetailBean.getResult().getCouponname());
                mTvCouponDate.setText(String.format(getResources().getString(R.string.tv_order_useful_time), couponDetailBean.getResult().getTimestr()));
                mTvCouponLijian.setText(String.format(getResources().getString(R.string.fenxiao_lijian), couponDetailBean.getResult().get_backmoney()));
                mTvCouponAvailableNum.setText(String.format(getResources().getString(R.string.tv_coupon_available_num), couponDetailBean.getResult().getGetmax()));
                mTvCouponDetail.setText(couponDetailBean.getResult().getDesc());
            } else {
                Toast.makeText(CouponDetailActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        initDetail();
    }

    private void initDetail() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "couponid=" + getIntent().getStringExtra("id") + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().couponDetail(
                new ProgressSubscriber(couponsDetailOnNext, CouponDetailActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), getIntent().getStringExtra("id"), sign, time);
    }


    @OnClick({R.id.iv_choose_doc_back, R.id.tv_coupon_get_now, R.id.tv_coupon_detail_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.tv_coupon_get_now:
                break;
            case R.id.tv_coupon_detail_get:
                break;
            default:
                break;
        }
    }
}
