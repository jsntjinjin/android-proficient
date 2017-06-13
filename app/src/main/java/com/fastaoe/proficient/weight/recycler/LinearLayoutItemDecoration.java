package com.fastaoe.proficient.weight.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jinjin on 2017/6/11.
 */

public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable drawable;
    private Paint paint;

    public LinearLayoutItemDecoration(Context context, int drawableResId) {
        this.drawable = ContextCompat.getDrawable(context, drawableResId);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        // 绘制区域
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 1; i < childCount; i++) {
            rect.bottom = parent.getChildAt(i).getTop();
            rect.top = rect.bottom - drawable.getIntrinsicHeight();
            drawable.setBounds(rect);
            drawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = drawable.getIntrinsicHeight();
        }
    }
}
