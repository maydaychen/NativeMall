package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.HosAdapter;
import com.example.nativeMall.Bean.CodeMsgMItemsBean;
import com.example.nativeMall.Bean.MapBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class HosListAct extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.rv_all_hos)
    RecyclerView rvAllHos;
    private HosAdapter hosAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<MapBean> mapBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_list);
        ButterKnife.bind(this);
        getSupportActionBar().hide();


        layoutManager = new LinearLayoutManager(this);
        rvAllHos.setLayoutManager(layoutManager);
        DividerLine dividerLine = new DividerLine();
        dividerLine.setColor(Config.line);
        dividerLine.setSize(1);
        rvAllHos.addItemDecoration(dividerLine);

        getHosListDataAsyncHttpClient();
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });
    }

    private void getHosListDataAsyncHttpClient() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        String URL = Config.GLOBAL_URL1 + "hospitalInfo/inquiryallhospitalinfo";
        client.get(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgMItemsBean codeMsgMItemsBean = gson.fromJson(result, CodeMsgMItemsBean
                        .class);

                if (codeMsgMItemsBean.getMSG().equals("SUCCESS")){
                    List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
                    for (int j = 0; j < codeMsgMItemsBean.getITEMS().size(); j++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ivHosImg", codeMsgMItemsBean.getITEMS().get(j).get("hospitalUrl"));
                        map.put("tvHosName", codeMsgMItemsBean.getITEMS().get(j).get("hospitalName"));
                        map.put("tvNum", codeMsgMItemsBean.getITEMS().get(j).get("doctorCount"));
                        map.put("tvAddress", codeMsgMItemsBean.getITEMS().get(j).get
                                ("hospitalAddress"));
                        map.put("tvHosLevel", codeMsgMItemsBean.getITEMS().get(j).get("hospitalLevel"));

                        listmaps.add(map);
                        MapBean HosMsg = new MapBean();
                        HosMsg.setMap(codeMsgMItemsBean.getITEMS().get(j));
                        mapBeanList.add(HosMsg);
                    }

                    hosAdapter = new HosAdapter(listmaps);
                    rvAllHos.setAdapter(hosAdapter);
                    hosAdapter.setOnItemClickListener(new HosAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            MapBean HosMsg = mapBeanList.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("HosMsg", HosMsg);
                            Intent intent = new Intent(HosListAct.this, HospDetailAct.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(HosListAct.this, "网络异常，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
