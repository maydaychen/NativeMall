package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.ScheduleAdapter;
import com.example.nativeMall.Bean.CodMsIteDocBean;
import com.example.nativeMall.Bean.DateDataBean;
import com.example.nativeMall.Bean.MapBean;
import com.example.nativeMall.Bean.TimeAndCostBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class DoctDateDetailAct extends AppCompatActivity {


    @BindView(R.id.tv_no_date)
    TextView tvNoDate;
    private SmartImageView ivDorImg;
    private TextView tvDorName, tvDorLevel, tvHosName, tvDepmtName;
    private TextView tvDorField, tvHosLevel, tvDepmtHos, tvDateSum;
    private String URL;
    private CustomTitle customTitle;
    private static final String TAG = "DoctDateDetailAct";

    private RecyclerView rvScheduleTable;
    private RecyclerView.LayoutManager layoutManager;
    private ScheduleAdapter scheduleAdapter;
    private String doctorId;
    private String hospitalId;
    private String firstDeptId;
    private String secondDeptId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doct_date_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        MapBean DoctMsg = intent.getParcelableExtra("DoctMsg");
        Log.i(TAG, "onCreate: " + DoctMsg);
        Log.i(TAG, "onCreate: " + DoctMsg.getMap().get("doctorName").toString());

        doctorId = DoctMsg.getMap().get("doctorId").toString();
        hospitalId = DoctMsg.getMap().get("hospitalId").toString();
        firstDeptId = DoctMsg.getMap().get("firstDeptId").toString();
        secondDeptId = DoctMsg.getMap().get("secondDeptId").toString();


        URL = Config.GLOBAL_URL1 + "doctorInfo/inquiryalldoctorinfo?Id=" + doctorId +
                "&hospitalId=" + hospitalId +
                "&firstDeptId=" + firstDeptId + "&secondDeptId=" + secondDeptId;

        Log.i(TAG, "onCreate: " + URL);
        InitView();
        getDoctDataByAsyncHttp();


        tvDepmtHos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DoctDateDetailAct.this, DateMsgAct.class);
                startActivity(intent1);
            }
        });
    }


    private void InitView() {


        customTitle = (CustomTitle) findViewById(R.id.custom_title);
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {
                Intent intent = new Intent(DoctDateDetailAct.this, MessageCenterActivity.class);
                startActivity(intent);
            }
        });

        ivDorImg = (SmartImageView) findViewById(R.id.iv_doct);
        tvDorName = (TextView) findViewById(R.id.tv_doct_name);
        tvHosName = (TextView) findViewById(R.id.tv_hos_name);
        tvHosLevel = (TextView) findViewById(R.id.tv_hos_level);
        tvDepmtName = (TextView) findViewById(R.id.tv_depmt);
        tvDorLevel = (TextView) findViewById(R.id.tv_level);
        tvDorField = (TextView) findViewById(R.id.tv_doct_field);
        tvDepmtHos = (TextView) findViewById(R.id.tv_depmt_hos);
        tvDateSum = (TextView) findViewById(R.id.tv_patient_num);
        rvScheduleTable = (RecyclerView) findViewById(R.id.rv_schedule_table);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvScheduleTable.setLayoutManager(layoutManager);

    }

    private void getDoctDataByAsyncHttp() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Log.i(TAG, "getDoctDataByAsyncHttp: " + URL);
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Log.i(TAG, "onSuccess: " + result);
                Gson gson = new Gson();
                String lastDate;
                CodMsIteDocBean codMsIteDocBean = gson.fromJson(result, CodMsIteDocBean
                        .class);
                if (codMsIteDocBean.getMSG().equals("success")) {
                    List<DateDataBean> dataBeenlist = codMsIteDocBean.getITEMS();
                    //已有几日排版：
                    if (dataBeenlist.size() < 7) {
                        for (int num = dataBeenlist.size(); num < 7; num++) {
                            DateDataBean dateDataBean = new DateDataBean();
                            if (num > 1) {
                                lastDate = dataBeenlist.get(num - 1).getWorkTime();

                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date temp = sdf.parse(lastDate);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(temp);
                                    cal.add(Calendar.DATE, 1);

                                    dateDataBean.setWorkTime(new SimpleDateFormat("yyyy-MM-dd")
                                            .format(cal.getTime()).toString());
                                    String date = dateDataBean.getWorkTime();
                                    List<TimeAndCostBean> temp1 = new ArrayList<>();
                                    TimeAndCostBean temple = new TimeAndCostBean();
                                    temple.setWorkTime(date);
                                    temple.setWorkAmPm("a");
                                    temple.setVisitType("");
                                    temple.setWorkPrice("");
                                    temp1.add(temple);
                                    dateDataBean.setData(temp1);
                                    dataBeenlist.add(num, dateDataBean);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                num = 8;
                            }

                        }

                    }
                    List<TimeAndCostBean> totalUnitList = new ArrayList<>(); //所有的data
                    final List<String> workTimeList = new ArrayList<>(); //所有的workTime

                    List<Map<String, Object>> listmaps = new ArrayList<>();
                    for (int j = 0; j < dataBeenlist.size(); j++) {
                        List<TimeAndCostBean> timeAndCostBeenlist = dataBeenlist.get(j).getData();
                        workTimeList.add(dataBeenlist.get(j).getWorkTime());
                        for (int k = 0; k < timeAndCostBeenlist.size(); k++) {
                            TimeAndCostBean timeAndCostBean = timeAndCostBeenlist.get(k);
                            totalUnitList.add(timeAndCostBean);

                        }
                        Map<String, Object> map = new HashMap<>();

                        if (timeAndCostBeenlist.size() == 1) {
                            if (timeAndCostBeenlist.get(0).getWorkAmPm().equals("a")) {
                                map.put("workTime", timeAndCostBeenlist.get(0).getWorkTime());
                                map.put("headerIdAm", timeAndCostBeenlist.get(0).getHeaderId());
                                map.put("workStatrDtAm", timeAndCostBeenlist.get(0)
                                        .getWorkStatrDt());
                                map.put("visiTypeAm", timeAndCostBeenlist.get(0).getVisitType());
                                map.put("workPriceAm", timeAndCostBeenlist.get(0).getWorkPrice());

                                map.put("headIdPm", "");
                                map.put("workStatrDtPm", "");
                                map.put("vistTypePm", "");
                                map.put("workPricePm", "");

                                map.put("doctorUrl", codMsIteDocBean.getDoctor().get("doctorUrl")
                                        .toString());
                                map.put("doctorName", codMsIteDocBean.getDoctor().get("doctorName")
                                        .toString());
                                map.put("firstDeptName", codMsIteDocBean.getDoctor().get
                                        ("firstDeptName").toString());
                                map.put("doctorLevel", codMsIteDocBean.getDoctor().get
                                        ("doctorLevel")
                                        .toString());
                                map.put("hospitalName", codMsIteDocBean.getDoctor().get
                                        ("hospitalName").toString());

                            } else if (timeAndCostBeenlist.get(0).getWorkAmPm().equals("p")) {
                                map.put("workTime", timeAndCostBeenlist.get(0).getWorkTime());

                                map.put("headerIdAm", "");
                                map.put("workStatrDtAm", "");
                                map.put("visiTypeAm", "");
                                map.put("workPriceAm", "");

                                map.put("headerIdPm", timeAndCostBeenlist.get(0).getHeaderId());
                                map.put("workStatrDtPm", timeAndCostBeenlist.get(0)
                                        .getWorkStatrDt());
                                map.put("vistTypePm", timeAndCostBeenlist.get(0).getVisitType());
                                map.put("workPricePm", timeAndCostBeenlist.get(0).getWorkPrice());

                                map.put("doctorUrl", codMsIteDocBean.getDoctor().get("doctorUrl")
                                        .toString());
                                map.put("doctorName", codMsIteDocBean.getDoctor().get("doctorName")
                                        .toString());
                                map.put("firstDeptName", codMsIteDocBean.getDoctor().get
                                        ("firstDeptName").toString());
                                map.put("doctorLevel", codMsIteDocBean.getDoctor().get
                                        ("doctorLevel")
                                        .toString());
                                map.put("hospitalName", codMsIteDocBean.getDoctor().get
                                        ("hospitalName").toString());

                            }
                        } else {
                            map.put("workTime", timeAndCostBeenlist.get(0).getWorkTime());

                            map.put("headerIdAm", timeAndCostBeenlist.get(0).getHeaderId());
                            map.put("workStatrDtAm", timeAndCostBeenlist.get(0).getWorkStatrDt());
                            map.put("visiTypeAm", timeAndCostBeenlist.get(0).getVisitType());
                            map.put("workPriceAm", timeAndCostBeenlist.get(0).getWorkPrice());

                            map.put("headerIdPm", timeAndCostBeenlist.get(1).getHeaderId());
                            map.put("workStatrDtPm", timeAndCostBeenlist.get(1).getWorkStatrDt());
                            map.put("vistTypePm", timeAndCostBeenlist.get(1).getVisitType());
                            map.put("workPricePm", timeAndCostBeenlist.get(1).getWorkPrice());

                            map.put("doctorUrl", codMsIteDocBean.getDoctor().get("doctorUrl")
                                    .toString());
                            map.put("doctorName", codMsIteDocBean.getDoctor().get("doctorName")
                                    .toString());
                            map.put("firstDeptName", codMsIteDocBean.getDoctor().get
                                    ("firstDeptName")
                                    .toString());
                            map.put("doctorLevel", codMsIteDocBean.getDoctor().get("doctorLevel")
                                    .toString());
                            map.put("hospitalName", codMsIteDocBean.getDoctor().get("hospitalName")
                                    .toString());


                        }
                        listmaps.add(map);

                    }
                    if (listmaps.size() == 0) {
                        tvNoDate.setVisibility(View.VISIBLE);
                    }
                    scheduleAdapter = new ScheduleAdapter(listmaps, DoctDateDetailAct.this);
                    rvScheduleTable.setAdapter(scheduleAdapter);

//                    ivDorImg.setImageUrl(Config.PIC_URL + codMsIteDocBean.getDoctor().get
//                            ("doctorUrl").toString());
                    if (!TextUtils.isEmpty(codMsIteDocBean.getDoctor().get
                            ("doctorUrl").toString())) {
                        String URL = Config.PIC_URL + codMsIteDocBean.getDoctor().get
                                ("doctorUrl").toString();
                        Util.GetCircleImg(URL, ivDorImg);
                    }

                    tvDorName.setText(codMsIteDocBean.getDoctor().get("doctorName").toString());
                    tvHosName.setText(codMsIteDocBean.getDoctor().get("hospitalName").toString());
                    tvHosLevel.setText(codMsIteDocBean.getDoctor().get("hospitalLevel").toString());
                    tvDepmtName.setText(codMsIteDocBean.getDoctor().get("firstDeptName").toString
                            ());
                    tvDorLevel.setText(codMsIteDocBean.getDoctor().get("doctorLevel").toString());
                    tvDorField.setText(codMsIteDocBean.getDoctor().get("doctorField").toString());
                    tvDepmtHos.setText(codMsIteDocBean.getDoctor().get("firstDeptName").toString() +
                            "  -  " + codMsIteDocBean.getDoctor().get("hospitalName").toString());
                    tvDateSum.setText((int) (Double.parseDouble(codMsIteDocBean.getDoctor().get
                            ("orderCount").toString())) + "");
                } else {

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "onFailure: ");
                Toast.makeText(DoctDateDetailAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
