package com.fastaoe.proficient.component.views;

import android.view.View;
import android.widget.TextView;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.views.weight.LetterSideBar;

/**
 * Created by jinjin on 17/6/27.
 * description:
 */

@ContentView(R.layout.activity_view_letter_side_bar)
public class LetterSideBarActivity extends BaseSkinActivity {

    @Bind(R.id.tv_letter_result)
    TextView tv_letter_result;
    @Bind(R.id.letter_side_bar)
    LetterSideBar letter_side_bar;
    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("字母索引列表")
                .builder();
    }

    @Override
    protected void initView() {
        letter_side_bar.setLetterTouchListener(new LetterSideBar.LetterTouchListener() {
            @Override
            public void touch(String currentText) {
                tv_letter_result.setText(currentText);
                if (tv_letter_result.getVisibility() == View.GONE) {
                    tv_letter_result.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void cancel() {
                if (tv_letter_result.getVisibility() == View.VISIBLE) {
                    tv_letter_result.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
