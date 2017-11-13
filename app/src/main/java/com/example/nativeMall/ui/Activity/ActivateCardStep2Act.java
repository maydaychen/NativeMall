package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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

public class ActivateCardStep2Act extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.tv_id_number)
    TextView tvIdNumber;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.et_confirm_code)
    EditText etConfirmCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    private String codeid = ""; //post后获取到的
    private String code = "";   //post后获取到的验证码
    private String phoneNumber = "";
    private static final String TAG = "ActivateCardStep2Act";
    private MyThread myThread;
    private int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_card_step2);
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
        tvIdNumber.setText(intent.getStringExtra("IdNumber"));  //设置为传过来的身份证号


    }


    @OnClick({R.id.tv_activate, R.id.tv_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_activate:
                if (etConfirmCode.getText().toString().equals(code)) {
                    ActivateCard();
                } else {
                    Toast.makeText(ActivateCardStep2Act.this, "验证码输入有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_get_code:
                //获取验证码
                if (tvPhone.getText().length() != 11) {
                    Toast.makeText(ActivateCardStep2Act.this, "请输入11位手机号码", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    phoneNumber = tvPhone.getText().toString().trim();
                    checkPhoneIdcardIsMatch();

                }
                break;
        }
    }

    private void checkPhoneIdcardIsMatch() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/checkPhone";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phoneNumber);
            jsonObject.put("idCard", tvIdNumber.getText().toString());
            Log.i(TAG, "checkPhoneIdcardIsMatch: " + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(ActivateCardStep2Act.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                        .getAsJsonObject();
                if (jsonObject1.get("success").getAsString().equals("T")) {
                    //校验身份证和phone匹配则发送验证码；
                    getPhoneCheckCode();
                    myThread = new MyThread();
                    time = 59;
                    myThread.start();
                    tvGetCode.setBackgroundResource(R.drawable.count_time_bg);
                    tvGetCode.setClickable(false);
                } else {
                    Toast.makeText(ActivateCardStep2Act.this, jsonObject1.get("msg").getAsString
                            (), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onSuccess: " + jsonObject1.get("msg").getAsString());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    private void ActivateCard() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/createCard";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phoneNumber);
            jsonObject.put("code", code);
            jsonObject.put("codeid", codeid);
            jsonObject.put("idcard", tvIdNumber.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(ActivateCardStep2Act.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                                .getAsJsonObject();
                        if (jsonObject1.get("success").getAsString().equals("T")) {
                            String cardNo = jsonObject1.get("data").getAsJsonObject()
                                    .getAsJsonArray("cards").get(0).getAsString();
                            Log.i(TAG, "onSuccess: " + cardNo);
                            Intent intent = new Intent(ActivateCardStep2Act.this,
                                    ActivateCardStep3Act
                                    .class);
                            String insuredId = jsonObject1.get("data").getAsJsonObject().get
                                    ("insuredId").getAsString();
                            intent.putExtra("cardNo", cardNo);
                            intent.putExtra("idNumber", tvIdNumber.getText().toString());
                            intent.putExtra("insuredId", insuredId);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ActivateCardStep2Act.this, jsonObject1.get("msg")
                                    .getAsString(),
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

    private void getPhoneCheckCode() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/shortmsg";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", tvPhone.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(ActivateCardStep2Act.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.i(TAG, "onSuccess: &&&" + response.toString());
                        JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                                .getAsJsonObject();
                        if (jsonObject1.get("success").getAsString().equals("T")) {
                            codeid = jsonObject1.get("data").getAsJsonObject().get("codeid")
                                    .getAsString();
                            code = jsonObject1.get("data").getAsJsonObject().get("code").toString();
                            Log.i(TAG, "onSuccess: ccc" + code + "ccc" + codeid);
                        } else {
                            // F 则打印出msg
                            Toast.makeText(ActivateCardStep2Act.this, jsonObject1.get("msg")
                                    .getAsString(), Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                          JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    if (time == 0) {
                        myThread.stop = true;
                        tvGetCode.setBackgroundResource(R.drawable.next_step_bg);
                        tvGetCode.setClickable(true);
                        tvGetCode.setText("重新获取");
                        Log.i(TAG, "handleMessage: ");
                    }else {
                        tvGetCode.setText(time-- + "秒后可获取");
                    }

                    break;

            }
        }
    };

    public class MyThread extends Thread{   // thread

        boolean stop = false;
        @Override
        public void run(){
            while(!stop){
                try{
                    Thread.sleep(1000);   // sleep 1000ms
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    Log.i(TAG, "run: " + time);
                }catch (Exception e) {
                }
            }
        }
    }
}
