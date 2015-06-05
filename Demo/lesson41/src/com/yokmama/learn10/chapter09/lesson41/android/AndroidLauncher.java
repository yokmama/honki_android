package com.yokmama.learn10.chapter09.lesson41.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AndroidLauncher extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson41);

        findViewById(R.id.startButton).setOnClickListener(this);
        findViewById(R.id.startHardButton).setOnClickListener(this);
        findViewById(R.id.exitButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.startButton) {
            Intent intent = new Intent(AndroidLauncher.this, GameActivity.class);
            intent.putExtra(GameActivity.INTENT_EXTRA_MODE, GameActivity.GAME_MODE_EASY);
            startActivity(intent);
        } else if (id == R.id.startHardButton) {
            Intent intent = new Intent(AndroidLauncher.this, GameActivity.class);
            intent.putExtra(GameActivity.INTENT_EXTRA_MODE, GameActivity.GAME_MODE_HARD);
            startActivity(intent);
        } else if (id == R.id.exitButton) {
            finish();
        }
    }
}
