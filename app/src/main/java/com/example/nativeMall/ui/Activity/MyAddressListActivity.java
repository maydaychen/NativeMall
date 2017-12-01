//package com.example.nativeMall.ui.Activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.example.nativeMall.Adapter.AllAddressListAdapter;
//import com.example.nativeMall.Bean.AddressBean;
//import com.example.nativeMall.Config;
//import com.example.nativeMall.R;
//import com.example.nativeMall.http.Http;
//import com.example.nativeMall.ui.widget.RecycleViewDivider;
//import com.google.gson.Gson;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class MyAddressListActivity extends InitActivity {
//
//    @BindView(R.id.rv_item_address)
//    RecyclerView mRvItemAddress;
//
//    Gson mGson = new Gson();
//    JSONObject mJSONObject = null;
//    AddressBean mAddressBean;
//    AllAddressListAdapter mAllAddressAdapter;
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        initData();
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_my_address);
//        ButterKnife.bind(this);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        mRvItemAddress.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
//        mRvItemAddress.setLayoutManager(layoutManager);
//
//    }
//
//    @Override
//    public void initData() {
//        Map<String, String> param = new HashMap<>();
//        param.put("type", "LIST");
//        param.put("uid", Config.userBean.getData().getUid());
//        Http.getInstance().init(MyAddressListActivity.this, handler, mGson.toJson(param), "address/shopAddressUpdate", 0).sendMsg();
//    }
//
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            String data = msg.obj.toString();
//            switch (msg.what) {
//                case 0:
//                    try {
//                        mJSONObject = new JSONObject(data);
//                        if (mJSONObject.getString("success").equals("T")) {
//                            mAddressBean = mGson.fromJson(data, AddressBean.class);
//                            List<Map<String, Object>> listmaps = new ArrayList<>();
//                            for (int i = 0; i < mAddressBean.getData().size(); i++) {
//                                Map<String, Object> map = new HashMap<>();
//                                map.put("name", mAddressBean.getData().get(i).getConsignee());
//                                map.put("mobile", mAddressBean.getData().get(i).getMobile());
//                                map.put("address", mAddressBean.getData().get(i).getProvincename()
//                                        + mAddressBean.getData().get(i).getCityname()
//                                        + mAddressBean.getData().get(i).getName()
//                                        + mAddressBean.getData().get(i).getAddress());
//                                map.put("default", mAddressBean.getData().get(i).getDefaultX());
//                                listmaps.add(map);
//                            }
//                            mAllAddressAdapter = new AllAddressListAdapter(listmaps);
//                            mRvItemAddress.setAdapter(mAllAddressAdapter);
//                            mAllAddressAdapter.setOnItemClickListener(new AllAddressListAdapter.OnRecyclerViewItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, int data) {
//                                    Intent intent = new Intent(MyAddressListActivity.this, ChangeAddressActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("address", mAddressBean.getData().get(data));
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//
//                                }
//                            });
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 1:
//                    break;
//            }
//        }
//    };
//
//    @OnClick({R.id.iv_choose_doc_back, R.id.rl_my_address_add})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_choose_doc_back:
//                finish();
//                break;
//            case R.id.rl_my_address_add:
//                Intent intent = new Intent(MyAddressListActivity.this, AddAddressActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }
//}
