package com.yokmama.learn10.chapter07.lesson34.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.yokmama.learn10.chapter07.lesson34.R;

/**
 * Created by kayo on 15/04/15.
 */
public class TranslateAnimationActivity extends FragmentActivity implements View.OnClickListener {

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
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                Toast.makeText(this, "Android Honeycomb以降のみ実行可能です。", Toast.LENGTH_SHORT).show();
                return;
            }
            TranslateAnimationDialogFragment f = new TranslateAnimationDialogFragment();
            f.show(getSupportFragmentManager(), "TranslateAnimationDialogFragment");
        }
    }

}
