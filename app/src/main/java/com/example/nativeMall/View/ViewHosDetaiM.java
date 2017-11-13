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
public class ViewHosDetaiM extends RelativeLayout implements ViewBaseAction {

    private static ListView lvDoctLevel;
    private static OnSelectListener mOnSelectListener;
    private static TextAdapter adapter;
    private static String showText = "全部医生";
    private static Context mContext;
    private static List<String> listDoctLevelName = new ArrayList<>();
    private static List<String> listDoctLevelNameId = new ArrayList<>();
    public static String ChosedLevelId = ""; //默认未选
    private static final String TAG = "ViewHosDetaiM";

    public String getShowText() {
        return showText;
    }

    public ViewHosDetaiM(Context context) {
        super(context);
        init(context);
    }

    public ViewHosDetaiM(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewHosDetaiM(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
        //setBackgroundResource(R.drawable.choosearea_bg_mid);
        setBackgroundResource(R.color.white);
        lvDoctLevel = (ListView) findViewById(R.id.lv_inpop);
        getDoctLevelList();
    }
    //
    public static void getDoctLevelList() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "doctorInfo/inquirydoctorlevel" ;//获取DoctLevelList;
        Log.i(TAG, "getDoctLevelList: " + URL);
//        listItemHos.clear();
//        listDoctLevelName.clear();  一次加载不用清空
//        listDoctLevelNameId.clear();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String reslut = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(reslut, CodeMsgMItemsBean
                        .class);
                for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                    listDoctLevelName.add(codeMsgMItemsBean.getITEMS().get(j).get("label").toString()
                    );//获取Level名字
                    listDoctLevelNameId.add(codeMsgMItemsBean.getITEMS().get(j).get("value").toString
                            ());//获取id
                }
                adapter = new TextAdapter(mContext, listDoctLevelName, R.color.white, R.color
                        .divide_line);

                lvDoctLevel.setAdapter(adapter);
                adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        HospDetailAct.listDoctInfo.clear();
                        HospDetailAct.doctListAdapter.notifyDataSetChanged();
                        if (mOnSelectListener != null) {
                            showText = listDoctLevelName.get(position);
                            mOnSelectListener.getValue(listDoctLevelName.get(position),
                                    listDoctLevelNameId.get(position));
                            ChosedLevelId = listDoctLevelNameId.get(position);
                            // ViewHosDetaiR.getWorkTimeByDoctLevel(ChosedLevelId);
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
        void getValue(String showText, String LevelId);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}



