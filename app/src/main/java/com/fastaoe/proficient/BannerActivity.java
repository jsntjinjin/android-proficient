package com.fastaoe.proficient;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.proficient.weight.banner.BannerAdapter;
import com.fastaoe.proficient.weight.banner.BannerView;
import com.fastaoe.proficient.weight.recycler.GridLayoutItemDecoration;
import com.fastaoe.proficient.weight.recycler.base.RecyclerAdapter;
import com.fastaoe.proficient.weight.recycler.base.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 2017/6/10.
 */

@ContentView(R.layout.activity_banner)
public class BannerActivity extends BaseSkinActivity {

    @Bind(R.id.banner_view)
    BannerView banner_view;
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;
    private List<String> recyclerData;

    @Override
    protected void initTitle() {

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

        recyclerData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            recyclerData.add("第" + i + "条数据");
        }

        recycler_view.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_view.setAdapter(initRecyclerAdapter(recyclerData));
        recycler_view.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_dirver_01));
    }

    // 初始化RecyclerAdapter
    private RecyclerAdapter initRecyclerAdapter(List<String> recyclerData) {
        RecyclerAdapter<String> adapter = new RecyclerAdapter(this, recyclerData, R.layout.item_recycler) {
            @Override
            protected void convert(ViewHolder holder, Object data, int position) {
                holder.setText(R.id.item_tv, recyclerData.get(position));
            }
        };

        adapter.setOnItemClickListener(position -> LogUtil.d("TAG", "点击子条目 -> " + recyclerData.get(position)));
        adapter.setOnItemLongClickListener(position -> {
            LogUtil.d("TAG", "点击Long子条目 -> " + recyclerData.get(position));
            return true;
        });
        return adapter;
    }


    // 初始化banner
    private void initBanner(List<String> urls) {
        banner_view.setBannerAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertViews) {
                ImageView banner = null;
                if (convertViews == null) {
                    banner = new ImageView(BannerActivity.this);
                    banner.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    banner = (ImageView) convertViews;
                }

                Picasso.with(BannerActivity.this).load(urls.get(position))
                        .placeholder(R.mipmap.ic_launcher).into(banner);
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
