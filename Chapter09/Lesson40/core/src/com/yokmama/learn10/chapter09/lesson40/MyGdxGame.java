package com.yokmama.learn10.chapter09.lesson40;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    // キャラクターのマスの大きさ指定
    final int SIZE_UNITY_CHAN = 64;
    private Animation imgAnimation;
    private float mCurrentDeltaTime;

    // フォント
    private BitmapFont font;

    // カメラ
    final int VIEWPORT_WIDTH = 800;
    final int VIEWPORT_HEIGHT = 480;
    private OrthographicCamera camera;

    // タッチ
    private int touchCount = 0;

    // 音
    Music music;
    Sound coin;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // テクスチャ
        img = new Texture("UnityChan.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int rows = 1; rows <= 2; ++rows) {
            for (int columns = 0; columns < 4; ++columns) {
                TextureRegion region = new TextureRegion(img, columns * SIZE_UNITY_CHAN, rows * SIZE_UNITY_CHAN, SIZE_UNITY_CHAN, SIZE_UNITY_CHAN);
                frames.add(region);
            }
        }
        float frameDuration = 0.05f;
        Animation.PlayMode playMode = Animation.PlayMode.LOOP;
        imgAnimation = new Animation(frameDuration, frames, playMode);

        // フォント
        font = new BitmapFont(Gdx.files.internal("verdana39.fnt"));

        // カメラ
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        // 音
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        coin = Gdx.audio.newSound(Gdx.files.internal("coin05.mp3"));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();
        mCurrentDeltaTime += deltaTime;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // テクスチャ
        TextureRegion keyFrame = imgAnimation.getKeyFrame(mCurrentDeltaTime);
        batch.draw(keyFrame, 0, 0, SIZE_UNITY_CHAN * 2, SIZE_UNITY_CHAN * 2);

        // フォント
        if (Gdx.input.justTouched()) {
            touchCount++;
            coin.play();
        }
        final String text = (touchCount <= 0) ? "Are you Ready?" : "Sure!\ncount=" + touchCount;
        GlyphLayout glyphLayout = new GlyphLayout(font, text, Color.WHITE, 0, Align.center, true);
        font.draw(batch, glyphLayout, VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2 + glyphLayout.height / 2);

        batch.end();
    }
}
