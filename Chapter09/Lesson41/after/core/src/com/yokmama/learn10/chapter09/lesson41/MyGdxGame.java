package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {

    // 現在のゲームの状態
    public GameState gameState = GameState.Ready;
    // スコア
    private int score;

    public SpriteBatch batch;

    // 描画範囲
    public final int viewportWidth = 800;
    public final int viewportHeight = 480;

    // カメラ
    OrthographicCamera camera;
    float cameraLeftEdge;

    // UI用カメラ
    OrthographicCamera uiCamera;

    // テキスト
    Text mText;

    // キャラクターの制御オブジェクト
    Hero mHero;
    // キャラクターの速度
    float heroVelocityX = 400;

    // 背景
    Texture backgroundClear;
    float bgWidth;
    float bgSpeed;

    // ゴール
    Texture finish;
    float finishX;

    // Generator
    private Generator generator;

    // スコアアイテム

    // 障害物

    // サウンド
    private SoundManager mSound;

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
        mText = new Text();

        // キャラクター
        mHero = new Hero();

        // 背景
        backgroundClear = new Texture("bg.png");
        bgWidth = viewportHeight * (backgroundClear.getWidth() / backgroundClear.getHeight());
        bgSpeed = 0.2f;

        // ゴール
        finish = new Texture("flag.png");
        finishX = (bgWidth - viewportWidth) / bgSpeed + Hero.HERO_LEFT_X;

        generator = new Generator();

        mSound = new SoundManager();
        mSound.music.play();

        resetWorld();
    }

    // ゲームを最初の状態に戻す
    private void resetWorld() {
        score = 0;

        // キャラクターの位置と状態の初期化
        mHero.getPosition().set(Hero.HERO_LEFT_X, Hero.HERO_FLOOR_Y);
        mHero.getVelocity().set(0, 0);
        mHero.init();

        // カメラの位置を開始点へ設定
        camera.position.x = viewportWidth / 2 - Hero.HERO_LEFT_X;
        cameraLeftEdge = camera.position.x - viewportWidth / 2;

        generator.init(viewportWidth);
        generator.clear();
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
                mHero.getVelocity().set(heroVelocityX, 0);
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

        if (generator.chipGenerationLine < cameraLeftEdge + viewportWidth &&
                generator.chipGenerationLine + 5 * 50.0f < finishX) {
            generator.generate(this);
        }

        // オブジェクトの更新

        generator.update(this, deltaTime);

        // キャラクターの状態を更新

        mHero.update(deltaTime);

        // カメラの位置をキャラクターに合わせて移動させる

        if (gameState != GameState.GameCleared) {
            camera.position.x = viewportWidth / 2 + mHero.getPosition().x - Hero.HERO_LEFT_X;
            cameraLeftEdge = camera.position.x - viewportWidth / 2;
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

        for (Chip chip : generator.chips) {
            if (!chip.isCollected && Intersector.overlaps(chip.collisionCircle, heroCollision)) {
                chip.collect();
                mSound.coin.play();

                score += Chip.chipScores[chip.type];
            }
        }

        for (Mine mine : generator.mines) {
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
        batch.draw(backgroundClear, drawOffset, 0, bgWidth, viewportHeight);

        generator.draw(this);

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
            mText.drawTextTop(batch, "START", uiCamera);
        }
        else if (gameState == GameState.GameCleared) {
            mText.drawTextTop(batch, "SCORE: " + score, uiCamera);
            mText.drawTextCenter(batch, "LEVEL CLEAR", uiCamera);
        }
        else if (gameState == GameState.GameOver) {
            mText.drawTextTop(batch, "SCORE: " + score, uiCamera);
            mText.drawTextCenter(batch, "GAME OVER", uiCamera);
        }
        else if (gameState == GameState.Running) {
            mText.drawTextTop(batch, "SCORE: " + score, uiCamera);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        mHero.dispose();
        mSound.dispose();
    }
}
