package com.fastaoe.proficient.weight.indicator;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jinjin on 17/6/9.
 */

public abstract class IndicatorAdapter {

    public abstract int getCount();

    public abstract View getView(int position, @Nullable ViewGroup parent);

    public View getBottomLine() {
        return null;
    }

    public void highLineIndicator(View view) {
    }

    public void restoreLineIndicator(View view) {
    }
}
