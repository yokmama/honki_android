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

    // アニメーションの状態
    private final static int ANIM_STATE_STILL = -1;
    private final static int ANIM_STATE_RUNNING = 0;
    private final static int ANIM_STATE_JUMPING = 1;
    private final static int ANIM_STATE_WIN = 2;

    // 地面からの距離
    static final float HERO_FLOOR_Y = 45;
    // 画面左からの距離
    static final float HERO_LEFT_X = 150;

    // テクスチャ
    private static Texture sHeroTexture;
    private static TextureRegion mAnimStillFrame;
    private static TextureRegion sDeadFrame;
    private static Animation[] sAnimations;

    // アニメーションの状態
    int mAnimState;
    // 現在のアニメーションの状態を続けた時間
    float mCurrentStateDisplayTime;

    // ジャンプ
    private boolean mIsJumping;
    private boolean mIsDoubleJumping;
    private float mJumpingTime;
    private static final float mJumpTime = 1.0f;
    private static final float mJumpHeight = 300.0f;

    // キャラクタの位置
    private Vector2 mPosition = new Vector2();
    // キャラクタの速度
    private Vector2 mVelocity = new Vector2();
    // キャラクタの衝突判定用範囲オブジェクト
    private Rectangle mCollisionRect = new Rectangle();

    // ゲームオーバー
    boolean mIsDead;
    float mDeadTime;
    float mDeadPosY;

    // ゲームクリア
    final static int WIN_ANIM_STATE_WAIT_FOR_LANDING = 0;
    final static int WIN_ANIM_STATE_RUN = 1;
    final static int WIN_ANIM_STATE_JUMP = 2;
    final static int WIN_ANIM_STATE_SPIRAL = 3;
    final static int WIN_ANIM_STATE_STATIC = 4;
    boolean mHasWon;
    int mWinAnimState;
    float mWinAnimStateTime;

    // キャラクターの速度
    float heroVelocityX = 400;

    public Hero() {
        loadTexture();
        init();
    }

    public void init() {
        mHasWon = false;
        mIsDead = false;
        mIsJumping = false;
        mIsDoubleJumping = false;
        mAnimState = ANIM_STATE_STILL;
        mAnimStillFrame = sAnimations[ANIM_STATE_RUNNING].getKeyFrame(0);
        mPosition.set(Hero.HERO_LEFT_X, Hero.HERO_FLOOR_Y);
        mVelocity.set(0, 0);
    }

    public static void loadTexture() {
        sHeroTexture = new Texture("UnityChan.png");

        sAnimations = new Animation[3];

        // テクスチャから、状態毎に TextureRegion を取得する
        Array<TextureRegion> regions;
        TextureRegion[][] split = TextureRegion.split(sHeroTexture,
                TEXTURE_TILE_WIDTH, TEXTURE_TILE_HEIGHT);

        regions = new Array<TextureRegion>();
        regions.addAll(split[1], 0, 4);
        regions.addAll(split[2], 0, 4);
        sAnimations[ANIM_STATE_RUNNING] = new Animation(TIME_DURATION_RUNNING,
                regions, Animation.PlayMode.LOOP);

        regions = new Array<TextureRegion>();
        regions.addAll(split[3], 0, 7);
        sAnimations[ANIM_STATE_JUMPING] = new Animation(TIME_DURATION_JUMPING,
                regions, Animation.PlayMode.NORMAL);

        regions = new Array<TextureRegion>();
        regions.addAll(split[0], 3, 2);
        sAnimations[ANIM_STATE_WIN] = new Animation(TIME_DURATION_WIN,
                regions, Animation.PlayMode.LOOP);

        sDeadFrame = split[0][3];
    }

    public static void disposeTexture() {
        if (sHeroTexture != null) {
            sHeroTexture.dispose();
            sHeroTexture = null;
            sAnimations = null;
            sDeadFrame = null;
        }
    }

    public void startRunning() {
        mAnimState = ANIM_STATE_RUNNING;
        mVelocity.set(heroVelocityX, 0);
    }

    public void jump() {
        if (!mIsJumping) {
            mIsJumping = true;
            mJumpingTime = 0;
            mAnimState = ANIM_STATE_JUMPING;
            mCurrentStateDisplayTime = 0;
        }
        else if (!mIsDoubleJumping) {
            if (mJumpingTime > mJumpTime / 2.0f) {
                mIsDoubleJumping = true;
                mJumpingTime = mJumpTime - mJumpingTime;
                mCurrentStateDisplayTime = 0;
            }
        }
    }

    public void update(float deltaTime) {
        if (mIsDead) {
            return;
        }

        mCurrentStateDisplayTime += deltaTime;

        if (mIsJumping) {
            // ジャンプ中
            mJumpingTime += deltaTime;
            float jumpTime = mJumpingTime / mJumpTime;
            mPosition.y = HERO_FLOOR_Y + jumpFunc(jumpTime) * mJumpHeight;
            if (mJumpingTime >= mJumpTime) {
                // ジャンプ終了時
                mIsJumping = false;
                mIsDoubleJumping = false;
                mAnimState = ANIM_STATE_RUNNING;
                mCurrentStateDisplayTime = 0;
                mPosition.y = HERO_FLOOR_Y;
            }
        }

        // Win animation
        if (mHasWon) {
            if (!mIsJumping) {
                if (mWinAnimState == WIN_ANIM_STATE_WAIT_FOR_LANDING) {
                    mWinAnimState = WIN_ANIM_STATE_RUN;
                    mAnimState = ANIM_STATE_RUNNING;
                    mCurrentStateDisplayTime = 0;
                }
                else if (mWinAnimState == WIN_ANIM_STATE_RUN) {
                    if (mCurrentStateDisplayTime > 0.4f) {
                        mWinAnimState = WIN_ANIM_STATE_SPIRAL;
                        mAnimState = ANIM_STATE_WIN;
                        mCurrentStateDisplayTime = 0;
                        mVelocity.set(0, 0);
                    }
                }
                else if (mWinAnimState == WIN_ANIM_STATE_SPIRAL) {
                    if (mCurrentStateDisplayTime > 1.f) {
                        mWinAnimState = WIN_ANIM_STATE_STATIC;
                        mAnimState = ANIM_STATE_STILL;
                        mAnimStillFrame = sAnimations[ANIM_STATE_WIN].getKeyFrame(2);
                        mCurrentStateDisplayTime = 0;
                    }
                }
            }
        }

        mPosition.mulAdd(mVelocity, deltaTime);

        mCollisionRect.set(mPosition.x + 30, mPosition.y, 40, 68);
    }

    private float jumpFunc(float t) {
        t = Math.min(Math.max(t, 0.0f), 1.0f);
        return t * (-4.0f * t + 4.0f);
    }

    public void draw(MyGdxGame game) {
        if (mAnimState == ANIM_STATE_STILL) {
            game.batch.draw(mAnimStillFrame,
                    mPosition.x, mPosition.y, 100, 98);
        }
        else {
            game.batch.draw(sAnimations[mAnimState]
                    .getKeyFrame(mCurrentStateDisplayTime),
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
        mHasWon = true;
        mWinAnimState = WIN_ANIM_STATE_WAIT_FOR_LANDING;
        mWinAnimStateTime = 0;
        mVelocity.set(300, 0);
    }

    // ゲームオーバー通知
    public void die() {
        mIsDead = true;
        mAnimStillFrame = sDeadFrame;
        mAnimState = ANIM_STATE_STILL;
        mDeadTime = 0.0f;
        mDeadPosY = mPosition.y;
    }

}