package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.nativeMall.Bean.MemberBean;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FenxiaoActivity extends InitActivity {
    @BindView(R.id.tv_fenxiao_nicheng)
    TextView mTvFenxiaoNicheng;
    @BindView(R.id.tv_fenxiao_id)
    TextView mTvFenxiaoId;
    @BindView(R.id.tv_fenxiao_level)
    TextView mTvFenxiaoLevel;
    @BindView(R.id.tv_fenxiao_recommender)
    TextView mTvFenxiaoRecommender;
    @BindView(R.id.tv_fenxiao_tuiguangfei)
    TextView mTvFenxiaoTuiguangfei;
    @BindView(R.id.iv_fenxiao_logo)
    SmartImageView mIvFenxiaoLogo;
    private MemberBean.ResultBean mDataBean;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fenxiao);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        mDataBean = (MemberBean.ResultBean) getIntent().getSerializableExtra("member");
        mIvFenxiaoLogo.setImageUrl(mDataBean.getAvatar());
        mTvFenxiaoNicheng.setText(String.format(getResources().getString(R.string.fenxiao_nickname), mDataBean.getNickname()));
        mTvFenxiaoId.setText(String.format(getResources().getString(R.string.fenxiao_id), mDataBean.getId()));
        mTvFenxiaoLevel.setText(String.format(getResources().getString(R.string.fenxiao_level), mDataBean.getLeveldetail().getLevelname()));
        mTvFenxiaoRecommender.setText(String.format(getResources().getString(R.string.fenxiao_recommender), mDataBean.getInviter()));
        mTvFenxiaoTuiguangfei.setText(String.format(getResources().getString(R.string.fenxiao_recommend_money), mDataBean.getCredit1()));
    }
}
