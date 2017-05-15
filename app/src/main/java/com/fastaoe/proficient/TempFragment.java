package com.fastaoe.proficient;


import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaoe.baselibrary.base.BaseFragment;
import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;

/**
 * Created by jinjin on 17/5/14.
 */

@ContentView(R.layout.main_fragment)
public class TempFragment extends BaseFragment {

    @Bind(R.id.main_tv1)
    private TextView main_tv1;

    @Override
    protected void initView() {
        main_tv1.setText("你好");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.main_tv2)
    private void onClick(TextView v) {
        Toast.makeText(this.getActivity(), v.getText().toString(), Toast.LENGTH_LONG).show();
    }
}
