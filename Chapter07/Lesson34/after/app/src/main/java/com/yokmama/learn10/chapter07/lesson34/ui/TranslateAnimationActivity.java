package com.yokmama.learn10.chapter07.lesson34.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yokmama.learn10.chapter07.lesson34.R;

/**
 * Created by kayo on 15/04/15.
 */
public class TranslateAnimationActivity extends Activity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_animation);
        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,
                TranslateAnimationActivity.class);
        startActivity(intent);
    }

}
