package com.fastaoe.proficient.component.opengles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fastaoe.baselibrary.base.BaseFragment;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.opengles.render.FGLViewActivity;

/**
 * Created by jinjin on 17/7/7.
 * description:
 */

@ContentView(R.layout.fragment_opengles)
public class OpenGLFragment extends BaseFragment {

    public static OpenGLFragment newInstance(String item) {
        OpenGLFragment itemFragment = new OpenGLFragment();
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

    @OnClick(R.id.fglview)
    void verticalDrag(TextView tv) {
        Intent intent = new Intent(getActivity(), FGLViewActivity.class);
        startActivity(intent);
    }
}
