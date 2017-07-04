package com.fastaoe.proficient.component.views.SlidingMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 2017/7/4.
 * description:
 */

public class QQSlidingMenu extends HorizontalScrollView {

    private int mMenuWidth;
    private View mMenuView;
    private View mContentView;
    private boolean isMenuOpen = false;
    private GestureDetector gestureDetector;
    private View shadowView;
    private boolean isIntercept;

    public QQSlidingMenu(Context context) {
        this(context, null);
    }

    public QQSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        gestureDetector = new GestureDetector(getContext(), new MyGestureDetector());

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQSlidingMenu);
        float rightMargin = array.getDimension(
                R.styleable.KGSlidingMenu_slidingRightMargin, ScreenUtils.dip2px(context, 50));
        // 菜单页的宽度是 = 屏幕的宽度 - 右边的一小部分距离
        mMenuWidth = (int) (ScreenUtils.getScreenWidth(context) - rightMargin);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ViewGroup contianer = (ViewGroup) getChildAt(0);
        if (contianer.getChildCount() != 2) {
            throw new RuntimeException("只能有2个子布局");
        }

        // menu
        mMenuView = contianer.getChildAt(0);
        ViewGroup.LayoutParams mMenuViewLayoutParams = mMenuView.getLayoutParams();
        mMenuViewLayoutParams.width = mMenuWidth;
        mMenuView.setLayoutParams(mMenuViewLayoutParams);

        // content
        mContentView = contianer.getChildAt(1);
        ViewGroup.LayoutParams mContentViewLayoutParams = mContentView.getLayoutParams();
        mContentViewLayoutParams.width = ScreenUtils.getScreenWidth(getContext());

        contianer.removeView(mContentView);
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.addView(mContentView);
        shadowView = new View(getContext());
        shadowView.setBackgroundColor(Color.parseColor("#55000000"));
        relativeLayout.addView(shadowView);

        relativeLayout.setLayoutParams(mContentViewLayoutParams);
        contianer.addView(relativeLayout);
        shadowView.setAlpha(0.0f);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isMenuOpen) {
            int currentX = (int) ev.getX();
            if (ev.getAction() == MotionEvent.ACTION_UP && currentX > mMenuWidth) {
                closeMenu();
                return false;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (isMenuOpen) {
//            int currentX = (int) ev.getX();
//            if (ev.getAction() == MotionEvent.ACTION_UP && currentX > mMenuWidth) {
//                isIntercept = true;
//                closeMenu();
//                return true;
//            }
//        }
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isIntercept) {
            isIntercept = false;
            return true;
        }
        if (gestureDetector.onTouchEvent(ev)) {
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int currentX = getScrollX();
            if (currentX < mMenuWidth && currentX > mMenuWidth / 2) {
                closeMenu();
            } else if (currentX < mMenuWidth / 2 && currentX > 0) {
                openMenu();
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isMenuOpen && velocityX < 0) {
                closeMenu();
                return true;
            }
            if (!isMenuOpen && velocityX > 0) {
                openMenu();
                return true;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mMenuWidth, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 计算梯度 0-1f
        float scale = 1f * l / mMenuWidth;

        // menu
        ViewCompat.setTranslationX(mMenuView, 0.7f * l);

        // content
        shadowView.setAlpha(1 - scale);
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
