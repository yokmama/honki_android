package com.yokmama.learn10.chapter07.lesson34;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kayo on 15/04/15.
 */
public class TranslateAnimationActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_animation);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(TranslateAnimationActivity.this, TranslateAnimationActivity.class);
        startActivity(intent);
    }
}
