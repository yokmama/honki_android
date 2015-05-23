package com.yokmama.learn10.chapter09.lesson41;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

/**
 * 音を扱うクラス
 */
public class SoundManager implements Disposable {
    public Music music;
    public Sound collision;
    public Sound coin;
    public Sound finaleClaps;

    public SoundManager() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);

        collision = Gdx.audio.newSound(Gdx.files.internal("laser3.mp3"));
        coin = Gdx.audio.newSound(Gdx.files.internal("coin05.mp3"));
        finaleClaps = Gdx.audio.newSound(Gdx.files.internal("clapping.mp3"));
    }

    @Override
    public void dispose() {
        music.dispose();
        collision.dispose();
        coin.dispose();
        finaleClaps.dispose();
    }
}
