package com.yokmama.learn10.chapter07.lesson33;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yokmama on 15/03/17.
 */
public class MyAdapter extends BaseAdapter{
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
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.list_row, parent, false);
            convertView.setTag(new ImageViewHolder(convertView));
        }

        ImageViewHolder holder = (ImageViewHolder)convertView.getTag();
        ImageItem imageItem = (ImageItem)mItems.get(position);

        holder.getTextView().setText(imageItem.getName());
        holder.getImageView().setImageResource(imageItem.getId());


        return convertView;
    }

}

