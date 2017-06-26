package com.fastaoe.proficient.component.views.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fastaoe.baselibrary.utils.DensityUtil;

/**
 * Created by jinjin on 17/6/26.
 * description:
 */

public class ShapeLoadingView extends View {

    private Shape mCurrentShape = Shape.Circle;
    private Paint mPaint;
    private Path path;

    public enum Shape {
        Circle, Square, Triangle
    }

    public ShapeLoadingView(Context context) {
        this(context, null);
    }

    public ShapeLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureSize(widthMeasureSpec);
        int height = measureSize(heightMeasureSpec);
        int finalSize = Math.min(width, height);
        setMeasuredDimension(finalSize, finalSize);
    }

    private int measureSize(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = DensityUtil.dip2px(getContext(), 200);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mCurrentShape) {
            case Circle:
                mPaint.setColor(Color.RED);
                int center = getWidth() / 2;
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                mPaint.setColor(Color.BLUE);
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case Triangle:
                mPaint.setColor(Color.GREEN);
                if (path == null) {
                    path = new Path();
                    int height = (int) (Math.sqrt(3) * getWidth() / 2);
                    path.moveTo(getWidth() / 2, getHeight() - height);
                    path.lineTo(0, getHeight());
                    path.lineTo(getWidth(), getHeight());
                    path.close();
                }
                canvas.drawPath(path, mPaint);
                break;
        }
    }

    // run
    public void execute() {
        switch (mCurrentShape) {
            case Circle:
                mCurrentShape = Shape.Square;
                break;
            case Square:
                mCurrentShape = Shape.Triangle;
                break;
            case Triangle:
                mCurrentShape = Shape.Circle;
                break;
        }
        invalidate();
    }
}
