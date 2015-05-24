package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 背景を描画するためのクラス
 */
public class Background {
    // 背景の動く速さ
    public static float SPEED = 0.05f;

    // テクスチャ
    private TextureRegion mBackground;
    private TextureRegion mBackgroundFar;
    private TextureRegion mBackgroundNear;
    private TextureRegion mRoad;

    // ステージの全長を返す
    public int stageWidth;
    // 領域の幅・高さ
    private int mViewportWidth;
    private int mViewportHeight;

    public Background(Texture background, Texture backgroundFar, Texture backgroundNear, Texture road) {
        this.mBackground = new TextureRegion(background);
        this.mBackgroundFar = new TextureRegion(backgroundFar);
        this.mBackgroundNear = new TextureRegion(backgroundNear);
        this.mRoad = new TextureRegion(road);
    }

    // 領域を設定する
    public void setViewport(int viewportWidth, int viewportHeight) {
        this.mViewportWidth = viewportWidth;
        this.mViewportHeight = viewportHeight;
        this.stageWidth = viewportHeight * (mBackground.getTexture().getWidth() / mBackground.getTexture().getHeight());
    }

    // 描画
    public void draw(Batch batch, float cameraLeftEdge) {
        // 背景（星空）の描画
        float drawOffset = cameraLeftEdge - cameraLeftEdge * SPEED;
        batch.draw(mBackground, drawOffset, 0, stageWidth, mViewportHeight);

        // 背景（遠）の描画を繰り返す
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.4f) % mViewportWidth;
        batch.draw(mBackgroundFar, drawOffset, 80, mViewportWidth, 250);
        batch.draw(mBackgroundFar, drawOffset + mViewportWidth, 80, mViewportWidth, 250);

        // 背景（近）の描画を繰り返す
        float viewportWidthTwoThirds = 2.0f * mViewportWidth / 3.0f;
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.6f) % viewportWidthTwoThirds;
        batch.draw(mBackgroundNear, drawOffset, 40, viewportWidthTwoThirds, 400);
        batch.draw(mBackgroundNear, drawOffset + viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);
        batch.draw(mBackgroundNear, drawOffset + 2 * viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);

        // 背景（道）の描画を繰り返す
        drawOffset = cameraLeftEdge - cameraLeftEdge % mViewportWidth;
        batch.draw(mRoad, drawOffset, 0, mViewportWidth, 58 * 1.2f);
        batch.draw(mRoad, drawOffset + mViewportWidth, 0, mViewportWidth, 58 * 1.2f);
    }

}
