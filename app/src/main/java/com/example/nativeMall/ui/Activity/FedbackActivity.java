package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nativeMall.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fedback);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @OnClick({R.id.iv_setting_back, R.id.tv_fedback_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.tv_fedback_commit:
                break;
        }
    }
}
