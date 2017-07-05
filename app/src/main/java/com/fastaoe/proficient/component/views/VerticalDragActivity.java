package com.fastaoe.proficient.component.views;

import android.support.v7.widget.LinearLayoutManager;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.recycler.HeadFooterRecyclerActivity;
import com.fastaoe.proficient.weight.recycler.LinearLayoutItemDecoration;
import com.fastaoe.proficient.weight.recycler.base.RecyclerAdapter;
import com.fastaoe.proficient.weight.recycler.base.ViewHolder;
import com.fastaoe.proficient.weight.recycler.wrap.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.fastaoe.proficient.R.id.recycler_view;

/**
 * Created by jinjin on 2017/7/4.
 * description:
 */

@ContentView(R.layout.activity_view_vertical_drag)
public class VerticalDragActivity extends BaseSkinActivity {

    @Bind(R.id.wrcv)
    WrapRecyclerView wrcv;

    @Override
    protected void initTitle() {

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

        wrcv.setLayoutManager(new LinearLayoutManager(this));
        wrcv.setAdapter(initRecyclerAdapter(list));
        wrcv.addItemDecoration(new LinearLayoutItemDecoration(this, R.drawable.item_dirver_01));

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
