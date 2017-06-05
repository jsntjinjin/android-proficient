package com.fastaoe.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by jinjin on 2017/6/4.
 */

public class SkinAttr {

    private String mResurceName;
    private SkinType mType;

    public SkinAttr(String resourceNmae, SkinType skinType) {
        this.mResurceName = resourceNmae;
        this.mType = skinType;
    }

    public void skin(View view) {
        mType.skin(view, mResurceName);
    }

}
