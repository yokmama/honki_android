package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

    // ゲームの状態
    enum GameState {
        Ready, Running, GameOver, GameCleared
    }

    // 現在のゲームの状態
    GameState gameState = GameState.Ready;
    // スコア
    int score;

    SpriteBatch batch;

    // 描画範囲
    int viewportWidth = 800;
    int viewportHeight = 480;

    // カメラ
    OrthographicCamera camera;

    // UI用カメラ
    OrthographicCamera uiCamera;

    // テキスト
    BitmapFont font;
    Text text;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // カメラ
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        // UI用カメラ
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
        uiCamera.update();

        // テキスト
        font = new BitmapFont(Gdx.files.internal("verdana39.fnt"));
        text = new Text(uiCamera);

        resetWorld();
    }

    // ゲームを最初の状態に戻す
    private void resetWorld() {
        score = 0;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 153.0f / 255.0f, 204.0f / 255.0f, 1); // #0099CC
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateWorld();
        drawWorld();
    }

    // 各種状態を変更するメソッド
    private void updateWorld() {

        // 入力制御

        if (Gdx.input.justTouched()) {
            if (gameState == GameState.Ready) {
                gameState = GameState.Running;
            }
            else if (gameState == GameState.GameOver) {
                gameState = GameState.Ready;
                resetWorld();
            }
            else if (gameState == GameState.GameCleared) {
                gameState = GameState.Ready;
                resetWorld();
            }
            else if (gameState == GameState.Running) {
                // FIXME: 試しに状態を変化させる
                gameState = GameState.GameCleared;
//                gameState = GameState.GameOver;
            }
            Gdx.app.log("MyGdxGame", "gameState=" + gameState);
        }
    }

    // 描画メソッド
    private void drawWorld() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // ゲーム描画

        batch.end();
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        // UI描画

        // 文字列描画

        if (gameState == GameState.Ready) {
            text.drawTextTop(batch, font, "START");
        }
        else if (gameState == GameState.GameCleared) {
            text.drawTextTop(batch, font, "SCORE: " + score);
            text.drawTextCenter(batch, font, "LEVEL CLEAR");
        }
        else if (gameState == GameState.GameOver) {
            text.drawTextTop(batch, font, "SCORE: " + score);
            text.drawTextCenter(batch, font, "GAME OVER");
        }
        else if (gameState == GameState.Running) {
            text.drawTextTop(batch, font, "SCORE: " + score);
        }

        batch.end();
    }
}
