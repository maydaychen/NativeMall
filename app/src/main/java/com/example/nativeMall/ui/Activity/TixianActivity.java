package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.TixianBean;
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

public class TixianActivity extends InitActivity {

    @BindView(R.id.tv_tixian_sale)
    TextView mTvTixianSale;
    @BindView(R.id.tv_tixian_manage)
    TextView mTvTixianManage;
    @BindView(R.id.tv_tixian_zhichu)
    TextView mTvTixianZhichu;
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    private SubscriberOnNextListener<JSONObject> tixianOnNext;
    private SharedPreferences preferences;
    private Gson gson = new Gson();
    private TixianBean mTixianBean;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tixian);
        ButterKnife.bind(this);
        tixianOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                Log.i("chenyi", "initView: " + jsonObject.toString());
                mTixianBean = gson.fromJson(jsonObject.toString(), TixianBean.class);
                mTvTixianSale.setText(String.format(getResources().getString(R.string.person_price), mTixianBean.getResult().getTotal().getC_money_sum()));
                mTvTixianManage.setText(String.format(getResources().getString(R.string.person_price), mTixianBean.getResult().getManage().getC_money_sum() + ""));
                mTvTixianZhichu.setText(String.format(getResources().getString(R.string.person_price), mTixianBean.getResult().getPay().getC_money_sum()));
                mTvTotalMoney.setText(String.format(getResources().getString(R.string.person_price), mTixianBean.getResult().getOk().getC_money_sum() + ""));
            } else {
                Toast.makeText(TixianActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().tixianList(
                new ProgressSubscriber(tixianOnNext, TixianActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), sign, time);
    }

    @OnClick({R.id.iv_choose_doc_back, R.id.rl_tixian_zhichu, R.id.tv_tixian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.rl_tixian_zhichu:
                break;
            case R.id.tv_tixian:
                if (mTixianBean.getResult().getOk().getC_money_sum()==0) {
                    Toast.makeText(this, "没有可提现金额", Toast.LENGTH_SHORT).show();
                }else {

                }
                break;
            default:
                break;
        }
    }
}
