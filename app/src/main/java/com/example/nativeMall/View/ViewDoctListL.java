package com.example.nativeMall.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
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
public class ViewDoctListL extends RelativeLayout implements ViewBaseAction {

    private ListView lvLeft, lvRight;
    private OnSelectListener mOnSelectListener;
    private TextAdapter adapterLeft, adapterRight;
    private String showText = "上海市";
    private Context mContext;
    private static final String TAG = "ViewDoctListL";
    private List<String> listItemsName = new ArrayList<>();
    private List<String> listItemsRegionId = new ArrayList<>();
    private List<String> listChildItemName = new ArrayList<>();
    private List<String> listChildItemRegionId = new ArrayList<>();
    public static String ChosedCityId;
    public String getShowText() {
        return showText;
    }

    public ViewDoctListL(Context context) {
        super(context);
        init(context);
        this.mContext = context;
    }

    public ViewDoctListL(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewDoctListL(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_left_right, this, true);
        //setBackgroundResource(R.drawable.choosearea_bg_left);
//        setBackgroundResource(R.color.white);
        lvLeft = (ListView) findViewById(R.id.lv_left);
        lvRight = (ListView) findViewById(R.id.lv_right);
        getCityDataByAsyncClient();


    }

    private void getCityDataByAsyncClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "region/inquiryallprovince";
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                final CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);

                if (codeMsgMItemsBean.getMSG().equals("SUCCESS")) {
                    Log.i(TAG, "onSuccess: parents");
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {

                        listItemsName.add(codeMsgMItemsBean.getITEMS().get(j).get("name")
                                .toString());
                        listItemsRegionId.add(codeMsgMItemsBean.getITEMS().get(j).get("regionid")
                                .toString());
                    }
                }

                adapterLeft = new TextAdapter(mContext, listItemsName, R.color.white, R
                        .color.divide_line);

                lvLeft.setAdapter(adapterLeft);
                adapterLeft.setOnItemClickListener((view, position) -> {


                    AsyncHttpClient client1 = new AsyncHttpClient();
                    String URL1 = Config.GLOBAL_URL1 + "region/inquirycities?regionid=" +
                            listItemsRegionId.get(position);
                    client1.get(URL1, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i1, Header[] headers1, byte[] bytes1) {
                            listChildItemName.clear();
                            listChildItemRegionId.clear();
                            Log.i(TAG, "onSuccess: child");
                            String result1 = new String(bytes1);
                            Gson gson1 = new Gson();
                            CodeMsgMItemsBean codeMsgMItemsBean1 = gson1.fromJson(result1,
                                    CodeMsgMItemsBean.class);
                            for (int k = 0; k < codeMsgMItemsBean1.getITEMS().size(); k++) {
                                listChildItemName.add(codeMsgMItemsBean1.getITEMS().get(k)
                                        .get("name").toString());
                                listChildItemRegionId.add(codeMsgMItemsBean1.getITEMS().get
                                        (k).get("regionid").toString());
                            }
                            adapterRight = new TextAdapter(mContext, listChildItemName, R
                                    .color.white, R.color.white);
                            lvRight.setAdapter(adapterRight);
                            adapterRight.setOnItemClickListener((view1, position1) -> {
                                if (mOnSelectListener != null) {
                                    showText = listChildItemName.get(position1);
                                    mOnSelectListener.getValue(listChildItemName.get
                                            (position1), listChildItemRegionId.get
                                            (position1));
                                    ChosedCityId = listChildItemRegionId.get(position1);
                                    ViewDoctListM.getHosByCity(ChosedCityId);
                                }
                            });
                        }

                        @Override
                        public void onFailure(int i1, Header[] headers1, byte[] bytes1,
                                              Throwable throwable) {
                            Log.i(TAG, "onFailure: child");

                        }
                    });
                });
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "onFailure: parents");
            }
        });

    }


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String showText, String cityId);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }


}

