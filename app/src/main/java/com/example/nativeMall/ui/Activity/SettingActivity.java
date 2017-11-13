package com.example.nativeMall.ui.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends InitActivity {

    private static final String TAG = "SettingActivity";
    @BindView(R.id.tv_setting_logout)
    TextView mTvSettingLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @OnClick({R.id.iv_setting_back, R.id.tv_setting_change_password, R.id.tv_setting_message, R
            .id.tv_setting_fedback, R.id.tv_setting_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.tv_setting_change_password:
                Intent intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_setting_message:
                Intent message_setting = new Intent(SettingActivity.this, SettingMessageActivity
                        .class);
                startActivity(message_setting);
                break;
            case R.id.tv_setting_fedback:
                Util.startIntent(SettingActivity.this, FedbackActivity.class);

                break;
            case R.id.tv_setting_about:
                Util.startIntent(SettingActivity.this, AboutActivity.class);
                break;

        }
    }

    @Override
    public void initView() {
        if (Config.IS_LOG) {
            mTvSettingLogout.setVisibility(View.VISIBLE);
        }
        mTvSettingLogout.setOnClickListener(view -> {
            StopService();
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setMessage("确定要退出登录么");
            builder.setTitle("健康商城");

            builder.setPositiveButton("确认", (dialog, which) -> {
                //用户退出环信服务器：
                LogOutEM();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Config.IS_LOG = false;
                        File files = new File(getFilesDir(), "data.obj");
                        if (files.isFile()) {
                            files.delete();
                        }
                        Config.userBean = null;
                        dialog.dismiss();
                        mTvSettingLogout.setVisibility(View.GONE);

                        SharedPreferences mySharedPreferences = getSharedPreferences
                                ("patientlist",
                                        Activity.MODE_PRIVATE);
                        //实例化SharedPreferences.Editor对象（第二步）
                        mySharedPreferences.edit().clear().commit();
                        Config.patientBeanList.clear();
                        finish();
                    }
                }, 1500);


            });

            builder.setNegativeButton("取消", (dialog, which) -> {
                Intent intent = new Intent();
                intent.setAction("FreeAskResponse.service");
                Intent intent1 = new Intent(Util.createExplicitFromImplicitIntent
                        (SettingActivity.this, intent));
                startService(intent1);
                dialog.dismiss();
            });
            builder.create().show();
        });
    }

    private void StopService() {
        Intent intent = new Intent();
        intent.setAction("FreeAskResponse.service");
        Intent intent1 = new Intent(Util.createExplicitFromImplicitIntent(SettingActivity.this,
                intent));
        stopService(intent1);
    }

    @Override
    public void initData() {

    }

    private void LogOutEM() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "onSuccess: 下线成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "onError: 下线失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}