package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.example.nativeMall.Adapter.PatenerAdapter;
import com.example.nativeMall.Bean.PatenerBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatenerActivity extends InitActivity {

    @BindView(R.id.tl_tab)
    TabLayout mTlTab;
    @BindView(R.id.rv_item_address)
    XRecyclerView mRvItemAddress;
    private int page = 1;
    private String type = "all";
    private Gson gson = new Gson();
    private List<PatenerBean.ResultBean.ListsBean> mGoodsBeanList = new ArrayList<>();
    private SharedPreferences preferences;
    private SubscriberOnNextListener<JSONObject> patenerListOnNext;
    private PatenerAdapter mPatenerAdapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_patener);
        ButterKnife.bind(this);

        mTlTab.setTabMode(TabLayout.MODE_FIXED);
        mTlTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.font_blue));
        mTlTab.setSelectedTabIndicatorHeight(5);

        mTlTab.addTab(mTlTab.newTab().setCustomView(R.layout.item_patener_all));
        mTlTab.addTab(mTlTab.newTab().setCustomView(R.layout.item_patener_bought));
        mTlTab.addTab(mTlTab.newTab().setCustomView(R.layout.item_patener_no));
        mTlTab.getTabAt(0).getCustomView().setSelected(true);
        mTlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        type = "all";
                        break;
                    case 1:
                        type = "agent";
                        break;
                    case 2:
                        type = "fans";
                        break;
                    default:
                        break;
                }
                mGoodsBeanList.clear();
                mPatenerAdapter.notifyDataSetChanged();
                page = 1;
                getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mRvItemAddress.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mGoodsBeanList.clear();
                mPatenerAdapter.notifyDataSetChanged();
                page = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                page += 1;
                getData();
            }
        });
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mPatenerAdapter = new PatenerAdapter(getApplicationContext(), mGoodsBeanList);
        patenerListOnNext = jsonObject -> {
            mRvItemAddress.refreshComplete();
            mRvItemAddress.loadMoreComplete();
            if (jsonObject.getInt("statusCode") == 1) {
                PatenerBean orderBean = gson.fromJson(jsonObject.toString(), PatenerBean.class);
                mGoodsBeanList.addAll(orderBean.getResult().getLists());
                mPatenerAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(PatenerActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        if (getIntent().getIntExtra("id", 0) == 0) {
            getData();
        } else {
            mTlTab.getTabAt(getIntent().getIntExtra("id", 0)).select();
        }
    }

    private void getData() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "page=" + page + "&";
        sign = sign + "psize=10&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "type=" + type + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().patenerList(
                new ProgressSubscriber(patenerListOnNext, PatenerActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), page + "", type, "10", sign, time);
    }

}
