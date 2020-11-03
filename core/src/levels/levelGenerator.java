package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enemy.Scorpion.Scorpion;



public class levelGenerator extends ApplicationAdapter {
    private final String level;

    SpriteBatch batch;
    Texture img;
    Texture scorp;
    public levelGenerator(String level){
        this.level = level;
    }

    Scorpion enemy = new Scorpion();
    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture(level);
        scorp = new Texture(enemy.entityFile);

    }
    @Override
    public void render () {
        batch.begin();
        //last both params allow to scale image to window size
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(scorp, 0,125, 90, 90); //need to create algorithm to compute size and position
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        scorp.dispose();
    }
}
