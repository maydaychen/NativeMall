package com.example.nativeMall.ui.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.CategoryAdapter;
import com.example.nativeMall.Adapter.DividerLine;
import com.example.nativeMall.Adapter.FamousAdapter;
import com.example.nativeMall.Adapter.GridAdapter;
import com.example.nativeMall.Adapter.GridAdapter2;
import com.example.nativeMall.Adapter.MyAdapter;
import com.example.nativeMall.Adapter.RecommendAdapter;
import com.example.nativeMall.Bean.DailyBean;
import com.example.nativeMall.Bean.FamousBean;
import com.example.nativeMall.Bean.FourPicBean;
import com.example.nativeMall.Bean.GridBean;
import com.example.nativeMall.Bean.MallCategoryBean;
import com.example.nativeMall.Bean.RecommendBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.Http;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.Activity.FenleiActivity;
import com.example.nativeMall.ui.Activity.GoodsDetailActivity;
import com.example.nativeMall.ui.Activity.GouwucheActivity;
import com.example.nativeMall.ui.Activity.GridDownActivity;
import com.example.nativeMall.ui.Activity.MyOrderActivity;
import com.example.nativeMall.ui.Activity.SearchActivity;
import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.CacheControl;

/**
 * 作者：JTR on 2016/8/29 10:35
 * 邮箱：2091320109@qq.com
 */
