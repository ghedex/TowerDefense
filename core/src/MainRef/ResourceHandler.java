package MainRef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class ResourceHandler {
    private HashMap<String, Music> music;
    private HashMap<String, Sound> sound;

    public ResourceHandler(){
        music = new HashMap<String, Music>();
        sound = new HashMap<String, Sound>();
    }

    public void loadMusic(String path, String key){
        Music musicfile = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.put(key, musicfile);
    }

    public void loadSound(String path, String key){
        Sound soundfile = Gdx.audio.newSound(Gdx.files.internal(path));
        sound.put(key, soundfile);
    }

    public Music getMusic(String key){
        return music.get(key);
    }

    public Sound getSound(String key){
        return sound.get(key);
    }
}
