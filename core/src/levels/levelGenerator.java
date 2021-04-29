package levels;

import MainRef.ResourceHandler;
import MainRef.TowerDefense;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;


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
    Scorpion scorpion2;
    private float timePassed;
    private boolean isPaused;
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/pauseButton.png";
    private Skin skin;
    private LinkedList<Scorpion> scorpionLinkedList;
    Wizard wizard;
    Sprite a;
    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer;
    ShapeRenderer shapeRenderer;
    float speed = 0.15f;
    float current = 0;
    int k = 1000; //increase k for more fidelity to the spline
    Vector2[] points = new Vector2[k];
    private ShapeRenderer sr;
    int waypoint = 1;
    private Vector2 velocity = new Vector2();
    private float tolerance = 3;
    private CatmullRomSpline<Vector2> scorpionPath;
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
        shapeRenderer = new ShapeRenderer();


        scorpionPath = new CatmullRomSpline<Vector2>(new Vector2[] {
                new Vector2(0, -150),
                new Vector2(10, 150),
                new Vector2(250, 150),
                new Vector2(360, 175),
                new Vector2(410, 225),
                new Vector2(440, 300),
                new Vector2(500, 360),
//                new Vector2(525, 375),
                new Vector2(625, 350),
                new Vector2(700, 325),
                new Vector2(750, 325),
                new Vector2(800, 375),
                new Vector2(850, 500),
                new Vector2(900, 550),
                new Vector2(1000, 550),
                new Vector2(1100, 550),
                new Vector2(1200, 500),
                new Vector2(1400, 500),
        }, false);


        for(int i = 0; i < k; ++i)
        {
            points[i] = new Vector2();
            scorpionPath.valueAt(points[i], ((float)i)/((float)k-1));
        }
    }

    @Override
    public void render(float delta) {
        batch.begin();
        level.renderBackground();
        timePassed += Gdx.graphics.getDeltaTime();
        if (!isPaused){
            spawnEnemyScorpions(Gdx.graphics.getDeltaTime());
        }

        current += Gdx.graphics.getDeltaTime() * speed;
        if(current >= 1)
            current -= 1;
        float place = current * k;

        //scorpion.setFirst(points[(int)place]);
        if(((int)place+1) < k){
            //second = points[(int)place+1];
//            scorpion.setSecond(points[(int)place+1]);
        }
        else{
            //second = points[0];
//            scorpion.setSecond(points[0]);//or finish, in case it does not loop.
        }
        float t = place - ((int)place); //the decimal part of place

        stage.act(Gdx.graphics.getDeltaTime());



        stage.draw();

        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(int i = 0; i < k-1; ++i)
        {
            shapeRenderer.line(points[i], points[i+1]);
        }
        shapeRenderer.end();

         */

        /*
        Iterator<Scorpion> scorpionIterator = scorpionLinkedList.iterator();
        while(scorpionIterator.hasNext()){
            Scorpion scorpion = scorpionIterator.next();

            scorpion.draw(batch);
            //scorpion.setPosition(scorpion.getFirst().x + (scorpion.getSecond().x - scorpion.getFirst().x) * t, scorpion.getFirst().y + (scorpion.getSecond().y - scorpion.getFirst().y) * t);

        }


         */



        for(Scorpion s: scorpionLinkedList){

            //s.draw(batch, first.x + (second.x - first.x) * t, first.y + (second.y - first.y) * t);
            //batch.draw(s, scorpion.getFirst().x + (scorpion.getSecond().x - scorpion.getFirst().x) * t, scorpion.getFirst().y + (scorpion.getSecond().y - scorpion.getFirst().y) * t);

            s.setPosition(-150, 150);

            //s.setPosition(s.getFirst().x + (s.getSecond().x - s.getFirst().x) * t, s.getFirst().y + (s.getSecond().y - s.getFirst().y) * t);
            s.translate(1,1);

        }
        //System.out.println(scorpionLinkedList.toString());


        batch.end();
    }


    public void spawnEnemyScorpions(float deltaTime){
        enemySpawnTimer += deltaTime;
        if(enemySpawnTimer > timeBetweenEnemySpawns){
            scorpionLinkedList.add(new Scorpion());
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
        scorpion.getTexture().dispose();
        scorpionEnemy.getTexture().dispose();
        wizard.getTexture().dispose();
        wizardEnemy.getTexture().dispose();
        level.dispose();

    }
}
