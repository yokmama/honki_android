
package com.yokmama.learn10.chapter06.lesson27;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Calculator {

    StringBuilder mInputNumber = new StringBuilder();

    String mOperator;

    float mResult = 0;

    private boolean isNumber(String key) {
        try {
            Integer.parseInt(key);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    private boolean isSupportedOperator(String key) {
        if (key.equals("+")) {
            return true;
        } else if (key.equals("-")) {
            return true;
        } else if (key.equals("×")) {
            return true;
        } else if (key.equals("÷")) {
            return true;
        } else if (key.equals("=")) {
            return true;
        }
        return false;
    }

    private void doCalculation(String ope) {
        if (TextUtils.isEmpty(mInputNumber.toString())) {
            return;
        }
        if (ope.equals("+")) {
            mResult = mResult + Float.parseFloat(mInputNumber.toString());
        } else if (ope.equals("-")) {
            mResult = mResult - Float.parseFloat(mInputNumber.toString());
        } else if (ope.equals("×")) {
            mResult = mResult * Float.parseFloat(mInputNumber.toString());
        } else if (ope.equals("÷")) {
            mResult = mResult / Float.parseFloat(mInputNumber.toString());
        }
        mInputNumber = new StringBuilder();
    }

    public void reset() {
        mOperator = null;
        mResult = 0;
        mInputNumber = new StringBuilder();
    }

    public String putInput(String key) {
        if (isNumber(key) || TextUtils.equals(key, ".")) {
            mInputNumber.append(key);
            return mInputNumber.toString();
        } else if (isSupportedOperator(key)) {
            if (key.equals("=")) {
                if (mOperator != null) {
                    doCalculation(mOperator);
                    mOperator = null;
                }
                DecimalFormat formatter = new DecimalFormat();
                return formatter.format(mResult);
            } else {
                if (mOperator != null) {
                    doCalculation(mOperator);
                    mOperator = null;
                } else if (mInputNumber.length() > 0) {
                    mResult = Float.parseFloat(mInputNumber.toString());
                    mInputNumber = new StringBuilder();
                }
                mOperator = key;
                return mOperator;
            }
        } else {
            return null;
        }
    }
}
