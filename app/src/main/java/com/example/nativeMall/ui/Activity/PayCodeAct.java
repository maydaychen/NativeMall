package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.DataMsgSucesBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.CreateQrCode;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Fragment.HealthCardFragment;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.WriterException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class PayCodeAct extends InitActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.iv_bar_code)
    ImageView ivBarCode;
    @BindView(R.id.tv_bar_code)
    TextView tvBarCode;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    private String content = null;
    private static final String TAG = "PayCodeAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_code);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initView();
        initData();

    }

    @Override
    public void initView() {

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

        getCardNumber();


    }

    private void getCardNumber() {

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/allCard";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", HealthCardFragment.HealthCardId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(PayCodeAct.this, URL, entity, "application/json", new JsonHttpResponseHandler
                () {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                        .getAsJsonObject();
                if (jsonObject1.get("success").getAsString().equals("T")) {
                    Gson gson = new Gson();
                    DataMsgSucesBean dataMsgSucesBean = gson.fromJson(response.toString(), DataMsgSucesBean.class);
//                        for (int i = 0; i < dataMsgSucesBean.getData().size();i++){
//
//                        }
                    content = dataMsgSucesBean.getData().get(0).get("cardNo").toString();
                    tvBarCode.setText(content);
                    getOneTwoCode();
                } else {
                    Toast.makeText(PayCodeAct.this, jsonObject1.get("msg").getAsString(), Toast
                            .LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(PayCodeAct.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOneTwoCode() {
        try {
            ivBarCode.setImageBitmap(CreateQrCode.CreateOneDCode(content));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        try {
            ivQrCode.setImageBitmap(CreateQrCode.CreateTwoDCode(content));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
