package com.example.nativeMall.ui.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.nativeMall.Adapter.MedicineShopAdapter;
import com.example.nativeMall.Bean.MallStoreBean;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.Activity.SupplierActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/6.
 */
public class SearchShopFragment extends Fragment {
    @BindView(R.id.lv_search_shop)
    ListView lvSearchShop;
    @BindView(R.id.iv_search_shop)
    ImageView ivSearchShop;

    private Gson mGson = new Gson();
    JSONObject json = null, json3 = null, dataJson = null;
    JSONArray json2 = null;
    private MallStoreBean mMallStoreBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_shop, container, false);
        Bundle bundle = getArguments();
        String shopName = bundle.getString("name");
        ButterKnife.bind(this, root);

        Map<String, String> search = new HashMap<>();
        search.put("kw", shopName);
        Http.getInstance().init(getActivity(), handler1, mGson.toJson(search), "index/searchStore", 0).sendMsg();

        lvSearchShop.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent();
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("store", mMallStoreBean.getData().getInfo());
            intent.setClass(getActivity(), SupplierActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        });
        return root;
    }

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        json = new JSONObject(data);
                        json2 = json.getJSONArray("data");
                        List<Map<String,String>> data_list = new ArrayList<>();
                        if (json.getString("success") .equals("T")){
                            mMallStoreBean = mGson.fromJson(data,MallStoreBean.class);
                            for (int i = 0;i<json2.length();i++){
                                dataJson = json2.getJSONObject(i);
                                Map<String,String> map = new HashMap<>();
                                map.put("img",dataJson.getString("logo"));
                                map.put("name",dataJson.getString("name"));
                                data_list.add(map);
                            }
                            MedicineShopAdapter medicineShopAdapter = new MedicineShopAdapter(getActivity(),data_list);
                            lvSearchShop.setEmptyView(ivSearchShop);
                            lvSearchShop.setAdapter(medicineShopAdapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
