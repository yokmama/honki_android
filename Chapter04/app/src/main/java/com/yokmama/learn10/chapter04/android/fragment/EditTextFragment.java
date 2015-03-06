package com.yokmama.learn10.chapter04.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yokmama.learn10.chapter04.android.R;

/**
 * Created by yokmama on 15/02/19.
 */
public class EditTextFragment extends Fragment {
    private static final String TAG = EditTextFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_text, container, false);

        //入力制限
        EditText editValidate = (EditText) rootView.findViewById(R.id.editValidate);
        // フィルターを作成
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source.toString().matches("^[0-9a-zA-Z@¥.¥_¥¥-]+$")) {
                    return source;
                } else {
                    return "";
                }
            }
        };
        editValidate.setFilters(new InputFilter[]{inputFilter});

        //入力処理
        EditText editChange = (EditText) rootView.findViewById(R.id.editChange);
        // リスナーを作成
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "input:" + s.toString());

            }
        };
        editChange.addTextChangedListener(textWatcher);

        return rootView;
    }
}
