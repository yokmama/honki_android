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
    private final float centerX;
    private final float centerY;
    private final BitmapFont font;

    public Text(BitmapFont font) {
        this.font = font;
        glyphLayout = new GlyphLayout();
        centerX = Gdx.graphics.getWidth() * 0.5f;
        centerY = Gdx.graphics.getHeight() * 0.5f;
    }

    // 上部にテキストを描画
    public void drawTextTop(Batch batch, String text, OrthographicCamera camera) {
        glyphLayout.setText(font, text, Color.WHITE, 0, Align.center, false);
        float textY = font.getLineHeight();
        Vector3 v = camera.unproject(new Vector3(centerX, textY, 0));
        font.draw(batch, glyphLayout, Math.round(v.x), v.y);
    }

    // 中央にテキストを描画
    public void drawTextCenter(Batch batch, String text, OrthographicCamera camera) {
        glyphLayout.setText(font, text, Color.WHITE, 0, Align.center, false);
        float textY = centerY - glyphLayout.height * 0.5f;
        Vector3 v = camera.unproject(new Vector3(centerX, textY, 0));
        font.draw(batch, glyphLayout, v.x, v.y);
    }

    public BitmapFont getFont() {
        return font;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
