package com.yokmama.learn10.chapter04.lesson16.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.lesson16.R;


/**
 * Created by yokmama on 15/02/19.
 */
public class CheckBoxFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_box, container, false);

        //各Checkboxにリスナーをセット
        CheckBox checkBox1 = (CheckBox) rootView.findViewById(R.id.checkBox1);
        checkBox1.setOnCheckedChangeListener(mListener);

        CheckBox checkBox2 = (CheckBox) rootView.findViewById(R.id.checkBox2);
        checkBox2.setOnCheckedChangeListener(mListener);

        CheckBox checkBox3 = (CheckBox) rootView.findViewById(R.id.checkBox3);
        checkBox3.setOnCheckedChangeListener(mListener);

        return rootView;
    }

    //Checkboxの変更を検出するリスナー
    CompoundButton.OnCheckedChangeListener mListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                //トーストを表示
                Toast.makeText(getActivity(), buttonView.getText() + " Checked", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };


}
