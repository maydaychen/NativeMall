package com.example.nativeMall.ui.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.MallTitle;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jakewharton.rxbinding.view.RxView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDCallBack;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;
import cn.wd.checkout.api.WDReqParams;
import cz.msebera.android.httpclient.Header;

public class PayActivity extends InitActivity {

    @BindView(R.id.rb_alipay)
    RadioButton mRbAlipay;
    @BindView(R.id.rb_weixinpay)
    RadioButton mRbWeixinpay;
    @BindView(R.id.tv_pay_solder_detail)
    TextView mTvPaySolder;
    @BindView(R.id.tv_pay_money_detail)
    TextView mTvPayMoney;
    private static final String TAG = "PayActivity";
    @BindView(R.id.bt_pay)
    TextView btPay;
    @BindView(R.id.rl_confirm_title)
    MallTitle mRlConfirmTitle;
    private String submerno = "303121700000042";
    private String Appid = null;
    JSONArray pList = null;
    private ProgressDialog loadingDialog;
    private String fromFlag = null;    // 标志支付是从商城发起，还是寻医问药挂号发起。 1：商城；0：寻医问药；
    private ProgressDialog progressDialog = null;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @Override
    public void initView() {
        mTvPaySolder.setText(getIntent().getStringExtra("sname"));
        mTvPayMoney.setText(String.format(getResources().getString(R.string.tv_mall_price), "" +
                getIntent().getStringExtra("price")));
        fromFlag = getIntent().getStringExtra("fromFlag");
        if (fromFlag.equals("0")) {
            String regOrderId = getIntent().getStringExtra("orderid");
            Appid = getIntent().getStringExtra("appid");
            submerno = getIntent().getStringExtra("submerno");
            Log.i(TAG, "initView: Appid=" + Appid + "sub=" + submerno);
        }
        Log.i(TAG, "initView: " + fromFlag);
        mRlConfirmTitle.setLeftRightImgClickListener(new MallTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                showDialog();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
    }

    @Override
    public void initData() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        Toast.makeText(PayActivity.this, data, Toast.LENGTH_SHORT).show();
                        JSONObject JSONObject = new JSONObject(data);
                        org.json.JSONObject JSONObject2 = JSONObject.getJSONObject("data");
                        if (JSONObject.getString("success").equals("T")) {
                            pList = JSONObject2.getJSONArray("pList");
                            CheckOut.setAppIdAndSecret("wd2015tst004", JSONObject2.getString
                                    ("payKey"));
                            CheckOut.setIsPrint(true);
                            /**
                             * 设置访问网络环境  CT 为联调测试环境 不调用此方法为生产环境
                             */
//                            CheckOut.setNetworkWay("CT");
//				CheckOut.setNetworkWay("CST");
//                            CheckOut.setLianNetworkWay("TS");

                            WDPay.getInstance(PayActivity.this).reqPayAsync(WDReqParams
                                            .WDChannelTypes.alipay, submerno,
                                    JSONObject2.getString("name"),               //订单标题
                                    "商品购买",
                                    // Double.parseDouble(mJSONObject2.getString("price"))*100,
                                    // 订单金额(分)
                                    (long) (Double.parseDouble(JSONObject2.getString("price")) *
                                            100),
                                    JSONObject2.getString("orderNo"),  //订单流水号
                                    "",
                                    null,            //扩展参数(可以null)
                                    bcCallback);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 1:
                    break;
                case 2:
                    //为您推荐
                    Toast.makeText(PayActivity.this, data, Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    break;
            }
        }
    };

