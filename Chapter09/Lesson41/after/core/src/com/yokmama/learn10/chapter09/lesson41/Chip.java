package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by maciek on 1/28/15.
 */
class Chip implements Disposable {

    // スコアアイテム毎の添字
    static final int SCORE_ITEM_ONE = 0;
    static final int SCORE_ITEM_TWO = 1;
    static final int SCORE_ITEM_THREE = 2;
    static final int SCORE_ITEM_FOUR = 3;

    static Texture coins;
    static TextureRegion[] chipRegions;
    public static final int[] chipScores = new int[4];
    public static final float chipSize = 50.0f;

    final float[] chipScales;

    final Vector2 position = new Vector2();
    final Vector2 positionPhase = new Vector2();
    final Vector2 size = new Vector2();
    final Rectangle bounds = new Rectangle();
    final Circle collisionCircle;
    boolean isCollected;
    boolean isDead;
    float timeSinceCreation;
    float timeSinceCollected;
    float collectAnimTimeFraction;
    int type;

    float phaseShiftFraction = 0.0f;
    float collectAnimTime = 1.0f;
    float collectAnimHeight = 100.0f;
    private float scorePhase;
    private Color oldColor;

    private static final int COINS_SIZE = 16;

    static {
        chipScores[SCORE_ITEM_ONE] = 10;
        chipScores[SCORE_ITEM_TWO] = 20;
        chipScores[SCORE_ITEM_THREE] = 50;
        chipScores[SCORE_ITEM_FOUR] = 100;
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
        this.isCollected = false;
        this.isDead = false;
        this.chipScales = new float[] { 0.6f, 0.65f, 0.7f, 0.8f };
    }

    public static void load() {
        if (coins == null) {
            coins = new Texture("coins.png");
            TextureRegion[] split = TextureRegion.split(coins, COINS_SIZE, COINS_SIZE)[0];
            chipRegions = new TextureRegion[4];
            chipRegions[SCORE_ITEM_ONE] = split[0];
            chipRegions[SCORE_ITEM_TWO] = split[1];
            chipRegions[SCORE_ITEM_THREE] = split[2];
            chipRegions[SCORE_ITEM_FOUR] = split[3];
        }
    }

    @Override
    public void dispose() {
        coins.dispose();
        chipRegions = null;
    }

    // 状態の更新
    public void update(float deltaTime) {
        timeSinceCreation += deltaTime;
        if (isCollected) {
            // アイテム収集後アニメーション
            timeSinceCollected += deltaTime;
            collectAnimTimeFraction = timeSinceCollected / collectAnimTime;
            positionPhase.y = collectFunc(collectAnimTimeFraction) * collectAnimHeight;
            scorePhase = scoreFunc(collectAnimTimeFraction) * collectAnimHeight;
            if (timeSinceCollected > collectAnimTime) {
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
            oldColor = game.batch.getColor();
            if (isCollected) {
                if (collectAnimTimeFraction < 0.5f) {
                    Color color = new Color(1.0f, 1.0f, 0.0f, 1.0f - 2.0f * collectAnimTimeFraction);
                    GlyphLayout glyphLayout = new GlyphLayout(text.getFont(), "+" + chipScores[type], color, 0, Align.left, false);
                    text.getFont().getData().setScale(chipScales[type]);
                    text.getFont().draw(game.batch, glyphLayout,
                            position.x + chipSize * 0.5f - bounds.width * 0.5f,
                            position.y + chipSize + bounds.height + scorePhase);
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

}
