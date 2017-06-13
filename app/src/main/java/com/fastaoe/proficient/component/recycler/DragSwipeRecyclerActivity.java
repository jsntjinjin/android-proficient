package com.fastaoe.proficient.component.recycler;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.weight.recycler.GridLayoutItemDecoration;
import com.fastaoe.proficient.weight.recycler.base.RecyclerAdapter;
import com.fastaoe.proficient.weight.recycler.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jinjin on 2017/6/10.
 */

@ContentView(R.layout.activity_recycler_drag_swipe)
public class DragSwipeRecyclerActivity extends BaseSkinActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;
    private List<String> recyclerData;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("侧滑删除和item拖动recycler")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        recyclerData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            recyclerData.add("第" + i + "条数据");
        }

        RecyclerAdapter adapter = initRecyclerAdapter(recyclerData);

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter);
        recycler_view.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_dirver_01));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                // 拖动
                int dragFlags = 0;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    // GridView 样式四个方向都可以
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.LEFT |
                            ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT;
                } else {
                    // ListView 样式不支持左右，只支持上下
                    dragFlags = ItemTouchHelper.UP |
                            ItemTouchHelper.DOWN;
                }

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int targetPosition = target.getAdapterPosition();


                if (fromPosition < targetPosition) {
                    for (int i = fromPosition; i < targetPosition; i++) {
                        Collections.swap(recyclerData, i, i + 1);// 改变实际的数据集
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(recyclerData, i, i - 1);// 改变实际的数据集
                    }
                }

                adapter.notifyItemMoved(fromPosition, targetPosition);

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int currentPosition = viewHolder.getAdapterPosition();
                recyclerData.remove(currentPosition);
                adapter.notifyItemRemoved(currentPosition);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.BLUE);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                viewHolder.itemView.setBackgroundColor(Color.RED);
                ViewCompat.setTranslationX(viewHolder.itemView, 0);

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
        });
        helper.attachToRecyclerView(recycler_view);
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

}
