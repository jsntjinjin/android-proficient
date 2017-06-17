package com.fastaoe.proficient.component.other;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fastaoe.baselibrary.base.BaseFragment;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.other.hook.TestHookActivity;
import com.fastaoe.proficient.component.other.notification.NotificationResultActivity;
import com.fastaoe.proficient.weight.notification.Notify;
import com.fastaoe.proficient.weight.notification.NotifyProxy;

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

    @OnClick(R.id.skin)
    void showSkin(TextView tv) {
        startActivity(new Intent(getActivity(), ZXingActivity.class));
    }

    @OnClick(R.id.hook)
    void hookClick(TextView tv) {
        startActivity(new Intent(getActivity(), TestHookActivity.class));
    }

    @OnClick(R.id.notification)
    void notificationClick(TextView tv) {
        new NotifyProxy(getActivity(), NotificationResultActivity.class).send();
    }

}
