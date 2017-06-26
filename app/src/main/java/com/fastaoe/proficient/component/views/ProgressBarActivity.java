package com.fastaoe.proficient.component.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.views.weight.RoundProgressBar;
import com.fastaoe.proficient.component.views.weight.ShapeLoadingView;

/**
 * Created by jinjin on 17/6/26.
 * description:
 */

@ContentView(R.layout.activity_view_progress_bar)
public class ProgressBarActivity extends BaseSkinActivity {

    @Bind(R.id.rpb_round)
    RoundProgressBar rpb_round;
    @Bind(R.id.slv_loading)
    ShapeLoadingView slv_loading;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("进度条")
                .builder();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_start)
    void onStartClick(Button button) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float animatedValue = (Float) animation.getAnimatedValue();
                rpb_round.setProgress(animatedValue);
            }
        });
        valueAnimator.start();

        new Thread(() -> {
            while (true) {
                runOnUiThread(() -> slv_loading.execute());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
