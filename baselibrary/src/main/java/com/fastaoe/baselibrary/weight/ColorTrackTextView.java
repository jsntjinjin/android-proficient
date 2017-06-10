package com.fastaoe.baselibrary.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.fastaoe.baselibrary.R;

import static com.fastaoe.baselibrary.weight.ColorTrackTextView.Direction.LEFT_TO_RIGHT;

/**
 * Created by jinjin on 2017/6/8.
 */

public class ColorTrackTextView extends AppCompatTextView {

    private Paint mOriginPaint;
    private Paint mChangePaint;

    // 变色进度
    private float mProgress = 0.0f;

    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint(context, attrs);

    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);

        int originColor = array.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor());
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());

        mOriginPaint = getPaintByColor(originColor);
        mChangePaint = getPaintByColor(changeColor);

        array.recycle();

    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        // 颜色
        paint.setColor(color);
        // 抗锯齿
        paint.setAntiAlias(true);
        // 防抖动
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }

    // 一个颜色两种颜色
    @Override
    protected void onDraw(Canvas canvas) {
        // 通过进度将变色的位置计算出来
        float middle = mProgress * getWidth();

        if (mDirection == LEFT_TO_RIGHT) {
            drawText(canvas, mChangePaint, 0, middle);
            drawText(canvas, mOriginPaint, middle, getWidth());
        } else {
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
            drawText(canvas, mOriginPaint, 0, getWidth() - middle);
        }

    }

    private void drawText(Canvas canvas, Paint paint, float start, float end) {
        canvas.save();
        // 获取字体的宽度
        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        // 基线
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;

        canvas.clipRect(start, 0, end, getHeight());
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }
}
