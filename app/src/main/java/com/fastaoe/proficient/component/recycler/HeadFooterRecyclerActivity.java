package com.fastaoe.proficient.component.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.weight.banner.BannerAdapter;
import com.fastaoe.proficient.weight.banner.BannerView;
import com.fastaoe.proficient.weight.recycler.LinearLayoutItemDecoration;
import com.fastaoe.proficient.weight.recycler.base.RecyclerAdapter;
import com.fastaoe.proficient.weight.recycler.base.ViewHolder;
import com.fastaoe.proficient.weight.recycler.wrap.WrapRecyclerView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 17/6/12.
 * description:
 */

@ContentView(R.layout.activity_recycler_items)
public class HeadFooterRecyclerActivity extends BaseSkinActivity {

    @Bind(R.id.recycler_view)
    WrapRecyclerView recycler_view;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("带头部和底部的recycler")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        List<ChatItem> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0) {
                list.add(new ChatItem("第" + i + "个item", 0));
            } else {
                list.add(new ChatItem("第" + i + "个item", 1));
            }
        }

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(initRecyclerAdapter(list));
        recycler_view.addItemDecoration(new LinearLayoutItemDecoration(this, R.drawable.item_dirver_01));

        View header = LayoutInflater.from(this).inflate(R.layout.item_chat_header, recycler_view, false);
        List<String> urls = new ArrayList<>();
        urls.add("http://7xsftu.com1.z0.glb.clouddn.com/a.jpg");
        urls.add("http://7xsftu.com1.z0.glb.clouddn.com/b.jpg");
        urls.add("http://7xsftu.com1.z0.glb.clouddn.com/c.jpg");
        urls.add("http://7xsftu.com1.z0.glb.clouddn.com/d.jpg");
        initBanner(urls, (BannerView) header.findViewById(R.id.banner_view));



        recycler_view.addHeaderView(header);
        recycler_view.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_chat_footer, recycler_view, false));
    }

    // 初始化banner
    private void initBanner(List<String> urls, BannerView banner_view) {
        banner_view.setBannerAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertViews) {
                ImageView banner = null;
                if (convertViews == null) {
                    banner = new ImageView(HeadFooterRecyclerActivity.this);
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

    private RecyclerAdapter initRecyclerAdapter(List<ChatItem> recyclerData) {
        RecyclerAdapter adapter = new RecyclerAdapter<ChatItem>(this, recyclerData, item -> {
            ChatItem chatItem = (ChatItem) item;
            if (chatItem.type == 0) {
                return R.layout.item_chat_01;
            }
            return R.layout.item_chat_02;
        }) {
            @Override
            protected void convert(ViewHolder holder, ChatItem data, int position) {
                if (data.type == 0) {
                    holder.setText(R.id.item_me, data.content);
                } else {
                    holder.setText(R.id.item_friend, data.content);
                }
            }
        };

        adapter.setOnItemClickListener(position -> LogUtil.d("TAG", "点击子条目 -> " + recyclerData.get(position)));
        return adapter;
    }

    public class ChatItem {
        public String content;
        public int type;

        public ChatItem(String content, int type) {
            this.content = content;
            this.type = type;
        }
    }
}
