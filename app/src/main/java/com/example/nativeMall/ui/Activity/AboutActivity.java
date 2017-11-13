package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.MallTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.rl_mine_title)
    MallTitle mRlMineTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        mTvVersion.setText(String.format(getResources().getString(R.string.current_version), Util.getVersionCode(this)));
        mRlMineTitle.setLeftRightImgClickListener(new MallTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
    }
//
//    @OnClick(R.id.iv_choose_doc_back)
//    public void onClick() {
//        finish();
//    }
}
