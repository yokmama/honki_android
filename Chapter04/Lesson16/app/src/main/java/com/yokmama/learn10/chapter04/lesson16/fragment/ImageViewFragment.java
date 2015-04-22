package com.yokmama.learn10.chapter04.lesson16.fragment;


import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.yokmama.learn10.chapter04.lesson16.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageViewFragment extends Fragment implements View.OnClickListener {

    private ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_view, container, false);

        //ImageViewのインスタンスを取得
        mImageView = (ImageView) rootView.findViewById(R.id.imageView);

        //リスナーをセット
        rootView.findViewById(R.id.center).setOnClickListener(this);
        rootView.findViewById(R.id.centerCrop).setOnClickListener(this);
        rootView.findViewById(R.id.centerInside).setOnClickListener(this);
        rootView.findViewById(R.id.fitCenter).setOnClickListener(this);
        rootView.findViewById(R.id.fitStart).setOnClickListener(this);
        rootView.findViewById(R.id.fitEnd).setOnClickListener(this);
        rootView.findViewById(R.id.fitXY).setOnClickListener(this);
        rootView.findViewById(R.id.matrix).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.center) {
            //スケールタイプをCENTERに変更
            mImageView.setScaleType(ImageView.ScaleType.CENTER);
        } else if (viewId == R.id.centerCrop) {
            //スケールタイプをCENTER_CROPに変更
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else if (viewId == R.id.centerInside) {
            //スケールタイプをCENTER_INSIDEに変更
            mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else if (viewId == R.id.fitCenter) {
            //スケールタイプをFIT_CENTERに変更
            mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else if (viewId == R.id.fitStart) {
            //スケールタイプをFIT_STARTに変更
            mImageView.setScaleType(ImageView.ScaleType.FIT_START);
        } else if (viewId == R.id.fitEnd) {
            //スケールタイプをFIT_ENDに変更
            mImageView.setScaleType(ImageView.ScaleType.FIT_END);
        } else if (viewId == R.id.fitXY) {
            //スケールタイプをFIT_XYに変更
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else if (viewId == R.id.matrix) {
            //スケールタイプをMATRIXに変更
            mImageView.setScaleType(ImageView.ScaleType.MATRIX);

            //画像を180度回転させるMatrixを適用
            Matrix matrix = new Matrix();
            matrix.postRotate(180, mImageView.getWidth() / 2, mImageView.getHeight() / 2);
            mImageView.setImageMatrix(matrix);
        }
    }
}