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
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Du on 2016/8/22.
 */
public class ViewDoctListR extends RelativeLayout implements ViewBaseAction {

    private static ListView lvDepmt;

    private static OnSelectListener mOnSelectListener;
    private static TextAdapter adapter;
    private static String showText = "内科";
    private static Context mContext;
    private static List<String> listItemsdDepmtName = new ArrayList<>();
    private static List<String> listItemsdDepmtId = new ArrayList<>();
    private static final String TAG = "ViewDoctListR";

    public String getShowText() {
        return showText;
    }

    public ViewDoctListR(Context context) {
        super(context);
        init(context);
    }

    public ViewDoctListR(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewDoctListR(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {


        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
       // setBackgroundResource(R.drawable.choosearea_bg_right);
        setBackgroundResource(R.color.white);
        lvDepmt = (ListView) findViewById(R.id.lv_inpop);


    }

    public static void getDepmtByCityAndHos(String HosId) {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 +
                "departmentInfo/inquiryFirstDeptByhospitalId?hospitalId=" + HosId; //通过HosId 列出科室列表
        client.get(URL, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                listItemsdDepmtName.clear();
                listItemsdDepmtId.clear();
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                    listItemsdDepmtName.add(codeMsgMItemsBean.getITEMS().get(j).get("deptName")
                            .toString());
                    listItemsdDepmtId.add(codeMsgMItemsBean.getITEMS().get(j).get("deptId").toString
                            ());
                }
                adapter = new TextAdapter(mContext, listItemsdDepmtName, R.color.white, R
                        .color.divide_line);

                lvDepmt.setAdapter(adapter);
                adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        if (mOnSelectListener != null) {
                            showText = listItemsdDepmtName.get(position);
                            mOnSelectListener.getValue(listItemsdDepmtName.get(position),
                                    listItemsdDepmtId.get(position), ViewDoctListM.ChosedHosId);
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
        void getValue(String showText, String DepmtId, String HosId);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}



