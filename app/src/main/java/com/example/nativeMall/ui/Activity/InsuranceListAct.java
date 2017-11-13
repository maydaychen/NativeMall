package com.example.nativeMall.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nativeMall.Adapter.InsuranceAdapter;
import com.example.nativeMall.Bean.DataMsgSucesBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.CustomTitle;
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

public class InsuranceListAct extends AppCompatActivity {

    @BindView(R.id.custom_title)
    CustomTitle customTitle;
    @BindView(R.id.rv_insurance_company)
    RecyclerView rvInsuranceCompany;
    private InsuranceAdapter insuranceAdapter;
    private List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_list);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        customTitle.setLeftRightImgClickListener(new CustomTitle.LeftRightImgClickListener() {
            @Override
            public void leftClick(Boolean click) {
                finish();
            }

            @Override
            public void rightClick(Boolean click) {

            }
        });

        getInsuranceList();
    }

    private void getInsuranceList() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/findInsurance";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", "8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(InsuranceListAct.this, URL, entity, "application/json", new
                JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson gson = new Gson();
                DataMsgSucesBean dataMsgSucesBean = gson.fromJson(response.toString(),
                        DataMsgSucesBean.class);

               if (dataMsgSucesBean.getSuccess().equals("T")){
                   for (int i = 0; i < dataMsgSucesBean.getData().size(); i++) {
                       Map<String, Object> map = new HashMap<String, Object>();
                       map.put("ivCompLogo", dataMsgSucesBean.getData().get(i).get("compLogo"));
                       listmaps.add(map);

                   }
                   insuranceAdapter = new InsuranceAdapter(listmaps);
                   rvInsuranceCompany.setAdapter(insuranceAdapter);
                   insuranceAdapter.setOnItemClickListener(new InsuranceAdapter
                           .OnRecyclerViewItemClickListener() {

                       @Override
                       public void onItemClick(View view, int position) {
                           String regionId = listmaps.get(position).get("regionId").toString();
                           Intent intent = new Intent(InsuranceListAct.this, InsuranceComInfoAct.class);
                           intent.putExtra("regionId",regionId);
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

}
