package com.example.nativeMall.ui.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.CodeMsgItemBean;
import com.example.nativeMall.Bean.MsgCodeBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class DateListDetailAct extends AppCompatActivity {

    @BindView(R.id.rl_is_cancel)
    RelativeLayout rlIsCancel;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.iv_doct_img)
    SmartImageView ivDoctImg; //接口暂无数据
    @BindView(R.id.tv_doct_name)
    TextView tvDoctName;
    @BindView(R.id.tv_depmt)
    TextView tvDepmt;
    @BindView(R.id.tv_doct_level)
    TextView tvDoctLevel;  //接口暂无数据
    @BindView(R.id.tv_real_hos_name)
    TextView tvRealHosName;
    @BindView(R.id.tv_depmt_name1)
    TextView tvDepmtName1;
    @BindView(R.id.tv_date_time)
    TextView tvDateTime;
    @BindView(R.id.tv_date_type)
    TextView tvDateType;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_pay_method)
    TextView tvPayMethod;
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;
    @BindView(R.id.tv_patient_id_number)
    TextView tvPatientIdNumber;
    @BindView(R.id.tv_once_or_twice)
    TextView tvOnceOrTwice;
    @BindView(R.id.tv_patient_phone)
    TextView tvPatientPhone;
    @BindView(R.id.tv_is_cancel) //隐藏的订单状态 暂时不传
            TextView tvIsCancel;
    @BindView(R.id.bt_pay)
    Button btPay;
    private String Id;  //点击的Item的Id;
    private String delFlag; //点击的Item是否取消；
    private static final String TAG = "DateListDetailAct";
    private String HosName,FirtDpmName,Price,PayMethod,Appid,submerno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Intent intent = getIntent();

        Id = intent.getStringExtra("Id");
        delFlag = intent.getStringExtra("delFlag");
        PayMethod = intent.getStringExtra("PayMethod");
        getItemInfoByAsyncClient();

        if (delFlag.equals("已取消")) {
            tvCancle.setVisibility(View.GONE);
            rlIsCancel.setVisibility(View.VISIBLE);
            btPay.setVisibility(View.GONE);
        } else if (delFlag.equals("未支付")) {
            btPay.setVisibility(View.VISIBLE);
        }else if (delFlag.equals("已预约")){
            btPay.setVisibility(View.GONE);
            if (PayMethod.equals("在线支付")){
                tvCancle.setText("退款");
                tvCancle.setClickable(false);
            }else {
                //
            }
        }else if(delFlag.equals("已支付")){
            btPay.setVisibility(View.GONE);
            tvCancle.setText("退款");
        }

    }

    private void getItemInfoByAsyncClient() {

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "orderInfo/getOrderByid?id=" + Id + "&userId=" + Config
                .userBean
                .getData().getUid();
        Log.i(TAG, "getItemInfoByAsyncClient: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgItemBean codeMsgItemBean = gson.fromJson(result, CodeMsgItemBean.class);
               if (codeMsgItemBean.getMSG().equals("success")){
                   // ivDoctImg.setImageUrl(Config.PIC_URL1 + codeMsgItemBean.getITEM().get("?"));
                   // 接口暂无数据
                   tvDoctName.setText(codeMsgItemBean.getITEM().get("doctorName").toString());
                   tvDepmt.setText(codeMsgItemBean.getITEM().get("firstDeptName").toString());
                   // tvDoctLevel.setText(codeMsgItemBean.getITEM().get("?").toString());//暂无数据
                   tvRealHosName.setText(codeMsgItemBean.getITEM().get("hospitalName").toString());

                   tvDepmtName1.setText(codeMsgItemBean.getITEM().get("firstDeptName").toString());

                   tvDateTime.setText(codeMsgItemBean.getITEM().get("workStatrDt").toString());
                   tvDateType.setText(codeMsgItemBean.getITEM().get("visitType").toString());
                   tvPrice.setText(codeMsgItemBean.getITEM().get("registerFee").toString() + "元");
                   tvPayMethod.setText(codeMsgItemBean.getITEM().get("payMethod").toString());
                   tvPatientName.setText(codeMsgItemBean.getITEM().get("patientName").toString());
                   String strId = codeMsgItemBean.getITEM().get("patientId").toString();
                   String IdNumberl = strId.substring(0, 7) + "**********" + strId.substring(16);
                   tvPatientIdNumber.setText(IdNumberl);
                   tvOnceOrTwice.setText(codeMsgItemBean.getITEM().get("hasVisit").toString());
                   String strPhone = codeMsgItemBean.getITEM().get("patientPhoneno").toString();
                   String Phone = strPhone.substring(0, 3) + "****" + strPhone.substring(7);
                   tvPatientPhone.setText(Phone);

                   HosName = codeMsgItemBean.getITEM().get("hospitalName").toString();
                   FirtDpmName = codeMsgItemBean.getITEM().get("firstDeptName").toString();
                   Price = codeMsgItemBean.getITEM().get("registerFee").toString();
                   Appid = codeMsgItemBean.getITEM().get("appid").toString();
                   submerno = codeMsgItemBean.getITEM().get("submerno").toString();
               }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @OnClick({R.id.iv_choose_doc_back, R.id.tv_cancle, R.id.bt_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.tv_cancle:
                AlertDialog.Builder builder = new AlertDialog.Builder(DateListDetailAct.this);
                builder.setMessage("确认取消预约？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteListItemById();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.bt_pay:
                Intent intent = new Intent(DateListDetailAct.this,PayActivity.class);
                intent.putExtra("sname", HosName + "(" + FirtDpmName + ")");
                intent.putExtra("price", Price);
                intent.putExtra("orderid",Id);
                intent.putExtra("appid",Appid);
                intent.putExtra("submerno",submerno);
                intent.putExtra("fromFlag","0");  // 商城：1 ，寻医问药：0；
                startActivity(intent);
                finish();
        }
    }

    private void deleteListItemById() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "orderInfo/deleteOrder?userId=" + Config.userBean
                .getData().getUid() + "&id=" + Id;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                MsgCodeBean msgCodeBean = gson.fromJson(result, MsgCodeBean.class);
                if (msgCodeBean.getMSG().equals("success")) {

                    rlIsCancel.setVisibility(View.VISIBLE);
                    tvCancle.setVisibility(View.GONE);
                    btPay.setVisibility(View.GONE);

                    Toast.makeText(DateListDetailAct.this, "取消预约成功", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(DateListDetailAct.this, "网络异常，未取消成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
