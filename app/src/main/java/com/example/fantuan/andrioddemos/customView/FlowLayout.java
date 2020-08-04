package com.example.fantuan.andrioddemos.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/***
 * 自定义view：继承viewgroup实现流式布局
 */
public class FlowLayout extends ViewGroup{
    // 存放容器所有的view
    private List<List<View>> mAllViews= new ArrayList<List<View>>();
    // 存放每一行最高的View高度
    private List<Integer> mPerLineMaxHeight= new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        super.generateLayoutParams(p);
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
    // 摆放控件
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mPerLineMaxHeight.clear();

        // 存放每一行的子view
        List<View> lineViews = new ArrayList<>();
        // 每一行已存放view的总宽度
        int totalLineWidth= 0;
        // 每一行最高view的高度
        int lineMaxHeight = 0;
        /*************遍历所有View，将View添加到List<List<View>>集合中***************/
        //获得子View的总个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWith = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if(totalLineWidth + childWith > getWidth()){
                mAllViews.add(lineViews);
                mPerLineMaxHeight.add(lineMaxHeight);
                //开启新的一行
                totalLineWidth = 0;
                lineMaxHeight = 0;
                lineViews = new ArrayList<>();
            }

            totalLineWidth += childWith;
            lineViews.add(childView);
            lineMaxHeight = Math.max(lineMaxHeight, childHeight);
        }

        // 单独处理最后一行
        mAllViews.add(lineViews);
        mPerLineMaxHeight.add(lineMaxHeight);

        /************遍历集合中的所有View并显示出来*****************/

        // 表示一个View和父容器左边的距离
        int mLeft = 0;
        // 表示一个View和父容器顶部的距离
        int mTop = 0;
        for (int i = 0; i < mAllViews.size(); i++) {
            lineViews = mAllViews.get(i);
            lineMaxHeight = mPerLineMaxHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View childView = lineViews.get(j);
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                int leftChild = mLeft + lp.leftMargin;
                int topChild = mTop + lp.topMargin;
                int rightChild = leftChild + childView.getMeasuredWidth();
                int bottomChild = topChild + childView.getMeasuredHeight();
                // 四个参数表示view的左上角和右下角
                childView.layout(leftChild, topChild, rightChild, bottomChild);
                mLeft += lp.leftMargin + childView.getMeasuredWidth() + lp.rightMargin;
            }

            mLeft = 0;
            mTop += lineMaxHeight;
        }
    }
    // 测量控件宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽高的测量模式和测量值
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize= MeasureSpec.getSize(heightMeasureSpec);
        // 获取容器中子view的个数
        int childCount = getChildCount();

        // 每一行的总宽度
        int totalLineWidth = 0;
        // 每一行最高View的高度
        int perLineMaxHeight = 0;
        // 当前viewgroup的总高度
        int totalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //测量子view宽高
            measureChild(childView,widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            //获取子view的测量宽度
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //获取子view的测量高度
            int childHeight= childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if(totalLineWidth + childWidth > withSize) {
                // 统计总高度
                totalHeight +=perLineMaxHeight;
                // 开启新的一行
                totalLineWidth = childWidth;
                perLineMaxHeight = childHeight;
            }else{
                // 记录每一行的宽度
                totalLineWidth += childWidth;
                // 比较每一行最高的view
                perLineMaxHeight = Math.max(perLineMaxHeight, childHeight);
            }

            if(i == childCount -1) {
                totalHeight += perLineMaxHeight;
            }

            //如果高度的测量模式是EXACTLY，则高度用测量值，否则用计算出来的总高度（这时高度的设置为wrap_content）
            heightSize = heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight;
            setMeasuredDimension(withSize, heightSize);
        }
    }
}
