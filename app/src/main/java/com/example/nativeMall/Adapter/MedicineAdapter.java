package com.example.nativeMall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Map;

/**
 * 作者：Administrator on 2016/6/2 10:47
 * 邮箱：2091320109@qq.com
 */
public class MedicineAdapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<Map<String, Object>> mData;

    private HolderView holder;

    public MedicineAdapter(Context context, List<Map<String, Object>> mdata) {
        flater = LayoutInflater.from(context);
        holder = new HolderView();
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
            convertView = flater.inflate(R.layout.item_medicine_detail, null);
            holder.medicine_pic = (SmartImageView) convertView.findViewById(R.id.medicine_pic);
            holder.medicine_name = (TextView) convertView.findViewById(R.id.medicine_name);
            holder.medicine_price = (TextView) convertView.findViewById(R.id.medicine_price);
            holder.medicine_otc = (ImageView) convertView.findViewById(R.id.medicine_otc);
            holder.medicine_res = (TextView) convertView.findViewById(R.id.medicine_res);
            convertView.setTag(holder);
//        } else {
//            holder = (HolderView) convertView.getTag();
//        }


        holder.medicine_pic.setImageUrl(Config.PIC_URL + mData.get(position).get("DRUG_IMG_URL"));
        holder.medicine_name.setText((String) mData.get(position).get("NAME"));
        holder.medicine_price.setText((String) mData.get(position).get("DRUG_PRICE"));
        holder.medicine_res.setText((String) mData.get(position).get("DRUG_SUPPLIER_NAME"));


        return convertView;
    }

    class HolderView {
        SmartImageView medicine_pic;
        TextView medicine_name;
        TextView medicine_price;
        ImageView medicine_otc;
        TextView medicine_res;
    }
}
