package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by maciek on 1/29/15.
 */
public class Generator {
    static final int GENERATE_SPACE = 0;
    static final int GENERATE_CHIPS = 1;
    static final int GENERATE_MINES = 2;

    static float chipGenerationLine;
    static int successiveMinesGenerated;

    // 初期化
    public static void init(float line) {
        chipGenerationLine = line;
        successiveMinesGenerated = 0;
    }

    // 生成
    public static void generate(MyGdxGame game) {
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
    private static void generateSpace(MyGdxGame game) {
        successiveMinesGenerated = 0;

        int spaceLength = MathUtils.random(3, 6);
        chipGenerationLine += game.chipSize * spaceLength;
    }

    // スコアアイテムの生成
    private static void generateChips(MyGdxGame game) {
        successiveMinesGenerated = 0;

        int chipType = MathUtils.random(0, 3);
        if (chipType == MyGdxGame.SCORE_ITEM_ONE) {
            boolean up = MathUtils.randomBoolean();
            for (int i = 0; i < 5; ++i) {
                float offsetY = up ? 2 - Math.abs(i - 2) : Math.abs(i - 2);
                Chip chip = new Chip(chipType, game.chipRegions[chipType], chipGenerationLine, 200 + offsetY * game.chipSize, game.chipSize, game.chipSize);
                game.chips.add(chip);
                chipGenerationLine += game.chipSize;
            }

            // 空白追加
            chipGenerationLine += game.chipSize;
        }
        else {
            float phaseShift = MathUtils.random();
            Chip chip = new Chip(chipType, game.chipRegions[chipType], chipGenerationLine, 200, game.chipSize, game.chipSize, phaseShift);
            game.chips.add(chip);
            chipGenerationLine += game.chipSize;

            // 空白追加
            chipGenerationLine += game.chipSize;
        }
    }

    // 障害物の生成
    private static void generateMines(MyGdxGame game) {
        ++successiveMinesGenerated;

        float phaseShift = MathUtils.random();
        Mine mine = new Mine(game.mineTexture, chipGenerationLine, Hero.HERO_FLOOR_Y, game.mineSize, 50.0f, phaseShift);
        game.mines.add(mine);
        chipGenerationLine += game.mineSize;

        // 空白追加
        chipGenerationLine += 3 * game.chipSize;
    }

}
