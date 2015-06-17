package com.yokmama.learn10.chapter06.lesson27;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.GridLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvPreview;

    private Calculator mCalculator = new Calculator();

    private int[] mBtnResIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.dot, R.id.equal, R.id.sum, R.id.difference, R.id.product, R.id.quotient,
            R.id.clear};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //インスタンスを取得
        mTvPreview = (TextView) findViewById(R.id.preview);
        //TODO:レッスンではここにプログラムを追加
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // GridLayout内のアイテムをレイアウトサイズに合わせてストレッチ
        //TODO:レッスンではここにプログラムを追加
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.clear) {
            //計算をリセット
            mCalculator.reset();
            mTvPreview.setText("0");
        } else {
            //入力した値を元に計算
            String input = ((Button) v).getText().toString();
            String dispText = mCalculator.input(input);

            //計算結果をTextViewに表示
            if (!TextUtils.isEmpty(dispText)) {
                mTvPreview.setText(dispText);
            }
        }
    }
}