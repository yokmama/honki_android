package com.yokmama.learn10.chapter04.lesson16.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yokmama.learn10.chapter04.lesson16.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SnackbarFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_snack_bar, container, false);

        //Toastの使用例
        rootView.findViewById(R.id.buttonToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show()を忘れないないように
                Toast.makeText(getActivity(), "こんにちはToastです。", Toast.LENGTH_SHORT).show();

            }
        });

        //SnabkBarの使用例
        rootView.findViewById(R.id.buttonSnackbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SnackbarがかかるViewを親として指定
                Snackbar.make(getView(), "あなたはToastですか？", Snackbar.LENGTH_SHORT)
                        .setAction("いいえ", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), "私はSnackbarです。", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

            }
        });

        return rootView;
    }


}
