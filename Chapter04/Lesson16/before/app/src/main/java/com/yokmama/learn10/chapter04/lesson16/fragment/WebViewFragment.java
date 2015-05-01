package com.yokmama.learn10.chapter04.lesson16.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import com.yokmama.learn10.chapter04.lesson16.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    private EditText mEditText;

    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);

        //WebView,EditTextのインスタンスを取得
        mWebView = (WebView) rootView.findViewById(R.id.webView);
        mEditText = (EditText) rootView.findViewById(R.id.editText);

        //JavaScriptをONにする
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //リスナーをセット
        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWebView();
            }
        });

        //WebViewを更新
        updateWebView();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        //WebViewを停止
        mWebView.onPause();
    }


    @Override
    public void onResume() {
        //WebViewを再開
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        //WebViewを開放
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * WebViewを更新.
     */
    private void updateWebView() {
        //入力されているURLをロード
        String url = mEditText.getText().toString();
        mWebView.loadUrl(url);
    }

}
