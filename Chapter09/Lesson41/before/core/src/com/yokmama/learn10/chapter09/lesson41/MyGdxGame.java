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

    @Override
    public void create() {
        Gdx.app.log("MyGdxGame", "create()");
        batch = new SpriteBatch();

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
    public void render() {
        Gdx.gl.glClearColor(0, 153.0f / 255.0f, 204.0f / 255.0f, 1); // #0099CC
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

}
