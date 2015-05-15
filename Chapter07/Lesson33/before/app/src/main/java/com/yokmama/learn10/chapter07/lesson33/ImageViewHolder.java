package com.yokmama.learn10.chapter07.lesson33;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yokmama on 15/03/17.
 */
public class ImageViewHolder {
    private ImageView mImageView;
    private TextView mTextView;

    public ImageViewHolder(View itemView) {
        mImageView = (ImageView)itemView.findViewById(R.id.imageView);
        mTextView = (TextView)itemView.findViewById(R.id.textView);
        itemView.setTag(this);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }
}
