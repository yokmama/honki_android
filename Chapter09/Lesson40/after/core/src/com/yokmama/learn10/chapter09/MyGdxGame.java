package com.yokmama.learn10.chapter09;

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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
    final int SIZE_CHR = 64; // キャラクターのマスの大きさ
    final int VIEWPORT_WIDTH = 800; // 画面横幅
    final int VIEWPORT_HEIGHT = 480; // 画面縦幅

    SpriteBatch batch;
    Texture img; // テクスチャ
    Animation anim; // アニメーション
    BitmapFont font; // フォント
    GlyphLayout glyph; // 文字位置を決めるクラス
    OrthographicCamera camera; // カメラ
    Music music; // 音楽
    Sound coin; // 音

    float mCurrentDeltaTime = 0; // 経過時間
    private int touchCount = 0; // タッチした回数

    @Override
    public void create() {
        batch = new SpriteBatch();

        // テクスチャ
        img = new Texture("UnityChan.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int rows = 1; rows <= 2; ++rows) {
            for (int columns = 0; columns < 4; ++columns) {
                TextureRegion region = new TextureRegion(img,
                        columns * SIZE_CHR, rows * SIZE_CHR,
                        SIZE_CHR, SIZE_CHR);
                frames.add(region);
            }
        }
        anim = new Animation(0.05f, frames, Animation.PlayMode.LOOP);

        // フォント
        font = new BitmapFont(Gdx.files.internal("verdana39.fnt"));
        glyph = new GlyphLayout();

        // カメラ
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);

        // 音
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
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
        TextureRegion keyFrame = anim.getKeyFrame(mCurrentDeltaTime);
        batch.draw(keyFrame, 0, 0, SIZE_CHR * 2, SIZE_CHR * 2);

        // フォント
        if (Gdx.input.justTouched()) {
            touchCount++;
            coin.play();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            Vector3 coords = camera.unproject(new Vector3(x, y, 0));
            Gdx.app.log("MyGdxGame", String.format(
                    "(x,y)=(%d,%d), coords(x,y)=(%f,%f)",
                    x, y, coords.x, coords.y));
        }
        final String text = (touchCount <= 0) ? "Are you Ready?"
                : "Of course.\ncount=" + touchCount;
        glyph.setText(font, text, Color.WHITE, 0, Align.center, true);
        font.draw(batch, glyph, VIEWPORT_WIDTH * 0.5f,
                VIEWPORT_HEIGHT * 0.5f + glyph.height * 0.5f);

        batch.end();
    }
}
