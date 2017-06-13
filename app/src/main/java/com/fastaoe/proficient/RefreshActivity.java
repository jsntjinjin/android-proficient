package com.fastaoe.proficient;

import android.support.v7.widget.GridLayoutManager;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.weight.recycler.DefaultLoadCreator;
import com.fastaoe.proficient.weight.recycler.DefaultRefreshCreator;
import com.fastaoe.proficient.weight.recycler.LinearLayoutItemDecoration;
import com.fastaoe.proficient.weight.recycler.refresh.LoadRefreshRecyclerView;
import com.fastaoe.proficient.weight.recycler.base.RecyclerAdapter;
import com.fastaoe.proficient.weight.recycler.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 17/6/12.
 * description:
 */

@ContentView(R.layout.activity_refresh)
public class RefreshActivity extends BaseSkinActivity {

    @Bind(R.id.refresh_view)
    LoadRefreshRecyclerView refresh_view;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("refresh")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        List<ChatItem> list = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            if (i % 3 == 0) {
                list.add(new ChatItem("第" + i + "个item", 0));
            } else {
                list.add(new ChatItem("第" + i + "个item", 1));
            }
        }

        refresh_view.setLayoutManager(new GridLayoutManager(this, 2));
        refresh_view.setAdapter(initRecyclerAdapter(list));
        refresh_view.addItemDecoration(new LinearLayoutItemDecoration(this, R.drawable.item_dirver_01));
        refresh_view.addRefreshViewCreator(new DefaultRefreshCreator());
        refresh_view.addLoadViewCreator(new DefaultLoadCreator());

        refresh_view.setOnRefreshListener(() -> refresh_view.postDelayed(() -> refresh_view.stopRefresh(), 3000));
        refresh_view.setOnLoadMoreListener(() -> refresh_view.postDelayed(() -> {
            for (int i = 100; i < 200; i++) {
                if (i % 3 == 0) {
                    list.add(new ChatItem("第" + i + "个item", 0));
                } else {
                    list.add(new ChatItem("第" + i + "个item", 1));
                }
            }
            refresh_view.stopLoad();
        }, 3000));
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
