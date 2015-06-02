package com.yokmama.learn10.chapter07.lesson32;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.List;

/**
 * GridItemを表示するアダプター.
 */
public class GridItemAdapter extends ArrayAdapter<Integer> {

    private static final String TAG = GridItemAdapter.class.getSimpleName();

    private LayoutInflater mLayoutInflater;


    public GridItemAdapter(Context context, List<Integer> items) {
        super(context, 0, items);
        mLayoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_grid_row, null);
            holder = new ViewHolder();
            holder.imagePicture = (ImageView) convertView.findViewById(R.id.imagePicture);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //画像をセット
        holder.imagePicture.setImageResource(getItem(position));

        return convertView;
    }

    private static class ViewHolder {
        ImageView imagePicture;
    }
}