package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.ConfirmPicAdapter;
import com.example.nativeMall.Bean.PreOrderBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jakewharton.rxbinding.view.RxView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmActivity extends InitActivity {

    @BindView(R.id.ll_confirm_peisong)
    LinearLayout mLlConfirmPeisong;
    @BindView(R.id.tv_confirm_name)
    TextView mTvConfirmName;
    @BindView(R.id.tv_confirm_telephone)
    TextView mTvConfirmTelephone;
    @BindView(R.id.tv_confirm_address)
    TextView mTvConfirmAddress;
    @BindView(R.id.tv_confirm_price)
    TextView mTvConfirmPrice;
    @BindView(R.id.tv_confirm_sname)
    TextView mTvConfirmSname;
    @BindView(R.id.tv_confirm_num)
    TextView mTvConfirmNum;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;
    @BindView(R.id.tv_ship_price)
    TextView mTvShipPrice;
    @BindView(R.id.rv_confirm_showimg)
    RecyclerView mRvConfirmShowimg;
    @BindView(R.id.bt_confirm_commit)
    Button mBtConfirmCommit;
    private PreOrderBean mPreOrderBean;
    private PreOrderBean.DataBean.AddressBean addressBean;
    private List<Map<String, Object>> pic_list = new ArrayList<>();
    private static final int SHOW_SUBACTIVITY = 1;
    private Gson mGson = new Gson();
    private double money;
    private JsonArray array;
    private int buycount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @OnClick({R.id.iv_comfirm_back, R.id.rl_confirm_address, R.id.rl_confirm_showimg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.rl_confirm_address:
                Intent intent = new Intent(ConfirmActivity.this, MyAddressActivity.class);
                intent.putExtra("ISCONFIRM", true);
                startActivityForResult(intent, SHOW_SUBACTIVITY);
                break;
            case R.id.rl_confirm_showimg:
//                Intent intent1 = new Intent(ConfirmActivity.this, ConfirmOrderActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("preorder", mPreOrderBean);
//                intent1.putExtras(bundle);
//                startActivityForResult(intent1, SHOW_SUBACTIVITY);
                Intent intent1 = new Intent(ConfirmActivity.this, ConfirmOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("preorder", mPreOrderBean);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                JsonObject outJson = new JsonObject();
                outJson.addProperty("uid", Config.userBean.getData().getUid());
                outJson.addProperty("addid", data.getIntExtra("said", 1));
                outJson.add("pinfo", array);
                Http.getInstance().init(ConfirmActivity.this, handler, outJson.toString(), "order/submitPreOrder", 1).sendMsg();
                break;
        }
    }

    @Override
    public void initView() {
        refreshText();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ConfirmActivity.this, 3, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRvConfirmShowimg.setLayoutManager(layoutManager);

        ConfirmPicAdapter confirmPicAdapter = new ConfirmPicAdapter(pic_list);
        mRvConfirmShowimg.setAdapter(confirmPicAdapter);

        RxView.clicks(mBtConfirmCommit)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    JsonObject outJson = new JsonObject();
                    outJson.addProperty("uid", Config.userBean.getData().getUid());
                    outJson.addProperty("totalMoney", money);
                    outJson.addProperty("addid", mPreOrderBean.getData().getAddress().getSaid());
                    outJson.addProperty("paytype", "1");
                    JsonArray array = new JsonArray();
                    for (int i = 0; i < mPreOrderBean.getData().getProducts().size(); i++) {
                        JsonObject arrJson = new JsonObject();
                        arrJson.addProperty("pid", mPreOrderBean.getData().getProducts().get(i).getProduct().getPid());
                        arrJson.addProperty("num", mPreOrderBean.getData().getProducts().get(i).getBuycount());
                        arrJson.addProperty("fare", mPreOrderBean.getData().getProducts().get(i).getFare());
                        String carid = mPreOrderBean.getData().getProducts().get(i).getCarid();
                        if (carid != null && !"".equals(carid)) {
                            arrJson.addProperty("carid", carid);
                        }
                        array.add(arrJson);
                    }
                    outJson.add("pinfo", array);
                    Http.getInstance().init(ConfirmActivity.this, handler, outJson.toString(), "order/ submitOrder", 0).sendMsg();
                });
    }

    @Override
    public void initData() {
        mPreOrderBean = (PreOrderBean) getIntent().getSerializableExtra("preorder");
        String arrayString = getIntent().getStringExtra("array");
        array = new JsonParser().parse(arrayString).getAsJsonArray();
        refreshDate();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            String id;
            try {
                switch (msg.what) {
                    case 0:
                        JSONObject jsonObject = new JSONObject(data);
                        id = jsonObject.getString("data");
                        Toast.makeText(ConfirmActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        if (jsonObject.get("success").equals("T")) {
                            Intent intent = new Intent(ConfirmActivity.this, PayActivity.class);
                            intent.putExtra("sname", mPreOrderBean.getData().getProducts().get(0).getProduct().getSname());
                            intent.putExtra("price", money + "");
                            intent.putExtra("orderid", id);
                            intent.putExtra("fromFlag", "1");
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        JSONObject json = new JSONObject(data);
                        if (json.getString("success").equals("T")) {
                            mPreOrderBean = mGson.fromJson(data, PreOrderBean.class);
                            refreshDate();
                            refreshText();
                        }
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void refreshDate() {
        money = 0;
        buycount = 0;
        pic_list = new ArrayList<>();
        for (int i = 0; i < mPreOrderBean.getData().getProducts().size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("logo", mPreOrderBean.getData().getProducts().get(i).getProduct().getShowimg());
            pic_list.add(map);
            money += Double.parseDouble(mPreOrderBean.getData().getProducts().get(i).getTotalMoney());
            buycount += Integer.parseInt(mPreOrderBean.getData().getProducts().get(i).getBuycount());
        }
        money -= mPreOrderBean.getData().getCutPrice();
        addressBean = mPreOrderBean.getData().getAddress();
    }

    private void refreshText() {
        mTvConfirmName.setText(addressBean.getConsignee());
        mTvConfirmPrice.setText(String.format(getResources().getString(R.string.tv_mall_price), money + ""));
        mTvConfirmTelephone.setText(addressBean.getMobile());
        mTvConfirmAddress.setText(addressBean.getProvincename() + addressBean.getCityname() + addressBean.getRename() + addressBean.getAddress());
        mTvConfirmSname.setText(mPreOrderBean.getData().getProducts().get(0).getProduct().getSname());
        mTvConfirmNum.setText(String.format(getResources().getString(R.string.tv_order_num), buycount + ""));
        mTvOrderPrice.setText(String.format(getResources().getString(R.string.tv_mall_price), money + ""));
        mTvShipPrice.setText(String.format(getResources().getString(R.string.tv_mall_price), mPreOrderBean.getData().getProducts().get(0).getFare() + ""));
    }
}
