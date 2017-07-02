package com.fastaoe.proficient.component.views.SlidingMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 2017/7/1.
 * description:
 */

public class KGSlidingMenu extends HorizontalScrollView {

    private int mMenuWidth;
    private View menuView;
    private View contentView;

    private boolean isMenuOpen = false;
    private GestureDetector gestureDetector;

    public KGSlidingMenu(Context context) {
        this(context, null);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        gestureDetector = new GestureDetector(context, new GestureDetectorListener());

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.KGSlidingMenu);
        float rightMargin = array.getDimension(
                R.styleable.KGSlidingMenu_slidingRightMargin, ScreenUtils.dip2px(context, 50));
        // 菜单页的宽度是 = 屏幕的宽度 - 右边的一小部分距离
        mMenuWidth = (int) (ScreenUtils.getScreenWidth(context) - rightMargin);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 布局解析完成之后会被调用
        ViewGroup contianer = (ViewGroup) getChildAt(0);

        int childCount = contianer.getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("只能有2个子布局");
        }

        // menu
        menuView = contianer.getChildAt(0);
        ViewGroup.LayoutParams menuLayoutParams = menuView.getLayoutParams();
        menuLayoutParams.width = mMenuWidth;
        menuView.setLayoutParams(menuLayoutParams);

        // content
        contentView = contianer.getChildAt(1);
        ViewGroup.LayoutParams contentLayoutParams = contentView.getLayoutParams();
        contentLayoutParams.width = ScreenUtils.getScreenWidth(getContext());
        contentView.setLayoutParams(contentLayoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 初次进来是menu是关闭的
        scrollTo(mMenuWidth, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (gestureDetector.onTouchEvent(ev)) {
            return gestureDetector.onTouchEvent(ev);
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int currentX = getScrollX();

            if (currentX != mMenuWidth && currentX > mMenuWidth / 2) {
                closeMenu();
            } else if (currentX != 0 && currentX <= mMenuWidth / 2) {
                openMenu();
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private class GestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isMenuOpen && velocityX < 0) {
                // 关闭
                closeMenu();
                return true;
            } else if (!isMenuOpen && velocityX > 0) {
                openMenu();
                return true;
            }
            return false;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 计算梯度 0-1f
        float scale = 1f * l / mMenuWidth;

        // content 缩放 0.8 - 1
        float rightScale = 0.8f + 0.2f * scale;

        ViewCompat.setPivotX(contentView, 0);
        ViewCompat.setPivotY(contentView, contentView.getMeasuredHeight() / 2);
        ViewCompat.setScaleX(contentView, rightScale);
        ViewCompat.setScaleY(contentView, rightScale);


        // menu 透明度 0.5 - 1 缩放 0.8 - 1
        float leftScale = 0.8f + (1 - scale) * 0.2f;
        ViewCompat.setScaleX(menuView, leftScale);
        ViewCompat.setScaleY(menuView, leftScale);

        float leftAlpha = 0.5f + (1 - scale) * 0.5f;
        ViewCompat.setAlpha(menuView, leftAlpha);
    }

    private void openMenu() {
        smoothScrollTo(0, 0);
        isMenuOpen = true;
    }

    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        isMenuOpen = false;
    }
}
