package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.nativeMall.Adapter.QuestionListAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.MallTitle;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MyQuestionAct extends AppCompatActivity {

    @BindView(R.id.custom_title)
    MallTitle customTitle;
    @BindView(R.id.rv_question_list)
    RecyclerView rvQuestionList;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionListAdapter questionListAdapter;
    private static final String TAG = "MyQuestionAct";
    private List<Map<String, Object>> listmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question_list);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        customTitle.setLeftRightImgClickListener(new MallTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });

        layoutManager = new LinearLayoutManager(this);
        rvQuestionList.setLayoutManager(layoutManager);
        getQuestionListByAcyncClient();
    }

    private void getQuestionListByAcyncClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "inquisitionInfo/qureyInquisition?userId=" + Config
                .userBean.getData().getUid();
        Log.i(TAG, "getQuestionListByAcyncClient: " + URL);

        client.get(URL, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();

                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("success")){
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("tvTitle", codeMsgMItemsBean.getITEMS().get(j).get("inquisitionBref"));
                        map.put("tvDescribe", codeMsgMItemsBean.getITEMS().get(j).get
                                ("inquisitionRequest"));
                        map.put("tvAnswer", codeMsgMItemsBean.getITEMS().get(j).get
                                ("inquisitionResponse"));
                        listmaps.add(map);

                    }
                    questionListAdapter = new QuestionListAdapter(listmaps);
                    rvQuestionList.setAdapter(questionListAdapter);
                    questionListAdapter.setOnItemClickListener(new QuestionListAdapter
                            .OnRecyclerViewItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            Intent intent = new Intent(MyQuestionAct.this, InquiryDetailAct.class);
                            intent.putExtra("tvTitle",listmaps.get(position).get("tvTitle").toString());
                            intent.putExtra("tvDescribe",listmaps.get(position).get("tvDescribe").toString());
                            intent.putExtra("tvAnswer",listmaps.get(position).get("tvAnswer").toString());
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
}
