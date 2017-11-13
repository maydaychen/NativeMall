package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.GridDownAdapter;
import com.example.nativeMall.Adapter.GridDownGridAdapter;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GridDownActivity extends InitActivity {

    @BindView(R.id.iv_grid_down_kind)
    ImageView ivGridDownKind;
    @BindView(R.id.rv_grid_down_list)
    RecyclerView rvGridDownList;
    @BindView(R.id.tv_supplier_title_name)
    TextView mTvSupplierTitleName;
    @BindView(R.id.et_grid_down_search)
    EditText mEtGridDownSearch;
    private List<Map<String, Object>> data_list;
    GridDownAdapter gridDownAdapter;
    Boolean FORM = true;
    GridDownGridAdapter gridDownGridAdapter;
    RecyclerView.LayoutManager layoutManager;
    private Gson mGson = new Gson();

    JSONObject json = null, json2 = null, dataJson = null;
    JSONArray jsonArray = null;
    private final Handler mHandler = new MyHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_down);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.iv_supplier_back, R.id.iv_grid_down_kind, R.id.tv_grid_down_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_supplier_back:
                finish();
                break;
            case R.id.iv_grid_down_kind:
                if (FORM) {
                    ivGridDownKind.setImageResource(R.drawable.v2_0_list_liebiao);
                    setGrid();
                    FORM = false;
                } else {
                    ivGridDownKind.setImageResource(R.drawable.v2_0_list_datu);
                    FORM = true;
                    setList();
                }
                break;
            case R.id.tv_grid_down_search:
                String kw = mEtGridDownSearch.getText().toString();
                Map<String, String> param = new HashMap<>();
                param.put("cateid", getIntent().getStringExtra("cateid"));
                param.put("kw", kw);
                Http.getInstance().init(this, mHandler, mGson.toJson(param), "index/search", 0).sendMsg();
                break;
        }
    }

    @Override
    public void initView() {
        mTvSupplierTitleName.setText(getIntent().getStringExtra("title"));
    }

    @Override
    public void initData() {
        Map<String, String> param = new HashMap<>();
        param.put("cateid", getIntent().getStringExtra("cateid"));
        Http.getInstance().init(this, mHandler, mGson.toJson(param), "address/cateproduct", 0).sendMsg();
    }

    public void setGrid() {
        if (data_list != null)  {
            gridDownGridAdapter = new GridDownGridAdapter(this, data_list);
            layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
            DividerLine dividerLine = new DividerLine(DividerLine.HORIZONTAL);
            dividerLine.setSize(10);
            dividerLine.setColor(0xFFf7f7f7);
            rvGridDownList.addItemDecoration(dividerLine);
            rvGridDownList.setLayoutManager(layoutManager);
            rvGridDownList.setAdapter(gridDownGridAdapter);
            gridDownGridAdapter.setOnItemClickListener(((view, data) -> {
                Intent intent = new Intent(GridDownActivity.this, GoodsDetailActivity.class);
                intent.putExtra("pid", (String) data_list.get(data).get("pid"));
                startActivity(intent);
            }));
            }
        }

    public void setList() {
        if (data_list != null) {
            rvGridDownList.setLayoutManager(new StaggeredGridLayoutManager(1,
                    StaggeredGridLayoutManager.VERTICAL));
            gridDownAdapter = new GridDownAdapter(this, data_list);
            rvGridDownList.setAdapter(gridDownAdapter);
            gridDownAdapter.setOnItemClickListener((view, data) -> {
                Intent intent = new Intent(GridDownActivity.this, GoodsDetailActivity.class);
                intent.putExtra("pid", (String) data_list.get(data).get("pid"));
                startActivity(intent);
            });
        }
    }

    private void getDate(String data){
        Util.showMsg(GridDownActivity.this, data);
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            json = new JSONObject(data);
            dataJson = json.getJSONObject("data");
            jsonArray = dataJson.getJSONArray("products");
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, Object> map = new HashMap<>();
                json2 = jsonArray.getJSONObject(i);
//                            fenleiRight.add((FenleiRightBean) Util.fromJsonToJava(json3, FenleiRightBean.class));
                map.put("name", json2.getString("name"));
                map.put("shopprice", json2.getString("shopprice"));
                map.put("sname", json2.getString("sname"));
                map.put("url", json2.getString("showimg"));
                map.put("pid", json2.getString("pid"));
                list.add(map);
            }
            data_list = list;
            setList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static class MyHandler extends Handler {
        WeakReference<GridDownActivity> mActivityReference;

        MyHandler(GridDownActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final GridDownActivity activity = mActivityReference.get();
            if (activity != null) {
                String data = msg.obj.toString();
                activity.getDate(data);
            }
        }
    }
}
