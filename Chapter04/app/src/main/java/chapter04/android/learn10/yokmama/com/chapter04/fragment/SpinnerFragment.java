package chapter04.android.learn10.yokmama.com.chapter04.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import chapter04.android.learn10.yokmama.com.chapter04.R;

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


        mSpinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
        mSpinner2 = (Spinner) rootView.findViewById(R.id.spinner2);

        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }


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
            String[] arrays = getResources().getStringArray(selecteArray);
            ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
            for (int i = 0; i < arrays.length; i++) {
                mAdapter.add(arrays[i]);
            }
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner2.setAdapter(mAdapter);
        }
    }
}
