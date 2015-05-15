package com.yokmama.learn10.chapter07.lesson30.fragment;


import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.yokmama.learn10.chapter07.lesson30.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageViewFragment extends Fragment {

    private RadioGroup mRadioFilterType;

    private ImageView mImageColorFilter;

    private SeekBar mSeekBarRed;

    private SeekBar mSeekBarGreen;

    private SeekBar mSeekBarBlue;

    //SeekBarの値が変更された事を受け取るリスナー
    private SeekBar.OnSeekBarChangeListener colorBarChangeListener
            = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            //カラーフィルターを更新
            updateColorFilter(mRadioFilterType);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_view, container, false);

        //RadioGroup,ImageView,SeekBarのインスタンスを取得
        mRadioFilterType = (RadioGroup) rootView.findViewById(R.id.radioFilterType);
        mImageColorFilter = (ImageView) rootView.findViewById(R.id.imageColorFilter);
        mSeekBarRed = (SeekBar) rootView.findViewById(R.id.seekBarRed);
        mSeekBarGreen = (SeekBar) rootView.findViewById(R.id.seekBarGreen);
        mSeekBarBlue = (SeekBar) rootView.findViewById(R.id.seekBarBlue);

        //リスナーをセット
        mRadioFilterType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //カラーフィルターを更新
                updateColorFilter(group);
            }
        });

        mSeekBarRed.setOnSeekBarChangeListener(colorBarChangeListener);
        mSeekBarGreen.setOnSeekBarChangeListener(colorBarChangeListener);
        mSeekBarBlue.setOnSeekBarChangeListener(colorBarChangeListener);

        updateColorFilter(mRadioFilterType);

        return rootView;
    }

    /**
     * カラーフィルターを更新.
     */
    private void updateColorFilter(RadioGroup radioGroup) {
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioColor) {
            setColorFilter(mImageColorFilter, ((float) mSeekBarRed.getProgress()) / 255,
                    ((float) mSeekBarGreen.getProgress()) / 255,
                    ((float) mSeekBarBlue.getProgress()) / 255);
        } else {
            setGrayScale(mImageColorFilter);
        }

    }

    /**
     * グレースケールをセット.
     *
     * @param imageView 対象のImageView
     */
    private void setGrayScale(ImageView imageView) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(filter);
    }

    /**
     * カラーフィルターをセット.
     *
     * @param imageView 対象のImageView
     * @param red       Redの値
     * @param green     Greenの値
     * @param blue      Blueの値
     */
    private void setColorFilter(ImageView imageView, float red, float green, float blue) {
        float[] colorMatrix = {
                (1.0f - red), 0, 0, 0, 0,  //red
                0, (1.0f - green), 0, 0, 0,//green
                0, 0, (1.0f - blue), 0, 0, //blue
                0, 0, 0, 1, 0            //alpha
        };
        ColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(filter);
    }
}
