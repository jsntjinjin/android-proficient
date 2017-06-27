package com.fastaoe.proficient.component.views.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fastaoe.baselibrary.utils.DensityUtil;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 17/6/27.
 * description:
 */

public class RatingBarView extends View {

    private static final String TAG = "RatingBarView";

    private Bitmap mNormalStar, mFocusStar;
    private int maxRating;
    private int padding;

    private int mCurrentRating = -1;

    public RatingBarView(Context context) {
        this(context, null);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        padding = DensityUtil.dip2px(getContext(), 5);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        int focusStarId = typedArray.getResourceId(R.styleable.RatingBarView_focusStar, 0);
        int normalStarId = typedArray.getResourceId(R.styleable.RatingBarView_normalStar, 0);
        if (focusStarId == 0 || normalStarId == 0) {
            throw new RuntimeException("请设置属性 focusStar 和 normalStar ");
        }
        mFocusStar = BitmapFactory.decodeResource(getResources(), focusStarId);
        mNormalStar = BitmapFactory.decodeResource(getResources(), normalStarId);
        maxRating = typedArray.getInt(R.styleable.RatingBarView_maxRating, 5);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = mNormalStar.getHeight() + getPaddingTop() + getPaddingBottom();
        int width = mNormalStar.getWidth() * maxRating + padding * 4 + getPaddingLeft() + getPaddingRight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < maxRating; i++) {
            int left = i * mNormalStar.getWidth() + padding * i + getPaddingLeft();
            if (i <= mCurrentRating) {
                canvas.drawBitmap(mFocusStar, left, getPaddingTop(), null);
            } else {
                canvas.drawBitmap(mNormalStar, left, getPaddingTop(), null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            // case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            // case MotionEvent.ACTION_UP:
                float moveX = event.getX();
                int currentRating = (int) ((moveX - getPaddingLeft()) / (mNormalStar.getWidth() + padding));
                // LogUtil.d(TAG, currentRating + "");
                if (currentRating < 0 ) {
                    currentRating = 0;
                }
                if (currentRating >= maxRating) {
                    currentRating = maxRating - 1;
                }
                if (mCurrentRating != currentRating) {
                    mCurrentRating = currentRating;
                    invalidate();
                }
                break;
        }
        return true;
    }
}
