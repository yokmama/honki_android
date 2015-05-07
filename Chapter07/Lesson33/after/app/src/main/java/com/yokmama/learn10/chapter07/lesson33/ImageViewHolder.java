package com.yokmama.learn10.chapter07.lesson33;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yokmama on 15/03/17.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder{
    private ImageView mImageView;
    private TextView mTextView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView)itemView.findViewById(R.id.imageView);
        mTextView = (TextView)itemView.findViewById(R.id.textView);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }
}
