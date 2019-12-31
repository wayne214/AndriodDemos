package com.example.fantuan.andrioddemos.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewGroup extends ViewGroup {
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();


            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
            }

            cr = cl + cWidth;
            cb = ct + cHeight;

            childView.layout(cl, ct, cr, cb);

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*
         * 获得此viewgroup上级容器为为推荐的宽和搞， 以及计算模式
         * */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 计算出所有childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 记录如果是wrap_content时设置的宽高
        int width = 0;
        int height = 0;
        // 获取子view的数量
        int cCount = getChildCount();
        // 默认子view的宽高
        int cWidth = 0;
        int cHeight = 0;

        MarginLayoutParams cParams = null;
        // 计算左面两个view的高度
        int lHeight = 0;
        // 计算右边两个view的高度
        int rHeight = 0;
        // 计算上边两个view的宽度
        int tWidth = 0;
        // 计算下边两个子view的宽度
        int bWidth = 0;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 1) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 2 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }


            width = Math.max(tWidth, bWidth);
            height = Math.max(lHeight, rHeight);

            /*
             * 如果是wrap_content 设置为我们计算的值
             * 否则，直接设置为父容器计算的值
             * */
            setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width,
                    (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);

        }


    }
}
