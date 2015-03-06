package chapter04.android.learn10.yokmama.com.chapter04.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import chapter04.android.learn10.yokmama.com.chapter04.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingBarFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rating_bar, container, false);

        RatingBar ratingBar1 = (RatingBar)rootView.findViewById(R.id.ratingBar1);
        RatingBar ratingBar4 = (RatingBar)rootView.findViewById(R.id.ratingBar4);

        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                RatingBar ratingBar2 = (RatingBar)getView().findViewById(R.id.ratingBar2);
                ratingBar2.setRating(rating);
            }
        });

        ratingBar4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                RatingBar ratingBar3 = (RatingBar)getView().findViewById(R.id.ratingBar3);
                ratingBar3.setRating(rating);
            }
        });

        return rootView;
    }


}
