package com.fastaoe.proficient.weight.recycler.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jinjin on 17/6/12.
 * description:
 */

public class WrapRecyclerView extends RecyclerView {

    private WrapRecyclerAdapter adapter;
    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            adapter.notifyItemRangeChanged(positionStart,itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            adapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            adapter.notifyItemRangeInserted(positionStart,itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof WrapRecyclerAdapter) {
            this.adapter = (WrapRecyclerAdapter) adapter;
        } else {
            this.adapter = new WrapRecyclerAdapter(adapter);
            adapter.registerAdapterDataObserver(observer);
        }
        super.setAdapter(this.adapter);
    }

    public void addHeaderView(View view) {
        if (adapter != null) {
            adapter.addHeaderView(view);
        }
    }

    public void removeHeaderView(View view) {
        if (adapter != null) {
            adapter.removeHeaderView(view);
        }
    }

    public void addFooterView(View view) {
        if (adapter != null) {
            adapter.addFooterView(view);
        }
    }

    public void removeFooterView(View view) {
        if (adapter != null) {
            adapter.removeFooterView(view);
        }
    }
}
