package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nativeMall.Adapter.MyShoucangAdapter;
import com.example.nativeMall.Bean.ShoucangBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.RecycleViewDivider;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyShoucangActivity extends InitActivity {

    @BindView(R.id.rv_my_shoucang)
    RecyclerView mRvMyShoucang;

    private Gson mGson = new Gson();
    private ShoucangBean mShoucangBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shoucang);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initView();
    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onClick() {
        finish();
    }

    @Override
    public void initView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvMyShoucang.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mRvMyShoucang.setLayoutManager(layoutManager);
    }

    @Override
    public void initData() {
        Map<String, String> param = new HashMap<>();
        param.put("type", "LIST");
        param.put("uid", Config.userBean.getData().getUid());
        param.put("witch", "PRODUCT");
        Http.getInstance().init(MyShoucangActivity.this, handler, mGson.toJson(param), "order/ collect", 0).sendMsg();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject JSONObject = new JSONObject(data);
                        if (JSONObject.getString("success").equals("T")) {
                            mShoucangBean = mGson.fromJson(data, ShoucangBean.class);
                            List<Map<String, Object>> listmaps = new ArrayList<>();
                            for (ShoucangBean.DataBean dataBean : mShoucangBean.getData()) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("name", dataBean.getName());
                                map.put("price", dataBean.getShopprice() + "");
                                map.put("shopname", dataBean.getSname());
                                map.put("url", dataBean.getShowimg());
                                listmaps.add(map);
                            }
                            MyShoucangAdapter myShoucangAdapter = new MyShoucangAdapter(listmaps, MyShoucangActivity.this);
                            mRvMyShoucang.setAdapter(myShoucangAdapter);
                            myShoucangAdapter.setOnItemClickListener((view, data1) -> {
                                Intent intent = new Intent(MyShoucangActivity.this, GoodsDetailActivity.class);
                                intent.putExtra("pid", mShoucangBean.getData().get(data1).getPid() + "");
                                startActivity(intent);
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
