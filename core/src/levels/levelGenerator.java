package levels;

import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.scenes.scene2d.EventListener;
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
import levels.menu.testMainMenu;


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


    Window tower;
    testActor towerMenue;
    private String towerMenueIcon = "background/tower/menue/button_menu.png";

    //Building Ground
    testActor placeTower1;
    testActor placeTower2;
    testActor placeTower3;
    testActor placeTower4;
    testActor placeTower5;
    testActor placeTower6;
    testActor placeTower7;
    testActor placeTower8;
    testActor placeTower9;

    //Tower select
    testActor towerArcher;
    testActor towerMagician;
    testActor towerSupport;

    //Preplaced Tower
    testActor towerArcherPlaced1;
    testActor towerMagicianPlaced1;
    testActor towerSupportPlaced1;

    testActor towerArcherPlaced2;
    testActor towerMagicianPlaced2;
    testActor towerSupportPlaced2;

    testActor towerArcherPlaced3;
    testActor towerMagicianPlaced3;
    testActor towerSupportPlaced3;

    testActor towerArcherPlaced4;
    testActor towerMagicianPlaced4;
    testActor towerSupportPlaced4;

    testActor towerArcherPlaced5;
    testActor towerMagicianPlaced5;
    testActor towerSupportPlaced5;

    testActor towerArcherPlaced6;
    testActor towerMagicianPlaced6;
    testActor towerSupportPlaced6;

    testActor towerArcherPlaced7;
    testActor towerMagicianPlaced7;
    testActor towerSupportPlaced7;

    testActor towerArcherPlaced8;
    testActor towerMagicianPlaced8;
    testActor towerSupportPlaced8;

    testActor towerArcherPlaced9;
    testActor towerMagicianPlaced9;
    testActor towerSupportPlaced9;

    testActor towerArcherArray[];


    //ground texture
    private String groundStandard = "background/ground/dot.png";
    private String groundHighlighted = "background/ground/Hover.png";
    //tower
    private String towerArcherImg = "background/tower/archer.png";
    private String towerMagicianImg = "background/tower/magician.png";
    private String towerSupportImg = "background/tower/support.png";

    //Tower placement selector
    boolean archer = false;
    boolean magician = false;
    boolean support = false;

    //Tower placed control
    boolean archerPlaced1 = false;
    boolean magicianPlaced1 = false;
    boolean supportPlaced1 = false;



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


        tower = new Window("Choose a tower to place", skin);
        tower.setVisible(false);;
        TextButton continueButton2 = new TextButton("Continue the Game",skin);


        towerArcher = new testActor(towerArcherImg, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 80f, 70f);
        towerMagician = new testActor(towerMagicianImg, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 80f, 70f);
        towerSupport = new testActor(towerSupportImg, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 80f, 70f);

        tower.padTop(64);
        tower.setSize(stage.getWidth() * 0.5f, stage.getHeight() * 0.5f);
        tower.setPosition(stage.getWidth() / 2 - tower.getWidth() / 2, stage.getHeight() / 2 - tower.getHeight() / 2);

        tower.add(towerArcher);
        tower.add(towerMagician);
        tower.add(towerSupport).row();
        tower.add(continueButton2);
        Gdx.input.setInputProcessor(stage);

        //MenueIcon
        towerMenue = new testActor(towerMenueIcon, Gdx.graphics.getWidth()/100*12f, Gdx.graphics.getHeight()/100*89f, 90f, 90f);


        for (int i = 0; i <= 1; i++) {
            towerArcherArray[i] = new testActor(groundHighlighted, Gdx.graphics.getWidth()*0.035f, Gdx.graphics.getHeight()*0.033f, 123.5f, 70f);
        }

        //Ground
        placeTower1 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.035f, Gdx.graphics.getHeight()*0.033f, 123.5f, 70f);
        placeTower2 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.246f, Gdx.graphics.getHeight()*0.033f, 123.5f, 70f);
        placeTower3 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.636f, Gdx.graphics.getHeight()*0.033f, 123.5f, 70f);
        placeTower4 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.845f, Gdx.graphics.getHeight()*0.033f, 123.5f, 70f);
        placeTower5 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.379f, Gdx.graphics.getHeight()*0.338f, 124.5f, 70f);
        placeTower6 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.578f, Gdx.graphics.getHeight()*0.300f, 123.5f, 70f);
        placeTower7 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.498f, Gdx.graphics.getHeight()*0.546f, 123.5f, 70f);
        placeTower8 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.691f, Gdx.graphics.getHeight()*0.590f, 123.5f, 70f);
        placeTower9 = new testActor(groundStandard, Gdx.graphics.getWidth()*0.864f, Gdx.graphics.getHeight()*0.513f, 123.5f, 70f);


        towerArcherPlaced1 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.055f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerArcherPlaced1.setVisible(false);
        towerMagicianPlaced1 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.055f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerMagicianPlaced1.setVisible(false);
        towerSupportPlaced1 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.055f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerSupportPlaced1.setVisible(false);

        towerArcherPlaced2 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.266f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerArcherPlaced2.setVisible(false);
        towerMagicianPlaced2 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.266f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerMagicianPlaced2.setVisible(false);
        towerSupportPlaced2 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.266f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerSupportPlaced2.setVisible(false);

        towerArcherPlaced3 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.656f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerArcherPlaced3.setVisible(false);
        towerMagicianPlaced3 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.656f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerMagicianPlaced3.setVisible(false);
        towerSupportPlaced3 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.656f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerSupportPlaced3.setVisible(false);

        towerArcherPlaced4 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.865f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerArcherPlaced4.setVisible(false);
        towerMagicianPlaced4 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.865f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerMagicianPlaced4.setVisible(false);
        towerSupportPlaced4 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.865f, Gdx.graphics.getHeight()*0.053f, 80f, 70f);
        towerSupportPlaced4.setVisible(false);

        towerArcherPlaced5 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.399f, Gdx.graphics.getHeight()*0.358f, 80f, 70f);
        towerArcherPlaced5.setVisible(false);
        towerMagicianPlaced5 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.399f, Gdx.graphics.getHeight()*0.358f, 80f, 70f);
        towerMagicianPlaced5.setVisible(false);
        towerSupportPlaced5 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.399f, Gdx.graphics.getHeight()*0.358f, 80f, 70f);
        towerSupportPlaced5.setVisible(false);

        towerArcherPlaced6 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.598f, Gdx.graphics.getHeight()*0.320f, 80f, 70f);
        towerArcherPlaced6.setVisible(false);
        towerMagicianPlaced6 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.598f, Gdx.graphics.getHeight()*0.320f, 80f, 70f);
        towerMagicianPlaced6.setVisible(false);
        towerSupportPlaced6 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.598f, Gdx.graphics.getHeight()*0.320f, 80f, 70f);
        towerSupportPlaced6.setVisible(false);

        towerArcherPlaced7 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.518f, Gdx.graphics.getHeight()*0.566f, 80f, 70f);
        towerArcherPlaced7.setVisible(false);
        towerMagicianPlaced7 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.518f, Gdx.graphics.getHeight()*0.566f, 80f, 70f);
        towerMagicianPlaced7.setVisible(false);
        towerSupportPlaced7 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.518f, Gdx.graphics.getHeight()*0.566f, 80f, 70f);
        towerSupportPlaced7.setVisible(false);

        towerArcherPlaced8 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.711f, Gdx.graphics.getHeight()*0.610f, 80f, 70f);
        towerArcherPlaced8.setVisible(false);
        towerMagicianPlaced8 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.711f, Gdx.graphics.getHeight()*0.610f, 80f, 70f);
        towerMagicianPlaced8.setVisible(false);
        towerSupportPlaced8 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.711f, Gdx.graphics.getHeight()*0.610f, 80f, 70f);
        towerSupportPlaced8.setVisible(false);

        towerArcherPlaced9 = new testActor(towerArcherImg, Gdx.graphics.getWidth()*0.884f, Gdx.graphics.getHeight()*0.533f, 80f, 70f);
        towerArcherPlaced9.setVisible(false);
        towerMagicianPlaced9 = new testActor(towerMagicianImg, Gdx.graphics.getWidth()*0.884f, Gdx.graphics.getHeight()*0.533f, 80f, 70f);
        towerMagicianPlaced9.setVisible(false);
        towerSupportPlaced9 = new testActor(towerSupportImg, Gdx.graphics.getWidth()*0.884f, Gdx.graphics.getHeight()*0.533f, 80f, 70f);
        towerSupportPlaced9.setVisible(false);


        //Tower Menue
        towerMenue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tower.setVisible(true);
            }
        });


        //EventHandler --- tower menue
        towerArcher.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                archer = true;
                magician = false;
                support = false;
                tower.setVisible(false);
            }
        });
        towerMagician.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                archer = false;
                magician = true;
                support = false;
                tower.setVisible(false);
            }
        });
        towerSupport.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                archer = false;
                magician = false;
                support = true;
                tower.setVisible(false);
            }
        });


        //Event Handler placeTower
        placeTower1.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               if (archer) {
                   towerArcherPlaced1.setVisible(true);
               }
               if (magician) {
                   towerMagicianPlaced1.setVisible(true);
               }
               if (support) {
                   towerSupportPlaced1.setVisible(true);
               }
           }
        });
        placeTower1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced1.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced1.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced1.setVisible(true);
                    support = false;
                }
            }
        });
        placeTower2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced2.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced2.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced2.setVisible(true);
                    support = false;
                }
            }
        });
        placeTower3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced3.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced3.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced3.setVisible(true);
                    support = false;
                }
            }
        });
        placeTower4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced4.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced4.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced4.setVisible(true);
                    support = false;
                }
            }
        });
        placeTower5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced5.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced5.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced5.setVisible(true);
                    support = false;
                }
            }
        });
        placeTower6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced6.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced6.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced6.setVisible(true);
                    support = false;
                }
            }
        });
        placeTower7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced7.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced7.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced7.setVisible(true);
                    support = false;
                }
            }
        });
        placeTower8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced8.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced8.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced8.setVisible(true);
                    support = false;
                }
            }
        });
        placeTower9.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    towerArcherPlaced9.setVisible(true);
                    archer = false;
                }
                if (magician) {
                    towerMagicianPlaced9.setVisible(true);
                    magician = false;
                }
                if (support) {
                    towerSupportPlaced9.setVisible(true);
                    support = false;
                }
            }
        });



        batch = new SpriteBatch();
        level = new LevelOne();

        level.createBackground();
        stage.addActor(pauseButtonActor);

        stage.addActor(towerMenue);

        for (int i = 0; i <= 9; i++) {
            stage.addActor(towerArcherArray[i]);
        }
        //Building Places
        stage.addActor(placeTower1);
        stage.addActor(placeTower2);
        stage.addActor(placeTower3);
        stage.addActor(placeTower4);
        stage.addActor(placeTower5);
        stage.addActor(placeTower6);
        stage.addActor(placeTower7);
        stage.addActor(placeTower8);
        stage.addActor(placeTower9);

        //Preplaced Towers
        stage.addActor(towerArcherPlaced1);
        stage.addActor(towerMagicianPlaced1);
        stage.addActor(towerSupportPlaced1);

        stage.addActor(towerArcherPlaced2);
        stage.addActor(towerMagicianPlaced2);
        stage.addActor(towerSupportPlaced2);

        stage.addActor(towerArcherPlaced3);
        stage.addActor(towerMagicianPlaced3);
        stage.addActor(towerSupportPlaced3);

        stage.addActor(towerArcherPlaced4);
        stage.addActor(towerMagicianPlaced4);
        stage.addActor(towerSupportPlaced4);

        stage.addActor(towerArcherPlaced5);
        stage.addActor(towerMagicianPlaced5);
        stage.addActor(towerSupportPlaced5);

        stage.addActor(towerArcherPlaced6);
        stage.addActor(towerMagicianPlaced6);
        stage.addActor(towerSupportPlaced6);

        stage.addActor(towerArcherPlaced7);
        stage.addActor(towerMagicianPlaced7);
        stage.addActor(towerSupportPlaced7);

        stage.addActor(towerArcherPlaced8);
        stage.addActor(towerMagicianPlaced8);
        stage.addActor(towerSupportPlaced8);

        stage.addActor(towerArcherPlaced9);
        stage.addActor(towerMagicianPlaced9);
        stage.addActor(towerSupportPlaced9);

        stage.addActor(pause);
        stage.addActor(tower);

        this.createAllEnemies();
        this.setUpEnemies();


        //scorpionAtlas = new TextureAtlas((FileHandle) scorpion.returnPath());
        //animation = new Animation(1/30f, scorpionAtlas.getRegions());

        //scorpionEnemy.setPosition(-100, 150);


    }

    @Override
    public void render(float delta) {
        batch.begin();
        if (!isPaused){
        }


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }
    public void createAllEnemies(){
        scorpion = new Scorpion();
        wizard = new Wizard();
    }

    public void updateAllEntites(){
        scorpionEnemy.update(batch);
        wizardEnemy.update(batch);
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


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        dispose();
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
