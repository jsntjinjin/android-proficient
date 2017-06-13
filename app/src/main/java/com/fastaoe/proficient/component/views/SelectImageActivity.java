package com.fastaoe.proficient.component.views;

import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 17/6/13.
 * description:
 */

@ContentView(R.layout.activity_select_image)
public class SelectImageActivity extends BaseSkinActivity {

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("dialog")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
