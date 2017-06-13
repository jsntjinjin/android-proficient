package com.fastaoe.proficient;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fastaoe.baselibrary.base.BaseFragment;
import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;

/**
 * Created by jinjin on 17/5/14.
 */

@ContentView(R.layout.fragment_default)
public class DefaultFragment extends BaseFragment {

    @Bind(R.id.textview)
    TextView textview;

    public static DefaultFragment newInstance(String item) {
        DefaultFragment itemFragment = new DefaultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", item);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        textview.setText(bundle.getString("title"));
    }

}
