package com.fastaoe.baselibrary.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by jinjin on 17/5/13.
 */

public class BaseApplication extends Application {

    public static boolean DEBUG = true;
    public static ConfigManager sConfigManager;
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
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
