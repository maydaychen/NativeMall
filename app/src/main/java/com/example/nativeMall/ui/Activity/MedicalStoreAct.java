package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.nativeMall.Adapter.GeneMenStoreAdapter;
import com.example.nativeMall.Bean.DataESMsgSucesBean;
import com.example.nativeMall.Bean.StoreBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
import com.example.nativeMall.ui.widget.RecycleViewDivider;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class MedicalStoreAct extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.rv_medical_store)
    RecyclerView rvMedicalStore;
    private GeneMenStoreAdapter geneMenStoreAdapter;
    private static final String TAG = "MedicalStoreAct";
    private List<StoreBean> storeBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_store);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        InitView();
        getMedicalStoreList();
    }

    private void getMedicalStoreList() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/findStores";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(MedicalStoreAct.this,URL,entity,"application/json",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson gson = new Gson();

                List<Map<String,Object>> listmaps = new ArrayList<>();
                DataESMsgSucesBean dataESMsgSucesBean = gson.fromJson(response.toString(),DataESMsgSucesBean.class);
                if (dataESMsgSucesBean.getSuccess().equals("T")){
                    for (int i = 0 ; i < dataESMsgSucesBean.getData().getStores().size();i++){
                        StoreBean storeBean = dataESMsgSucesBean.getData().getStores().get(i);
                        Map<String,Object> map = new HashMap<String, Object>();
                        map.put("storeId",storeBean.getStoreid());
                        map.put("ivCompany",storeBean.getLogo());
                        map.put("tvCompanyName",storeBean.getName());
                        map.put("tvMobile",storeBean.getMobile());
                        map.put("tvAddress",storeBean.getAddress());
                        map.put("tvDistance",storeBean.getDistance());
                        Log.i(TAG, "onSuccess: ???" + storeBean.toString());
                        listmaps.add(map);
                        storeBeanList.add(storeBean);
                    }
                    geneMenStoreAdapter = new GeneMenStoreAdapter(listmaps);
                    rvMedicalStore.setAdapter(geneMenStoreAdapter);
                    geneMenStoreAdapter.setOnItemClickListener(new GeneMenStoreAdapter.OnRecyclerViewItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            StoreBean storeBean = storeBeanList.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("storeBean",storeBean);
                            Intent intent = new Intent(MedicalStoreAct.this,MedicalStoreDtlAct.class);
                            intent.putExtras(bundle);
                            startActivity(intent);


                        }
                    });
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void InitView() {
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });

        rvMedicalStore.setLayoutManager(new LinearLayoutManager(this));
        rvMedicalStore.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));

    }
}
