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
    Vector2 position = LevelOne.levelOnePath().first(); /* BUG FIXEN, POSITION FUNKTIONIERT NICHT */
    Vector2 size = new Vector2(95, 95);


    public levelGenerator() {
    }

    public void create(){
        batch = new SpriteBatch();
        level = new LevelOne();
        scorpion = new Scorpion(position, size, "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas");
        level.createBackground();
        scorpion.create();
    }
    @Override
    public void render(){
        batch.begin();
        level.renderBackground();
        scorpion.animate();
        batch.end();
    }

    /*
    Es muss das Movement eingefügt werden, also in etwa das was unter PathfindingEnemy zu finden ist
    Außerdem ist ein Zugriff auf den LevelOnePath notwendig.
    als Position wird der erste Vektor im levelOnepath() benötigt
     */

}
