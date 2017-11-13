package com.example.nativeMall.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.nativeMall.Bean.MsgCodeBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.google.gson.Gson;
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
public class HotTopicAdapter extends RecyclerView.Adapter<HotTopicAdapter.ViewHolder> implements
        View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;
    private Context mContext;
    private static final String TAG = "HotTopicAdapter";

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public HotTopicAdapter(List<Map<String, Object>> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hot_topic,
                viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int position) {
        //设置头像
       // viewHolder.ivDoctImg.setImageUrl(Config.PIC_URL + mData.get(position).get("ivDoctImg"));
        if (!TextUtils.isEmpty(mData.get(position).get("ivDoctImg").toString())) {
            String URL = Config.PIC_URL + mData.get(position).get("ivDoctImg");
            Util.GetCircleImg(URL,viewHolder.ivDoctImg);
        }
        //
        viewHolder.tvDoctName.setText((String) mData.get(position).get("tvDoctName"));
        viewHolder.tvDepmt.setText((String) mData.get(position).get("tvDepmt"));
        viewHolder.tvDoctLevel.setText((String) mData.get(position).get("tvDoctLevel"));
        viewHolder.tvHosName.setText((String) mData.get(position).get("tvHosName"));
        viewHolder.tvHosLevel.setText((String) mData.get(position).get("tvHosLevel"));
        viewHolder.tvContent.setText((String) mData.get(position).get("tvContent"));
        viewHolder.tvTimeAgo.setText((String) mData.get(position).get("tvTime"));//修改
        viewHolder.tvReadSum.setText("阅读量：" + mData.get(position).get("tvReadSum"));
        viewHolder.tvZanSum.setText((String) mData.get(position).get("tvZan"));
        viewHolder.tvComment.setText((String) mData.get(position).get("tvComment"));
        //判断用户是否点过赞
        if (mData.get(position).get("IsMark").equals("1")){
            viewHolder.cbZan.setChecked(true);
        }

        viewHolder.cbZan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int sum = Integer.valueOf(viewHolder.tvZanSum.getText().toString().trim());
                if (Config.IS_LOG) {
                    if (b) {
                        sum += 1;
                        viewHolder.tvZanSum.setText(String.valueOf(sum));
                        ZanTopicByAsyncClient();
                    } else {
                        sum = sum - 1;
                        viewHolder.tvZanSum.setText(String.valueOf(sum));
                        UnZanopicByAsyncClient();
                    }
                } else {
                    Util.show(mContext);
                    viewHolder.cbZan.setChecked(false);
                }

            }

            private void UnZanopicByAsyncClient() {
                AsyncHttpClient client = new AsyncHttpClient();
                String URL =Config.GLOBAL_URL1 + "topic/unsupport";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("topicId",mData.get(position).get("topicId").toString());
                    jsonObject.put("visitorId",Config.userBean.getData().getUid());
                    jsonObject.put("type","1");
                    jsonObject.put("commentId","0");
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
                            Log.i(TAG, "onSuccess: 取消赞成功" );
                        }
                    }
                });
            }

            private void ZanTopicByAsyncClient() {
                AsyncHttpClient client = new AsyncHttpClient();  //发送点赞到后台
                String URL = Config.GLOBAL_URL1 + "topic/comment";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("topicId", mData.get(position).get("topicId").toString());
                    jsonObject.put("visitorId", Config.userBean.getData().getUid());
                    jsonObject.put("visitorName", Config.userBean.getData().getName());
                    jsonObject.put("support", "1");
                    jsonObject.put("type", "1");
                    jsonObject.put("commentId", "0");//为话题点赞，设置为0，为评论点赞设置为评论人ID
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

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable
                                    throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Log.i(TAG, "onFailure: >>" + "xxx");
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

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SmartImageView ivDoctImg;
        public TextView tvDoctName, tvDepmt, tvDoctLevel, tvHosName, tvHosLevel;
        public TextView tvContent, tvTimeAgo, tvReadSum, tvComment, tvZanSum;
        public CheckBox cbZan;


        public ViewHolder(View view) {
            super(view);
            ivDoctImg = (SmartImageView) view.findViewById(R.id.iv_doct);
            tvDoctName = (TextView) view.findViewById(R.id.tv_doct_name);
            tvDepmt = (TextView) view.findViewById(R.id.tv_depmt);
            tvDoctLevel = (TextView) view.findViewById(R.id.tv_doct_level);
            tvHosName = (TextView) view.findViewById(R.id.tv_hos_name);
            tvHosLevel = (TextView) view.findViewById(R.id.tv_hos_level);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            tvTimeAgo = (TextView) view.findViewById(R.id.tv_timeago);
            tvReadSum = (TextView) view.findViewById(R.id.tv_read_sum);
            cbZan = (CheckBox) view.findViewById(R.id.cb_zan);
            tvZanSum = (TextView) view.findViewById(R.id.tv_zan_sum);
            tvComment = (TextView) view.findViewById(R.id.tv_comment);


        }
    }
}

