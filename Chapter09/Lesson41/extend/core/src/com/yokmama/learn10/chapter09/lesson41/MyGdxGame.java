package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by maciek on 1/28/15.
 * edited by kayo on 6/3/15.
 */
public class MyGdxGame extends ApplicationAdapter {

    // 描画範囲
    public static final int VIEWPORT_WIDTH = 800;
    public static final int VIEWPORT_HEIGHT = 480;

    SpriteBatch batch;

    // 現在のゲームの状態
    public GameState gameState = GameState.Ready;

    // スコア
    private int score;

    // カメラ
    private OrthographicCamera uiCamera;
    private OrthographicCamera camera;

    // テクスチャ
    private BitmapFont textFont;
    private Texture heroTexture;
    private Texture backgroundTexture;
    private Texture backgroundFarTexture;
    private Texture backgroundNearTexture;
    private Texture roadTexture;
    private Texture chipsTexture;
    private Texture mineTexture;
    private Texture finishTexture;
    private Music music;
    private Sound collisionSound;
    private Sound coinSound;
    private Sound finaleClapsSound;

    // 各種制御用クラス
    Text text;
    private Hero hero;
    private Background background;
    private Generator generator;

    // カメラ左端の位置
    float cameraLeftEdge;

    // ゴール位置
    float finishX;

    // 難易度
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_HARD = 1;

    // 難易度
    private final int defaultDifficulty;
    private int currentDifficulty;
    private int previousScore;

    // 衝突判定デバッグ用フラグ。trueにするとデバッグ描画が有効となる
    static final boolean DEBUG_DRAW = false;
    private ShapeRenderer shapeRenderer;

    // ポーズ中フラグ
    private boolean pause = false;

    public MyGdxGame(int difficulty) {
        defaultDifficulty = difficulty;
    }

    @Override
    public void create() {
        Gdx.app.log("MyGdxGame", "create()");
        batch = new SpriteBatch();

        // ゲーム用カメラ
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        // UI用カメラ
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        initResources();

        // ゴール地点の決定
        finishX = (background.stageWidth - VIEWPORT_WIDTH) / Background.SPEED + Hero.HERO_LEFT_X;

        // 音楽の再生
        music.setLooping(true);
        music.setVolume(0.6f);
        music.play();

        resetWorld();
    }

