package com.yokmama.learn10.chapter04.lesson18.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.yokmama.learn10.chapter04.lesson18.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScrollViewFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scroll_view, container, false);

        return rootView;
    }


}
