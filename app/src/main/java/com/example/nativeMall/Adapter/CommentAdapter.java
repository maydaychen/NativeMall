package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.MsgCodeBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by Du on 2016/8/31.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> implements
        View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;
    private static final String TAG = "CommentAdapter";
    private Context mContext;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public CommentAdapter(List<Map<String, Object>> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment,
                viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.ivUserImg.setImageUrl(Config.PIC_URL + mData.get(position).get("ivUserImg"));
        viewHolder.tvUserName.setText((String) mData.get(position).get("tvUserName"));
        viewHolder.tvTimeAgo.setText((String) mData.get(position).get("tvTimAgo"));
        viewHolder.tvZanSum.setText((String) mData.get(position).get("tvZanSum"));
        viewHolder.tvContent.setText((String) mData.get(position).get("tvContent"));
        if (mData.get(position).get("IsMark").equals("1")){
            viewHolder.cbZan.setChecked(true);
        }
        viewHolder.cbZan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (Config.IS_LOG) {
                    int sum = Integer.valueOf(viewHolder.tvZanSum.getText().toString());
                    if (b) {
                        sum += 1;
                        viewHolder.tvZanSum.setText(String.valueOf(sum));
                        ZanVisitorByAsyncClient();
                    } else {
                        sum = sum - 1;
                        viewHolder.tvZanSum.setText(String.valueOf(sum));
                        UnZanVisitorByAsynClient();
                    }

                } else {
                    Util.show(mContext);
                    viewHolder.cbZan.setChecked(false);
                }
            }

            private void UnZanVisitorByAsynClient() {
                AsyncHttpClient client = new AsyncHttpClient();
                String URL = Config.GLOBAL_URL1 + "topic/unsupport";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("topicId", mData.get(position).get("topicId"));
                    jsonObject.put("visitorId", Config.userBean.getData().getUid());
                    //jsonObject.put("visitorName", Config.userBean.getData().getName());
                    jsonObject.put("type", "1");
                    jsonObject.put("commentId", mData.get(position).get("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
                client.post(mContext,URL,entity,"application/json",new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Gson gson = new Gson();
                        MsgCodeBean msgCodeBean = gson.fromJson(response.toString(),MsgCodeBean.class);
                        if (msgCodeBean.getMSG().equals("success")){
                            Toast.makeText(mContext,"取消点赞成功",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }

            private void ZanVisitorByAsyncClient() {
                AsyncHttpClient client = new AsyncHttpClient();
                String URL = Config.GLOBAL_URL1 + "topic/comment";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("topicId", mData.get(position).get("topicId"));
                    jsonObject.put("visitorId", Config.userBean.getData().getUid());
                   // jsonObject.put("visitorName", Config.userBean.getData().getName());
                    jsonObject.put("support", "1");
                    jsonObject.put("type", "1");
                    jsonObject.put("commentId", mData.get(position).get("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ByteArrayEntity entity = null;
                try {
                    entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
                    entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
                            "application/json"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                client.post(mContext, URL, entity, "application/json", new
                        JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject
                                    response) {
                                super.onSuccess(statusCode, headers, response);
                                JsonObject jsonObject1 = new JsonParser().parse(response.toString()).getAsJsonObject();
                                if (jsonObject1.get("MSG").equals("success")){
                                    Toast.makeText(mContext,"点赞成功",Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable
                                    throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Log.i(TAG, "onFailure: 111");
                            }
                        });
            }
        });

        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public SmartImageView ivUserImg;
        public TextView tvUserName;
        public TextView tvTimeAgo;
        public TextView tvZanSum;
        public TextView tvContent;
        public CheckBox cbZan;


        public ViewHolder(View view) {
            super(view);
            ivUserImg = (SmartImageView) view.findViewById(R.id.iv_user);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvTimeAgo = (TextView) view.findViewById(R.id.tv_timeago);
            tvZanSum = (TextView) view.findViewById(R.id.tv_zan_sum);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            cbZan = (CheckBox) view.findViewById(R.id.cb_zan);
        }
    }
}

