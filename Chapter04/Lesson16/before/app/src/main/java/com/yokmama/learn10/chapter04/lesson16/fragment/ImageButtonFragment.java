package com.yokmama.learn10.chapter04.lesson16.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yokmama.learn10.chapter04.lesson16.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageButtonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_button, container, false);

        return rootView;
    }
}
