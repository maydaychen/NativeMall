package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nativeMall.Bean.MemberBean;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick({R.id.rl_all_friend, R.id.rl_bought_friend, R.id.rl_no_friend, R.id.rl_all_promote, R.id.rl_weijiesuan, R.id.rl_yituikuan, R.id.rl_yijiesuan, R.id.rl_fenxiao_promote, R.id.rl_fenxiao_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_all_friend:
                Intent orders = new Intent(FenxiaoActivity.this, PatenerActivity.class);
                orders.putExtra("id", 0);
                startActivity(orders);
                break;
            case R.id.rl_bought_friend:
                break;
            case R.id.rl_no_friend:
                break;
            case R.id.rl_all_promote:
                break;
            case R.id.rl_weijiesuan:
                break;
            case R.id.rl_yituikuan:
                break;
            case R.id.rl_yijiesuan:
                break;
            case R.id.rl_fenxiao_promote:
                startActivity(new Intent(FenxiaoActivity.this, TixianActivity.class));
                break;
            case R.id.rl_fenxiao_code:
                startActivity(new Intent(FenxiaoActivity.this, CodeActivity.class));
                break;
            default:
                break;
        }
    }
}
