package com.example.nativeMall.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nativeMall.R;

/**
 * 作者：JTR on 2016/10/14 16:50
 * 邮箱：2091320109@qq.com
 */
public class TimeTextView extends LinearLayout {
    private Context mContext;
    private TextView mDay,mHour,mMin,mMse;
    private long days,hours,minutes,seconds;
    private boolean run = true; //是否启动了
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (run) {
                        if(computeTime()){
                            setUi();
                            handler.sendEmptyMessageDelayed(0, 1000);
                        }
                    }
                    break;
            }
        }
    };
    public TimeTextView(Context context){
        super(context);
        this.mContext = context;
        inflateLayout();
    }


    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        inflateLayout();
    }


    @SuppressLint("NewApi")
    public TimeTextView(Context context, AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        inflateLayout();
    }


    private void inflateLayout(){
        this.addView(LayoutInflater.from(mContext).inflate(R.layout.electricity_time_day, null));
        mDay = (TextView) this.findViewById(R.id.electricity_time_day);
        mHour = (TextView) this.findViewById(R.id.electricity_time_hour);
        mMin = (TextView) this.findViewById(R.id.electricity_time_min);
        mMse = (TextView) this.findViewById(R.id.electricity_time_mse);
    }


    public void setTime(String day,String hour,String minute,String second){
        if(TextUtils.isEmpty(day)){
            days = 0;
        }else{
            days = Long.parseLong(day);
        }


        if(TextUtils.isEmpty(hour)){
            hours = 0;
        }else{
            hours = Long.parseLong(hour);
        }


        if(TextUtils.isEmpty(minute)){
            minutes = 0;
        }else{
            minutes = Long.parseLong(minute);
        }


        if(TextUtils.isEmpty(second)){
            seconds = 0;
        }else{
            seconds = Long.parseLong(second);
        }
        setUi();

        handler.removeMessages(0);

//每隔1秒钟发送一次handler消息

        handler.sendEmptyMessageDelayed(0,1000);
    }


    private void setUi(){
        mDay.setText(String.valueOf(days));
        mHour.setText(String.valueOf(hours));
        mMin.setText(String.valueOf(minutes));
        mMse.setText(String.valueOf(seconds));
    }


    private boolean computeTime(){
        boolean flag = true;
        seconds--;
        if (seconds < 0) {
            minutes--;
            seconds = 59;
            if (minutes < 0) {
                minutes = 59;
                hours--;
                if (hours < 0) {
                    hours = 23;
                    days--;
                    if(days<0){
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }


    public void stopComputeTime(){
        run = false;
    }

}
