package com.example.nativeMall.ui.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.CommentAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Bean.CodeMsgSItemsBean;
import com.example.nativeMall.Bean.MapBean;
import com.example.nativeMall.Bean.MsgCodeBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.example.nativeMall.ui.widget.RecycleViewDivider;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class HotTopicDetailAct extends AppCompatActivity {


    @BindView(R.id.rv_commentlist)
    RecyclerView rvCommentlist;
    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.iv_doct)
    SmartImageView ivDoct;
    @BindView(R.id.tv_doct_name)
    TextView tvDoctName;
    @BindView(R.id.tv_depmt)
    TextView tvDepmt;
    @BindView(R.id.tv_doct_level)
    TextView tvDoctLevel;
    @BindView(R.id.tv_hos_name)
    TextView tvHosName;
    @BindView(R.id.tv_hos_level)
    TextView tvHosLevel;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_timeago)
    TextView tvTimeago;
    @BindView(R.id.tv_read_sum)
    TextView tvReadSum;
    @BindView(R.id.et_write_comment)
    EditText etWriteComment;

    private RecyclerView.LayoutManager layoutManager;
    private CommentAdapter commentAdapter;
    private static final String TAG = "HotTopicDetailAct";
    private MapBean topicMsg;
    private ProgressDialog progressDialog = null;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_topic_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        mContext = this;
        Intent intent = getIntent();
        topicMsg = intent.getParcelableExtra("TopicMsg");
        InitViewAndListener();
        InitDoctMsg(topicMsg);
        Log.i(TAG, "onCreate: " + topicMsg.getMap().get("doctorName"));
       if (Config.IS_LOG){
           Log.i(TAG, "onCreate: "  + Config.userBean.getData().getUid());
       }
        sendReadSumByAsyncClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.IS_LOG){
            getCommentVisiterByUserId();

        }else {
            getCommentVisiterByNothing();
        }
    }



    //增加一次阅读量
    private void sendReadSumByAsyncClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "topic/visit?id=" + topicMsg.getMap().get("id");
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                MsgCodeBean msgCodeBean = gson.fromJson(result,MsgCodeBean.class);
                //可以输出是否增加阅读量成功
                if (msgCodeBean.getMSG().equals("success")) {
                    getContentByAsycHttpClient();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }


    private void getCommentVisiterByUserId() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "topic/inquirytopiccommentbytopicid?topicId=" + topicMsg
                .getMap().get("id") + "&userId=" + Config.userBean.getData().getUid();
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();

                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
                for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ivUserImg", codeMsgMItemsBean.getITEMS().get(j).get("visitorUrl"));
                    map.put("tvUserName", codeMsgMItemsBean.getITEMS().get(j).get("visitorName"));
                    map.put("tvTimAgo", Util.changeDateToStandard(codeMsgMItemsBean.getITEMS().get(j).get("createDate").toString()));
                    map.put("tvZanSum", codeMsgMItemsBean.getITEMS().get(j).get("supportCount"));
                    map.put("tvContent", codeMsgMItemsBean.getITEMS().get(j).get("comment"));
                    map.put("id", codeMsgMItemsBean.getITEMS().get(j).get("id"));//待！
                    map.put("topicId", topicMsg.getMap().get("id"));
                    map.put("IsMark",codeMsgMItemsBean.getITEMS().get(j).get("mark"));
                    map.put("visitorId",codeMsgMItemsBean.getITEMS().get(j).get("visitorId"));
                    listmaps.add(map);
                    Log.i(TAG, "onSuccess: " + codeMsgMItemsBean.getITEMS().get(j).get("mark"));
                }
                // Log.i(TAG, "onSuccess: " + listmaps.get(1).get("tvContent").toString());
                commentAdapter = new CommentAdapter(listmaps, HotTopicDetailAct.this);
                rvCommentlist.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(HotTopicDetailAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCommentVisiterByNothing() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "topic/inquirytopiccommentbytopicid?topicId=" + topicMsg
                .getMap().get("id");
//        Log.i(TAG, "getCommentVisiterByNothing: " + URL);

        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();

                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("success")){
                    List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("ivUserImg", codeMsgMItemsBean.getITEMS().get(j).get("visitorUrl"));
                        map.put("tvUserName", codeMsgMItemsBean.getITEMS().get(j).get("visitorName"));
                        map.put("tvTimAgo", Util.changeDateToStandard(codeMsgMItemsBean.getITEMS().get(j).get("createDate").toString()));
                        map.put("tvZanSum", codeMsgMItemsBean.getITEMS().get(j).get("supportCount"));
                        map.put("tvContent", codeMsgMItemsBean.getITEMS().get(j).get("comment"));
                        map.put("id", codeMsgMItemsBean.getITEMS().get(j).get("id"));//待！
                        map.put("topicId", topicMsg.getMap().get("id"));
                        map.put("IsMark",codeMsgMItemsBean.getITEMS().get(j).get("mark"));
                        map.put("visitorId",codeMsgMItemsBean.getITEMS().get(j).get("visitorId"));
                        listmaps.add(map);
                    }
                    // Log.i(TAG, "onSuccess: " + listmaps.get(1).get("tvContent").toString());
                    commentAdapter = new CommentAdapter(listmaps, HotTopicDetailAct.this);
                    rvCommentlist.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(HotTopicDetailAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getContentByAsycHttpClient() {
        //获取医生话题的内容
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String URL = Config.GLOBAL_URL1 + "topic/inquirytopicbyid?id=" + topicMsg.getMap().get("id");

        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();

                CodeMsgSItemsBean codeMsgSItemsBean = gson.fromJson(result, CodeMsgSItemsBean
                        .class);
                if (codeMsgSItemsBean.getMSG().equals("success")){
                    tvContent.setText(codeMsgSItemsBean.getITEMS().get("content").toString());
                    tvReadSum.setText("阅读量：" + codeMsgSItemsBean.getITEMS().get("visitCount").toString());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(HotTopicDetailAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void InitDoctMsg(MapBean topicMsg) {
        ivDoct.setImageUrl(Config.PIC_URL + topicMsg.getMap().get("doctorUrl"));
        tvDoctName.setText(topicMsg.getMap().get("doctorName").toString());
        tvDepmt.setText(topicMsg.getMap().get("firstDeptName").toString());
        tvDoctLevel.setText(topicMsg.getMap().get("doctorLevel").toString());
        tvHosName.setText(topicMsg.getMap().get("hospitalName").toString());
        tvHosLevel.setText(topicMsg.getMap().get("hospitalLevel").toString());
        tvTimeago.setText(Util.changeDateToStandard(topicMsg.getMap().get("createDate").toString()));
    }

    private void InitViewAndListener() {
        layoutManager = new LinearLayoutManager(this);
        rvCommentlist.setLayoutManager(layoutManager);
        rvCommentlist.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });

    }


    @OnClick(R.id.btn_send)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                if (Config.IS_LOG) {
                    if (TextUtils.isEmpty(etWriteComment.getText().toString())) {
                        Toast.makeText(HotTopicDetailAct.this, "请填写评论内容", Toast.LENGTH_SHORT)
                                .show();
                    } else {

                        progressDialog = ProgressDialog.show(HotTopicDetailAct.this, "上传评论",
                                "正在上传，请稍等");
                        sendCommentByAsyncClient(); //发送评论

                    }
                } else {
                    Util.show(HotTopicDetailAct.this);
                }
                break;

        }
    }


    private void sendCommentByAsyncClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "topic/comment";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("topicId", topicMsg.getMap().get("id"));
            jsonObject.put("visitorId", Config.userBean.getData().getUid());
            jsonObject.put("visitorName", Config.userBean.getData().getName());
            jsonObject.put("comment", etWriteComment.getText().toString());
            jsonObject.put("support", "0");
            jsonObject.put("type", "0");
            jsonObject.put("commentId", "0");//为话题点赞，设置为0，为评论点赞设置为评论人ID
//            jsonObject.put("visitorUrl",Config.userBean.getData().);//获取用户头像
            jsonObject.put("visitorUrl", "imgservice/userfiles/b3888f2a805b4309967f4c15606994e0" +
                    ".png");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(HotTopicDetailAct.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if (response.get("MSG").equals("success")) {
                                getCommentVisiterByNothing(); //刷新评论Adapter
                                etWriteComment.setText("");
                                progressDialog.dismiss();

                                //关键盘
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                if (imm.isActive()){
                                    imm.hideSoftInputFromWindow(HotTopicDetailAct.this.getCurrentFocus().getWindowToken(),0);
                                }
                                Toast.makeText(HotTopicDetailAct.this,"评论成功！",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                          JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        Toast.makeText(HotTopicDetailAct.this, "评论失败，请检查网络状态！", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

