package com.fastaoe.proficient.component.views.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.fastaoe.proficient.R;
import com.fastaoe.proficient.weight.recycler.base.RecyclerAdapter;
import com.fastaoe.proficient.weight.recycler.base.ViewHolder;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 2017/6/13.
 * description:
 */

public class SelectImageListAdapter extends RecyclerAdapter<String> {

    private ArrayList<String> mResultList;
    private int mMaxCount;


    public SelectImageListAdapter(Context context, List<String> data, ArrayList<String> resultList, int maxCount) {
        super(context, data, R.layout.item_media_choose);
        this.mResultList = resultList;
        this.mMaxCount = maxCount;
    }

    @Override
    public void convert(ViewHolder holder, String item, int position) {
        if (TextUtils.isEmpty(item)) {
            // 显示拍照
            holder.setViewVisibility(R.id.camera_ll, View.VISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.INVISIBLE);
            holder.setViewVisibility(R.id.image, View.INVISIBLE);
        } else {
            // 显示图片
            holder.setViewVisibility(R.id.camera_ll, View.INVISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.VISIBLE);
            holder.setViewVisibility(R.id.image, View.VISIBLE);

            // 显示图片利用Glide
            ImageView imageView = holder.getView(R.id.image);
            ImageOptions options = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.CENTER)
                    .build();
            x.image().bind(imageView, item, options);

            ImageView selectIndicatorIv = holder.getView(R.id.media_selected_indicator);

            if (mResultList.contains(item)) {
                selectIndicatorIv.setSelected(true);
            } else {
                selectIndicatorIv.setSelected(false);
            }

            holder.setOnItemClickListener(v -> {
                if (!mResultList.contains(item)) {
                    mResultList.add(item);
                } else {
                    mResultList.remove(item);
                }

                notifyDataSetChanged();
            });

            // 通知显示布局
            if(mListener != null){
                mListener.select();
            }
        }
    }

    // 设置选择图片监听
    private SelectImageListener mListener;
    public void setOnSelectImageListener(SelectImageListener listener){
        this.mListener = listener;
    }

    public interface SelectImageListener {
        // 选择回调
        void select();
    }
}
