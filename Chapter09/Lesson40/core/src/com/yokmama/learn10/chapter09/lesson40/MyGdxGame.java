package com.yokmama.learn10.chapter09.lesson40;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    // キャラクターのマスの大きさ指定
    final int SIZE_UNITY_CHAN = 64;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("UnityChan.png");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // テクスチャ
        TextureRegion textureRegion = new TextureRegion(img, 0, SIZE_UNITY_CHAN, SIZE_UNITY_CHAN, SIZE_UNITY_CHAN);
        batch.draw(textureRegion, 0, 0);

        batch.end();
    }
}
