package com.yokmama.learn10.chapter06.lesson27;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTvPreview;

    private Calculator mCalculator = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvPreview = (TextView) findViewById(R.id.preview);
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.dot).setOnClickListener(this);
        findViewById(R.id.equal).setOnClickListener(this);
        findViewById(R.id.sum).setOnClickListener(this);
        findViewById(R.id.difference).setOnClickListener(this);
        findViewById(R.id.product).setOnClickListener(this);
        findViewById(R.id.quotient).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);

        // GridLayoutの子ViewをLayoutサイズに合わせてストレッチさせる
        final GridLayout gl = (GridLayout) findViewById(R.id.calcFrame);
        gl.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        stretchColumns(gl);
                        stretchRows(gl);
                        gl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    /**
     * GridLayoutの子Viewの横幅ストレッチ.
     */
    public void stretchColumns(GridLayout gl) {
        int childWidth = (int) (gl.getWidth() / gl.getColumnCount());
        for (int i = 0; i < gl.getChildCount(); i++) {
            View childView = gl.getChildAt(i);
            childView.setMinimumWidth(childWidth);
        }
    }

    /**
     * GridLayoutの子Viewの縦幅をストレッチ.
     */
    public void stretchRows(GridLayout gl) {
        int childHeight = (int) (gl.getHeight() / gl.getRowCount());
        for (int i = 0; i < gl.getChildCount(); i++) {
            View childView = gl.getChildAt(i);
            childView.setMinimumHeight(childHeight);
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        if (btn.getId() == R.id.clear) {
            mCalculator.reset();
            mTvPreview.setText("0");
        } else {
            String input = btn.getText().toString();
            Log.d("MainActivity", "input=" + input);

            String dispText = mCalculator.putInput(input);
            if (!TextUtils.isEmpty(dispText)) {
                mTvPreview.setText(dispText);
            }
        }
    }

}
