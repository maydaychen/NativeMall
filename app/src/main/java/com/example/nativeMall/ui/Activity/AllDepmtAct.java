package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.nativeMall.Adapter.AllDepmtAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.example.nativeMall.ui.widget.RecycleViewDivider;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class AllDepmtAct extends AppCompatActivity {


    private CustomTitle customTitlel;
    private RecyclerView rvRegDepmt;
    private RecyclerView.LayoutManager layoutManager;
    private String URL = Config.GLOBAL_URL1 + "departmentInfo/queryFirstDepartment?deptLevel=0";
    private AllDepmtAdapter allDepmtAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_depmt);
        getSupportActionBar().hide();


        customTitlel = (CustomTitle)findViewById(R.id.custom_title);
        rvRegDepmt =(RecyclerView)findViewById(R.id.rv_reg_depmt);
        layoutManager = new LinearLayoutManager(this);
        rvRegDepmt.setLayoutManager(layoutManager);
        rvRegDepmt.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));


        customTitlel.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {
                Toast.makeText(AllDepmtAct.this,"jump",Toast.LENGTH_SHORT).show();
            }
        });
        getDepmtDataByAsyncHttp();




    }

    private void getDepmtDataByAsyncHttp() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result,CodeMsgMItemsBean.class);
               if (codeMsgMItemsBean.getMSG().equals("SUCCESS")){
                   List<Map<String,Object>> listmaps = new ArrayList<Map<String, Object>>();
                   for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++){
                       Map<String,Object> map = new HashMap<String, Object>();
                       map.put("tvPatientName", codeMsgMItemsBean.getITEMS().get(j).get("deptName"));
                       // map.put("img",codeMsgMItemsBean.getITEMS().get(j).get("imgUrl"));
                       listmaps.add(map);
                   }
                   allDepmtAdapter = new AllDepmtAdapter(listmaps);
                   rvRegDepmt.setAdapter(allDepmtAdapter);
                   allDepmtAdapter.setOnItemClickListener(new AllDepmtAdapter.OnRecyclerViewItemClickListener() {
                       @Override
                       public void onItemClick(View view, int position) {
                           String deptName = OnekeyRegAct.DepmtNameList.get(position).toString();
                           Intent intent = new Intent(AllDepmtAct.this,DoctListAct.class);
                           intent.putExtra("deptName",deptName);
                           startActivity(intent);
                       }
                   });
               }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }
}
