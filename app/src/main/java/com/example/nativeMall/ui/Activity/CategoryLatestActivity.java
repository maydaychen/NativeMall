package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.GridDownAdapter;
import com.example.nativeMall.Bean.GridDownBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressErrorSubscriber;
import com.example.nativeMall.http.SubscriberOnNextAndErrorListener;
import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryLatestActivity extends InitActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    @BindView(R.id.rv_grid_down_list)
    PullLoadMoreRecyclerView mRvGridDownList;
    @BindView(R.id.tv_fenlei_zonghe)
    TextView mTvFenleiZonghe;
    @BindView(R.id.tv_fenlei_xiaoliang)
    TextView mTvFenleiXiaoliang;
    @BindView(R.id.tv_fenlei_zuixin)
    TextView mTvFenleiZuixin;
    @BindView(R.id.tv_fenlei_jiage)
    TextView mTvFenleiJiage;

    private GridDownAdapter mRecyclerViewAdapter;
    private Gson mGson = new Gson();
    private SharedPreferences preferences;
    private int page = 1;
    private String sortType = "";
    private String ccate = "";
    private SubscriberOnNextAndErrorListener<JSONObject> hotOnNext;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category_latest);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        try {
            ccate = getIntent().getStringExtra("ccate");
        } catch (NullPointerException ignored) {
        }
        RecyclerView recyclerView = mRvGridDownList.getRecyclerView();
        recyclerView.setVerticalScrollBarEnabled(true);
        mRvGridDownList.setRefreshing(false);
        mRvGridDownList.setPullRefreshEnable(true);
        mRvGridDownList.setPushRefreshEnable(true);
        mRvGridDownList.setFooterViewText("正在加载，请稍后");
        mRvGridDownList.setFooterViewTextColor(R.color.font_gray);
        mRvGridDownList.setFooterViewBackgroundColor(R.color.ccccc);
        mRvGridDownList.setLinearLayout();

        mRvGridDownList.setOnPullLoadMoreListener(this);
        mRvGridDownList.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_order, null));
        mRecyclerViewAdapter = new GridDownAdapter(this);
        mRvGridDownList.setAdapter(mRecyclerViewAdapter);

        hotOnNext = new SubscriberOnNextAndErrorListener<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) throws JSONException {
                if (jsonObject.getInt("statusCode") == 1) {
                    GridDownBean indexBean = mGson.fromJson(jsonObject.toString(), GridDownBean.class);
                    mRvGridDownList.setVisibility(View.VISIBLE);
                    try {
                        mRvGridDownList.setPullLoadMoreCompleted();
                        mRecyclerViewAdapter.addAllData(indexBean.getResult());
                        mRecyclerViewAdapter.notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        if (page != 0) {
                            Toast.makeText(CategoryLatestActivity.this, "已经加载完毕！", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(CategoryLatestActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                mRvGridDownList.setVisibility(View.VISIBLE);
                mRvGridDownList.setPullLoadMoreCompleted();
            }
        };
//        initCategoryGoods();
        mTvFenleiZonghe.setOnClickListener(view -> {
            mTvFenleiZonghe.setTextColor(getResources().getColor(R.color.font_blue));
            mTvFenleiXiaoliang.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiZuixin.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiJiage.setTextColor(getResources().getColor(R.color.gray));
            sortType = "";
            setRefresh();
            initCategoryGoods();
        });
        mTvFenleiXiaoliang.setOnClickListener(view -> {
            mTvFenleiZonghe.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiXiaoliang.setTextColor(getResources().getColor(R.color.font_blue));
            mTvFenleiZuixin.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiJiage.setTextColor(getResources().getColor(R.color.gray));
            sortType = "sales";
            setRefresh();
            initCategoryGoods();
        });
        mTvFenleiZuixin.setOnClickListener(view -> {
            mTvFenleiZonghe.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiXiaoliang.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiZuixin.setTextColor(getResources().getColor(R.color.font_blue));
            mTvFenleiJiage.setTextColor(getResources().getColor(R.color.gray));
            sortType = "new";
            setRefresh();
            initCategoryGoods();
        });
        mTvFenleiJiage.setOnClickListener(view -> {
            mTvFenleiZonghe.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiXiaoliang.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiZuixin.setTextColor(getResources().getColor(R.color.gray));
            mTvFenleiJiage.setTextColor(getResources().getColor(R.color.font_blue));
            sortType = "price_desc";
            setRefresh();
            initCategoryGoods();
        });
        mTvFenleiZonghe.performClick();
    }

    private void initCategoryGoods() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "attributes=isnew:1&";
        sign = sign + "page=" + page + "&";
        sign = sign + "psize=" + 10 + "&";
        if (!"".equals(sortType)) {
            sign = sign + "sorttype=" + sortType + "&";
        }
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().getAttributes(
                new ProgressErrorSubscriber<>(hotOnNext, CategoryLatestActivity.this),
                preferences.getString("access_token", ""), sortType, "isnew:1", page, 10, sign, time);
    }

    @Override
    public void onRefresh() {
        setRefresh();
        initCategoryGoods();
    }

    private void setRefresh() {
        mRecyclerViewAdapter.clearData();
        mRecyclerViewAdapter.notifyDataSetChanged();
        page = 1;
        mRvGridDownList.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        initCategoryGoods();
    }

    @OnClick(R.id.iv_supplier_back)
    public void onViewClicked() {
        finish();
    }
}