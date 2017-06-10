package com.fastaoe.proficient.weight.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fastaoe.baselibrary.utils.DensityUtil;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 2017/6/10.
 */

public class BannerView extends RelativeLayout {

    private BannerViewPager bannerVp;
    private TextView bannerTitle;
    private LinearLayout llDotContainer;
    private RelativeLayout rlBottomRoot;

    private BannerAdapter mBannerAdapter;

    // 选中的点样式
    private Drawable mIndicatorFocusDot;
    // 默认的点样式
    private Drawable mIndicatorNormalDot;
    // 点的位置
    private int mDotGravity = 2;
    // 点的宽度
    private int mDotSize = 8;
    // 点的间距
    private int mDotDistance = 2;
    // 当前点的位置
    private int mCurrentPosition = 0;
    private float mWidthProportion = 8;
    private float mHeightProportion = 3;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 加载布局
        inflate(context, R.layout.weight_banner_view, this);
        initView();

        initAttribute(attrs);
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BannerView);

        // 获取点的位置
        mDotGravity = array.getInt(R.styleable.BannerView_bvDotIndicatorGravity, mDotGravity);
        // 获取点的颜色（默认、选中）
        mIndicatorFocusDot = array.getDrawable(R.styleable.BannerView_bvDotFocus);
        if (mIndicatorFocusDot == null) {
            // 如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorFocusDot = new ColorDrawable(Color.RED);
        }
        mIndicatorNormalDot = array.getDrawable(R.styleable.BannerView_bvDotNormal);
        if (mIndicatorNormalDot == null) {
            // 如果在布局文件中没有配置点的颜色  有一个默认值
            mIndicatorNormalDot = new ColorDrawable(Color.WHITE);
        }
        // 获取点的大小和距离
        mDotSize = (int) array.getDimension(R.styleable.BannerView_bvDotWidth, mDotSize);
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_bvDotMargin, mDotDistance);

        // 底部的颜色
        int mBottomColor = array.getColor(R.styleable.BannerView_bvBottomColor, Color.parseColor("#666666"));
        rlBottomRoot.setBackgroundColor(mBottomColor);

        // 图片宽高比
        mWidthProportion = array.getFloat(R.styleable.BannerView_bvWidthProportion, mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_bvHeightProportion, mHeightProportion);

        array.recycle();
    }

    private void initView() {
        bannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        bannerTitle = (TextView) findViewById(R.id.tv_banner_title);
        llDotContainer = (LinearLayout) findViewById(R.id.ll_dot_container);
        rlBottomRoot = (RelativeLayout) findViewById(R.id.rl_bottom_root);
    }

    public void setBannerAdapter(BannerAdapter adapter) {
        mBannerAdapter = adapter;
        bannerVp.setAdapter(adapter);

        initDotIndicator();

        // 监听当前选中的位置
        bannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                pageSelected(position);
            }
        });

        // 初始化title
        String mCurrentTitle = mBannerAdapter.getBannerTitle(0);
        bannerTitle.setText(mCurrentTitle);

        // 动态指定宽高
        post(() -> {
            int width1 = getMeasuredWidth();
            int height = (int) (width1 * mHeightProportion / mWidthProportion);
            getLayoutParams().height = height;
            bannerVp.getLayoutParams().height = height;
        });

    }

    /**
     * 页面切换设置点的样式和title
     *
     * @param position
     */
    private void pageSelected(int position) {
        // 切换点的样式
        ((DotIndicatorView) llDotContainer.getChildAt(mCurrentPosition)).setDrawable(mIndicatorNormalDot);
        this.mCurrentPosition = position % mBannerAdapter.getCount();
        ((DotIndicatorView) llDotContainer.getChildAt(mCurrentPosition)).setDrawable(mIndicatorFocusDot);
        // 切换title
        String mCurrentTitle = mBannerAdapter.getBannerTitle(mCurrentPosition);
        bannerTitle.setText(mCurrentTitle);
    }

    /**
     * 初始化点的指示器
     */
    private void initDotIndicator() {
        int count = mBannerAdapter.getCount();

        // 点的位置
        llDotContainer.setGravity(getDotGravity());

        for (int i = 0; i < count; i++) {
            // 添加点
            DotIndicatorView view = new DotIndicatorView(getContext());
            // 设置点的样式
            if (i == 0) {
                view.setDrawable(mIndicatorFocusDot);
            } else {
                view.setDrawable(mIndicatorNormalDot);
            }
            // 设置点的大小
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    DensityUtil.dip2px(getContext(), mDotSize), DensityUtil.dip2px(getContext(), mDotSize));
            layoutParams.leftMargin = layoutParams.rightMargin = DensityUtil.dip2px(getContext(), mDotDistance);

            view.setLayoutParams(layoutParams);
            llDotContainer.addView(view);
        }
    }

    public void setOnBannerItemClickListener(BannerViewPager.BannerItemClickListener listener) {
        bannerVp.setOnBannerItenClickListener(listener);
    }

    private int getDotGravity() {
        if (mDotGravity == 0) {
            return Gravity.CENTER;
        } else if (mDotGravity == 1) {
            return Gravity.LEFT;
        } else {
            return Gravity.RIGHT;
        }
    }

    public BannerView setRollTime(int time) {
        bannerVp.setRollTime(time);
        return this;
    }

    public BannerView setRollChangeDuration(int duration) {
        bannerVp.setRollChangeDuration(duration);
        return this;
    }

    public void startRoll() {
        bannerVp.startRoll();
    }
}
