package com.yokmama.learn10.chapter05.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yokmama.learn10.chapter05.android.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class RelativeLayoutExFragment extends Fragment {


    public RelativeLayoutExFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_relative_ex_layout, container, false);
    }


}
