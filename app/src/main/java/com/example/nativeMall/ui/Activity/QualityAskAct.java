package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.nativeMall.Adapter.QualityAskAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class QualityAskAct extends AppCompatActivity {


    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.rv_quality_ask)
    RecyclerView rvQualityAsk;
    private RecyclerView.LayoutManager layoutManager;
    private List cardList = new ArrayList();
    private QualityAskAdapter qualityAskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_ask);
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

        layoutManager = new LinearLayoutManager(this);
        rvQualityAsk.setLayoutManager(layoutManager);
        getQualityDataByAsyncClient();

    }

    private void getQualityDataByAsyncClient() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL =  Config.GLOBAL_URL1+"inquisitionInfo/qureyInquisition?highInquisition=0";

        client.get(URL, params, new AsyncHttpResponseHandler() {

            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result,CodeMsgMItemsBean.class);
               if (codeMsgMItemsBean.getMSG().equals("success")){
                   List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
                   for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++){
                       Map<String,Object> map = new HashMap<String, Object>();
                       map.put("tvTitle", codeMsgMItemsBean.getITEMS().get(j).get("inquisitionBref"));
                       map.put("tvDescribe", codeMsgMItemsBean.getITEMS().get(j).get("inquisitionRequest"));
                       map.put("tvAnswer", codeMsgMItemsBean.getITEMS().get(j).get("inquisitionResponse"));
                       listmaps.add(map);

                       cardList.add(codeMsgMItemsBean.getITEMS().get(j).get("id"));

                   }
                   qualityAskAdapter = new QualityAskAdapter(listmaps);
                   rvQualityAsk.setAdapter(qualityAskAdapter);
                   qualityAskAdapter.setOnItemClickListener(new QualityAskAdapter.OnRecyclerViewItemClickListener() {
                       @Override
                       public void onItemClick(View view, int position) {

                           Intent intent = new Intent(QualityAskAct.this,QualityAskDetailAct.class);
                           String id = cardList.get(position).toString();
                           intent.putExtra("ID",id);
                           startActivity(intent);
                       }
                   });
               }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(QualityAskAct.this,"网络异常，请检查网络状态",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
