package com.example.nativeMall.ui.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nativeMall.Bean.OrderDetailBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.http.Http;
import com.example.nativeMall.ui.widget.TimeTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends InitActivity {
    @BindView(R.id.tv_order_detail_ordernum)
    TextView mTvOrderDetailOrdernum;
    @BindView(R.id.tv_order_detail_ems)
    TextView mTvOrderDetailEms;
    @BindView(R.id.tv_order_detail_paykind)
    TextView mTvOrderDetailPaykind;
    @BindView(R.id.tv_confirm_name)
    TextView mTvConfirmName;
    @BindView(R.id.tv_confirm_telephone)
    TextView mTvConfirmTelephone;
    @BindView(R.id.tv_confirm_address)
    TextView mTvConfirmAddress;
    @BindView(R.id.tv_confirm_sname)
    TextView mTvConfirmSname;
    @BindView(R.id.tv_confirm_num)
    TextView mTvConfirmNum;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;
    @BindView(R.id.tv_ship_time)
    TextView mTvShipTime;
    @BindView(R.id.rv_confirm_showimg)
    RecyclerView mRvConfirmShowimg;
    @BindView(R.id.electricity_countdown)
    TimeTextView mElectricityCountdown;
    @BindView(R.id.rl_order_detail_time)
    RelativeLayout mRlOrderDetailTime;
    @BindView(R.id.bt_order_detail_bt_right)
    Button mBtOrderDetailBtRight;
    @BindView(R.id.bt_order_detail_bt_left)
    Button mBtOrderDetailBtLeft;

    private Gson mGson = new Gson();
    private OrderDetailBean mOrderDetailBean;

    private int num = 0;
    private int orderstate;


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        Map<String, String> param = new HashMap<>();
        param.put("oid", getIntent().getStringExtra("oid"));
        Http.getInstance().init(OrderDetailActivity.this, handler, mGson.toJson(param), "order/orderDtl", 0).sendMsg();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        if (jsonObject.getString("success").equals("T")) {
                            mOrderDetailBean = mGson.fromJson(data, OrderDetailBean.class);
                        } else {
                            return;
                        }
                        for (OrderDetailBean.DataBean.DetailsBean detailsBean : mOrderDetailBean.getData().getDetails()) {
                            num += Integer.parseInt(detailsBean.getBuycount());
                        }
                        orderstate = Integer.parseInt(mOrderDetailBean.getData().getOrderstate());
                        JSONObject dataJson = jsonObject.getJSONObject("data");
                        switch (orderstate) {
                            case 30:
                                mRlOrderDetailTime.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtRight.setText("立即支付");
                                mBtOrderDetailBtLeft.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtRight.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtLeft.setText("取消订单");
                                mBtOrderDetailBtLeft.setTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.font_colour_99));
                                mBtOrderDetailBtLeft.setBackgroundResource(R.drawable.bt_order_grey);
                                Long time = dataJson.optLong("surplus");
                                List<String> time_array;
                                time_array = Util.formatTime(time);
                                mElectricityCountdown.setVisibility(View.VISIBLE);
                                mElectricityCountdown.setTime("0", time_array.get(0) + "", time_array.get(1) + "", time_array.get(2));
                                break;
                            case 70:
                                mRlOrderDetailTime.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtRight.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtRight.setText("申请退款");
                                mBtOrderDetailBtRight.setTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.green));
                                mBtOrderDetailBtRight.setBackgroundResource(R.drawable.br_order_green);
                                break;
                            case 110:
                                mRlOrderDetailTime.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtRight.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtRight.setText("确认收货");
                                mBtOrderDetailBtRight.setTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.blue));
                                mBtOrderDetailBtRight.setBackgroundResource(R.drawable.bt_order_blue);
                                mBtOrderDetailBtLeft.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtLeft.setText("申请退货");
                                mBtOrderDetailBtLeft.setTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.blue));
                                mBtOrderDetailBtLeft.setBackgroundResource(R.drawable.bt_order_blue);
                                break;
                            case 140:
                                mRlOrderDetailTime.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtRight.setVisibility(View.VISIBLE);
                                mBtOrderDetailBtRight.setText("立即评价");
                                mBtOrderDetailBtRight.setTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.font_colour_ff7d));
                                mBtOrderDetailBtRight.setBackgroundResource(R.drawable.bt_order_origen);
                                break;
                        }
                        mTvOrderDetailOrdernum.setText("订单号：" + dataJson.getString("osn"));
                        if (!(dataJson.getString("shipsn").equals("null"))) {
//                            mTvOrderDetailEms.setText((String.format(getResources().getString(R.string.tv_ems), dataJson.optString("shipsn"))));
                        }
                        mTvConfirmName.setText(dataJson.getString("consignee"));
                        mTvConfirmSname.setText(dataJson.getString("storename"));
                        mTvConfirmNum.setText(String.format(getResources().getString(R.string.tv_order_num), num + ""));
                        mTvOrderPrice.setText(String.format(getResources().getString(R.string.tv_mall_price), dataJson.optString("surplusmoney")));
                        mTvShipTime.setText((String.format(getResources().getString(R.string.tv_order_time), dataJson.optString("addtime"))));
                        mTvConfirmTelephone.setText(dataJson.optString("mobile"));
                        mTvConfirmAddress.setText(dataJson.optString("address"));
                        mTvOrderDetailPaykind.setText(mOrderDetailBean.getData().getPaymode().equals("1")
                                ? "在线支付" : "线下支付");
                        List<Map<String, Object>> pic_list = new ArrayList<>();
                        for (OrderDetailBean.DataBean.DetailsBean detailsBean : mOrderDetailBean.getData().getDetails()) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("logo", detailsBean.getShowimg());
                            pic_list.add(map);
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(OrderDetailActivity.this, 3, LinearLayoutManager.VERTICAL, false) {
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        };
//                        mRvConfirmShowimg.setLayoutManager(layoutManager);
//                        ConfirmPicAdapter confirmPicAdapter = new ConfirmPicAdapter(pic_list);
//                        mRvConfirmShowimg.setAdapter(confirmPicAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(OrderDetailActivity.this, MyOrderActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @OnClick({R.id.iv_choose_doc_back, R.id.bt_order_detail_bt_right, R.id.bt_order_detail_bt_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_doc_back:
                Util.startIntent(OrderDetailActivity.this, MyOrderActivity.class);
                break;
            case R.id.bt_order_detail_bt_right:
                switch (orderstate) {
                    case 30:
                        Intent intent1 = new Intent(OrderDetailActivity.this, PayActivity.class);
                        intent1.putExtra("sname", mOrderDetailBean.getData().getStorename());
                        intent1.putExtra("price", mOrderDetailBean.getData().getSurplusmoney());
                        intent1.putExtra("orderid", mOrderDetailBean.getData().getOid());
                        intent1.putExtra("fromFlag", "1");
                        startActivity(intent1);
                        break;
                    case 70:
                        break;
                    case 110:
                        Map<String, String> comment = new HashMap<>();
                        comment.put("oid", mOrderDetailBean.getData().getOid());
                        comment.put("statue", "140");
                        Http.getInstance().init(OrderDetailActivity.this, mHandler, mGson.toJson(comment), "order/handleOrder", 0).sendMsg();
                        break;
                    case 140:
//                        Intent intent2 = new Intent(OrderDetailActivity.this, CommentGoodsActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("order", mOrderDetailBean.getData());
//                        intent2.putExtras(bundle);
//                        startActivity(intent2);
//                        break;
                }
                break;
            case R.id.bt_order_detail_bt_left:
                switch (orderstate) {
                    case 30:
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                        builder.setMessage("确定要取消订单么？");
                        builder.setTitle("健康商城");
                        builder.setPositiveButton("确认", (dialog, which) -> {
                            Map<String, String> comment = new HashMap<>();
                            comment.put("oid", mOrderDetailBean.getData().getOid());
                            comment.put("statue", "200");
                            Http.getInstance().init(OrderDetailActivity.this, mHandler, mGson.toJson(comment), "order/handleOrder", 0).sendMsg();
                        });
                        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                        builder.create().show();
                        break;
                    case 90:
                        break;
                }
                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    Util.showMsg(OrderDetailActivity.this, data);
                    finish();
                    break;
            }
        }
    };
}
