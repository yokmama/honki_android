package com.yokmama.learn10.chapter09.lesson41.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AndroidLauncher extends Activity {

    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mContentView = findViewById(R.id.fullscreen_content);

        final Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidLauncher.this, GameActivity.class);
                intent.putExtra(GameActivity.INTENT_EXTRA_MODE, GameActivity.GAME_MODE_EASY);
                startActivity(intent);
            }
        });

        final Button startHardButton = (Button) findViewById(R.id.startHardButton);
        startHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidLauncher.this, GameActivity.class);
                intent.putExtra(GameActivity.INTENT_EXTRA_MODE, GameActivity.GAME_MODE_HARD);
                startActivity(intent);
            }
        });

        final Button exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int visibility;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                visibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                visibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
            }
            else {
                visibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            mContentView.setSystemUiVisibility(visibility);
        }
    }
}
