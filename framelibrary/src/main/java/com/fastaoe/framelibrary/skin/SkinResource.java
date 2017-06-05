package com.fastaoe.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jinjin on 2017/6/4.
 */

public class SkinResource {

    private Resources mSkinResource;
    private String mPackageName;

    public SkinResource(Context context, String skinPath) {
        try {
            Resources superRes = context.getResources();

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPath);

            mSkinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            mPackageName = context.getPackageManager()
                    .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Drawable getDrawableByName(String resourceName) {
        try {
            int resId = mSkinResource.getIdentifier(resourceName, "drawable", mPackageName);
            Drawable drawable = mSkinResource.getDrawable(resId);
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorByName(String resourceName) {
        try {
            int resId = mSkinResource.getIdentifier(resourceName, "color", mPackageName);
            ColorStateList color = mSkinResource.getColorStateList(resId);
            return color;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
