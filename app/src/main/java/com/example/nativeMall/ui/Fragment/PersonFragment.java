package com.example.nativeMall.ui.Fragment;

import android.app.AlertDialog;
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
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.example.nativeMall.Config.fileName;
import static com.example.nativeMall.Utils.logout;

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
    private SharedPreferences.Editor editor;
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

            } else {
                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
            switch (jsonObject.getInt("statusCode")) {
                case 1:
                    indexBean = mGson.fromJson(jsonObject.toString(), MemberBean.class);
                    mTvPersonUsername.setText(indexBean.getResult().getNickname());
                    mTvPersonId.setText(String.format(getActivity().getResources().getString(R.string.person_id), indexBean.getResult().getId()));
                    mTvPersonPrice.setText(String.format(getActivity().getResources().getString(R.string.person_price), indexBean.getResult().getCredit1()));
                    ivMyLogo.setImageUrl(indexBean.getResult().getAvatar());
                    break;
                case 10010:
                    logout(getActivity());
                    break;
                default:
                    break;
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

    @OnClick({R.id.rl_person_orders, R.id.rl_all_order, R.id.rl_daizhifu, R.id.rl_daifahuo, R.id.rl_yifahuo, R.id.rl_yiwancheng, R.id.rl_person_fenxiao, R.id.rl_person_invite, R.id.rl_person_money, R.id.rl_person_address, R.id.rl_person_collect, R.id.rl_person_history, R.id.tv_person_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_person_orders:
                Intent orders = new Intent(getActivity(), MyOrderActivity.class);
                orders.putExtra("id", 0);
                startActivity(orders);
                break;
            case R.id.rl_all_order:
                Intent allOrders = new Intent(getActivity(), MyOrderActivity.class);
                allOrders.putExtra("id", 0);
                startActivity(allOrders);
                break;
            case R.id.rl_daizhifu:
                Intent daizhifu = new Intent(getActivity(), MyOrderActivity.class);
                daizhifu.putExtra("id", 1);
                startActivity(daizhifu);
                break;
            case R.id.rl_daifahuo:
                Intent daifahuo = new Intent(getActivity(), MyOrderActivity.class);
                daifahuo.putExtra("id", 2);
                startActivity(daifahuo);
                break;
            case R.id.rl_yifahuo:
                Intent yifahuo = new Intent(getActivity(), MyOrderActivity.class);
                yifahuo.putExtra("id", 3);
                startActivity(yifahuo);
                break;
            case R.id.rl_yiwancheng:
                Intent yiwancheng = new Intent(getActivity(), MyOrderActivity.class);
                yiwancheng.putExtra("id", 4);
                startActivity(yiwancheng);
                break;
            case R.id.rl_person_fenxiao:
                Intent intent = new Intent(getActivity(), FenxiaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("member", indexBean.getResult());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.rl_person_invite:
                showShare();
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
            case R.id.tv_person_logout:
                show();
                break;
            default:
                break;
        }
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确定要退出么？");
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton("确认", (dialog, which) -> {
            Toast.makeText(getActivity(), "退出成功！", Toast.LENGTH_SHORT).show();
            editor = preferences.edit();
            editor.putString("sessionkey", "");
            editor.putString("access_token", "");
            editor.putBoolean("autoLog", false);
            editor.putString("auth_key", "");
            editor.putInt("access_time", 0);
            editor.putInt("session_time", 0);
            if (editor.commit()) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("纯生商城");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("快来下载吧");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(fileName);//确保SDcard下面存在此张图片
//        oks.setImagePath("file:///android_asset/logo.jpg");//确保SDcard下面存在此张图片
//        oks.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503506356816&di=5dadbd01e162deb6601a801dc6258361&imgtype=0&src=http%3A%2F%2Fimg1.bitautoimg.com%2Fautoalbum%2Ffiles%2F20170407%2F958%2F16325395873602_5454777_3.jpg%3Fr%3D20170703");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://fir.im/lag3");
        oks.setTitleUrl("https://fir.im/lag3");
//        oks.setSiteUrl("https://fir.im/lag3");
//        oks.setUrl("http://www.baidu.com");
        // 启动分享GUI
        oks.show(getActivity());
    }
}
