package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nativeMall.Adapter.FenleiAdapter;
import com.example.nativeMall.Bean.FenleiBean;import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.http.Http;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FenleiActivity extends InitActivity {
    @BindView(R.id.fenleiList)
    ListView fenleiList;
    @BindView(R.id.fenlei_list_right)
    RecyclerView mFenleiListRight;

    private JSONObject json = null, json2 = null, json3 = null;
    private JSONArray jsonArray = null, mJSONArray = null;
    private List<String> left_list = new ArrayList<>();
    private Gson gson = new Gson();
    private FenleiBean fenleiBean;
    int currentItem = 0;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            try {
                switch (msg.what) {
                    case 0:
                        json = new JSONObject(data);
                        jsonArray = json.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            json2 = jsonArray.getJSONObject(i);
                            left_list.add(json2.getString("name"));
                        }
                        fenleiBean = gson.fromJson(data, FenleiBean.class);
                        final FenleiLeftAdapter fenleiLeftAdapter = new FenleiLeftAdapter(FenleiActivity.this, R.layout.layout_fenlei, left_list);
                        fenleiList.setAdapter(fenleiLeftAdapter);
                        fenleiList.setSelection(0);
                        fenleiList.setOnItemClickListener((adapterView, view, i, l) -> {
                            currentItem = i;
                            fenleiList.setAdapter(fenleiLeftAdapter);
                            Map<String, String> param = new HashMap<>();
                            param.put("cateid", fenleiBean.getData().get(i).getCateid());
                            param.put("level", "");
                            Http.getInstance().init(FenleiActivity.this, handler, gson.toJson(param), "address/category", 1).sendMsg();
                        });
                        Map<String, String> param = new HashMap<>();
                        param.put("cateid", fenleiBean.getData().get(0).getCateid());
                        param.put("level", "");
                        Http.getInstance().init(FenleiActivity.this.getApplicationContext(), handler, gson.toJson(param), "address/category", 1).sendMsg();
                        break;
                    case 1:
                        List<Map<String, Object>> list = new ArrayList<>();
                        json = new JSONObject(data);
                        jsonArray = json.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            List<Map<String, Object>> inside_list = new ArrayList<>();
                            json2 = jsonArray.getJSONObject(i);
//                            fenleiRight.add((FenleiRightBean) Util.fromJsonToJava(json3, FenleiRightBean.class));
                            map.put("name", json2.getString("name"));
                            mJSONArray = json2.getJSONArray("children");
                            for (int j = 0; j < mJSONArray.length(); j++) {
                                Map<String, Object> inside_map = new HashMap<>();
                                json3 = mJSONArray.getJSONObject(j);
                                inside_map.put("inside_name", json3.getString("name"));
                                inside_map.put("inside_cateid", json3.getString("cateid"));
                                inside_map.put("inside_path", json3.getString("imgpath"));
                                inside_map.put("title", json3.getString("name"));
                                inside_list.add(inside_map);
                            }
                            map.put("inside_list", inside_list);
                            list.add(map);
                        }
                        mFenleiListRight.setLayoutManager(
                                new LinearLayoutManager(FenleiActivity.this, LinearLayoutManager.VERTICAL, false));
                        FenleiAdapter mFenleiAdapter = new FenleiAdapter(list, FenleiActivity.this);
                        mFenleiListRight.setAdapter(mFenleiAdapter);

                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fenlei);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        Map<String, String> param = new HashMap<>();
        param.put("cateid", "1");
        param.put("level", "level");
        Http.getInstance().init(this, handler, gson.toJson(param), "address/category", 0).sendMsg();
    }

    @OnClick(R.id.iv_choose_doc_back)
    public void onClick() {
        finish();
    }

    class FenleiLeftAdapter extends BaseAdapter {

        private LayoutInflater flater;
        private int resource;
        private TextView text;
        private List mList;

        public FenleiLeftAdapter(Context context, int resource, List objects) {
            flater = LayoutInflater.from(context);
            this.mList = objects;
            this.resource = resource;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = flater.inflate(resource, null);
            text = (TextView) convertView.findViewById(R.id.fenlei_text);
            text.setHeight(Util.dip2px(40, FenleiActivity.this));
            text.setText((String) mList.get(position));
            if (position == currentItem) {
                convertView.setBackgroundResource(R.color.white);
            } else {
                convertView.setBackgroundResource(R.color.lightgray);
            }
            return convertView;
        }


    }
}
