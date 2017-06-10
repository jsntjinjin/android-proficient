package com.fastaoe.proficient.weight.banner;

import android.view.View;

/**
 * Created by jinjin on 2017/6/10.
 */

public abstract class BannerAdapter {
    // 获取banner的每个子View
    public abstract View getView(int position, View convantView);

    // 获取banner的数量
    public abstract int getCount();

    // 设置当前选中的page的title
    public String getBannerTitle(int currentPosition){
        return "";
    }
}
