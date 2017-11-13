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

import com.example.nativeMall.R;

import java.util.List;
import java.util.Map;

/**
 * 作者：JTR on 2016/8/31 14:41
 * 邮箱：2091320109@qq.com
 */
public class GoodsCommentAdapter extends RecyclerView.Adapter<GoodsCommentAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, Object>> mData;
    private Context mContext = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public GoodsCommentAdapter(Context context, List<Map<String, Object>> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goods_comment, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText((String) mData.get(position).get("name"));
        viewHolder.time.setText((String) mData.get(position).get("time"));
        LayerDrawable layerDrawable = (LayerDrawable) viewHolder.miaoshu.getProgressDrawable();
        layerDrawable.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.star), PorterDuff.Mode.SRC_ATOP);
        LayerDrawable layerDrawable1 = (LayerDrawable) viewHolder.fuwu.getProgressDrawable();
        layerDrawable1.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.star), PorterDuff.Mode.SRC_ATOP);
        LayerDrawable layerDrawable2 = (LayerDrawable) viewHolder.fahuo.getProgressDrawable();
        layerDrawable2.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.star), PorterDuff.Mode.SRC_ATOP);
        viewHolder.miaoshu.setRating(Float.valueOf( mData.get(position).get("miaoshu").toString()) );
        viewHolder.fuwu.setRating(Float.valueOf( mData.get(position).get("fuwu").toString()));
        viewHolder.fahuo.setRating(Float.valueOf( mData.get(position).get("fahuo").toString()));
        viewHolder.itemView.setTag(position);
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
            mOnItemClickListener.onItemClick(v, String.valueOf(v.getTag()));
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView name;
        public RatingBar miaoshu;
        public RatingBar fuwu;
        public RatingBar fahuo;

        public ViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.tv_comment_time);
            name = (TextView) view.findViewById(R.id.tv_comment_name);
            miaoshu = (RatingBar) view.findViewById(R.id.rb_comment_miaoshu);
            fuwu = (RatingBar) view.findViewById(R.id.rb_comment_fuwu);
            fahuo = (RatingBar) view.findViewById(R.id.rb_comment_fahuo);
        }


    }
}
