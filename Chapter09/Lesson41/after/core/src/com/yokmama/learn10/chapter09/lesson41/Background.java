package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 背景を描画するためのクラス
 */
public class Background {
    public static float SPEED = 0.05f;

    private int mStageWidth;
    private int mViewportWidth;
    private int mViewportHeight;

    private TextureRegion mBackground;
    private TextureRegion mBackgroundFar;
    private TextureRegion mBackgroundNear;
    private TextureRegion mRoad;

    public Background(Texture background, Texture backgroundFar, Texture backgroundNear, Texture road) {
        this.mBackground = new TextureRegion(background);
        this.mBackgroundFar = new TextureRegion(backgroundFar);
        this.mBackgroundNear = new TextureRegion(backgroundNear);
        this.mRoad = new TextureRegion(road);
    }
    public void setViewport(int viewportWidth, int viewportHeight) {
        this.mViewportWidth = viewportWidth;
        this.mViewportHeight = viewportHeight;
        this.mStageWidth = viewportHeight * (mBackground.getTexture().getWidth() / mBackground.getTexture().getHeight());
    }

    public int getStageWidth() {
        return this.mStageWidth;
    }

    public void draw(Batch batch, float cameraLeftEdge) {
        float drawOffset = cameraLeftEdge - cameraLeftEdge * SPEED;
        batch.draw(mBackground, drawOffset, 0, mStageWidth, mViewportHeight);

        // Repeat two far mBackgroundNear textures
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.4f) % mViewportWidth;
        batch.draw(mBackgroundFar, drawOffset, 80, mViewportWidth, 250);
        batch.draw(mBackgroundFar, drawOffset + mViewportWidth, 80, mViewportWidth, 250);

        // Repeat three near mBackgroundNear textures
        float viewportWidthTwoThirds = 2.0f * mViewportWidth / 3.0f;
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.6f) % viewportWidthTwoThirds;
        batch.draw(mBackgroundNear, drawOffset, 40, viewportWidthTwoThirds, 400);
        batch.draw(mBackgroundNear, drawOffset + viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);
        batch.draw(mBackgroundNear, drawOffset + 2 * viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);

        drawOffset = cameraLeftEdge - cameraLeftEdge % mViewportWidth;
        batch.draw(mRoad, drawOffset, 0, mViewportWidth, 58 * 1.2f);
        batch.draw(mRoad, drawOffset + mViewportWidth, 0, mViewportWidth, 58 * 1.2f);
    }

}
