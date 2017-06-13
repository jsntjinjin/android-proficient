package com.fastaoe.proficient;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.permission.PermissionFailure;
import com.fastaoe.baselibrary.permission.PermissionHelper;
import com.fastaoe.baselibrary.permission.PermissionSuccess;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.component.recycler.RecyclerFragment;
import com.fastaoe.proficient.component.views.ViewFragment;
import com.fastaoe.proficient.weight.indicator.IndicatorAdapter;
import com.fastaoe.proficient.weight.indicator.TrackIndicatorView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseSkinActivity {

    private String[] items = {"View", "Recycler", "RxJava", "Http", "Other"};

    @Bind(R.id.trackView)
    TrackIndicatorView trackView;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("我的资料站")
                .setRightText("right")
                .setRightClickListener(v ->
                        PermissionHelper.with(MainActivity.this)
                                .requestCode(1)
                                .permissions(Manifest.permission.CALL_PHONE)
                                .request())
                .builder();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions);
    }

    @PermissionSuccess(requestCode = 1)
    private void callSuccess() {
        Intent intent = new Intent();
        Uri parse = Uri.parse("tel:" + "15862932131");
        intent.setData(parse);
        startActivity(intent);
    }

    @PermissionFailure(requestCode = 1)
    private void callFailure() {
        Toast.makeText(this, "拒绝call", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initView() {
        initViewPager();
        initIndicator();
    }

    private void initIndicator() {
        trackView.setAdapter(new IndicatorAdapter() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public View getView(int position, @Nullable ViewGroup parent) {
                TextView tv = new TextView(MainActivity.this);
                tv.setTextSize(14);
                tv.setText(items[position]);
                tv.setGravity(Gravity.CENTER);
                int padding = 20;
                tv.setPadding(padding, padding, padding, padding);
                return tv;
            }

            @Override
            public View getBottomLine() {
                View view = new View(MainActivity.this);
                view.setBackgroundColor(Color.RED);
                view.setLayoutParams(new ViewGroup.LayoutParams(50, 8));
                return view;
            }

            @Override
            public void highLineIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.RED);
            }

            @Override
            public void restoreLineIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.BLACK);
            }
        }, viewpager);
    }

    private void initViewPager() {
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return ViewFragment.newInstance(items[position]);
                    case 1:
                        return RecyclerFragment.newInstance(items[position]);
                    default:
                        return DefaultFragment.newInstance(items[position]);
                }
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });
    }

    @Override
    protected void initData() {

    }

    //    @OnItemClick(R.id.main_listview)
    //    @CheckNet
    //    private void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    //        Toast.makeText(this, id + "", Toast.LENGTH_LONG).show();
    //    }
    //    @OnClick({R.id.main_tv1, R.id.main_tv2})
    //    private void onClick(TextView v) {
    //        AlertDialog dialog = new AlertDialog.Builder(this)
    //                .setContentView(R.layout.main_fragment)
    //                .setText(R.id.main_tv2, "赵六")
    //                .fullWidth()
    //                .fromBottom(true)
    //                .setOnCancelListener(dialog1 -> {
    //                    Toast.makeText(this, "xixixixi", Toast.LENGTH_LONG).show();
    //                })
    //                .show();
    //        dialog.setOnClickListener(R.id.main_tv2,
    //                v1 -> Toast.makeText(this, "哈哈哈哈", Toast.LENGTH_LONG).show());
    //    }
}
