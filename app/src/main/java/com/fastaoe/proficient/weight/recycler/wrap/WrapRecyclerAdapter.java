package com.fastaoe.proficient.weight.recycler.wrap;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fastaoe.proficient.weight.recycler.base.ViewHolder;

/**
 * Created by jinjin on 17/6/12.
 * description:
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_HEADER_KEY = 100000;
    private static final int BASE_FOOTER_KEY = 200000;
    private RecyclerView.Adapter adapter;

    private SparseArray<View> headers, footers;

    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        headers = new SparseArray<>();
        footers = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headers.indexOfKey(viewType) >= 0) {
            return createHeaderOrFooterViewHolder(headers.get(viewType));
        } else if (footers.indexOfKey(viewType) >= 0) {
            return createHeaderOrFooterViewHolder(footers.get(viewType));
        }

        return adapter.onCreateViewHolder(parent, viewType);
    }

    private RecyclerView.ViewHolder createHeaderOrFooterViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerCount = headers.size();
        if (position < headerCount) {
            return;
        }
        int adapterPosition = position - headerCount;
        int adapterCount = 0;
        if (adapter != null) {
            adapterCount = adapter.getItemCount();
            if (adapterPosition < adapterCount) {
                adapter.onBindViewHolder(holder, adapterPosition);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        int headerCount = headers.size();

        if (position < headerCount) {
            return headers.keyAt(position);
        }

        int adapterCount = adapter.getItemCount();
        int adapterPosition = position - headerCount;
        if (adapterPosition < adapterCount) {
            return adapter.getItemViewType(adapterPosition);
        }

        int footerPosition = adapterPosition - adapterCount;

        return footers.keyAt(footerPosition);
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + headers.size() + footers.size();
    }

    public void addHeaderView(View view) {
        if (headers.indexOfValue(view) == -1) {
            headers.put(BASE_HEADER_KEY, view);
        }
    }

    public void removeHeaderView(View view) {
        if (headers.indexOfValue(view) >= 0) {
            headers.removeAt(headers.indexOfValue(view));
        }
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        if (footers.indexOfValue(view) == -1) {
            footers.put(BASE_FOOTER_KEY, view);
        }

    }

    public void removeFooterView(View view) {
        if (footers.indexOfValue(view) >= 0) {
            footers.removeAt(footers.indexOfValue(view));
        }
        notifyDataSetChanged();

    }

    /**
     * 是不是底部位置
     */
    private boolean isFooterPosition(int position) {
        return position >= (headers.size() + adapter.getItemCount());
    }

    /**
     * 是不是头部位置
     */
    private boolean isHeaderPosition(int position) {
        return position < headers.size();
    }

    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     *
     * @param recycler
     * @version 1.0
     */
    public void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =
                            isHeaderPosition(position) || isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}
