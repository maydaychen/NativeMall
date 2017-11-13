package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.InsuranProductAdapter;
import com.example.nativeMall.Bean.DataMsgSucesInsuBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.example.nativeMall.ui.widget.RecycleViewDivider;
import com.google.gson.Gson;
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
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class InsuranceComInfoAct extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    private static final String TAG = "InsuranceComInfoAct";
    @BindView(R.id.iv_company)
    SmartImageView ivCompany;
    @BindView(R.id.tv_company_describe)
    TextView tvCompanyDescribe;
    @BindView(R.id.rv_production)
    RecyclerView rvProduction;
    private String compId;
    private InsuranProductAdapter insuranProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_com_info);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
        Intent intent = getIntent();
        compId = intent.getStringExtra("compId");
        Log.i(TAG, "onCreate: " + compId);
        rvProduction.setLayoutManager(new LinearLayoutManager(this));
        rvProduction.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        getCompInfo();
    }

    private void getCompInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/insuranceDtl";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("compId", compId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(InsuranceComInfoAct.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Gson gson = new Gson();
                        //Log.i(TAG, "onSuccess: " + response.toString());
                        DataMsgSucesInsuBean dataMsgSucesInsuBean = gson.fromJson(response
                                        .toString(),
                                DataMsgSucesInsuBean.class);
                        if (dataMsgSucesInsuBean.getSuccess().equals("T")) {
                            List<Map<String, Object>> listmaps = new ArrayList<>();
                            DataMsgSucesInsuBean.InsuranceBean insuranceBean = dataMsgSucesInsuBean
                                    .getData();
                            ivCompany.setImageUrl(Config.PIC_URL + insuranceBean.getLogo());
                            tvCompanyDescribe.setText(insuranceBean.getMemo());
                            DataMsgSucesInsuBean.InsuranceItemBean itemBean;
                            for (int i = 0; i < insuranceBean.getDetails().size(); i++) {
                                itemBean = insuranceBean.getDetails().get(i);
                                Map<String, Object> map = new HashMap<>();
                                map.put("tvProductName", itemBean.getPname());
                                map.put("tvPrice", itemBean.getPrice());
                                map.put("tvProductDes", itemBean.getDmemo());
                                map.put("tvBzService", itemBean.getBzservice());
                                map.put("tvCbAge", itemBean.getCbage());
                                map.put("tvSaleSum", itemBean.getSalenum());
                                map.put("tvGoodRate", itemBean.getMent());
                                listmaps.add(map);

                            }
                            Log.i(TAG, "onSuccess: " + listmaps);
                            insuranProductAdapter = new InsuranProductAdapter(listmaps);
                            rvProduction.setAdapter(insuranProductAdapter);
                            insuranProductAdapter.setOnItemClickListener(new InsuranProductAdapter.OnRecyclerViewItemClickListener() {

                                @Override
                                public void onItemClick(View view, int position) {

                                }
                            });

                        } else {
                            Toast.makeText(InsuranceComInfoAct.this, dataMsgSucesInsuBean.getMsg
                                    (), Toast
                                    .LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                          JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.i(TAG, "onFailure: ");
                    }
                });
    }

    @OnClick({ R.id.tv_official_web,R.id.tv_buy_from})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_official_web:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse("http://www.baidu.com");
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.tv_buy_from:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                Uri content_url1 = Uri.parse("http://www.baidu.com");
                intent1.setData(content_url1);
                startActivity(intent1);
                break;

        }
    }
}
