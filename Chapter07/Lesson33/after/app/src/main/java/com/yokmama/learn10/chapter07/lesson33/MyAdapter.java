package com.yokmama.learn10.chapter07.lesson33;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yokmama on 15/03/17.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private List<BaseItem> mItems;
    private LayouType mLayoutType = LayouType.ListStyle;

    public enum LayouType{
        ListStyle,
        GridStyle
    }

    public MyAdapter(Context context){
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setItems(List<BaseItem> items){
        mItems = items;
    }

    public void setLayoutType(LayouType layoutType) {
        mLayoutType = layoutType;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        if(type == 0){
            return new IndexViewHolder(mLayoutInflater.inflate(R.layout.index_row, viewGroup, false));
        }else{
            if(mLayoutType == LayouType.GridStyle) {
                return new ImageViewHolder(mLayoutInflater.inflate(R.layout.grid_row, viewGroup, false));
            }else{
                return new ImageViewHolder(mLayoutInflater.inflate(R.layout.list_row, viewGroup, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BaseItem item = mItems.get(position);
        if(item.getType() == 0){
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

