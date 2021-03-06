package com.example.nativeMall.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nativeMall.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：JTR on 2016/9/18 14:37
 * 邮箱：2091320109@qq.com
 */
public class AllOrderFragment extends Fragment {
    @BindView(R.id.rv_all_orders)
    RecyclerView recylcerview;

    private Gson mGson = new Gson();

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            String data = msg.obj.toString();
//            switch (msg.what) {
//                case 0:
//                    List<Map<String, Object>> list = new ArrayList<>();
//                    try {
//                        JSONObject mJSONObject = new JSONObject(data);
//                        if (mJSONObject.getString("success").equals("T")) {
//                            OrderBean orderBean = mGson.fromJson(data, OrderBean.class);
//
//                            for (int i = 0; i < orderBean.getData().size(); i++) {
//                                ArrayList<String> imgList = new ArrayList<>();
//                                Map<String, Object> map = new HashMap<>();
//                                map.put("storename", orderBean.getData().get(i).getStorename());
//                                map.put("payfee", orderBean.getData().get(i).getPayfee());
//                                map.put("oid", orderBean.getData().get(i).getOid());
//                                map.put("sname", orderBean.getData().get(i).getStorename());
//                                map.put("orderstate", orderBean.getData().get(i).getOrderstate());
//                                int num = 0;
//                                for (int j = 0; j < orderBean.getData().get(i).getDetails().size(); j++) {
//                                    imgList.add(orderBean.getData().get(i).getDetails().get(j).getShowimg());
//                                    num += Integer.parseInt(orderBean.getData().get(i).getDetails().get(j).getBuycount());
//                                }
//                                map.put("img", imgList);
//                                map.put("num", num);
//                                list.add(map);
//                            }
//                            //竖直排列、正向排序
//                            recylcerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//                            //添加了一个灰色背景
//                            recylcerview.setBackgroundResource(R.color.background);
//                            recylcerview.setAdapter(new MyOrderAdapter(list, getActivity(), mHandler));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }
//    };

    private void initData() {
//        Map<String, String> param2 = new HashMap<>();
//        param2.put("uid", Config.userBean.getData().getUid());
//        Http.getInstance().init(getActivity(), handler, mGson.toJson(param2), "order/findOrderlist", 0).sendMsg();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_all_order, container, false);
        ButterKnife.bind(this, root);
        initData();
        return root;
    }

}
