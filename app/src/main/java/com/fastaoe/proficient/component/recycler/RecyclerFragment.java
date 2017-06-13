package com.fastaoe.proficient.component.recycler;


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

@ContentView(R.layout.fragment_recycler)
public class RecyclerFragment extends BaseFragment {

    public static RecyclerFragment newInstance(String item) {
        RecyclerFragment itemFragment = new RecyclerFragment();
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

    @OnClick(R.id.recycler_01)
    void recycler01(TextView tv) {
        Intent intent = new Intent(getActivity(), NormalRecyclerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.recycler_02)
    void recycler02(TextView tv) {
        Intent intent = new Intent(getActivity(), ManyItemsRecyclerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.recycler_03)
    void recycler03(TextView tv) {
        Intent intent = new Intent(getActivity(), HeadFooterRecyclerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.recycler_04)
    void recycler04(TextView tv) {
        Intent intent = new Intent(getActivity(), RefreshRecyclerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.recycler_05)
    void recycler05(TextView tv) {
        Intent intent = new Intent(getActivity(), DragSwipeRecyclerActivity.class);
        startActivity(intent);
    }
}
