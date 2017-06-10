package com.fastaoe.baselibrary.weight.indicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by jinjin on 17/6/9.
 */

public class IndicatorGroupView extends FrameLayout {

    // 添加view的容器
    private LinearLayout mIndicatorViewGroup;

    // 底部指示器
    private View mBottomView;

    // 一个条目的宽度
    private int mItemWidth;
    // 底部指示器的params
    private LayoutParams layoutParams;
    private int mInitBottomLeftMargin;

    public IndicatorGroupView(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mIndicatorViewGroup = new LinearLayout(context);
        addView(mIndicatorViewGroup);
    }

    public void addItemView(View itemView) {
        mIndicatorViewGroup.addView(itemView);
    }

    public View getItemAt(int position) {
        return mIndicatorViewGroup.getChildAt(position);
    }

    public void addBottomLine(View bottomView, int itemWidth) {
        if (bottomView == null) {
            return;
        }

        this.mBottomView = bottomView;
        this.mItemWidth = itemWidth;
        addView(mBottomView);

        // 设置为底部
        layoutParams = (LayoutParams) mBottomView.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;

        int bottomWidth = layoutParams.width;
        if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            bottomWidth = mItemWidth;
        }
        if (bottomWidth > mItemWidth) {
            bottomWidth = mItemWidth;
        }

        // 设置宽度为一个item的宽度
        layoutParams.width = bottomWidth;

        // 设置居中
        mInitBottomLeftMargin = (mItemWidth - bottomWidth) / 2;
        layoutParams.leftMargin = mInitBottomLeftMargin;
    }

    public void scrollBottomView(int position, float positionOffset) {
        if (mBottomView == null) {
            return;
        }
        int leftMargin = (int) ((position + positionOffset) * mItemWidth);
        layoutParams.leftMargin = leftMargin + mInitBottomLeftMargin;
        mBottomView.setLayoutParams(layoutParams);
    }

    public void scrollBottomView(int position) {
        if (mBottomView == null) {
            return;
        }
        int finalLeftMargin = position * mItemWidth + mInitBottomLeftMargin;
        int currentLeftMargin = layoutParams.leftMargin;
        // 移动的距离
        int temp = finalLeftMargin - currentLeftMargin;
        // 设置动画
        final ValueAnimator valueAnimator =
                ObjectAnimator.ofFloat(currentLeftMargin, finalLeftMargin).setDuration((long) Math.abs(temp * 0.5));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentLeftMargin = (float) valueAnimator.getAnimatedValue();
                layoutParams.leftMargin = (int) currentLeftMargin;
                mBottomView.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }
}