public class MallFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_CONTENT = "content";
    @BindView(R.id.gv_mall_up)
    GridView gvMallUp;
    @BindView(R.id.gv_mall_down)
    GridView gvMallDown;
    @BindView(R.id.tv_mall_daily)
    TextView tvMallDaily;
    @BindView(R.id.rv_mall_famous)
    RecyclerView rvMallFamous;
    @BindView(R.id.rv_mall_recommend)
    RecyclerView rvMallRecommend;
    @BindView(R.id.tv_mall_daily_old_price)
    TextView tvMallDailyOldPrice;
    @BindView(R.id.iv_main_mobile)
    SmartImageView ivMainMobile;
    @BindView(R.id.iv_main_newer)
    SmartImageView ivMainNewer;
    @BindView(R.id.iv_main_combo)
    SmartImageView ivMainCombo;
    @BindView(R.id.iv_main_share)
    SmartImageView ivMainShare;
    @BindView(R.id.iv_mall_daily)
    SmartImageView mIvMallDaily;
    @BindView(R.id.tv_mall_daily_name)
    TextView mTvMallDailyName;
    @BindView(R.id.tv_mall_daily_num)
    TextView mTvMallDailyNum;
    @BindView(R.id.tv_mall_daily_now_price)
    TextView mTvMallDailyNowPrice;
    @BindView(R.id.rv_mall_category)
    RecyclerView mRvMallCategory;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private int[] icon = {R.drawable.v2_0_index_menu1,
            R.drawable.v2_0_index_menu2,
            R.drawable.v2_0_index_menu3,
            R.drawable.v2_0_index_menu5,};
    private String[] iconName = {"药品分类", "购物车", "三级分类", "我的订单"};
    private List<Map<String, Object>> data_list;

    private List<String> url_list = new ArrayList<>();

    private SmartImageView[] imageViews;
    private RecommendBean recommendBean;
    private GridBean gridBean;
    private DailyBean mDailyBean;

    private FourPicBean mFourPicBean;
    private MyAdapter adapter;
    private int curIndex = 0;
    private List<ImageView> img_list = new ArrayList<>();
    CacheControl my_cache;
    RecyclerView.LayoutManager layoutManager, mLayoutManager;
    JSONObject json = null, json2 = null;
    JSONArray jsonArray = null;
    Gson gson = new Gson();
    private final Handler mHandler = new MyHandler(MallFragment.this);
    private final Handler mHandler2 = new MyHandler2(MallFragment.this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mall, container, false);
        ButterKnife.bind(this, root);
        initData();
        initView();
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler2.removeCallbacksAndMessages(null);
    }

    public static MallFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);

        MallFragment mallFragment = new MallFragment();
        mallFragment.setArguments(arguments);
        return mallFragment;
    }


    public void initData() {
        imageViews = new SmartImageView[]{ivMainMobile, ivMainNewer, ivMainCombo, ivMainShare};
        data_list = getData();

        Map<String, String> param = new HashMap<>();
        param.put("type", "2");
        Http.getInstance().init(getActivity(), mHandler, gson.toJson(param), "index/querycategory", 2).sendMsg();
        //图表
        Map<String, String> param1 = new HashMap<>();
        param1.put("type", "1");
        Http.getInstance().init(getActivity(), mHandler, gson.toJson(param1), "index/querycategory", 1).sendMsg();
        //四图片
        Map<String, String> param2 = new HashMap<>();
        param2.put("type", "0");
        Http.getInstance().init(getActivity(), mHandler2, gson.toJson(param2), "index/querycategory", 1).sendMsg();
        //名店抢购
        Http.getInstance().init(getActivity(), mHandler, "", "index/buyingshops", 3).sendMsg();
        //每日一抢
        Http.getInstance().init(getActivity(), mHandler, "", "index/dayactivity", 0).sendMsg();
        //主页分类
        Http.getInstance().init(getActivity(), mHandler2, "", "index/indexClass", 0).sendMsg();
        //banner
        Map<String, String> banner = new HashMap<>();
        banner.put("type", "1");
        Http.getInstance().init(getActivity(), mHandler2, gson.toJson(banner), "address/adverts", 2).sendMsg();

    }

    public void initView() {
        tvMallDailyOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        SpannableStringBuilder builder1 = new SpannableStringBuilder(tvMallDaily.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder1.setSpan(redSpan, 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvMallDaily.setText(builder1);
        gvMallUp.setAdapter(new GridAdapter(getActivity(), data_list));
        gvMallUp.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    Util.startIntent(getActivity(), FenleiActivity.class);
                    break;
                case 1:
                    Util.startIntent(getActivity(), GouwucheActivity.class);
                    break;
                case 2:
                    Intent intent2 = new Intent(getActivity(), GridDownActivity.class);
                    intent2.putExtra("title", gridBean.getData().get(4).getCatename());
                    intent2.putExtra("cateid", gridBean.getData().get(4).getCateid());
                    startActivity(intent2);
                    break;
                case 3:
                    if (Config.IS_LOG) {
                        Util.startIntent(getActivity(), MyOrderActivity.class);
                    } else {
                        Util.show(getActivity());
                    }
                    break;
            }
        });

        rvMallFamous.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        DividerLine dividerLine = new DividerLine(DividerLine.HORIZONTAL);
        dividerLine.setSize(1);
        dividerLine.setColor(0xFFEEEEEE);
        rvMallRecommend.addItemDecoration(dividerLine);
        rvMallRecommend.setLayoutManager(layoutManager);

        CacheControl.Builder builder =
                new CacheControl.Builder().
                        maxAge(6, TimeUnit.SECONDS).//这个是控制缓存的最大生命时间
                        maxStale(6, TimeUnit.SECONDS);//这个是控制缓存的过时时间

        my_cache = builder.build();

        gvMallDown.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getActivity(), GridDownActivity.class);
            intent.putExtra("title", gridBean.getData().get(i).getCatename());
            intent.putExtra("cateid", gridBean.getData().get(i).getCateid());
            startActivity(intent);
        });

        mSwipeContainer.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeContainer.setDistanceToTriggerSync(300);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeContainer.setProgressBackgroundColor(R.color.blue); // 设定下拉圆圈的背景
        mSwipeContainer.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            list.add(map);
        }
        return list;
    }

    //每日一抢
    private void dayActivity(String data) {
        try {
            json = new JSONObject(data);
            if (json.getString("success").equals("T")) {
                mDailyBean = gson.fromJson(data, DailyBean.class);
                mIvMallDaily.setImageUrl(Config.PIC_URL + mDailyBean.getData().getProduct().getShowimg());
                mTvMallDailyName.setText(mDailyBean.getData().getProduct().getPname());
                mTvMallDailyNum.setText(String.format(getResources().getString(R.string.mall_daily_pnum), mDailyBean.getData().getStoreid() + ""));
                mTvMallDailyNowPrice.setText(String.format(getResources().getString(R.string.mall_daily_nowprice), mDailyBean.getData().getProduct().getShopprice()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getTubiao(String data) throws JSONException {
        //banner+商品
        List<Map<String, Object>> category_list = new ArrayList<>();
        List<Map<String, Object>> category_inside_list = new ArrayList<>();

        json = new JSONObject(data);
        if (json.getString("success").equals("T")) {
            MallCategoryBean mallCategoryBean = gson.fromJson(data, MallCategoryBean.class);
            for (MallCategoryBean.DataBean dataBean : mallCategoryBean.getData()) {
                Map<String, Object> map = new HashMap<>();
                map.put("pic", dataBean.getImgpath());
                for (MallCategoryBean.DataBean.ProjectsBean projectsBean : dataBean.getProjects()) {
                    Map<String, Object> inside_map = new HashMap<>();
                    inside_map.put("price", projectsBean.getShopprice());
                    inside_map.put("name", projectsBean.getPname());
                    inside_map.put("url", projectsBean.getShowimg());
                    inside_map.put("pid", projectsBean.getPid());
                    category_inside_list.add(inside_map);
                }
                map.put("inside", category_inside_list);
                category_list.add(map);
            }
            mLayoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mRvMallCategory.setLayoutManager(mLayoutManager);
            CategoryAdapter categoryAdapter = new CategoryAdapter(category_list, getActivity());
            mRvMallCategory.setAdapter(categoryAdapter);
        }
    }

    private void getRecommend(String data) throws JSONException {
        //为您推荐
        List<Map<String, Object>> recommend_list = new ArrayList<>();
        json = new JSONObject(data);
        if (json.getString("success").equals("T")) {
            Gson gson = new Gson();
            recommendBean = gson.fromJson(data, RecommendBean.class);
            for (RecommendBean.DataBean.ProjectsBean projectsBean : recommendBean.getData().get(0).getProjects()) {
                Map<String, Object> map = new HashMap<>();
                map.put("logo", projectsBean.getShowimg());
                map.put("name", projectsBean.getPname());
                map.put("price", projectsBean.getShopprice());
                recommend_list.add(map);
            }
            RecommendAdapter recommendAdapter = new RecommendAdapter(getActivity(), recommend_list);
            rvMallRecommend.setAdapter(recommendAdapter);
            recommendAdapter.setOnItemClickListener((view, data1) -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("pid", recommendBean.getData().get(0).getProjects().get(data1).getPid());
                startActivity(intent);
            });
        }
    }

    //名店抢购
    private void getFamous(String data) throws JSONException {
        json = new JSONObject(data);
        FamousBean mFamousBean;
        List<Map<String, Object>> famous_list = new ArrayList<>();
        if (json.getString("success").equals("T")) {
            mFamousBean = gson.fromJson(data, FamousBean.class);
            for (FamousBean.DataBean dataBean : mFamousBean.getData()) {
                Map<String, Object> map = new HashMap<>();
                map.put("price", dataBean.getProduct().getShopprice() + "");
                map.put("name", dataBean.getProduct().getPname());
                map.put("logo", dataBean.getProduct().getShowimg());
                famous_list.add(map);
            }
            FamousAdapter famousAdapter = new FamousAdapter(getActivity(), famous_list);
            rvMallFamous.setAdapter(famousAdapter);
            famousAdapter.setOnItemClickListener((view, data12) -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("pid", mFamousBean.getData().get(data12).getPid() + "");
                startActivity(intent);
            });
        }
    }

    //五大分类
    private void getFivekind(String data) throws JSONException {
        json = new JSONObject(data);
        jsonArray = json.getJSONArray("data");
        if (json.getString("success").equals("T")) {
            Gson fenleigson = new Gson();
            gridBean = fenleigson.fromJson(data, GridBean.class);
            if (gridBean.getSuccess().equals("T")) {
                List<Map<String, Object>> list = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("img_url", gridBean.getData().get(i).getImgpath());
                    map.put("name", gridBean.getData().get(i).getCatename());
                    list.add(map);
                }
                GridAdapter2 gridAdapter2 = new GridAdapter2(getActivity(), list);
                gvMallDown.setAdapter(gridAdapter2);
            }
        }
    }

    private void getFourPic(String data) throws JSONException {
        //四图片
        json = new JSONObject(data);
        jsonArray = json.getJSONArray("data");
        if (json.getString("success").equals("T")) {
            mFourPicBean = gson.fromJson(data, FourPicBean.class);
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            json2 = jsonArray.getJSONObject(i);
            imageViews[i].setImageUrl(Config.PIC_URL + json2.getString("imgpath"));
        }
        imageViews[0].setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GridDownActivity.class);
            intent.putExtra("title", mFourPicBean.getData().get(0).getName());
            intent.putExtra("cateid", mFourPicBean.getData().get(0).getNpid());
            startActivity(intent);

        });
        imageViews[1].setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GridDownActivity.class);
            intent.putExtra("title", mFourPicBean.getData().get(1).getName());
            intent.putExtra("cateid", mFourPicBean.getData().get(1).getNpid());
            startActivity(intent);
        });
        imageViews[2].setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GridDownActivity.class);
            intent.putExtra("title", mFourPicBean.getData().get(2).getName());
            intent.putExtra("cateid", mFourPicBean.getData().get(2).getNpid());
            startActivity(intent);
        });
        imageViews[3].setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), GridDownActivity.class);
            intent.putExtra("title", mFourPicBean.getData().get(3).getName());
            intent.putExtra("cateid", mFourPicBean.getData().get(3).getNpid());
            startActivity(intent);
        });
    }

    private void getBanner(String data) throws JSONException {
        json = new JSONObject(data);
        json2 = json.getJSONObject("data");
        url_list = new ArrayList<>();
        if (json.getString("success").equals("T")) {
            jsonArray = json2.getJSONArray("adverts");
            for (int i = 0; i < jsonArray.length(); i++) {
                url_list.add(Config.PIC_URL + jsonArray.getJSONObject(i).getString("image"));
            }
        }
        if (url_list.isEmpty()) {
            url_list.add("http://101.231.124.9:56677/imgservice/userfiles/78b065753eca4c5181882d12ae7df960.jpg");
        }
        List<View> list = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        /**
         * 创建多个item （每一条viewPager都是一个item）
         * 从服务器获取完数据（如文章标题、url地址） 后，再设置适配器
         */
        for (String ignored : url_list) {
            View item = inflater.inflate(R.layout.item, null);
            list.add(item);
        }
        //创建适配器， 把组装完的组件传递进去
        adapter = new MyAdapter(list, url_list);
        view_pager.setAdapter(adapter);
        //绑定动作监听器：如翻页的动画
        view_pager.addOnPageChangeListener(new MyListener());
        initIndicator();
        startAutoScroll();
    }

    @OnClick({R.id.tv_mall_search, R.id.rl_mall_daily})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mall_search:
                Util.startIntent(getActivity(), SearchActivity.class);
                break;
            case R.id.rl_mall_daily:
                if (mDailyBean == null) {
                    Toast.makeText(getActivity(), "无此商品", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent1.putExtra("pid", mDailyBean.getData().getPid() + "");
                    startActivity(intent1);
                }
                break;
        }
    }

    public void onRefresh() {
        new Handler().postDelayed(() -> {
            // 停止刷新
            initData();
            mSwipeContainer.setRefreshing(false);
        }, 3000); // 3秒后发送消息，停止刷新
    }

    private void initIndicator() {
        ImageView imgView;
        View v = getActivity().findViewById(R.id.indicator1);// 线性水平布局，负责动态调整导航图标
        ((ViewGroup) v).removeAllViews();
        img_list = new ArrayList<>();
        for (int i = 0; i < url_list.size(); i++) {
            imgView = new ImageView(getActivity());
            LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10, 10);
            params_linear.setMargins(7, 10, 7, 10);
            imgView.setLayoutParams(params_linear);
            img_list.add(imgView);
            img_list.get(i).setBackgroundResource(i == 0 ? R.drawable.v2_0_spxq_dot1 : R.drawable.v2_0_spxq_dot2);
            ((ViewGroup) v).addView(img_list.get(i));
        }

    }

    private class MyListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int position) {
            // 改变所有导航的背景图片为：未选中
            for (ImageView imageView : img_list) {
                imageView.setBackgroundResource(R.drawable.v2_0_spxq_dot2);
            }
            // 改变当前背景图片为：选中
            img_list.get(position).setBackgroundResource(R.drawable.v2_0_spxq_dot1);
            curIndex = position;
        }


    }

    // 切换图片任务
    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            getActivity().runOnUiThread(() -> {
                int count = adapter.getCount();
                view_pager.setCurrentItem((curIndex + 1) % count);
            });
        }
    }

    private void startAutoScroll() {
        ScheduledExecutorService scheduledExecutorService = Executors
                .newSingleThreadScheduledExecutor();
        // 每隔2秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 2,
                3, TimeUnit.SECONDS);
    }

    static class MyHandler extends Handler {
        WeakReference<MallFragment> mActivityReference;

        MyHandler(MallFragment activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MallFragment activity = mActivityReference.get();
            if (activity != null) {
                String data = msg.obj.toString();
                try {
                    switch (msg.what) {
                        case 0:
                            activity.dayActivity(data);
                            break;
                        case 1:
                            activity.getTubiao(data);
                            break;
                        case 2:
                            activity.getRecommend(data);
                            break;
                        case 3:
                            activity.getFamous(data);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyHandler2 extends Handler {
        WeakReference<MallFragment> mActivityReference;

        MyHandler2(MallFragment activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MallFragment activity = mActivityReference.get();
            if (activity != null) {
                String data = msg.obj.toString();
                try {
                    switch (msg.what) {
                        case 0:
                            activity.getFivekind(data);
                            break;
                        case 1:
                            activity.getFourPic(data);
                            break;
                        case 2:
                            activity.getBanner(data);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
