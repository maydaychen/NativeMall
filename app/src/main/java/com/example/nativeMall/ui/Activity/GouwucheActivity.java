package com.example.nativeMall.ui.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.ShopcartExpandableListViewAdapter;
import com.example.nativeMall.Bean.PreOrderBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.entity.GroupInfo;
import com.example.nativeMall.entity.ProductInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GouwucheActivity extends InitActivity implements ShopcartExpandableListViewAdapter.CheckInterface, ShopcartExpandableListViewAdapter.ModifyCountInterface, View.OnClickListener {
    @BindView(R.id.tv_go_to_pay)
    TextView mTvGoToPay;
    @BindView(R.id.tv_gouwuche_bianji)
    TextView mTvGouwucheBianji;
    private int totalCount = 0;// 购买的商品总数量
    private ShopcartExpandableListViewAdapter selva;
    private List<GroupInfo> groups = new ArrayList<>();// 组元素数据列表
    private Map<String, List<ProductInfo>> children = new HashMap<>();// 子元素数据列表

    private ExpandableListView exListView;
    private CheckBox cb_check_all;
    private TextView tv_total_price;
    private TextView tv_go_to_pay;
    private Context context;
    private Context mContext = null;
    JSONObject json = null, json3 = null, json_med = null;
    JSONArray json2 = null, jsonarray_med = null;
    private String PAYOREDIT = "pay";
    JsonArray array = new JsonArray();
    GroupInfo groupInfo;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        json = new JSONObject(data);
                        json2 = json.getJSONArray("data");
                        groups = new ArrayList<>();
                        children = new HashMap<>();
                        for (int j = 0; j < json2.length(); j++) {
                            json3 = json2.getJSONObject(j);
                            groupInfo = new GroupInfo(j + "", json3.getString("sName"));
                            groups.add(groupInfo);
                            jsonarray_med = json3.getJSONArray("proList");
                            List<ProductInfo> products = new ArrayList<>();
                            for (int k = 0; k < jsonarray_med.length(); k++) {
                                json_med = jsonarray_med.getJSONObject(k);
                                double price = Double.parseDouble(json_med.getString("shopprice"));
                                ProductInfo productInfo = new ProductInfo(k + "", "商品", json_med.getString("showimg"), json_med.getString("name"),
                                        price, Integer.valueOf(json_med.getString("buycount")), json_med.getString("recordid"), json_med.getString("pid"));
                                products.add(productInfo);
                            }
                            children.put(groups.get(j).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
                        }
                        selva = new ShopcartExpandableListViewAdapter(groups, children, GouwucheActivity.this);
                        selva.setCheckInterface(GouwucheActivity.this);// 关键步骤1,设置复选框接口
                        selva.setModifyCountInterface(GouwucheActivity.this);// 关键步骤2,设置数量增减接口
                        exListView.setAdapter(selva);

                        for (int z = 0; z < selva.getGroupCount(); z++) {
                            exListView.expandGroup(z);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
                        }
//                        groups = null;
                        cb_check_all.setOnClickListener(GouwucheActivity.this);
                        tv_go_to_pay.setOnClickListener(GouwucheActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    Util.showMsg(GouwucheActivity.this, data);
                    initData();
//                    Toast.makeText(GouwucheActivity.this, data, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //提交订单
                    try {
                        json = new JSONObject(data);
                        if (json.getString("success").equals("T")) {
                            if (json.getString("msg").equals("NoAddress")) {
                                Toast.makeText(mContext, "请添加收货地址！", Toast.LENGTH_SHORT).show();
                                Util.startIntent(GouwucheActivity.this, AddAddressActivity.class);
                            } else {
                                PreOrderBean preOrderBean = mGson.fromJson(data, PreOrderBean.class);
                                Intent intent = new Intent(GouwucheActivity.this, ConfirmActivity.class);
                                intent.putExtra("array",array.toString());
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("preorder", preOrderBean);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        } else {
                            initData();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    Gson mGson = new Gson();

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouwuche);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
//        initData();
//        initView();
    }

    @Override
    public void initView() {
        context = this;
        exListView = (ExpandableListView) findViewById(R.id.exListView);
        cb_check_all = (CheckBox) findViewById(R.id.all_chekbox);
        cb_check_all.setChecked(false);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        tv_go_to_pay = (TextView) findViewById(R.id.tv_go_to_pay);
        ImageView back = (ImageView) findViewById(R.id.iv_choose_doc_back);
        back.setOnClickListener(view -> finish());
        mTvGouwucheBianji.setOnClickListener(view -> {
            if (PAYOREDIT .equals("pay") ) {
                mTvGoToPay.setText("删除");
                mTvGouwucheBianji.setText("保存");
                PAYOREDIT = "delete";
            } else {
                PAYOREDIT = "pay";
                mTvGoToPay.setText("立即购买");
                mTvGouwucheBianji.setText("编辑");
            }
        });
    }

    @Override
    public void initData() {
        mContext = this;
        Map<String, String> param = new HashMap<>();
        if (Config.IS_LOG) {
            param.put("uid", Config.userBean.getData().getUid());
            param.put("sid", MainActivity.szImei);
        } else {
            param.put("uid", "-1");
            param.put("sid", MainActivity.szImei);
        }
        param.put("type", "LIST");
        Http.getInstance().init(this, handler, mGson.toJson(param), "order/shoppingCart", 0).sendMsg();
    }

    @Override
    public void onClick(View v) {
        AlertDialog alert;
        switch (v.getId()) {
            case R.id.all_chekbox:
                doCheckAll();
                break;
            case R.id.tv_go_to_pay:
                if (PAYOREDIT .equals("pay") ) {
                    if (totalCount == 0) {
                        Toast.makeText(context, "请选择要支付的商品", Toast.LENGTH_LONG).show();
                        return;
                    }
                    doPay();
                } else {
                    if (totalCount == 0) {
                        Toast.makeText(context, "请选择要移除的商品", Toast.LENGTH_LONG).show();
                        return;
                    }
                    alert = new AlertDialog.Builder(context).create();
                    alert.setTitle("操作提示");
                    alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                    alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (dialog, which) -> {
                    });
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
                        doDelete();
                    });
                    alert.show();
                }
                break;
        }
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {
        Map<String, Object> param = new HashMap<>();
        List<Integer> scids = new ArrayList<>();
        List<GroupInfo> toBeDeleteGroups = new ArrayList<>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<ProductInfo> toBeDeleteProducts = new ArrayList<>();// 待删除的子元素列表
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    toBeDeleteProducts.add(childs.get(j));
                    scids.add(Integer.valueOf(childs.get(j).getRecordId()));
                }
            }
            childs.removeAll(toBeDeleteProducts);
        }
        groups.removeAll(toBeDeleteGroups);
        selva.notifyDataSetChanged();
        if (Config.IS_LOG) {
            param.put("uid", Config.userBean.getData().getUid());
            param.put("sid", MainActivity.szImei);
        } else {
            param.put("uid", "-1");
            param.put("sid", MainActivity.szImei);
        }
        param.put("cid", scids);
        param.put("type", "DELETE");
        Http.getInstance().init(this, handler, mGson.toJson(param), "order/shoppingCart", 1).sendMsg();

        calculate();
    }

    protected void doPay() {
        if (Config.IS_LOG) {
            array = new JsonArray();
            JsonObject outJson = new JsonObject();
            List<GroupInfo> toBeDeleteGroups = new ArrayList<>();// 待删除的组元素列表
            for (int j = 0; j < groups.size(); j++) {
                GroupInfo group = groups.get(j);
                if (group.isChoosed()) {
                    toBeDeleteGroups.add(group);
                }
            }
            if (toBeDeleteGroups.size() > 1) {
                Toast.makeText(context, "抱歉，请选择同一商家商品", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < groups.size(); i++) {
                GroupInfo group = groups.get(i);
                if (group.isChoosed()) {
                    toBeDeleteGroups.add(group);
                }
//                List<ProductInfo> toBeDeleteProducts = new ArrayList<>();// 待删除的子元素列表
                List<ProductInfo> childs = children.get(group.getId());
                for (int j = 0; j < childs.size(); j++) {
                    if (childs.get(j).isChoosed()) {
//                        toBeDeleteProducts.add(childs.get(j));
                        JsonObject arrJson = new JsonObject();
                        arrJson.addProperty("pid", childs.get(j).getPid());
                        arrJson.addProperty("num", childs.get(j).getCount() + "");
                        arrJson.addProperty("carid", childs.get(j).getRecordId());
                        array.add(arrJson);
                    }
                }
//                childs.removeAll(toBeDeleteProducts);
            }
//            groups.removeAll(toBeDeleteGroups);
            outJson.addProperty("uid", Config.userBean.getData().getUid());
            outJson.add("pinfo", array);
            Http.getInstance().init(this.getApplicationContext(), handler, outJson.toString(), "order/submitPreOrder", 2).sendMsg();
            totalCount = 0;
        } else {
            Util.startIntent(GouwucheActivity.this,LoginActivity.class);
        }

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ProductInfo product = (ProductInfo) selva.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        currentCount++;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

        ProductInfo product = (ProductInfo) selva.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        if (currentCount == 1)
            return;
        currentCount--;

        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");

        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            cb_check_all.setChecked(true);
        else
            cb_check_all.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosiTion, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck())
            cb_check_all.setChecked(true);
        else
            cb_check_all.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();
    }

    private boolean isAllCheck() {

        for (GroupInfo group : groups) {
            if (!group.isChoosed())
                return false;

        }

        return true;
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(cb_check_all.isChecked());
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(cb_check_all.isChecked());
            }
        }
        selva.notifyDataSetChanged();
        calculate();
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * <p/>
     * <p/>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        double totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                ProductInfo product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                }
            }
        }
        tv_total_price.setText("￥" + String.format("%.2f", totalPrice));
//        tv_go_to_pay.setText("去支付(" + totalCount + ")");
    }
}
