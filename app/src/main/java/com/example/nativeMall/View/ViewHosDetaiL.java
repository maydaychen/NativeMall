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
public class ViewHosDetaiL extends RelativeLayout implements ViewBaseAction {

    private ListView lvLeft, lvRight;

    private OnSelectListener mOnSelectListener;
    private TextAdapter adapterLeft, adapterRight;
    private String showText = "科室";
    private Context mContext;
    private List<String> listItemsLeftName = new ArrayList<>();
    private List<String> listItemsRightName = new ArrayList<>();
    private List<String> listItemsLeftId = new ArrayList<>();
    private List<String> listItemsRightId = new ArrayList<>();
    public static String ChosedDepmtId = "";
    public static String ChosedDepmtName;
    private static final String TAG = "ViewHosDetaiL";

    public String getShowText() {
        return showText;
    }

    public ViewHosDetaiL(Context context) {
        super(context);
        init(context);
    }

    public ViewHosDetaiL(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewHosDetaiL(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {


        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_left_right, this, true);
        //setBackgroundResource(R.drawable.choosearea_bg_left);
        setBackgroundResource(R.color.white);
        lvLeft = (ListView) findViewById(R.id.lv_left);
        lvRight = (ListView) findViewById(R.id.lv_right);
        getDepmtByAsyncClient();


    }

    private void getDepmtByAsyncClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 +
                "departmentInfo/inquiryFirstDeptByhospitalId?hospitalId=" +
                HospDetailAct.HosId;
        Log.i(TAG, "getDepmtByAsyncClient: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                listItemsLeftName.clear();
                listItemsLeftId.clear();

                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);
                if (codeMsgMItemsBean.getMSG().equals("SUCCESS")) {
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        listItemsLeftName.add(codeMsgMItemsBean.getITEMS().get(j).get("deptName")
                                .toString());
                        listItemsLeftId.add(codeMsgMItemsBean.getITEMS().get(j).get("deptId")
                                .toString());
                    }
                    Log.i(TAG, "onSuccess: " + listItemsLeftName);
                    adapterLeft = new TextAdapter(mContext, listItemsLeftName, R.color.white, R
                            .color
                            .divide_line);
                    lvLeft.setAdapter(adapterLeft);
                    adapterLeft.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            AsyncHttpClient client1 = new AsyncHttpClient();
                            String URL = Config.GLOBAL_URL1 +
                                    "departmentInfo/inquirySecondDeptByFirstDeptId?firstDeptCode" +
                                    "=" + listItemsLeftId.get(position);//根据一级ID获得二级ID
                            Log.i(TAG, "onItemClick: " + URL);

                            client1.get(URL, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                    listItemsRightName.clear();
                                    listItemsRightId.clear();
                                    String result = new String(bytes);
                                    Gson gson1 = new Gson();
                                    CodeMsgMItemsBean codeMsgMItemsBean1 = gson1.fromJson
                                            (result, CodeMsgMItemsBean.class);
                                    if (codeMsgMItemsBean1.getMSG().equals("SUCCESS")) {
                                        for (int k = 0; k < codeMsgMItemsBean1.getITEMS()
                                                .size();k++ ) {
                                            listItemsRightName.add(codeMsgMItemsBean1.getITEMS()
                                                    .get(k).get("deptName").toString());
                                            listItemsRightId.add(codeMsgMItemsBean1.getITEMS()
                                                    .get(k).get("deptId").toString());
                                        }
                                        Log.i(TAG, "onSuccess: " + listItemsRightName);
                                        adapterRight = new TextAdapter(mContext,
                                                listItemsRightName, R.color.white, R.color
                                                .white);
                                        lvRight.setAdapter(adapterRight);
                                        adapterRight.setOnItemClickListener(new TextAdapter
                                                .OnItemClickListener() {

                                            @Override
                                            public void onItemClick(View view, int position) {
                                                HospDetailAct.listDoctInfo.clear();
                                                HospDetailAct.doctListAdapter.notifyDataSetChanged();
                                                if (mOnSelectListener != null) {
                                                    showText = listItemsRightName.get(position);
                                                    mOnSelectListener.getValue(listItemsRightName
                                                            .get(position), listItemsRightId.get
                                                            (position));
                                                    ChosedDepmtId = listItemsRightId
                                                            .get(position);
                                                    ChosedDepmtName = listItemsRightName.get(position);
                                                    Log.i(TAG, "onItemClick: >" + ChosedDepmtId);
                                                    //  ViewHosDetaiM.getDoctLevelList(ChosedDepmtId);
                                                }
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes,
                                                      Throwable throwable) {
                                    Log.i(TAG, "onFailure: >>>");
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "onFailure: ????");
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String showText, String SecondDepmtId);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}
