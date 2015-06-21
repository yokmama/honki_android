package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * キャラクターの制御
 */
class Hero {

    // テクスチャタイルの横幅
    private static final int TEXTURE_TILE_WIDTH = 64;
    private static final int TEXTURE_TILE_HEIGHT = 64;

    // ジャンプ時間
    private static final float TIME_DURATION_RUNNING = 0.05f;
    private static final float TIME_DURATION_JUMPING = 0.05f;
    private static final float TIME_DURATION_WIN = 0.2f;

    // アニメーションの状態(または添字)
    private final static int ANIM_STATE_STILL = -1;
    private final static int ANIM_STATE_RUNNING = 0;
    private final static int ANIM_STATE_JUMPING = 1;
    private final static int ANIM_STATE_WIN = 2;

    // 地面からの距離
    public static final float HERO_FLOOR_Y = 40;
    // 画面左からの距離
    public static final float HERO_LEFT_X = 150;

    // テクスチャ
    private final TextureRegion deadFrame;
    private final Animation[] animations = new Animation[3];
    private TextureRegion animStillFrame;

    // アニメーションの状態
    private int animState;
    // 現在のアニメーションの状態を続けた時間
    private float currentStateDisplayTime;
    // キャラクターの移動速度
    float heroVelocityX = 400;

    // キャラクタの位置
    final Vector2 position = new Vector2();
    // キャラクタの速度
    final Vector2 velocity = new Vector2();
    // キャラクタの衝突判定用範囲オブジェクト
    final Rectangle collisionRect = new Rectangle();

    // ゲームオーバー
    private static final float DEAD_JUMP_HEIGHT = 100.0f;
    private boolean isDead;
    private float deadPosY;
    private boolean hasDeathAnimEnded;

    // ゲームクリア
    private final static int WIN_ANIM_STATE_WAIT_FOR_LANDING = 0;
    private final static int WIN_ANIM_STATE_RUN = 1;
    private final static int WIN_ANIM_STATE_SPIRAL = 2;
    private final static int WIN_ANIM_STATE_STATIC = 3;
    private boolean hasWon;
    private int winAnimState;

    // ジャンプ
    private static final float JUMP_TIME = 1.0f;
    private static final float JUMP_HEIGHT = 300.0f;
    private boolean isJumping;
    private boolean isDoubleJumping;
    private float jumpingTime;

    public Hero(Texture heroTexture) {
        // テクスチャから、状態毎に TextureRegion を取得する
        Array<TextureRegion> regions;
        TextureRegion[][] split = TextureRegion.split(heroTexture,
                TEXTURE_TILE_WIDTH, TEXTURE_TILE_HEIGHT);

        regions = new Array<TextureRegion>();
        regions.addAll(split[1], 0, 4);
        regions.addAll(split[2], 0, 4);
        animations[ANIM_STATE_RUNNING] = new Animation(TIME_DURATION_RUNNING,
                regions, Animation.PlayMode.LOOP);

        regions = new Array<TextureRegion>();
        regions.addAll(split[3], 0, 7);
        animations[ANIM_STATE_JUMPING] = new Animation(TIME_DURATION_JUMPING,
                regions, Animation.PlayMode.NORMAL);

        regions = new Array<TextureRegion>();
        regions.addAll(split[0], 3, 2);
        animations[ANIM_STATE_WIN] = new Animation(TIME_DURATION_WIN,
                regions, Animation.PlayMode.LOOP);

        // ゲームオーバー時の表示
        deadFrame = split[0][3];

        init();
    }

    public void init() {
        animState = ANIM_STATE_STILL;
        animStillFrame = animations[ANIM_STATE_RUNNING].getKeyFrame(0);
        position.set(Hero.HERO_LEFT_X, Hero.HERO_FLOOR_Y);
        velocity.set(0, 0);
        hasWon = false;
        isDead = false;
        hasDeathAnimEnded = false;
        isJumping = false;
        isDoubleJumping = false;
    }

    public void startRunning() {
        animState = ANIM_STATE_RUNNING;
        velocity.set(heroVelocityX, 0);
    }

