package com.fastaoe.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by jinjin on 2017/6/4.
 */

public class SkinView {

    private View mView;

    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mAttrs) {
            attr.skin(mView);
        }
    }
}
