package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.CustomTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InquiryDetailAct extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.tv_title1)
    TextView tvTitle;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        tvTitle.setText(intent.getStringExtra("tvTitle"));
        tvDescribe.setText(intent.getStringExtra("tvDescribe"));
        tvAnswer.setText(intent.getStringExtra("tvAnswer"));


        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
    }
}
