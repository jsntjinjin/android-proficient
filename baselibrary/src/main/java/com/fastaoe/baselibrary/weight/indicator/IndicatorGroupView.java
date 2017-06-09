package com.fastaoe.baselibrary.weight.indicator;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
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

    public void addBottomLine(View bottomView) {
        if (bottomView == null) {
            return;
        }

        this.mBottomView = bottomView;
        addView(mBottomView);
    }
}
