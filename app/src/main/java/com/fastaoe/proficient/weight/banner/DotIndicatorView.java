package com.fastaoe.proficient.weight.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jinjin on 2017/6/10.
 */

public class DotIndicatorView extends View {
    private Drawable drawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawable != null) {
            Bitmap bitmap = drawableToBitmap(drawable);
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            canvas.drawBitmap(circleBitmap, 0, 0, null);
        }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        bitmap.recycle();
        bitmap = null;

        return circleBitmap;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // colordrawable
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        invalidate();
    }
}
