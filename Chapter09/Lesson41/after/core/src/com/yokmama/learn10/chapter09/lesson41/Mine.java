package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by maciek on 1/28/15.
 */
class Mine {
    public static final float TEXTURE_SIZE = 50.0f;

    public static Texture mineTexture;
    public static TextureRegion mineTextureRegion;

    final Vector2 position = new Vector2();
    final Vector2 size = new Vector2();
    Circle collisionCircle;
    boolean hasCollided;
    boolean isDead;
    float timeSinceCreation;
    float timeSinceCollided;
    float collideAnimTimeFraction;

    private final float phaseShift;
    private final float collisionAnimTime = 1.0f;
    private float visiblePart;

    public Mine(float x, float y, float width, float height, float phaseShift) {
        this.position.x = x;
        this.position.y = y;
        this.size.x = width;
        this.size.y = height;
        this.timeSinceCreation = 0;
        this.phaseShift = phaseShift;
        this.collisionCircle = new Circle(x + width / 2, 0, Math.min(width, height) / 2);
        this.hasCollided = false;
        this.isDead = false;
    }

    public static void loadTexture() {
        if (mineTexture == null) {
            mineTexture = new Texture("fire.png");
            mineTextureRegion = new TextureRegion(mineTexture);
        }
    }

    public static void disposeTexture() {
        mineTexture.dispose();
        mineTextureRegion = null;
    }

    public void update(float deltaTime) {
        timeSinceCreation += deltaTime;
        if (hasCollided) {
            timeSinceCollided += deltaTime;
            collideAnimTimeFraction = timeSinceCollided / collisionAnimTime;
            if (timeSinceCollided > collisionAnimTime) {
                isDead = true;
            }
        }
        else {
            visiblePart = 0.2f + showFunc(timeSinceCreation * 0.2f + phaseShift) * 0.4f;
            collisionCircle.y = position.y - size.y / 2 + visiblePart * size.y;
        }
    }

    private float showFunc(float t) {
        t = t % 1.0f;
        return Math.min(Math.max(t * (-12.5f * t + 12.5f) - 2.0f, 0.0f), 1.0f);
    }

    public void draw(MyGdxGame game) {
        mineTextureRegion.setRegion(0, 0, mineTextureRegion.getTexture().getWidth(), (int) (mineTextureRegion.getTexture().getHeight() * visiblePart));
        game.batch.draw(mineTextureRegion, position.x, position.y, size.x, size.y * visiblePart);
    }

    public void collide() {
        hasCollided = true;
        timeSinceCollided = 0;
    }
}
