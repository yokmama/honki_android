package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
* Created by maciek on 1/28/15.
*/
class Hero {
    final static int ANIM_STATE_STILL = -1;
    final static int ANIM_STATE_RUNNING = 0;
    final static int ANIM_STATE_JUMPING = 1;
    final static int ANIM_STATE_WIN = 2;
    Animation[] mAnimations;
    TextureRegion mAnimStillFrame;
    int mAnimState;
    float mCurrentStateDisplayTime;

    boolean mIsJumping;
    boolean mIsDoubleJumping;
    float mJumpingTime;

    float mJumpTime = 1.0f;
    float mJumpHeight = 300.0f;

    Vector2 mPosition = new Vector2();
    Vector2 mVelocity = new Vector2();
    Rectangle mCollisionRect = new Rectangle();

    boolean mIsDead;
    boolean mHasDeathAnimEnded;
    float mDeadTime;
    float mDeadPosY;
    float mDeadJumpHeight = 100.0f;

    final static int WIN_ANIM_STATE_WAIT_FOR_LANDING = 0;
    final static int WIN_ANIM_STATE_RUN = 1;
    final static int WIN_ANIM_STATE_JUMP = 2;
    final static int WIN_ANIM_STATE_SPIRAL = 3;
    final static int WIN_ANIM_STATE_STATIC = 4;
    boolean mHasWon;
    int mWinAnimState;
    float mWinAnimStateTime;

    public Hero(Texture texture, int spriteWidth, int spriteHeight, float[] msPerFrame, int[] stateFrames) {
        mAnimations = new Animation[3];

        Array<TextureRegion> regions;
        regions = Utils.getFrames(texture, spriteHeight, stateFrames[ANIM_STATE_RUNNING], spriteWidth, spriteHeight);
        regions.addAll(Utils.getFrames(texture, 2 * spriteHeight, stateFrames[ANIM_STATE_RUNNING], spriteWidth, spriteHeight));
        mAnimations[ANIM_STATE_RUNNING] = new Animation(msPerFrame[ANIM_STATE_RUNNING], regions, Animation.PlayMode.LOOP);

        regions = Utils.getFrames(texture, 3 * spriteHeight, stateFrames[ANIM_STATE_JUMPING], spriteWidth, spriteHeight);
        mAnimations[ANIM_STATE_JUMPING] = new Animation(msPerFrame[ANIM_STATE_JUMPING], regions, Animation.PlayMode.NORMAL);

        regions = Utils.getFrames(texture, 0, stateFrames[ANIM_STATE_WIN], spriteWidth, spriteHeight);
        mAnimations[ANIM_STATE_WIN] = new Animation(msPerFrame[ANIM_STATE_WIN], regions, Animation.PlayMode.LOOP);

        init();
    }

    public int getFrameWidth() {
        return mAnimations[mAnimState].getKeyFrames()[0].getRegionWidth();
    }

    public int getFrameHeight() {
        return mAnimations[mAnimState].getKeyFrames()[0].getRegionHeight();
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
            mDeadTime += deltaTime;
            float deadTimeFrac = mDeadTime / 0.75f;
            mPosition.y = mDeadPosY + deadFunc(deadTimeFrac) * mDeadJumpHeight;
            if (deadTimeFrac >= 0.75f) {
                mHasDeathAnimEnded = true;
            }
            return;
        }

        mCurrentStateDisplayTime += deltaTime;

        if (mIsJumping) {
            mJumpingTime += deltaTime;
            float jumpTime = mJumpingTime / mJumpTime;
            mPosition.y = MyGdxGame.HERO_FLOOR_Y + jumpFunc(jumpTime) * mJumpHeight;
            if (mJumpingTime >= mJumpTime) {
                mIsJumping = false;
                mIsDoubleJumping = false;
                mAnimState = ANIM_STATE_RUNNING;
                mCurrentStateDisplayTime = 0;
                mPosition.y = MyGdxGame.HERO_FLOOR_Y;
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
                    if (mCurrentStateDisplayTime > 0.6f) {
                        mWinAnimState = WIN_ANIM_STATE_STATIC;
                        mAnimState = ANIM_STATE_STILL;
                        mAnimStillFrame = mAnimations[ANIM_STATE_WIN].getKeyFrame(2);
                        mCurrentStateDisplayTime = 0;
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
        if (mHasDeathAnimEnded)
            return;

        if (mAnimState == ANIM_STATE_STILL) {
            game.batch.draw(mAnimStillFrame, mPosition.x, mPosition.y, 100, 98);
        }
        else if (mAnimState == ANIM_STATE_WIN) {
            // ぐらぐら
            if ((int)(mCurrentStateDisplayTime * 10.f / 2) % 2 == 0) {
                mAnimStillFrame = mAnimations[ANIM_STATE_WIN].getKeyFrames()[3];
            } else {
                mAnimStillFrame = mAnimations[ANIM_STATE_WIN].getKeyFrames()[4];
            }
            game.batch.draw(mAnimStillFrame, mPosition.x, mPosition.y, 100, 98);
        }
        else {
            game.batch.draw(mAnimations[mAnimState].getKeyFrame(mCurrentStateDisplayTime), mPosition.x, mPosition.y, 100, 98);
        }
    }

    public void drawDebug(MyGdxGame game) {
        game.shapeRenderer.setColor(Color.WHITE);
        game.shapeRenderer.rect(mPosition.x, mPosition.y, 100, 98);
        game.shapeRenderer.setColor(Color.YELLOW);
        game.shapeRenderer.rect(mCollisionRect.x, mCollisionRect.y, mCollisionRect.width, mCollisionRect.height);
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    public Vector2 getVelocity() {
        return mVelocity;
    }

    public void startRunning() {
        mAnimState = ANIM_STATE_RUNNING;
    }

    public void init() {
        mHasWon = false;
        mIsDead = false;
        mIsJumping = false;
        mIsDoubleJumping = false;
        mHasDeathAnimEnded = false;
        mAnimState = ANIM_STATE_STILL;
        mAnimStillFrame = mAnimations[ANIM_STATE_RUNNING].getKeyFrame(0);
    }

    public void win() {
        mHasWon = true;
        mWinAnimState = WIN_ANIM_STATE_WAIT_FOR_LANDING;
        mWinAnimStateTime = 0;
        mVelocity.set(300, 0);
    }

    public void die() {
        mIsDead = true;
        mDeadTime = 0.0f;
        mDeadPosY = mPosition.y;
        mAnimStillFrame = mAnimations[ANIM_STATE_JUMPING].getKeyFrames()[3];
        mAnimState = ANIM_STATE_STILL;
    }
}
