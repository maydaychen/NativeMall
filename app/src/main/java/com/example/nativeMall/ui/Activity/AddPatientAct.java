package com.example.nativeMall.ui.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.nativeMall.Bean.CodeMsgSItemsBean;
import com.example.nativeMall.Bean.PatientBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class AddPatientAct extends AppCompatActivity implements View.OnFocusChangeListener {


    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.woman)
    RadioButton woman;
    @BindView(R.id.et_patient_name)
    EditText etPatientName;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.cb_default)
    CheckBox cbDefault;
    private LocationClient locationClient;
    private String patitentSex = "0";//默认为男
    private String isDefault = "n";//默认为no
    private static final String TAG = "AddPatientAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        InitView();
        InitListener();

    }

    private void InitListener() {
        etPatientName.setOnFocusChangeListener(this);
        etIdNumber.setOnFocusChangeListener(this);
        etAge.setOnFocusChangeListener(this);
        etPhone.setOnFocusChangeListener(this);
        etAddress.setOnFocusChangeListener(this);
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbMan.getId()) {
                    patitentSex = "0";
                    //0表示男
                } else {
                    patitentSex = "1";
                    //1表示女
                }
            }
        });
        cbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isDefault = "y";
                } else {
                    isDefault = "n";
                }
            }
        });
    }

    private void InitView() {
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new Mylistner());
        InitLocation();
        locationClient.start();
    }


    @OnClick({R.id.et_address, R.id.iv_choose_doc_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.tv_submit:
                if (checkInputInfo()){
                    submitPatientInfoByAsyncClient();
                }
                break;
        }
    }


    private void submitPatientInfoByAsyncClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "patientInfo/addPatient?patientAddress=" + etAddress
                .getText().toString() + "&patientId=" + etIdNumber.getText().toString() +
                "&patientName=" + etPatientName.getText().toString() + "&patientPhoneno=" +
                etPhone.getText().toString() + "&patientSex=" + patitentSex + "&patientAge=" +
                etAge.getText().toString() + "&isDefault=" + isDefault + "&userId=" + Config
                .userBean.getData().getUid();
        Log.i(TAG, "submitPatientInfoByAsyncClient: " + URL);

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Config.patientBeanList.clear();

                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgSItemsBean codeMsgSItemsBean = gson.fromJson(result,CodeMsgSItemsBean.class);
                Log.i(TAG, "onSuccess: >>>" + result);
                if (codeMsgSItemsBean.getMSG().equals("success")){
                    Toast.makeText(AddPatientAct.this, "提交成功", Toast.LENGTH_SHORT).show();

                    PatientInfoByAsyncHttpClientGet();

                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(AddPatientAct.this, "提交失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkInputInfo() {
        String str = etPatientName.getText().toString();
        for (int i = 0; i < etPatientName.getText().length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                etPatientName.setError("请输入正确姓名");
                return false;
            }
        }
        if (etIdNumber.getText().length() != 18) {
            etIdNumber.setError("请输入18位身份证");
            return false;
        }
        if (!TextUtils.isEmpty(etAge.getText().toString())) {
            if(Integer.parseInt(etAge.getText().toString()) > 120){
                etAge.setError("请输入正确的年龄");
                return false;
            }
        }else {
            etAge.setError("请输入正确的年龄");
            return false;
        }
        if (TextUtils.isEmpty(etAddress.getText().toString())) {
            etAddress.setError("地址不能为空");
            return false;
        }
        if (etPhone.getText().length() != 11) {
            etPhone.setError("请输入正确的11位手机号");
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_patient_name:
                if (!hasFocus) {
                    String str = etPatientName.getText().toString();
                    for (int i = 0; i < etPatientName.getText().length(); i++) {
                        if (Character.isDigit(str.charAt(i))) {
                            Toast.makeText(AddPatientAct.this, "请输入正确的姓名", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
                break;
            case R.id.et_id_number:
                if (!hasFocus) {
                    if (etIdNumber.getText().length() != 18) {
                        Toast.makeText(AddPatientAct.this, "请输入18位身份证", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.et_age:
                if (!hasFocus) {
                    if (!TextUtils.isEmpty(etAge.getText().toString())) {
                        if(Integer.parseInt(etAge.getText().toString()) > 120){
                            etAge.setError("请输入正确的年龄");
                        }
                    }else {
                        etAge.setError("请输入正确的年龄");
                    }
                }
                break;
            case R.id.et_address:
                if (!hasFocus) {
                    if (TextUtils.isEmpty(etAddress.getText().toString())) {
                        Toast.makeText(AddPatientAct.this, "地址不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.et_phone:
                if (!hasFocus) {
                    if (etPhone.getText().length() != 11) {
                        Toast.makeText(AddPatientAct.this, "请输入正确的11位手机号", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        }
    }


    public class Mylistner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            etAddress.setText(bdLocation.getAddrStr());
            Toast.makeText(AddPatientAct.this, "定位成功：" + bdLocation.getAddrStr(), Toast
                    .LENGTH_LONG).show();
            if (!"".equals(etAddress.getText())) {
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


    private  void PatientInfoByAsyncHttpClientGet() {
        // 创建异步的客户端对象
        AsyncHttpClient client = new AsyncHttpClient();

        // 请求的地址
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        params.put("userId", Config.userBean.getData().getUid());
        String URL = Config.GLOBAL_URL1 + "patientInfo/getAllPatient";

        client.get(URL, params, new AsyncHttpResponseHandler() {

            JSONObject json = null, json2 = null;
            JSONArray jsonArray = null;

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                // 成功处理的方法
                String result = new String(bytes);
                try {
                    json = new JSONObject(result);
                    jsonArray = json.getJSONArray("ITEMS");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        json2 = jsonArray.getJSONObject(j);
                        Config.patientBeanList.add((PatientBean) Util.fromJsonToJava(json2,
                                PatientBean.class));
                    }

                    Gson gson = new Gson();
                    String jsonSting = gson.toJson(Config.patientBeanList);

                    SharedPreferences mySharedPreferences = getSharedPreferences("patientlist",
                            Activity.MODE_PRIVATE);
//实例化SharedPreferences.Editor对象（第二步）
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
//用putString的方法保存数据
                    editor.putString("patientList", jsonSting);
//提交当前数据
                    editor.commit();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(AddPatientAct.this,"网络异常，默认失败",Toast.LENGTH_SHORT).show();
                finish();
                throwable.printStackTrace();

            }

        });
    }
}
