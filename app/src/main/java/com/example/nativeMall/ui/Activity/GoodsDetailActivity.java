package com.example.nativeMall.ui.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Adapter.GoodsCommentAdapter;
import com.example.nativeMall.Adapter.MyAdapter;
import com.example.nativeMall.Adapter.PropertyAdapter;
import com.example.nativeMall.Bean.CommentBean;
import com.example.nativeMall.Bean.DetailBean;
import com.example.nativeMall.Bean.GouwucheBean;
import com.example.nativeMall.Bean.ImageInfo;
import com.example.nativeMall.Bean.MallStoreBean;
import com.example.nativeMall.Bean.PreOrderBean;
import com.example.nativeMall.Bean.ShoppingCarBean;
import com.example.nativeMall.Bean.UselessBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.HttpMethods;
import com.example.nativeMall.ProgressSubscriber;
import com.example.nativeMall.R;
import com.example.nativeMall.SubscriberOnNextListener;
import com.example.nativeMall.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.loopj.android.image.SmartImageView;

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
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.hyphenate.util.DensityUtil.dip2px;

public class GoodsDetailActivity extends InitActivity {

    @BindView(R.id.rv_goods_comment)
    RecyclerView rvGoodsComment;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.iv_goods_detail_shoucang)
    ImageView ivGoodsDetailShoucang;
    @BindView(R.id.ll_goods_detail_shoucang)
    LinearLayout llGoodsDetailShoucang;
    @BindView(R.id.tv_goods_detail_name)
    TextView tvGoodsDetailName;
    @BindView(R.id.tv_goods_detail_price)
    TextView tvGoodsDetailPrice;
    @BindView(R.id.tv_goods_detail_shop)
    TextView tvGoodsDetailShop;
    @BindView(R.id.tv_goods_detail_sold)
    TextView tvGoodsDetailSold;
    @BindView(R.id.tv_goods_detail_inventory)
    TextView tvGoodsDetailInventory;
    @BindView(R.id.tv_shoucang)
    TextView mTvShoucang;
    @BindView(R.id.ll_detail_botton)
    LinearLayout mLlDetailBotton;
    @BindView(R.id.shop_info)
    RelativeLayout mShopInfo;
    @BindView(R.id.tv_goods_detail_gouwuche_num)
    TextView mTvGoodsDetailGouwucheNum;
    @BindView(R.id.wv_goods_detail)
    WebView mWvGoodsDetail;
    @BindView(R.id.iv_detail_pic1)
    ImageView mIvDetailPic1;
    @BindView(R.id.iv_detail_pic2)
    ImageView mIvDetailPic2;
    @BindView(R.id.iv_detail_pic3)
    ImageView mIvDetailPic3;
    @BindView(R.id.iv_detail_pic4)
    ImageView mIvDetailPic4;
    @BindView(R.id.tv_detail_gouwuche)
    TextView mTvDetailGouwuche;

    private DetailBean mDetailBean;
    private MallStoreBean.DataBean mMallStoreBean;
    private Gson gson = new Gson();
    private PropertyAdapter propertyAdapter;
    private ViewPager view_pager;
    private MyAdapter adapter;
    private JsonArray array1 = new JsonArray();
    private ScheduledExecutorService scheduledExecutorService;
    //是否收藏
    private boolean IS_COLLECTED = false;
    private int curIndex = 0;
    //购物车数量
    private int number = 0;
    //动画数量
    int num = 0;

    private List<String> url_list = new ArrayList<>();
    private List<ImageView> img_list = new ArrayList<>();
    FrameLayout animation_viewGroup;
    int AnimationDuration = 1000;
    //正在执行的动画数量
    boolean isClean = false;
    private int[] start_location = new int[2];
    private Drawable drawable;

    private SubscriberOnNextListener UpdateGouwucheOnNext, CheckStoreOnNext, CommentOnNext, ShouCangOnNext;
    private SubscriberOnNextListener GouwucheOnNext, BuyNowOnNext, DetailOnNext;

    //回退刷新数据
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        animation_viewGroup = createAnimLayout();
        UpdateGouwucheOnNext = new SubscriberOnNextListener<List<ShoppingCarBean.DataBean>>() {
            @Override
            public void onNext(List<ShoppingCarBean.DataBean> dataBeen) {
                for (int i = 0; i < dataBeen.size(); i++) {
                    number += dataBeen.get(i).getProList().size();
                }
                if (number != 0) {
                    mTvGoodsDetailGouwucheNum.setText(number + "");
                    mTvGoodsDetailGouwucheNum.setVisibility(View.VISIBLE);
                } else {
                    mTvGoodsDetailGouwucheNum.setVisibility(View.INVISIBLE);
                }
            }
        };

        CheckStoreOnNext = new SubscriberOnNextListener<MallStoreBean.DataBean>() {
            @Override
            public void onNext(MallStoreBean.DataBean dataBeen) {
                mMallStoreBean = dataBeen;
                mShopInfo.setVisibility(View.VISIBLE);
            }
        };

        CommentOnNext = new SubscriberOnNextListener<List<CommentBean.DataBean>>() {
            @Override
            public void onNext(List<CommentBean.DataBean> dataBeen) {
                //商品评价
                List<Map<String, Object>> list = new ArrayList<>();
                for (CommentBean.DataBean bean : dataBeen) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", bean.getUsername());
                    map.put("time", bean.getReviewtime());
                    map.put("miaoshu", bean.getQuality() + "");
                    map.put("fuwu", bean.getStar() + "");
                    map.put("fahuo", bean.getSpeed() + "");
                    list.add(map);
                }
                rvGoodsComment.setAdapter(new GoodsCommentAdapter(GoodsDetailActivity.this, list));
                if (dataBeen == null) {
                    mIvDetailPic3.setVisibility(View.INVISIBLE);
                    mIvDetailPic4.setVisibility(View.INVISIBLE);
                }
            }
        };

        ShouCangOnNext = new SubscriberOnNextListener<UselessBean>() {
            @Override
            public void onNext(UselessBean dataBeen) {
                Toast.makeText(GoodsDetailActivity.this, dataBeen.getMsg(), Toast.LENGTH_SHORT).show();
            }
        };

        GouwucheOnNext = new SubscriberOnNextListener<UselessBean>() {
            @Override
            public void onNext(UselessBean dataBeen) {
                if (dataBeen.getSuccess().equals("T")) {
                    Toast.makeText(GoodsDetailActivity.this, dataBeen.getMsg(), Toast.LENGTH_SHORT).show();
                    setAnim(drawable, start_location);
                    updateGouwuche();
                } else {
                    Toast.makeText(GoodsDetailActivity.this, dataBeen.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        BuyNowOnNext = new SubscriberOnNextListener<PreOrderBean>() {
            @Override
            public void onNext(PreOrderBean dataBeen) {
                if (dataBeen.getSuccess().equals("T")) {
                    if (dataBeen.getSuccess().equals("NoAddress")) {
                        Toast.makeText(GoodsDetailActivity.this, "请添加收货地址！", Toast.LENGTH_SHORT).show();
                        Util.startIntent(GoodsDetailActivity.this, AddAddressActivity.class);
                    } else {
                        Intent intent = new Intent(GoodsDetailActivity.this, ConfirmActivity.class);
                        intent.putExtra("array", array1.toString());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("preorder", dataBeen);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(GoodsDetailActivity.this, dataBeen.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        DetailOnNext = new SubscriberOnNextListener<DetailBean>() {
            @Override
            public void onNext(DetailBean dataBeen) {
                if (dataBeen.getSuccess().equals("T")) {
                    mDetailBean = dataBeen;
                    url_list = new ArrayList<>();
                    tvGoodsDetailName.setText(dataBeen.getData().getName());
                    tvGoodsDetailShop.setText(dataBeen.getData().getSname());
                    tvGoodsDetailPrice.setText(String.format(getResources().getString(R.string.tv_mall_price), dataBeen.getData().getShopprice()));
                    tvGoodsDetailSold.setText(String.format(getResources().getString(R.string.goods_detail_sold), dataBeen.getData().getSalecount()));
                    tvGoodsDetailInventory.setText(String.format(getResources().getString(R.string.goods_detail_inventory), dataBeen.getData().getPnumber() + ""));
                    if (dataBeen.getData().isCheck()) {
                        IS_COLLECTED = true;
                        ivGoodsDetailShoucang.setImageResource(R.drawable.v2_0_spxq_shoucang_hover);
                        mTvShoucang.setTextColor(ContextCompat.getColor(GoodsDetailActivity.this, R.color.blue));
                    }
                    for (Map m : mDetailBean.getData().getPimages()) {
                        url_list.add(Config.PIC_URL + m.get("showimg"));
                    }
                    if (url_list.isEmpty()) {
                        url_list.add("http://101.231.124.9:56677/imgservice/userfiles/78b065753eca4c5181882d12ae7df960.jpg");
                    }
                    Map<String, String> comment = new HashMap<>();
                    comment.put("storeid", mDetailBean.getData().getStoreid());
//                            Http.getInstance().init(GoodsDetailActivity.this, shoucang_handler, gson.toJson(comment), "address/storedetail", 2).sendMsg();
                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                            gson.toJson(comment));
                    HttpMethods.getInstance().checkStore(
                            new ProgressSubscriber(CheckStoreOnNext, GoodsDetailActivity.this),
                            body);
                    if (mDetailBean.getData().getDescription().equals("")) {
                        mIvDetailPic1.setVisibility(View.GONE);
                        mIvDetailPic2.setVisibility(View.GONE);
                    } else {
                        mWvGoodsDetail.setVisibility(View.VISIBLE);
                        mWvGoodsDetail.loadDataWithBaseURL("x-data://base", mDetailBean.getData().getDescription(), "text/html", "UTF-8", null);
                    }
                    view_pager = (ViewPager) findViewById(R.id.view_pager);
                    List<View> list = new ArrayList<>();
                    LayoutInflater inflater = LayoutInflater.from(GoodsDetailActivity.this);

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
                    mLlDetailBotton.setVisibility(View.VISIBLE);
                }
            }
        };

    }

    @Override
    public void initView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvGoodsComment.setLayoutManager(layoutManager);
        scrollView.smoothScrollTo(0, 0);

        llGoodsDetailShoucang.setOnClickListener(view -> {
            if (Config.IS_LOG) {
                shoucang();
                ivGoodsDetailShoucang.setImageResource(IS_COLLECTED ? R.drawable.v2_0_spxq_shoucang : R.drawable.v2_0_spxq_shoucang_hover);
                mTvShoucang.setTextColor(IS_COLLECTED ?
                        ContextCompat.getColor(GoodsDetailActivity.this, R.color.font_colour_33) :
                        ContextCompat.getColor(GoodsDetailActivity.this, R.color.blue));
                IS_COLLECTED = !IS_COLLECTED;
            } else {
                Util.show(GoodsDetailActivity.this);
            }
        });
    }

    private void shoucang() {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", Config.userBean.getData().getUid());
        param.put("type", IS_COLLECTED ? "DELETE" : "ADD");
        List<Integer> scids = new ArrayList<>();
        scids.add(Integer.valueOf(mDetailBean.getData().getPid()));
        param.put("scids", scids);
        param.put("psid", mDetailBean.getData().getPid());
        param.put("witch", "PRODUCT");
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                gson.toJson(param));
        HttpMethods.getInstance().shoucang(
                new ProgressSubscriber(ShouCangOnNext, GoodsDetailActivity.this),
                body);
    }

    @Override
    public void initData() {
        //商品详情
        Map<String, String> param = new HashMap<>();
        param.put("pid", getIntent().getStringExtra("pid"));
        if (Config.IS_LOG) {
            param.put("uid", Config.userBean.getData().getUid());
        }
//        Http.getInstance().init(this, handler, gson.toJson(param), "order/productinfo", 0).sendMsg();
        HttpMethods.getInstance().checkDetail(
                new ProgressSubscriber(DetailOnNext, GoodsDetailActivity.this),
                RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                        gson.toJson(param)));

        //商品评价
        Map<String, String> comment = new HashMap<>();
        comment.put("pid", getIntent().getStringExtra("pid"));
        HttpMethods.getInstance().findComment(
                new ProgressSubscriber(CommentOnNext, GoodsDetailActivity.this),
                RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                        gson.toJson(comment)));

        updateGouwuche();
    }


    @OnClick({R.id.tv_detail_gouwuche, R.id.tv_add_gouwuche, R.id.tv_buynow, R.id.iv_choose_doc_back,
            R.id.tv_goods_detail_zizhi, R.id.bt_goods_detail_enter_supplier, R.id.bt_goods_detail_all_med})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_detail_gouwuche:
                Util.startIntent(GoodsDetailActivity.this, GouwucheActivity.class);
                break;
            case R.id.tv_add_gouwuche:
                showPopupWindow(mDetailBean.getData().getShopprice(), GoodsDetailActivity.this, mDetailBean.getData().getPnumber());
                break;
            case R.id.tv_buynow:
                if (Config.IS_LOG) {
                    buy_now(mDetailBean.getData().getPid(), "1");
                } else {
                    Util.startIntent(GoodsDetailActivity.this, LoginActivity.class);
                }
                break;
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.tv_goods_detail_zizhi:
                Util.startIntent(GoodsDetailActivity.this.getApplicationContext(), ZizhiActivity.class);
                break;
            case R.id.bt_goods_detail_enter_supplier:
                Intent enter_intent = new Intent(GoodsDetailActivity.this, SupplierActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("store", mMallStoreBean);
                enter_intent.putExtras(bundle);
                startActivity(enter_intent);
                break;
            case R.id.bt_goods_detail_all_med:
                Intent all_intent = new Intent(GoodsDetailActivity.this, AllMedicineActivity.class);
                all_intent.putExtra("sid", mDetailBean.getData().getStoreid());
                startActivity(all_intent);
                break;
        }
    }

    private void initIndicator() {
        ImageView imgView;
        View v = findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标
        ((ViewGroup) v).removeAllViews();
        img_list = new ArrayList<>();
        for (int i = 0; i < url_list.size(); i++) {
            imgView = new ImageView(this);
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
            runOnUiThread(() -> {
                int count = adapter.getCount();
                view_pager.setCurrentItem((curIndex + 1) % count);
            });
        }
    }

    private void startAutoScroll() {
        scheduledExecutorService = Executors
                .newSingleThreadScheduledExecutor();
        // 每隔2秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 2,
                3, TimeUnit.SECONDS);
    }

    private void showPopupWindow(String price, final Context context, int kucun) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popwindow_good_detail, null);
        final SmartImageView smartImageView = (SmartImageView) contentView.findViewById(R.id.iv_pop_goods);
        final TextView tvrepository = (TextView) contentView.findViewById(R.id.tv_repository);
        TextView tvPrice = (TextView) contentView.findViewById(R.id.tv_price);
        TextView tvSubCount = (TextView) contentView.findViewById(R.id.tv_subtraction);
        final TextView tvAddCount = (TextView) contentView.findViewById(R.id.tv_addition);
        final TextView tvGoodCount = (TextView) contentView.findViewById(R.id.tv_good_count);
        TextView tvAddToCar = (TextView) contentView.findViewById(R.id.tv_add_to_car);
        TextView tvBuyNow = (TextView) contentView.findViewById(R.id.tv_buy_now);
        final ListView gouwucheAdd = (ListView) contentView.findViewById(R.id.rv_good_detail_add);
        final ImageInfo info = new ImageInfo();

        SubscriberOnNextListener UpdateGouwucheOnNext = new SubscriberOnNextListener<GouwucheBean.DataBean>() {
            @Override
            public void onNext(GouwucheBean.DataBean dataBeen) {
                ArrayList<ArrayMap<String, Object>> outside_list = new ArrayList<>();//颜色属性数据
                for (GouwucheBean.DataBean.AttrsBean attrsBean : dataBeen.getAttrs()) {
                    ArrayMap<String, Object> mStringObjectMap = new ArrayMap<>();
                    mStringObjectMap.put("type", attrsBean.getAttname());
                    List<String> inside_list = new ArrayList<>();
                    for (GouwucheBean.DataBean.AttrsBean.AttvalBean attvalBean : attrsBean.getAttval()) {
                        inside_list.add(attvalBean.getAttrvalue());
                    }
                    mStringObjectMap.put("lable", inside_list);
                    outside_list.add(mStringObjectMap);
                }
                propertyAdapter = new PropertyAdapter(GoodsDetailActivity.this.getApplicationContext(), outside_list, tvrepository, dataBeen, smartImageView);
                gouwucheAdd.setAdapter(propertyAdapter);
            }
        };

        Map<String, String> gouwuche = new HashMap<>();
        gouwuche.put("skucode", mDetailBean.getData().getSkugid());
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                gson.toJson(gouwuche));
        HttpMethods.getInstance().gouwuche(
                new ProgressSubscriber(UpdateGouwucheOnNext, GoodsDetailActivity.this),
                body);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                true);

        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });

        tvPrice.setText(price);
        if (mDetailBean.getData().getSkugid().equals("0")) {
            tvrepository.setText("库存" + kucun + "件");
        }
        smartImageView.setImageUrl(Config.PIC_URL + mDetailBean.getData().getShowimg());
        if (Integer.parseInt(tvGoodCount.getText().toString()) == 0) {
            tvSubCount.setTextColor(ContextCompat.getColor(this, R.color.font_colour_99));
        }

        tvSubCount.setOnClickListener(v -> {
            int count = Integer.parseInt(tvGoodCount.getText().toString());
            if (count > 1) {
                count--;
            }
            tvGoodCount.setText(String.valueOf(count));
        });

        tvAddCount.setOnClickListener(v -> {
            int count = Integer.parseInt(tvGoodCount.getText().toString());
            count++;
            tvGoodCount.setText(String.valueOf(count));
        });
        tvAddToCar.setOnClickListener(view -> {
            if (mDetailBean.getData().getSkugid().equals("0")) {
                add_to_cart(tvGoodCount.getText().toString(), mDetailBean.getData().getPid());
                popupWindow.dismiss();
            } else {
                String pid = propertyAdapter.getPid();
                if (pid.equals("-1")) {
                    Toast.makeText(GoodsDetailActivity.this, "请选择完整属性", Toast.LENGTH_SHORT).show();
                } else {
                    add_to_cart(tvGoodCount.getText().toString(), pid);
                    popupWindow.dismiss();
                }
            }
            smartImageView.getLocationInWindow(start_location);//获取点击商品图片的位置
            drawable = smartImageView.getDrawable();//复制一个新的商品图标
        });

        smartImageView.setOnClickListener(v -> {
            info.setBigImageUrl(mDetailBean.getData().getSkugid().equals("0") ?
                    Config.PIC_URL + mDetailBean.getData().getShowimg() : propertyAdapter.getUrl());
            info.imageViewWidth = smartImageView.getWidth();
            info.imageViewHeight = smartImageView.getHeight();
            int[] points = new int[2];
            smartImageView.getLocationInWindow(points);
            info.imageViewX = points[0];
            info.imageViewY = points[1];
            Intent intent = new Intent(context, ImagePreviewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("IMAGE_INFO", info);
            intent.putExtras(bundle);
            startActivity(intent);
            ((Activity) context).overridePendingTransition(0, 0);
        });

        tvBuyNow.setOnClickListener(view -> {
                    if (Config.IS_LOG) {
                        if (mDetailBean.getData().getSkugid().equals("0")) {
                            buy_now(mDetailBean.getData().getPid(), tvGoodCount.getText().toString());
                        } else {
                            String pid = propertyAdapter.getPid();
                            if (pid.equals("-1")) {
                                Toast.makeText(GoodsDetailActivity.this, "请选择完整属性", Toast.LENGTH_SHORT).show();
                            } else {
                                buy_now(pid, tvGoodCount.getText().toString());
                            }
                        }
                    } else {
                        Util.startIntent(GoodsDetailActivity.this.getApplicationContext(), LoginActivity.class);
                    }
                }

        );
        popupWindow.showAtLocation(GoodsDetailActivity.this.findViewById(R.id.rl_mine_title),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void updateGouwuche() {
        number = 0;
        //购物车数量
        Map<String, String> gouwuche = new HashMap<>();
        if (Config.IS_LOG) {
            gouwuche.put("uid", Config.userBean.getData().getUid());
            gouwuche.put("sid", MainActivity.szImei);
        } else {
            gouwuche.put("uid", "-1");
            gouwuche.put("sid", MainActivity.szImei);
        }
        gouwuche.put("type", "LIST");
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                gson.toJson(gouwuche));
        HttpMethods.getInstance().updateGouwuche(
                new ProgressSubscriber(UpdateGouwucheOnNext, GoodsDetailActivity.this),
                body);
    }

    private void buy_now(String pid, String num) {
        JsonObject outJson = new JsonObject();
        outJson.addProperty("uid", Config.userBean.getData().getUid());
        JsonArray array = new JsonArray();
        JsonObject arrJson = new JsonObject();
        arrJson.addProperty("pid", pid);
        arrJson.addProperty("num", num);
        array.add(arrJson);
        array1 = array;
        outJson.add("pinfo", array);
        HttpMethods.getInstance().buyNow(
                new ProgressSubscriber(BuyNowOnNext, GoodsDetailActivity.this),
                RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                        gson.toJson(outJson)));
    }

    private void add_to_cart(String num, String pid) {
        JsonObject outJson1 = new JsonObject();
        if (Config.IS_LOG) {
            outJson1.addProperty("uid", Config.userBean.getData().getUid());
        } else {
            outJson1.addProperty("uid", "-1");
            outJson1.addProperty("sid", MainActivity.szImei);
        }

        outJson1.addProperty("type", "ADD");
        outJson1.addProperty("pid", pid);
        outJson1.addProperty("num", num);
        JsonArray array1 = new JsonArray();
        JsonObject arrJson1 = new JsonObject();
        array1.add(arrJson1);
        outJson1.add("cid", array1);
//        Http.getInstance().init(GoodsDetailActivity.this, handler, outJson1.toString(), "order/shoppingCart", 1).sendMsg();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                gson.toJson(outJson1));
        HttpMethods.getInstance().addToGouwuche(
                new ProgressSubscriber(GouwucheOnNext, GoodsDetailActivity.this),
                body);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scheduledExecutorService.shutdown();
    }

    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }

    /**
     * @param vg       动画运行的层 这里是frameLayout
     * @param view     要运行动画的View
     * @param location 动画的起始位置
     * @return view
     * @deprecated 将要执行动画的view 添加到动画层
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                dip2px(this, 90), dip2px(this, 90));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);
        return view;
    }

    private void setAnim(Drawable drawable, int[] start_location) {
        Animation mScaleAnimation = new ScaleAnimation(1.5f, 0.0f, 1.5f, 0.0f, Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f);
        mScaleAnimation.setDuration(AnimationDuration);
        mScaleAnimation.setFillAfter(true);


        final ImageView iview = new ImageView(this);
        iview.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animation_viewGroup, iview, start_location);
        view.setAlpha(0.6f);

        int[] end_location = new int[2];
        mTvDetailGouwuche.getLocationInWindow(end_location);
        int endX = end_location[0];
        int endY = end_location[1] - start_location[1];

        Animation mTranslateAnimation = new TranslateAnimation(0, endX, 0, endY);
        Animation mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(AnimationDuration);
        mTranslateAnimation.setDuration(AnimationDuration);
        AnimationSet mAnimationSet = new AnimationSet(true);
        mAnimationSet.setFillAfter(true);
        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mTranslateAnimation);

        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                num++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                num--;
                if (num == 0) {
                    isClean = true;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(mAnimationSet);
    }

}
