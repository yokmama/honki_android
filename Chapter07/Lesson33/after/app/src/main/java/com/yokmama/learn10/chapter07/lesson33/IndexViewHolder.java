package com.yokmama.learn10.chapter07.lesson33;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yokmama on 15/03/17.
 */
public class IndexViewHolder extends RecyclerView.ViewHolder{
    private TextView mTextView;

    public IndexViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView)itemView.findViewById(R.id.textView);
    }

    public TextView getTextView() {
        return mTextView;
    }
}
