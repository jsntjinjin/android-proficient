package com.fastaoe.baselibrary.base;

import android.app.Application;

/**
 * Created by jinjin on 17/5/13.
 */

public class BaseApplication extends Application {

    public static boolean DEBUG = true;
    public static ConfigManager sConfigManager;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        sConfigManager = ConfigManager.getInstance();
        CrashHandler.getInstance().init(this);

        refreshConfig();
    }

    public static void refreshConfig() {
        DEBUG = sConfigManager.mDebugOn;
    }
}
