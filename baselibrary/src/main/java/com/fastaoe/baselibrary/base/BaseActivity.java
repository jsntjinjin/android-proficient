package com.fastaoe.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fastaoe.baselibrary.ioc.ViewInjectorImpl;

/**
 * Created by jinjin on 17/5/13.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectorImpl.getInstance().inject(this);
        initTitle();
        initView();
        initData();
    }

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

}
