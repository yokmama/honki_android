package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

/**
 * テキスト
 */
public class Text implements Disposable {

    private final GlyphLayout glyphLayout;
    private float centerX = MyGdxGame.VIEWPORT_WIDTH;
    private float centerY = MyGdxGame.VIEWPORT_HEIGHT;
    private final BitmapFont font;

    public Text(BitmapFont font) {
        this.font = font;
        glyphLayout = new GlyphLayout();
    }

    public void setViewport(float width, float height) {
        centerX = width * 0.5f;
        centerY = height * 0.5f;
    }

    // 上部にテキストを描画
    public void drawTextTop(Batch batch, String text, OrthographicCamera camera) {
        glyphLayout.setText(font, text, Color.WHITE, 0, Align.center, false);
        font.draw(batch, glyphLayout, centerX, centerY);
    }

    // 中央にテキストを描画
    public void drawTextCenter(Batch batch, String text, OrthographicCamera camera) {
        glyphLayout.setText(font, text, Color.WHITE, 0, Align.center, false);
        float textY = centerY - glyphLayout.height * 0.5f;
        Vector3 v = camera.unproject(new Vector3(centerX, textY, 0));
        font.draw(batch, glyphLayout, v.x, v.y);
    }

    // スコアアイテム取得時のテキスト描画
    public void drawChipScore(Batch batch, String text, Color color, float scaleXY, float x, float y) {
        glyphLayout.setText(font, text, color, 0, Align.left, false);
        font.getData().setScale(scaleXY);
        font.draw(batch, glyphLayout, x, y);
        font.getData().setScale(1.0f);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
