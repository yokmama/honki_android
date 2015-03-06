package chapter04.android.learn10.yokmama.com.chapter04.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import chapter04.android.learn10.yokmama.com.chapter04.R;

/**
 * Created by yokmama on 15/02/19.
 */
public class CheckBoxFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_box, container, false);

        //各ボタンにチェック変更時の処理を設定
        CheckBox checkBox1 = (CheckBox) rootView.findViewById(R.id.checkBox1);
        checkBox1.setOnCheckedChangeListener(mListener);

        CheckBox checkBox2 = (CheckBox) rootView.findViewById(R.id.checkBox2);
        checkBox2.setOnCheckedChangeListener(mListener);

        CheckBox checkBox3 = (CheckBox) rootView.findViewById(R.id.checkBox3);
        checkBox3.setOnCheckedChangeListener(mListener);

        return rootView;
    }

    CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Toast.makeText(getActivity(), buttonView.getText() + " Checked", Toast.LENGTH_SHORT).show();
            }
        }
    };


}
