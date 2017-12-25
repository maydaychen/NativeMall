package com.example.nativeMall.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nativeMall.Bean.GoodsDetailBean;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.MyViewGroup;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：JTR on 2016/10/26 13:22
 * 邮箱：2091320109@qq.com
 */
public class PropertyAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ArrayMap<String, Object>> mList;
    private TextView mTextView;
    private GoodsDetailBean mGouwucheBean;
    private SmartImageView mSmartImageView;
    private String pid;
    private String url;

    //用于保存用户的属性集合
    private ArrayMap<String, String> selectProMap = new ArrayMap<String, String>();

    /**
     * 返回选中的属性
     *
     * @return 选中商品属性
     */
    public ArrayMap<String, String> getSelectProMap() {
        return selectProMap;
    }

    public void setSelectProMap(ArrayMap<String, String> selectProMap) {
        this.selectProMap = selectProMap;
    }

    public String getPid() {
        if (selectProMap.size() == mGouwucheBean.getResult().getSpecs().size()) {
            return pid;
        } else {
            return "-1";
        }
    }

    public String getUrl() {
        if (selectProMap.size() == mGouwucheBean.getResult().getSpecs().size()) {
            return url;
        } else {
            return "-1";
        }
    }

    public PropertyAdapter(Context context, ArrayList<ArrayMap<String, Object>> list, TextView textView, GoodsDetailBean gouwucheBean, SmartImageView smartImageView) {
        super();
        this.mContext = context;
        this.mList = list;
        this.mTextView = textView;
        this.mGouwucheBean = gouwucheBean;
        this.mSmartImageView = smartImageView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 获取list_item布局文件的视图
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.lv_property_item, null, true);
            holder = new ViewHolder();

            // 获取控件对象
            holder.tvPropName = convertView.findViewById(R.id.tv_property_name);
            holder.vgPropContents = convertView.findViewById(R.id.myviewgroup);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.mList != null) {
            ArrayList<String> lables = (ArrayList<String>) this.mList.get(position).get("lable");
            String type = (String) this.mList.get(position).get("type");
            holder.tvPropName.setText(type);//规格名称
            //动态加载标签
            //判断布局中的子控件是否为0，如果不为0，就不添加了，防止ListView滚动时重复添加
            if (holder.vgPropContents.getChildCount() == 0) {
                TextView[] textViews = new TextView[lables.size()];
                //设置每个标签的文本和布局
                //TableRow tr=new TableRow(mContext);

                for (int i = 0; i < lables.size(); i++) {
                    TextView textView = new TextView(mContext);
//                    textView.setGravity(17);
                    textView.setPadding(18, 16, 28, 16);
                    textViews[i] = textView;
                    textViews[i].setBackgroundResource(R.drawable.gouwuche_unselect);
                    textViews[i].setTextColor(Color.parseColor("#333333"));
                    textViews[i].setText(lables.get(i));
                    textViews[i].setGravity(17);
                    textViews[i].setTag(i);
//                    textViews[i].setBackgroundColor(mContext.getResources().getColor(R.color.blue));

                    holder.vgPropContents.addView(textViews[i]);
                }
                //绑定标签的Click事件
                for (TextView textView : textViews) {
                    textView.setTag(textViews);
                    textView.setOnClickListener(new LableClickListener(type));
                }
            }
            /**判断之前是否已选中标签*/
            if (selectProMap.get(type) != null) {
                for (int h = 0; h < holder.vgPropContents.getChildCount(); h++) {
                    TextView v = (TextView) holder.vgPropContents.getChildAt(h);
                    if (selectProMap.get(type).equals(v.getText().toString())) {
                        v.setBackgroundColor(Color.parseColor("#EE5500"));
                        v.setTextColor(Color.parseColor("#FFFFFF"));
                        selectProMap.put(type, v.getText().toString());
                    }
                }
            }

        }
        return convertView;
    }

    /*定义item对象*/
    public class ViewHolder {
        TextView tvPropName;
        MyViewGroup vgPropContents;
    }

    class LableClickListener implements View.OnClickListener {
        private String type;

        public LableClickListener(String type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            TextView[] textViews = (TextView[]) v.getTag();
            TextView tv = (TextView) v;
            for (int i = 0; i < textViews.length; i++) {
                TextView textView = textViews[i];
//            }
//            for (TextView textView : textViews) {
                if (tv.equals(textView)) {
                    textView.setBackgroundResource(R.drawable.gouwuche_selected);
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    selectProMap.put(type, textView.getText().toString());
                    if (selectProMap.size() == mGouwucheBean.getResult().getSpecs().size()) {
                        List<String> list = new ArrayList<String>();
                        for (int j = 0; j < mGouwucheBean.getResult().getSpecs().size(); j++) {
                            String attName = mGouwucheBean.getResult().getSpecs().get(j).getTitle();
                            for (int k = 0; k < mGouwucheBean.getResult().getSpecs().get(j).getItems().size(); k++) {
                                if (selectProMap.get(attName).equals(mGouwucheBean.getResult().getSpecs().get(j).getItems().get(k).getTitle())) {
                                    list.add(mGouwucheBean.getResult().getSpecs().get(j).getItems().get(k).getId());
                                }
                            }
                        }
                        int num = Util.check(list, mGouwucheBean.getResult().getOptions());
                        pid = mGouwucheBean.getResult().getOptions().get(num).getId();
//                        url = Config.PIC_URL + mGouwucheBean.getProducts().get(num).getPinfo().get(0).getShowing();
                        mTextView.setText("库存" +mGouwucheBean.getResult().getOptions().get(num).getStock() + "件");
                        mSmartImageView.setImageUrl(url);
                    }
                } else {
                    textView.setBackgroundResource(R.drawable.gouwuche_unselect);
                    textView.setTextColor(Color.parseColor("#333333"));
                }
            }
        }
    }

}