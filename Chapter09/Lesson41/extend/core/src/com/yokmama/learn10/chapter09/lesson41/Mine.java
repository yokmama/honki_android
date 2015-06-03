package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by maciek on 1/28/15.
 * edited by kayo on 6/3/15.
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

    // 作成してからの時間
    private float timeSinceCreation = 0;
    // 表示範囲
    private float visiblePart;

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
        timeSinceCreation += deltaTime;
        if (!hasCollided) {
            // 障害物のアニメーション
            visiblePart = 0.2f + showFunc(timeSinceCreation * 0.2f + phaseShift) * 0.4f;
            collisionCircle.y = origin.y - origin.height / 2 + visiblePart * origin.height;
        }
    }

    private float showFunc(float t) {
        t = t % 1.0f;
        return Math.min(Math.max(t * (-12.5f * t + 12.5f) - 2.0f, 0.0f), 1.0f);
    }

    // 描画
    public void draw(SpriteBatch batch) {
        region.setRegion(0, 0, region.getTexture().getWidth(), (int) (region.getTexture().getHeight() * visiblePart));
        batch.draw(region, origin.x, origin.y, origin.width, origin.height * visiblePart);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(origin.x, origin.y, origin.width, origin.height);
    }

}
