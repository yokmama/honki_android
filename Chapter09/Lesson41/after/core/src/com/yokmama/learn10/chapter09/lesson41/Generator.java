package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import javax.swing.DebugGraphics;

/**
 * スコアアイテムと障害物を生成する
 *
 * Created by maciek on 1/29/15.
 */
public class Generator implements Disposable {
    private static final int GENERATE_SPACE = 0;
    private static final int GENERATE_CHIPS = 1;
    private static final int GENERATE_MINES = 2;

    float chipGenerationLine;
    private int successiveMinesGenerated;

    private final Texture chipsTexture;
    private final TextureRegion[] chipRegions;
    final Array<Chip> chips = new Array<Chip>();
    final Array<Chip> chipsToRemove = new Array<Chip>();

    private final Texture mineTexture;
    private final TextureRegion mineTextureRegion;
    final Array<Mine> mines = new Array<Mine>();
    final Array<Mine> minesToRemove = new Array<Mine>();

    public Generator() {
        chipsTexture = new Texture("coins.png");
        TextureRegion[] split = TextureRegion.split(chipsTexture, Chip.TEXTURE_COIN_SIZE, Chip.TEXTURE_COIN_SIZE)[0];
        chipRegions = new TextureRegion[4];
        chipRegions[Chip.TYPE_ONE] = split[0];
        chipRegions[Chip.TYPE_TWO] = split[1];
        chipRegions[Chip.TYPE_THREE] = split[2];
        chipRegions[Chip.TYPE_FOUR] = split[3];

        mineTexture = new Texture("fire.png");
        mineTextureRegion = new TextureRegion(mineTexture);
    }

    // 初期化
    public void init(float line) {
        chipGenerationLine = line;
        successiveMinesGenerated = 0;
    }

    // 生成
    public void generate(MyGdxGame game) {
        int generate = MathUtils.random(0, 2);
        if (generate == GENERATE_SPACE) {
            // 何もない空間を生成する
            generateSpace(game);
        }
        else if (generate == GENERATE_CHIPS) {
            // スコアアイテムを生成する
            generateChips(game);
        }
        else if (generate == GENERATE_MINES) {
            if (successiveMinesGenerated < 2) {
                generateMines(game);
            }
            else {
                // Try once again
                generate(game);
                return;
            }
        }
    }

    // 空白の生成
    private void generateSpace(MyGdxGame game) {
        successiveMinesGenerated = 0;

        int spaceLength = MathUtils.random(3, 6);
        chipGenerationLine += Chip.CHIP_SIZE * spaceLength;
    }

    // スコアアイテムの生成
    private void generateChips(MyGdxGame game) {
        successiveMinesGenerated = 0;

        int chipType = MathUtils.random(0, 3);
        if (chipType == Chip.TYPE_ONE) {
            boolean up = MathUtils.randomBoolean();
            for (int i = 0; i < 5; ++i) {
                float offsetY = up ? 2 - Math.abs(i - 2) : Math.abs(i - 2);
                Chip chip = new Chip(chipRegions, chipType, chipGenerationLine, 200 + offsetY * Chip.CHIP_SIZE, Chip.CHIP_SIZE, Chip.CHIP_SIZE);
                chips.add(chip);

                // 空白追加
                chipGenerationLine += Chip.CHIP_SIZE;
            }

            // 空白追加
            chipGenerationLine += Chip.CHIP_SIZE;
        }
        else {
            float phaseShift = MathUtils.random();
            Chip chip = new Chip(chipRegions, chipType,
                    chipGenerationLine, 200,
                    Chip.CHIP_SIZE, Chip.CHIP_SIZE,
                    phaseShift);
            chips.add(chip);
            chipGenerationLine += Chip.CHIP_SIZE;

            // 空白追加
            chipGenerationLine += Chip.CHIP_SIZE;
        }
    }

    // 障害物の生成
    private void generateMines(MyGdxGame game) {
        ++successiveMinesGenerated;

        float phaseShift = MathUtils.random();
        Mine mine = new Mine(mineTextureRegion, chipGenerationLine, Hero.HERO_FLOOR_Y, Mine.TEXTURE_SIZE, 50.0f, phaseShift);
        mines.add(mine);
        chipGenerationLine += Mine.TEXTURE_SIZE;

        // 空白追加
        chipGenerationLine += 3 * Chip.CHIP_SIZE;
    }

    public void update(MyGdxGame game, float deltaTime) {
        chipsToRemove.clear();
        for (Chip chip : chips) {
            chip.update(deltaTime);

            if (chip.isDead) {
                chipsToRemove.add(chip);
            }
            else if (chip.position.x + chip.size.x < game.cameraLeftEdge) {
                chipsToRemove.add(chip);
            }
        }
        for (Chip chip : chipsToRemove) {
            chips.removeValue(chip, false);
        }

        minesToRemove.clear();
        for (Mine mine : mines) {
            mine.update(deltaTime);

            if (mine.position.x + mine.size.x < game.cameraLeftEdge) {
                minesToRemove.add(mine);
            }
        }
        for (Mine mine : minesToRemove) {
            mines.removeValue(mine, false);
        }
    }

    public void draw(MyGdxGame game) {
        for (Chip chip : chips) {
            chip.draw(game, game.text);
        }

        for (Mine mine : mines) {
            mine.draw(game);
        }
    }

    public void clear() {
        chips.clear();
        mines.clear();
    }

    @Override
    public void dispose() {
        chipsTexture.dispose();

        mineTexture.dispose();
    }
}
