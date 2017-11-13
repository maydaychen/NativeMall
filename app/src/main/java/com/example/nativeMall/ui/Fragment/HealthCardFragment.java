package com.example.nativeMall.ui.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.RecommendAdapter;
import com.example.nativeMall.Bean.DataMsgSucesInsuBean;
import com.example.nativeMall.Bean.RecommendBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Activity.ActivateCardStep1Act;
import com.example.nativeMall.ui.Activity.GeneCheckAct;
import com.example.nativeMall.ui.Activity.GoodsDetailActivity;
import com.example.nativeMall.ui.Activity.InsuranceComInfoAct;
import com.example.nativeMall.ui.Activity.MedicalStoreAct;
import com.example.nativeMall.ui.Activity.MenzhenAct;
import com.example.nativeMall.ui.Activity.PayCodeAct;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthCardFragment extends Fragment {


    private static final String EXTRA_CONTENT = "content";
    @BindView(R.id.tv_company_describ)
    TextView tvCompanyDescrib;
    @BindView(R.id.tv_item_insurance)
    TextView tvItemInsurance;
    @BindView(R.id.tv_sale_sum)
    TextView tvSaleSum;
    @BindView(R.id.tv_good_rate)
    TextView tvGoodRate;
    @BindView(R.id.iv_company)
    SmartImageView ivCompany;
    @BindView(R.id.tv_item_price)
    TextView tvItemPrice;
    @BindView(R.id.ll_pay_scan)
    LinearLayout llPayScan;
    @BindView(R.id.rl_click_bind_card)
    RelativeLayout rlClickBindCard;
    @BindView(R.id.rv_goods_activity)
    RecyclerView rvGoodsActivity;

    //    @BindView(R.id.rv_insurance_company)   ///  暂时注释
//    RecyclerView rvInsuranceCompany;
    private RecyclerView.LayoutManager layoutManager;
    //    private InsuranceAdapter insuranceAdapter;
    private static final String TAG = "HealthCardFragment";
    private List<DataMsgSucesInsuBean.InsuranceItemBean> listInsuranceItem = new ArrayList<>();
    private String compId = "";
    private int i = 1;
    private RecommendBean recommendBean;
    public static int HealthCardId = -1;   //通过用户uid获取的cardId;
    private RecommendAdapter recommendAdapter;
    public static boolean BindCard = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_card, container, false);
        ButterKnife.bind(this, view);
//        layoutManager = new GridLayoutManager(getActivity(), 4);
//        rvInsuranceCompany.setLayoutManager(layoutManager);
//        rvInsuranceCompany.addItemDecoration(new RecycleViewGridDivider(getActivity()));

