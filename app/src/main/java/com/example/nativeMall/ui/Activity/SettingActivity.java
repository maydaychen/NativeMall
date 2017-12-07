package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.nativeMall.Bean.MemberBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.SubscriberOnNextAndErrorListener;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends InitActivity {
    @BindView(R.id.iv_person_logo)
    SmartImageView mIvPersonLogo;
    @BindView(R.id.et_person_nicheng)
    EditText mEtPersonNicheng;
    @BindView(R.id.et_person_mobile)
    EditText mEtPersonMobile;
    @BindView(R.id.et_person_wechat)
    EditText mEtPersonWechat;
    @BindView(R.id.et_person_ali_user)
    EditText mEtPersonAliUser;
    @BindView(R.id.et_person_ali_name)
    EditText mEtPersonAliName;
    @BindView(R.id.et_person_real_name)
    EditText mEtPersonRealName;
    private MemberBean.ResultBean mDataBean;
    private SubscriberOnNextAndErrorListener<JSONObject> saveOnNext;
    private SharedPreferences preferences;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mDataBean = (MemberBean.ResultBean) getIntent().getSerializableExtra("member");
        mIvPersonLogo.setImageUrl(mDataBean.getAvatar());
        mEtPersonNicheng.setText(mDataBean.getNickname());
        mEtPersonMobile.setText(mDataBean.getMobile());
        mEtPersonWechat.setText(mDataBean.getWeixin());
        mEtPersonRealName.setText(mDataBean.getRealname());
        saveOnNext = new SubscriberOnNextAndErrorListener<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) throws JSONException {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    private void saveInfo() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "fields=id,thumb,title,productprice,marketprice&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
//        HttpJsonMethod.getInstance().history_list(
//                new ProgressErrorSubscriber<>(saveOnNext, SettingActivity.this),
//                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
//                page, sign, time);
    }

    @OnClick({R.id.iv_choose_doc_back, R.id.rl_person_logo, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.rl_person_logo:
                break;
            case R.id.button:
                break;
        }
    }
}
