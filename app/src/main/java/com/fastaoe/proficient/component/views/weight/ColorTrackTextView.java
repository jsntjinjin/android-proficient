package com.fastaoe.proficient.component.views.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 17/6/26.
 * description:
 */

public class ColorTrackTextView extends AppCompatTextView {

    private Paint mOriginPaint;
    private Paint mChangePaint;

    private float progress = 0.0f;

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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);

        int originColor = typedArray.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor());
        int changeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, Color.RED);

        mOriginPaint = setPaintByColor(originColor);
        mChangePaint = setPaintByColor(changeColor);

        typedArray.recycle();
    }

    private Paint setPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        paint.setColor(color);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int middle = (int) (progress * getWidth());

        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mChangePaint, 0, middle);
            drawText(canvas, mOriginPaint, middle, getWidth());
        } else {
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
            drawText(canvas, mOriginPaint, 0, getWidth() - middle);
        }

    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        String text = getText().toString();
        // 获取字体宽度
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        // 获取baseLine
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;

        canvas.clipRect(start, 0, end, getHeight());
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    public void setOriginColor(int color) {
        mOriginPaint.setColor(color);
    }

    public void setChangeColor(int color) {
        mChangePaint.setColor(color);
    }
}
