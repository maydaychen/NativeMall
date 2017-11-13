package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.CustomTitle;

import butterknife.ButterKnife;

public class OnlineInquiryAct extends AppCompatActivity implements View.OnClickListener {


    RelativeLayout rlFreeAsk, rlSpecifyAsk, rlQualityAsk;

    private CustomTitle customTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_inquiry);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        Config.ToDoctListFlag = 2;
        InitView();

    }

    private void InitView() {

        customTitle = (CustomTitle) findViewById(R.id.custom_title);
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
        rlFreeAsk = (RelativeLayout) findViewById(R.id.rl_free_ask);
        rlSpecifyAsk = (RelativeLayout) findViewById(R.id.rl_specify_ask);
        rlQualityAsk = (RelativeLayout) findViewById(R.id.rl_quality_ask);

        rlFreeAsk.setOnClickListener(this);
        rlSpecifyAsk.setOnClickListener(this);
        rlQualityAsk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_free_ask:
                Intent intent = new Intent(OnlineInquiryAct.this, FreeAskAct.class);
                startActivity(intent);
                break;
            case R.id.rl_specify_ask:
                Intent intent1 = new Intent(OnlineInquiryAct.this, DoctListAct.class);
                startActivity(intent1);
                break;
            case R.id.rl_quality_ask:
                Intent intent2 = new Intent(OnlineInquiryAct.this, QualityAskAct.class);
                startActivity(intent2);
                break;

        }
    }


}
