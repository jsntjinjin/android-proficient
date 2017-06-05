package com.fastaoe.framelibrary.skin.config;

import android.content.Context;

/**
 * Created by jinjin on 17/6/5.
 */

public class SkinPreUtils {

    private static SkinPreUtils mInstance;
    private Context mContext;
    private SkinPreUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static SkinPreUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SkinPreUtils.class) {
                if (mInstance == null) {
                    mInstance = new SkinPreUtils(context);
                }
            }
        }
        return mInstance;
    }

    public void saveSkinPath(String path) {
        mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_NAME, Context.MODE_PRIVATE)
                .edit().putString(SkinConfig.SKIN_PATH, path).apply();
    }

    public String getSkinPath() {
       return mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_NAME, Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_PATH, "");
    }

    public void clearSkinInfo() {
        saveSkinPath("");
    }
}
