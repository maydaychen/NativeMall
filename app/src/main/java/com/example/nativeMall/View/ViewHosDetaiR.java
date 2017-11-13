package com.example.nativeMall.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.nativeMall.Adapter.TextAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.Activity.HospDetailAct;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Du on 2016/8/23.
 */
public class ViewHosDetaiR extends RelativeLayout implements ViewBaseAction {

    private static ListView lvWorkTime;
    private static OnSelectListener mOnSelectListener;
    private static TextAdapter adapter;
    private static String showText = "不限时间";
    private static Context mContext;
    private static List<String> listWorkTime = new ArrayList<>();
    private static List<String> listWorkTimeId = new ArrayList<>();
    private static final String TAG = "ViewHosDetaiR";
    public static String ChosedWorkTimeId = "";
    public String getShowText() {
        return showText;
    }

    public ViewHosDetaiR(Context context) {
        super(context);
        init(context);
    }

    public ViewHosDetaiR(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewHosDetaiR(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {


        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
       // setBackgroundResource(R.drawable.choosearea_bg_right);
        setBackgroundResource(R.color.white);
        lvWorkTime = (ListView) findViewById(R.id.lv_inpop);
        //getWorkTimeByDoctLevel();
    }
    //改动后不根据DoctLevel ， 直接一次加载
    public static void getWorkTimeByDoctLevel(String DoctLevel) {

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "?" + DoctLevel;//获取DoctLevelList;
        Log.i(TAG, "getDoctLevelList: " + URL);
//        listItemHos.clear();
//        listWorkTime.clear();   一次加载不用清空
//        listWorkTimeId.clear();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String reslut = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(reslut, CodeMsgMItemsBean
                        .class);
                for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                    listWorkTime.add(codeMsgMItemsBean.getITEMS().get(j).get("?").toString()
                    );//获取Level名字
                    listWorkTimeId.add(codeMsgMItemsBean.getITEMS().get(j).get("？").toString
                            ());//获取id
                }
                adapter = new TextAdapter(mContext, listWorkTime, R.color.white, R.color
                        .divide_line);

                lvWorkTime.setAdapter(adapter);
                adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        HospDetailAct.listDoctInfo.clear();
                        HospDetailAct.doctListAdapter.notifyDataSetChanged();
                        if (mOnSelectListener != null) {
                            showText = listWorkTime.get(position);
                            mOnSelectListener.getValue(listWorkTime.get(position),listWorkTimeId.get(position));
                            ChosedWorkTimeId = listWorkTimeId.get(position);
                        }
                    }
                });
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "onFailure: 网络异常");
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String showText, String workTimeId);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}




