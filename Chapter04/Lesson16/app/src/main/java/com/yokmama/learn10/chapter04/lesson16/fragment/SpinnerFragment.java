package com.yokmama.learn10.chapter04.lesson16.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.yokmama.learn10.chapter04.lesson16.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpinnerFragment extends Fragment {

    private Spinner mSpinner1;

    private Spinner mSpinner2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spinner, container, false);

        //Spinnerのインスタンスを取得
        mSpinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
        mSpinner2 = (Spinner) rootView.findViewById(R.id.spinner2);

        //リスナーをセット
        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Spinnerを更新
                updateSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }


    /**
     * Spinnerのアイテムを更新.
     */
    private void updateSpinner() {
        String selectedItem = (String) mSpinner1.getSelectedItem();
        int selecteArray = -1;
        if ("果物".equals(selectedItem)) {
            selecteArray = R.array.sub_fruit;
        } else if ("動物".equals(selectedItem)) {
            selecteArray = R.array.sub_animal;
        } else if ("乗り物".equals(selectedItem)) {
            selecteArray = R.array.sub_vehicle;
        }

        if (selecteArray != -1) {
            //セットするアイテムを読込
            String[] arrays = getResources().getStringArray(selecteArray);
            ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item);
            for (int i = 0; i < arrays.length; i++) {
                mAdapter.add(arrays[i]);
            }
            //Spinnerにアイテムをセット
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner2.setAdapter(mAdapter);
        }
    }
}
