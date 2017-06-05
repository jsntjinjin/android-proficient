package com.fastaoe.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.fastaoe.framelibrary.skin.SkinManager;
import com.fastaoe.framelibrary.skin.SkinResource;

/**
 * Created by jinjin on 2017/6/4.
 */

public enum SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resurceName) {
            SkinResource skinResource = SkinManager.getInstance().getSkinResource();
            ColorStateList colorByName = skinResource.getColorByName(resurceName);
            if (colorByName == null) {
                return;
            }

            TextView textView = (TextView) view;
            textView.setTextColor(colorByName);

        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resurceName) {
            SkinResource skinResource = SkinManager.getInstance().getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resurceName);
            if (drawable == null) {
                return;
            }

            TextView textView = (TextView) view;
            textView.setTextColor(drawable);
        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resurceName) {
            SkinResource skinResource = SkinManager.getInstance().getSkinResource();
            ColorStateList colorByName = skinResource.getColorByName(resurceName);
            if (colorByName == null) {
                return;
            }

            TextView textView = (TextView) view;
            textView.setTextColor(colorByName);
        }
    };

    private String mResourceName;

    SkinType(String resourceNmae) {
        this.mResourceName = resourceNmae;
    }

    public abstract void skin(View view, String resurceName);

    public String getResourceName() {
        return mResourceName;
    }
}
