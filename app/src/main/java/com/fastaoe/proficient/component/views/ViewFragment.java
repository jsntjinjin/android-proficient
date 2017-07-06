package com.fastaoe.proficient.component.views;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fastaoe.baselibrary.base.BaseFragment;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.views.weight.RoundProgressBar;

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

    @OnClick(R.id.lock_pattern)
    void lockPattern(TextView tv) {
        Intent intent = new Intent(getActivity(), LockPatternActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sliding_vertical_drag)
    void verticalDrag(TextView tv) {
        Intent intent = new Intent(getActivity(), VerticalDragActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sliding_menu_qq)
    void QQSlidingMenu(TextView tv) {
        Intent intent = new Intent(getActivity(), QQSlidingMenuActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sliding_menu_kg)
    void KGSlidingMenu(TextView tv) {
        Intent intent = new Intent(getActivity(), KGSlidingMenuActivity.class);
        startActivity(intent);
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

    @OnClick(R.id.run_num)
    void runNumClick(TextView tv) {
        Intent intent = new Intent(getActivity(), StepViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.progress_bar)
    void progressBarClick(TextView tv) {
        Intent intent = new Intent(getActivity(), ProgressBarActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rating_bar)
    void ratingBarClick(TextView tv) {
        Intent intent = new Intent(getActivity(), RatingBarActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.letter_side_bar)
    void letterSideBarClick(TextView tv) {
        Intent intent = new Intent(getActivity(), LetterSideBarActivity.class);
        startActivity(intent);
    }
}
