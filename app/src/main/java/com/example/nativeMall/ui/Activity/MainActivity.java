package com.example.nativeMall.ui.Activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.example.nativeMall.Bean.ResultBean;
import com.example.nativeMall.Bean.UserBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Fragment.FenleiFragment;
import com.example.nativeMall.ui.Fragment.GouwucheFragment;
import com.example.nativeMall.ui.Fragment.MainFragment;
import com.example.nativeMall.ui.Fragment.PersonFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.nativeMall.Config.fileName;

public class MainActivity extends InitActivity implements EasyPermissions.PermissionCallbacks {

    private TabLayout mTabTl;
    public static ViewPager mContentVp;

    private List<String> tabIndicators;
    private static final int RC_STORAGE_CONTACTS_PERM = 125;
    private List<Fragment> tabFragments;
    public static String szImei;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final String TAG = "MainActivity";

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                    default:
                        break;
                }
            }
        }
        mTabTl.getTabAt(0).getCustomView().setSelected(true);
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        tabIndicators.add("首页");
        tabIndicators.add("分类");
        tabIndicators.add("购物车");
        tabIndicators.add("个人中心");
        tabFragments = new ArrayList<>();
        tabFragments.add(MainFragment.newInstance(tabIndicators.get(0)));
        tabFragments.add(FenleiFragment.newInstance(tabIndicators.get(1)));
        tabFragments.add(GouwucheFragment.newInstance(tabIndicators.get(2)));
        tabFragments.add(PersonFragment.newInstance(tabIndicators.get(3)));
        ContentPagerAdapter contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
        mContentVp.setOffscreenPageLimit(4);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mTabTl = findViewById(R.id.tl_tab);
        mContentVp = findViewById(R.id.vp_content);
        initContent();
        initTab();
    }

    @Override
    public void initData() {
        saveImg();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(ResultBean resultBean) {
        if (resultBean.getCode() == 1) {
            mContentVp.setCurrentItem(1);
        }
    }

    @AfterPermissionGranted(RC_STORAGE_CONTACTS_PERM)
    public void saveImg() {
        String[] perms = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Have permissions, do the thing!
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_logo);
            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_locate),
                    RC_STORAGE_CONTACTS_PERM, perms);
        }
    }
}
