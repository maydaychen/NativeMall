package com.example.nativeMall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nativeMall.R;
import com.example.nativeMall.ui.Activity.DateMsgAct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Du on 2016/8/25.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> implements
        View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;
    private Context mContext;
    private static final String TAG = "ScheduleAdapter";
    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public ScheduleAdapter(List<Map<String, Object>> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule,
                viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String date = mData.get(position).get("workTime").toString().substring(5, 10);
        String Oas = null;


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date temp = sdf.parse(mData.get(position).get("workTime").toString());

            Oas = new SimpleDateFormat("EEEE").format(temp).toString();
            Log.i(TAG, "onBindViewHolder: " + Oas);
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        viewHolder.day.setText((String) mData.get(position).get("workTime"));
        viewHolder.day.setText( date + "\n" + Oas);
        viewHolder.morning.setText(mData.get(position).get("visiTypeAm") + "\n" + mData.get
                (position).get("workPriceAm"));
        viewHolder.afternoon.setText(mData.get(position).get("vistTypePm") + "\n" + mData.get
                (position).get("workPricePm"));
        if (!"\n".equals(viewHolder.morning.getText())) {
            viewHolder.morning.setBackgroundColor(Color.parseColor("#33d54e"));
            viewHolder.morning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, DateMsgAct.class);
                    getDoctMsg(intent, position);
                    intent.putExtra("workTime", mData.get(position).get("workStatrDtAm").toString
                            ());
                    intent.putExtra("headerId", mData.get(position).get("headerIdAm").toString());
                    intent.putExtra("visitType", mData.get(position).get("visiTypeAm").toString());
                    intent.putExtra("workPrice", mData.get(position).get("workPriceAm").toString());
                    mContext.startActivity(intent);
                }
            });
        }
        if (!"\n".equals(viewHolder.afternoon.getText())) {
            viewHolder.afternoon.setBackgroundColor(Color.parseColor("#33d54e"));
            viewHolder.afternoon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DateMsgAct.class);
                    getDoctMsg(intent, position);

                    intent.putExtra("workTime", mData.get(position).get("workStatrDtPm").toString
                            ());
                    intent.putExtra("headerId", mData.get(position).get("headerIdPm").toString());
                    intent.putExtra("visitType", mData.get(position).get("vistTypePm").toString());
                    intent.putExtra("workPrice", mData.get(position).get("workPricePm").toString());
                    mContext.startActivity(intent);
                }
            });
        }


        viewHolder.itemView.setTag(position);
    }

    private void getDoctMsg(Intent intent, int position) {
        intent.putExtra("doctorUrl", mData.get(position).get("doctorUrl").toString());
        intent.putExtra("doctorName", mData.get(position).get("doctorName").toString());
        intent.putExtra("firstDeptName", mData.get(position).get("firstDeptName").toString());
        intent.putExtra("doctorLevel", mData.get(position).get("doctorLevel").toString());
        intent.putExtra("hospitalName", mData.get(position).get("hospitalName").toString());
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
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView day;
        public TextView morning;
        public TextView afternoon;

        public ViewHolder(View view) {
            super(view);
            day = (TextView) view.findViewById(R.id.tv_schedule_day);
            morning = (TextView) view.findViewById(R.id.tv_schedule_morning);
            afternoon = (TextView) view.findViewById(R.id.tv_schedule_afternoon);
        }
    }
}
