package com.fastaoe.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.fastaoe.framelibrary.skin.attr.SkinAttr;
import com.fastaoe.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 2017/6/4.
 */

public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        // src textcolor background
        List<SkinAttr> skinAttrs = new ArrayList<>();

        int attributeCount = attrs.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            // 获取属性名称、值
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);

            // 过滤
            SkinType skinType = getSkinType(attributeName);

            if (skinType != null) {
                String resourceNmae = getResourceName(context, attributeValue);

                if (TextUtils.isEmpty(resourceNmae)) {
                    continue;
                }

                SkinAttr skinAttr = new SkinAttr(resourceNmae,skinType);
                skinAttrs.add(skinAttr);
            }
        }

        return skinAttrs;
    }

    private static String getResourceName(Context context, String attributeValue) {
        if (attributeValue.startsWith("@")) {
            attributeValue = attributeValue.substring(1);
            int resourceId = Integer.parseInt(attributeValue);
            return context.getResources().getResourceEntryName(resourceId);
        }
        return null;
    }

    private static SkinType getSkinType(String attributeName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResourceName().equals(attributeName)) {
                return skinType;
            }
        }
        return null;
    }
}
