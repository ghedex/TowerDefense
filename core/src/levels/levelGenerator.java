package levels;

import MainRef.ResourceHandler;
import MainRef.TowerDefense;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import enemy.wizardEntity.Wizard;
import enemy.scorpionEntity.Scorpion;

import levels.menu.testActor;
import levels.menu.testMainMenu;

import java.util.LinkedList;



public class levelGenerator implements Screen {
    final TowerDefense game;
    testActor pauseButtonActor;
    Window pause;
    Stage stage;
    private ResourceHandler resourceHandler = new ResourceHandler();
    SpriteBatch batch;
    LevelOne level;
    PathfindingEnemy scorpionEnemy;
    PathfindingEnemy wizardEnemy;
    Scorpion scorpion;
    private float timePassed;
    private boolean isPaused;
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/pauseButton.png";
    private Skin skin;
    private LinkedList<PathfindingEnemy> scorpionLinkedList;
    Wizard wizard;
    Sprite a;
    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer;
    public levelGenerator(final TowerDefense game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        resourceHandler.loadSound("menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3", "buttonClickSound");
        skin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));
        pause = new Window("Pause", skin);

        pause.setVisible(false);
        TextButton continueButton = new TextButton("Continue the Game",skin);
        TextButton exitButton = new TextButton("Exit to Main Menu", skin);
        exitButton.setSize(250f,250f);
        pause.padTop(64);
        pause.setSize(stage.getWidth() / 1.5f, stage.getHeight() / 1.5f);
        pause.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
        pause.add(continueButton).row();
        pause.add(exitButton);
        Gdx.input.setInputProcessor(stage);
        pauseButtonActor = new testActor(pauseButton, Gdx.graphics.getWidth()/100*0.5f, Gdx.graphics.getHeight()/100*93.5f);
        pauseButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                isPaused = !isPaused;
                pause.setVisible(true);
            }
        });
        continueButton.addListener(new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            resourceHandler.getSound("buttonClickSound").play(0.5f);
            pause.setVisible(false);
        }
    });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
               game.setScreen(new testMainMenu(game));
            }
        });
        batch = new SpriteBatch();
        level = new LevelOne();

        level.createBackground();
        stage.addActor(pauseButtonActor);
        stage.addActor(pause);

        scorpionLinkedList = new LinkedList<>();


        scorpion = new Scorpion();

        wizard = new Wizard();

    }

    @Override
    public void render(float delta) {
        batch.begin();
        level.renderBackground();
        timePassed += Gdx.graphics.getDeltaTime();
        if (!isPaused){
            spawnEnemyScorpions(Gdx.graphics.getDeltaTime());
        }
        stage.act(Gdx.graphics.getDeltaTime());

        stage.draw();

        makeEnemiesMove();

        batch.end();
    }

    public void makeEnemiesMove(){
        for(PathfindingEnemy s: scorpionLinkedList){
            s.update(batch, LevelOne.levelOnePath());
        }
    }


    public void spawnEnemyScorpions(float deltaTime){
        enemySpawnTimer += deltaTime;
        if(enemySpawnTimer > timeBetweenEnemySpawns){
            scorpionLinkedList.add(new PathfindingEnemy(scorpion.idleFrame()));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
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
        wizard.getTexture().dispose();
        wizardEnemy.getTexture().dispose();
        level.dispose();

    }
}
