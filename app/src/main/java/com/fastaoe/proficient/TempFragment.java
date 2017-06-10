package com.fastaoe.proficient;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaoe.baselibrary.base.BaseFragment;
import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;

/**
 * Created by jinjin on 17/5/14.
 */

@ContentView(R.layout.main_fragment)
public class TempFragment extends BaseFragment {

    @Bind(R.id.textview)
    TextView tv;

    public static TempFragment newInstance(String item) {
        TempFragment itemFragment = new TempFragment();
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
        tv.setText(bundle.getString("title"));
    }

    @OnClick(R.id.textview)
    void showBanner(TextView tv) {
        Intent intent = new Intent(getActivity(), BannerActivity.class);
        startActivity(intent);
    }

}
