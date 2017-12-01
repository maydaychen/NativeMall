package com.example.nativeMall.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.GoodsDetailBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Utils;
import com.example.nativeMall.http.HttpJsonMethod;
import com.example.nativeMall.http.ProgressSubscriber;
import com.example.nativeMall.http.SubscriberOnNextListener;
import com.example.nativeMall.ui.widget.GlideImageLoader;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailActivity extends InitActivity {
    @BindView(R.id.iv_shoucang)
    ImageView mIvShoucang;
    @BindView(R.id.tv_shoucang)
    TextView mTvShoucang;
    private SubscriberOnNextListener<JSONObject> goodsOnNext;
    private SubscriberOnNextListener<JSONObject> buyNowOnNext;
    private SubscriberOnNextListener<JSONObject> shoucangOnNext;
    private SubscriberOnNextListener<JSONObject> addCartOnNext;
    private SubscriberOnNextListener<JSONObject> cartNumOnNext;
    private Gson gson = new Gson();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private GoodsDetailBean indexBean;
    private int total = 1;
    private boolean IS_SHOUCANG = false;


    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tv_buynow)
    TextView tvBuynow;
    @BindView(R.id.ll_detail_botton)
    LinearLayout llDetailBotton;
    @BindView(R.id.goods_detail_line)
    TextView goodsDetailLine;
    @BindView(R.id.iv_choose_doc_back)
    ImageView ivChooseDocBack;
    @BindView(R.id.rl_mine_title)
    RelativeLayout rlMineTitle;
    @BindView(R.id.tv_goods_detail_name)
    TextView tvGoodsDetailName;
    @BindView(R.id.tv_goods_detail_price)
    TextView tvGoodsDetailPrice;
    @BindView(R.id.tv_goods_detail_sold)
    TextView tvGoodsDetailSold;
    @BindView(R.id.tv_goods_detail_inventory)
    TextView tvGoodsDetailInventory;
    @BindView(R.id.wv_goods_detail)
    WebView wvGoodsDetail;

    @Override
    protected void onResume() {
        super.onResume();
        total = 1;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        buyNowOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                Log.i("chenyi", "onNext: " + jsonObject.toString());
                Intent intent = new Intent(GoodsDetailActivity.this, ConfirmActivity.class);
                intent.putExtra("objs", jsonObject.toString());
                startActivity(intent);
            } else {
                Toast.makeText(GoodsDetailActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        goodsOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                indexBean = gson.fromJson(jsonObject.toString(), GoodsDetailBean.class);
                mBanner.setImages(indexBean.getResult().getPics()).setImageLoader(new GlideImageLoader()).start();
                mBanner.isAutoPlay(false);
                mBanner.setOnBannerListener(position -> Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show());
                tvGoodsDetailName.setText(indexBean.getResult().getGoods().getTitle());
                tvGoodsDetailPrice.setText(String.format(getResources().getString(R.string.price_detail), indexBean.getResult().getGoods().getMarketprice()));
                tvGoodsDetailInventory.setText(String.format(getResources().getString(R.string.goods_detail_inventory), indexBean.getResult().getGoods().getTotal()));
                tvGoodsDetailSold.setText(String.format(getResources().getString(R.string.goods_detail_sold), indexBean.getResult().getGoods().getSales()));
                IS_SHOUCANG = indexBean.getResult().isIsfavorite();
                mIvShoucang.setImageDrawable(indexBean.getResult().isIsfavorite() ? getResources().getDrawable(R.drawable.v2_0_spxq_shoucang_hover) : getResources().getDrawable(R.drawable.v2_0_spxq_shoucang));
                mTvShoucang.setText(indexBean.getResult().isIsfavorite() ? "已收藏" : "收藏");
                wvGoodsDetail.getSettings().setJavaScriptEnabled(true);
//                String css = "<link rel=\"stylesheet\" href=\"" + s.getCss().get(0) + "\" type=\"text/css\">";
                String html = "<html><head>" + "</head><body>" + indexBean.getResult().getGoods().getContent() + "</body></html>";
                html = html.replace("<div class=\"img-place-holder\">", "");
                wvGoodsDetail.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);

            } else {
                Toast.makeText(GoodsDetailActivity.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
            }
        };
        shoucangOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                if (IS_SHOUCANG) {
                    Toast.makeText(GoodsDetailActivity.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                    mIvShoucang.setImageDrawable(getResources().getDrawable(R.drawable.v2_0_spxq_shoucang));
                    mTvShoucang.setText("收藏");

                } else {
                    Toast.makeText(GoodsDetailActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                    mIvShoucang.setImageDrawable(getResources().getDrawable(R.drawable.v2_0_spxq_shoucang_hover));
                    mTvShoucang.setText("已收藏");
                }
                IS_SHOUCANG = !IS_SHOUCANG;
            }
        };
        addCartOnNext = jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
                Toast.makeText(GoodsDetailActivity.this, "加入购物车成功！", Toast.LENGTH_SHORT).show();
            }
        };
        cartNumOnNext= jsonObject -> {
            if (jsonObject.getInt("statusCode") == 1) {
            }
        };
        initDetail();
        initCartNum();
    }

    private void initDetail() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "id=" + getIntent().getStringExtra("id") + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().good_detail(
                new ProgressSubscriber(goodsOnNext, GoodsDetailActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
                getIntent().getStringExtra("id"), sign, time);
    }

    private void initCartNum() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().cartNum(
                new ProgressSubscriber(cartNumOnNext, GoodsDetailActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), sign, time);
    }

    private void buy_now(int optionId) {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
//        sign = sign + "cartids=" + pid + "&";
        sign = sign + "goodsid=" + getIntent().getStringExtra("id") + "&";
        sign = sign + "optionid=" + optionId + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "total=" + total + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().buy_now(
                new ProgressSubscriber(buyNowOnNext, GoodsDetailActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
                getIntent().getStringExtra("id"), optionId, "", total, sign, time);
    }

    private void add_shoucang() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "goodsid=" + getIntent().getStringExtra("id") + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().add_shoucang(
                new ProgressSubscriber(shoucangOnNext, GoodsDetailActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
                getIntent().getStringExtra("id"), sign, time);
    }

    private void delete_shoucang() {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "goodsid=" + getIntent().getStringExtra("id") + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().delete_shoucang(
                new ProgressSubscriber(shoucangOnNext, GoodsDetailActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
                getIntent().getStringExtra("id"), sign, time);
    }

    private void addCart(String optionId) {
        String sign = "";
        int time = (int) (System.currentTimeMillis() / 1000);
        sign = sign + "goodsid=" + getIntent().getStringExtra("id") + "&";
        sign = sign + "optionid=" + optionId + "&";
        sign = sign + "sessionkey=" + preferences.getString("sessionkey", "") + "&";
        sign = sign + "timestamp=" + time + "&";
        sign = sign + "total=" + total + "&";
        sign = sign + "key=" + preferences.getString("auth_key", "");
        sign = Utils.md5(sign);
        HttpJsonMethod.getInstance().addCart(
                new ProgressSubscriber(addCartOnNext, GoodsDetailActivity.this),
                preferences.getString("access_token", ""), preferences.getString("sessionkey", ""),
                getIntent().getStringExtra("id"), total, optionId, sign, time);
    }

    @OnClick({R.id.rl_detail_shoucang, R.id.rl_detail_gouwuche, R.id.tv_add_gouwuche, R.id.tv_buynow, R.id.iv_choose_doc_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_detail_shoucang:
                if (indexBean.getResult().isIsfavorite()) {
                    delete_shoucang();
                } else {
                    add_shoucang();
                }
                break;
            case R.id.rl_detail_gouwuche:
                break;
            case R.id.tv_add_gouwuche:
                addCart("847");
                break;
            case R.id.tv_buynow:
                buy_now(847);
                break;
            case R.id.iv_choose_doc_back:
                finish();
                break;
            default:
                break;
        }
    }

    //    private void showPopupWindow(Context context) {
//        View contentView = LayoutInflater.from(this).inflate(
//                R.layout.popwindow_good_detail, null);
//        final SmartImageView smartImageView = contentView.findViewById(R.id.iv_pop_goods);
//        final TextView tvrepository = contentView.findViewById(R.id.tv_repository);
//        TextView tvPrice = contentView.findViewById(R.id.tv_price);
//        TextView tvSubCount = contentView.findViewById(R.id.tv_subtraction);
//        final TextView tvAddCount = contentView.findViewById(R.id.tv_addition);
//        final TextView tvGoodCount = contentView.findViewById(R.id.tv_good_count);
//        TextView tvBuyNow = (TextView) contentView.findViewById(R.id.tv_buy_now);
//        final ImageInfo info = new ImageInfo();
//
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
//                true);
//        tvGoodCount.setText(total + "");
//        tvAddCount.setOnClickListener(v -> {
//
//            total++;
//            tvGoodCount.setText(total + "");
//        });
//        tvSubCount.setOnClickListener(v -> {
//            if (total > 1) {
//                total--;
//                tvGoodCount.setText(total + "");
//            }
//        });
//        tvBuyNow.setOnClickListener(v -> {
//            popupWindow.dismiss();
//            HttpJsonMethod.getInstance().buy_now(
//                    new ProgressSubscriber(buyNowOnNext, GoodsDetailActivity.this),
//                    preferences.getString("access_token", ""), preferences.getString("sessionkey", ""), getIntent().getStringExtra("id"), tvGoodCount.getText().toString());
//        });
//        tvPrice.setText(String.format(getResources().getString(R.string.price), indexBean.getResult().getGoods().getMarketprice()));
//        tvrepository.setText(String.format(getResources().getString(R.string.goods_detail_inventory), indexBean.getResult().getGoods().getTotal()));
//        smartImageView.setImageUrl(indexBean.getResult().getGoods().getThumb());
//        smartImageView.setOnClickListener(v -> {
//            info.setBigImageUrl(indexBean.getResult().getGoods().getThumb());
//            info.imageViewWidth = smartImageView.getWidth();
//            info.imageViewHeight = smartImageView.getHeight();
//            int[] points = new int[2];
//            smartImageView.getLocationInWindow(points);
//            info.imageViewX = points[0];
//            info.imageViewY = points[1];
//            Intent intent = new Intent(context, ImagePreviewActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("IMAGE_INFO", info);
//            intent.putExtras(bundle);
//            startActivity(intent);
////            ((Activity) context).overridePendingTransition(0, 0);
//        });
//        ColorDrawable dw = new ColorDrawable(0x00000000);
//        popupWindow.setBackgroundDrawable(dw);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.4f;
//        getWindow().setAttributes(lp);
//        popupWindow.setOnDismissListener(() -> {
//            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
//            lp1.alpha = 1f;
//            getWindow().setAttributes(lp1);
//        });
//
//        popupWindow.showAtLocation(GoodsDetailActivity.this.findViewById(R.id.rl_mine_title),
//                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//
//    }
}
