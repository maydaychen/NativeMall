package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nativeMall.Adapter.DateListAdapter;
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

public class DateListAct extends AppCompatActivity {
    @BindView(R.id.custom_title)
    CustomTitle customTitle;

    @BindView(R.id.rv_date_list)
    RecyclerView rvDataList;
    private DateListAdapter dateListAdapter;
    private List listItemId = new ArrayList(); // 存列表Item中的Id；
    private List<Map<String, Object>> listmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvDataList.setLayoutManager(layoutManager);
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

    @Override
    protected void onResume() {
        super.onResume();
        getDateListByAsyncClient();
    }


    private void getDateListByAsyncClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "orderInfo/getAllOrder";
        RequestParams params = new RequestParams();
        params.put("userId", Config.userBean.getData().getUid());
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(final int i, Header[] headers, byte[] bytes) {
                listmaps.clear();
                String result = new String(bytes);
                Gson gson = new Gson();
                final CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result,
                        CodeMsgMItemsBean.class);

                if (codeMsgMItemsBean.getMSG().equals("success")) {
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<>();
//                    map.put("ivDoctImg",codeMsgMItemsBean.getITEMS().get(j).get(""));接口暂时无该信息
                        map.put("tvDoctName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("doctorName"));
                        map.put("tvDempt", codeMsgMItemsBean.getITEMS().get(j).get
                                ("firstDeptName"));
//                    map.put("tvDoctLevel",codeMsgMItemsBean.getITEMS().get(j).get(""));暂无
                        map.put("tvPrice", codeMsgMItemsBean.getITEMS().get(j).get("registerFee")
                                + "元");
                        map.put("tvHospName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalName"));
                        map.put("tvPatientName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("patientName"));
                        map.put("tvIsCancel", codeMsgMItemsBean.getITEMS().get(j).get("delFlag"));
                        map.put("tvDateTime", codeMsgMItemsBean.getITEMS().get(j).get
                                ("workStatrDt"));
                        map.put("PayMethod", codeMsgMItemsBean.getITEMS().get(j).get("payMethod"));
                        listmaps.add(map);
                        listItemId.add(codeMsgMItemsBean.getITEMS().get(j).get("id"));

                    }
                    dateListAdapter = new DateListAdapter(listmaps);
                    rvDataList.setAdapter(dateListAdapter);
                    dateListAdapter.setOnItemClickListener(new DateListAdapter
                            .OnRecyclerViewItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            //将点击的预约单传到下个Act;
                            String Id = listItemId.get(position).toString();
                            String delFlag = listmaps.get(position).get("tvIsCancel").toString();
                            String PayMethod = listmaps.get(position).get("PayMethod").toString();
                            Intent intent = new Intent(DateListAct.this, DateListDetailAct.class);
                            intent.putExtra("Id", Id);
                            intent.putExtra("delFlag", delFlag);
                            intent.putExtra("PayMethod", PayMethod);
                            startActivity(intent);
                            listmaps.clear();
                            dateListAdapter.notifyDataSetChanged();
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
