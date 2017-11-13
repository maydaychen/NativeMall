package com.example.nativeMall.ui.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.HotTopicAdapter;
import com.example.nativeMall.Adapter.RecommendDoctAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Bean.MapBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Activity.DoctDateDetailAct;
import com.example.nativeMall.ui.Activity.HotTopicAct;
import com.example.nativeMall.ui.Activity.HotTopicDetailAct;
import com.example.nativeMall.ui.Activity.MessageCenterActivity;
import com.example.nativeMall.ui.Activity.OnekeyRegAct;
import com.example.nativeMall.ui.Activity.OnlineInquiryAct;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegFragment extends Fragment implements View.OnClickListener {

    private static final String EXTRA_CONTENT = "content";
    private RelativeLayout rlOnekeyReg, rlOnlineInquiry, rlHotTopic;
    private RecyclerView rvRecommendDoct, rvHotopic;
    private CustomTitle customTitle;

    private HotTopicAdapter hotTopicAdapter = null;
    private RecommendDoctAdapter recommendDoctAdapter;
    private RecyclerView.LayoutManager layoutManager1, layoutManager2;
    private static final String TAG = "MainActivity";
    private List<MapBean> listRecomDoctbean = new ArrayList<>();
    private List<MapBean> listTopicDoctbean = new ArrayList<>();
    private List<Map<String, Object>> listHotTopic = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg, container, false);
        InitView(view);
        getRecommendDoct();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Config.IS_LOG) {
            listHotTopic.clear();
            if (hotTopicAdapter != null) {
                hotTopicAdapter.notifyDataSetChanged();
            }
            getHotTopicDataByUid();
        } else {
            getHotTopicDataByNothing();
        }

    }

    private void InitView(View view) {
        customTitle = (CustomTitle) view.findViewById(R.id.custom_title);
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {

            }

            @Override
            public void rightClick(Boolean click) {
                Intent intent = new Intent(getActivity(), MessageCenterActivity.class);
                startActivity(intent);
            }
        });

        rlOnekeyReg = (RelativeLayout) view.findViewById(R.id.rl_onekey_reg);
        rlOnlineInquiry = (RelativeLayout) view.findViewById(R.id.rl_online_inquiry);
        rlHotTopic = (RelativeLayout) view.findViewById(R.id.rl_hot_topic);
        rvRecommendDoct = (RecyclerView) view.findViewById(R.id.rv_recommend_doct);

        layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                false);
        rvRecommendDoct.setLayoutManager(layoutManager1);

        rvHotopic = (RecyclerView) view.findViewById(R.id.rv_hot_topic);
