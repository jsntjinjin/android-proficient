package com.fastaoe.proficient.component.views;

import android.view.View;

import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 2017/7/1.
 * description:
 */

@ContentView(R.layout.activity_view_qq_sliding_menu)
public class QQSlidingMenuActivity extends BaseSkinActivity {
    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.content)
    private void onCOntent(View view) {
        LogUtil.d("QQSlidingMenuActivity", "content被点击了");
    }
}
