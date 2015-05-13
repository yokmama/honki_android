package com.yokmama.learn10.chapter07.lesson33.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
public class MyListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<BaseItem> mItems;

    public MyListAdapter(Context context, List<BaseItem> items){
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public int getCount() {
        if(mItems!=null) {
            return mItems.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageViewHolder holder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.list_row, parent, false);
            holder = new ImageViewHolder(convertView);
        }else{
            holder = (ImageViewHolder)convertView.getTag();
        }

        ImageItem imageItem = (ImageItem)mItems.get(position);

        holder.getTextView().setText(imageItem.getName());
        holder.getImageView().setImageResource(imageItem.getId());


        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }
}

