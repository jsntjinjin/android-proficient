package com.fastaoe.proficient.component.views;

import android.view.View;
import android.widget.ImageView;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.Constants;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.weight.banner.BannerAdapter;
import com.fastaoe.proficient.weight.banner.BannerView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 2017/6/10.
 */

@ContentView(R.layout.activity_view_banner)
public class BannerViewActivity extends BaseSkinActivity {

    @Bind(R.id.banner_view)
    BannerView banner_view;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("banner")
                .setLeftIcon(Constants.ICON_ADD)
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        List<String> urls = new ArrayList<>();
        urls.add("http://7xsftu.com1.z0.glb.clouddn.com/a.jpg");
        urls.add("http://7xsftu.com1.z0.glb.clouddn.com/b.jpg");
        urls.add("http://7xsftu.com1.z0.glb.clouddn.com/c.jpg");
        urls.add("http://7xsftu.com1.z0.glb.clouddn.com/d.jpg");
        initBanner(urls);

    }

    // 初始化banner
    private void initBanner(List<String> urls) {
        banner_view.setBannerAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertViews) {
                ImageView banner = null;
                if (convertViews == null) {
                    banner = new ImageView(BannerViewActivity.this);
                    banner.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    banner = (ImageView) convertViews;
                }

                x.image().bind(banner, urls.get(position));
                return banner;
            }

            @Override
            public int getCount() {
                return urls.size();
            }
        });
        banner_view.setOnBannerItemClickListener(position -> {
            LogUtil.d("tag", "setOnBannerItemClickListener -> " + position);
        });
        banner_view.setRollTime(3000);
        banner_view.setRollChangeDuration(1000);
        banner_view.startRoll();
    }
}
