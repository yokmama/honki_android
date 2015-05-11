package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
* Created by maciek on 1/28/15.
*/
class Chip {
    Vector2 position = new Vector2();
    Vector2 positionPhase = new Vector2();
    Vector2 size = new Vector2();
    Rectangle bounds = new Rectangle();
    TextureRegion image;
    Circle collisionCircle;
    boolean isCollected;
    boolean isDead;
    float timeSinceCreation;
    float timeSinceCollected;
    float collectAnimTimeFraction;
    int type;

    float phaseShift = 0.0f;
    float collectAnimTime = 1.0f;
    float collectAnimHeight = 100.0f;
    private float scorePhase;

    public Chip(int type, TextureRegion image, float x, float y, float width, float height) {
        this(type, image, x, y, width, height, 0);
    }

    public Chip(int type, TextureRegion image, float x, float y, float width, float height, float phaseShift) {
        this.position.x = x;
        this.position.y = y;
        this.size.x = width;
        this.size.y = height;
        this.bounds.set(x, y, width, height);
        this.image = image;
        this.type = type;
        this.timeSinceCreation = 0;
        this.phaseShift = phaseShift;
        this.collisionCircle = new Circle(x + width / 2, y + height / 2, Math.min(width, height) / 2);
        this.isCollected = false;
        this.isDead = false;
    }

    public void update(float deltaTime) {
        timeSinceCreation += deltaTime;
        if (isCollected) {
            timeSinceCollected += deltaTime;
            collectAnimTimeFraction = timeSinceCollected / collectAnimTime;
            positionPhase.y = collectFunc(collectAnimTimeFraction) * collectAnimHeight;
            scorePhase = scoreFunc(collectAnimTimeFraction) * collectAnimHeight;
            if (timeSinceCollected > collectAnimTime) {
                isDead = true;
            }
        }
        else {
            positionPhase.y = (float) Math.sin(timeSinceCreation * 4.0f + phaseShift * 2.0f * Math.PI) * size.y / 2.0f;
            collisionCircle.y = position.y + positionPhase.y + size.y / 2;
        }
        bounds.set(position.x, position.y + positionPhase.y, size.x, size.y);
    }

    private float scoreFunc(float t) {
        t = 0.5f * Math.min(Math.max(t, 0.0f), 1.0f);
        return 2.0f * t * (-4.0f * t + 4.0f);
    }

    private float collectFunc(float t) {
        t = Math.min(Math.max(t, 0.0f), 1.0f);
        return t * (-4.0f * t + 4.0f);
    }

    public void draw(MyGdxGame game) {
        if (!isDead) {
            game.oldColor = game.batch.getColor();
            if (isCollected) {
                if (collectAnimTimeFraction < 0.5f) {
                    game.font.getData().setScale(game.chipScales[type]);
                    game.font.setColor(1.0f, 1.0f, 0.0f, 1.0f - 2.0f * collectAnimTimeFraction);
                    game.font.draw(
                            game.batch,
                            "+" + game.chipScores[type],
                            position.x + game.chipSize * 0.5f - bounds.width * 0.5f,
                            position.y + game.chipSize + bounds.height + scorePhase
                    );
                    game.font.getData().setScale(1.0f);
                }

                game.batch.setColor(Color.alpha(1.0f - collectAnimTimeFraction));
            }
            game.batch.draw(image, position.x + positionPhase.x, position.y + positionPhase.y, size.x, size.y);
            if (isCollected) {
                game.batch.setColor(game.oldColor);
            }
        }
    }

    public void drawDebug(MyGdxGame game) {
        if (!isCollected) {
            game.shapeRenderer.setColor(Color.YELLOW);
            game.shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
        }
    }

    public void collect() {
        isCollected = true;
        timeSinceCollected = 0.0f;
        scorePhase = 0.0f;
    }
}
