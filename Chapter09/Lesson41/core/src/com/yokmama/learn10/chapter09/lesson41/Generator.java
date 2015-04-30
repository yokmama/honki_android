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

    public static void init(float line) {
        chipGenerationLine = line;
        successiveMinesGenerated = 0;
    }

    public static void generate(MyGdxGame game) {
        int generate = MathUtils.random(0, 2);
        if (generate == GENERATE_SPACE) {
            generateSpace(game);
        }
        else if (generate == GENERATE_CHIPS) {
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

    private static void generateSpace(MyGdxGame game) {
        successiveMinesGenerated = 0;

        int spaceLength = MathUtils.random(3, 6);
        chipGenerationLine += game.chipSize * spaceLength;
    }

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

            // Add space
            chipGenerationLine += game.chipSize;
        }
        else {
            float phaseShift = MathUtils.random();
            Chip chip = new Chip(chipType, game.chipRegions[chipType], chipGenerationLine, 200, game.chipSize, game.chipSize, phaseShift);
            game.chips.add(chip);
            chipGenerationLine += game.chipSize;

            // Add space
            chipGenerationLine += game.chipSize;
        }
    }

    private static void generateMines(MyGdxGame game) {
        ++successiveMinesGenerated;

        float phaseShift = MathUtils.random();
        Mine mine = new Mine(game.mineTexture, chipGenerationLine, Hero.HERO_FLOOR_Y, game.mineSize, 50.0f, phaseShift);
        game.mines.add(mine);
        chipGenerationLine += game.mineSize;

        // Add space
        chipGenerationLine += 3 * game.chipSize;
    }

}
