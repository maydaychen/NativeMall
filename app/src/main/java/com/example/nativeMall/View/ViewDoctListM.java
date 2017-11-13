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
public class ViewDoctListM extends RelativeLayout implements ViewBaseAction {

    private static ListView lvHospital;
    private static OnSelectListener mOnSelectListener;
    private  static TextAdapter adapter;
    private static String showText = "选择医院";
    private static Context mContext;
    private static List<String> listItemHos = new ArrayList<>();
    private static List<String> listItemHosId = new ArrayList<>();
    public String getShowText() {
        return showText;
    }
    public static String ChosedHosId;
    private static final String TAG = "ViewDoctListM";
    public ViewDoctListM(Context context) {
        super(context);
        init(context);
    }

    public ViewDoctListM(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewDoctListM(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
       // setBackgroundResource(R.drawable.choosearea_bg_mid);
        setBackgroundResource(R.color.white);
        lvHospital = (ListView) findViewById(R.id.lv_inpop);
//        getHosByCity();

    }

    public static void getHosByCity(String ChosedCityId) {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "hospitalInfo/inquiryHospital?hospitalCityid=" + ChosedCityId;//从选择的城市获取医院
        Log.i(TAG, "getHosByCity: " + URL);
        listItemHos.clear();
        listItemHosId.clear();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String reslut = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(reslut,CodeMsgMItemsBean.class);
                for (int j = 0; j < codeMsgMItemsBean.getITEMS().size();j++){
                    listItemHos.add(codeMsgMItemsBean.getITEMS().get(j).get("hospitalName").toString());//获取医院的名字
                    listItemHosId.add(codeMsgMItemsBean.getITEMS().get(j).get("id").toString());//获取医院ID；
                }
                adapter = new TextAdapter(mContext, listItemHos, R.color.white, R.color.divide_line);

                lvHospital.setAdapter(adapter);
                adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        if (mOnSelectListener != null) {
                            showText = listItemHos.get(position);
                            mOnSelectListener.getValue(listItemHos.get(position),listItemHosId.get(position));
                            ChosedHosId = listItemHosId.get(position);
                            ViewDoctListR.getDepmtByCityAndHos(ChosedHosId);
                        }
                    }
                });
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "onFailure: 网络异常" );
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String showText, String HosId);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

}


