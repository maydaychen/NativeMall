package com.example.nativeMall.ui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nativeMall.Adapter.GouwucheAdapter;
import com.example.nativeMall.Bean.GouwucheBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：JTR on 2016/8/29 10:35
 * 邮箱：2091320109@qq.com
 */
public class GouwucheFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    @BindView(R.id.rv_cart_list)
    RecyclerView mRvCartList;
    private SubscriberOnNextListener<JSONObject> cartsOnNext;
    private SharedPreferences preferences;
    private static final String EXTRA_CONTENT = "content";
    private Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gouwuche, container, false);
        ButterKnife.bind(this, root);
        initData();
        initDetail();
        return root;
    }

    public static GouwucheFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        GouwucheFragment gouwucheFragment = new GouwucheFragment();
        gouwucheFragment.setArguments(arguments);
        return gouwucheFragment;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            initDetail();
            mSwipeContainer.setRefreshing(false);
        }, 3000);
        // 3秒后发送消息，停止刷新
    }

    private void initData() {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        mSwipeContainer.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeContainer.setDistanceToTriggerSync(300);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeContainer.setProgressBackgroundColor(R.color.blue); // 设定下拉圆圈的背景
        mSwipeContainer.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小

        cartsOnNext = jsonObject -> {
            GouwucheBean gouwucheBean = gson.fromJson(jsonObject.toString(), GouwucheBean.class);
            Log.i("chenyi", "gouwuche initData: " + jsonObject.toString());
            mRvCartList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRvCartList.setAdapter(new GouwucheAdapter(getActivity(), gouwucheBean.getResult().getList()));
        };
    }

    private void initDetail() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().cartsList(
                new ProgressSubscriber(cartsOnNext, getActivity()),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), sign, time);
    }
}
