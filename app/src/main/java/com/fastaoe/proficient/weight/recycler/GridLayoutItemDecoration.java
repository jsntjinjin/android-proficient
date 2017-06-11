package com.fastaoe.proficient.weight.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jinjin on 2017/6/11.
 */

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable drawable;
    private Paint paint;

    public GridLayoutItemDecoration(Context context, int drawableResId) {
        this.drawable = ContextCompat.getDrawable(context, drawableResId);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int right = drawable.getIntrinsicWidth();
        int bottom = drawable.getIntrinsicHeight();

        if (isLastColumn(view, parent)) {
            right = 0;
        }

        if (isLastRow(view, parent)) {
            bottom = 0;
        }

        outRect.bottom = bottom;
        outRect.right = right;
    }

    private boolean isLastRow(View view, RecyclerView parent) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int rowNumber = parent.getAdapter().getItemCount() % spanCount == 0
                ? parent.getAdapter().getItemCount() / spanCount
                : parent.getAdapter().getItemCount() / spanCount + 1;

        return (position + 1) > (rowNumber - 1) * spanCount;
    }

    private boolean isLastColumn(View view, RecyclerView parent) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        return (position + 1) % spanCount == 0;
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            return spanCount;
        }
        return 1;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getTop() - params.topMargin;
            int bottom = childView.getBottom() + params.bottomMargin;
            int left = childView.getRight() + params.rightMargin;
            int right = left + drawable.getIntrinsicWidth();

            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - params.leftMargin;
            int right = childView.getRight() + drawable.getIntrinsicWidth() + params.rightMargin;
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + drawable.getIntrinsicHeight();

            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }
}
