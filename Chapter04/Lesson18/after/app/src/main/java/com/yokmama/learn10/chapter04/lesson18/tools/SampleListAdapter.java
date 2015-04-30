package com.yokmama.learn10.chapter04.lesson18.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yokmama.learn10.chapter04.lesson18.tools.ListItem;

import java.util.List;

/**
 * Created by yokmama on 15/03/10.
 */
public class SampleListAdapter extends BaseAdapter{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<ListItem> mItems;

    public SampleListAdapter(Context context, List<ListItem> items){
        mContext = context;
        mItems = items;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mItems.size();
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
            convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        ListItem item = mItems.get(position);

        ((TextView)convertView).setText(item.getName());

        return convertView;
    }
}
