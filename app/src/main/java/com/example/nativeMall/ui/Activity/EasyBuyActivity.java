package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nativeMall.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EasyBuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_buy);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @OnClick({R.id.iv_choose_doc_back, R.id.chakanyaodian, R.id.paizhaogouyao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.chakanyaodian:
                break;
            case R.id.paizhaogouyao:
                break;
        }
    }
}
