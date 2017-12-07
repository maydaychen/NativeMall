package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeActivity extends InitActivity {
    @BindView(R.id.iv_code)
    SmartImageView mIvCode;
    private SubscriberOnNextListener<JSONObject> codeOnNext;
    private SharedPreferences preferences;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
        codeOnNext = jsonObject -> {
            Log.i("chenyi", "initView: " + jsonObject.toString());
            if (jsonObject.getInt("statusCode") == 1) {
                mIvCode.setImageUrl(jsonObject.getString("result"));
            } else {
                Toast.makeText(CodeActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
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
        HttpJsonMethod.getInstance().getCode(
                new ProgressSubscriber(codeOnNext, CodeActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), sign, time);
    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onViewClicked() {
        finish();
    }
}
