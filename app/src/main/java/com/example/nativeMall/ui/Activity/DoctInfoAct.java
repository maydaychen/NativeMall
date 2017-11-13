package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.nativeMall.Bean.CodMsIteDocBean;
import com.example.nativeMall.Bean.MapBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class DoctInfoAct extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.iv_doct)
    SmartImageView ivDoct;
    @BindView(R.id.tv_doct_name)
    TextView tvDoctName;
    @BindView(R.id.tv_hos_name)
    TextView tvHosName;
    @BindView(R.id.tv_hos_level)
    TextView tvHosLevel;
    @BindView(R.id.tv_depmt)
    TextView tvDepmt;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_patient_num)
    TextView tvPatientNum;
    @BindView(R.id.tv_doct_field)
    TextView tvDoctField;
    @BindView(R.id.tv_doct_desc)
    TextView tvDoctDesc;
    private String DoctId;
    private String DoctName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doct_info);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        MapBean DoctMsg = intent.getParcelableExtra("DoctMsg");
        DoctId = DoctMsg.getMap().get("doctorId").toString();
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });

        getDoctInfoByAsync();

    }

    private void getDoctInfoByAsync() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "doctorInfo/getDoctorByid?id=" + DoctId;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodMsIteDocBean codMsIteDocBean = gson.fromJson(result, CodMsIteDocBean.class);
                if (codMsIteDocBean.getMSG().equals("success")){
                    SetDoctInfoByAsyncData(codMsIteDocBean);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void SetDoctInfoByAsyncData(CodMsIteDocBean codMsIteDocBean) {

//        ivDoct.setImageUrl(Config.PIC_URL + codMsIteDocBean.getDoctor().get("doctorUrl"));
        if (!TextUtils.isEmpty(codMsIteDocBean.getDoctor().get("doctorUrl").toString())){
            String URL = Config.PIC_URL + codMsIteDocBean.getDoctor().get("doctorUrl").toString();
            Util.GetCircleImg(URL,ivDoct);
        }
        tvDoctName.setText(codMsIteDocBean.getDoctor().get("doctorName").toString());
        DoctName = codMsIteDocBean.getDoctor().get("doctorName").toString();
        tvHosName.setText(codMsIteDocBean.getDoctor().get("hospitalName").toString());
        //tvHosLevel.setText(codMsIteDocBean.getDoctor().get("hospitalLevel").toString());
        tvDepmt.setText(codMsIteDocBean.getDoctor().get("firstDeptName").toString());
        tvLevel.setText(codMsIteDocBean.getDoctor().get("doctorLevel").toString());
        tvDoctField.setText(codMsIteDocBean.getDoctor().get("doctorField").toString());
        tvDoctDesc.setText(codMsIteDocBean.getDoctor().get("doctorDesc").toString());
        tvPatientNum.setText(((int) (Double.parseDouble(codMsIteDocBean.getDoctor().get
                ("orderCount").toString())) + ""));
    }

    @OnClick(R.id.rl_online_inquiry)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_online_inquiry:
                Intent intent = new Intent(DoctInfoAct.this, OnlineAskDoctAct.class);
                intent.putExtra("DoctName",DoctName);
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                startActivity(intent);
                break;


        }
    }
}