    private void initResources() {
        // 各種リソースの読込み
        textFont = new BitmapFont(Gdx.files.internal("verdana39.fnt"));
        heroTexture = new Texture("UnityChan.png");
        finishTexture = new Texture("flag.png");
        backgroundTexture = new Texture("bg.png");
        backgroundFarTexture = new Texture("bg_far.png");
        backgroundNearTexture = new Texture("bg_near.png");
        roadTexture = new Texture("road.png");
        chipsTexture = new Texture("coins.png");
        mineTexture = new Texture("fire.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("laser3.mp3"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("coin05.mp3"));
        finaleClapsSound = Gdx.audio.newSound(Gdx.files.internal("clapping.mp3"));

        // 各種制御用クラス初期化
        text = new Text(textFont);
        hero = new Hero(heroTexture);
        background = new Background(backgroundTexture, backgroundFarTexture, backgroundNearTexture, roadTexture);
        background.setViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        generator = new Generator(chipsTexture, mineTexture);
    }

    @Override
    public void dispose() {
        Gdx.app.log("MyGdxGame", "dispose()");

        textFont.dispose();
        heroTexture.dispose();
        backgroundTexture.dispose();
        backgroundFarTexture.dispose();
        backgroundNearTexture.dispose();
        roadTexture.dispose();
        finishTexture.dispose();
        chipsTexture.dispose();
        mineTexture.dispose();
        music.dispose();
        collisionSound.dispose();
        coinSound.dispose();
        finaleClapsSound.dispose();
    }

    @Override
    public void resize(int width, int height) {
        uiCamera.update();
    }

    @Override
    public void pause() {
        pauseGame();
    }

    // ゲームを最初の状態に戻す
    private void resetWorld() {
        score = 0;

        // キャラクターの位置と状態の初期化
        hero.init();

        currentDifficulty = defaultDifficulty;

        if (defaultDifficulty == DIFFICULTY_EASY) {
            hero.heroVelocityX = 400;
        }
        else if (defaultDifficulty == DIFFICULTY_HARD) {
            hero.heroVelocityX = 600;
        }

        // カメラの位置を開始点へ設定
        camera.position.x = VIEWPORT_WIDTH / 2 - Hero.HERO_LEFT_X;
        cameraLeftEdge = camera.position.x - VIEWPORT_WIDTH / 2;

        generator.init(VIEWPORT_WIDTH);
        generator.clear();
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
        if (pause) return;

        float deltaTime = Gdx.graphics.getDeltaTime();

        // 入力制御
        if (Gdx.input.justTouched()) {
            if (gameState == GameState.Ready) {
                gameState = GameState.Running;

                hero.startRunning();
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
                hero.jump();
            }
            Gdx.app.log("MyGdxGame", "gameState=" + gameState);
        }

        // オブジェクトの新規生成
        if (generator.chipGenerationLine < cameraLeftEdge + VIEWPORT_WIDTH &&
                generator.chipGenerationLine + 5 * 50.0f < finishX) {
            generator.generate(this);
        }

        // オブジェクトの更新
        generator.update(this, deltaTime);

        // キャラクターの状態を更新
        hero.update(deltaTime);

        // カメラの位置をキャラクターに合わせて移動させる
        if (gameState != GameState.GameCleared) {
            camera.position.x = VIEWPORT_WIDTH / 2 + hero.position.x - Hero.HERO_LEFT_X;
            cameraLeftEdge = camera.position.x - VIEWPORT_WIDTH / 2;
        }

        // ゲームクリアチェック
        if (gameState != GameState.GameCleared) {
            float heroX = hero.position.x;
            if (finishX < heroX) {
                finaleClapsSound.play();
                gameState = GameState.GameCleared;
                hero.win(); // クリアしたことを通知
            }
        }

        // ゲームオーバーまたはゲームクリア後は衝突判定を行わない
        if (gameState == GameState.GameOver || gameState == GameState.GameCleared) {
            return;
        }

        // 衝突判定
        Rectangle heroCollision = hero.collisionRect;
        for (Chip chip : generator.chips) {
            if (!chip.isCollected && Intersector.overlaps(chip.collisionCircle, heroCollision)) {
                chip.collect();
                coinSound.play();

                previousScore = score;
                score += chip.getScore();

                // Speedup
                if (previousScore / 500 != score / 500) {
                    hero.heroVelocityX += 100;
                }
            }
        }
        for (Mine mine : generator.mines) {
            if (!mine.hasCollided && Intersector.overlaps(mine.collisionCircle, heroCollision)) {
                mine.collide();
                collisionSound.play();
                hero.die();
                gameState = GameState.GameOver;
            }
        }
    }

    // 描画
    private void drawWorld() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // ゲーム描画

        background.draw(batch, cameraLeftEdge);
        generator.draw(this);
        hero.draw(this);

        batch.draw(finishTexture, finishX, 0,
                finishTexture.getWidth() * 0.35f,
                finishTexture.getHeight() * 0.35f);

        batch.end();

        // デバッグ描画

        if (DEBUG_DRAW) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.circle((background.stageWidth - VIEWPORT_WIDTH) / Background.SPEED + Hero.HERO_LEFT_X, Hero.HERO_FLOOR_Y, 5);

            generator.drawDebug(shapeRenderer);

            hero.drawDebug(shapeRenderer);

            shapeRenderer.end();
        }

        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        // UI描画

        // 文字列描画
        if (gameState == GameState.Ready) {
            text.drawTextTop(batch, "START");
        }
        else if (gameState == GameState.GameCleared) {
            text.drawTextTop(batch, "SCORE: " + score);
            text.drawTextCenter(batch, "LEVEL CLEAR");
        }
        else if (gameState == GameState.GameOver) {
            text.drawTextTop(batch, "SCORE: " + score);
            text.drawTextCenter(batch, "GAME OVER");
        }
        else if (gameState == GameState.Running) {
            text.drawTextTop(batch, "SCORE: " + score);
        }

        batch.end();
    }

    public void resumeGame() {
        pause = false;
    }

    public void pauseGame() {
        pause = true;
    }
}
