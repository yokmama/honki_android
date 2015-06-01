package com.yokmama.learn10.chapter04.lesson16.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import com.yokmama.learn10.chapter04.lesson16.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressBarFragment extends Fragment {

    private static final int PROGRESS_MAX = 100;

    private ProgressBar mProgressBar1;

    private ProgressBar mProgressBar2;

    private ProgressBar mProgressBar3;

    private ProgressBar mProgressBar4;

    private Timer mTimer;

    private int mCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_progress_bar, container, false);

        //ProgressBarのインスタンスを取得
        mProgressBar1 = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        mProgressBar2 = (ProgressBar) rootView.findViewById(R.id.progressBar2);
        mProgressBar3 = (ProgressBar) rootView.findViewById(R.id.progressBar3);
        mProgressBar4 = (ProgressBar) rootView.findViewById(R.id.progressBar4);

        //ProgressBarの最大値をセット
        mProgressBar1.setMax(PROGRESS_MAX);
        mProgressBar2.setMax(PROGRESS_MAX);
        mProgressBar3.setMax(PROGRESS_MAX);
        mProgressBar4.setMax(PROGRESS_MAX);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        //ProgressBarの進捗を開始.
        stopProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        //ProgressBarの進捗を停止.
        startProgress();
    }

    /**
     * ProgressBarの進捗を進める.
     */
    private void startProgress() {
        if (mTimer == null) {
            mTimer = new Timer(true);
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mCounter++;
                    if (mCounter > PROGRESS_MAX) {
                        mCounter = 0;
                    }
                    updateProgress();
                }
            }, 0, 50);
        }
    }

    /**
     * ProgressBarの進捗を停止.
     */
    private void stopProgress() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * ProgressBarを更新.
     */
    private void updateProgress() {
        int secondary = (mCounter * 2) % 100;

        mProgressBar1.setProgress(mCounter);
        mProgressBar2.setProgress(mCounter);
        mProgressBar3.setProgress(mCounter);
        mProgressBar4.setProgress(mCounter);

        mProgressBar1.setSecondaryProgress(secondary);
        mProgressBar2.setSecondaryProgress(secondary);
        mProgressBar3.setSecondaryProgress(secondary);
        mProgressBar4.setSecondaryProgress(secondary);
    }
}
