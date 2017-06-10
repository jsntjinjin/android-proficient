package com.fastaoe.proficient;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.weight.indicator.IndicatorAdapter;
import com.fastaoe.proficient.weight.indicator.TrackIndicatorView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseSkinActivity {

    private String[] items = {"直播", "推荐", "视频", "段友秀", "图片", "段子", "精华", "同城", "游戏", "直播", "推荐", "视频", "段友秀", "图片"};

    @Bind(R.id.trackView)
    TrackIndicatorView trackView;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("title")
                .setRightText("right")
                .setRightClickListener(v -> {
                    Intent intent = new Intent(this, BannerActivity.class);
                    startActivity(intent);
                })
                .builder();
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
                return TempFragment.newInstance(items[position]);
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
