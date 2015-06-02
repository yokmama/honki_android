package com.yokmama.learn10.chapter04.lesson17.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yokmama.learn10.chapter04.lesson17.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class RelativeLayoutFragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_relative_layout2, container, false);
    }


}
