package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by maciek on 1/28/15.
 */
class Chip {

    // スコアアイテム毎の種別
    public static final int TYPE_ONE = 0;
    public static final int TYPE_TWO = 1;
    public static final int TYPE_THREE = 2;
    public static final int TYPE_FOUR = 3;

    // テクスチャの大きさ
    public static final int TEXTURE_COIN_SIZE = 16;

    // 大きさ
    public static final float CHIP_SIZE = 50.0f;

    // スコアアイテム毎の種別
    public int type;

    // テクスチャ
    private final TextureRegion[] chipRegions;
    // スコアアイテム種別毎の得点
    public static final int[] chipScores = new int[] { 10, 20, 50, 100 };
    // スコアアイテムの拡大率
    private static final float[] scoreTextScales = new float[] { 0.6f, 0.65f, 0.7f, 0.8f };

    // 初期位置
    final Rectangle origin = new Rectangle();
    final Vector2 positionPhase = new Vector2();
    final Rectangle bounds = new Rectangle();
    final Circle collisionCircle;
    private final float phaseShiftFraction;
    // アイテム取得済フラグ
    public boolean isCollected = false;
    // アイテム収集後アニメーションも終わり、完全に消失した時trueとなるフラグ
    public boolean isDead = false;

    public Chip(TextureRegion[] chipRegions, int type, float x, float y,
                float width, float height, float phaseShiftFraction) {
        this.chipRegions = chipRegions;
        this.type = type;
        this.origin.set(x, y, width, height);
        this.bounds.set(x, y, width, height);
        this.collisionCircle = new Circle(x + width / 2, y + height / 2, Math.min(width, height) / 2);
        this.phaseShiftFraction = phaseShiftFraction;
    }

    // アイテムを収集
    public void collect() {
        isCollected = true;
    }

    // スコアを取得
    public int getScore() {
        return chipScores[this.type];
    }

    // 状態の更新
    public void update(float deltaTime) {
    }

    // 描画
    public void draw(SpriteBatch batch, Text text) {
        batch.draw(chipRegions[type], origin.x + positionPhase.x, origin.y + positionPhase.y, origin.width, origin.height);
    }

}
