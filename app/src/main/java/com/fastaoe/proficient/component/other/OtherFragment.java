package com.fastaoe.proficient.component.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fastaoe.baselibrary.base.BaseFragment;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.other.zxing.activity.CaptureActivity;
import com.fastaoe.proficient.component.views.BannerViewActivity;

/**
 * Created by jinjin on 17/6/14.
 * description:
 */

@ContentView(R.layout.fragment_other)
public class OtherFragment extends BaseFragment {

    public static OtherFragment newInstance(String item) {
        OtherFragment itemFragment = new OtherFragment();
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

    }

    @OnClick(R.id.zxing)
    void showBanner(TextView tv) {
        startActivity(new Intent(getActivity(), ZXingActivity.class));
    }

}
