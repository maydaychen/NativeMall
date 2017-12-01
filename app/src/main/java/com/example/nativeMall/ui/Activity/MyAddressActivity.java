package com.example.nativeMall.ui.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nativeMall.Adapter.AllAddressAdapter;
import com.example.nativeMall.Bean.AddressBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.example.nativeMall.ui.widget.RecycleViewDivider;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddressActivity extends InitActivity implements AllAddressAdapter.EditInterface {

    @BindView(R.id.rv_item_address)
    RecyclerView mRvItemAddress;
    private Gson gson = new Gson();
    private SubscriberOnNextListener<JSONObject> addressListOnNext;
    private SubscriberOnNextListener<JSONObject> deleteAddressListOnNext;
    private SharedPreferences preferences;
    private AddressBean addressBean;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvItemAddress.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mRvItemAddress.setLayoutManager(layoutManager);
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        addressListOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                Log.i("chenyi", "initView: " + jsonObject.toString());
                addressBean = gson.fromJson(jsonObject.toString(), AddressBean.class);
                mRvItemAddress.setLayoutManager(new LinearLayoutManager(MyAddressActivity.this));
                AllAddressAdapter allAddressAdapter = new AllAddressAdapter(addressBean.getResult().getList());
                mRvItemAddress.setAdapter(allAddressAdapter);
                allAddressAdapter.setCheckInterface(MyAddressActivity.this);
                allAddressAdapter.setOnItemClickListener((view, data) -> {
                    if (getIntent().getBooleanExtra("ISCONFIRM", false)) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("address", addressBean.getResult().getList().get(data));
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            } else {
                Toast.makeText(MyAddressActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        deleteAddressListOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                Toast.makeText(MyAddressActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                initAddress();
            } else {
                Toast.makeText(MyAddressActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        initAddress();
    }

    @OnClick({R.id.iv_choose_doc_back, R.id.rl_my_address_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.rl_my_address_add:
                Intent intent = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initAddress() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().address_list(
                new ProgressSubscriber(addressListOnNext, MyAddressActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), sign, time);
    }

    private void deleteAddress(String addressId) {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "addressid=" + addressId + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().deleteAddress(
                new ProgressSubscriber(deleteAddressListOnNext, MyAddressActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), addressId, sign, time);
    }

    @Override
    public void changeAddress(int positon) {
        Intent intent = new Intent(MyAddressActivity.this, ChangeAddressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("address", addressBean.getResult().getList().get(positon));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void deleteAddress(int positon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyAddressActivity.this);
        builder.setMessage("确定要删除该地址么？");
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton("确认", (dialog, which) -> {
            deleteAddress(addressBean.getResult().getList().get(positon).getId());
            dialog.dismiss();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