    @OnClick(R.id.bt_pay)
    public void btnClick() {
        RxView.clicks(btPay)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    String PAY;
                    if (mRbAlipay.isChecked()) {
                        PAY = "alipay";
                        if (fromFlag.equals("1")) {  //从商城来
                            Map<String, String> param = new HashMap<>();
                            param.put("oid", getIntent().getStringExtra("orderid"));
                            param.put("type", PAY);
                            param.put("appid", "wd2015tst004");
                            Http.getInstance().init(PayActivity.this.getApplicationContext(), handler, gson.toJson(param), "pay/paynow", 0)
                                    .sendMsg();
                        } else if (fromFlag.equals("0")) {  //从寻医问药来
                            getPayKeyByAsync();
                        }
                    } else {
                        Toast.makeText(PayActivity.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //支付结果返回入口
    WDCallBack bcCallback = bcResult -> {
        final WDPayResult bcPayResult = (WDPayResult) bcResult;
        PayActivity.this.runOnUiThread(() -> {
            CloesLoading();
            String result = bcPayResult.getResult();
            Log.i("demo", "done   result=" + result);
            if (result.equals(WDPayResult.RESULT_SUCCESS)) {
                Toast.makeText(PayActivity.this, "用户支付成功", Toast.LENGTH_LONG).show();
                if (fromFlag.equals("1")) {
                    JSONObject outJson = new JSONObject();
                    try {
                        outJson.put("pList", pList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Http.getInstance().init(PayActivity.this, handler, outJson.toString(), "order/updateNum", 1)
                            .sendMsg();
                    Intent intent = new Intent(PayActivity.this, MyOrderActivity.class);
                    startActivity(intent);
                } else {
                    //不为1；
                    //通知后台，并跳转：
                    //TellBackPaySuccess();
                    new Handler().postDelayed(() -> {
                        Intent intent1 = new Intent(PayActivity.this, DateListAct.class);
                        startActivity(intent1);
                        finish();
                    }, 1500);
                }

            } else if (result.equals(WDPayResult.RESULT_CANCEL)) {
                Toast.makeText(PayActivity.this, "用户取消支付", Toast.LENGTH_LONG).show();
                if (fromFlag.equals("1")) {
                    Intent intent = new Intent(PayActivity.this, MyOrderActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //不为1则取订单列表
                    Intent intent = new Intent(PayActivity.this, DateListAct.class);
                    startActivity(intent);
                    finish();
                }
            } else if (result.equals(WDPayResult.RESULT_FAIL)) {
                String info = "支付失败, 原因: " + bcPayResult.getErrMsg()
                        + ", " + bcPayResult.getDetailInfo();
                Toast.makeText(PayActivity.this, info, Toast.LENGTH_LONG).show();
            } else if (result.equals(WDPayResult.FAIL_UNKNOWN_WAY)) {
                Toast.makeText(PayActivity.this, "未知支付渠道", Toast.LENGTH_LONG).show();
            } else if (result.equals(WDPayResult.FAIL_WEIXIN_VERSION_ERROR)) {
                Toast.makeText(PayActivity.this, "针对微信 支付版本错误（版本不支持）", Toast.LENGTH_LONG)
                        .show();
            } else if (result.equals(WDPayResult.FAIL_EXCEPTION)) {
                Toast.makeText(PayActivity.this, "支付过程中的Exception", Toast.LENGTH_LONG)
                        .show();
            } else if (result.equals(WDPayResult.FAIL_ERR_FROM_CHANNEL)) {
                Toast.makeText(PayActivity.this, "从第三方app支付渠道返回的错误信息，原因: " + bcPayResult
                        .getErrMsg(), Toast.LENGTH_LONG).show();
            } else if (result.equals(WDPayResult.FAIL_INVALID_PARAMS)) {
                Toast.makeText(PayActivity.this, "参数不合法造成的支付失败", Toast.LENGTH_LONG).show();
            } else if (result.equals(WDPayResult.RESULT_PAYING_UNCONFIRMED)) {
                Toast.makeText(PayActivity.this, "表示支付中，未获取确认信息", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PayActivity.this, "invalid return", Toast.LENGTH_LONG)
                        .show();
            }
        });
    };

    private void CloesLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            //此处关闭loading界面
            loadingDialog.dismiss();
        }
    }

    private void getPayKeyByAsync() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "orderInfo/topay?appid=" + Appid + "&orderid="
                + getIntent().getStringExtra("orderid");
        Log.i(TAG, "getPayKeyByAsync: " + URL);

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                JsonObject jsonObject1 = new JsonParser().parse(result).getAsJsonObject();
                if (jsonObject1.get("MSG").getAsString().equals("success")) {

                    String PayKey = jsonObject1.get("ITEM").getAsString();
                    String orderNo = jsonObject1.get("serialNumber").getAsString();
                    long price = (long) (Double.parseDouble(getIntent().getStringExtra("price")) *
                            100);
                    Log.i(TAG, "onSuccess:price1 " + price + ">>price2" + getIntent()
                            .getStringExtra("price"));
                    Log.i(TAG, "onSuccess: PayKey=" + PayKey + "orderNo=" + orderNo + "price=" +
                            price + "APPID=" + Appid + "sub=" + submerno);
                    //progressDialog.dismiss();
                    Util.PayByHuiDao(PayActivity.this, Appid, PayKey, "alipay",
                            submerno, "挂号订单", "商品描述", price, orderNo, null, null, bcCallback);
                    Toast.makeText(PayActivity.this, "click", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PayActivity.this, "获取PayKey失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

//    WDCallBack callBack = wdResult -> {
//
//    };

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
        builder.setMessage("确定要取消支付么？");
        builder.setTitle("健康商城");

        builder.setPositiveButton("确认", (dialog, which) -> {
            Intent intent = new Intent(PayActivity.this, MyOrderActivity.class);
            startActivity(intent);
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog();
        }
        return false;
    }
}
