package com.fastaoe.proficient.component.views.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fastaoe.baselibrary.utils.DensityUtil;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 17/6/22.
 * description:
 */

public class StepView extends View {

    private int mBorderWidth = 20; // px
    private int mInnerColor = Color.parseColor("#ff3300");
    private int mOutColor = Color.parseColor("#008888");
    private int mStepTextColor = Color.parseColor("#ff8800");
    private int mStepTextSize = DensityUtil.sp2px(getContext(), 16); //px
    private Paint outPaint;
    private Paint innerPaint;
    private Paint textPaint;

    private int maxStep;
    private int currentStep;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // attrs
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StepView);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.StepView_borderWidth, mBorderWidth);
        mInnerColor = typedArray.getColor(R.styleable.StepView_innerColor, mInnerColor);
        mOutColor = typedArray.getColor(R.styleable.StepView_outColor, mOutColor);
        mStepTextColor = typedArray.getColor(R.styleable.StepView_stepTextColor, mStepTextColor);
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.StepView_stepTextSize, mStepTextSize);
        typedArray.recycle();

        // init paint
        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setStrokeWidth(mBorderWidth);
        outPaint.setColor(mOutColor);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeCap(Paint.Cap.ROUND);

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStrokeWidth(mBorderWidth);
        innerPaint.setColor(mInnerColor);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(mStepTextSize);
        textPaint.setColor(mStepTextColor);
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
        super.onDraw(canvas);

        // out
        RectF oval = new RectF(mBorderWidth / 2, mBorderWidth / 2,
                getWidth() - mBorderWidth / 2, getHeight() - mBorderWidth / 2);
        canvas.drawArc(oval, 135, 270, false, outPaint);

        // inner
        if (maxStep == 0) return;
        float progress = (float)currentStep / (float)maxStep;
        canvas.drawArc(oval, 135, 270 * (progress >= 1 ? 1 : progress), false, innerPaint);

        // text
        String text = currentStep + "";
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;
        // baseline
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseLine, textPaint);

    }

    // 设置最大值
    public synchronized void setMaxStep(int maxStep) {
        this.maxStep = maxStep;
    }

    // 设置当前值
    public synchronized void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
        invalidate();
    }
}
