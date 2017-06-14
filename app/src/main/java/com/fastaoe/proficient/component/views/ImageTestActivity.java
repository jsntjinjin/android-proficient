package com.fastaoe.proficient.component.views;

import android.content.Intent;
import android.widget.Button;

import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;

import java.util.ArrayList;

/**
 * Created by jinjin on 2017/6/13.
 * description:
 */

@ContentView(R.layout.activity_view_test_image)
public class ImageTestActivity extends BaseSkinActivity {

    private ArrayList<String> images = new ArrayList<>();

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("图片选择结果")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_choose_many)
    void chooseMany(Button button) {
        Intent intent = new Intent(this, SelectImageActivity.class);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_COUNT, 9);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_MODE, SelectImageActivity.MODE_MULTI);
        intent.putStringArrayListExtra(SelectImageActivity.EXTRA_DEFAULT_SELECTED_LIST, images);
        intent.putExtra(SelectImageActivity.EXTRA_SHOW_CAMERA, true);
        startActivity(intent);
    }

    @OnClick(R.id.btn_choose_one)
    void chooseOne(Button button) {
        Intent intent = new Intent(this, SelectImageActivity.class);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_COUNT, 1);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_MODE, SelectImageActivity.MODE_SINGLE);
        intent.putStringArrayListExtra(SelectImageActivity.EXTRA_DEFAULT_SELECTED_LIST, images);
        intent.putExtra(SelectImageActivity.EXTRA_SHOW_CAMERA, true);
        startActivity(intent);
    }
}
