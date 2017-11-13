package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nativeMall.Adapter.AllDepmtAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.KMPAutoComplTextView;
import com.example.nativeMall.ui.widget.RecycleViewDivider;
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
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class ConfirmSickMsgAct extends AppCompatActivity {


    @BindView(R.id.rv_disease_info)
    RecyclerView rvDiseaseInfo;
    @BindView(R.id.tvAutoCompl)
    KMPAutoComplTextView tvAutoCompl;


    private RecyclerView.LayoutManager layoutManager;
    AllDepmtAdapter allDepmtAdapter;  //暂时填充所有部门
    private List listAllDepmt = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sick);
        ButterKnife.bind(this);
        getSupportActionBar().hide();


        layoutManager = new LinearLayoutManager(this);
        rvDiseaseInfo.setLayoutManager(layoutManager);
        rvDiseaseInfo.addItemDecoration(new RecycleViewDivider(this, LinearLayout.VERTICAL));
        getDepmtDataByAsyncHttp();

    }

    @OnClick({R.id.iv_choose_doc_back, R.id.tv_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.tv_cancle:
                finish();
                break;
        }
    }

    private void getDepmtDataByAsyncHttp() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "departmentInfo/queryFirstDepartment?deptLevel=0";
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
               if (codeMsgMItemsBean.getMSG().equals("SUCCESS")){
                   List<Map<String, Object>> listmaps = new ArrayList<>();
                   for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                       Map<String, Object> map = new HashMap<>();
                       map.put("tvPatientName", codeMsgMItemsBean.getITEMS().get(j).get("deptName"));
                       // map.put("img",codeMsgMItemsBean.getITEMS().get(j).get("imgUrl"));
                       listAllDepmt.add(codeMsgMItemsBean.getITEMS().get(j).get("deptName"));
                       listmaps.add(map);
                   }
                   allDepmtAdapter = new AllDepmtAdapter(listmaps);
                   rvDiseaseInfo.setAdapter(allDepmtAdapter);
                   allDepmtAdapter.setOnItemClickListener(new AllDepmtAdapter
                           .OnRecyclerViewItemClickListener() {
                       @Override
                       public void onItemClick(View view, int position) {
                           String deptName = listAllDepmt.get(position).toString();
                           Intent intent = new Intent(ConfirmSickMsgAct.this, DateMsgAct.class);
                           intent.putExtra("deptName", deptName);
                           ConfirmSickMsgAct.this.setResult(RESULT_OK, intent);
                           ConfirmSickMsgAct.this.finish();

                       }
                   });
               }

                tvAutoCompl.setDatas(listAllDepmt);
                tvAutoCompl.setOnPopupItemClickListener(new KMPAutoComplTextView.OnPopupItemClickListener() {
                    @Override
                    public void onPopupItemClick(CharSequence charSequence) {
                        Intent intent = new Intent(ConfirmSickMsgAct.this, DateMsgAct.class);
                        intent.putExtra("deptName", charSequence);
                        ConfirmSickMsgAct.this.setResult(RESULT_OK, intent);
                        ConfirmSickMsgAct.this.finish();

                    }
                });
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }
}
