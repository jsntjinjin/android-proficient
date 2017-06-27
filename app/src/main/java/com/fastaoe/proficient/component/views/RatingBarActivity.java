package com.fastaoe.proficient.component.views;

import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 17/6/27.
 * description:
 */

@ContentView(R.layout.activity_view_rating_bar)
public class RatingBarActivity extends BaseSkinActivity {
    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("评分控件")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
