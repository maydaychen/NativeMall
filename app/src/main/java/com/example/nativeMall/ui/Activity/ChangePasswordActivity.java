package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends InitActivity {

    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.change_pass_yanzhengma)
    EditText mChangePassYanzhengma;
    @BindView(R.id.et_change_address_old_pass)
    EditText mEtChangeAddressOldPass;
    @BindView(R.id.et_change_address_new_pass)
    EditText mEtChangeAddressNewPass;
    @BindView(R.id.et_change_address_new_pass_repeat)
    EditText mEtChangeAddressNewPassRepeat;
    @BindView(R.id.change_pass_tele)
    TextView mChangePassTele;

    private Gson mGson = new Gson();
    private String codeID;
    private final Handler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @OnClick({R.id.iv_setting_back, R.id.change_pass_commit, R.id.change_pass_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.change_pass_commit:
                Map<String, String> param1 = new HashMap<>();
                param1.put("phone", Config.userBean.getData().getMobile());
                param1.put("octopusApiid", Config.SEND_MSG_TOKEN);
                param1.put("oldPwd", mEtChangeAddressOldPass.getText().toString());
                param1.put("newPwd", mEtChangeAddressNewPass.getText().toString());
                param1.put("code", mChangePassYanzhengma.getText().toString());
                param1.put("uid", Config.userBean.getData().getUid());
                param1.put("codeid", codeID);
                Http.getInstance().init(ChangePasswordActivity.this, mHandler, mGson.toJson(param1), "index/ resetpwd", 1).sendMsg();
                break;
            case R.id.change_pass_send:
                Map<String, String> param = new HashMap<>();
                param.put("phone", "18301721508");
//                param.put("phone", Config.userBean.getData().getMobile());
//                param.put("octopusApiid", Config.SEND_MSG_TOKEN);
                Http.getInstance().init(ChangePasswordActivity.this, mHandler, mGson.toJson(param), "health/shortmsg", 0).sendMsg();
                break;
        }
    }

    @Override
    public void initView() {
        String mobile = Config.userBean.getData().getMobile();
        String result = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        mChangePassTele.setText(result);
    }

    @Override
    public void initData() {
    }

    private void ChangePass(String data) throws JSONException {
        Util.showMsg(ChangePasswordActivity.this, data);
        JSONObject json;
        json = new JSONObject(data);
        codeID = json.getJSONObject("data").getString("codeid");
    }

    public void showMsg(String data) throws JSONException {
        JSONObject json;
        json = new JSONObject(data);
        Toast.makeText(ChangePasswordActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();

    }

    static class MyHandler extends Handler {
        WeakReference<ChangePasswordActivity> mActivityReference;

        MyHandler(ChangePasswordActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ChangePasswordActivity activity = mActivityReference.get();
            if (activity != null) {
                String data = msg.obj.toString();
                try {
                    switch (msg.what) {
                        case 0:
                            activity.ChangePass(data);
                            break;
                        case 1:
                            activity.showMsg(data);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
