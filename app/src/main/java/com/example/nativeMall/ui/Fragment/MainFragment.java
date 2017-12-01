package com.example.nativeMall.ui.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nativeMall.Adapter.GridAdapter2;
import com.example.nativeMall.Adapter.HotGoodsAdapter;
import com.example.nativeMall.Adapter.RecommendAdapter;
import com.example.nativeMall.Bean.AccessTokenBean;
import com.example.nativeMall.Bean.AttrbuteBean;
import com.example.nativeMall.Bean.BannerBean;
import com.example.nativeMall.Bean.CategoryBean;
import com.example.nativeMall.Bean.ResultBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.example.nativeMall.ui.Activity.GoodsDetailActivity;
import com.example.nativeMall.ui.Activity.GridDownActivity;
import com.example.nativeMall.ui.Activity.LoginActivity;
import com.example.nativeMall.ui.Activity.SearchActivity;
import com.example.nativeMall.ui.widget.GlideImageLoader;
import com.example.nativeMall.ui.widget.GrideViewScroll;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：JTR on 2016/8/18 13:56
 * 邮箱：2091320109@qq.com
 */
public class MainFragment extends Fragment {
    private static final String EXTRA_CONTENT = "content";
    @BindView(R.id.rv_mall_latest)
    RecyclerView mRvMallLatest;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.gv_mall_down)
    GrideViewScroll mGvMallDown;
    @BindView(R.id.rv_mall_hot)
    RecyclerView mRvMallHot;
    @BindView(R.id.rv_mall_recomment)
    RecyclerView mRvMallRecomment;

    private SubscriberOnNextListener<JSONObject> getTokenOnNext;
    private SubscriberOnNextListener<JSONObject> bannerOnNext;
    private SubscriberOnNextListener<JSONObject> categoryOnNext;
    private SubscriberOnNextListener<JSONObject> hotOnNext;
    private SubscriberOnNextListener<JSONObject> latestOnNext;
    private SubscriberOnNextListener<JSONObject> recommendOnNext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();


    @Override
    public void onResume() {
        HttpJsonMethod.getInstance().get_token(
                new ProgressSubscriber(getTokenOnNext, getActivity()),
                "ganglong", "69534b32ab51f8cb802720d30fedbxxx");
        super.onResume();

    }

    public static MainFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(arguments);
        return mainFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        initData();
        initBanner();
        initCategory();
        getHot();
        getLatest();
        getRecommend();
        return root;
    }

    private void initData() {
        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();

        getTokenOnNext = resultBean -> {
            switch (resultBean.getInt("statusCode")) {
                case 1:
                    AccessTokenBean indexBean = gson.fromJson(resultBean.toString(), AccessTokenBean.class);
                    editor.putString("access_token", indexBean.getResult().getAccess_token());
                    editor.putString("auth_key", indexBean.getResult().getAuth_key());
                    editor.putInt("access_time", indexBean.getResult().getTimestamp());
                    editor.apply();
                    break;
                case 10003:
                    Toast.makeText(getActivity(), "服务器错误，请稍后再试...", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getActivity(), resultBean.getString("result"), Toast.LENGTH_SHORT).show();
                    break;
            }
        };
        bannerOnNext = jsonObject -> {
            switch (jsonObject.getInt("statusCode")) {
                case 1:
                    BannerBean indexBean = gson.fromJson(jsonObject.toString(), BannerBean.class);
                    List<String> banner = new ArrayList<>();
                    for (BannerBean.ResultBean resultBean : indexBean.getResult()) {
                        banner.add(resultBean.getThumb());
                    }
                    mBanner.setImages(banner).setImageLoader(new GlideImageLoader()).start();
                    mBanner.isAutoPlay(true);
                    mBanner.setOnBannerListener(position -> Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show());
                    break;
                case 10010:
                    logout(getActivity());
                    break;
                default:
                    break;
            }
        };
        categoryOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                CategoryBean indexBean = gson.fromJson(jsonObject.toString(), CategoryBean.class);
                List<HashMap<String, String>> list = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("img_url", indexBean.getResult().get(i).getAdvimg());
                    map.put("name", indexBean.getResult().get(i).getName());
                    list.add(map);
                }
                GridAdapter2 gridAdapter2 = new GridAdapter2(getActivity(), list);
                mGvMallDown.setAdapter(gridAdapter2);
                mGvMallDown.setOnItemClickListener((adapterView, view, i, l) -> {
                    if (i == 7) {
                        ResultBean resultBean = new ResultBean();
                        resultBean.setCode(1);
                        resultBean.setResult("");
                        EventBus.getDefault().post(resultBean);
                    } else {
                        Intent intent2 = new Intent(getActivity(), GridDownActivity.class);
                        intent2.putExtra("title", indexBean.getResult().get(i).getName());
                        intent2.putExtra("cateid", indexBean.getResult().get(i).getId());
                        startActivity(intent2);
                    }

                });
            } else {
                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        hotOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                AttrbuteBean indexBean = gson.fromJson(jsonObject.toString(), AttrbuteBean.class);
                HotGoodsAdapter hotGoodsAdapter = new HotGoodsAdapter(getActivity(), indexBean.getResult());
                mRvMallHot.setLayoutManager(new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.HORIZONTAL));
                mRvMallHot.setAdapter(hotGoodsAdapter);
                hotGoodsAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("id", indexBean.getResult().get(data).getId());
                    startActivity(intent);
                });
            } else {
                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        latestOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                AttrbuteBean indexBean = gson.fromJson(jsonObject.toString(), AttrbuteBean.class);
                HotGoodsAdapter hotGoodsAdapter = new HotGoodsAdapter(getActivity(), indexBean.getResult());
                mRvMallLatest.setLayoutManager(new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.HORIZONTAL));
                mRvMallLatest.setAdapter(hotGoodsAdapter);
                hotGoodsAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("id", indexBean.getResult().get(data).getId());
                    startActivity(intent);
                });
            } else {
                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        recommendOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                AttrbuteBean indexBean = gson.fromJson(jsonObject.toString(), AttrbuteBean.class);
                RecommendAdapter hotGoodsAdapter = new RecommendAdapter(getActivity(), indexBean.getResult());
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
//                DividerLine dividerLine = new DividerLine(DividerLine.HORIZONTAL);
//                dividerLine.setSize(1);
//                dividerLine.setColor(0xFFEEEEEE);
//                mRvMallRecomment.addItemDecoration(dividerLine);
                mRvMallRecomment.setLayoutManager(layoutManager);
                mRvMallRecomment.setAdapter(hotGoodsAdapter);
                hotGoodsAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("id", indexBean.getResult().get(data).getId());
                    startActivity(intent);
                });
            } else {
                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public static void logout(Activity context) {
        new AlertDialog.Builder(context)
                .setTitle("警告")
                .setCancelable(false)
                .setMessage("账号验证失效，请重新登录！")
                .setPositiveButton("确定", (dialog, which) -> {
                    SharedPreferences mySharedPreferences = context.getSharedPreferences("user",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putBoolean("autoLog", false);
                    if (editor.commit()) {
                        context.finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                })
                .show();
    }

    private void initBanner() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().get_banner(
                new ProgressSubscriber(bannerOnNext, getActivity()),
                preferences.getString("access_token", ""), sign, time);
    }

    private void initCategory() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().get_category(
                new ProgressSubscriber(categoryOnNext, getActivity()),
                preferences.getString("access_token", ""), sign, time);
    }

    private void getHot() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "attributes=ishot:1&";
        sign = sign + "page=" + 1 + "&";
        sign = sign + "psize=" + 10 + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().getAttributes(
                new ProgressSubscriber(hotOnNext, getActivity()),
                preferences.getString("access_token", ""), "ishot:1", 1, 10, sign, time);
    }

    private void getLatest() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "attributes=isnew:1&";
        sign = sign + "page=" + 1 + "&";
        sign = sign + "psize=" + 10 + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().getAttributes(
                new ProgressSubscriber(latestOnNext, getActivity()),
                preferences.getString("access_token", ""), "isnew:1", 1, 10, sign, time);
    }

    private void getRecommend() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "attributes=isrecommand:1&";
        sign = sign + "page=" + 1 + "&";
        sign = sign + "psize=" + 10 + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().getAttributes(
                new ProgressSubscriber(recommendOnNext, getActivity()),
                preferences.getString("access_token", ""), "isrecommand:1", 1, 10, sign, time);
    }

    @OnClick({R.id.tv_main_search, R.id.tv_main_hot_more, R.id.tv_main_latest_more, R.id.tv_main_recommend_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_main_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.tv_main_hot_more:
                break;
            case R.id.tv_main_latest_more:
                break;
            case R.id.tv_main_recommend_more:
                break;
            default:
                break;
        }
    }
}
