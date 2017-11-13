package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.DoctListAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Bean.MapBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.View.ExpandTabView;
import com.example.nativeMall.View.ViewHosDetaiL;
import com.example.nativeMall.View.ViewHosDetaiM;
import com.example.nativeMall.View.ViewHosDetaiR;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class HospDetailAct extends AppCompatActivity {


    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.iv_hos_img)
    SmartImageView ivHosImg;
    @BindView(R.id.tv_hos_name)
    TextView tvHosName;
    @BindView(R.id.tv_doct_num)
    TextView tvDoctNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_hos_level)
    TextView tvHosLevel;
    @BindView(R.id.tv_look_all_hosp)
    TextView tvLookAllHosp;
    @BindView(R.id.expandtab_in_hosdetail)
    ExpandTabView expandtabInHosdetail;
    @BindView(R.id.rv_doct_list)
    RecyclerView rvDoctList;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewHosDetaiL viewHosDetaiL;
    private ViewHosDetaiM viewHosDetaiM;
    private ViewHosDetaiR viewHosDetaiR;
    public static DoctListAdapter doctListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String TAG = "HospDetailAct";
    private MapBean HosMsg;
    public static String HosId;
    private List<MapBean> listDoctBeanList = new ArrayList<>();
    public static List<Map<String, Object>> listDoctInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosp_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        initView();
        initVaule();
        initListener();
        getDoctListDataByAsync();

    }

    private void getDoctListDataByAsync() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "doctorInfo/inquirydoctorinfo?hospitalId=" + HosId;

        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ivDoctImg", codeMsgMItemsBean.getITEMS().get(j).get("doctorUrl"));
                    map.put("tvDoctName", codeMsgMItemsBean.getITEMS().get(j).get("doctorName"));
                    map.put("tvHosName", codeMsgMItemsBean.getITEMS().get(j).get("hospitalName"));
                    map.put("tvHosLevel", codeMsgMItemsBean.getITEMS().get(j).get("hospitalLevel"));
                    map.put("tvDoctField", codeMsgMItemsBean.getITEMS().get(j).get("doctorField"));
                    listDoctInfo.add(map);
                    MapBean mapBean = new MapBean();
                    mapBean.setMap(codeMsgMItemsBean.getITEMS().get(j));
                    listDoctBeanList.add(mapBean);
                }
                doctListAdapter = new DoctListAdapter(listDoctInfo);
                rvDoctList.setAdapter(doctListAdapter);
                doctListAdapter.setOnItemClickListener(new DoctListAdapter
                        .OnRecyclerViewItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        MapBean mapBean = listDoctBeanList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("DoctMsg", mapBean);
                        Intent intent = new Intent(HospDetailAct.this, DoctDateDetailAct.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(HospDetailAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {

        Intent intent = getIntent();
        HosMsg = intent.getParcelableExtra("HosMsg");
        HosId = HosMsg.getMap().get("hospitalId").toString();


        listDoctInfo.clear();
        ivHosImg.setImageUrl(Config.PIC_URL + HosMsg.getMap().get("hospitalUrl"));
        tvHosName.setText(HosMsg.getMap().get("hospitalName").toString());
        tvDoctNum.setText(HosMsg.getMap().get("doctorCount").toString());
        tvAddress.setText(HosMsg.getMap().get("hospitalAddress").toString());
        tvHosLevel.setText(HosMsg.getMap().get("hospitalLevel").toString());

        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });

        tvLookAllHosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HospDetailAct.this,HosListAct.class);
                startActivity(intent1);
            }
        });


        viewHosDetaiL = new ViewHosDetaiL(this);
        viewHosDetaiM = new ViewHosDetaiM(this);
        viewHosDetaiR = new ViewHosDetaiR(this);

        layoutManager = new LinearLayoutManager(this);
        rvDoctList.setLayoutManager(layoutManager);
        DividerLine dividerLine = new DividerLine();
        dividerLine.setColor(Config.line);
        dividerLine.setSize(1);
        rvDoctList.addItemDecoration(dividerLine);


    }

    private void initVaule() {

        mViewArray.add(viewHosDetaiL);
        mViewArray.add(viewHosDetaiM);
        mViewArray.add(viewHosDetaiR);

        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("科室");
        mTextArray.add("全部医生");
        mTextArray.add("不限时间");

        expandtabInHosdetail.setValue(mTextArray, mViewArray);
//        expandtabInHosdetail.setTitle(viewHosDetaiL.getShowText(), 0);
//        expandtabInHosdetail.setTitle(viewHosDetaiM.getShowText(), 1);
//        expandtabInHosdetail.setTitle(viewHosDetaiR.getShowText(), 2);

    }

    private void initListener() {

        viewHosDetaiL.setOnSelectListener(new ViewHosDetaiL.OnSelectListener() {

            @Override
            public void getValue(String showText, String SecondDepmtId) {
                onRefresh(viewHosDetaiL, showText);
                getDoctListByDepmtId(SecondDepmtId, ViewHosDetaiM.ChosedLevelId, ViewHosDetaiR
                        .ChosedWorkTimeId);

            }
        });

        viewHosDetaiM.setOnSelectListener(new ViewHosDetaiM.OnSelectListener() {

            @Override
            public void getValue(String showText, String LevelId) {
                onRefresh(viewHosDetaiM, showText);
                getDoctListByDepmtId(ViewHosDetaiL.ChosedDepmtId, LevelId, ViewHosDetaiR
                        .ChosedWorkTimeId);
            }
        });

        viewHosDetaiR.setOnSelectListener(new ViewHosDetaiR.OnSelectListener() {

            @Override
            public void getValue(String showText, String workTimeId) {
                onRefresh(viewHosDetaiR, showText);
                getDoctListByDepmtId(ViewHosDetaiL.ChosedDepmtId, ViewHosDetaiM.ChosedLevelId, workTimeId);
            }
        });

    }


    private void getDoctListByDepmtId(String ChosedDepmtId, String ChosedLevelId, String
            ChosedWorkTimeId) {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "doctorInfo/inquirydoctorinfo?hospitalId=" + HosId +
                "&secondDeptId=" + ChosedDepmtId + "&doctorLevel=" + ChosedLevelId +
                "&workStatrDt=" + ChosedWorkTimeId;
        Log.i(TAG, "getDoctListByDepmtId: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                SetListAdapter(codeMsgMItemsBean);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(HospDetailAct.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SetListAdapter(CodeMsgMItemsBean codeMsgMItemsBean) {
        listDoctInfo.clear();
        listDoctBeanList.clear();
        for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
            Map<String, Object> map = new HashMap<>();
            map.put("ivDoctImg", codeMsgMItemsBean.getITEMS().get(j).get("doctorUrl"));
            map.put("tvDoctName", codeMsgMItemsBean.getITEMS().get(j).get("doctorName"));
            map.put("tvHosName", codeMsgMItemsBean.getITEMS().get(j).get("hospitalName"));
            map.put("tvHosLevel", codeMsgMItemsBean.getITEMS().get(j).get("hospitalLevel"));
            map.put("tvDoctField", codeMsgMItemsBean.getITEMS().get(j).get("doctorField"));
            listDoctInfo.add(map);
            MapBean mapBean = new MapBean();
            mapBean.setMap(codeMsgMItemsBean.getITEMS().get(j));
            listDoctBeanList.add(mapBean);
        }
        doctListAdapter = new DoctListAdapter(listDoctInfo);
        rvDoctList.setAdapter(doctListAdapter);
        doctListAdapter.setOnItemClickListener(new DoctListAdapter
                .OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                MapBean DoctMsg = listDoctBeanList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("DoctMsg", DoctMsg);
                if (Config.ToDoctListFlag == 1) {
                    Intent intent = new Intent(HospDetailAct.this, DoctDateDetailAct.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (Config.ToDoctListFlag == 2) {
                    Intent intent = new Intent(HospDetailAct.this, DoctInfoAct.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void onRefresh(View view, String showText) {

        expandtabInHosdetail.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandtabInHosdetail.getTitle(position).equals(showText)) {
            if (showText.length() > 5){
                String text = showText.substring(0,4);
                expandtabInHosdetail.setTitle(text + "...",position);
            }else {
                expandtabInHosdetail.setTitle(showText, position);
            }

        }
        Toast.makeText(HospDetailAct.this, showText, Toast.LENGTH_SHORT).show();

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

        if (!expandtabInHosdetail.onPressBack()) {
            finish();
        }

    }
}
