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
import enemy.scorpionEntity.Scorpion;
import enemy.wizardEntity.Wizard;
import levels.menu.testActor;
import levels.menu.MainMenuScreen;


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
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/button_pause.png";
    private Skin skin;
    Wizard wizard;


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
        pause.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);
        pause.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
        pause.add(continueButton).row();
        pause.add(exitButton);
        Gdx.input.setInputProcessor(stage);
        pauseButtonActor = new testActor(pauseButton, Gdx.graphics.getWidth()/100*0.5f, Gdx.graphics.getHeight()/100*89f, 90, 90);
        pauseButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                isPaused = !isPaused;
                if(pause.isVisible() == false) {
                    pause.setVisible(true);
                }
                else {
                    pause.setVisible(false);
                }
            }
        });
        continueButton.addListener(new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            isPaused = !isPaused;
            resourceHandler.getSound("buttonClickSound").play(0.5f);
            pause.setVisible(false);
        }
    });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
               game.setScreen(new MainMenuScreen(game));
            }
        });
        batch = new SpriteBatch();
        level = new LevelOne();

        level.createBackground();
        stage.addActor(pauseButtonActor);
        stage.addActor(pause);
        this.createAllEnemies();
        this.setUpEnemies();


        //scorpionAtlas = new TextureAtlas((FileHandle) scorpion.returnPath());
        //animation = new Animation(1/30f, scorpionAtlas.getRegions());

        //scorpionEnemy.setPosition(-100, 150);


    }

    @Override
    public void render(float delta) {
        batch.begin();
        level.renderBackground();
        if (!isPaused){
            this.updateAllEntites();
        }
        drawAllEntites();
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    public void createAllEnemies(){
        scorpion = new Scorpion();
        wizard = new Wizard();
    }
    public void drawAllEntites(){
        scorpionEnemy.draw(batch);
        wizardEnemy.draw(batch);
    }
    public void updateAllEntites(){
        scorpionEnemy.update();
        wizardEnemy.update();
    }
    public void setUpEnemies(){
        //velocity not quite working yet, origin too
        scorpionEnemy = new PathfindingEnemy(scorpion.idleFrame(), LevelOne.levelOnePath());
        //scorpionEnemy.setOrigin(-150, 150);
        scorpionEnemy.setPosition(-150, 150);
        scorpionEnemy.setSize(scorpion.getWIDTH(), scorpion.getHEIGHT());
        //velocity not quite working yet, origin too
        wizardEnemy = new PathfindingEnemy(wizard.idleFrame(), LevelOne.levelOnePath());
        //wizardEnemy.setOrigin(-150, 150);
        wizardEnemy.setPosition(-150, 150);
        wizardEnemy.setSize(wizard.getWIDTH(), wizard.getHEIGHT());
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
        wizard.getStage().dispose();
        wizardEnemy.getTexture().dispose();
        level.dispose();

    }
}
