package com.fastaoe.proficient.component.views;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fastaoe.baselibrary.base.BaseFragment;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 17/5/14.
 */

@ContentView(R.layout.fragment_view)
public class ViewFragment extends BaseFragment {

    public static ViewFragment newInstance(String item) {
        ViewFragment itemFragment = new ViewFragment();
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
    }

    @OnClick(R.id.banner)
    void showBanner(TextView tv) {
        Intent intent = new Intent(getActivity(), BannerViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.dialog)
    void showDialog(TextView tv) {
        Intent intent = new Intent(getActivity(), DialogViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.image_choose)
    void chooseImage(TextView tv) {
        Intent intent = new Intent(getActivity(), ImageTestActivity.class);
        startActivity(intent);
    }

}
