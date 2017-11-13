package com.example.nativeMall.ui.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nativeMall.Adapter.RecyclerViewAdapter;
import com.example.nativeMall.Bean.OrderBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：JTR on 2016/9/20 11:08
 * 邮箱：2091320109@qq.com
 */
public class DaishouhuoFragment extends Fragment {
    @BindView(R.id.rv_all_orders)
    RecyclerView recylcerview;

    Gson mGson = new Gson();
    JSONObject mJSONObject = null;
    OrderBean mOrderBean;
    //
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    List<Map<String, Object>> list = new ArrayList<>();
                    try {
                        mJSONObject = new JSONObject(data);
                        if (mJSONObject.getString("success").equals("T")) {
                            mOrderBean = mGson.fromJson(data, OrderBean.class);

                            for (int i = 0; i < mOrderBean.getData().size(); i++) {
                                ArrayList<String> imgList = new ArrayList<>();
                                Map<String, Object> map = new HashMap<>();
                                map.put("storename", mOrderBean.getData().get(i).getStorename());
                                map.put("payfee", mOrderBean.getData().get(i).getPayfee());
                                map.put("oid", mOrderBean.getData().get(i).getOid());
                                map.put("payfee",mOrderBean.getData().get(i).getPayfee());
                                map.put("sname",mOrderBean.getData().get(i).getStorename());
                                map.put("orderstate", mOrderBean.getData().get(i).getOrderstate());
                                map.put("num", mOrderBean.getData().get(i).getDetails().size());
                                for (int j = 0; j < mOrderBean.getData().get(i).getDetails().size(); j++) {
                                    imgList.add(mOrderBean.getData().get(i).getDetails().get(j).getShowimg());
                                }
                                map.put("img", imgList);
                                list.add(map);
                            }
                            //竖直排列、正向排序
                            recylcerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            //添加了一个灰色背景
                            recylcerview.setBackgroundResource(R.color.background);
                            recylcerview.setAdapter(new RecyclerViewAdapter(list,getActivity(),mHandler));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    Util.showMsg(getActivity(),data);
                    initData();
                    break;
            }
        }
    };

    private void initData() {
        Map<String, String> param2 = new HashMap<>();
        param2.put("orderstate", "110");
        param2.put("uid", Config.userBean.getData().getUid());
        Http.getInstance().init(getActivity(), handler, mGson.toJson(param2), "order/findOrderlist", 0).sendMsg();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_all_order, container, false);
        ButterKnife.bind(this, root);
        initData();
        return root;
    }

}