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

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.GridDownAdapter;
import com.example.nativeMall.Adapter.GridDownGridAdapter;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.google.gson.Gson;

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

public class AllMedicineActivity extends InitActivity {

    @BindView(R.id.rv_grid_down_list)
    RecyclerView mRvGridDownList;
    @BindView(R.id.iv_grid_down_kind)
    ImageView mIvGridDownKind;
    @BindView(R.id.et_grid_down_search)
    EditText mEtGridDownSearch;

    private Gson mGson = new Gson();
    JSONObject json = null, json2 = null, dataJson = null;
    JSONArray jsonArray = null;
    private List<Map<String, Object>> data_list;
    GridDownAdapter gridDownAdapter;
    Boolean FORM = true;
    GridDownGridAdapter gridDownGridAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_medicine);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Map<String, String> param = new HashMap<>();
        param.put("storeid", getIntent().getStringExtra("sid"));
        param.put("pageSize", "10000");
        Http.getInstance().init(this, mHandler, mGson.toJson(param), "index/search", 0).sendMsg();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
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
                    break;
            }
        }
    };

    public void setGrid() {
        gridDownGridAdapter = new GridDownGridAdapter(this, data_list);
        layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        DividerLine dividerLine = new DividerLine(DividerLine.HORIZONTAL);
        dividerLine.setSize(10);
        dividerLine.setColor(0xFFf7f7f7);
        mRvGridDownList.addItemDecoration(dividerLine);
        mRvGridDownList.setLayoutManager(layoutManager);
        mRvGridDownList.setAdapter(gridDownGridAdapter);
        gridDownGridAdapter.setOnItemClickListener((view, data) -> {
            Intent intent = new Intent(AllMedicineActivity.this, GoodsDetailActivity.class);
            intent.putExtra("pid", (String) data_list.get(data).get("pid"));
            startActivity(intent);
        });
    }

    public void setList() {
        mRvGridDownList.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));

        gridDownAdapter = new GridDownAdapter(this, data_list);
        mRvGridDownList.setAdapter(gridDownAdapter);
        gridDownAdapter.setOnItemClickListener((view, data) -> {
            Intent intent = new Intent(AllMedicineActivity.this, GoodsDetailActivity.class);
            intent.putExtra("pid", (String) data_list.get(data).get("pid"));
            startActivity(intent);
        });
    }

    @OnClick({R.id.iv_supplier_back, R.id.iv_grid_down_kind, R.id.tv_grid_down_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_supplier_back:
                finish();
                break;
            case R.id.iv_grid_down_kind:
                if (FORM) {
                    mIvGridDownKind.setImageResource(R.drawable.v2_0_list_liebiao);
                    setGrid();
                    FORM = false;
                } else {
                    mIvGridDownKind.setImageResource(R.drawable.v2_0_list_datu);
                    FORM = true;
                    setList();
                }
                break;
            case R.id.tv_grid_down_search:
                String kw = mEtGridDownSearch.getText().toString();
                Map<String, String> param = new HashMap<>();
                param.put("storeid", getIntent().getStringExtra("sid"));
                param.put("kw", kw);
                Http.getInstance().init(this, mHandler, mGson.toJson(param), "index/search", 0).sendMsg();
                break;
        }
    }
}
