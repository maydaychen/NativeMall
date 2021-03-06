package com.example.nativeMall;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nativeMall.Bean.GoodsDetailBean;
import com.example.nativeMall.ui.Activity.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * 作者：JTR on 2016/8/16 10:49
 * 邮箱：2091320109@qq.com
 */
public class Util {
    private static final String TAG = "chenyi";


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean fileExist(Activity activity, String filePath) {
        String finalPath = activity.getFilesDir().toString() + File.separator + filePath;
        File file = new File(finalPath);
        return file.exists();
    }

    public static void show(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您还未登录，需要立即登录么？");
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton("确认", (dialog, which) -> {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            dialog.dismiss();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void save(Context context, Object object, String dataName) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(dataName,
                Activity.MODE_PRIVATE);
//实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
//用putString的方法保存数据
        editor.putString(dataName, object.toString());
//提交当前数据
        editor.commit();
    }

    public static Object fromJsonToJava(JSONObject json, Class pojo) throws Exception {
        // 首先得到pojo所定义的字段
        Field[] fields = pojo.getDeclaredFields();
        // 根据传入的Class动态生成pojo对象
        Object obj = pojo.newInstance();
        for (Field field : fields) {
            // 设置字段可访问（必须，否则报错）
            field.setAccessible(true);
            // 得到字段的属性名
            String name = field.getName();
            // 这一段的作用是如果字段在JSONObject中不存在会抛出异常，如果出异常，则跳过。
            try {
                json.get(name);
            } catch (Exception ex) {
                continue;
            }
            Boolean t2 = ((json.get(name) != null));
            Boolean t1 = !"".equals(json.getString(name));

            if (json.get(name) != null || !"".equals(json.getString(name))) {
                // 根据字段的类型将值转化为相应的类型，并设置到生成的对象中。
                if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    field.set(obj, Long.parseLong(json.getString(name)));
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, json.getString(name));
                } else if (field.getType().equals(Double.class) || field.getType().equals(double
                        .class)) {
                    field.set(obj, Double.parseDouble(json.getString(name)));
                } else if (field.getType().equals(Integer.class) || field.getType().equals(int
                        .class)) {
                    field.set(obj, Integer.parseInt(json.getString(name)));
                } else if (field.getType().equals(Date.class)) {
                    field.set(obj, Date.parse(json.getString(name)));
                } else {
                }
            }
        }
        return obj;
    }


    //时间转换
    public static String changeDateToStandard(String yMdHms) {
        String date = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date giveTime = df.parse(yMdHms);
            Date currenTime = new Date(System.currentTimeMillis());
            long value = currenTime.getTime() - giveTime.getTime();
            long days = value / (1000 * 60 * 60 * 24);
            long hours = (value - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (value - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) /
                    (1000 * 60);
            if (days > 0) {
                date = String.valueOf(days);
                date = date + "天以前";
            } else if (hours > 1 & hours < 24) {
                date = String.valueOf(hours);
                date = date + "小时以前";
            } else if (minutes > 0 & minutes < 60) {
                date = String.valueOf(minutes);
                date = date + "分钟以前";
            } else {
                date = "1分钟以前";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Nullable
    public static ByteArrayEntity getByteArrayEntity(JSONObject jsonObject) {
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public static void showMsg(Context context, String data) {
        JSONObject json;
        try {
            json = new JSONObject(data);
            Toast.makeText(context, json.getString("msg"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public static Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(target);

        canvas.drawCircle(min / 2, min / 2, min / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    public static void GetCircleImg(String URL, final ImageView imageView) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (!TextUtils.isEmpty(new String(bytes))) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int min = 0;
                    if (width == height) {
                        min = width;
                    } else {
                        min = width > height ? height : width;
                    }
                    Bitmap bitmap1 = Util.createCircleImage(bitmap, min);
                    imageView.setImageBitmap(bitmap1);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    /**
     * 将dp转化为px
     */
    public static int dip2px(float dip, Context context) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }

    public static List<String> formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        List<String> time = new ArrayList<>();

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        time.add(strHour);
        time.add(strMinute);
        time.add(strSecond);

        return time;
    }

    public static int check(List<String> list, List<GoodsDetailBean.ResultBean.OptionsBean> optionsBean) {
        String contect = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                contect = contect + list.get(i);
            } else {
                contect = contect + list.get(i) + "_";
            }

        }
        Log.i(TAG, "check: "+contect);
        for (int i = 0; i < optionsBean.size(); i++) {
            if (optionsBean.get(i).getSpecs().equals(contect)) {
                return i;
            }
        }
        return -1;
    }

    public static String getVersionCode(Context context) {
        String code = "";
        if (context == null) {
            return code;
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            code = info.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return code;
    }

    public static void startIntent(Context context, Class target) {
        Intent intent = new Intent(context, target);
        context.startActivity(intent);
    }
}
