package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nativeMall.Bean.CodeMsgItemBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class DateSuccessAct extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    private static final String TAG = "DateSuccessAct";
    private String DateId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_success);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        InitView();
        InitData();
    }

    private void InitData() {
        Intent intent = getIntent();
        DateId = intent.getStringExtra("id");

    }

    private void InitView() {
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

    @OnClick({R.id.tv_check_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_date:
                getItemInfoByAsyncClient();
                break;
        }
    }

    private void getItemInfoByAsyncClient() {

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "orderInfo/getOrderByid?id=" + DateId + "&userId=" +
                Config
                .userBean
                .getData().getUid();
        Log.i(TAG, "getItemInfoByAsyncClient: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgItemBean codeMsgItemBean = gson.fromJson(result, CodeMsgItemBean.class);
                if (codeMsgItemBean.getMSG().equals("success")) {

                    String payMethod = codeMsgItemBean.getITEM().get("payMethod").toString();
                    String delFlag = codeMsgItemBean.getITEM().get("delFlag").toString();

                    Intent intent = new Intent(DateSuccessAct.this,DateListDetailAct.class);
                    intent.putExtra("Id", DateId);
                    intent.putExtra("delFlag", delFlag);
                    intent.putExtra("PayMethod", payMethod);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(DateSuccessAct.this,"网络异常",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DateSuccessAct.this,DateListAct.class);
                startActivity(intent);
            }
        });
    }
}
