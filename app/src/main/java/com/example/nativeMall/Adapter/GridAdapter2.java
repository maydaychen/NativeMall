package com.example.nativeMall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/21.
 */
public class GridAdapter2 extends BaseAdapter {
    private LayoutInflater flater;
    private List<HashMap<String, String>> mData;
    private Context mContext;
    private HolderView holder;

    public GridAdapter2(Context context, List<HashMap<String, String>> mdata) {
        mContext = context;
        flater = LayoutInflater.from(context);
        holder = new HolderView();
        mData = mdata;
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = flater.inflate(R.layout.item_grid, null);
            holder.image = convertView.findViewById(R.id.gridimage);
            holder.text = convertView.findViewById(R.id.gridtext);
            convertView.setTag(holder);
        } else {
            holder = (HolderView) convertView.getTag();
        }
        if (position == mData.size()) {
            holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.yuyue_chenggong));
            holder.text.setText("全部");
        } else {
            holder.image.setImageUrl(mData.get(position).get("img_url"));
            holder.text.setText(mData.get(position).get("name"));
        }
        return convertView;
    }

    class HolderView {
        SmartImageView image;
        TextView text;
    }
}
