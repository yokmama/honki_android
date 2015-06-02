package com.yokmama.learn10.chapter04.lesson16.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.lesson16.R;

/**
 * Created by yokmama on 15/02/19.
 */
public class RadioButtonFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_radio_button, container, false);

        //RadioGroupのインスタンスを取得
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);

        //リスナーをセット
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //選択したRadioButtonのテキストを表示
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                Toast.makeText(getActivity(), radioButton.getText() + " Selected",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


}
