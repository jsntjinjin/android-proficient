package com.fastaoe.proficient;

import android.graphics.Typeface;

import com.fastaoe.baselibrary.base.BaseApplication;
import com.fastaoe.framelibrary.skin.SkinManager;

import org.xutils.x;

/**
 * Created by jinjin on 17/5/16.
 */

public class MyBaseApplication extends BaseApplication {

    public static Typeface sTypeface;

    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.getInstance().init(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        sTypeface = Typeface.createFromAsset(getAssets(), "iconfont.ttf");

        initConfig();

    }

    private void initConfig() {
        sConfigManager.mDebugOn = Constants.DEBUG;

        refreshConfig();
    }
}
