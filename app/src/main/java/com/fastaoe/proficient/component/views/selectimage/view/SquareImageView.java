package com.fastaoe.proficient.component.views.selectimage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jinjin on 2017/6/13.
 * description: 正方形图片
 */

public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 自定义View
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        // 设置宽高为一样
        setMeasuredDimension(width, height);
    }
}