//        getInsuranceCompanyLogo();
        //Log.i(TAG, "onCreateView: " + Config.userBean.getData().getUid());
        rvGoodsActivity.setLayoutManager(new GridLayoutManager(getActivity(), 2,
                LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvGoodsActivity.setHasFixedSize(true);
        DividerLine dividerLine = new DividerLine(DividerLine.HORIZONTAL);
        dividerLine.setSize(1);
        dividerLine.setColor(0xFFEEEEEE);
       // dividerLine.setColor(Color.parseColor("#ff7272"));
        rvGoodsActivity.addItemDecoration(dividerLine);
        getGoodsActivityByAsync();

        getInsuranceCompanyInfo();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (Config.IS_LOG) {
            getHealthUser();
        }
    }

    //获取cardId
    private void getHealthUser() {

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/healthUser";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", Config.userBean.getData().getUid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(getActivity(), URL, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JsonObject jsonObject1 = new JsonParser().parse(response.toString())
                        .getAsJsonObject();

                if (jsonObject1.get("success").getAsString().equals("T")) {

                    //根据uid获取cardId
                    HealthCardId = Integer.parseInt(jsonObject1.get("data").getAsJsonObject().get
                            ("insuredId").toString());

                    //判断用户是否绑定了卡，绑定则换界面
                    if (jsonObject1.get("data").getAsJsonObject().get("cardStatus").getAsString()
                            .equals("1")) {
                        llPayScan.setVisibility(View.VISIBLE);
                        rlClickBindCard.setVisibility(View.GONE);
                        BindCard = true;
                    }
                } else {
                    Toast.makeText(getActivity(),jsonObject1.get("msg").getAsString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i(TAG, "onFailure: " + "healthUser未成功");
            }
        });
    }

    private void getGoodsActivityByAsync() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "index/querycategory";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(getActivity(), URL, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                List<Map<String, Object>> recommend_list = new ArrayList<>();
                Gson gson = new Gson();
                recommendBean = gson.fromJson(response.toString(), RecommendBean.class);
                if (recommendBean.getSuccess().equals("T")) {

                    for (int i = 0; i < recommendBean.getData().get(0).getProjects().size(); i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("logo", recommendBean.getData().get(0).getProjects().get(i)
                                .getShowimg());
                        map.put("name", recommendBean.getData().get(0).getProjects().get(i)
                                .getPname());
                        map.put("price", recommendBean.getData().get(0).getProjects().get(i)
                                .getShopprice());
                        recommend_list.add(map);
                    }
                    recommendAdapter = new RecommendAdapter(getActivity(), recommend_list);
                    rvGoodsActivity.setAdapter(recommendAdapter);
                    recommendAdapter.setOnItemClickListener(new RecommendAdapter
                            .OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int data) {
                            Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                            intent.putExtra("pid", recommendBean.getData().get(0).getProjects()
                                    .get(data).getPid());
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), recommendBean.getMsg(), Toast.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInsuranceCompanyInfo() {

        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL3 + "health/insuranceDtl";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("compId", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
        client.post(getActivity(), URL, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson gson = new Gson();

                DataMsgSucesInsuBean dataMsgSucesInsuBean = gson.fromJson(response.toString(),
                        DataMsgSucesInsuBean.class);
                if (dataMsgSucesInsuBean.getSuccess().equals("T")) {
                    DataMsgSucesInsuBean.InsuranceBean insuranceBean = dataMsgSucesInsuBean
                            .getData();
                    compId = insuranceBean.getCompId();
                    ivCompany.setImageUrl(Config.PIC_URL + insuranceBean.getLogo());
                    tvCompanyDescrib.setText(insuranceBean.getMemo());
                    listInsuranceItem = insuranceBean.getDetails();
                    DataMsgSucesInsuBean.InsuranceItemBean itemBean = listInsuranceItem.get(0);
                    tvItemInsurance.setText(itemBean.getPname());
                    tvSaleSum.setText("销量：" + itemBean.getSalenum());
                    tvGoodRate.setText("好评率：" + itemBean.getMent());
                    tvItemPrice.setText("¥" + itemBean.getPrice());

                } else {
                    Toast.makeText(getActivity(), dataMsgSucesInsuBean.getMsg(), Toast
                            .LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.i(TAG, "onFailure: :网络异常");
            }
        });
    }

//    private void getInsuranceCompanyLogo() {
//        AsyncHttpClient client = new AsyncHttpClient();
//        String URL = Config.GLOBAL_URL3 + "health/findInsurance";
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("number", "8");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        ByteArrayEntity entity = Util.getByteArrayEntity(jsonObject);
//        client.post(getActivity(), URL, entity, "application/json", new JsonHttpResponseHandler
// () {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.i(TAG, "onSuccess: " + response.toString());
//                Gson gson = new Gson();
//
//                DataMsgSucesBean dataMsgSucesBean = gson.fromJson(response.toString(),
//                        DataMsgSucesBean.class);
//                JsonObject json = new JsonParser().parse("").getAsJsonObject();
//                json.getAsJsonArray("stores");

//                List<Map<String, Object>> listmaps = new ArrayList<Map<String, Object>>();
//                for (int i = 0; i < dataMsgSucesBean.getData().size(); i++) {
//                    Map<String, Object> map = new HashMap<String, Object>();
//                    map.put("ivCompLogo", dataMsgSucesBean.getData().get(i).get("compLogo"));
//                    listmaps.add(map);
//                    insuranceAdapter = new InsuranceAdapter(listmaps);
//                    rvInsuranceCompany.setAdapter(insuranceAdapter);
//                    insuranceAdapter.setOnItemClickListener(new InsuranceAdapter
//                            .OnRecyclerViewItemClickListener() {
//
//                        @Override
//                        public void onItemClick(View view, int position) {
//                            Intent intent = new Intent(getActivity(), InsuranceComInfoAct.class);
//                            startActivity(intent);
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
//                                  JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                Log.i(TAG, "onFailure: " + "xxxxxx");
//            }
//        });
//
//    }

    public static HealthCardFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        HealthCardFragment healthCardFragment = new HealthCardFragment();
        healthCardFragment.setArguments(arguments);
        return healthCardFragment;
    }

    @OnClick({R.id.iv_add_card, R.id.ll_gene_check, R.id.ll_menzhen, R.id.ll_medical_store, R.id
            .rl_activity, R.id.ll_to_company_describe, R.id.ll_pay_money, R.id.ll_scan_code})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_gene_check:
                Intent intent = new Intent(getActivity(), GeneCheckAct.class);
                startActivity(intent);
                break;
            case R.id.ll_menzhen:
                Intent intent1 = new Intent(getActivity(), MenzhenAct.class);
                startActivity(intent1);
                break;
            case R.id.ll_medical_store:
                Intent intent2 = new Intent(getActivity(), MedicalStoreAct.class);
                startActivity(intent2);
                break;
            case R.id.rl_activity:
                break;
            case R.id.iv_add_card:
                if (Config.IS_LOG) {

                    Intent intent4 = new Intent(getActivity(), ActivateCardStep1Act.class);
                    startActivity(intent4);

                } else {
                    Util.show(getActivity());
                }
                break;
            case R.id.ll_to_company_describe:
                if (TextUtils.isEmpty(compId)) {
                    //compId 为空不处理
                } else {
                    Intent intent3 = new Intent(getActivity(), InsuranceComInfoAct.class);
                    intent3.putExtra("compId", compId);
                    startActivity(intent3);
                }
                break;
            case R.id.ll_pay_money:
                Intent intent4 = new Intent(getActivity(), PayCodeAct.class);
                startActivity(intent4);

        }
    }


}
