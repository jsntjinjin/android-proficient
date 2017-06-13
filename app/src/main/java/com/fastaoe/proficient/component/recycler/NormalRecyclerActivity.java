package com.fastaoe.proficient.component.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.weight.recycler.GridLayoutItemDecoration;
import com.fastaoe.proficient.weight.recycler.LinearLayoutItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 17/6/13.
 * description:
 */

@ContentView(R.layout.activity_recycler_normal)
public class NormalRecyclerActivity extends BaseSkinActivity {
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    private List<ChatItem> list;

    private boolean isGridLayout = false;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("普通recyclerview")
                .setRightText("切换")
                .setRightClickListener(v ->{
                    if (isGridLayout) {
                        recycler_view.setLayoutManager(new LinearLayoutManager(this));
                    } else {
                        recycler_view.setLayoutManager(new GridLayoutManager(this, 4));
                    }
                    isGridLayout = !isGridLayout;
                })
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0) {
                list.add(new ChatItem("第" + i + "个item", 0));
            } else {
                list.add(new ChatItem("第" + i + "个item", 1));
            }
        }

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(new MyAdapter());
        recycler_view.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_dirver_01));

    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(NormalRecyclerActivity.this).inflate(R.layout.item_chat_01, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.item_me.setText(list.get(position).content);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView item_me;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_me = (TextView) itemView.findViewById(R.id.item_me);
        }
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
