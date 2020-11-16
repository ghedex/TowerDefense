package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

import enemy.Entity;
import enemy.scorpionEntity.Scorpion;
import com.badlogic.gdx.utils.Array;




public class levelGenerator extends ApplicationAdapter {


    private final String level;
    SpriteBatch batch;
    Texture img;
    Scorpion scorpion;
    private Array<AISprite> aiSprites;
    private CatmullRomSpline<Vector2> enemyPath;

    public levelGenerator(String level){
        this.level = level;
    }

    @Override
    public void create () {

        Gdx.graphics.setTitle("Tower Defense Game");

        img = new Texture(level);
        enemyPath = new CatmullRomSpline<Vector2>(new Vector2[] {
                new Vector2(0, 150),
                new Vector2(250, 150),
                new Vector2(360, 175),
                new Vector2(410, 225),
                new Vector2(440, 300),
                new Vector2(500, 360),
                new Vector2(525, 375),
                new Vector2(625, 350),
                new Vector2(700, 325),
                new Vector2(750, 325),
                new Vector2(800, 375),
                new Vector2(850, 500),
                new Vector2(900, 550),
                new Vector2(1000, 550),
                new Vector2(1100, 550),
                new Vector2(1200, 500),
                new Vector2(1400, 500)

        }, true);



        scorpion = new Scorpion();
        scorpion.createImage();
        aiSprites = new Array<AISprite>();
        aiSprites.add(new AISprite(scorpion, LevelOne.levelOnePath()));

    }

    public void render () {
        batch = new SpriteBatch();

        //last both params allow to scale image to window size
        //just the level image

        //need to end the batch before drawing another Sprite, otherwise it'll cause a nullpointer
        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        batch.begin();

        scorpion.renderImage(); // TO DO
        batch.end();

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        scorpion.disposeImage();

    }
}
