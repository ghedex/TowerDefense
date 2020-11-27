package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import enemy.scorpionEntity.Scorpion;

import java.util.logging.Level;


public class levelGenerator extends ApplicationAdapter {
    SpriteBatch batch;
    Scorpion scorpion;
    LevelOne level;
    Vector2 position = new Vector2(0, 0);
    Vector2 size = new Vector2(95, 95);


    public levelGenerator() {
    }

    public void create(){
        batch = new SpriteBatch();
        level = new LevelOne();
        level.createBackground();
        scorpion = new Scorpion(position, size, "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas");
        scorpion.create();
    }
    @Override
    public void render(){
        batch.begin();
        level.renderBackground();
        scorpion.animate();
        batch.end();
    }

}
