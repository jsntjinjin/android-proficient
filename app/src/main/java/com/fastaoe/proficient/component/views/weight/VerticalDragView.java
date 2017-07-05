package com.fastaoe.proficient.component.views.weight;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by jinjin on 2017/7/4.
 * description:
 */

public class VerticalDragView extends FrameLayout {

    private ViewDragHelper helper;

    private View viewDragListView;
    private int menuHeight;
    private boolean isMenuOpen = false;

    public VerticalDragView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        helper = ViewDragHelper.create(this, mViewDragHelperCallback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("child views should be only two !");
        }

        viewDragListView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuHeight = getChildAt(0).getMeasuredHeight();
    }

    private float currentY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isMenuOpen) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentY = ev.getY();
                helper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentY < ev.getY() && !canChildScrollUp(viewDragListView)) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (helper.continueSettling(true)) {
            invalidate();
        }
    }

    private ViewDragHelper.Callback mViewDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == viewDragListView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top < 0) {
                top = 0;
            }

            if (top >= menuHeight) {
                top = menuHeight;
            }

            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (viewDragListView.getTop() > menuHeight / 2) {
                // open
                helper.settleCapturedViewAt(0, menuHeight);
                isMenuOpen = true;
            } else {
                // close
                helper.settleCapturedViewAt(0, 0);
                isMenuOpen = false;
            }
            invalidate();
        }
    };

    /**
     * @return Whether it is possible for the child view of this layout to
     *         scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
    }
}
