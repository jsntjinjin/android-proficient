package com.fastaoe.proficient;

import com.fastaoe.baselibrary.base.BaseApplication;
import com.fastaoe.framelibrary.skin.SkinManager;

/**
 * Created by jinjin on 17/5/16.
 */

public class MyBaseApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.getInstance().init(this);

        initConfig();

    }

    private void initConfig() {
        sConfigManager.mDebugOn = Constants.DEBUG;

        refreshConfig();
    }
}
