package com.example.nativeMall.ui.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.CodeMsgItemBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.NewPatientPopWindow;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class DateMsgAct extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_first_second)
    TextView tvFirstSecond;
    @BindView(R.id.tv_pay_method)
    TextView tvPayMethod;
//    @BindView(R.id.tv_disease_info)
//    TextView tvDiseaseInfo;
    @BindView(R.id.iv_choose_doc_back)
    ImageView ivChooseDocBack;
    @BindView(R.id.et_card_number)
    EditText etCardNumber;
    @BindView(R.id.et_disease)
    EditText etDisease;
    private SmartImageView ivDoct;
    private TextView tvDoctName, tvDepmt, tvDoctLevel, tvHosName, tvDepmt1, tvDateTime, tvPrice,
            tvChoseP, tvSubmit, tvDateType;
    private String doctorUrl, doctorName, firstDeptName, doctorLevel, hospitalName, workTime,
            visitType, price,
            headerId;
    private String payMethod = "1";//默认支付方式为医院支付；0为在线支付
    private String hasVisit = "0"; //默认初复诊为初诊；1为复诊
    private RelativeLayout rlChoosePatient, rlFirstTwice, rlSickMsg, rlPayMethod;
    private static final String TAG = "DateMsgAct";
    private NewPatientPopWindow newPatientPopWindow;
    private static com.example.nativeMall.Bean.PatientBean PatientBean; // 确定挂号的病人
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_message);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        getIntentMsg();


    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        InitView();
        InitListener();
        super.onResume();
    }

    private void getIntentMsg() {
        Intent intent = getIntent();
        doctorUrl = intent.getStringExtra("doctorUrl");
        doctorName = intent.getStringExtra("doctorName");
        firstDeptName = intent.getStringExtra("firstDeptName");
        doctorLevel = intent.getStringExtra("doctorLevel");
        hospitalName = intent.getStringExtra("hospitalName");
        workTime = intent.getStringExtra("workTime");
        visitType = intent.getStringExtra("visitType");
        price = intent.getStringExtra("workPrice");
        headerId = intent.getStringExtra("headerId");
    }

    private void InitView() {

        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        rlChoosePatient = (RelativeLayout) findViewById(R.id.rl_choose_patient);
        rlFirstTwice = (RelativeLayout) findViewById(R.id.rl_first_or_twice);
        rlSickMsg = (RelativeLayout) findViewById(R.id.rl_choose_sick_msg);
        rlPayMethod = (RelativeLayout) findViewById(R.id.rl_pay_method);
        ivDoct = (SmartImageView) findViewById(R.id.iv_doct_img);
        tvDoctName = (TextView) findViewById(R.id.tv_doct_name);
        tvDepmt = (TextView) findViewById(R.id.tv_depmt);
        tvDoctLevel = (TextView) findViewById(R.id.tv_doct_level);
        tvHosName = (TextView) findViewById(R.id.tv_real_hos_name);
        tvDepmt1 = (TextView) findViewById(R.id.tv_depmt_name1);
        tvDateTime = (TextView) findViewById(R.id.tv_date_time);
        tvDateType = (TextView) findViewById(R.id.tv_date_type);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvChoseP = (TextView) findViewById(R.id.tv_chose_patient);


        ivDoct.setImageUrl(Config.PIC_URL + doctorUrl);
        tvDoctName.setText(doctorName);
        tvDepmt.setText(firstDeptName);
        tvDoctLevel.setText(doctorLevel);
        tvHosName.setText(hospitalName);
        tvDepmt1.setText(firstDeptName);
        tvDateTime.setText(workTime);
        tvDateType.setText(visitType);
        tvPrice.setText(price);

        if (Config.IS_LOG && Config.patientBeanList.size() > 0) {
            tvChoseP.setText(Config.patientBeanList.get(0).getPatientName());
            PatientBean = Config.patientBeanList.get(0);
        }

    }


    private void InitListener() {
        rlChoosePatient.setOnClickListener(this);
        rlFirstTwice.setOnClickListener(this);
        rlSickMsg.setOnClickListener(this);
        rlPayMethod.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        ivChooseDocBack.setOnClickListener(this);
    }

    private void getSubmitByAsyncClient() {

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "orderInfo/addOrder?headId=" + headerId +
                "&patientInfoId=" + PatientBean.getId() + "&userId=" + Config.userBean
                .getData().getUid() + "&diseaseInfo=" + etDisease.getText().toString().toString
                () + "&payMethod=" + payMethod + "&hasVisit=" + hasVisit + "&medicalRecordNo=" +
                etCardNumber.getText().toString();
        Log.i(TAG, "getSubmitByAsyncClient: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgItemBean codeMsgItemBean = gson.fromJson(result, CodeMsgItemBean.class);

                if (codeMsgItemBean.getMSG().equals("预约成功")) {

                    String orderId = codeMsgItemBean.getITEM().get("id").toString();
                    String appid = codeMsgItemBean.getITEM().get("appid").toString();
                    String submerno = codeMsgItemBean.getITEM().get("submerno").toString().trim();
                    Log.i(TAG, "onSuccess:??? " + "appid=" + appid + "sub=" + submerno);
                    if (payMethod.equals("1")) {
                        //医院支付
                        Intent intent2 = new Intent(DateMsgAct.this, DateSuccessAct.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(DateMsgAct.this, "已提交成功", Toast.LENGTH_SHORT).show();
                    } else if (payMethod.equals("0")) {
                        //在线支付
                        progressDialog.dismiss();
                        Intent intent = new Intent(DateMsgAct.this, PayActivity.class);
                        intent.putExtra("sname", hospitalName + "(" + firstDeptName + ")");
                        intent.putExtra("price", price);
                        intent.putExtra("orderid", orderId);
                        intent.putExtra("appid", appid);
                        intent.putExtra("submerno", submerno);
                        intent.putExtra("fromFlag", "0");  // 商城：1 ，寻医问药：0；
                        Log.i(TAG, "onSuccess: " + "appid=" + appid + "sub=" + submerno);
                        startActivity(intent);

                        finish();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(DateMsgAct.this, codeMsgItemBean.getMSG().toString(), Toast
                            .LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(DateMsgAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SendShortMsgAndJump(String PhoneNum) {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "mesgInfo/shortmsg";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", PhoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(DateMsgAct.this, URL, entity, "application/json", new JsonHttpResponseHandler
                () {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                progressDialog.dismiss();
                JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                        .getAsJsonObject();
                //如果发送成功
                if (!TextUtils.isEmpty(jsonObject1.get("codeid").getAsString())) {
                    Intent intent = new Intent(DateMsgAct.this, CheckPhoneAct.class);
                    intent.putExtra("codeid", jsonObject1.get("codeid").getAsString());
                    intent.putExtra("headerId", headerId);
                    intent.putExtra("PatientBean_id", PatientBean.getId());
                    intent.putExtra("diseaseInfo", etDisease.getText().toString());
                    intent.putExtra("payMethod", payMethod);
                    intent.putExtra("hasVisit", hasVisit);
                    intent.putExtra("medicalRecordNo", etCardNumber.getText().toString());
                    intent.putExtra("patitentPhone", PatientBean.getPatientPhoneno().trim());
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_choose_patient:
                if (Config.IS_LOG) {
                    newPatientPopWindow = new NewPatientPopWindow(DateMsgAct.this);
                    newPatientPopWindow.showAtLocation(DateMsgAct.this.findViewById(R.id
                            .rl_mine_title), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    newPatientPopWindow.setNewAndCancelClickListener(new NewPatientPopWindow
                            .NewAndCancelClickListener() {
                        @Override
                        public void newClick(Boolean click) {
                            Intent intent = new Intent(DateMsgAct.this, AddPatientAct.class);
                            startActivity(intent);
                            newPatientPopWindow.dismiss();
                        }

                        @Override
                        public void cancleClick(Boolean click) {
                            newPatientPopWindow.dismiss();
                        }

                        @Override
                        public void listClick(com.example.nativeMall.Bean.PatientBean patientBean) {
                            tvChoseP.setText(patientBean.getPatientName());
                            PatientBean = patientBean;
                            Log.i(TAG, "listClick: " + PatientBean.getId());
                            newPatientPopWindow.dismiss();
                        }
                    });
                } else {
                    Util.show(DateMsgAct.this);
                }
                break;
//            case R.id.rl_choose_sick_msg:
//                Intent intent1 = new Intent(DateMsgAct.this, ConfirmSickMsgAct.class);
//                startActivityForResult(intent1, 100);
//                break;

            case R.id.rl_first_or_twice:
                showFirstPopWindow();
                break;
            case R.id.rl_pay_method:
                showPayMethodPopWindow();
                break;
            case R.id.tv_submit:
                if (tvChoseP.getText().equals("请选择")  | TextUtils.isEmpty(etCardNumber.getText().toString()) | TextUtils.isEmpty(etDisease.getText().toString())) {
                    Toast.makeText(DateMsgAct.this, "请补全预约信息", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = ProgressDialog.show(DateMsgAct.this, "提交中", "正在提交,请稍等！");
                    if (payMethod.equals("1")) {
                        SendShortMsgAndJump(PatientBean.getPatientPhoneno().trim());

                    } else {
                        getSubmitByAsyncClient();
                    }

                }
                break;
            case R.id.iv_choose_doc_back:
                finish();
                break;
        }
    }

    private void showPayMethodPopWindow() {

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.simple_pop_window, null);

        TextView tvFirst = (TextView) contentView.findViewById(R.id.tv_first);
        TextView tvSecond = (TextView) contentView.findViewById(R.id.tv_second);

        tvFirst.setText("医院支付");
        tvSecond.setText("在线支付");

        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setTouchable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(DateMsgAct.this.findViewById(R.id.rl_mine_title), Gravity
                .BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        tvFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPayMethod.setText("医院支付");
                payMethod = "1";
                popupWindow.dismiss();
            }
        });
        tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPayMethod.setText("在线支付");
                payMethod = "0";
                popupWindow.dismiss();
            }
        });
    }

    private void showFirstPopWindow() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.simple_pop_window, null);

        TextView tvFirst = (TextView) contentView.findViewById(R.id.tv_first);
        TextView tvSecond = (TextView) contentView.findViewById(R.id.tv_second);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setTouchable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(DateMsgAct.this.findViewById(R.id.rl_mine_title), Gravity
                .BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        tvFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFirstSecond.setText("初诊");
                hasVisit = "1";
                popupWindow.dismiss();
            }
        });
        tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFirstSecond.setText("复诊");
                hasVisit = "0";
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100:  //获取点击的疾病信息内容并显示
                if (resultCode == RESULT_OK) {
                    //tvDiseaseInfo.setText(data.getStringExtra("deptName"));
                }
                break;

        }
    }
}
