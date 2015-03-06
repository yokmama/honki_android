package com.yokmama.learn10.chapter04.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageButtonFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_image_button, container, false);

        rootView.findViewById(R.id.imageButton1).setOnClickListener(this);
        rootView.findViewById(R.id.imageButton2).setOnClickListener(this);
        rootView.findViewById(R.id.imageButton3).setOnClickListener(this);
        rootView.findViewById(R.id.imageButton4).setOnClickListener(this);
        rootView.findViewById(R.id.imageButton5).setOnClickListener(this);
        rootView.findViewById(R.id.imageButton6).setOnClickListener(this);
        rootView.findViewById(R.id.imageButton7).setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        ImageButton button = (ImageButton)v;

        Toast.makeText(getActivity(), "Click "+button.getScaleType().toString(), Toast.LENGTH_SHORT).show();
    }
}
