package com.fastaoe.baselibrary.base;

/**
 * Created by jinjin on 17/5/15.
 */

public class ConfigManager {

    private ConfigManager() {
    }

    public static ConfigManager mInstance;

    public static ConfigManager getInstance() {
        if (mInstance == null) {
            synchronized (ConfigManager.class) {
                if (mInstance == null) {
                    mInstance = new ConfigManager();
                }
            }
        }
        return mInstance;
    }

    public boolean mDebugOn = true;
}
