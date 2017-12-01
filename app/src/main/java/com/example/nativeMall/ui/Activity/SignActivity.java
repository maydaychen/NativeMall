package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.http.Http;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {

    @BindView(R.id.sign_username)
    EditText mSignUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.iv_password_mingwen)
    ImageView mIvPasswordMingwen;
    @BindView(R.id.et_sign_yanzhengma)
    EditText mEtSignYanzhengma;
    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.bt_sign_send_msg)
    Button btSignSendMsg;


    //    密码，false为密文，true为明文
    private boolean PASSWORD = false;
    private Gson mGson = new Gson();
    private int time = 0;
    private MyThread myThread;
    private static final String TAG = "SignActivity";
    private static final int Sign_Success = 200;
    private static final int Sign_Fail = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
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

    @OnClick({R.id.iv_password_mingwen, R.id.bt_sign, R.id.tv_go_to_log, R.id.bt_sign_send_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_password_mingwen:
                if (PASSWORD) {
                    mPassword.setInputType(Config.PASSWORD_MIWEN);//设置显示为密文
                    mIvPasswordMingwen.setImageResource(R.drawable.v2_0_login_eyehui);
                    PASSWORD = false;
                } else {
                    mPassword.setInputType(Config.PASSWORD_MINGWEN);//设置显示为密文
                    PASSWORD = true;
                    mIvPasswordMingwen.setImageResource(R.drawable.v2_0_login_eyelan);
                }
                break;
            case R.id.bt_sign:
                Map<String, String> sign = new HashMap<>();
                sign.put("phone", mSignUsername.getText().toString());
                sign.put("octopusApiid", Config.JIAOYAN_TOKEN);
                sign.put("code", mEtSignYanzhengma.getText().toString());
                Http.getInstance().init(SignActivity.this, handler, mGson.toJson(sign), "index/ " +
                        "checkPhone", 1).sendMsg();
                break;
            case R.id.tv_go_to_log:
                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_sign_send_msg:
                if (mSignUsername.getText().length() == 11) {
                    Map<String, String> param = new HashMap<>();
                    param.put("phone", mSignUsername.getText().toString());
                    param.put("octopusApiid", Config.SEND_MSG_TOKEN);
                    Http.getInstance().init(SignActivity.this, handler, mGson.toJson(param),
                            "index/shortmsg", 0).sendMsg();

                    myThread = new MyThread();
                    time = 59;
                    myThread.start();
                    btSignSendMsg.setBackgroundResource(R.drawable.count_time_bg);
                    btSignSendMsg.setClickable(false);

                } else {
                    Toast.makeText(SignActivity.this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    // Util.showMsg(SignActivity.this, data);
                    try {
                        JSONObject json = new JSONObject(data);
                        if (json.get("success").equals("T")){

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    JSONObject json = null;
                    try {
                        json = new JSONObject(data);
                        if (json.getString("success").equals("T")) {
                            Map<String, String> sign = new HashMap<>();
                            sign.put("phone", mSignUsername.getText().toString());
                            sign.put("octopusApiid", Config.SIGN_TOKEN);
                            sign.put("pwd", mPassword.getText().toString());
                            Http.getInstance().init(SignActivity.this, handler, mGson.toJson
                                    (sign), "index/register", 2).sendMsg();
                        } else {
                            Toast.makeText(SignActivity.this, json.getString("msg"), Toast
                                    .LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        json = new JSONObject(data);
                        if (json.getString("success").equals("T")) {
                            Toast.makeText(SignActivity.this, json.getString("msg"), Toast
                                    .LENGTH_SHORT).show();
                            SignEmAccount();

                        } else {
                            Toast.makeText(SignActivity.this, json.getString("msg"), Toast
                                    .LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    if (time == 0) {
                        myThread.stop = true;
                        btSignSendMsg.setBackgroundResource(R.drawable.next_step_bg);
                        btSignSendMsg.setClickable(true);
                        btSignSendMsg.setText("重新获取");
                        Log.i(TAG, "handleMessage: ");
                    } else {
                        btSignSendMsg.setText(time-- + "秒后可获取");
                    }
                    break;
                case Sign_Success:
                    Toast.makeText(SignActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "handleMessage: 注册成功");
                    finish();
                    break;

                case Sign_Fail:
                    Toast.makeText(SignActivity.this,"账号注册成功，环信注册失败",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }

        }
    };

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
//                case 3:
//                    if (time == 0) {
//                        myThread.stop = true;
//                        btSignSendMsg.setBackgroundResource(R.drawable.next_step_bg);
//                        btSignSendMsg.setClickable(true);
//                        btSignSendMsg.setText("重新获取");
//                        Log.i(TAG, "handleMessage: ");
//                    } else {
//                        btSignSendMsg.setText(time-- + "秒后可获取");
//                    }
//
//                    break;

            }
        }
    };

    public class MyThread extends Thread {   // thread

        boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
                try {
                    Thread.sleep(1000);   // sleep 1000ms
//                    Message message = new Message();
//
//                    message.what = 3;
//                    handler1.sendMessage(message);
                    handler.sendMessage(handler.obtainMessage(3, ""));
                    Log.i(TAG, "run: " + time);
                } catch (Exception e) {
                }
            }
        }
    }

    private void SignEmAccount() {
        new Thread() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(mSignUsername.getText().toString(),
                            mPassword.getText().toString());
                    handler.sendMessage(handler.obtainMessage(Sign_Success,""));
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    handler.sendMessage(handler.obtainMessage(Sign_Fail,""));
                }
            }
        }.start();
    }
}
