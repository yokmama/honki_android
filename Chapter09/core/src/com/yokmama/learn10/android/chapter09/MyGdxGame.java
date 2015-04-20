package com.yokmama.learn10.android.chapter09;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class MyGdxGame extends ApplicationAdapter {

    static enum GameState {
        Start, Running, GameOver, LevelCleared
    }

    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_HARD = 1;

    static final float HERO_FLOOR_Y = 45;
    static final float HERO_LEFT_X = 150;

    static final boolean DEBUG_DRAW = false;
    private final int defaultDifficulty;

    ShapeRenderer shapeRenderer;
    SpriteBatch batch;

    OrthographicCamera camera;
    OrthographicCamera uiCamera;
    int viewportWidth = 800;
    int viewportHeight = 480;
    float cameraLeftEdge;

    BitmapFont font;

    Hero hero;
    Rectangle heroCollision = new Rectangle();
    float heroVelocityX;

    Texture backgroundClear;
    Texture background;
    Texture backgroundFar;
    Texture bridge;
    float bgWidth;
    float bgSpeed;

    Texture finish;
    float finishX;

    static final int CHIP_ONE = 0;
    static final int CHIP_TWO = 1;
    static final int CHIP_THREE = 2;
    static final int CHIP_FOUR = 3;

    TextureRegion[] chipTextures;
    Array<Chip> chips = new Array<Chip>();
    Array<Chip> chipsToRemove = new Array<Chip>();
    int[] chipScores;
    float[] chipScales;

    TextureRegion mineTexture;
    Array<Mine> mines = new Array<Mine>();
    Array<Mine> minesToRemove = new Array<Mine>();

    float chipSize = 50.0f;
    float mineSize = 50.0f;

    int currentDifficulty;
    GameState gameState = GameState.Start;
    int score = 0;
    int previousScore;

    Music music;
    Sound explode;
    Sound drop;
    Sound finaleCrackers;
    Sound finaleCheers;
    float mTimeAfterEnd;

    boolean mPause = false;

    // Temporary variables
    BitmapFont.TextBounds uiTextBounds;
    Color oldColor;
    float textX;
    float textY;

    public MyGdxGame(int difficulty) {
        defaultDifficulty = difficulty;
    }

    @Override
    public void create () {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.update();

        font = new BitmapFont(Gdx.files.internal("arial.fnt"));

        backgroundClear = new Texture("bg.png");
        background = new Texture("city_skyline.png");
        backgroundFar = new Texture("city_skyline_far.png");
        bridge = new Texture("bridge.png");

        bgWidth = viewportHeight * (backgroundClear.getWidth() / backgroundClear.getHeight());
        bgSpeed = 0.05f;

        finish = new Texture("flag.png");
        finishX = (bgWidth - viewportWidth) / bgSpeed + HERO_LEFT_X;

        chipTextures = new TextureRegion[4];
        Texture coins = new Texture("coins.png");
        final int COINS_SIZE = 16;
        chipTextures[CHIP_ONE] = new TextureRegion(coins, 0, 0, COINS_SIZE, COINS_SIZE);
        chipTextures[CHIP_TWO] = new TextureRegion(coins, COINS_SIZE, 0, COINS_SIZE, COINS_SIZE);
        chipTextures[CHIP_THREE] = new TextureRegion(coins, COINS_SIZE * 2, 0, COINS_SIZE, COINS_SIZE);
        chipTextures[CHIP_FOUR] = new TextureRegion(coins, COINS_SIZE * 3, 0, COINS_SIZE, COINS_SIZE);

        chipScores = new int[4];
        chipScores[CHIP_ONE] = 10;
        chipScores[CHIP_TWO] = 20;
        chipScores[CHIP_THREE] = 50;
        chipScores[CHIP_FOUR] = 100;

        chipScales = new float[] { 0.6f, 0.65f, 0.7f, 0.8f };

        mineTexture = new TextureRegion(new Texture("mine.png"));

        Texture heroTexture = new Texture("UnityChan.png");
        float[] timePerFrame = new float[] { 0.05f, 0.05f, 0.05f };
        int[] numFrames = new int[] { 4, 7, 5 };
        hero = new Hero(heroTexture, 64, 64, timePerFrame, numFrames);

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        explode = Gdx.audio.newSound(Gdx.files.internal("laser3.mp3"));
        drop = Gdx.audio.newSound(Gdx.files.internal("coin05.mp3"));
        finaleCrackers = Gdx.audio.newSound(Gdx.files.internal("finale-crackers.wav"));
        finaleCheers = Gdx.audio.newSound(Gdx.files.internal("finale-cheers.wav"));

        resetWorld(defaultDifficulty);
    }

    private void resetWorld(int difficulty) {
        score = 0;

        Generator.init(viewportWidth);

        hero.getPosition().set(HERO_LEFT_X, HERO_FLOOR_Y);
        hero.getVelocity().set(0, 0);
        hero.init();

        currentDifficulty = difficulty;

        if (difficulty == DIFFICULTY_EASY) {
            heroVelocityX = 400;
        }
        else if (difficulty == DIFFICULTY_HARD) {
            heroVelocityX = 600;
        }

        camera.position.x = viewportWidth / 2 - HERO_LEFT_X;
        cameraLeftEdge = camera.position.x - viewportWidth / 2;

        chips.clear();
        mines.clear();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 153.0f / 255.0f, 204.0f / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateWorld();
        drawWorld();
    }

    private void updateWorld() {
        if (mPause)
            return;

        float deltaTime = Gdx.graphics.getDeltaTime();

        if (gameState == GameState.GameOver || gameState == GameState.LevelCleared) {
            mTimeAfterEnd += deltaTime;
        }

        // Handle input

        if (Gdx.input.justTouched()) {
            if (gameState == GameState.Start) {
                gameState = GameState.Running;
                hero.startRunning();
                hero.mVelocity.set(heroVelocityX, 0);
            }
            else if (gameState == GameState.GameOver) {
                // Prevent accidental taps
                if (mTimeAfterEnd > 0.6f) {
                    gameState = GameState.Start;
                    resetWorld(currentDifficulty);
                }
            }
            else if (gameState == GameState.LevelCleared) {
                // Prevent accidental taps
                if (mTimeAfterEnd > 0.6f) {
                    gameState = GameState.Start;
                    resetWorld(currentDifficulty);
                }
            }
            else if (gameState == GameState.Running) {
                hero.jump();
            }
        }

        // Generate new objects

        if (Generator.chipGenerationLine < cameraLeftEdge + viewportWidth &&
                Generator.chipGenerationLine + 5 * 50.0f < finishX) {
            Generator.generate(this);
        }

        // Update objects

        chipsToRemove.clear();
        for (Chip chip : chips) {
            chip.update(deltaTime);

            if (chip.isDead) {
                chipsToRemove.add(chip);
            }
            else if (chip.position.x + chip.size.x < cameraLeftEdge) {
                chipsToRemove.add(chip);
            }
        }
        for (Chip chip : chipsToRemove) {
            chips.removeValue(chip, false);
        }

        minesToRemove.clear();
        for (Mine mine : mines) {
            mine.update(deltaTime);

            if (mine.position.x + mine.size.x < cameraLeftEdge) {
                minesToRemove.add(mine);
            }
        }
        for (Mine mine : minesToRemove) {
            mines.removeValue(mine, false);
        }

        // Update hero and camera

        hero.update(deltaTime);

        if (gameState != GameState.LevelCleared) {
            camera.position.x = viewportWidth / 2 + hero.getPosition().x - HERO_LEFT_X;
            cameraLeftEdge = camera.position.x - viewportWidth / 2;
        }

        // Check for winning condition

        if (gameState != GameState.LevelCleared) {
            float drawOffset = cameraLeftEdge - cameraLeftEdge * bgSpeed;
            if (drawOffset + bgWidth < cameraLeftEdge + viewportWidth) {
                finaleCrackers.play();
                finaleCheers.play();
                gameState = GameState.LevelCleared;
                mTimeAfterEnd = 0;
                hero.win();
            }
        }

        // Stop collision checks after Game Over or Level Cleared

        if (gameState == GameState.GameOver || gameState == GameState.LevelCleared) {
            return;
        }

        // Check collisions

        heroCollision = hero.mCollisionRect;

        for (Chip chip : chips) {
            if (!chip.isCollected && Intersector.overlaps(chip.collisionCircle, heroCollision)) {
                chip.collect();
                drop.play();

                previousScore = score;
                score += chipScores[chip.type];

                // Speedup
                if (previousScore / 500 != score / 500) {
                    heroVelocityX += 100;
                }
            }
        }

        for (Mine mine : mines) {
            if (!mine.hasCollided && Intersector.overlaps(mine.collisionCircle, heroCollision)) {
                mine.collide();
                explode.play();
                hero.die();
                gameState = GameState.GameOver;
                mTimeAfterEnd = 0;
            }
        }
    }

    private void drawWorld() {
        camera.update();

        // Draw graphics

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        float drawOffset = cameraLeftEdge - cameraLeftEdge * bgSpeed;
        batch.draw(backgroundClear, drawOffset, 0, bgWidth, viewportHeight);

        // Repeat two far background textures
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.4f) % viewportWidth;
        batch.draw(backgroundFar, drawOffset, 80, viewportWidth, 250);
        batch.draw(backgroundFar, drawOffset + viewportWidth, 80, viewportWidth, 250);

        // Repeat three near background textures
        float viewportWidthTwoThirds = 2.0f * viewportWidth / 3.0f;
        drawOffset = cameraLeftEdge - (cameraLeftEdge * 0.6f) % viewportWidthTwoThirds;
        batch.draw(background, drawOffset, 40, viewportWidthTwoThirds, 400);
        batch.draw(background, drawOffset + viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);
        batch.draw(background, drawOffset + 2 * viewportWidthTwoThirds, 40, viewportWidthTwoThirds, 400);

        drawOffset = cameraLeftEdge - cameraLeftEdge % viewportWidth;
        batch.draw(bridge, drawOffset, 0, viewportWidth, 58 * 1.2f);
        batch.draw(bridge, drawOffset + viewportWidth, 0, viewportWidth, 58 * 1.2f);

        for (Chip chip : chips) {
            chip.draw(this);
        }

        for (Mine mine : mines) {
            mine.draw(this);
        }

        hero.draw(this);

        batch.draw(finish, finishX, 0, finish.getWidth() * 0.35f, finish.getHeight() * 0.35f);

        batch.end();

        // Draw debug primitives

        if (DEBUG_DRAW) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.circle((bgWidth - viewportWidth) / bgSpeed + HERO_LEFT_X, HERO_FLOOR_Y, 5);

            for (Chip chip : chips) {
                chip.drawDebug(this);
            }

            for (Mine mine : mines) {
                mine.drawDebug(this);
            }

            hero.drawDebug(this);

            shapeRenderer.end();
        }

        // Draw UI

        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        font.setColor(Color.WHITE);
        if (gameState == GameState.Start) {
            drawTextTop("START");
        }
        else if (gameState == GameState.LevelCleared) {
            drawTextTop("SCORE: " + score);
            drawTextCenter("LEVEL CLEAR");
        }
        else if (gameState == GameState.GameOver) {
            drawTextTop("SCORE: " + score);
            drawTextCenter("GAME OVER");
        }
        else if (gameState == GameState.Running) {
            drawTextTop("SCORE: " + score);
        }
        batch.end();
    }

    private void drawTextTop(String text) {
        uiTextBounds = font.getBounds(text);
        textX = Gdx.graphics.getWidth() * 0.5f - uiTextBounds.width * 0.5f;
        textY = Gdx.graphics.getHeight() - font.getLineHeight();
        font.draw(batch, text, textX, textY);
    }

    private void drawTextCenter(String text) {
        uiTextBounds = font.getBounds(text);
        textX = Gdx.graphics.getWidth() * 0.5f - uiTextBounds.width * 0.5f;
        textY = Gdx.graphics.getHeight() * 0.5f + uiTextBounds.height * 0.5f;
        font.draw(batch, text, textX, textY);
    }

    public void resumeGame() {
        mPause = false;
    }

    public void pauseGame() {
        mPause = true;
    }
}