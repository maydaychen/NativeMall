package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Fragment.AllOrderFragment;
import com.example.nativeMall.ui.Fragment.DaifahuoFragment;
import com.example.nativeMall.ui.Fragment.DaifukuanOrderFragment;
import com.example.nativeMall.ui.Fragment.DaipingjiaFragment;
import com.example.nativeMall.ui.Fragment.DaishouhuoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends AppCompatActivity {
    @BindView(R.id.framtabhost)
    FragmentTabHost framtabhost;

    private String txt_Array[] = {
            "全部", "待付款", "待发货", "待收货", "待评价"
    };

    private Class fragmentArray[] = {
            AllOrderFragment.class, DaifukuanOrderFragment.class,
            DaifahuoFragment.class, DaishouhuoFragment.class, DaipingjiaFragment.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        int count = fragmentArray.length;
        framtabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        TabWidget tabWidget = framtabhost.getTabWidget();

        for (int i = 0; i < count; i++) {
            framtabhost.addTab(framtabhost.newTabSpec(txt_Array[i]).setIndicator(getIndicatorView(txt_Array[i], R.layout.alwayscontact_indicator)), fragmentArray[i], null);
            // 将Tab按钮添加进Tab选项卡中

            View view = tabWidget.getChildTabViewAt(i);
            view.getLayoutParams().height = Util.dip2px(40, MyOrderActivity.this);
        }
        framtabhost.setCurrentTab(0);
        framtabhost.getTabWidget().setShowDividers(0);
    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onClick() {
        Intent intent = new Intent(MyOrderActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MyOrderActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return false;
    }

    public View getIndicatorView(String name, int layoutId) {

        View v = getLayoutInflater().inflate(layoutId, null);

        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(name);
        return v;
    }
}
