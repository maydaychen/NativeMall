package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class ActivateCardStep1Act extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;

    private static final String TAG = "ActivateCardStep1Act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_card_step1);
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

    }

    private void BindCheckIdNumber() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/checkIdCard";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", Config.userBean.getData().getUid());
            jsonObject.put("idCard", etIdNumber.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(ActivateCardStep1Act.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                        .getAsJsonObject();
                Log.i(TAG, "onSuccess: " + response.toString());
                if (jsonObject1.get("success").getAsString().equals("T")) {

                    Intent intent = new Intent(ActivateCardStep1Act.this, ActivateCardStep2Act
                            .class);
                    intent.putExtra("IdNumber",etIdNumber.getText().toString());
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(ActivateCardStep1Act.this, jsonObject1.get("msg").getAsString(),
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

    @OnClick({R.id.tv_next_step})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next_step:
                if (etIdNumber.getText().length() != 18) {
                    Toast.makeText(ActivateCardStep1Act.this, "请输入18位身份证", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    BindCheckIdNumber();

                }
                break;
        }
    }
}
