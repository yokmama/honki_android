package com.yokmama.learn10.chapter07.lesson34.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.yokmama.learn10.chapter07.lesson34.R;

/**
 * Created by kayo on 15/04/15.
 */
public class TranslateAnimationActivity extends ActionBarActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_animation);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.btn_fragment).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button) {
            Intent intent = new Intent(TranslateAnimationActivity.this, TranslateAnimationActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_fragment) {
            TranslateAnimationDialogFragment f = new TranslateAnimationDialogFragment();
            f.show(getSupportFragmentManager(), "TranslateAnimationDialogFragment");
        }
    }

}
