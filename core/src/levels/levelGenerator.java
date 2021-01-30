package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.*;

import enemy.scorpionEntity.Scorpion;



public class levelGenerator extends ApplicationAdapter {
    SpriteBatch batch;

    LevelOne level;
    PathfindingEnemy scorpionEnemy;
    Scorpion scorpion;
    private float timePassed;
    private TextureAtlas scorpionAtlas;
    private Animation<TextureRegion> animation;


    public levelGenerator() {
    }

    public void create(){
        batch = new SpriteBatch();
        level = new LevelOne();
        level.createBackground();

        scorpion = new Scorpion();
        scorpionAtlas = new TextureAtlas(Gdx.files.internal("assetsPack/scorpions/scorpionRunning/scorpionPack.atlas"));
        animation = new Animation(1/30f, scorpionAtlas.getRegions());

        scorpionEnemy = new PathfindingEnemy(animation.getKeyFrame(timePassed), LevelOne.levelOnePath());

        scorpionEnemy.setSize(90, 90);
        scorpionEnemy.setPosition(-100, 150);

    }
    @Override
    public void render(){
        batch.begin();

        level.renderBackground();
        timePassed += Gdx.graphics.getDeltaTime();

        scorpionEnemy.update(batch, timePassed);
        batch.end();

    }



    @Override
    public void dispose(){
        batch.dispose();
        scorpion.getTexture().dispose();
        scorpionEnemy.getTexture().dispose();
        level.dispose();
        scorpionAtlas.dispose();
    }



}
