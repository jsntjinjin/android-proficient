package com.fastaoe.proficient.weight.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 2017/6/10.
 */

public class BannerViewPager extends ViewPager {

    private static final int SCROLL_MESSAGE = 0x0001;

    private long mRollTime = 3500;

    private BannerAdapter mBannerAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setCurrentItem(getCurrentItem() + 1);

            // 轮询
            startRoll();
        }
    };

    // View复用
    private List<View> mConvertViews;

    private BannerItemClickListener mListener;

    public interface BannerItemClickListener {
        void onClick(int position);
    }


    public void setOnBannerItenClickListener(BannerItemClickListener listener) {
        this.mListener = listener;
    }

    private BannerScroller bannerScroller;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 改变viewpager切换的速度 -> duration
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            bannerScroller = new BannerScroller(context);
            mScroller.setAccessible(true);
            mScroller.set(this, bannerScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mConvertViews = new ArrayList<>();

    }

    /**
     * 设置内容
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mBannerAdapter = adapter;
        // 设置ViewPager的adapter
        setAdapter(new BannerPagerAdapter());

        // 管理activity的生命周期
        ((Activity)getContext()).getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    /**
     * 设置自动轮播
     */
    public void startRoll() {
        mHandler.removeMessages(SCROLL_MESSAGE);
        mHandler.sendEmptyMessageDelayed(SCROLL_MESSAGE, mRollTime);
    }

    /**
     * 销毁handler并停止发送
     */
    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeMessages(SCROLL_MESSAGE);
        mHandler = null;
        ((Activity)getContext()).getApplication().unregisterActivityLifecycleCallbacks(callbacks);
        super.onDetachedFromWindow();
    }

    /**
     * 设置轮播时间
     *
     * @param time
     */
    public void setRollTime(long time) {
        this.mRollTime = time;
    }

    /**
     * 设置页面切换时间
     *
     * @param duration
     */
    public void setRollChangeDuration(int duration) {
        bannerScroller.setScrollerDuration(duration);
    }

    private class BannerPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // 创建item
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View bannerItemView = mBannerAdapter.getView(position % mBannerAdapter.getCount(), getConvertView());
            container.addView(bannerItemView);
            bannerItemView.setOnClickListener(v -> {
                mListener.onClick(position % mBannerAdapter.getCount());
            });
            return bannerItemView;
        }

        // 销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mConvertViews.add((View) object);
        }
    }

    private View getConvertView() {
        for (View convertView : mConvertViews) {
            if (convertView.getParent() == null) {
                return convertView;
            }
        }
        return null;
    }

    private Application.ActivityLifecycleCallbacks callbacks = new DefaultActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(Activity activity) {
            // 开始轮播
            if (activity == getContext()) {
                mHandler.sendEmptyMessageDelayed(SCROLL_MESSAGE, mRollTime);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            // 停止轮播
            if (activity == getContext()) {
                mHandler.removeMessages(SCROLL_MESSAGE);
            }
        }
    };
}
