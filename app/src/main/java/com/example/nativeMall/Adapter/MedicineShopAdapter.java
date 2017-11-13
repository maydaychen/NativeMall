package com.example.nativeMall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2016/10/18 10:40
 * 邮箱：2091320109@qq.com
 */
public class MedicineShopAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<Map<String, String>> mData;

    private MedicineShopAdapter.HolderView holder;

    public MedicineShopAdapter(Context context, List<Map<String, String>> mdata) {
        flater = LayoutInflater.from(context);
        holder = new MedicineShopAdapter.HolderView();
        mData = mdata;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        if (null == convertView) {
        convertView = flater.inflate(R.layout.item_medicine_shop, null);
        holder.medicine_pic = (SmartImageView) convertView.findViewById(R.id.iv_search_shop);
        holder.medicine_name = (TextView) convertView.findViewById(R.id.tv_search_shop);
        convertView.setTag(holder);
//        } else {
//            holder = (HolderView) convertView.getTag();
//        }
        holder.medicine_pic.setImageUrl(Config.PIC_URL + mData.get(position).get("logo"));
        holder.medicine_name.setText(mData.get(position).get("name"));
        return convertView;
    }

    class HolderView {
        SmartImageView medicine_pic;
        TextView medicine_name;}
}