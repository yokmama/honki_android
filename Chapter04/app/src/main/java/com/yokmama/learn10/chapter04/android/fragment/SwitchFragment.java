package com.yokmama.learn10.chapter04.android.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.android.R;

/**
 * Created by yokmama on 15/02/19.
 */
public class SwitchFragment extends Fragment {
    Switch mSwitch1;
    TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_switch, container, false);

        mTextView = (TextView) rootView.findViewById(R.id.textView);
        mSwitch1 = (Switch) rootView.findViewById(R.id.switch1);
        mSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), "onCheckedChanged:" + isChecked, Toast.LENGTH_SHORT).show();
                updateText();
            }
        });

        updateText();

        return rootView;
    }


    private void updateText() {
        mTextView.setText("Switch is " + mSwitch1.isChecked());
    }

}
