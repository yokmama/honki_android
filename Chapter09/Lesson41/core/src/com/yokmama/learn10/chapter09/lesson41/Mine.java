package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
* Created by maciek on 1/28/15.
*/
class Mine {
    Vector2 position = new Vector2();
    Vector2 size = new Vector2();
    TextureRegion image;
    Circle collisionCircle;
    boolean hasCollided;
    boolean isDead;
    float timeSinceCreation;
    float timeSinceCollided;
    float collideAnimTimeFraction;

    float phaseShift = 0.0f;
    float collisionAnimTime = 1.0f;
    float visiblePart;

    public Mine(TextureRegion image, float x, float y, float width, float height, float phaseShift) {
        this.position.x = x;
        this.position.y = y;
        this.size.x = width;
        this.size.y = height;
        this.image = image;
        this.timeSinceCreation = 0;
        this.phaseShift = phaseShift;
        this.collisionCircle = new Circle(x + width / 2, 0, Math.min(width, height) / 2);
        this.hasCollided = false;
        this.isDead = false;
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
        image.setRegion(0, 0, image.getTexture().getWidth(), (int) (image.getTexture().getHeight() * visiblePart));
        game.batch.draw(image, position.x, position.y, size.x, size.y * visiblePart);
    }

    public void collide() {
        hasCollided = true;
        timeSinceCollided = 0;
    }
}
