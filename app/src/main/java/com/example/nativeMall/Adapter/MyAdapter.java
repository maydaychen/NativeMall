package com.example.nativeMall.Adapter;

/**
 * 作者：Administrator on 2016/6/29 10:47
 * 邮箱：2091320109@qq.com
 */

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nativeMall.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

/**
 * 适配器，负责装配 、销毁  数据  和  组件 。
 */
public class MyAdapter extends PagerAdapter {
    private ImageView image;
    private List<View> mList;
    private List<String> murl_list;


    private AsyncImageLoader asyncImageLoader;

    public MyAdapter(List<View> list,List<String> url_list) {
        murl_list = url_list;
        mList = list;
        asyncImageLoader = new AsyncImageLoader();
    }


    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return murl_list.size();
    }


    /**
     * Remove a page for the given position.
     * 滑动过后就销毁 ，销毁当前页的前一个的前一个的页！
     * instantiateItem(View container, int position)
     * This method was deprecated in API level . Use instantiateItem(ViewGroup, int)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView(mList.get(position));

    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }


    /**
     * Create the page for the given position.
     */
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {


        Drawable cachedImage = asyncImageLoader.loadDrawable(
                murl_list.get(position), new AsyncImageLoader.ImageCallback() {

                    public void imageLoaded(Drawable imageDrawable,
                                            String imageUrl) {

                        View view = mList.get(position);
                        image = ((ImageView) view.findViewById(R.id.image));
                        image.setBackground(imageDrawable);
                        container.removeView(mList.get(position));
                        container.addView(mList.get(position));
                        // adapter.notifyDataSetChanged();

                    }
                });

        View view = mList.get(position);
        image = ((ImageView) view.findViewById(R.id.image));
        image.setBackground(cachedImage);

        container.removeView(mList.get(position));
        container.addView(mList.get(position));
        // adapter.notifyDataSetChanged();


        return mList.get(position);

    }

    /**
     * 异步加载图片
     */
    static class AsyncImageLoader {

        // 软引用，使用内存做临时缓存 （程序退出，或内存不够则清除软引用）
        private HashMap<String, SoftReference<Drawable>> imageCache;

        public AsyncImageLoader() {
            imageCache = new HashMap<String, SoftReference<Drawable>>();
        }

        /**
         * 定义回调接口
         */
        public interface ImageCallback {
            void imageLoaded(Drawable imageDrawable, String imageUrl);
        }


        /**
         * 创建子线程加载图片
         * 子线程加载完图片交给handler处理（子线程不能更新ui，而handler处在主线程，可以更新ui）
         * handler又交给imageCallback，imageCallback须要自己来实现，在这里可以对回调参数进行处理
         *
         * @param imageUrl ：须要加载的图片url
         * @param imageCallback：
         * @return drawable
         */
        public Drawable loadDrawable(final String imageUrl,
                                     final ImageCallback imageCallback) {

            //如果缓存中存在图片  ，则首先使用缓存
            if (imageCache.containsKey(imageUrl)) {
                SoftReference<Drawable> softReference = imageCache.get(imageUrl);
                Drawable drawable = softReference.get();
                if (drawable != null) {
                    imageCallback.imageLoaded(drawable, imageUrl);//执行回调
                    return drawable;
                }
            }

            /**
             * 在主线程里执行回调，更新视图
             */
            final Handler handler = new Handler() {
                public void handleMessage(Message message) {
                    imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
                }
            };


            /**
             * 创建子线程访问网络并加载图片 ，把结果交给handler处理
             */
            new Thread() {
                @Override
                public void run() {
                    Drawable drawable = loadImageFromUrl(imageUrl);
                    // 下载完的图片放到缓存里
                    imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                    Message message = handler.obtainMessage(0, drawable);
                    handler.sendMessage(message);
                }
            }.start();

            return null;
        }


        /**
         * 下载图片  （注意HttpClient 和httpUrlConnection的区别）
         */
        public Drawable loadImageFromUrl(String url) {

            try {
                HttpClient client = new DefaultHttpClient();
                client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000*15);
                HttpGet get = new HttpGet(url);
                HttpResponse response;

                response = client.execute(get);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();

                    return Drawable.createFromStream(entity.getContent(),
                            "src");
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}