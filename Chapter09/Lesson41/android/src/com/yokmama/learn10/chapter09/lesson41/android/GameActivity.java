package com.yokmama.learn10.chapter09.lesson41.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.yokmama.learn10.chapter09.lesson41.MyGdxGame;


public class GameActivity extends AndroidApplication {
    public final static String INTENT_EXTRA_MODE = "com.yokmama.learn10.chapter09.lesson41.android.GAME_MODE";
    public static final int GAME_MODE_EASY = 0;
    public static final int GAME_MODE_HARD = 1;
    private boolean mPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 難易度を取得
        Intent intent = getIntent();
        int mode = intent.getExtras().getInt(INTENT_EXTRA_MODE);

        // ゲーム画面の初期化と、ビュー階層への追加
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        final MyGdxGame game = new MyGdxGame(mode);
        final View gameView = initializeForView(game, config);
        ViewGroup content = (ViewGroup) findViewById(R.id.fullscreen_content);
        content.addView(gameView);

        // 各種ボタン押下時の実装
        final Button pauseButton = (Button) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPaused) {
                    pauseButton.setText(getResources().getText(R.string.button_pause_game));
                    game.resumeGame();
                }
                else {
                    pauseButton.setText(getResources().getText(R.string.button_resume_game));
                    game.pauseGame();
                }
                mPaused = !mPaused;
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
}
