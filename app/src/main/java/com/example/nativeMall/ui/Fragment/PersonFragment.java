package com.example.nativeMall.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Activity.DateListAct;
import com.example.nativeMall.ui.Activity.LoginActivity;
import com.example.nativeMall.ui.Activity.MessageCenterActivity;
import com.example.nativeMall.ui.Activity.MyAddressActivity;
import com.example.nativeMall.ui.Activity.MyOrderActivity;
import com.example.nativeMall.ui.Activity.MyShoucangActivity;
import com.example.nativeMall.ui.Activity.SettingActivity;
import com.loopj.android.image.SmartImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：JTR on 2016/9/23 09:31
 * 邮箱：2091320109@qq.com
 */
public class PersonFragment extends Fragment {
    private static final String EXTRA_CONTENT = "content";
    @BindView(R.id.iv_person_shezhi)
    ImageView mIvPersonShezhi;
    @BindView(R.id.tv_person_username)
    TextView mTvPersonUsername;
    @BindView(R.id.iv_my_logo)
    SmartImageView ivMyLogo;

    public static PersonFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        PersonFragment personFragment = new PersonFragment();
        personFragment.setArguments(arguments);
        return personFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    public void initView() {
        mIvPersonShezhi.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        });

        ivMyLogo.setOnClickListener(v -> {
            if (!Config.IS_LOG) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mTvPersonUsername.setText(Config.IS_LOG ? Config.userBean.getData().getUsername() : "请登录");
    }

    @OnClick({R.id.rl_person_jiankangka, R.id.rl_person_zhifu, R.id.rl_person_date, R.id
            .rl_person_message, R.id.rl_person_order, R.id.rl_person_address, R.id.rl_person_shoucang, R.id.iv_my_logo})
    public void onClick(View view) {
        if (Config.IS_LOG) {
            switch (view.getId()) {
                case R.id.rl_person_jiankangka:
                    break;
                case R.id.rl_person_zhifu:
                    break;
                case R.id.rl_person_date:
                    Util.startIntent(getActivity(), DateListAct.class);
                    break;
                case R.id.rl_person_message:
                    Util.startIntent(getActivity(), MessageCenterActivity.class);
                    break;
                case R.id.rl_person_order:
                    Util.startIntent(getActivity(), MyOrderActivity.class);
                    break;
                case R.id.rl_person_address:
                    Util.startIntent(getActivity(), MyAddressActivity.class);
                    break;
                case R.id.rl_person_shoucang:
                    Util.startIntent(getActivity(), MyShoucangActivity.class);
                    break;
            }
        } else {
            Util.show(getActivity());
        }
    }
}
