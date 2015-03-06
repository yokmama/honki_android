package chapter04.android.learn10.yokmama.com.chapter04.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import chapter04.android.learn10.yokmama.com.chapter04.R;

//入力処理
public class ButtonFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_button, container, false);

        //Buttonクリック処理
        rootView.findViewById(R.id.buttonNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button Click!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


}
