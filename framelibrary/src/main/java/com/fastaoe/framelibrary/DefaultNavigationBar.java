package com.fastaoe.framelibrary;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fastaoe.baselibrary.navigationbar.AbsNavigationBar;

/**
 * Created by jinjin on 17/5/14.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {

    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams mParams) {
        super(mParams);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.default_navigation_bar;
    }

    @Override
    public void applyView() {
        // 绑定参数
        setText(R.id.bar_title, getParams().mTitle);
        setText(R.id.bar_right_text, getParams().mRightText);
        setOnClickListener(R.id.bar_left_text, getParams().mLeftListener);
        setOnClickListener(R.id.bar_right_text, getParams().mRightListener);
    }

    private void setText(int viewId, String text) {
        TextView textView = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        if (listener != null) {
            view.setOnClickListener(listener);
        }
    }

    public static class Builder extends AbsNavigationBar.Builder {

        private DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }

        // 设置参数
        public Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        public Builder setRightText(String rightText) {
            P.mRightText = rightText;
            return this;
        }

        public Builder setRightIcon(String rightIcon) {
            P.mRightText = rightIcon;
            return this;
        }

        public Builder setRightClickListener(View.OnClickListener listener) {
            P.mRightListener = listener;
            return this;
        }

        public Builder setLeftClickListener(View.OnClickListener listener) {
            P.mLeftListener = listener;
            return this;
        }

        public static class DefaultNavigationParams extends AbsNavigationParams {

            // 所有的参数
            public String mTitle;
            public String mRightText;
            public View.OnClickListener mRightListener;
            public View.OnClickListener mLeftListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity)mContext).finish();
                }
            };

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }

    }

}
