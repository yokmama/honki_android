package com.yokmama.learn10.chapter07.lesson30.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.yokmama.learn10.chapter07.lesson30.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingBarFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rating_bar, container, false);

        //RatingBarのインスタンスを取得
        final RatingBar ratingBar1 = (RatingBar) rootView.findViewById(R.id.ratingBar1);
        final RatingBar ratingBar2 = (RatingBar) rootView.findViewById(R.id.ratingBar2);
        final RatingBar ratingBar3 = (RatingBar) rootView.findViewById(R.id.ratingBar3);

        //リスナーをセット
        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //他のRatingBarにも同じレーティングをセット
                ratingBar2.setRating(rating);
                ratingBar3.setRating(rating);
            }
        });
        return rootView;
    }


}
