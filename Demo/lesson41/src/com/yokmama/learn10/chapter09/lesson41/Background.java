package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 背景を描画するためのクラス
 * Created by kayo on 6/3/15.
 */
class Background {
    // 背景の動く速さ
    public static final float SPEED = 0.05f;

    // テクスチャ
    private TextureRegion background;
    private TextureRegion backgroundFar;
    private TextureRegion backgroundNear;
    private TextureRegion road;

    // ステージの全長を返す
    public int stageWidth;
    // 領域の幅・高さ
    private int viewportWidth;
    private int viewportHeight;

    public Background(Texture background, Texture backgroundFar, Texture backgroundNear, Texture road) {
        this.background = new TextureRegion(background);
        this.backgroundFar = new TextureRegion(backgroundFar);
        this.backgroundNear = new TextureRegion(backgroundNear);
        this.road = new TextureRegion(road);
    }

    // 領域を設定する
    public void setViewport(int viewportWidth, int viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.stageWidth = viewportHeight * (background.getTexture().getWidth() / background.getTexture().getHeight());
    }

    // 描画
    public void draw(Batch batch, float cameraLeftEdge) {
        // 背景（星空）の描画
        float drawOffset = cameraLeftEdge - cameraLeftEdge * SPEED;
        batch.draw(background, drawOffset, 0, stageWidth, viewportHeight);

        // 背景（遠）の描画を繰り返す
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.4f) % viewportWidth;
        batch.draw(backgroundFar, drawOffset, 80, viewportWidth, 250);
        batch.draw(backgroundFar, drawOffset + viewportWidth, 80, viewportWidth, 250);

        // 背景（近）の描画を繰り返す
        float viewportWidthTwoThirds = 2.0f * viewportWidth / 3.0f;
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.6f) % viewportWidthTwoThirds;
        batch.draw(backgroundNear, drawOffset, 40, viewportWidthTwoThirds, 400);
        batch.draw(backgroundNear, drawOffset + viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);
        batch.draw(backgroundNear, drawOffset + 2 * viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);

        // 背景（道）の描画を繰り返す
        drawOffset = cameraLeftEdge - cameraLeftEdge % viewportWidth;
        batch.draw(road, drawOffset, 0, viewportWidth, 58 * 1.2f);
        batch.draw(road, drawOffset + viewportWidth, 0, viewportWidth, 58 * 1.2f);
    }

}
