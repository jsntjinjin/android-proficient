package com.fastaoe.baselibrary.weight.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.fastaoe.baselibrary.R;

/**
 * Created by jinjin on 17/6/9.
 */

public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {

    private IndicatorAdapter mAdapter;

    // 添加view的容器，scrollView只能有一个子View
    private IndicatorGroupView mIndicatorViewGroup;

    // 显示的item的个数
    private int mTabVisibleNums = 0;

    // item的宽度
    private int mItemWidth = 0;

    private ViewPager mViewpager;

    // 当前的位置
    private int mCurrentPosition = 0;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorViewGroup = new IndicatorGroupView(context);
        addView(mIndicatorViewGroup);

        initAttribute(context, attrs);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNums = typedArray.getInteger(R.styleable.TrackIndicatorView_visibleNums, 0);
        typedArray.recycle();
    }

    public void setAdapter(IndicatorAdapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("IndicatorAdapter is empty !");
        }
        this.mAdapter = adapter;

        // 动态添加view
        // 获取条数
        int count = mAdapter.getCount();
        // 循环添加view
        for (int i = 0; i < count; i++) {
            View itemView = mAdapter.getView(i, this);
            mIndicatorViewGroup.addItemView(itemView);

            // 对itemView添加点击事件
            if (mViewpager != null) {
                setItemClick(itemView, i);
            }
        }

        // 默认第一个item被点亮
        mAdapter.highLineIndicator(mIndicatorViewGroup.getItemAt(0));

        mIndicatorViewGroup.addBottomLine(mAdapter.getBottomLine());
    }

    private void setItemClick(View itemView, final int position) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(position);
            }
        });
    }

    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager) {
        if (viewPager == null) {
            throw new NullPointerException("ViewPager is empty !");
        }
        this.mViewpager = viewPager;

        mViewpager.addOnPageChangeListener(this);

        setAdapter(adapter);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 指定item的宽度
            mItemWidth = getItemWidth();

            for (int i = 0; i < mAdapter.getCount(); i++) {
                mIndicatorViewGroup.getItemAt(i).getLayoutParams().width = mItemWidth;
            }
        }
    }

    /**
     * 获取item的宽度
     *
     * @return
     */
    private int getItemWidth() {
        // 首先判断有没有指定
        int parentWidth = getWidth();

        if (mTabVisibleNums != 0) {
            return parentWidth / mTabVisibleNums;
        }

        // 没有指定，获取最宽的
        int itemWidth = 0;
        int maxItemWidth = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            int currentItemWidth = mIndicatorViewGroup.getItemAt(i).getWidth();
            maxItemWidth = Math.max(maxItemWidth, currentItemWidth);
        }

        itemWidth = maxItemWidth;

        // 计算所有item宽度之和是否大于parent的width
        if (maxItemWidth * mAdapter.getCount() < parentWidth) {
            itemWidth = parentWidth / mAdapter.getCount();
        }

        return itemWidth;
    }

    private void scrollCurrentIndicator(int position, float positionOffset) {
        // 当前滑动的位置
        float totalWidth = (position + positionOffset) * mItemWidth;
        // 当前中间tab文字的左边偏移量
        float offsetScroll = (totalWidth - mItemWidth) / 2;
        int finalScroll = (int) (totalWidth - offsetScroll);
        // 滚动
        scrollTo(finalScroll, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 滚动的时候不断调用
        scrollCurrentIndicator(position, positionOffset);

    }

    @Override
    public void onPageSelected(int position) {
        // 将上次的itemview设为默认样式
        mAdapter.restoreLineIndicator(mIndicatorViewGroup.getItemAt(mCurrentPosition));
        this.mCurrentPosition = position;
        // 将这次的itemview设为点亮样式
        mAdapter.highLineIndicator(mIndicatorViewGroup.getItemAt(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
