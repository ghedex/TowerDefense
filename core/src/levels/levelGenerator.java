package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.math.Vector2;
import enemy.scorpionEntity.Scorpion;
import java.util.Timer;
import java.util.TimerTask;


public class levelGenerator extends ApplicationAdapter {
    SpriteBatch batch;

    LevelOne level;
    PathfindingEnemy scorpionEnemy;
    Scorpion scorpion;
    private float timePassed;


    public levelGenerator() {
    }

    public void create(){
        batch = new SpriteBatch();
        level = new LevelOne();
        level.createBackground();

        scorpion = new Scorpion();
        //scorpionAtlas = new TextureAtlas((FileHandle) scorpion.returnPath());
        //animation = new Animation(1/30f, scorpionAtlas.getRegions());

        //scorpionEnemy = new PathfindingEnemy(scorpion.idleFrame(), LevelOne.levelOnePath());


        //scorpionEnemy.setPosition(-100, 150);


    }
    @Override
    public void render(){
        batch.begin();

        level.renderBackground();
        timePassed += Gdx.graphics.getDeltaTime();
        scorpionEnemy = new PathfindingEnemy(scorpion.idleFrame(), LevelOne.levelOnePath());
        // m = (y2 - y1) / (x2 - x1)
        //for(Vector2 i: scorpionEnemy.getPath()){
            //scorpionEnemy.setPosition(scorpionEnemy.getPath().first().y); }

        scorpionEnemy.setPosition();
        scorpionEnemy.update(batch, timePassed);



        batch.end();

    }



    @Override
    public void dispose(){
        batch.dispose();
        scorpion.getStage().dispose();
        scorpionEnemy.getTexture().dispose();
        level.dispose();

    }



}
