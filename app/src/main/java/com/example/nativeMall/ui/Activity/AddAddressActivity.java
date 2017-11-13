package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Config;
import com.example.nativeMall.Listener.AddAddressListener;
import com.example.nativeMall.Model.NewsModel;
import com.example.nativeMall.Model.NewsModelImpl;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.MallTitle;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;


public class AddAddressActivity extends AppCompatActivity implements OnWheelChangedListener, AddAddressListener {
    @BindView(R.id.et_add_address_name)
    EditText mEtAddAddressName;
    @BindView(R.id.et_add_address_telephone)
    EditText mEtAddAddressTelephone;
    @BindView(R.id.et_add_address_address)
    EditText mEtAddAddressAddress;
    @BindView(R.id.et_add_address_code)
    EditText mEtAddAddressCode;
    @BindView(R.id.cb_add_address_default)
    CheckBox mCbAddAddressDefault;
    @BindView(R.id.wheel_add_address)
    RelativeLayout mWheelAddAddress;
    @BindView(R.id.tv_add_address_address)
    TextView mTvAddAddressAddress;
    @BindView(R.id.title_add_address)
    MallTitle mTitleAddAddress;
    private Gson mGson = new Gson();
    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private JSONObject mJsonObj;
    /**
     * 省的WheelView控件
     */
    private WheelView mProvince;
    /**
     * 市的WheelView控件
     */
    private WheelView mCity;
    /**
     * 区的WheelView控件
     */
    private WheelView mArea;

    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    private String[] mProvinceIds;
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<>();
    private Map<String, String[]> mCitisDatasID = new HashMap<>();
    /**
     * key - 市 values - 区s
     */
    private Map<String, String[]> mAreaDatasMap = new HashMap<>();
    private Map<String, String[]> mAreaDatasId = new HashMap<>();

    /**
     * 当前省的名称
     */
    private String mCurrentProviceName;
    private String mCurrentProviceId;
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;
    private String mCurrentCityId;
    /**
     * 当前区的名称
     */
    private String mCurrentAreaId;
    private String mCurrentAreaName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        initJsonData();

        mProvince = (WheelView) findViewById(R.id.id_province);
        mCity = (WheelView) findViewById(R.id.id_city);
        mArea = (WheelView) findViewById(R.id.id_area);

        initDatas();
        mProvince.setViewAdapter(new ArrayWheelAdapter<>(this, mProvinceDatas));
        // 添加change事件
        mProvince.addChangingListener(this);
        // 添加change事件
        mCity.addChangingListener(this);
        // 添加change事件
        mArea.addChangingListener(this);

        mProvince.setVisibleItems(5);
        mCity.setVisibleItems(5);
        mArea.setVisibleItems(5);
        updateCities();
        updateAreas();
        mTvAddAddressAddress.setText(mCurrentProviceName + mCurrentCityName + mCurrentAreaName);
        mTitleAddAddress.setLeftRightImgClickListener(new MallTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        mCurrentCityId = mCitisDatasID.get(mCurrentProviceName)[pCurrent];
        String[] areas = mAreaDatasMap.get(mCurrentCityName);
        String[] areasId = mAreaDatasId.get(mCurrentCityName);
        mCurrentAreaName = areas[0];
        mCurrentAreaId = areasId[0];
        if (areas == null) {
            areas = new String[]{""};
        }
        mArea.setViewAdapter(new ArrayWheelAdapter<>(this, areas));
        mArea.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        mCurrentProviceId = mProvinceIds[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
//        String[] citieIds = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mCity.setViewAdapter(new ArrayWheelAdapter<>(this, cities));
        mCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            mProvinceDatas = new String[jsonArray.length()];
            mProvinceIds = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("name");// 省名字
                String provinceId = jsonP.getString("regionid");//省ID

                mProvinceDatas[i] = province;
                mProvinceIds[i] = provinceId;

                JSONArray jsonCs = null;
                try {
                    jsonCs = jsonP.getJSONArray("children");
                } catch (Exception e1) {
                    continue;
                }
                String[] mCitiesDatas = new String[jsonCs.length()];
                String[] mCitiesIds = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("name");// 市名字
                    String cityId = jsonCity.getString("regionid");// 市id
                    mCitiesDatas[j] = city;
                    mCitiesIds[j] = cityId;
                    JSONArray jsonAreas;
                    try {
                        jsonAreas = jsonCity.getJSONArray("children");
                    } catch (Exception e) {
                        continue;
                    }

                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
                    String[] mAreasIds = new String[jsonAreas.length()];// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        String area = jsonAreas.getJSONObject(k).getString("name");// 区域的名称
                        String areaId = jsonAreas.getJSONObject(k).getString("regionid");// 区域的id
                        mAreasDatas[k] = area;
                        mAreasIds[k] = areaId;
                    }
                    mAreaDatasMap.put(city, mAreasDatas);
                    mAreaDatasId.put(city, mAreasIds);
                }
                mCitisDatasMap.put(province, mCitiesDatas);
                mCitisDatasID.put(province, mCitiesIds);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "UTF-8"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mProvince) {
            updateCities();
        } else if (wheel == mCity) {
            updateAreas();
        } else if (wheel == mArea) {
            mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
            mCurrentAreaId = mAreaDatasId.get(mCurrentCityName)[newValue];
        }
    }

    public void showChoose(View view) {
        mWheelAddAddress.setVisibility(View.GONE);
        Toast.makeText(this, mCurrentProviceName + mCurrentCityName + mCurrentAreaName,
                Toast.LENGTH_SHORT).show();
        mTvAddAddressAddress.setText(mCurrentProviceName + mCurrentCityName + mCurrentAreaName);
    }

    @OnClick({R.id.bt_add_address_save, R.id.rl_add_address_sanjiliandogn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_address_save:
                doAdd();
                break;
            case R.id.rl_add_address_sanjiliandogn:
                mWheelAddAddress.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(AddAddressActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }

    private void doAdd() {
        if (mEtAddAddressTelephone.getText().length() != 11) {
            Toast.makeText(this, "手机号输入有误！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEtAddAddressAddress.getText().toString().equals("")
                || mEtAddAddressName.getText().toString().equals("")
                || mEtAddAddressCode.getText().toString().equals("")) {
            Toast.makeText(this, "请填写详细信息！", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> param = new HashMap<>();
        param.put("type", "ADD");
        param.put("uid", Config.userBean.getData().getUid());
        param.put("consignee", mEtAddAddressName.getText().toString());
        param.put("mobile", mEtAddAddressTelephone.getText().toString());
        param.put("email", "abc123@qq.com");
        param.put("provinceid", mCurrentProviceId);
        param.put("cityid", mCurrentCityId);
        param.put("regionid", mCurrentAreaId);
        param.put("alias", "wonders");
        param.put("address", mEtAddAddressAddress.getText().toString());
        param.put("zipcode", mEtAddAddressCode.getText().toString());
        if (mCbAddAddressDefault.isChecked()) {
            param.put("isdefault", "11");
        } else {
            param.put("isdefault", "00");
        }
        NewsModel addAddress = new NewsModelImpl();
        addAddress.addAddress(AddAddressActivity.this, this, "address/shopAddressUpdate", mGson.toJson(param));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            System.out.println("down");
            if (AddAddressActivity.this.getCurrentFocus() != null) {
                if (AddAddressActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(AddAddressActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onSuccess(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFaile(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
