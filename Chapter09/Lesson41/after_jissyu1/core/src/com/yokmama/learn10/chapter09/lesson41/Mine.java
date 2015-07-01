package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by maciek on 1/28/15.
 */
class Mine {
    // テクスチャの大きさを決定
    public static final float TEXTURE_SIZE = 50.0f;

    // リージョン
    private final TextureRegion region;
    final Rectangle origin = new Rectangle();
    // 衝突範囲
    final Circle collisionCircle;
    // アニメーション開始点
    private final float phaseShift;
    // 障害物したかどうか
    boolean hasCollided = false;

    public Mine(TextureRegion region, float x, float y, float width, float height, float phaseShift) {
        this.region = region;
        this.origin.set(x, y, width, height);
        this.collisionCircle = new Circle(x + width / 2, 0, Math.min(width, height) / 2);
        this.phaseShift = phaseShift;
    }

    // 衝突通知
    public void collide() {
        hasCollided = true;
    }

    // 更新
    public void update(float deltaTime) {
    }

    // 描画
    public void draw(SpriteBatch batch) {
        batch.draw(region, origin.x, origin.y, origin.width, origin.height);
    }

}
