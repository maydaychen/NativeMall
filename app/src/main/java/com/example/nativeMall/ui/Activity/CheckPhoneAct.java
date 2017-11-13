package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class CheckPhoneAct extends AppCompatActivity {

    @BindView(R.id.et_check_code)
    EditText etCheckCode;
    @BindView(R.id.btn_check)
    Button btnCheck;
    private static final String TAG = "CheckPhoneAct";
    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    private String headerId, PatientBean_id, diseaseInfo, payMethod, hasVisit, medicalRecordNo,
            codeid, Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        InitData();

    }

    private void InitData() {

        Intent intent = getIntent();
        headerId = intent.getStringExtra("headerId");
        PatientBean_id = intent.getStringExtra("PatientBean_id");
        diseaseInfo = intent.getStringExtra("diseaseInfo");
        payMethod = intent.getStringExtra("payMethod");
        hasVisit = intent.getStringExtra("hasVisit");
        medicalRecordNo = intent.getStringExtra("medicalRecordNo");
        codeid = intent.getStringExtra("codeid");
        Phone = intent.getStringExtra("patitentPhone");

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

    private void SubmitDateList() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "orderInfo/addOrder?headId=" + headerId +
                "&patientInfoId=" + PatientBean_id + "&userId=" + Config.userBean
                .getData().getUid() + "&diseaseInfo" + diseaseInfo + "&payMethod=" + payMethod +
                "&hasVisit=" + hasVisit + "&medicalRecordNo=" + medicalRecordNo;
        Log.i(TAG, "SubmitDateList: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                JsonObject jsonObject1 = new JsonParser().parse(result).getAsJsonObject();
                if (jsonObject1.get("MSG").getAsString().equals("预约成功")) {
                    Intent intent2 = new Intent(CheckPhoneAct.this, DateSuccessAct.class);
                   String Id = jsonObject1.get("ITEM").getAsJsonObject().get("id").getAsString();
                    intent2.putExtra("id",Id);
                    startActivity(intent2);
                    finish();
                    Toast.makeText(CheckPhoneAct.this, "已提交成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CheckPhoneAct.this,jsonObject1.get("MSG").getAsString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @OnClick(R.id.btn_check)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_check:
                CheckPatientPhone(Phone);
                break;

        }
    }

    private void CheckPatientPhone(String PhoneNumber) {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "mesgInfo/codeCheck";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", PhoneNumber);
            jsonObject.put("code", etCheckCode.getText().toString().trim());
            jsonObject.put("codeid", codeid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(CheckPhoneAct.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                                .getAsJsonObject();
                        if (jsonObject1.get("MSG").getAsString().equals("验证码验证成功")) {
                            SubmitDateList();
                        } else {
                            Toast.makeText(CheckPhoneAct.this, jsonObject1.get("MSG").getAsString
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
