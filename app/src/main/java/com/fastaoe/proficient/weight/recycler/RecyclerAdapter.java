package com.fastaoe.proficient.weight.recycler;

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

    public RecyclerAdapter(Context context,int layoutId, List<T> data) {
        this.layoutId = layoutId;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, data.get(position), position);

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
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
