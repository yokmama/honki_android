package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * キャラクター
 */
class Hero implements Disposable {

    // 地面からの距離
    static final float HERO_FLOOR_Y = 45;
    // 画面左からの距離
    static final float HERO_LEFT_X = 150;

    // アニメーションの状態
    final static int ANIM_STATE_STILL = -1;
    final static int ANIM_STATE_RUNNING = 0;
    final static int ANIM_STATE_JUMPING = 1;

    // テクスチャ
    private Texture texture;
    private TextureRegion mAnimStillFrame;
    private TextureRegion mDeadFrame;
    private Animation[] mAnimations;

    // アニメーションの状態
    int mAnimState;
    // 現在のアニメーションの状態を続けた時間
    float mCurrentStateDisplayTime;

    // ジャンプ
    boolean mIsJumping;
    boolean mIsDoubleJumping;
    float mJumpingTime;
    float mJumpTime = 1.0f;
    float mJumpHeight = 300.0f;

    // キャラクタの位置
    private Vector2 mPosition = new Vector2();
    // キャラクタの速度
    private Vector2 mVelocity = new Vector2();
    // キャラクタの衝突判定用範囲オブジェクト
    private Rectangle mCollisionRect = new Rectangle();

    // フラグ
    boolean mIsDead;
    boolean mHasWon;

    private static final int TILE_WIDTH = 64;
    private static final int TILE_HEIGHT = 64;

    private static final float TIME_DURATION_JUMPING = 0.05f;
    private static final float TIME_DURATION_RUNNING = 0.05f;

    public Hero() {
        texture = new Texture("UnityChan.png");

        mAnimations = new Animation[3];

        // テクスチャから、状態毎に TextureRegion を取得する
        Array<TextureRegion> regions;
        TextureRegion[][] split = TextureRegion.split(texture,
                TILE_WIDTH, TILE_HEIGHT);

        regions = new Array<TextureRegion>();
        regions.addAll(split[1], 0, 4);
        regions.addAll(split[2], 0, 4);
        mAnimations[ANIM_STATE_RUNNING] = new Animation(TIME_DURATION_RUNNING,
                regions, Animation.PlayMode.LOOP);

        regions = new Array<TextureRegion>();
        regions.addAll(split[3], 0, 7);
        mAnimations[ANIM_STATE_JUMPING] = new Animation(TIME_DURATION_JUMPING,
                regions, Animation.PlayMode.NORMAL);

        mDeadFrame = split[0][3];

        init();
    }

    public void init() {
        mHasWon = false;
        mIsDead = false;
        mIsJumping = false;
        mIsDoubleJumping = false;
        mAnimState = ANIM_STATE_STILL;
        mAnimStillFrame = mAnimations[ANIM_STATE_RUNNING].getKeyFrame(0);
    }

    public void startRunning() {
        mAnimState = ANIM_STATE_RUNNING;
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
            game.batch.draw(mAnimations[mAnimState]
                    .getKeyFrame(mCurrentStateDisplayTime),
                    mPosition.x, mPosition.y, 100, 98);
        }
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    public Vector2 getVelocity() {
        return mVelocity;
    }

    public Rectangle getCollisionRect() {
        return mCollisionRect;
    }

    // ゲームクリア通知
    public void win() {
        mHasWon = true;
        // TODO: ゴール時
    }

    // ゲームオーバー通知
    public void die() {
        mIsDead = true;
        mAnimStillFrame = mDeadFrame;
        mAnimState = ANIM_STATE_STILL;
        // TODO: ゲームオーバー時
    }

    @Override
    public void dispose() {

    }
}