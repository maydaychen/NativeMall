package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nativeMall.Adapter.SearchAdapter;
import com.example.nativeMall.Bean.SearchBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends InitActivity {

    @BindView(R.id.searchBack)
    ImageView searchBack;
    @BindView(R.id.searchName)
    EditText mSearchName;
    @BindView(R.id.rv_search)
    RecyclerView mRvSearch;
    private SharedPreferences preferences;
    private SubscriberOnNextListener<JSONObject> searchOnNext;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        searchBack.setOnClickListener(view -> finish());

    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        searchOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                SearchBean indexBean = mGson.fromJson(jsonObject.toString(), SearchBean.class);
                SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this, indexBean.getResult());
                mRvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                mRvSearch.setAdapter(searchAdapter);
                searchAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent(SearchActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("id", indexBean.getResult().get(data).getId());
                    startActivity(intent);
                });
            }
        };
        RxTextView.textChanges(mSearchName)
                .subscribe(charSequence -> search(charSequence.toString()));
    }

    private void search(String name) {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        if (!"".equals(name)) {
            sign = sign + "keywords=" + name + "&";
        }
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().search_goods(
                new ProgressSubscriber(searchOnNext, SearchActivity.this),
                preferences.getString("access_token", ""), name, preferences.getString("sessionkey", ""), sign, time);
    }
}
