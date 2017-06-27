package com.fastaoe.proficient.component.views.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fastaoe.baselibrary.utils.DensityUtil;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 17/6/27.
 * description:
 */

public class LetterSideBar extends View {

    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    private int mTextColorFocus = Color.BLACK;
    private int mTextColorNormal = Color.RED;
    private int mTextSize = DensityUtil.sp2px(getContext(), 6);
    private Paint mNormalPaint;
    private Paint mFocusPaint;

    private String mCurrentText = "";

    private LetterTouchListener mListener;

    public interface LetterTouchListener {
        void touch(String currentText);

        void cancel();
    }

    public void setLetterTouchListener(LetterTouchListener mListener) {
        this.mListener = mListener;
    }

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mNormalPaint = new Paint();
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setTextSize(mTextSize);
        mNormalPaint.setColor(mTextColorNormal);

        mFocusPaint = new Paint();
        mFocusPaint.setAntiAlias(true);
        mFocusPaint.setTextSize(mTextSize);
        mFocusPaint.setColor(mTextColorFocus);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);

        mTextColorFocus = typedArray.getColor(R.styleable.LetterSideBar_lsTextColorFocus, mTextColorFocus);
        mTextColorNormal = typedArray.getColor(R.styleable.LetterSideBar_lsTextColorNormal, mTextColorNormal);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.LetterSideBar_lsTextSize, mTextSize);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getPaddingLeft() + getPaddingRight() + (int) mNormalPaint.measureText("A");
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
        for (int i = 0; i < letters.length; i++) {
            String text = letters[i];
            Rect bounds = new Rect();
            mNormalPaint.getTextBounds(text, 0, text.length(), bounds);
            int dx = getWidth() / 2 - bounds.width() / 2;
            Paint.FontMetricsInt fontMetricsInt = mNormalPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            int baseLine = itemHeight / 2 + dy + itemHeight * i;
            if (!mCurrentText.equals(text)) {
                canvas.drawText(text, dx, baseLine, mNormalPaint);
            } else {
                canvas.drawText(text, dx, baseLine, mFocusPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
                int moveY = (int) event.getY();
                int currentPisition = moveY / itemHeight;

                if (currentPisition < 0) {
                    currentPisition = 0;
                }
                if (currentPisition > letters.length - 1) {
                    currentPisition = letters.length - 1;
                }
                String currentText = letters[currentPisition];
                if (!mCurrentText.equals(currentText)) {
                    mCurrentText = currentText;
                    invalidate();
                }

                if (mListener != null) {
                    mListener.touch(mCurrentText);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    postDelayed(() -> mListener.cancel(), 300);
                }
                break;
        }
        return true;
    }
}
