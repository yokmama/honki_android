package com.yokmama.learn10.chapter07.lesson34.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yokmama.learn10.chapter07.lesson34.R;

/**
 * Created by kayo on 15/04/15.
 */
public class TranslateAnimationDialogActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_animation_dialog);
        findViewById(R.id.btn_fragment).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TranslateAnimationDialogFragment f
                = new TranslateAnimationDialogFragment();
        f.show(getSupportFragmentManager(), "Fragment");
    }

}
