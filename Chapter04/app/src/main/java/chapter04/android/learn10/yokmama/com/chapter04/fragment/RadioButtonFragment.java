package chapter04.android.learn10.yokmama.com.chapter04.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import chapter04.android.learn10.yokmama.com.chapter04.R;

/**
 * Created by yokmama on 15/02/19.
 */
public class RadioButtonFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_radio_button, container, false);

        //ラジオボタンの状態が変更された際の処理を設定
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                Toast.makeText(getActivity(), radioButton.getText() + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


}
