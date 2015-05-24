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

    // ジャンプ
    private boolean isJumping;
    private boolean isDoubleJumping;
    private float jumpingTime;
    private static final float jumpTime = 1.0f;
    private static final float jumpHeight = 300.0f;

    // キャラクタの位置
    private Vector2 mPosition = new Vector2();
    // キャラクタの速度
    private Vector2 mVelocity = new Vector2();
    // キャラクタの衝突判定用範囲オブジェクト
    private Rectangle mCollisionRect = new Rectangle();

    // ゲームオーバー
    private boolean isDead;
    private float deadTime;
    private float deadPosY;
    private float deadJumpHeight = 100.0f;
    private boolean hasDeathAnimEnded;

    // ゲームクリア
    private final static int WIN_ANIM_STATE_WAIT_FOR_LANDING = 0;
    private final static int WIN_ANIM_STATE_RUN = 1;
    private final static int WIN_ANIM_STATE_SPIRAL = 2;
    private final static int WIN_ANIM_STATE_STATIC = 3;
    private boolean hasWon;
    private int winAnimState;
    private float winAnimStateTime;

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
        hasWon = false;
        isDead = false;
        isJumping = false;
        isDoubleJumping = false;
        animState = ANIM_STATE_STILL;
        animStillFrame = animations[ANIM_STATE_RUNNING].getKeyFrame(0);
        mPosition.set(Hero.HERO_LEFT_X, Hero.HERO_FLOOR_Y);
        mVelocity.set(0, 0);
        hasDeathAnimEnded = false;
    }

    public void startRunning() {
        animState = ANIM_STATE_RUNNING;
        mVelocity.set(heroVelocityX, 0);
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            jumpingTime = 0;
            animState = ANIM_STATE_JUMPING;
            currentStateDisplayTime = 0;
        }
        else if (!isDoubleJumping) {
            if (jumpingTime > jumpTime / 2.0f) {
                isDoubleJumping = true;
                jumpingTime = jumpTime - jumpingTime;
                currentStateDisplayTime = 0;
            }
        }
    }

    public void update(float deltaTime) {
        if (isDead) {
            deadTime += deltaTime;
            float deadTimeFrac = deadTime / 0.75f;
            mPosition.y = deadPosY + deadFunc(deadTimeFrac) * deadJumpHeight;
            if (deadTimeFrac >= 0.75f) {
                hasDeathAnimEnded = true;
            }
            return;
        }


        currentStateDisplayTime += deltaTime;

        if (isJumping) {
            // ジャンプ中
            jumpingTime += deltaTime;
            float jumpTime = jumpingTime / Hero.jumpTime;
            mPosition.y = HERO_FLOOR_Y + jumpFunc(jumpTime) * jumpHeight;
            if (jumpingTime >= Hero.jumpTime) {
                // ジャンプ終了時
                isJumping = false;
                isDoubleJumping = false;
                animState = ANIM_STATE_RUNNING;
                currentStateDisplayTime = 0;
                mPosition.y = HERO_FLOOR_Y;
            }
        }

        // Win animation
        if (hasWon) {
            if (!isJumping) {
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
                        mVelocity.set(0, 0);
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
        }

        mPosition.mulAdd(mVelocity, deltaTime);

        mCollisionRect.set(mPosition.x + 30, mPosition.y, 40, 68);
    }

    private float deadFunc(float t) {
        t = Math.min(Math.max(t, 0.0f), 1.0f);
        return -7.6f * t * t + 5.62f * t;
    }

    private float jumpFunc(float t) {
        t = Math.min(Math.max(t, 0.0f), 1.0f);
        return t * (-4.0f * t + 4.0f);
    }

    public void draw(MyGdxGame game) {
        if (hasDeathAnimEnded)
            return;

        if (animState == ANIM_STATE_STILL) {
            game.batch.draw(animStillFrame,
                    mPosition.x, mPosition.y, 100, 98);
        }
        else {
            game.batch.draw(animations[animState]
                    .getKeyFrame(currentStateDisplayTime),
                    mPosition.x, mPosition.y, 100, 98);
        }
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    public Rectangle getCollisionRect() {
        return mCollisionRect;
    }

    // ゲームクリア通知
    public void win() {
        hasWon = true;
        winAnimState = WIN_ANIM_STATE_WAIT_FOR_LANDING;
        winAnimStateTime = 0;
        mVelocity.set(300, 0);
    }

    // ゲームオーバー通知
    public void die() {
        isDead = true;
        animStillFrame = deadFrame;
        animState = ANIM_STATE_STILL;
        deadTime = 0.0f;
        deadPosY = mPosition.y;
    }

}