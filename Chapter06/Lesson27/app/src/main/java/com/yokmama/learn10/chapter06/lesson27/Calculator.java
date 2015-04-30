package com.yokmama.learn10.chapter06.lesson27;

import android.text.TextUtils;

import java.text.DecimalFormat;

public class Calculator {

    private StringBuilder mInputNumber = new StringBuilder();

    private String mOperator;

    private float mResult = 0;

    /**
     * 入力処理.
     *
     * @param value 値
     */
    public String input(String value) {
        if (isNumber(value) || TextUtils.equals(value, ".")) {
            //数字か小数点なら数値として処理
            mInputNumber.append(value);
            return mInputNumber.toString();
        } else if (isOperator(value)) {
            //それ以外は計算操作として処理
            if (value.equals("=")) {
                //イコールが入力されたら計算を実行
                if (mOperator != null) {
                    doCalc(mOperator);
                    mOperator = null;
                }
                return new DecimalFormat().format(mResult);
            } else {
                //イコール以外は計算演算子として処理
                if (mOperator != null) {
                    //計算操作中だった場合は計算を実行
                    doCalc(mOperator);
                } else if (mInputNumber.length() > 0) {
                    //計算操作をしていない場合は数字を格納
                    mResult = Float.parseFloat(mInputNumber.toString());
                    mInputNumber = new StringBuilder();
                }
                mOperator = value;
                return mOperator;
            }
        } else {
            return null;
        }
    }

    /**
     * 計算を実行.
     *
     * @param value 値
     */
    private void doCalc(String value) {
        if (!TextUtils.isEmpty(mInputNumber.toString())) {
            if (value.equals("+")) {
                mResult = mResult + Float.parseFloat(mInputNumber.toString());
            } else if (value.equals("-")) {
                mResult = mResult - Float.parseFloat(mInputNumber.toString());
            } else if (value.equals("×")) {
                mResult = mResult * Float.parseFloat(mInputNumber.toString());
            } else if (value.equals("÷")) {
                mResult = mResult / Float.parseFloat(mInputNumber.toString());
            }
            mInputNumber = new StringBuilder();
        }
    }

    /**
     * 計算をリセット.
     */
    public void reset() {
        mOperator = null;
        mResult = 0;
        mInputNumber = new StringBuilder();
    }

    /**
     * 文字列が数字か確認.
     *
     * @param value 値
     */
    private boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    /**
     * 文字列が計算操作か確認.
     *
     * @param value 値
     */
    private boolean isOperator(String value) {
        if (value.equals("+") || value.equals("-") || value.equals("×")
                || (value.equals("÷") || value.equals("="))) {
            return true;
        }
        return false;
    }
}