package levels;

import MainRef.TowerDefense;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.math.Vector2;
import enemy.scorpionEntity.Scorpion;
import java.util.Timer;
import java.util.TimerTask;



public class levelGenerator implements Screen {
    final TowerDefense game;
    SpriteBatch batch;
    LevelOne level;
    PathfindingEnemy scorpionEnemy;
    Scorpion scorpion;
    private float timePassed;


    public levelGenerator(final TowerDefense game) {
        this.game = game;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        level = new LevelOne();
        level.createBackground();

        scorpion = new Scorpion();
        //scorpionAtlas = new TextureAtlas((FileHandle) scorpion.returnPath());
        //animation = new Animation(1/30f, scorpionAtlas.getRegions());

        scorpionEnemy = new PathfindingEnemy(scorpion.idleFrame(), LevelOne.levelOnePath());


        //scorpionEnemy.setPosition(-100, 150);
    }

    @Override
    public void render(float delta) {
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
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose(){
        batch.dispose();
        scorpion.getStage().dispose();
        scorpionEnemy.getTexture().dispose();
        level.dispose();

    }
}
