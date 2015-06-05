package com.yokmama.learn10.chapter07.lesson30.fragment;

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
import android.widget.Toast;

import com.yokmama.learn10.chapter07.lesson30.R;

/**
 * Created by yokmama on 15/02/19.
 */
public class EditTextFragment extends Fragment {

    private static final String TAG = EditTextFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_text, container, false);

        //EditTextのインスタンスを取得
        EditText etValidate = (EditText) rootView.findViewById(R.id.editValidate);

        // 入力を制限するInputFilterを作成
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
        //InputFilterをセット
        etValidate.setFilters(new InputFilter[]{inputFilter});

        //入力チェックを設定
        EditText userid = (EditText) rootView.findViewById(R.id.userid);
        //リスナーをセット
        userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //テキストが変更される直前に呼ばれる
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //テキストが変更されたら呼ばれる
                if (count > 10) {
                    //１０文字以上入力したらエラーを設定する
                    Toast.makeText(getActivity(), "文字数は１０以下にしてください", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //テキストが変更された後に呼ばれる
            }
        });

        return rootView;
    }
}
