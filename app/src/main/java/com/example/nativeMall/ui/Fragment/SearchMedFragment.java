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
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.MedicineAdapter;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.Activity.GoodsDetailActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Administrator on 2016/7/5 10:48
 * 邮箱：2091320109@qq.com
 */
public class SearchMedFragment extends Fragment {

    @BindView(R.id.lv_search_med)
    ListView lvSearchMed;
    @BindView(R.id.xiaoliang_pic)
    ImageView mXiaoliangPic;
    @BindView(R.id.jiage_pic)
    ImageView mJiagePic;
    @BindView(R.id.pingjia_pic)
    ImageView mPingjiaPic;
    @BindView(R.id.tv_search_med_zonghe)
    TextView mTvSearchMedZonghe;
    @BindView(R.id.tv_search_med_sold)
    TextView mTvSearchMedSold;
    @BindView(R.id.tv_search_med_price)
    TextView mTvSearchMedPrice;
    @BindView(R.id.tv_search_med_comment)
    TextView mTvSearchMedComment;
    @BindView(R.id.iv_search_med)
    ImageView ivSearchMed;
    private String name;
    JSONObject json = null, json3 = null, dataJson = null;
    JSONArray json2 = null;
    private Gson mGson = new Gson();
    private String KIND = "high";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_med, container, false);
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        ButterKnife.bind(this, root);

        Map<String, String> search = new HashMap<>();
        search.put("kw", name);
        search.put("pageNo", "");
        search.put("pageSize", "");
        Http.getInstance().init(getActivity(), handler1, mGson.toJson(search), "index/search", 0).sendMsg();

        return root;
    }

    @OnClick({R.id.tv_search_med_zonghe, R.id.tv_search_med_sold, R.id.tv_search_med_price, R.id.tv_search_med_comment})
    public void onClick(View view) {
        inite();
        switch (view.getId()) {
            case R.id.tv_search_med_zonghe:
                Map<String, String> search = new HashMap<>();
                search.put("kw", name);
                search.put("pageNo", "");
                search.put("pageSize", "");
                mTvSearchMedZonghe.setTextColor(getResources().getColor(R.color.blue));
                Http.getInstance().init(getActivity(), handler1, mGson.toJson(search), "index/search", 0).sendMsg();
                break;
            case R.id.tv_search_med_sold:
                mTvSearchMedSold.setTextColor(getResources().getColor(R.color.blue));
                if (KIND == "high") {
                    Map<String, String> search1 = new HashMap<>();
                    search1.put("kw", name);
                    search1.put("pageNo", "");
                    search1.put("pageSize", "");
                    search1.put("number", KIND);
                    Http.getInstance().init(getActivity(), handler1, mGson.toJson(search1), "index/search", 0).sendMsg();
                    mXiaoliangPic.setImageDrawable(getResources().getDrawable(R.drawable.yisheng_more1));
                    KIND = "low";
                } else {
                    Map<String, String> search1 = new HashMap<>();
                    search1.put("kw", name);
                    search1.put("pageNo", "");
                    search1.put("pageSize", "");
                    search1.put("number", KIND);
                    Http.getInstance().init(getActivity(), handler1, mGson.toJson(search1), "index/search", 0).sendMsg();
                    mXiaoliangPic.setImageDrawable(getResources().getDrawable(R.drawable.yisheng_more2));
                    KIND = "high";
                }

                break;
            case R.id.tv_search_med_price:
                mTvSearchMedPrice.setTextColor(getResources().getColor(R.color.blue));
                mTvSearchMedComment.setTextColor(getResources().getColor(R.color.font_colour_33));
                if (KIND == "high") {
                    Map<String, String> search1 = new HashMap<>();
                    search1.put("kw", name);
                    search1.put("pageNo", "");
                    search1.put("pageSize", "");
                    search1.put("price", KIND);
                    Http.getInstance().init(getActivity(), handler1, mGson.toJson(search1), "index/search", 0).sendMsg();
                    mJiagePic.setImageDrawable(getResources().getDrawable(R.drawable.yisheng_more1));
                    KIND = "low";
                } else {
                    Map<String, String> search1 = new HashMap<>();
                    search1.put("kw", name);
                    search1.put("pageNo", "");
                    search1.put("pageSize", "");
                    search1.put("price", KIND);
                    Http.getInstance().init(getActivity(), handler1, mGson.toJson(search1), "index/search", 0).sendMsg();
                    mJiagePic.setImageDrawable(getResources().getDrawable(R.drawable.yisheng_more2));
                    KIND = "high";
                }
                break;
            case R.id.tv_search_med_comment:
                mTvSearchMedZonghe.setTextColor(getResources().getColor(R.color.font_colour_33));
                mTvSearchMedSold.setTextColor(getResources().getColor(R.color.font_colour_33));
                mTvSearchMedPrice.setTextColor(getResources().getColor(R.color.font_colour_33));
                mTvSearchMedComment.setTextColor(getResources().getColor(R.color.blue));
                if (KIND == "high") {
                    Map<String, String> search1 = new HashMap<>();
                    search1.put("kw", name);
                    search1.put("pageNo", "");
                    search1.put("pageSize", "");
                    search1.put("talk", KIND);
                    Http.getInstance().init(getActivity(), handler1, mGson.toJson(search1), "index/search", 0).sendMsg();
                    mPingjiaPic.setImageDrawable(getResources().getDrawable(R.drawable.yisheng_more1));
                    KIND = "low";
                } else {
                    Map<String, String> search1 = new HashMap<>();
                    search1.put("kw", name);
                    search1.put("pageNo", "");
                    search1.put("pageSize", "");
                    search1.put("talk", KIND);
                    Http.getInstance().init(getActivity(), handler1, mGson.toJson(search1), "index/search", 0).sendMsg();
                    mPingjiaPic.setImageDrawable(getResources()
                            .getDrawable(R.drawable.yisheng_more2));
                    KIND = "high";
                }
                break;
        }
    }

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        json = new JSONObject(data);
                        dataJson = json.getJSONObject("data");
                        json2 = dataJson.getJSONArray("products");
                        List<Map<String, Object>> list = new ArrayList<>();
                        final List<String> detail_list = new ArrayList<>();
                        for (int i = 0; i < json2.length(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            json3 = json2.getJSONObject(i);
                            map.put("NAME", json3.getString("name"));
                            map.put("DRUG_PRICE", json3.getString("shopprice"));
                            map.put("DRUG_SUPPLIER_NAME", json3.getString("sname"));
                            map.put("DRUG_IMG_URL", json3.getString("showimg"));
                            map.put("DRUG_CODE", json3.getString("pid"));
                            map.put("DRUG_SUPPLIER_CODE", json3.getString("storeid"));
                            detail_list.add(json3.getString("pid"));
                            list.add(map);
                        }
                        MedicineAdapter medicineAdapter = new MedicineAdapter(getActivity(), list);
                        lvSearchMed.setAdapter(medicineAdapter);
                        lvSearchMed.setEmptyView(ivSearchMed);
                        lvSearchMed.setOnItemClickListener((adapterView, view, i, l) -> {
                            Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                            intent.putExtra("pid", detail_list.get(i));
                            startActivity(intent);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:

                    break;
                case 2:
                    Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void inite(){
        mTvSearchMedZonghe.setTextColor(getResources().getColor(R.color.font_colour_33));
        mTvSearchMedSold.setTextColor(getResources().getColor(R.color.font_colour_33));
        mTvSearchMedPrice.setTextColor(getResources().getColor(R.color.font_colour_33));
        mTvSearchMedComment.setTextColor(getResources().getColor(R.color.font_colour_33));
        mXiaoliangPic.setImageDrawable(getResources().getDrawable(R.drawable.yisheng_more1));
        mJiagePic.setImageDrawable(getResources().getDrawable(R.drawable.yisheng_more1));
        mPingjiaPic.setImageDrawable(getResources()
                .getDrawable(R.drawable.yisheng_more1));
    }
}
