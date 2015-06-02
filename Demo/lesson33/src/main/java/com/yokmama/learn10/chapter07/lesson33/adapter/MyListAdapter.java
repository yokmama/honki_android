package com.yokmama.learn10.chapter07.lesson33.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yokmama.learn10.chapter07.lesson33.ImageViewHolder;
import com.yokmama.learn10.chapter07.lesson33.IndexViewHolder;
import com.yokmama.learn10.chapter07.lesson33.R;
import com.yokmama.learn10.chapter07.lesson33.item.BaseItem;
import com.yokmama.learn10.chapter07.lesson33.item.ImageItem;
import com.yokmama.learn10.chapter07.lesson33.item.IndexItem;

import java.util.List;

/**
 * Created by yokmama on 15/03/17.
 */
public class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private List<BaseItem> mItems;

    public MyListAdapter(Context context, List<BaseItem> items){
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        if(type == R.id.index_type){
            return new IndexViewHolder(mLayoutInflater.inflate(R.layout.index_row, viewGroup, false));
        }else{
            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.list_row, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BaseItem item = mItems.get(position);
        if(item.getType() == R.id.index_type){
            IndexItem indexItem = (IndexItem)item;
            IndexViewHolder indexViewHolder = (IndexViewHolder)viewHolder;
            indexViewHolder.getTextView().setText(indexItem.getName());

        }else{
            ImageItem imageItem = (ImageItem)item;
            ImageViewHolder imageViewHolder = (ImageViewHolder)viewHolder;
            imageViewHolder.getTextView().setText(imageItem.getName());
            imageViewHolder.getImageView().setImageResource(imageItem.getId());
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

