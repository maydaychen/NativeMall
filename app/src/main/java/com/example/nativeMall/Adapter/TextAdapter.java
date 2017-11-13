package com.example.nativeMall.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nativeMall.R;

import java.util.List;

/**
 * Created by Du on 2016/8/22.
 */
public class TextAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mListData;
    private int selectedPos = -1;
    private String selectedText = "";
    private int normalDrawbleId;
    private int selectedDrawble;


    private View.OnClickListener onClickListener;
    private OnItemClickListener mOnItemClickListener;



    public TextAdapter(Context context, List<String> listData , int sId, int nId) {
        super(context, R.layout.item_choose, listData);
        mContext = context;
        mListData = listData;

        selectedDrawble = sId;
        normalDrawbleId = nId;

        init();
    }

    private void init() {
        onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedPos = (Integer) view.getTag();
                setSelectedPosition(selectedPos);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, selectedPos);
                }
            }
        };
    }


    /**
     * 设置选中的position,并通知列表刷新
     */
    public void setSelectedPosition(int pos) {
        if (mListData != null && pos < mListData.size()) {
            selectedPos = pos;
            selectedText = mListData.get(pos);
            notifyDataSetChanged();
        }

    }

    /**
     * 设置选中的position,但不通知刷新
     */
    public void setSelectedPositionNoNotify(int pos) {
        selectedPos = pos;
        if (mListData != null && pos < mListData.size()) {
            selectedText = mListData.get(pos);
        }
    }

    /**
     * 获取选中的position
     */
    public int getSelectedPosition() {

        if (mListData != null && selectedPos < mListData.size()) {
            return selectedPos;
        }

        return -1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_choose, parent, false);
        } else {
            view = (TextView) convertView;
        }
        view.setGravity(Gravity.CENTER);
        view.setTag(position);
        String mString = "";
        if (mListData != null) {
            if (position < mListData.size()) {
                mString = mListData.get(position);
            }
        }
        view.setText(mString);

        if (selectedText != null && selectedText.equals(mString)) {

            view.setBackgroundResource(selectedDrawble);//设置选中的背景图片或颜色
            view.setTextColor(Color.parseColor("#25afff"));

        } else {
            view.setBackgroundResource(normalDrawbleId);//设置未选中状态背景图片或颜色
            view.setTextColor(Color.parseColor("#666666"));
        }
        view.setPadding(20, 0, 0, 0);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    /**
     * 重新定义菜单选项单击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
