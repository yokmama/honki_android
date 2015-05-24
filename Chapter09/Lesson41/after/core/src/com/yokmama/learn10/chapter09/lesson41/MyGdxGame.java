package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class MyGdxGame extends ApplicationAdapter {

    // 描画範囲
    public static final int VIEWPORT_WIDTH = 800;
    public static final int VIEWPORT_HEIGHT = 480;

    SpriteBatch batch;

    // 現在のゲームの状態
    public GameState gameState = GameState.Ready;

    // スコア
    private int mScore;

    // カメラ
    private OrthographicCamera mUiCamera;
    private OrthographicCamera mCamera;
    float cameraLeftEdge;

    // テクスチャ
    private BitmapFont mTextFont;
    private Texture mHeroTexture;
    private Texture mBackgroundTexture;
    private Texture mBackgroundFarTexture;
    private Texture mBackgroundNearTexture;
    private Texture mRoadTexture;
    private Texture mChipsTexture;
    private Texture mMineTexture;
    private Texture mFinishTexture;
    private Music mMusic;
    private Sound mCollisionSound;
    private Sound mCoinSound;
    private Sound mFinaleClapsSound;

    // 各種制御用クラス
    Text text;
    private Hero mHero;
    private Background mBackground;
    private Generator mGenerator;

    // ゴール位置
    float finishX;

    @Override
    public void create() {
        Gdx.app.log("MyGdxGame", "create()");
        batch = new SpriteBatch();

        initResources();

        // ゲーム用カメラ
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        mCamera.position.set(mCamera.viewportWidth / 2, mCamera.viewportHeight / 2, 0);

        // UI用カメラ
        mUiCamera = new OrthographicCamera();
        mUiCamera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        // ゴール地点の決定
        finishX = (mBackground.stageWidth - VIEWPORT_WIDTH) / Background.SPEED + Hero.HERO_LEFT_X;

        // 音楽の再生
        mMusic.setLooping(true);
        mMusic.setVolume(0.5f);
        mMusic.play();

        resetWorld();
    }

    private void initResources() {
        // 各種リソースの読込み
        mTextFont = new BitmapFont(Gdx.files.internal("verdana39.fnt"));
        mHeroTexture = new Texture("UnityChan.png");
        mFinishTexture = new Texture("flag.png");
        mBackgroundTexture = new Texture("bg.png");
        mBackgroundFarTexture = new Texture("bg_far.png");
        mBackgroundNearTexture = new Texture("bg_near.png");
        mRoadTexture = new Texture("road.png");
        mChipsTexture = new Texture("coins.png");
        mMineTexture = new Texture("fire.png");
        mMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        mCollisionSound = Gdx.audio.newSound(Gdx.files.internal("laser3.mp3"));
        mCoinSound = Gdx.audio.newSound(Gdx.files.internal("coin05.mp3"));
        mFinaleClapsSound = Gdx.audio.newSound(Gdx.files.internal("clapping.mp3"));

        // 各種制御用クラス初期化
        text = new Text(mTextFont);
        mHero = new Hero(mHeroTexture);
        mBackground = new Background(mBackgroundTexture, mBackgroundFarTexture, mBackgroundNearTexture, mRoadTexture);
        mBackground.setViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        mGenerator = new Generator(mChipsTexture, mMineTexture);
    }

    @Override
    public void resize(int width, int height) {
        mUiCamera.update();
        text.setViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    @Override
    public void dispose() {
        Gdx.app.log("MyGdxGame", "dispose()");

        mTextFont.dispose();
        mHeroTexture.dispose();
        mBackgroundTexture.dispose();
        mBackgroundFarTexture.dispose();
        mBackgroundNearTexture.dispose();
        mRoadTexture.dispose();
        mFinishTexture.dispose();
        mChipsTexture.dispose();
        mMineTexture.dispose();
        mMusic.dispose();
        mCollisionSound.dispose();
        mCoinSound.dispose();
        mFinaleClapsSound.dispose();
    }

    // ゲームを最初の状態に戻す
    private void resetWorld() {
        mScore = 0;

        // キャラクターの位置と状態の初期化
        mHero.init();

        // カメラの位置を開始点へ設定
        mCamera.position.x = VIEWPORT_WIDTH / 2 - Hero.HERO_LEFT_X;
        cameraLeftEdge = mCamera.position.x - VIEWPORT_WIDTH / 2;

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

    // 各種状態を変更する
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
            mCamera.position.x = VIEWPORT_WIDTH / 2 + mHero.getPosition().x - Hero.HERO_LEFT_X;
            cameraLeftEdge = mCamera.position.x - VIEWPORT_WIDTH / 2;
        }

        // ゲームクリアチェック
        if (gameState != GameState.GameCleared) {
            float heroX = mHero.getPosition().x;
            if (finishX < heroX) {
                mFinaleClapsSound.play();
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
                mCoinSound.play();

                mScore += chip.getScore();
            }
        }
        for (Mine mine : mGenerator.mines) {
            if (!mine.hasCollided && Intersector.overlaps(mine.collisionCircle, heroCollision)) {
                mine.collide();
                mCollisionSound.play();
                mHero.die();
                gameState = GameState.GameOver;
            }
        }
    }

    // 描画
    private void drawWorld() {
        mCamera.update();
        batch.setProjectionMatrix(mCamera.combined);
        batch.begin();

        // ゲーム描画

        mBackground.draw(batch, cameraLeftEdge);
        mGenerator.draw(this);
        mHero.draw(this);

        batch.draw(mFinishTexture, finishX, 0,
                mFinishTexture.getWidth() * 0.35f,
                mFinishTexture.getHeight() * 0.35f);

        batch.end();
        batch.setProjectionMatrix(mUiCamera.combined);
        batch.begin();

        // UI描画

        // 文字列描画
        if (gameState == GameState.Ready) {
            text.drawTextTop(batch, "START", mUiCamera);
        }
        else if (gameState == GameState.GameCleared) {
            text.drawTextTop(batch, "SCORE: " + mScore, mUiCamera);
            text.drawTextCenter(batch, "LEVEL CLEAR", mUiCamera);
        }
        else if (gameState == GameState.GameOver) {
            text.drawTextTop(batch, "SCORE: " + mScore, mUiCamera);
            text.drawTextCenter(batch, "GAME OVER", mUiCamera);
        }
        else if (gameState == GameState.Running) {
            text.drawTextTop(batch, "SCORE: " + mScore, mUiCamera);
        }

        batch.end();
    }
}
