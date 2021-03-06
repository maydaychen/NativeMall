package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.ConfirmPicAdapter;
import com.example.nativeMall.Bean.AddressBean;
import com.example.nativeMall.Bean.ConfirmBean;
import com.example.nativeMall.Bean.CouponsBean;
import com.example.nativeMall.Bean.PreOrderBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmActivity extends InitActivity {
    @BindView(R.id.tv_confirm_send_kind)
    TextView mTvConfirmSendKind;
    @BindView(R.id.tv_order_yunfei)
    TextView mTvOrderYunfei;
    @BindView(R.id.tv_order_zhekou)
    TextView mTvOrderZhekou;
    @BindView(R.id.et_confirm_remark)
    EditText mEtConfirmRemark;
    @BindView(R.id.tv_confirm_youhuiquan)
    TextView mTvConfirmYouhuiquan;
    private Gson gson = new Gson();
    private SubscriberOnNextListener<JSONObject> confirmOnNext;
    private SharedPreferences preferences;
    private static final int SHOW_ADDRESS = 1;
    private static final int SHOW_COUPON = 2;
    private PreOrderBean mBuyNowBean;
    private String addressId;
    private String dispatchid;

    @BindView(R.id.tv_confirm_sname)
    TextView mTvConfirmSname;
    @BindView(R.id.tv_add_address)
    TextView mTvAddAddress;
    @BindView(R.id.rl_confirm_address)
    RelativeLayout mRlConfirmAddress;
    @BindView(R.id.bt_confirm_commit)
    Button btConfirmCommit;
    @BindView(R.id.tv_confirm_name)
    TextView mTvConfirmName;
    @BindView(R.id.tv_confirm_telephone)
    TextView mTvConfirmTelephone;
    @BindView(R.id.tv_confirm_address)
    TextView mTvConfirmAddress;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;

    @BindView(R.id.tv_confirm_price)
    TextView mTvConfirmPrice;
    @BindView(R.id.rv_confirm_showimg)
    RecyclerView mRvConfirmShowimg;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        Log.i("chenyi", "initData: " + getIntent().getStringExtra("objs"));
        mBuyNowBean = gson.fromJson(getIntent().getStringExtra("objs"), PreOrderBean.class);
        confirmOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                ConfirmBean confirmBean = gson.fromJson(jsonObject.toString(), ConfirmBean.class);
                Intent intent = new Intent(ConfirmActivity.this, PayActivity.class);
                intent.putExtra("price", mBuyNowBean.getResult().getMemberDiscount().getRealprice() + "");
                intent.putExtra("order_num", confirmBean.getResult().getOrdersn());
                startActivity(intent);
            } else {
                Toast.makeText(ConfirmActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void initData() {
        RxView.clicks(btConfirmCommit)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    String goods = "";
                    for (PreOrderBean.ResultBean.OrderGoodsBean orderGoodsBean : mBuyNowBean.getResult().getOrderGoods()) {
                        goods = orderGoodsBean.getGoodsid() + "," + orderGoodsBean.getOptionid() + "," + orderGoodsBean.getTotal();
                    }
                    confirm_order(goods);
                });

        if (null != mBuyNowBean.getResult().getDefaultAddress()) {
            mRlConfirmAddress.setVisibility(View.VISIBLE);
            mTvAddAddress.setVisibility(View.GONE);
            addressId = mBuyNowBean.getResult().getDispatches().get(0).getId();
            dispatchid = mBuyNowBean.getResult().getDispatches().get(0).getId();
            mTvConfirmName.setText(mBuyNowBean.getResult().getDefaultAddress().getRealname());
            mTvConfirmTelephone.setText(mBuyNowBean.getResult().getDefaultAddress().getMobile());
            mTvConfirmAddress.setText(mBuyNowBean.getResult().getDefaultAddress().getProvince() + mBuyNowBean.getResult().getDefaultAddress().getCity() +
                    mBuyNowBean.getResult().getDefaultAddress().getArea() + mBuyNowBean.getResult().getDefaultAddress().getAddress());
        } else {
            mTvAddAddress.setVisibility(View.VISIBLE);
            mRlConfirmAddress.setVisibility(View.GONE);
        }
        addressId = mBuyNowBean.getResult().getAddressLists().get(0).getId();
        dispatchid = mBuyNowBean.getResult().getDispatches().get(0).getId();
        mTvConfirmSname.setText(mBuyNowBean.getResult().getShopSet().getName());
        mTvOrderPrice.setText(String.format(getResources().getString(R.string.mall_daily_nowprice), mBuyNowBean.getResult().getMemberDiscount().getRealprice() + ""));
        mTvOrderYunfei.setText(String.format(getResources().getString(R.string.mall_daily_nowprice_plus), mBuyNowBean.getResult().getDispatches().get(0).getPrice() + ""));
        mTvOrderZhekou.setText(String.format(getResources().getString(R.string.mall_daily_nowprice_minus), mBuyNowBean.getResult().getMemberDiscount().getDiscountprice() + ""));
        mTvConfirmPrice.setText(String.format(getResources().getString(R.string.mall_daily_nowprice), mBuyNowBean.getResult().getMemberDiscount().getRealprice() + ""));
        mTvConfirmSendKind.setText(mBuyNowBean.getResult().getDispatches().get(0).getDispatchname());

        mRvConfirmShowimg.setLayoutManager(new LinearLayoutManager(getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        ConfirmPicAdapter confirmPicAdapter = new ConfirmPicAdapter(mBuyNowBean.getResult().getOrderGoods(), getApplicationContext());
        mRvConfirmShowimg.setAdapter(confirmPicAdapter);

        mTvAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmActivity.this, MyAddressActivity.class);
            intent.putExtra("ISCONFIRM", true);
            startActivityForResult(intent, SHOW_ADDRESS);
        });
    }


    @OnClick({R.id.iv_comfirm_back, R.id.rl_confirm_address, R.id.rl_confirm_youhuiquan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.rl_confirm_address:
                Intent intent = new Intent(ConfirmActivity.this, MyAddressActivity.class);
                intent.putExtra("ISCONFIRM", true);
                startActivityForResult(intent, SHOW_ADDRESS);
                break;
            case R.id.rl_confirm_youhuiquan:
                String goods = "";
                for (int i = 0; i < mBuyNowBean.getResult().getOrderGoods().size(); i++) {
                    if (i == mBuyNowBean.getResult().getOrderGoods().size() - 1) {
                        goods = goods + mBuyNowBean.getResult().getOrderGoods().get(i).getGoodsid() + ","
                                + mBuyNowBean.getResult().getOrderGoods().get(i).getGoodsid() + ","
                                + mBuyNowBean.getResult().getOrderGoods().get(i).getTotal();
                    } else {
                        goods = goods + mBuyNowBean.getResult().getOrderGoods().get(i).getGoodsid() + ","
                                + mBuyNowBean.getResult().getOrderGoods().get(i).getGoodsid() + ","
                                + mBuyNowBean.getResult().getOrderGoods().get(i).getTotal() + "%7C";
                    }
                }
                Intent youhui = new Intent(ConfirmActivity.this, UseCouponActivity.class);
                youhui.putExtra("goods", goods);
                startActivityForResult(youhui, SHOW_COUPON);
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case SHOW_ADDRESS:
                    mRlConfirmAddress.setVisibility(View.VISIBLE);
                    mTvAddAddress.setVisibility(View.GONE);
                    AddressBean.ResultBean.ListBean addressBean = (AddressBean.ResultBean.ListBean) data.getSerializableExtra("address");
                    addressId = addressBean.getId();
                    mTvConfirmName.setText(addressBean.getRealname());
                    mTvConfirmTelephone.setText(addressBean.getMobile());
                    mTvConfirmAddress.setText(addressBean.getProvince() + addressBean.getCity() +
                            addressBean.getArea() + addressBean.getAddress());
                    break;
                case SHOW_COUPON:
                    CouponsBean.ResultBean resultBean = (CouponsBean.ResultBean) data.getSerializableExtra("objs");
                    mTvConfirmYouhuiquan.setText("- ￥" + resultBean.getBackmoney());
                    break;
                default:
                    break;
            }
        }
    }


    private void confirm_order(String goods) {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
//        sign = sign + "cartids=" + pid + "&";
        sign = sign + "addressid=" + addressId + "&";
        sign = sign + "dispatchid=" + dispatchid + "&";
        sign = sign + "goods=" + goods + "&";
        if (!"".equals(mEtConfirmRemark.getText().toString())) {
            sign = sign + "remark=" + mEtConfirmRemark.getText().toString() + "&";
        }
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        Log.i("chenyi", "confirm_order: " + sign);
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().confirm_order(
                new ProgressSubscriber(confirmOnNext, ConfirmActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
                goods, addressId, dispatchid, mEtConfirmRemark.getText().toString(), sign, time);
    }
}
