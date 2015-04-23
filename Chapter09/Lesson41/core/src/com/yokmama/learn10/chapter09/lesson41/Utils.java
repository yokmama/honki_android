package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by maciek on 1/28/15.
 */
public class Utils {
    public static Array<TextureRegion> getFrames(Texture texture, int offsetY, int frames, int frameWidth, int frameHeight) {
        Array<TextureRegion> textures = new Array<TextureRegion>();
        for (int i = 0; i < frames; ++i) {
            TextureRegion region = new TextureRegion(texture, i * frameWidth, offsetY, frameWidth, frameHeight);
            textures.add(region);
        }
        return textures;
    }
}
