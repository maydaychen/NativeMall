package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.GridDownGridAdapter;
import com.example.nativeMall.Bean.MallStoreBean;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupplierActivity extends InitActivity {

    @BindView(R.id.tv_supplier_title_name)
    TextView tvSupplier;
    @BindView(R.id.supplierLogo)
    SmartImageView supplierLogo;
    @BindView(R.id.tv_supplier_name)
    TextView tvSupplierName;
    @BindView(R.id.tv_supplier_tel)
    TextView tvSupplierTel;

    Gson mGson = new Gson();
    GridDownGridAdapter gridDownGridAdapter;
    RecyclerView.LayoutManager layoutManager;
    JSONObject json = null, json2 = null, dataJson = null;
    JSONArray jsonArray = null;
    @BindView(R.id.rv_supplier_drug)
    RecyclerView mRvSupplierDrug;
    private List<Map<String, Object>> data_list;

    private MallStoreBean.DataBean shopBean;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        List<Map<String, Object>> list = new ArrayList<>();
                        json = new JSONObject(data);
                        dataJson = json.getJSONObject("data");
                        jsonArray = dataJson.getJSONArray("products");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            json2 = jsonArray.getJSONObject(i);
                            map.put("name", json2.getString("name"));
                            map.put("shopprice", json2.getString("shopprice"));
                            map.put("sname", json2.getString("sname"));
                            map.put("url", json2.getString("showimg"));
                            map.put("pid", json2.getString("pid"));
                            list.add(map);
                        }
                        data_list = list;
                        setGrid();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initData();
        initView();
    }


    @OnClick({R.id.iv_supplier_back, R.id.bt_supplier_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_supplier_back:
                finish();
                break;
            case R.id.bt_supplier_all:
                Intent intent = new Intent(SupplierActivity.this, AllMedicineActivity.class);
                intent.putExtra("sid", shopBean.getInfo().getStoreid() + "");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initView() {
        supplierLogo.setImageUrl(shopBean.getInfo().getLogo());
        tvSupplier.setText(shopBean.getInfo().getName());
        tvSupplierName.setText(shopBean.getInfo().getName());
        tvSupplierTel.setText(shopBean.getInfo().getMobile());
    }

    @Override
    public void initData() {
        shopBean = (MallStoreBean.DataBean) getIntent().getSerializableExtra("store");
        Map<String, String> param = new HashMap<>();
        param.put("storeid", shopBean.getInfo().getStoreid() + "");
        param.put("pageSize", "10000");
        Http.getInstance().init(this, handler, mGson.toJson(param), "index/search", 0).sendMsg();
    }

    public void setGrid() {
        gridDownGridAdapter = new GridDownGridAdapter(this, data_list);
        layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        DividerLine dividerLine = new DividerLine(DividerLine.HORIZONTAL);
        dividerLine.setSize(Util.dip2px(4,SupplierActivity.this));
        dividerLine.setColor(0xFFf7f7f7);
        mRvSupplierDrug.addItemDecoration(dividerLine);
        mRvSupplierDrug.setLayoutManager(layoutManager);
        mRvSupplierDrug.setAdapter(gridDownGridAdapter);
        gridDownGridAdapter.setOnItemClickListener((view, data) -> {
            Intent intent = new Intent(SupplierActivity.this, GoodsDetailActivity.class);
            intent.putExtra("pid", (String) data_list.get(data).get("pid"));
            startActivity(intent);
        });
    }
}