//        layoutManager2 = new LinearLayoutManager(getActivity()) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
        layoutManager2 = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvHotopic.setLayoutManager(layoutManager2);
        rvHotopic.setHasFixedSize(true);
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(0xFFEEEEEE);
        // dividerLine.setColor(Color.parseColor("#ff7272"));
        rvHotopic.addItemDecoration(dividerLine);


        rlOnekeyReg.setOnClickListener(this);
        rlOnlineInquiry.setOnClickListener(this);
        rlHotTopic.setOnClickListener(this);
    }

    public static RegFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        RegFragment regFragment = new RegFragment();
        regFragment.setArguments(arguments);
        return regFragment;
    }

    private void getRecommendDoct() {

        listRecomDoctbean.clear();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "recommendDoctorInfo/getAllDoctor";
        Log.i(TAG, "getRecommendDoct: " + URL);
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                Log.i(TAG, "onSuccess: " + result);
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("success")) {
                    List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ivDoctImg", codeMsgMItemsBean.getITEMS().get(j).get("doctorUrl"));
                        map.put("tvDoctName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("doctorName"));
                        map.put("tvDepmt", codeMsgMItemsBean.getITEMS().get(j).get
                                ("firstDeptName"));
                        listmaps.add(map);
                        MapBean mapBean = new MapBean();
                        mapBean.setMap(codeMsgMItemsBean.getITEMS().get(j));
                        listRecomDoctbean.add(mapBean);

                    }
                    recommendDoctAdapter = new RecommendDoctAdapter(listmaps);
                    rvRecommendDoct.setAdapter(recommendDoctAdapter);
                    recommendDoctAdapter.setOnItemClickListener(new RecommendDoctAdapter
                            .OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            MapBean mapBean = listRecomDoctbean.get(position);

                            Log.i(TAG, "onItemClick: " + mapBean.getMap().get("doctorName"));
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("DoctMsg", mapBean);
                            Intent intent = new Intent(getActivity(), DoctDateDetailAct.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getHotTopicDataByNothing() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "topic/inquiryhottopics";
        listHotTopic.clear();
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("success")) {
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ivDoctImg", codeMsgMItemsBean.getITEMS().get(j).get("doctorUrl"));
                        map.put("tvDoctName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("doctorName"));
                        map.put("tvDepmt", codeMsgMItemsBean.getITEMS().get(j).get
                                ("firstDeptName"));
                        map.put("tvDoctLevel", codeMsgMItemsBean.getITEMS().get(j).get
                                ("doctorLevel"));
                        map.put("tvHosName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalName"));
                        map.put("tvHosLevel", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalLevel"));
                        map.put("tvContent", codeMsgMItemsBean.getITEMS().get(j).get("brief"));
                        map.put("tvTime", Util.changeDateToStandard(codeMsgMItemsBean.getITEMS().get
                                (j).get("createDate").toString()));
                        map.put("tvReadSum", codeMsgMItemsBean.getITEMS().get(j).get("visitCount"));
                        map.put("tvZan", codeMsgMItemsBean.getITEMS().get(j).get("supportCount"));
                        map.put("tvComment", codeMsgMItemsBean.getITEMS().get(j).get
                                ("commentCount"));
                        map.put("topicId", codeMsgMItemsBean.getITEMS().get(j).get("id"));
                        map.put("IsMark", codeMsgMItemsBean.getITEMS().get(j).get("mark"));
                        Log.i(TAG, "onSuccess: " + map.get("IsMark"));
                        listHotTopic.add(map);
                        MapBean mapBean = new MapBean();
                        mapBean.setMap(codeMsgMItemsBean.getITEMS().get(j));
                        listTopicDoctbean.add(mapBean);

                    }
                    Log.i(TAG, "onSuccess: " + listHotTopic);
                    hotTopicAdapter = new HotTopicAdapter(listHotTopic, getActivity());
                    rvHotopic.setAdapter(hotTopicAdapter);
                    hotTopicAdapter.setOnItemClickListener(new HotTopicAdapter
                            .OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //修改本Act中的阅读量+1;
                            listHotTopic.get(position).put("tvReadSum", (Integer.parseInt
                                    (listHotTopic.get(position).get("tvReadSum").toString()) + 1)
                                    + "");
                            hotTopicAdapter.notifyDataSetChanged();
                            //
                            MapBean mapBean = listTopicDoctbean.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("TopicMsg", mapBean);
                            Intent intent = new Intent(getActivity(), HotTopicDetailAct.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getHotTopicDataByUid() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "topic/inquiryhottopics?userId=" + Config.userBean
                .getData().getUid();

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("success")) {
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("ivDoctImg", codeMsgMItemsBean.getITEMS().get(j).get("doctorUrl"));
                        map.put("tvDoctName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("doctorName"));
                        map.put("tvDepmt", codeMsgMItemsBean.getITEMS().get(j).get
                                ("firstDeptName"));
                        map.put("tvDoctLevel", codeMsgMItemsBean.getITEMS().get(j).get
                                ("doctorLevel"));
                        map.put("tvHosName", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalName"));
                        map.put("tvHosLevel", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalLevel"));
                        map.put("tvContent", codeMsgMItemsBean.getITEMS().get(j).get("brief"));
                        map.put("tvTime", Util.changeDateToStandard(codeMsgMItemsBean.getITEMS().get
                                (j).get("createDate").toString()));
                        map.put("tvReadSum", codeMsgMItemsBean.getITEMS().get(j).get("visitCount"));
                        map.put("tvZan", codeMsgMItemsBean.getITEMS().get(j).get("supportCount"));
                        map.put("tvComment", codeMsgMItemsBean.getITEMS().get(j).get
                                ("commentCount"));
                        map.put("topicId", codeMsgMItemsBean.getITEMS().get(j).get("id"));
                        map.put("IsMark", codeMsgMItemsBean.getITEMS().get(j).get("mark"));
                        listHotTopic.add(map);
                        MapBean mapBean = new MapBean();
                        mapBean.setMap(codeMsgMItemsBean.getITEMS().get(j));
                        listTopicDoctbean.add(mapBean);

                    }
                    Log.i(TAG, "onSuccess: " + listHotTopic);
                    hotTopicAdapter = new HotTopicAdapter(listHotTopic, getActivity());
                    rvHotopic.setAdapter(hotTopicAdapter);
                    hotTopicAdapter.setOnItemClickListener(new HotTopicAdapter
                            .OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //修改本Act中的阅读量+1;
                            listHotTopic.get(position).put("tvReadSum", (Integer.parseInt
                                    (listHotTopic.get(position).get("tvReadSum").toString()) + 1)
                                    + "");
                            hotTopicAdapter.notifyDataSetChanged();
                            //
                            MapBean mapBean = listTopicDoctbean.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("TopicMsg", mapBean);
                            Intent intent = new Intent(getActivity(), HotTopicDetailAct.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_onekey_reg:
                Intent intent = new Intent(getActivity(), OnekeyRegAct.class);
                startActivity(intent);
                break;

            case R.id.rl_online_inquiry:
                Intent intent1 = new Intent(getActivity(), OnlineInquiryAct.class);
                startActivity(intent1);
                break;

            case R.id.rl_hot_topic:
                Intent intent3 = new Intent(getActivity(), HotTopicAct.class);
                startActivity(intent3);
                break;

        }
    }

}
