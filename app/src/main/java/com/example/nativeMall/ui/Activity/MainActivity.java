package com.example.nativeMall.ui.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.example.nativeMall.Bean.PatientBean;
import com.example.nativeMall.Bean.UserBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Fragment.HealthCardFragment;
import com.example.nativeMall.ui.Fragment.MainFragment;
import com.example.nativeMall.ui.Fragment.MallFragment;
import com.example.nativeMall.ui.Fragment.PersonFragment;
import com.example.nativeMall.ui.Fragment.RegFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends InitActivity {

    private TabLayout mTabTl;
    public static ViewPager mContentVp;

    private List<String> tabIndicators;

    private List<Fragment> tabFragments;
    public static String szImei;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        testCall();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setAction("FreeAskResponse.service");
        Intent intent1 = new Intent(Util.createExplicitFromImplicitIntent(MainActivity.this, intent));
        stopService(intent1);
        super.onDestroy();
    }

    private void initService() {
        Intent intent = new Intent();
        intent.setAction("FreeAskResponse.service");
        Intent intent1 = new Intent(Util.createExplicitFromImplicitIntent(MainActivity.this, intent));
        startService(intent1);
        Log.i(TAG, "initService: 启动service成功");
    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);
        for (int i = 0; i < tabIndicators.size(); i++) {
            TabLayout.Tab itemTab = mTabTl.getTabAt(i);
            if (itemTab != null) {
                switch (i) {
                    case 0:
                        itemTab.setCustomView(R.layout.item_tab_layout_custom);
                        TextView itemTv = itemTab.getCustomView().findViewById(R.id
                                .tv_menu_item);
                        itemTv.setText(tabIndicators.get(i));
                        break;
                    case 1:
                        itemTab.setCustomView(R.layout.item_tab_layout_jkj);
                        TextView itemTv1 = itemTab.getCustomView().findViewById(R.id
                                .tv_menu_item1);
                        itemTv1.setText(tabIndicators.get(i));
                        break;
                    case 2:
                        itemTab.setCustomView(R.layout.item_tab_layout_jksc);
                        TextView itemTv2 = itemTab.getCustomView().findViewById(R.id
                                .tv_menu_item2);
                        itemTv2.setText(tabIndicators.get(i));
                        break;
                    case 3:
                        itemTab.setCustomView(R.layout.item_tab_layout_xywy);
                        TextView itemTv3 = itemTab.getCustomView().findViewById(R.id
                                .tv_menu_item3);
                        itemTv3.setText(tabIndicators.get(i));
                        break;
                    case 4:
                        itemTab.setCustomView(R.layout.item_tab_layout_wo);
                        TextView itemTv4 = itemTab.getCustomView().findViewById(R.id
                                .tv_menu_item4);
                        itemTv4.setText(tabIndicators.get(i));
                        break;
                }
//                itemTab.setCustomView(R.layout.item_tab_layout_custom);
//                TextView itemTv = (TextView) itemTab.getCustomView().findViewById(R.id
// .tv_menu_item);
//                itemTv.setText(tabIndicators.get(i));
            }
        }
        mTabTl.getTabAt(0).getCustomView().setSelected(true);
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        tabIndicators.add("首页");
        tabIndicators.add("购物车");
        tabIndicators.add("店铺");
        tabIndicators.add("个人中心");
        tabFragments = new ArrayList<>();
        tabFragments.add(MainFragment.newInstance(tabIndicators.get(0)));
        tabFragments.add(HealthCardFragment.newInstance(tabIndicators.get(1)));
        tabFragments.add(MallFragment.newInstance(tabIndicators.get(2)));
        tabFragments.add(PersonFragment.newInstance(tabIndicators.get(3)));
        ContentPagerAdapter contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }

    @Override
    public void initView() {
        mTabTl = findViewById(R.id.tl_tab);
        mContentVp = findViewById(R.id.vp_content);
        initContent();
        initTab();
    }

    @Override
    public void initData() {
        try {
            if (Util.fileExist(this, "data.obj")) {
                FileInputStream fin = openFileInput("data.obj");
                ObjectInputStream input = null;
                input = new ObjectInputStream(fin);
                Config.userBean = (UserBean) input.readObject();
                input.close();
                Config.IS_LOG = true;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        szImei = TelephonyMgr.getDeviceId();

        if (Config.IS_LOG) {
            Gson gson = new Gson();
            SharedPreferences mySharedPreferences = getSharedPreferences("patientlist",
                    Activity.MODE_PRIVATE);
            String patientlist = mySharedPreferences.getString("patientList", null);
            Config.patientBeanList = gson.fromJson(patientlist, new TypeToken<List<PatientBean>>() {
            }.getType());
            Log.i(TAG, "initData: " + Config.userBean.getData().getUid());
        }
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }

    public void testCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, READ_EXTERNAL_STORAGE, READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            } else {
                callPhone();
            }
        } else callPhone();
    }

    public void callPhone() {
        initData();
        initView();
        if (Config.IS_LOG) {
            initService();
        }
    }
}
