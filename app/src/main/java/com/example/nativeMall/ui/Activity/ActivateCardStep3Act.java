package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class ActivateCardStep3Act extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_id_number)
    TextView tvIdNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    private String PASSWORD;
    private String CardNumber;
    private String IdNumber;
    private String insuredId;
    private static final String KEY = "health";
    private static final String TAG = "ActivateCardStep3Act";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_card_step3);
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
        CardNumber = intent.getStringExtra("cardNo");
        IdNumber = intent.getStringExtra("idNumber");
        insuredId = intent.getStringExtra("insuredId");
        tvCardNumber.setText(CardNumber);
        tvIdNumber.setText(IdNumber);
    }

    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (etPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString()
                .trim())) {
                    PASSWORD = Base64.encodeToString(etConfirmPassword.getText().toString().trim().getBytes(),1);
                    Log.i(TAG, "onClick: " + PASSWORD);
                    SetPayPassWord();

                } else {
                    Toast.makeText(ActivateCardStep3Act.this, "两次密码输入不同", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }

    private void SetPayPassWord() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/setpwd";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", insuredId);
            jsonObject.put("cardNo", CardNumber);
            jsonObject.put("cardPwd", PASSWORD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(ActivateCardStep3Act.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                        .getAsJsonObject();
                if (jsonObject1.get("success").getAsString().equals("T")) {
                    //next
                    ////
                    finish();
                }else {
                    Toast.makeText(ActivateCardStep3Act.this,jsonObject1.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
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
