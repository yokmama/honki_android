package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by maciek on 1/28/15.
 * edited by kayo on 6/3/15.
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

    // スコア取得時のテキストの色
    private final Color chipScoreColor = new Color();

    // 取得時にアニメーションする時間
    private static final float COLLECT_ANIM_TIME = 1.0f;
    private static final float COLLECT_ANIM_HEIGHT = 100.0f;

    // アニメーション変数
    private float timeSinceCollected = 0;
    private float timeSinceCreation = 0;
    private float collectAnimTimeFraction;
    private float scorePhase = 0;

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
        timeSinceCreation += deltaTime;
        if (isCollected) {
            // アイテム収集後アニメーション
            timeSinceCollected += deltaTime;
            collectAnimTimeFraction = timeSinceCollected / COLLECT_ANIM_TIME;
            positionPhase.y = collectFunc(collectAnimTimeFraction) * COLLECT_ANIM_HEIGHT;
            scorePhase = scoreFunc(collectAnimTimeFraction) * COLLECT_ANIM_HEIGHT;
            if (timeSinceCollected > COLLECT_ANIM_TIME) {
                // アニメーション終了後、フラグを立てる
                isDead = true;
            }
        }
        else {
            // アイテム収集前アニメーション
            double af = 2 * Math.PI / 1.4f; // 角周波数
            double phaseShift = 2 * Math.PI * phaseShiftFraction;
            positionPhase.y = (float) Math.sin(timeSinceCreation * af - phaseShift) * origin.height / 2.0f;
            collisionCircle.y = origin.y + positionPhase.y + origin.height / 2;
        }
        bounds.set(origin.x, origin.y + positionPhase.y, origin.width, origin.height);
    }

    // スコア表示アニメーション関数
    private float scoreFunc(float t) {
        t = 0.5f * Math.min(Math.max(t, 0.0f), 1.0f);
        return 2.0f * t * (-4.0f * t + 4.0f);
    }

    // アイテム収集後アニメーション関数
    private float collectFunc(float t) {
        t = Math.min(Math.max(t, 0.0f), 1.0f);
        return t * (-4.0f * t + 4.0f);
    }

    // 描画
    public void draw(SpriteBatch batch, Text text) {
        // 既にアニメーション終了している場合は描画しない
        if (isDead) {
            return;
        }
        Color oldColor = batch.getColor();
        if (isCollected) {
            // スコアテキストの描画
            if (collectAnimTimeFraction < 0.5f) {
                chipScoreColor.set(1.0f, 1.0f, 0.0f, 1.0f - 2.0f * collectAnimTimeFraction);
                text.drawChipScore(batch, "+" + chipScores[type], chipScoreColor,
                        scoreTextScales[type],
                        origin.x + CHIP_SIZE * 0.5f - bounds.width * 0.5f,
                        origin.y + CHIP_SIZE + bounds.height + scorePhase);
            }

            batch.setColor(Color.alpha(1.0f - collectAnimTimeFraction));
        }
        // 取得後の描画
        batch.draw(chipRegions[type], origin.x + positionPhase.x, origin.y + positionPhase.y, origin.width, origin.height);
        if (isCollected) {
            batch.setColor(oldColor);
        }
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        if (!isCollected) {
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
        }
    }

}
