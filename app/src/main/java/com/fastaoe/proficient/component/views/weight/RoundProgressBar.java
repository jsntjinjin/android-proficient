package com.fastaoe.proficient.component.views.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.fastaoe.baselibrary.utils.DensityUtil;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 17/6/26.
 * description:
 */

public class RoundProgressBar extends View {

    private int mRoundBackground = Color.BLUE;
    private int mRoundColor = Color.RED;
    private int mProgressTextColor = Color.GRAY;
    private int mProgressTextSize = DensityUtil.sp2px(getContext(), 16);
    private int mRoundWidth = DensityUtil.dip2px(getContext(), 5);

    private Paint mInnerPaint;
    private Paint mRoundPaint;
    private Paint mTextPaint;

    private float progress;
    private String text = "0%";

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mRoundWidth);
        mInnerPaint.setColor(mRoundBackground);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        mRoundPaint = new Paint();
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setStrokeWidth(mRoundWidth);
        mRoundPaint.setColor(mRoundColor);
        mRoundPaint.setStyle(Paint.Style.STROKE);
        mRoundPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);

        mRoundBackground = typedArray.getColor(R.styleable.RoundProgressBar_innerBackground, mRoundBackground);
        mRoundColor = typedArray.getColor(R.styleable.RoundProgressBar_outerBackground, mRoundColor);
        mProgressTextColor = typedArray.getColor(R.styleable.RoundProgressBar_progressTextColor, mProgressTextColor);
        mProgressTextSize = typedArray.getDimensionPixelSize(R.styleable.RoundProgressBar_progressTextSize, mProgressTextSize);
        mRoundWidth = (int) typedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, mRoundWidth);

        typedArray.recycle();
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
        RectF oval = new RectF(
                mRoundWidth / 2 + getPaddingLeft(),
                mRoundWidth / 2 + getPaddingTop(),
                getWidth() - mRoundWidth / 2 - getPaddingRight(),
                getHeight() - mRoundWidth / 2 - getPaddingBottom());
        // background
        canvas.drawArc(oval, 0, 360, false, mInnerPaint);
        // roundColor
        canvas.drawArc(oval, 0, 360 * progress, false, mRoundPaint);
        // text
        text = (int) (progress * 100) + "%";
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, mTextPaint);
    }

    public RoundProgressBar setProgress(float progress) {
        if (progress < 0) {
            progress = 0;
        }
        this.progress = progress;
        invalidate();
        return this;
    }

    public RoundProgressBar setText(String text) {
        this.text = text;
        return this;
    }
}
