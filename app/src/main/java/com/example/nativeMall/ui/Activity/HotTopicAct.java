package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.HotTopicAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Bean.MapBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class HotTopicAct extends AppCompatActivity {


    @BindView(R.id.rv_hot_topic_list)
    RecyclerView rvHotTopicList;

    private RecyclerView.LayoutManager layoutManager;
    private HotTopicAdapter hotTopicAdapter;
    private List<MapBean> mapBeanList = new ArrayList<>();
    private static final String TAG = "HotTopicAct";
    private List<Map<String, Object>> listTopic = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_topic);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        InitViewAndListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        listTopic.clear();
        if (hotTopicAdapter != null) {
            hotTopicAdapter.notifyDataSetChanged();
        }
        getHotTopickListByAsycClient();
    }

    private void InitViewAndListener() {

        layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(0xFFEEEEEE);
        rvHotTopicList.addItemDecoration(dividerLine);
        rvHotTopicList.setLayoutManager(layoutManager);

    }

    private void getHotTopickListByAsycClient() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "topic/inquiryhottopics";
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();

                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);

                if (codeMsgMItemsBean.getMSG().equals("success")){
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ivDoctImg", codeMsgMItemsBean.getITEMS().get(j).get("doctorUrl"));
                        map.put("tvDoctName", codeMsgMItemsBean.getITEMS().get(j).get("doctorName"));
                        map.put("tvDepmt", codeMsgMItemsBean.getITEMS().get(j).get("firstDeptName"));
                        map.put("tvDoctLevel", codeMsgMItemsBean.getITEMS().get(j).get("doctorLevel"));
                        map.put("tvHosName", codeMsgMItemsBean.getITEMS().get(j).get("hospitalName"));
                        map.put("tvHosLevel", codeMsgMItemsBean.getITEMS().get(j).get("hospitalLevel"));
                        map.put("tvContent", codeMsgMItemsBean.getITEMS().get(j).get("brief"));
                        map.put("tvDempt", codeMsgMItemsBean.getITEMS().get(j).get("createDate"));//修改
                        map.put("tvReadSum", codeMsgMItemsBean.getITEMS().get(j).get("visitCount"));
                        map.put("tvZan", codeMsgMItemsBean.getITEMS().get(j).get("supportCount"));
                        map.put("tvComment", codeMsgMItemsBean.getITEMS().get(j).get("commentCount"));
                        map.put("topicId", codeMsgMItemsBean.getITEMS().get(j).get("id"));
                        map.put("IsMark",codeMsgMItemsBean.getITEMS().get(j).get("id"));
                        Log.i(TAG, "onSuccess: " + map.get("topicId"));
                        listTopic.add(map);
                        MapBean mapBean = new MapBean();
                        mapBean.setMap(codeMsgMItemsBean.getITEMS().get(j));
                        mapBeanList.add(mapBean);

                    }
                    hotTopicAdapter = new HotTopicAdapter(listTopic, HotTopicAct.this);
                    rvHotTopicList.setAdapter(hotTopicAdapter);
                    hotTopicAdapter.setOnItemClickListener((view, position) -> {
                        MapBean mapBean = mapBeanList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("TopicMsg", mapBean);
                        Intent intent = new Intent(HotTopicAct.this, HotTopicDetailAct.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    });
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }


    @OnClick(R.id.iv_ask_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ask_back:
                finish();
                break;

        }
    }

}
