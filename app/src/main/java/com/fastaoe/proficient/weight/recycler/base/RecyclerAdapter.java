package com.fastaoe.proficient.weight.recycler.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jinjin on 2017/6/11.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private int layoutId;
    private List<T> data;
    private LayoutInflater inflater;
    private MuliteTypeSupport typeSupport;

    public RecyclerAdapter(Context context, List<T> data, int layoutId) {
        this.layoutId = layoutId;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    public RecyclerAdapter(Context context, List<T> data, MuliteTypeSupport typeSupport) {
        this(context, data, -1);
        this.typeSupport = typeSupport;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (typeSupport != null) {
            layoutId = viewType;
        }
        View view = inflater.inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (typeSupport != null) {
            return typeSupport.getLayoutId(data.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, data.get(position), position);

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
        }
        if (longClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> longClickListener.onItemLongClick(position));
        }
    }

    protected abstract void convert(ViewHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }

    private ItemClickListener listener;

    public RecyclerAdapter setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    private ItemLongClickListener longClickListener;

    public RecyclerAdapter setOnItemLongClickListener(ItemLongClickListener listener) {
        this.longClickListener = listener;
        return this;
    }
}
