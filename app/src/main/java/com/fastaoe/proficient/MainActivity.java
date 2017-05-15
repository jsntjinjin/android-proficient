package com.fastaoe.proficient;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaoe.baselibrary.dialog.AlertDialog;
import com.fastaoe.baselibrary.http.EngineCallback;
import com.fastaoe.baselibrary.http.HttpUtils;
import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.framelibrary.HttpCallback;
import com.fastaoe.proficient.Model.AbcModel;

import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseSkinActivity {

    @Bind(R.id.main_tv1)
    private TextView main_tv1;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("标题")
                .setRightText("右边")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .builder();
    }

    @Override
    protected void initView() {
        main_tv1.setText("hahahaha");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TempFragment tempFragment = new TempFragment();
        fragmentTransaction.add(R.id.main_fragment, tempFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void initData() {
        HttpUtils.with(this).get("url").addParam("a", "b").execute(new HttpCallback<AbcModel>() {
            @Override
            public void onSuccess(AbcModel result) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @OnClick({R.id.main_tv1, R.id.main_tv2})
    private void onClick(TextView v) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.main_fragment)
                .setText(R.id.main_tv2, "赵六")
                .fullWidth()
                .fromBottom(true)
                .show();
        dialog.setOnClickListener(R.id.main_tv2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "哈哈哈哈", Toast.LENGTH_LONG).show();
            }
        });
    }
}
