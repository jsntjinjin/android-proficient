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

import static android.R.attr.banner;

/**
 * Created by jinjin on 17/5/14.
 */

@ContentView(R.layout.main_fragment)
public class TempFragment extends BaseFragment {

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
        //        banner.setText(bundle.getString("title"));
    }

    @OnClick(R.id.banner)
    void showBanner(TextView tv) {
        Intent intent = new Intent(getActivity(), BannerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.recycler)
    void recycler(TextView tv) {
        Intent intent = new Intent(getActivity(), RecyclerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.chat)
    void chat(TextView tv) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.refresh)
    void refresh(TextView tv) {
        Intent intent = new Intent(getActivity(), RefreshActivity.class);
        startActivity(intent);
    }

}
