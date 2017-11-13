package com.example.nativeMall.ui.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.CommentGoodsAdapter;
import com.example.nativeMall.Bean.OrderDetailBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentGoodsActivity extends InitActivity {
    @BindView(R.id.tv_comment_goods_ordernum)
    TextView mTvCommentGoodsOrdernum;
    @BindView(R.id.rv_comment_goods_detail)
    RecyclerView mRvCommentGoodsDetail;
    @BindView(R.id.activity_comment_goods)
    ScrollView mActivityCommentGoods;

    private OrderDetailBean.DataBean mOrderDetailBean;
    private CommentGoodsAdapter mCommentGoodsAdapter;
    private List<Map<String, Object>> data_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_goods);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        initView();
    }

    @Override
    public void initView() {
        mTvCommentGoodsOrdernum.setText(String.format(getResources().getString(R.string.tv_order_number), mOrderDetailBean.getOsn()));
        mRvCommentGoodsDetail.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mCommentGoodsAdapter = new CommentGoodsAdapter(this, data_list);
        mRvCommentGoodsDetail.setAdapter(mCommentGoodsAdapter);
        mActivityCommentGoods.smoothScrollTo(0, 0);
    }

    @Override
    public void initData() {
        mOrderDetailBean = (OrderDetailBean.DataBean) getIntent().getSerializableExtra("order");
        for (int i = 0; i < mOrderDetailBean.getDetails().size(); i++) {
            Map<String, Object> map = new HashMap<>();
            OrderDetailBean.DataBean.DetailsBean dataBean = mOrderDetailBean.getDetails().get(i);
            map.put("url", dataBean.getShowimg());
            map.put("name", dataBean.getName());
            data_list.add(map);
        }
    }

    @OnClick({R.id.iv_gomment_goods_back, R.id.bt_comment_goods_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_gomment_goods_back:
                finish();
                break;
            case R.id.bt_comment_goods_commit:
                List<Map<String, String>> mList = new ArrayList();
                mList = mCommentGoodsAdapter.getRate();
                JsonObject outJson = new JsonObject();
                outJson.addProperty("uid", Config.userBean.getData().getUid());
                outJson.addProperty("oid", mOrderDetailBean.getOid());
                outJson.addProperty("storeid", mOrderDetailBean.getStoreid());
                JsonArray array = new JsonArray();
                for (int i = 0; i < mOrderDetailBean.getDetails().size(); i++) {
                    JsonObject arrJson = new JsonObject();
                    arrJson.addProperty("pid", mOrderDetailBean.getDetails().get(i).getPid());
                    arrJson.addProperty("star", mList.get(i).get("fuwu"));
                    arrJson.addProperty("quality", mList.get(i).get("miaoshu"));
                    arrJson.addProperty("speed", mList.get(i).get("fahuo"));
                    array.add(arrJson);
                }
                outJson.add("pinfo", array);

                Http.getInstance().init(this, mHandler, outJson.toString(), "index/evaluate", 0).sendMsg();
                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    JSONObject json;
                    try {
                        json = new JSONObject(data);
                        Toast.makeText(CommentGoodsActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                        if (json.get("success").equals("T")) {
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
