package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.GridDepmtAdapter;
import com.example.nativeMall.Adapter.HosAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Bean.MapBean;
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

import cz.msebera.android.httpclient.Header;

public class OnekeyRegAct extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView rvDepmt, rvRecommendHos;
    private RecyclerView.LayoutManager layoutManager1, layoutManager2;
    private CustomTitle customTitle;
    private static final String TAG = "OnekeyRegAct";
    public static List DepmtNameList = new ArrayList<>();
    private List HosIdList = new ArrayList<>();
    private List<MapBean> mapBeanList = new ArrayList<>();
    private RelativeLayout rlGetLocation;
    private GridDepmtAdapter gridDepmtAdapter;
    private HosAdapter hosAdapter;
    private LocationClient locationClient;
    private TextView tvCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onekey_reg);
        getSupportActionBar().hide();

        Config.ToDoctListFlag = 1;
        InitViewAndListner();
        getDepmtDataByAsyncHttpClient();
        getHosListDataAsyncHttpClient();


    }

    private void InitViewAndListner() {

        customTitle = (CustomTitle) findViewById(R.id.custom_title);
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });

        rvDepmt = (RecyclerView) findViewById(R.id.rv_depmt);
        layoutManager1 = new GridLayoutManager(OnekeyRegAct.this, 4, LinearLayout.VERTICAL, false);
        rvDepmt.setLayoutManager(layoutManager1);
        DividerLine dividerLine = new DividerLine(DividerLine.HORIZONTAL);
        dividerLine.setSize(1);
        dividerLine.setColor(0xFFEEEEEE);
//        dividerLine.setColor(Color.parseColor("#ff7272"));
        rvDepmt.addItemDecoration(dividerLine);

        rvRecommendHos = (RecyclerView) findViewById(R.id.rv_recommend_hospital);
        layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecommendHos.setLayoutManager(layoutManager2);
        DividerLine dividerLine1 = new DividerLine(DividerLine.VERTICAL);
        dividerLine1.setSize(1);
        dividerLine1.setColor(0xFFEEEEEE);
        rvRecommendHos.addItemDecoration(dividerLine1);


        tvCity = (TextView) findViewById(R.id.tv_real_city);

        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new Mylistner());

        rlGetLocation = (RelativeLayout) findViewById(R.id.rl_getlocation);
        rlGetLocation.setOnClickListener(this);
    }

    private void getHosListDataAsyncHttpClient() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
//        String URL = Config.GLOBAL_URL1 + "?";
        String URL = Config.GLOBAL_URL1 + "hospitalRecommend/inquiryrecommendhospital";
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("success")) {
                    List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ivHosImg", codeMsgMItemsBean.getITEMS().get(j).get("hospitalUrl"));
                        map.put("tvHosName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalName"));
                        map.put("tvNum", codeMsgMItemsBean.getITEMS().get(j).get("doctorCount"));
                        map.put("tvAddress", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalAddress"));
                        map.put("tvHosLevel", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalLevel"));

                        listmaps.add(map);
                        MapBean HosMsg = new MapBean();
                        HosMsg.setMap(codeMsgMItemsBean.getITEMS().get(j));
                        mapBeanList.add(HosMsg);
                    }

                    hosAdapter = new HosAdapter(listmaps);
                    rvRecommendHos.setAdapter(hosAdapter);
                    hosAdapter.setOnItemClickListener(new HosAdapter
                            .OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            MapBean HosMsg = mapBeanList.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("HosMsg", HosMsg);
                            Intent intent = new Intent(OnekeyRegAct.this, HospDetailAct.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(OnekeyRegAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getDepmtDataByAsyncHttpClient() {

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
                if (codeMsgMItemsBean.getMSG().equals("SUCCESS")) {
                    List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
                    for (int j = 0; j < 7; j++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("tvPatientName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("deptName"));
                        listmaps.add(map);
                        DepmtNameList.add(codeMsgMItemsBean.getITEMS().get(j).get("deptName"));
                    }
                    Map<String, Object> mapAppend = new HashMap<String, Object>();
                    mapAppend.put("tvPatientName", "全部科室");
                    listmaps.add(mapAppend);
//                Log.i(TAG, "onSuccess: " + DepmtNameList);

                    gridDepmtAdapter = new GridDepmtAdapter(listmaps);
                    rvDepmt.setAdapter(gridDepmtAdapter);
                    gridDepmtAdapter.setOnItemClickListener(new GridDepmtAdapter
                            .OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (position != 7) {
                                String deptName = DepmtNameList.get(position).toString();
                                Intent intent = new Intent(OnekeyRegAct.this, DoctListAct.class);
                                intent.putExtra("deptName", deptName);
                                startActivity(intent);
                            } else {
                                // Toast.makeText(ChoseDoctActivity.this,"other",Toast
                                // .LENGTH_SHORT).show();
                                Intent intent = new Intent(OnekeyRegAct.this, AllDepmtAct.class);
                                startActivity(intent);
                            }

                        }
                    });
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(OnekeyRegAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_getlocation:
                InitLocation();
                locationClient.start();
                break;

        }
    }

    public class Mylistner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            tvCity.setText(bdLocation.getCity());
            Toast.makeText(OnekeyRegAct.this, "定位成功：+la:" + bdLocation.getLatitude() + " ::" +
                    bdLocation.getAddrStr(), Toast.LENGTH_LONG).show();

            if (!"".equals(tvCity.getText())) {
                locationClient.stop();
//                Log.i(TAG, "onReceiveLocation:  结束定位" );
            }
        }
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式

        int span = 1000;
        option.setScanSpan(span * 5);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
    }


}
