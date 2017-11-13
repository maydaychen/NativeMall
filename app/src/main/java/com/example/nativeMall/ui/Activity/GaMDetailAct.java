package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.StoreBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class GaMDetailAct extends InitActivity {


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
    private static final String TAG = "GaMDetailAct";
    @BindView(R.id.jianjie_content)
    TextView jianjieContent;
    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    private String StoreId = null;

    //基因检测 门诊详情Act
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gam_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initView();
        initData();

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        StoreBean storeBean = (StoreBean) intent.getSerializableExtra("storeBean");
        ivCompany.setImageUrl(Config.PIC_URL + storeBean.getLogo());
        tvCompanyName.setText(storeBean.getName());
        tvCompanyPhone.setText(storeBean.getMobile());
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

    @Override
    public void initData() {

        getDetailByAsync();
    }

    private void getDetailByAsync() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/storeDtl";
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "0");
            jsonObject.put("storeId", StoreId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(GaMDetailAct.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                                .getAsJsonObject();
                        if (jsonObject1.get("success").getAsString().equals("T")) {
                            if (jsonObject1.get("data").getAsJsonObject().get("desc").getAsString
                                    () != null) {
                                jianjieContent.setText(jsonObject1.get("data").getAsJsonObject().get
                                        ("desc").getAsString());
                            }
                        } else {
                            Toast.makeText(GaMDetailAct.this, jsonObject1.get("msg").getAsString
                                    (), Toast.LENGTH_SHORT).show();
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
