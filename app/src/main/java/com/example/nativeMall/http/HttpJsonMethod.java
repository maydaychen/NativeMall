package com.example.nativeMall.http;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by user on 2017/7/13.
 */

public class HttpJsonMethod {
    public static final String BASE_URL = "https://ganglong.wshoto.com";
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private BlueService movieService;

    private HttpJsonMethod() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).build();


        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(BlueService.class);
    }

    private static class SingletonHolder {
        private static final HttpJsonMethod INSTANCE = new HttpJsonMethod();
    }

    //获取单例
    public static HttpJsonMethod getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            return httpResult.getOthers();
        }
    }


    public void get_token(Subscriber<JSONObject> subscriber, String apiname, String apipass) {
        movieService.index_info(apiname, apipass)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void send_code(Subscriber<JSONObject> subscriber, String accessToken, String mobile, String sign, int timestamp) {
        movieService.send_code(accessToken, mobile, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void login(Subscriber<JSONObject> subscriber, String accessToken, String device_tokens, String kapkey, String mobile, String sign, int timestamp) {
        movieService.login(accessToken, device_tokens, kapkey, mobile, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void get_banner(Subscriber<JSONObject> subscriber, String accessToken, String sign, int timestamp) {
        movieService.get_banner(accessToken, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void get_category(Subscriber<JSONObject> subscriber, String accessToken, String sign, int timestamp) {
        movieService.getCategory(accessToken, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCategoryGoods(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String ccate, String pcate, int pnum, String sorttype, String sign, int timestamp) {
        movieService.getCategoryGoods(accessToken, sessionKey, ccate, pcate, 10, pnum, sorttype, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getRightCategory(Subscriber<JSONObject> subscriber, String accessToken, String pid, String sign, int timestamp) {
        movieService.getRightCategory(accessToken, pid, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getAttributes(Subscriber<JSONObject> subscriber, String accessToken, String sortType, String attributes, int page, int psize, String sign, int timestamp) {
        movieService.getAttributes(accessToken, sortType, attributes, page, psize, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void good_detail(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String id, String sign, int timestamp) {
        movieService.good_detail(accessToken, sessionKey, id, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void buy_now(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String goodsid, String optionid, String cartids, String total, String sign, int timestamp) {
        movieService.buy_now(accessToken, sessionKey, goodsid, optionid, cartids, total, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void confirm_order(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String goods, String addressid, String dispatchid, String remark, String sign, int timestamp) {
        movieService.confirm_order(accessToken, sessionKey, goods, addressid, dispatchid, remark, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void pay_now(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String ordersn, String type, String sign, int timestamp) {
        movieService.pay_now(accessToken, sessionKey, ordersn, type, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void address_list(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String sign, int timestamp) {
        movieService.addressList(accessToken, sessionKey, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void deleteAddress(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String addressid, String sign, int timestamp) {
        movieService.deleteAddress(accessToken, sessionKey, addressid, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addAddress(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey,
                           String realname, String mobile, String province, String city, String area,
                           String address, String sign, int timestamp) {
        movieService.addAddress(accessToken, sessionKey, realname, mobile, province, city, area, address, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void member_info(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String sign, int timestamp) {
        movieService.member_info(accessToken, sessionKey, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void shoucang_list(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, int page, String sign, int timestamp) {
        movieService.shoucang_list(accessToken, sessionKey, "id,thumb,title,productprice,marketprice", page, 10, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void add_shoucang(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String goodsid, String sign, int timestamp) {
        movieService.add_shoucang(accessToken, sessionKey, goodsid, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void delete_shoucang(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String goodsid, String sign, int timestamp) {
        movieService.delete_shoucang(accessToken, sessionKey, goodsid, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void history_list(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, int page, String sign, int timestamp) {
        movieService.history_list(accessToken, sessionKey, "id,thumb,title,productprice,marketprice", page, 10, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void cartsList(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String sign, int timestamp) {
        movieService.cartsList(accessToken, sessionKey, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addCart(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String goodsid, int total, String optionId, String sign, int timestamp) {
        movieService.addCart(accessToken, sessionKey, goodsid, total, optionId, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void changeCart(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String cartid, String type, String sign, int timestamp) {
        movieService.changeCart(accessToken, sessionKey, cartid, type, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void cartNum(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String sign, int timestamp) {
        movieService.cartNum(accessToken, sessionKey, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void deleteCart(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String id, String sign, int timestamp) {
        movieService.deleteCart(accessToken, sessionKey, id, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void search_goods(Subscriber<JSONObject> subscriber, String accessToken, String keywords, String sessionKey, String sign, int timestamp) {
        movieService.searchGoods(accessToken, keywords, sessionKey, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCode(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String sign, int timestamp) {
        movieService.getCode(accessToken, sessionKey, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void tixianList(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String sign, int timestamp) {
        movieService.tixianList(accessToken, sessionKey, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void couponList(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String page, String status, String psize, String sign, int timestamp) {
        movieService.couponList(accessToken, sessionKey, page, status, psize, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void useCouponList(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String goods, String sign, int timestamp) {
        movieService.useCouponList(accessToken, sessionKey, goods, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void couponTitle(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String sign, int timestamp) {
        movieService.couponTitle(accessToken, sessionKey, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void couponDetail(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String id, String sign, int timestamp) {
        movieService.couponDetail(accessToken, sessionKey, id, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void usefulCouponList(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String page, String catid, String psize, String sign, int timestamp) {
        movieService.usefulCouponList(accessToken, sessionKey, page, catid, psize, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void orderList(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String page, String status, String psize, String sign, int timestamp) {
        movieService.orderList(accessToken, sessionKey, page, status, psize, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getAva(Subscriber<JSONObject> subscriber, String access_token, String avatar, String session, String sign, int timestamp) {
        movieService.getAva(access_token, avatar, session, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void patenerList(Subscriber<JSONObject> subscriber, String accessToken, String sessionKey, String page, String type, String psize, String sign, int timestamp) {
        movieService.patenerList(accessToken, sessionKey, page, type, psize, sign, timestamp)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
