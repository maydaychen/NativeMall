package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.RecommendAdapter;
import com.example.nativeMall.Bean.GoodsBean;
import com.example.nativeMall.Bean.StoreBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.example.nativeMall.ui.widget.RecycleViewGridDivider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class MedicalStoreDtlAct extends AppCompatActivity {

    private static final String TAG = "MedicalStoreDtlAct";
    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.iv_company)
    SmartImageView ivCompany;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_phone)
    TextView tvCompanyPhone;
    @BindView(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.rv_goods_list)
    RecyclerView rvGoodsList;
    private String StoreId = null;
    private List<GoodsBean> listGoodsBean = new ArrayList<>();
    private RecommendAdapter recommendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_store_dtl);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        InitView();
        InitData();
    }


    private void InitView() {
        Intent intent = getIntent();
        StoreBean storeBean = (StoreBean) intent.getSerializableExtra("storeBean");
        ivCompany.setImageUrl(Config.PIC_URL + storeBean.getLogo());
        tvCompanyName.setText(storeBean.getName());
        tvCompanyPhone.setText(storeBean.getPhone());
        tvCompanyAddress.setText(storeBean.getAddress());
        tvDistance.setText(storeBean.getDistance());
        StoreId = storeBean.getStoreid();
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
    }

    private void InitData() {

        rvGoodsList.setLayoutManager(new GridLayoutManager(MedicalStoreDtlAct.this, 2,
                LinearLayoutManager.VERTICAL, false));
        rvGoodsList.addItemDecoration(new RecycleViewGridDivider(MedicalStoreDtlAct.this));
        getStoreGoods();
    }

    private void getStoreGoods() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/storeDtl";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "1");
            jsonObject.put("storeId", StoreId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(MedicalStoreDtlAct.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                        .getAsJsonObject();
                if ((jsonObject1.get("success").getAsString().equals("T"))) {
                    Gson gson = new Gson();
                    JsonArray jsonArray = jsonObject1.get("data").getAsJsonObject().get
                            ("products").getAsJsonArray();
                    List<Map<String,Object>> listmaps = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        GoodsBean goodsBean = gson.fromJson(jsonArray.get(i).getAsJsonObject().toString(),
                                GoodsBean.class);
                        Map<String,Object> map = new HashMap<>();
                        map.put("logo",goodsBean.getShowimg());
                        map.put("name",goodsBean.getName());
                        map.put("price",goodsBean.getShopprice());
                        listmaps.add(map);
                        listGoodsBean.add(goodsBean);
                    }
                    recommendAdapter = new RecommendAdapter(MedicalStoreDtlAct.this,listmaps);
                    rvGoodsList.setAdapter(recommendAdapter);
                    recommendAdapter.setOnItemClickListener(new RecommendAdapter.OnRecyclerViewItemClickListener() {

                        @Override
                        public void onItemClick(View view, int data) {
                            Intent intent = new Intent(MedicalStoreDtlAct.this, GoodsDetailActivity.class);
                            intent.putExtra("pid",listGoodsBean.get(data).getPid());
                            startActivity(intent);
                        }
                    });

                } else {
                    Toast.makeText(MedicalStoreDtlAct.this, jsonObject1.get("msg").getAsString(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
