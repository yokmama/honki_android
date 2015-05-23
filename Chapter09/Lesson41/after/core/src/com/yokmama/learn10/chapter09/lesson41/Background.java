package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * 背景を描画するためのクラス
 */
public class Background {
    public static float SPEED = 0.05f;

    private int bgWidth;
    private int viewportWidth;
    private int viewportHeight;

    private Texture mBackground;
    private Texture mBackgroundNear;
    private Texture mBackgroundFar;
    private Texture mRoad;

    public Background(int viewportWidth, int viewportHeight) {
        mBackgroundNear = new Texture("bg_near.png");
        mBackgroundFar = new Texture("bg_far.png");
        mRoad = new Texture("road.png");
        mBackground = new Texture("bg.png");

        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.bgWidth = viewportHeight * (mBackground.getWidth() / mBackground.getHeight());
    }

    public int getStageWidth() {
        return this.bgWidth;
    }

    public void update() {
    }

    public void draw(Batch batch, float cameraLeftEdge) {
        float drawOffset = cameraLeftEdge - cameraLeftEdge * SPEED;
        batch.draw(mBackground, drawOffset, 0, bgWidth, viewportHeight);

        // Repeat two far mBackgroundNear textures
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.4f) % viewportWidth;
        batch.draw(mBackgroundFar, drawOffset, 80, viewportWidth, 250);
        batch.draw(mBackgroundFar, drawOffset + viewportWidth, 80, viewportWidth, 250);

        // Repeat three near mBackgroundNear textures
        float viewportWidthTwoThirds = 2.0f * viewportWidth / 3.0f;
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.6f) % viewportWidthTwoThirds;
        batch.draw(mBackgroundNear, drawOffset, 40, viewportWidthTwoThirds, 400);
        batch.draw(mBackgroundNear, drawOffset + viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);
        batch.draw(mBackgroundNear, drawOffset + 2 * viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);

        drawOffset = cameraLeftEdge - cameraLeftEdge % viewportWidth;
        batch.draw(mRoad, drawOffset, 0, viewportWidth, 58 * 1.2f);
        batch.draw(mRoad, drawOffset + viewportWidth, 0, viewportWidth, 58 * 1.2f);
    }
}
