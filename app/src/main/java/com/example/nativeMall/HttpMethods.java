package com.example.nativeMall;

import com.example.nativeMall.Bean.CommentBean;
import com.example.nativeMall.Bean.DetailBean;
import com.example.nativeMall.Bean.GouwucheBean;
import com.example.nativeMall.Bean.MallStoreBean;
import com.example.nativeMall.Bean.PreOrderBean;
import com.example.nativeMall.Bean.ShoppingCarBean;
import com.example.nativeMall.Bean.UselessBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：JTR on 2016/11/25 10:18
 * 邮箱：2091320109@qq.com
 */
public class HttpMethods {
    public static final String BASE_URL = "http://101.231.124.9:56679/mcmall/phone/";
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private GoodsDetailService movieService;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(GoodsDetailService.class);
    }

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            if (!httpResult.getSuccess().equals("T")) {
            }
            return httpResult.getData();
        }
    }

    private class HttpResultFunc1<T> implements Func1<T, T> {
        @Override
        public T call(T httpResult) {
//            if (!httpResult.getSuccess().equals("T")) {
//            }
            return httpResult;
        }
    }

    public void updateGouwuche(Subscriber<List<ShoppingCarBean.DataBean>> subscriber, RequestBody parm) {
        movieService.getString(parm)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //
    public void gouwuche(Subscriber<GouwucheBean.DataBean> subscriber, RequestBody parm) {
        movieService.gouwuche(parm)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void checkStore(Subscriber<MallStoreBean.DataBean> subscriber, RequestBody parm) {
        movieService.checkStore(parm)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void findComment(Subscriber<List<CommentBean.DataBean>> subscriber, RequestBody parm) {
        movieService.findComment(parm)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void shoucang(Subscriber<UselessBean> subscriber, RequestBody parm) {
        movieService.shoucang(parm)
                .map(new HttpResultFunc1<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void buyNow(Subscriber<PreOrderBean> subscriber, RequestBody parm) {
        movieService.buyNow(parm)
                .map(new HttpResultFunc1<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addToGouwuche(Subscriber<UselessBean> subscriber, RequestBody parm) {
        movieService.addToGouwuche(parm)
                .map(new HttpResultFunc1<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void checkDetail(Subscriber<DetailBean> subscriber, RequestBody parm) {
        movieService.checkDetail(parm)
                .map(new HttpResultFunc1<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