    public void update(float deltaTime) {
        currentStateDisplayTime += deltaTime;

        // ゲームオーバー時のアニメーション
        if (isDead) {
            updateDeadAnimation(deltaTime);
            return;
        }

        if (isJumping) {
            // ジャンプ中
            jumpingTime += deltaTime;
            float jumpTime = jumpingTime / Hero.JUMP_TIME;
            position.y = HERO_FLOOR_Y + jumpFunc(jumpTime) * JUMP_HEIGHT;
            if (jumpingTime >= Hero.JUMP_TIME) {
                // ジャンプ終了時
                isJumping = false;
                isDoubleJumping = false;
                animState = ANIM_STATE_RUNNING;
                currentStateDisplayTime = 0;
                position.y = HERO_FLOOR_Y;
            }
        }

        // ゲームクリア時のアニメーション
        if (hasWon && !isJumping) {
            updateWinAnimation(deltaTime);
        }

        position.mulAdd(velocity, deltaTime);

        collisionRect.set(position.x + 30, position.y, 40, 68);
    }

    private void updateDeadAnimation(float deltaTime) {
        float deadTimeFraction = currentStateDisplayTime / 0.75f;
        position.y = deadPosY + deadFunc(deadTimeFraction) * DEAD_JUMP_HEIGHT;
        if (deadTimeFraction >= 0.75f) {
            hasDeathAnimEnded = true;
        }
    }

    private void updateWinAnimation(float deltaTime) {
        if (winAnimState == WIN_ANIM_STATE_WAIT_FOR_LANDING) {
            winAnimState = WIN_ANIM_STATE_RUN;
            animState = ANIM_STATE_RUNNING;
            currentStateDisplayTime = 0;
        }
        else if (winAnimState == WIN_ANIM_STATE_RUN) {
            if (currentStateDisplayTime > 0.4f) {
                winAnimState = WIN_ANIM_STATE_SPIRAL;
                animState = ANIM_STATE_WIN;
                currentStateDisplayTime = 0;
                velocity.set(0, 0);
            }
        }
        else if (winAnimState == WIN_ANIM_STATE_SPIRAL) {
            if (currentStateDisplayTime > 1.4f) {
                winAnimState = WIN_ANIM_STATE_STATIC;
                animState = ANIM_STATE_STILL;
                animStillFrame = animations[ANIM_STATE_WIN].getKeyFrame(0);
                currentStateDisplayTime = 0;
            }
        }
    }

    private float deadFunc(float t) {
        t = Math.min(Math.max(t, 0.0f), 1.0f);
        return -7.6f * t * t + 5.62f * t;
    }

    public void draw(MyGdxGame game) {
        if (hasDeathAnimEnded)
            return;

        if (animState == ANIM_STATE_STILL) {
            game.batch.draw(animStillFrame,
                    position.x, position.y, 100, 98);
        }
        else {
            game.batch.draw(animations[animState]
                    .getKeyFrame(currentStateDisplayTime),
                    position.x, position.y, 100, 98);
        }
    }

    // ゲームクリア通知
    public void win() {
        hasWon = true;
        winAnimState = WIN_ANIM_STATE_WAIT_FOR_LANDING;
        velocity.set(300, 0);
    }

    // ゲームオーバー通知
    public void die() {
        isDead = true;
        animStillFrame = deadFrame;
        animState = ANIM_STATE_STILL;
        currentStateDisplayTime = 0.0f;
        deadPosY = position.y;
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            jumpingTime = 0;
            animState = ANIM_STATE_JUMPING;
            currentStateDisplayTime = 0;
        }
        else if (!isDoubleJumping) {
            if (jumpingTime > JUMP_TIME / 2.0f) {
                isDoubleJumping = true;
                jumpingTime = JUMP_TIME - jumpingTime;
                currentStateDisplayTime = 0;
            }
        }
    }

    private float jumpFunc(float t) {
        t = Math.min(Math.max(t, 0.0f), 1.0f);
        return t * (-4.0f * t + 4.0f);
    }

}
