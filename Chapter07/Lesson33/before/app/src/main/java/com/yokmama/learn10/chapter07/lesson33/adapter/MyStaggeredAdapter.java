package com.yokmama.learn10.chapter07.lesson33.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yokmama.learn10.chapter07.lesson33.ImageViewHolder;
import com.yokmama.learn10.chapter07.lesson33.R;
import com.yokmama.learn10.chapter07.lesson33.item.BaseItem;
import com.yokmama.learn10.chapter07.lesson33.item.ImageItem;

import java.util.List;

/**
 * Created by yokmama on 15/05/13.
 */
public class MyStaggeredAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<BaseItem> mItems;

    public MyStaggeredAdapter(Context context, List<BaseItem> items){
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BaseItem item = mItems.get(position);
        ImageItem imageItem = (ImageItem)item;
        ImageViewHolder imageViewHolder = (ImageViewHolder)viewHolder;
        imageViewHolder.getTextView().setText(imageItem.getName());
        imageViewHolder.getImageView().setImageResource(imageItem.getId());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (R.id.big_image_type == viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.big_image_row, parent, false);
            return new ImageViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_row, parent, false);
            return new ImageViewHolder(v);
        }
    }

    @Override
    public int getItemCount() {
        if(mItems!=null) {
            return mItems.size();
        }else{
            return 0;
        }
    }
}
