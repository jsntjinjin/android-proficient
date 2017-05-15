package com.fastaoe.baselibrary.base;

import android.app.Application;

/**
 * Created by jinjin on 17/5/13.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.mInstance.init(this);
    }
}
