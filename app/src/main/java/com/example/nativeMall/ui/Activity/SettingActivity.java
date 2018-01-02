package com.example.nativeMall.ui.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.MemberBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressErrorSubscriber;
import com.example.nativeMall.http.SubscriberOnNextAndErrorListener;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SettingActivity extends InitActivity implements OnWheelChangedListener, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.iv_person_logo)
    SmartImageView mIvPersonLogo;
    @BindView(R.id.et_person_nicheng)
    EditText mEtPersonNicheng;
    @BindView(R.id.et_person_mobile)
    EditText mEtPersonMobile;
    @BindView(R.id.et_person_wechat)
    EditText mEtPersonWechat;
    @BindView(R.id.et_person_ali_user)
    EditText mEtPersonAliUser;
    @BindView(R.id.et_person_ali_name)
    EditText mEtPersonAliName;
    @BindView(R.id.et_person_real_name)
    EditText mEtPersonRealName;
    @BindView(R.id.et_person_city)
    TextView mEtPersonCity;
    @BindView(R.id.et_person_birthday)
    TextView mEtPersonBirthday;
    private MemberBean.ResultBean mDataBean;
    private SubscriberOnNextAndErrorListener<JSONObject> saveOnNext;
    private SharedPreferences preferences;
    private static final int SHOW_SUBACTIVITY = 1;
    private SubscriberOnNextAndErrorListener<JSONObject> uploadOnNext;
    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private JSONObject mJsonObj;
    private WheelView mProvince;
    private WheelView mCity;
    private WheelView mArea;
    private Button mButton;
    private File picFile;
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    final public static int REQUEST_WRITE = 222;
    private Bitmap bmp;
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
    private View inflate;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private Boolean IS_UPLOAD = false;
    private ProgressDialog updateDialog = null;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mDataBean = (MemberBean.ResultBean) getIntent().getSerializableExtra("member");
        mIvPersonLogo.setImageUrl(mDataBean.getAvatar());
        mEtPersonNicheng.setText(mDataBean.getNickname());
        mEtPersonMobile.setText(mDataBean.getMobile());
        mEtPersonWechat.setText(mDataBean.getWeixin());
        mEtPersonRealName.setText(mDataBean.getRealname());
        uploadOnNext = new SubscriberOnNextAndErrorListener<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) {
                if (updateDialog != null) {
                    updateDialog.dismiss();
                    updateDialog = null;
                }
                String msg = jsonObject.toString();
                try {
                    if (jsonObject.getInt("statusCode") == 1) {
                        Toast.makeText(SettingActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                        msg = jsonObject.getString("result");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                deletePic();
            }

            @Override
            public void onError(Throwable e) {
                if (updateDialog == null) {
                    updateDialog.dismiss();
                }
                Toast.makeText(SettingActivity.this, "上传失败，请稍后再试", Toast.LENGTH_SHORT).show();
                deletePic();
            }
        };
        saveOnNext = new SubscriberOnNextAndErrorListener<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) throws JSONException {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    private void saveInfo() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "fields=id,thumb,title,productprice,marketprice&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
//        HttpJsonMethod.getInstance().history_list(
//                new ProgressErrorSubscriber<>(saveOnNext, SettingActivity.this),
//                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
//                page, sign, time);
    }

    @OnClick({R.id.iv_choose_doc_back, R.id.rl_person_logo, R.id.button, R.id.et_person_city, R.id.et_person_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.rl_person_logo:
                showType();
                break;
            case R.id.button:
                break;
            case R.id.et_person_city:
                showPopupWindow();
                break;
            case R.id.et_person_birthday:
                Intent intent1 = new Intent(SettingActivity.this, CalendarActivity.class);
                startActivityForResult(intent1, SHOW_SUBACTIVITY);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            IS_UPLOAD = true;
            switch (requestCode) {
                case 1:
                    mEtPersonBirthday.setText(data.getStringExtra("date"));
                    break;
                case 100:
//         从图库裁减返回
                    Log.d("wjj", "100");
                    if (data != null) {
                        Uri uri = data.getData();
                        ContentResolver cr = this.getContentResolver();
                        try {
                            bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                            Matrix matrix = new Matrix();
                            matrix.setScale(0.5f, 0.5f);
                            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                                    bmp.getHeight(), matrix, true);
                            upDataHeadImg();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 101:
                    // 从拍照返回
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        bmp = (Bitmap) bundle.get("data");
                        Matrix matrix = new Matrix();
                        matrix.setScale(0.5f, 0.5f);
                        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                                bmp.getHeight(), matrix, true);
                        upDataHeadImg();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_choose_city, null);
        mProvince = contentView.findViewById(R.id.id_province);
        mCity = contentView.findViewById(R.id.id_city);
        mArea = contentView.findViewById(R.id.id_area);
        mButton = contentView.findViewById(R.id.bt_person_city);
        initJsonData();
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
        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                true);
        mButton.setOnClickListener(v -> {
            mEtPersonCity.setText(mCurrentProviceName + mCurrentCityName + mCurrentAreaName);
            popupWindow.dismiss();
        });
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });

        popupWindow.showAtLocation(SettingActivity.this.findViewById(R.id.rl_mine_title),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

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
                JSONObject jsonP = jsonArray.getJSONObject(i);
                String province = jsonP.getString("name");
                String provinceId = jsonP.getString("regionid");

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

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void showType() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Dialog dialog = new Dialog(this, R.style.BottomDialog);
            inflate = LayoutInflater.from(this).inflate(R.layout.pop_avatar, null);
            Button camera = inflate.findViewById(R.id.camera);
            Button img_lib = inflate.findViewById(R.id.img_lib);
            Button cancel = inflate.findViewById(R.id.btn_cancel);
            camera.setOnClickListener(v -> {
                dialog.dismiss();
                camera();
            });
            img_lib.setOnClickListener(v -> {
                selectImage();
                dialog.dismiss();
            });
            cancel.setOnClickListener(v -> dialog.dismiss());
            dialog.setContentView(inflate);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 20;
            lp.width = -1;
            dialogWindow.setAttributes(lp);
            dialog.show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.permition),
                    RC_LOCATION_CONTACTS_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d("chenyi", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("chenyi", "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public void camera() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                //上面已经写好的拍照方法
                write(true);
            }
        } else {
            //上面已经写好的拍照方法
            write(true);
        }
    }

    public void write(boolean iscamera) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
                return;
            } else {
                if (iscamera) {
                    //上面已经写好的拍照方法
                    takePhoto();
                } else {
                    selectImage();
                }
            }
        } else {
            if (iscamera) {
                //上面已经写好的拍照方法
                takePhoto();
            } else {
                selectImage();
            }
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        createPicFile();
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 101);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从相册选择
     */
    private void selectImage() {
        createPicFile();
        Intent intent;
        if (Build.VERSION.SDK_INT >= 23) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
        }
        if (isIntentAvailable(SettingActivity.this, intent)) {
            startActivityForResult(Intent.createChooser(intent, "选择图片"), 100);
        } else {
            Toast.makeText(SettingActivity.this, "请安装相关图片查看应用。", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建上传图片文件
     */
    private void createPicFile() {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Toast.makeText(SettingActivity.this, "请检查SD卡是否可用", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory().toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        picFile = new File(file
                + "/seawaterHeadImg.jpg");
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

    public void deletePic() {
        if (picFile.exists()) {
            picFile.delete();
            if (bmp != null)
                bmp.recycle();
        }
    }

    /**
     * 上传用户头像
     */
    private void upDataHeadImg() {
        IS_UPLOAD = false;
        if (updateDialog == null) {
            updateDialog = ProgressDialog.show(SettingActivity.this, "上传头像", "头像正在上传中，请稍等...", true, false);
        }
        Log.i("chenyi", "head");
        int time = (int) (System.currentTimeMillis() / 1000);
        String sign = "";
//        sign = sign + "avatar=" + fileToBase64(bmp) + "&";
        sign = sign + "image=" + Utils.bitmaptoString(bmp) + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);

        HttpJsonMethod.getInstance().getAva(
                new ProgressErrorSubscriber<>(uploadOnNext, SettingActivity.this), preferences.getString("access_token", ""), Utils.bitmaptoString(bmp),
                preferences.getString("sessionkey", ""), sign, time);
    }
}
