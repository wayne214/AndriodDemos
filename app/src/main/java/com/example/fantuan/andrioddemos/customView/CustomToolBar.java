package com.example.fantuan.andrioddemos.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fantuan.andrioddemos.R;

/**
 * 继承现有控件：自定义view
 */

public class CustomToolBar extends RelativeLayout {

    private ImageView leftImg, rightImg;
    private TextView titleTextView;

    /**
     * 接口回调
     */
    // 1.声明一个接口
    public interface ImgClickListener {
        public void leftImgClick();

        public void rightImgClick();
    }

    // 2. 创建一个接口变量
    private ImgClickListener imgClickListener;

    // 3.为接口声明一个方法
    public void setImgClickListener(ImgClickListener imgClickListener) {
        this.imgClickListener = imgClickListener;
    }


    public CustomToolBar(Context context) {
        this(context, null);
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 创建中间titleText
        titleTextView = new TextView(context);
        // 创建左右image
        leftImg = new ImageView(context);
        leftImg.setPadding(12, 12, 12, 12);
        rightImg = new ImageView(context);
        rightImg.setPadding(12, 12, 12, 12);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar);
        String titleText = ta.getString(R.styleable.CustomToolBar_myTitleText);
        // 第二个参数表示默认颜色
        int titleTextColor = ta.getColor(R.styleable.CustomToolBar_myTitleTextColor, Color.BLACK);
        // 已经有sp转为px
        float titleTextSize = ta.getDimension(R.styleable.CustomToolBar_myTitleTextSize, 12);

        // 读取图片
        Drawable leftDrawable = ta.getDrawable(R.styleable.CustomToolBar_leftImageSrc);
        Drawable rightDrawable = ta.getDrawable(R.styleable.CustomToolBar_rightImageSrc);
        // 回收TypedArray
        ta.recycle();

        leftImg.setImageDrawable(leftDrawable);
        rightImg.setImageDrawable(rightDrawable);

        titleTextView.setText(titleText);
        titleTextView.setTextColor(titleTextColor);
        titleTextView.setTextSize(titleTextSize);


        LayoutParams leftParams = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));
        leftParams.addRule(ALIGN_PARENT_LEFT, TRUE);

        this.addView(leftImg, leftParams);

        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleParams.addRule(CENTER_IN_PARENT, TRUE);

        addView(titleTextView, titleParams);

        LayoutParams rightParams = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));
        rightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);

        addView(rightImg, rightParams);

        //4.点击ImageView时调用接口中的方法
        leftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgClickListener != null) {
                    imgClickListener.leftImgClick();
                }
            }
        });

        rightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgClickListener != null) {
                    imgClickListener.rightImgClick();
                }
            }
        });

    }
}
