package com.fastaoe.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by jinjin on 17/5/13.
 */

class ViewFinder {

    private View view;
    private Activity activity;

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public View findViewById(int viewId) {
        if (view != null) {
            return view.findViewById(viewId);
        }
        if (activity != null) {
            return activity.findViewById(viewId);
        }
        return null;
    }
}
