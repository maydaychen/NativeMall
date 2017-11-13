package com.example.nativeMall.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nativeMall.R;


/**
 * Created by Du on 2016/9/4.
 */
public class CustomTitle extends RelativeLayout {

    private ImageView ivLeft, ivRight;
    private TextView tvTitle;

    public void setIvLeft(ImageView ivLeft) {
        this.ivLeft = ivLeft;
    }

    public void setIvRight(ImageView ivRight) {
        this.ivRight = ivRight;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle.setText(tvTitle);
    }

    private LeftRightImgClickListener leftRightImgClickListener = null;

    public interface LeftRightImgClickListener {
        void leftClick(Boolean click);

        void rightClick(Boolean click);

    }

    public CustomTitle(Context context) {
        super(context);
    }

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.custom_title, this);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitle);
        Drawable drawableLeft = typedArray.getDrawable(R.styleable.CustomTitle_srcLeftIv);
        if (drawableLeft != null) ivLeft.setImageDrawable(drawableLeft);
        Drawable drawableRight = typedArray.getDrawable(R.styleable.CustomTitle_srcRightIv);
        if (drawableRight != null) ivRight.setImageDrawable(drawableRight);
        CharSequence textTitle = typedArray.getText(R.styleable.CustomTitle_textTitle);
        if (textTitle != null) tvTitle.setText(textTitle);

        typedArray.recycle();

        ivLeft.setOnClickListener(view -> leftRightImgClickListener.leftClick(true));
        ivRight.setOnClickListener(view -> leftRightImgClickListener.rightClick(true));
    }

    public void setLeftRightImgClickListener(LeftRightImgClickListener leftRightImgClickListener) {

        this.leftRightImgClickListener = leftRightImgClickListener;
    }
}
