package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

/**
 * Created by maciek on 1/28/15.
 */
public class Chip {

    // スコアアイテム毎の種別
    public static final int TYPE_ONE = 0;
    public static final int TYPE_TWO = 1;
    public static final int TYPE_THREE = 2;
    public static final int TYPE_FOUR = 3;

    // テクスチャの大きさ
    private static final int TEXTURE_COIN_SIZE = 16;

    // 取得時にアニメーションする時間
    private static final float COLLECT_ANIM_TIME = 1.0f;
    private static final float COLLECT_ANIM_HEIGHT = 100.0f;

    // 大きさ
    public static final float CHIP_SIZE = 50.0f;

    // スコアアイテム毎の種別
    public int type;

    //
    static Texture chipsTexture;
    static TextureRegion[] chipRegions;
    public static final int[] chipScores;
    private static final float[] scoreTextScales;

    final Vector2 position = new Vector2();
    final Vector2 positionPhase = new Vector2();
    final Vector2 size = new Vector2();
    final Rectangle bounds = new Rectangle();
    final Circle collisionCircle;
    public boolean isCollected = false;
    public boolean isDead = false;

    // アニメーション変数
    private float timeSinceCreation;
    private float timeSinceCollected;
    private float collectAnimTimeFraction;
    private final float phaseShiftFraction;
    private float scorePhase;

    static {
        chipScores = new int[] { 10, 20, 50, 100 };
        scoreTextScales = new float[] { 0.6f, 0.65f, 0.7f, 0.8f };
    }

    public Chip(int type, float x, float y, float width, float height) {
        this(type, x, y, width, height, 0);
    }

    public Chip(int type, float x, float y, float width, float height, float phaseShiftFraction) {
        this.position.x = x;
        this.position.y = y;
        this.size.x = width;
        this.size.y = height;
        this.bounds.set(x, y, width, height);
        this.type = type;
        this.timeSinceCreation = 0;
        this.phaseShiftFraction = phaseShiftFraction;
        this.collisionCircle = new Circle(x + width / 2, y + height / 2, Math.min(width, height) / 2);
    }

    public static void loadTexture() {
        if (chipsTexture == null) {
            chipsTexture = new Texture("coins.png");
            TextureRegion[] split = TextureRegion.split(chipsTexture, TEXTURE_COIN_SIZE, TEXTURE_COIN_SIZE)[0];
            chipRegions = new TextureRegion[4];
            chipRegions[TYPE_ONE] = split[0];
            chipRegions[TYPE_TWO] = split[1];
            chipRegions[TYPE_THREE] = split[2];
            chipRegions[TYPE_FOUR] = split[3];
        }
    }

    public static void disposeTexture() {
        if (chipsTexture != null) {
            chipsTexture.dispose();
            chipRegions = null;
        }
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
                isDead = true;
            }
        }
        else {
            // アイテム収集前アニメーション
            double af = 2 * Math.PI / 1.4f; // 角周波数
            double phaseShift = 2 * Math.PI * phaseShiftFraction;
            positionPhase.y = (float) Math.sin(timeSinceCreation * af - phaseShift) * size.y / 2.0f;
            collisionCircle.y = position.y + positionPhase.y + size.y / 2;
        }
        bounds.set(position.x, position.y + positionPhase.y, size.x, size.y);
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
    public void draw(MyGdxGame game, Text text) {
        if (!isDead) {
            Color oldColor = game.batch.getColor();
            if (isCollected) {
                if (collectAnimTimeFraction < 0.5f) {
                    Color color = new Color(1.0f, 1.0f, 0.0f, 1.0f - 2.0f * collectAnimTimeFraction);
                    GlyphLayout glyphLayout = new GlyphLayout(text.getFont(), "+" + chipScores[type], color, 0, Align.left, false);
                    text.getFont().getData().setScale(scoreTextScales[type]);
                    text.getFont().draw(game.batch, glyphLayout,
                            position.x + CHIP_SIZE * 0.5f - bounds.width * 0.5f,
                            position.y + CHIP_SIZE + bounds.height + scorePhase);
                    text.getFont().getData().setScale(1.0f);
                }

                game.batch.setColor(Color.alpha(1.0f - collectAnimTimeFraction));
            }
            game.batch.draw(Chip.chipRegions[type], position.x + positionPhase.x, position.y + positionPhase.y, size.x, size.y);
            if (isCollected) {
                game.batch.setColor(oldColor);
            }
        }
    }

    // アイテムを収集
    public void collect() {
        isCollected = true;
        timeSinceCollected = 0.0f;
        scorePhase = 0.0f;
    }

    // スコアを取得
    public int getScore() {
        return chipScores[this.type];
    }
}
