package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import enemy.scorpionEntity.Scorpion;
import enemy.wizardEntity.Wizard;


public class levelGenerator extends ApplicationAdapter {
    SpriteBatch batch;

    LevelOne level;
    PathfindingEnemy scorpionEnemy;
    PathfindingEnemy wizardEnemy;
    Scorpion scorpion;
    Wizard wizard;

    public levelGenerator() {
    }

    /*
    To create new enemies edit the following functions:
    1. createAllEnemies
    2. setUpEnemies (for pathfinding)

     */


    @Override
    public void create(){
        batch = new SpriteBatch();
        level = new LevelOne();
        //load all Enemies
        this.createAllEnemies();
        //creates the first level background
        level.createBackground();
        //edit setUpEnemies to create new ones
        this.setUpEnemies();


    }
    @Override
    public void render(){
        batch.begin();

        level.renderBackground();
        this.updateAllEntites();

        batch.end();

    }
    public void createAllEnemies(){
        scorpion = new Scorpion();
        wizard = new Wizard();
    }

    public void setUpEnemies(){
        //velocity not quite working yet, origin too
        scorpionEnemy = new PathfindingEnemy(scorpion.idleFrame(), LevelOne.levelOnePath());
        scorpionEnemy.setOrigin(-150, 150);
        scorpionEnemy.setSize(scorpion.getWIDTH(), scorpion.getHEIGHT());
        //velocity not quite working yet, origin too
        wizardEnemy = new PathfindingEnemy(wizard.idleFrame(), LevelOne.levelOnePath());
        wizardEnemy.setOrigin(-150, 150);
        wizardEnemy.setSize(wizard.getWIDTH(), wizard.getHEIGHT());
    }

    public void updateAllEntites(){
        scorpionEnemy.update(batch);
        wizardEnemy.update(batch);
    }


    @Override
    public void dispose(){
        batch.dispose();
        scorpion.getStage().dispose();
        scorpionEnemy.getTexture().dispose();
        wizard.getStage().dispose();
        wizardEnemy.getTexture().dispose();
        level.dispose();

    }





}
