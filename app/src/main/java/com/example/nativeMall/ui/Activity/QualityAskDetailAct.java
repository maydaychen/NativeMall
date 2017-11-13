package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.nativeMall.Bean.CodeMsgSItemBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class QualityAskDetailAct extends AppCompatActivity {


    private static final String TAG = "QualityAskDetailAct";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_ask_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        Log.i(TAG, "onCreate: " + ID);
        getDetailDataByAsyncClient();
    }

    private void getDetailDataByAsyncClient() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "inquisitionInfo/getInquisitionInfo?id=" + ID + "&flag=0";
        Log.i(TAG, "getDetailDataByAsyncClient: " + URL);
        client.get(URL, params, new AsyncHttpResponseHandler() {

            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                Log.i(TAG, "onSuccess: " + result);
                Gson gson = new Gson();
                CodeMsgSItemBean codeMsgSItemsBean = gson.fromJson(result, CodeMsgSItemBean.class);

                if (codeMsgSItemsBean.getMSG().equals("success")){
                    tvTitle.setText(codeMsgSItemsBean.getITEMS().get("inquisitionBref").toString());
                    tvDescribe.setText(codeMsgSItemsBean.getITEMS().get("inquisitionRequest").toString());
                    tvAnswer.setText(codeMsgSItemsBean.getITEMS().get("inquisitionResponse").toString());
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


}
