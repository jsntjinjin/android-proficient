package com.fastaoe.proficient.component.views;

import android.widget.Toast;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.views.weight.LockPatternView;

/**
 * Created by jinjin on 17/7/6.
 * description:
 */

@ContentView(R.layout.activity_view_lock_pattern)
public class LockPatternActivity extends BaseSkinActivity {

    @Bind(R.id.lock_pattern)
    LockPatternView lock_pattern;

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        lock_pattern.setLockPatternListener(password -> {
            Toast.makeText(this, password, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void initData() {

    }
}
