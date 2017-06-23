package com.fastaoe.framelibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spanned;
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
        setText(R.id.bar_title, null, getParams().mTitle);
        setText(R.id.bar_right_text, getParams().mTypeface, getText(getParams().mRightText, getParams().mRightTextIcon));
        setText(R.id.bar_left_text, getParams().mTypeface, getText(getParams().mLeftText, getParams().mLeftTextIcon));
        setOnClickListener(R.id.bar_left_text, getParams().mLeftListener);
        setOnClickListener(R.id.bar_right_text, getParams().mRightListener);
    }

    private CharSequence getText(String text, String icon) {
        if (!TextUtils.isEmpty(text)) {
            return text;
        } else if (!TextUtils.isEmpty(icon)) {
            return getHtmlText(icon);
        } else {
            return null;
        }
    }

    private void setText(int viewId, Typeface typeface, CharSequence text) {
        TextView textView = findViewById(viewId);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
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

    private Spanned getHtmlText(String which) {
        return Html.fromHtml(which);
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
            P.mRightTextIcon = rightIcon;
            return this;
        }

        public Builder setLeftText(String leftText) {
            P.mLeftText = leftText;
            return this;
        }

        public Builder setLeftIcon(String leftIcon) {
            P.mLeftTextIcon = leftIcon;
            return this;
        }

        public Builder hideLeftText() {
            P.mLeftText = null;
            P.mLeftTextIcon = null;
            P.mLeftListener = null;
            return this;
        }

        public Builder hideRightText() {
            P.mRightText = null;
            P.mRightTextIcon = null;
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
            public String mLeftText;
            public String mLeftTextIcon = "&#xe614;";
            public String mRightText;
            public String mRightTextIcon;

            public Typeface mTypeface = Typeface.createFromAsset(mContext.getAssets(), "iconfont.ttf");

            public View.OnClickListener mRightListener;
            public View.OnClickListener mLeftListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            };

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }

    }

}
