package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.MallTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageCenterActivity extends InitActivity {

    @BindView(R.id.rl_my_order_title)
    MallTitle mRlMyOrderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @OnClick({R.id.rl_message_system_message, R.id
            .rl_message_tiezi_message, R.id.rl_message_huodong_message, R.id.ll_message_question})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_message_system_message:
                Intent system_message = new Intent(MessageCenterActivity.this,
                        SystemMessageActivity.class);
                startActivity(system_message);
                break;
            case R.id.rl_message_tiezi_message:
                Intent tiezi_message = new Intent(MessageCenterActivity.this,
                        TieziMessageActivity.class);
                startActivity(tiezi_message);
                break;
            case R.id.rl_message_huodong_message:
                Intent huodong_message = new Intent(MessageCenterActivity.this,
                        HuodongMessageActivity.class);
                startActivity(huodong_message);
                break;
            case R.id.ll_message_question:
                Intent intent = new Intent(MessageCenterActivity.this, MyQuestionAct.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initView() {
        mRlMyOrderTitle.setLeftRightImgClickListener(new MallTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
    }

    @Override
    public void initData() {

    }
}
