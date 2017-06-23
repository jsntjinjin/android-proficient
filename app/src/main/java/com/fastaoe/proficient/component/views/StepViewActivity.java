package com.fastaoe.proficient.component.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.views.weight.StepView;

/**
 * Created by jinjin on 17/6/22.
 * description:
 */

@ContentView(R.layout.activity_view_step_view)
public class StepViewActivity extends BaseSkinActivity{

    @Bind(R.id.step_view)
    StepView step_view;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("计步器")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        step_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                step_view.setMaxStep(4000);

                ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
                valueAnimator.setDuration(2000);
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(animation ->
                        step_view.setCurrentStep((int) ((float) animation.getAnimatedValue())));
                valueAnimator.start();
            }
        },1000);
    }
}
