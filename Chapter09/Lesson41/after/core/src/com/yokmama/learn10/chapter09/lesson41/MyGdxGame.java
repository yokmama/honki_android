package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class MyGdxGame extends ApplicationAdapter {

    // 描画範囲
    public final int VIEWPORT_WIDTH = 800;
    public final int VIEWPORT_HEIGHT = 480;

    // 現在のゲームの状態
    public GameState gameState = GameState.Ready;

    // スコア
    private int score;

    public SpriteBatch batch;

    // カメラ
    OrthographicCamera uiCamera;
    OrthographicCamera camera;
    float cameraLeftEdge;

    // テキスト
    Text text;

    // キャラクターの制御オブジェクト
    Hero mHero;

    // 背景
    Texture backgroundClear;
    float bgWidth;
    float bgSpeed;

    // ゴール
    Texture finish;
    float finishX;

    // Generator
    private Generator mGenerator;

    // サウンド
    private SoundManager mSound;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // ゲーム用カメラ
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        // UI用カメラ
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        // テキスト
        text = new Text();

        // キャラクター
        mHero = new Hero();

        // 背景
        backgroundClear = new Texture("bg.png");
        bgWidth = VIEWPORT_HEIGHT * (backgroundClear.getWidth() / backgroundClear.getHeight());
        bgSpeed = 0.2f;

        // ゴール
        finish = new Texture("flag.png");
        finishX = (bgWidth - VIEWPORT_WIDTH) / bgSpeed + Hero.HERO_LEFT_X;

        mGenerator = new Generator();

        mSound = new SoundManager();
        mSound.music.play();

        resetWorld();
    }

    @Override
    public void resize(int width, int height) {
        uiCamera.update();
    }

    @Override
    public void dispose() {
        Hero.disposeTexture();
        mSound.dispose();
    }

    // ゲームを最初の状態に戻す
    private void resetWorld() {
        score = 0;

        // キャラクターの位置と状態の初期化
        mHero.init();

        // カメラの位置を開始点へ設定
        camera.position.x = VIEWPORT_WIDTH / 2 - Hero.HERO_LEFT_X;
        cameraLeftEdge = camera.position.x - VIEWPORT_WIDTH / 2;

        mGenerator.init(VIEWPORT_WIDTH);
        mGenerator.clear();
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

        float deltaTime = Gdx.graphics.getDeltaTime();

        // 入力制御

        if (Gdx.input.justTouched()) {
            if (gameState == GameState.Ready) {
                gameState = GameState.Running;

                mHero.startRunning();
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
                mHero.jump();
            }
            Gdx.app.log("MyGdxGame", "gameState=" + gameState);
        }

        // オブジェクトの新規生成

        if (mGenerator.chipGenerationLine < cameraLeftEdge + VIEWPORT_WIDTH &&
                mGenerator.chipGenerationLine + 5 * 50.0f < finishX) {
            mGenerator.generate(this);
        }

        // オブジェクトの更新

        mGenerator.update(this, deltaTime);

        // キャラクターの状態を更新

        mHero.update(deltaTime);

        // カメラの位置をキャラクターに合わせて移動させる

        if (gameState != GameState.GameCleared) {
            camera.position.x = VIEWPORT_WIDTH / 2 + mHero.getPosition().x - Hero.HERO_LEFT_X;
            cameraLeftEdge = camera.position.x - VIEWPORT_WIDTH / 2;
        }

        // ゲームクリアチェック

        if (gameState != GameState.GameCleared) {
            float heroX = mHero.getPosition().x;
            if (finishX < heroX) {
                mSound.finaleClaps.play();
                gameState = GameState.GameCleared;
                mHero.win(); // クリアしたことを通知
            }
        }

        // ゲームオーバーまたはゲームクリア後は衝突判定を行わない

        if (gameState == GameState.GameOver || gameState == GameState.GameCleared) {
            return;
        }

        // 衝突判定

        Rectangle heroCollision = mHero.getCollisionRect();

        for (Chip chip : mGenerator.chips) {
            if (!chip.isCollected && Intersector.overlaps(chip.collisionCircle, heroCollision)) {
                chip.collect();
                mSound.coin.play();

                score += chip.getScore();
            }
        }

        for (Mine mine : mGenerator.mines) {
            if (!mine.hasCollided && Intersector.overlaps(mine.collisionCircle, heroCollision)) {
                mine.collide();
                mSound.collision.play();
                mHero.die();
                gameState = GameState.GameOver;
            }
        }
    }

    // 描画メソッド
    private void drawWorld() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // ゲーム描画

        float drawOffset = cameraLeftEdge - cameraLeftEdge * bgSpeed;
        batch.draw(backgroundClear, drawOffset, 0, bgWidth, VIEWPORT_HEIGHT);

        mGenerator.draw(this);
        mHero.draw(this);

        batch.draw(finish, finishX, 0,
                finish.getWidth() * 0.35f,
                finish.getHeight() * 0.35f);

        batch.end();
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        // UI描画

        // 文字列描画

        if (gameState == GameState.Ready) {
            text.drawTextTop(batch, "START", uiCamera);
        }
        else if (gameState == GameState.GameCleared) {
            text.drawTextTop(batch, "SCORE: " + score, uiCamera);
            text.drawTextCenter(batch, "LEVEL CLEAR", uiCamera);
        }
        else if (gameState == GameState.GameOver) {
            text.drawTextTop(batch, "SCORE: " + score, uiCamera);
            text.drawTextCenter(batch, "GAME OVER", uiCamera);
        }
        else if (gameState == GameState.Running) {
            text.drawTextTop(batch, "SCORE: " + score, uiCamera);
        }

        batch.end();
    }
}
