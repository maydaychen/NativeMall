package com.example.nativeMall.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.MemberBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.example.nativeMall.ui.Activity.FenxiaoActivity;
import com.example.nativeMall.ui.Activity.LoginActivity;
import com.example.nativeMall.ui.Activity.MoneyActivity;
import com.example.nativeMall.ui.Activity.MyAddressActivity;
import com.example.nativeMall.ui.Activity.MyHistoryActivity;
import com.example.nativeMall.ui.Activity.MyOrderActivity;
import com.example.nativeMall.ui.Activity.MyShoucangActivity;
import com.example.nativeMall.ui.Activity.SettingActivity;
import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：JTR on 2016/9/23 09:31
 * 邮箱：2091320109@qq.com
 */
public class PersonFragment extends Fragment {
    private static final String EXTRA_CONTENT = "content";
    @BindView(R.id.tv_person_id)
    TextView mTvPersonId;
    @BindView(R.id.tv_person_price)
    TextView mTvPersonPrice;
    private SharedPreferences preferences;
    private MemberBean indexBean;
    private SubscriberOnNextListener<JSONObject> infoOnNext;
    private Gson mGson = new Gson();

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
        initInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, root);
        initData();
        initView();
        return root;
    }

    public void initData() {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        infoOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                indexBean = mGson.fromJson(jsonObject.toString(), MemberBean.class);
                mTvPersonUsername.setText(indexBean.getResult().getNickname());
                mTvPersonId.setText(String.format(getActivity().getResources().getString(R.string.person_id), indexBean.getResult().getId()));
                mTvPersonPrice.setText(String.format(getActivity().getResources().getString(R.string.person_price), indexBean.getResult().getCredit1()));
                ivMyLogo.setImageUrl(indexBean.getResult().getAvatar());
            } else {
                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void initView() {
        mIvPersonShezhi.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("member", indexBean.getResult());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        ivMyLogo.setOnClickListener(v -> {
            if (!Config.IS_LOG) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initInfo() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().member_info(
                new ProgressSubscriber(infoOnNext, getContext()),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), sign, time);
    }

    @OnClick({R.id.rl_person_orders, R.id.rl_all_order, R.id.rl_daizhifu, R.id.rl_daifahuo, R.id.rl_yifahuo, R.id.rl_yiwancheng, R.id.rl_person_fenxiao, R.id.rl_person_invite, R.id.rl_person_money, R.id.rl_person_address, R.id.rl_person_collect, R.id.rl_person_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_person_orders:
                Intent orders = new Intent(getActivity(), MyOrderActivity.class);
                orders.putExtra("id",0);
                startActivity(orders);
                break;
            case R.id.rl_all_order:
                break;
            case R.id.rl_daizhifu:
                break;
            case R.id.rl_daifahuo:
                break;
            case R.id.rl_yifahuo:
                break;
            case R.id.rl_yiwancheng:
                break;
            case R.id.rl_person_fenxiao:
                Intent intent = new Intent(getActivity(), FenxiaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("member", indexBean.getResult());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.rl_person_invite:
                break;
            case R.id.rl_person_money:
                startActivity(new Intent(getActivity(), MoneyActivity.class));
                break;
            case R.id.rl_person_address:
                startActivity(new Intent(getActivity(), MyAddressActivity.class));
                break;
            case R.id.rl_person_collect:
                startActivity(new Intent(getActivity(), MyShoucangActivity.class));
                break;
            case R.id.rl_person_history:
                startActivity(new Intent(getActivity(), MyHistoryActivity.class));
                break;
            default:
                break;
        }
    }
}
