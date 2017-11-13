package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.DoctListAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Bean.MapBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.View.ExpandTabView;
import com.example.nativeMall.View.ViewDoctListL;
import com.example.nativeMall.View.ViewDoctListM;
import com.example.nativeMall.View.ViewDoctListR;
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

public class DoctListAct extends AppCompatActivity {


    private ExpandTabView expandTabInDoctL;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewDoctListL viewLeft;
    private ViewDoctListM viewMid;
    private ViewDoctListR viewRight;
    private CustomTitle customTitle;
    private RecyclerView rvDoctList;
    private DoctListAdapter doctListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String URL = Config.GLOBAL_URL1;
    private static final String TAG = "DoctListAct";
    private List<MapBean> mapBeanList = new ArrayList<>();
    private List<Map<String, Object>> listmapsDoct = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doct_list);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        if (Config.ToDoctListFlag == 1) {
            String deptName = intent.getStringExtra("deptName");
            URL = URL + "departmentInfo/queryDoctorByDeptname?deptName=" + deptName +
                    "&deptLevel=0";
        } else if (Config.ToDoctListFlag == 2) {
            URL = URL + "recommendDoctorInfo/getAllDoctor";
        }

        initView();
        initVaule();
        initListener();
        getDoctListByDefalutLocation();

    }

    private void getDoctListByDefalutLocation() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(URL, params, new AsyncHttpResponseHandler() {
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("success")){
                    SetAdapterByAsyncData(codeMsgMItemsBean);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(DoctListAct.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {

        customTitle = (CustomTitle) findViewById(R.id.custom_title);
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

                Intent intent = new Intent(DoctListAct.this,MessageCenterActivity.class);
                startActivity(intent);

            }
        });

        expandTabInDoctL = (ExpandTabView) findViewById(R.id.expandtab_in_doctlist);
        viewLeft = new ViewDoctListL(this);
        viewMid = new ViewDoctListM(this);
        viewRight = new ViewDoctListR(this);
        rvDoctList = (RecyclerView) findViewById(R.id.rv_doct_detail);
        layoutManager = new LinearLayoutManager(this);
        rvDoctList.setLayoutManager(layoutManager);
        DividerLine dividerLine = new DividerLine();
        dividerLine.setColor(Config.line);
        dividerLine.setSize(1);
        rvDoctList.addItemDecoration(dividerLine);

    }

    private void initVaule() {
        mViewArray.add(viewLeft);
        mViewArray.add(viewMid);
        mViewArray.add(viewRight);

        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("城市");
        mTextArray.add("医院");
        mTextArray.add("科室");

        expandTabInDoctL.setValue(mTextArray, mViewArray);
//        expandTabInDoctL.setTitle(viewLeft.getShowText(), 0);
//        expandTabInDoctL.setTitle(viewMid.getShowText(), 1);
//        expandTabInDoctL.setTitle(viewRight.getShowText(), 2);
    }

    private void initListener() {

        viewLeft.setOnSelectListener(new ViewDoctListL.OnSelectListener() {

            @Override
            public void getValue(String showText, String cityId) {
                onRefresh(viewLeft, showText);
                listmapsDoct.clear();  //将list清空，否则数据叠加；

                getDoctlistByCity(cityId);

            }
        });

        viewMid.setOnSelectListener(new ViewDoctListM.OnSelectListener() {

            @Override
            public void getValue(String showText, String HosId) {
                onRefresh(viewMid, showText);
                listmapsDoct.clear();//清空，
                getDoctlistByHosId(HosId);
            }
        });

        viewRight.setOnSelectListener(new ViewDoctListR.OnSelectListener() {

            @Override
            public void getValue(String showText, String DepmtId, String HosId) {
                onRefresh(viewRight, showText);
                listmapsDoct.clear();//清空，
                getDoctListByDepmtId(DepmtId, HosId);
            }
        });

    }

    private void getDoctListByDepmtId(String DepmtId, String HosId) {

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "doctorInfo/inquirydoctorinfo?" + "hospitalId=" + HosId
                + "&firstDeptId=" + DepmtId; // 根据部门查询所有医生
        Log.i(TAG, "getDoctListByDepmtId: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("SUCCESS")){
                    SetAdapterByAsyncData(codeMsgMItemsBean);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(DoctListAct.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDoctlistByHosId(String HosId) {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "doctorInfo/inquirydoctorinfo?hospitalId=" + HosId; //
        // 根据医院查询所有医生
        Log.i(TAG, "getDoctlistByHosId: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("SUCCESS")){
                    SetAdapterByAsyncData(codeMsgMItemsBean);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(DoctListAct.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDoctlistByCity(String cityId) {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "doctorInfo/inquirydoctorinfo?cityid=" + cityId; //
        // 根据城市查询所有医生
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.equals("SUCCESS")){
                    SetAdapterByAsyncData(codeMsgMItemsBean);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(DoctListAct.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onRefresh(View view, String showText) {

        expandTabInDoctL.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabInDoctL.getTitle(position).equals(showText)) {
            if (showText.length() > 5) {
                String text = showText.substring(0, 4);
                expandTabInDoctL.setTitle(text + "...", position);
            } else {
                expandTabInDoctL.setTitle(showText, position);

            }
            Toast.makeText(DoctListAct.this, showText, Toast.LENGTH_SHORT).show();
        }


    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {

        if (!expandTabInDoctL.onPressBack()) {
            finish();
        }

    }

    private void SetAdapterByAsyncData(CodeMsgMItemsBean codeMsgMItemsBean) {

        listmapsDoct.clear();
        mapBeanList.clear();
        for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
            Map<String, Object> map = new HashMap<>();
            map.put("ivDoctImg", codeMsgMItemsBean.getITEMS().get(j).get("doctorUrl"));
            map.put("tvDoctName", codeMsgMItemsBean.getITEMS().get(j).get("doctorName"));
            map.put("tvHosName", codeMsgMItemsBean.getITEMS().get(j).get("hospitalName"));
            map.put("tvHosLevel", codeMsgMItemsBean.getITEMS().get(j).get("hospitalLevel"));
            map.put("tvDoctField", codeMsgMItemsBean.getITEMS().get(j).get("doctorField"));
            listmapsDoct.add(map);
            MapBean mapBean = new MapBean();
            mapBean.setMap(codeMsgMItemsBean.getITEMS().get(j));
            mapBeanList.add(mapBean);
        }
        doctListAdapter = new DoctListAdapter(listmapsDoct);
        rvDoctList.setAdapter(doctListAdapter);
        doctListAdapter.setOnItemClickListener(new DoctListAdapter
                .OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                MapBean DoctMsg = mapBeanList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("DoctMsg", DoctMsg);
                if (Config.ToDoctListFlag == 1) {
                    Intent intent = new Intent(DoctListAct.this, DoctDateDetailAct.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (Config.ToDoctListFlag == 2) {
                    Intent intent = new Intent(DoctListAct.this, DoctInfoAct.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
