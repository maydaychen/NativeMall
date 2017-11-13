package com.example.nativeMall.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nativeMall.Config;
import com.example.nativeMall.R;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2016/10/27 14:57
 * 邮箱：2091320109@qq.com
 */
public class CommentGoodsAdapter extends RecyclerView.Adapter<CommentGoodsAdapter.ViewHolder> implements View.OnClickListener {
    private CommentGoodsAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;
    private Context mContext = null;
    private List<Map<String, String>> mList = new ArrayList();

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public CommentGoodsAdapter(Context context, List<Map<String, Object>> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public List getRate() {
        return mList;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public CommentGoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment_goods, viewGroup, false);
        CommentGoodsAdapter.ViewHolder vh = new CommentGoodsAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final CommentGoodsAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.name.setText((String) mData.get(position).get("name"));
        viewHolder.logo.setImageUrl(Config.PIC_URL + mData.get(position).get("url"));
        LayerDrawable layerDrawable = (LayerDrawable) viewHolder.miaoshu.getProgressDrawable();
        layerDrawable.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.star), PorterDuff.Mode.SRC_ATOP);
        LayerDrawable layerDrawable1 = (LayerDrawable) viewHolder.fuwu.getProgressDrawable();
        layerDrawable1.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.star), PorterDuff.Mode.SRC_ATOP);
        LayerDrawable layerDrawable2 = (LayerDrawable) viewHolder.fahuo.getProgressDrawable();
        layerDrawable2.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.star), PorterDuff.Mode.SRC_ATOP);
        viewHolder.itemView.setTag(position);
        for (int i = 0; i < mData.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("miaoshu", viewHolder.miaoshu.getRating() + "");
            map.put("fuwu", viewHolder.fuwu.getRating() + "");
            map.put("fahuo", viewHolder.fahuo.getRating() + "");
            mList.add(map);
        }
        viewHolder.miaoshu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mList.get(position).put("miaoshu", (int)viewHolder.miaoshu.getRating() + "");
            }
        });
        viewHolder.fuwu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mList.get(position).put("fuwu", (int)viewHolder.miaoshu.getRating() + "");
            }
        });
        viewHolder.fahuo.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mList.get(position).put("fahuo", (int)viewHolder.miaoshu.getRating() + "");
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(CommentGoodsAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SmartImageView logo;
        public TextView name;
        public RatingBar miaoshu;
        public RatingBar fuwu;
        public RatingBar fahuo;

        public ViewHolder(View view) {
            super(view);
            logo = (SmartImageView) view.findViewById(R.id.iv_comment_goods_logo);
            name = (TextView) view.findViewById(R.id.tv_comment_goods_name);
            miaoshu = (RatingBar) view.findViewById(R.id.rb_comment_miaoshu);
            fuwu = (RatingBar) view.findViewById(R.id.rb_comment_fuwu);
            fahuo = (RatingBar) view.findViewById(R.id.rb_comment_fahuo);
        }
    }
}