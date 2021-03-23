package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.*;

import enemy.scorpionEntity.Scorpion;
import java.util.Timer;
import java.util.TimerTask;


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
        scorpionAtlas = new TextureAtlas((FileHandle) scorpion.returnPath());
        animation = new Animation(1/30f, scorpionAtlas.getRegions());



        //scorpionEnemy.setPosition(-100, 150);


    }
    @Override
    public void render(){
        batch.begin();
        scorpionEnemy = new PathfindingEnemy(animation.getKeyFrame(timePassed, true), LevelOne.levelOnePath());
        level.renderBackground();
        timePassed += Gdx.graphics.getDeltaTime();


        scorpionEnemy.update(batch, timePassed);



        batch.end();

    }



    @Override
    public void dispose(){
        batch.dispose();
        scorpion.getStage().dispose();
        scorpionEnemy.getTexture().dispose();
        level.dispose();
        scorpionAtlas.dispose();
    }



}
