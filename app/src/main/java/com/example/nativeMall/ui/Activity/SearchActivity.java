package com.example.nativeMall.ui.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Fragment.MedicineKindFragment;
import com.example.nativeMall.ui.Fragment.MedicineShopFragment;
import com.example.nativeMall.ui.Fragment.SearchMedFragment;
import com.example.nativeMall.ui.Fragment.SearchShopFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.searchBack)
    ImageView searchBack;
    @BindView(R.id.searchButton)
    TextView searchButton;
    @BindView(R.id.searchKind)
    TextView searchKind;
    @BindView(R.id.frame)
    FrameLayout frame;

    //标识为，判断药品搜索或者药店搜索
    //0为药品，1为药店
    private int FLAG = 0;
    public static EditText searchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        setDefaultFragment();

        searchName = (EditText) this.findViewById(R.id.searchName);
        searchButton.setOnClickListener(view -> search());

        searchName.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // do some your things
                search();
            }
            return false;
        });

        searchKind.setOnClickListener(this::showPopupWindow);
        searchBack.setOnClickListener(view -> finish());
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("name", searchName.getText().toString());
        fragment.setArguments(bundle);
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.layout_popup, null);
        // 设置按钮的点击事件

        TextView medicine = (TextView) contentView.findViewById(R.id.medicine);
        TextView medicine_shop = (TextView) contentView.findViewById(R.id.medicineshop);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                Util.dip2px(80, SearchActivity.this), Util.dip2px(70, SearchActivity.this), true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor((v, event) -> {
            Log.i("mengdd", "onTouch : ");
            return false;
            // 这里如果返回true的话，touch事件将被拦截
            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.v2_0_searchbg));
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(view, -16, 30);
        // 设置好参数之后再show
//        popupWindow.showAsDropDown(view);

        medicine.setOnClickListener(view1 -> {
            searchKind.setText("药品");
            FLAG = 0;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, new MedicineKindFragment()).commit();
            popupWindow.dismiss();
        });

        medicine_shop.setOnClickListener(view12 -> {
            searchKind.setText("药店");
            FLAG = 1;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, new MedicineShopFragment()).commit();
            popupWindow.dismiss();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
//                super.onSaveInstanceState(outState);
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MedicineKindFragment medicineKindFragment = new MedicineKindFragment();
        transaction.replace(R.id.frame, medicineKindFragment);
        transaction.commit();
    }

    private void search() {
        if (searchName.getText().toString().equals("")) {
            Toast.makeText(SearchActivity.this, "您没有搜索任何商品，请输入商品名！", Toast.LENGTH_SHORT).show();
        } else {
            if (FLAG == 0) {
                SearchMedFragment searchMedFragment = new SearchMedFragment();
                Config.shistory.add(searchName.getText().toString());
                changeFragment(searchMedFragment);
            } else {
                SearchShopFragment searchShopFragment = new SearchShopFragment();
                changeFragment(searchShopFragment);
                Config.supplier_history.add(searchName.getText().toString());
            }
        }
    }

}
