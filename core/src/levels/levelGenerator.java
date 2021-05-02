package levels;

import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import abilities.Ability;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import enemy.scorpionEntity.Scorpion;
import enemy.wizardEntity.Wizard;
import levels.menu.testActor;

import levels.menu.MainMenuScreen;

import java.util.LinkedList;
import java.util.*;


public class levelGenerator implements Screen {
    final TowerDefense game;
    testActor pauseButtonActor, abilityButtonActor, spawnButtonActor;
    Window pause, abilityList;
    Stage stage;
    TooltipManager toolTipManager;
    ShapeRenderer shapeRenderer;
    ClickListener placementListener;
    private ResourceHandler resourceHandler = new ResourceHandler();
    SpriteBatch batch;
    LevelOne level;
    PathfindingEnemy scorpionEnemy, wizardEnemy, fireBallAbility;
    Scorpion scorpion;
    Wizard wizard;
    Ability fireBall, damage = new Ability();
    private Array<Vector2> abilityPath;
    private boolean isPaused;
    private LinkedList<PathfindingEnemy> scorpionLinkedList;
    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer;
    private LinkedList<testActor> placeGroundList;
    //private LinkedList<testActor> prePlacedTowerList;

    Window tower;
    testActor towerMenue;
    private String towerMenueIcon = "background/tower/menue/button_menu.png";

    //Building Ground
    testActor ground1;
    testActor ground2;
    testActor ground3;
    testActor ground4;
    testActor ground5;
    testActor ground6;
    testActor ground7;
    testActor ground8;
    testActor ground9;

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

    boolean archerPlaced2 = false;
    boolean magicianPlaced2 = false;
    boolean supportPlaced2 = false;

    boolean archerPlaced3 = false;
    boolean magicianPlaced3 = false;
    boolean supportPlaced3 = false;

    boolean archerPlaced4 = false;
    boolean magicianPlaced4 = false;
    boolean supportPlaced4 = false;

    boolean archerPlaced5 = false;
    boolean magicianPlaced5 = false;
    boolean supportPlaced5 = false;

    boolean archerPlaced6 = false;
    boolean magicianPlaced6 = false;
    boolean supportPlaced6 = false;

    boolean archerPlaced7 = false;
    boolean magicianPlaced7 = false;
    boolean supportPlaced7 = false;

    boolean archerPlaced8 = false;
    boolean magicianPlaced8 = false;
    boolean supportPlaced8 = false;

    boolean archerPlaced9 = false;
    boolean magicianPlaced9 = false;
    boolean supportPlaced9 = false;


    private boolean rangeCircle = false;
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/button_pause.png";
    private String abilityButton = "menuAssets/mainMenuAssets/buttonAssets/optiButton(FINAL_VERSION).png";
    private Skin uiSkin, fireAbilitySkin, fireBallSkin, windowSkin;
    private boolean towerIsPlaced;

    //TODO
    LinkedList<testActor> towerList = new LinkedList<>();
    LinkedList<PathfindingEnemy> enemyList = new LinkedList<>();
    Array<PathfindingEnemy> ability = new Array<>();
    ArrayList<ImageButton> abilityButtonArray = new ArrayList();
    private String fireAbilityToolTip = "Deals "+ damage.getFireDamage() + " Damage against 1 Enemy";
    private String thunderAbilityToolTip = "Deals "+ damage.getThunderDamage() + " Damage to all enemies";
    //TODO

    public levelGenerator(final TowerDefense game) {
        this.game = game;
    }
    //TODO Add Lightning Ability Image
    //TODO Add Lightning Ability Effect

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        toolTipManager = new TooltipManager();
        toolTipManager.initialTime = 0.0f;
        toolTipManager.resetTime = 0.0f;
        toolTipManager.subsequentTime = 0.0f;
        toolTipManager.hideAll();
        toolTipManager.instant();
        Gdx.input.setInputProcessor(stage);
        resourceHandler.loadSound("menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3", "buttonClickSound");
        uiSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));
        fireAbilitySkin = new Skin(Gdx.files.internal("abilities/abilitesSkin/fire/fireAbilitySkin.json"), new TextureAtlas("abilities/abilitesSkin/fire/fireAbilitySkin.atlas"));
        windowSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.atlas"));
        //----------------------------------------------------------PauseMenu------------------------------------------------------//
        //TODO outsource to a different file
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.background = windowSkin.getDrawable("default-window");
        pause = new Window("Pause", uiSkin);
        pause.setVisible(false);
        pause.padTop(64);
        pause.setBackground(windowStyle.background);
        //pause.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);
        pause.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
        //----------------------------------------------------------PauseMenuButtons------------------------------------------------------//
        TextButton continueButton = new TextButton("Continue the Game",uiSkin);
        TextButton exitButton = new TextButton("Exit to Main Menu", uiSkin);
        exitButton.setSize(250f,250f);
        pause.add(continueButton).row();
        pause.add(exitButton);
        pause.pack();
        //----------------------------------------------------------PauseMenuButtonFunctionality------------------------------------------------------//
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
        //--------------------------------------------------------AbilityMenu----------------------------------------------------//
        //TODO outsource to individual file
        abilityList = new Window("Abilities", uiSkin);
        abilityList.setVisible(false);
        abilityList.padBottom(5);
        abilityList.setPosition(stage.getWidth() / 2f, stage.getHeight());
        abilityList.setMovable(true);
        //--------------------------------------------------------AbilityMenuButtons----------------------------------------------------//
        ImageButtonStyle style = new ImageButtonStyle();
        style.imageUp = fireAbilitySkin.getDrawable("fire_up");
        style.imageOver = fireAbilitySkin.getDrawable("fire_over");
        style.imageChecked = fireAbilitySkin.getDrawable("fire_checked");
        final ImageButton fireAbility = new ImageButton(style);
        final ImageButton thunderAbility = new ImageButton(style);
        abilityButtonArray.add(fireAbility);
        abilityButtonArray.add(thunderAbility);
        //--------------------------------------------------------AbilityMenuButtonFunctionality----------------------------------------------------//
        //fireAbility.addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
        fireAbility.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                if(fireAbility.isChecked()){
                    rangeCircle = true;
                    stage.addListener(placementListener = new ClickListener(Input.Buttons.LEFT) {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(fireAbility.isChecked()) {
                                super.clicked(event, x, y);
                                createAbility();
                                setUpAbility(Gdx.input.getX() - fireBall.getWIDTH() / 2f, 720 - Gdx.input.getY() - fireBall.getHEIGHT() / 2f);
                                //Gdx.app.log("Mouse_X", String.valueOf(Gdx.input.getX()));
                                //Gdx.app.log("Mouse_Y", String.valueOf(Gdx.input.getY()));
                                Gdx.app.log("Ability", abilityButtonArray.get(0).toString());
                                fireAbility.setChecked(false);
                                stage.removeListener(placementListener);
                                rangeCircle = !rangeCircle;
                            }
                            else{
                                rangeCircle = !rangeCircle;
                                fireAbility.setChecked(false);
                                stage.removeListener(placementListener);
                            }
                        }
                    });
                }
            }
        });
        thunderAbility.addListener(new TextTooltip(thunderAbilityToolTip, toolTipManager, uiSkin));
        thunderAbility.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                if(thunderAbility.isChecked()){
                    dealThunderDamage();
                    thunderAbility.setChecked(false);
                }
            }
        });
        for (ImageButton imgButton : abilityButtonArray){
            abilityList.add(imgButton);
        }
        abilityList.pack();
        //----------------------------------------------------------GameplayButtons------------------------------------------------------//
        pauseButtonActor = new testActor(pauseButton, Gdx.graphics.getWidth()/100*0.5f, Gdx.graphics.getHeight()/100*89f, 90, 90);
        pauseButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                isPaused = !isPaused;
                pause.setVisible(!pause.isVisible());
            }
        });
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                tower.setVisible(false);
            }
        });
        //exitButton.addListener(new ClickListener(){}
        abilityButtonActor = new testActor(abilityButton, Gdx.graphics.getWidth()*0.10f, Gdx.graphics.getHeight()*0.90f, 125,50);
        abilityButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                abilityList.setVisible(!abilityList.isVisible());
            }
        });

        spawnButtonActor = new testActor(abilityButton, Gdx.graphics.getWidth()*0.20f, Gdx.graphics.getHeight()*0.80f, 125,50);
        spawnButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                damage.setThunderDamage(damage.getThunderDamage() + 5f);
                thunderAbilityToolTip = "Deals "+ damage.getThunderDamage() + " Damage to all enemies";
                Gdx.app.log("Thunder Damage", String.valueOf(damage.getThunderDamage()));
            }
        });


        tower = new Window("Choose a tower to place", uiSkin);
        tower.setVisible(false);;
        TextButton continueButton2 = new TextButton("Continue the Game",uiSkin);
        continueButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                tower.setVisible(!tower.isVisible());
            }
        });


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


        //Ground
        placeGroundList = new LinkedList<testActor>(Arrays.asList(
                ground1 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.035f, Gdx.graphics.getHeight() * 0.033f, 123.5f, 70f),
                ground2 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.246f, Gdx.graphics.getHeight() * 0.033f, 123.5f, 70f),
                ground3 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.636f, Gdx.graphics.getHeight() * 0.033f, 123.5f, 70f),
                ground4 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.845f, Gdx.graphics.getHeight() * 0.033f, 123.5f, 70f),
                ground5 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.379f, Gdx.graphics.getHeight() * 0.338f, 124.5f, 70f),
                ground6 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.578f, Gdx.graphics.getHeight() * 0.300f, 123.5f, 70f),
                ground7 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.498f, Gdx.graphics.getHeight() * 0.546f, 123.5f, 70f),
                ground8 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.691f, Gdx.graphics.getHeight() * 0.590f, 123.5f, 70f),
                ground9 = new testActor(groundStandard, Gdx.graphics.getWidth() * 0.864f, Gdx.graphics.getHeight() * 0.513f, 123.5f, 70f)
        ));
        //prePlacedTowerList = new LinkedList<testActor>();





        towerList = new LinkedList<testActor>();





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
        setUpEventListener(towerArcher, true, false, false);
        setUpEventListener(towerMagician, false, true, false);
        setUpEventListener(towerSupport, false, false, true);


        /*
        Ideas for tower placement:
        -alle drei möglichen Towerarten müssen in einer LinkedList gespeichert sein
        -alle Böden sollen in einer Linkedlist gespeichert sein (vordefinierte Plätze usw)
            -über die Böden soll eine Box2D gesetzt werden (fürs Klicken)
                wenn !towerIsPlaced:
                -Placement: Auswahl Tower -> Klick auf Fläche -> Box2D berechnet width/2 und height/2 fürs zentrale placement
                    -towerIsPlaced = true

         */

        for(testActor tA: placeGroundList){
            tA.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (archer) {
                        if (!archerPlaced3 && !magicianPlaced3 && !supportPlaced3 ) {
                            towerArcherPlaced3.setVisible(true);
                            archer = false;
                            archerPlaced1 = true;
                        }
                    }
                    if (magician) {
                        if (!archerPlaced1 && !magicianPlaced1 && !supportPlaced1 ) {
                            towerMagicianPlaced1.setVisible(true);
                            magician = false;
                            magicianPlaced1 = true;
                        }
                    }
                    if (support) {
                        if (!archerPlaced1 && !magicianPlaced1 && !supportPlaced1 ) {
                            towerSupportPlaced1.setVisible(true);
                            support = false;
                            supportPlaced1 = true;
                        }
                    }


                    /*
                    wenn click an ground intersects box2d of ground && !isTowerPlaced:
                        setze Tower an stelle
                        isTowerPlaced = true;


                     */



                }
            });
        }




        //Event Handler placeTower
        /*
        ground1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced1 && !magicianPlaced1 && !supportPlaced1 ) {
                        towerArcherPlaced1.setVisible(true);
                        archer = false;
                        archerPlaced1 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced1 && !magicianPlaced1 && !supportPlaced1 ) {
                        towerMagicianPlaced1.setVisible(true);
                        magician = false;
                        magicianPlaced1 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced1 && !magicianPlaced1 && !supportPlaced1 ) {
                        towerSupportPlaced1.setVisible(true);
                        support = false;
                        supportPlaced1 = true;
                    }
                }
            }
        });
        ground2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced2 && !magicianPlaced2 && !supportPlaced2 ) {
                        towerArcherPlaced2.setVisible(true);
                        archer = false;
                        archerPlaced2 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced2 && !magicianPlaced2 && !supportPlaced2 ) {
                        towerMagicianPlaced2.setVisible(true);
                        magician = false;
                        magicianPlaced2 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced2 && !magicianPlaced2 && !supportPlaced2 ) {
                        towerSupportPlaced2.setVisible(true);
                        support = false;
                        supportPlaced2 = true;
                    }
                }
            }
        });
        ground3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced3 && !magicianPlaced3 && !supportPlaced3 ) {
                        towerArcherPlaced3.setVisible(true);
                        archer = false;
                        archerPlaced3 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced3 && !magicianPlaced3 && !supportPlaced3 ) {
                        towerMagicianPlaced3.setVisible(true);
                        magician = false;
                        magicianPlaced3 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced3 && !magicianPlaced3 && !supportPlaced3 ) {
                        towerSupportPlaced3.setVisible(true);
                        support = false;
                        supportPlaced3 = true;
                    }
                }
            }
        });
        ground4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced4 && !magicianPlaced4 && !supportPlaced4 ) {
                        towerArcherPlaced4.setVisible(true);
                        archer = false;
                        archerPlaced4 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced4 && !magicianPlaced4 && !supportPlaced4 ) {
                        towerMagicianPlaced4.setVisible(true);
                        magician = false;
                        magicianPlaced4 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced4 && !magicianPlaced4 && !supportPlaced4 ) {
                        towerSupportPlaced4.setVisible(true);
                        support = false;
                        supportPlaced4 = true;
                    }
                }
            }
        });
        ground5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced5 && !magicianPlaced5 && !supportPlaced5 ) {
                        towerArcherPlaced5.setVisible(true);
                        archer = false;
                        archerPlaced5 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced5 && !magicianPlaced5 && !supportPlaced5 ) {
                        towerMagicianPlaced5.setVisible(true);
                        magician = false;
                        magicianPlaced5 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced5 && !magicianPlaced5 && !supportPlaced5 ) {
                        towerSupportPlaced5.setVisible(true);
                        support = false;
                        supportPlaced5 = true;
                    }
                }
            }
        });
        ground6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced6 && !magicianPlaced6 && !supportPlaced6 ) {
                        towerArcherPlaced6.setVisible(true);
                        archer = false;
                        archerPlaced6 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced6 && !magicianPlaced6 && !supportPlaced6 ) {
                        towerMagicianPlaced6.setVisible(true);
                        magician = false;
                        magicianPlaced6 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced6 && !magicianPlaced6 && !supportPlaced6 ) {
                        towerSupportPlaced6.setVisible(true);
                        support = false;
                        supportPlaced6 = true;
                    }
                }
            }
        });
        ground7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced7 && !magicianPlaced7 && !supportPlaced7 ) {
                        towerArcherPlaced7.setVisible(true);
                        archer = false;
                        archerPlaced7 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced7 && !magicianPlaced7 && !supportPlaced7 ) {
                        towerMagicianPlaced7.setVisible(true);
                        magician = false;
                        magicianPlaced7 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced7 && !magicianPlaced7 && !supportPlaced7 ) {
                        towerSupportPlaced7.setVisible(true);
                        support = false;
                        supportPlaced7 = true;
                    }
                }
            }
        });
        ground8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced8 && !magicianPlaced8 && !supportPlaced8 ) {
                        towerArcherPlaced8.setVisible(true);
                        archer = false;
                        archerPlaced8 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced8 && !magicianPlaced8 && !supportPlaced8 ) {
                        towerMagicianPlaced8.setVisible(true);
                        magician = false;
                        magicianPlaced8 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced8 && !magicianPlaced8 && !supportPlaced8 ) {
                        towerSupportPlaced8.setVisible(true);
                        support = false;
                        supportPlaced8 = true;
                    }
                }
            }
        });
        ground9.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (archer) {
                    if (!archerPlaced9 && !magicianPlaced9 && !supportPlaced9 ) {
                        towerArcherPlaced9.setVisible(true);
                        archer = false;
                        archerPlaced9 = true;
                    }
                }
                if (magician) {
                    if (!archerPlaced9 && !magicianPlaced9 && !supportPlaced9 ) {
                        towerMagicianPlaced9.setVisible(true);
                        magician = false;
                        magicianPlaced9 = true;
                    }
                }
                if (support) {
                    if (!archerPlaced9 && !magicianPlaced9 && !supportPlaced9 ) {
                        towerSupportPlaced9.setVisible(true);
                        support = false;
                        supportPlaced9 = true;
                    }
                }
            }
        });


         */


        batch = new SpriteBatch();
        level = new LevelOne();
        level.createBackground();
        stage.addActor(pauseButtonActor);

        stage.addActor(towerMenue);

        //Building Places
        addBuildingPlacesToStage();

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

        stage.addActor(abilityButtonActor);
        stage.addActor(spawnButtonActor);
        stage.addActor(pause);
        stage.addActor(abilityList);
        stage.addActor(tower);

        /*
        for(testActor prePlacedTower: prePlacedTowerList){
            stage.addActor(prePlacedTower);
        }

         */

        scorpionLinkedList = new LinkedList<>();
        createAllEnemies();


    }



    @Override
    public void render(float delta) {
        level.renderBackground();
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
        updateToolTips();

        batch.begin();
        if (!isPaused){
            spawnEnemyScorpions(Gdx.graphics.getDeltaTime());
            updateAllEntities();
            makeEnemiesMove(delta);
            checkFireAbilityCollision();

        }
        drawAllEntites();

        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if(rangeCircle){
            drawCircle();
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    public void addBuildingPlacesToStage(){
        for(testActor toPlaceTower: placeGroundList){
            stage.addActor(toPlaceTower);
        }
    }

    public void setUpEventListener(testActor towerEventListener, final boolean archerBoolean, final boolean magicianBoolean, final boolean supportBoolean){
        towerEventListener.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                archer = archerBoolean;
                magician = magicianBoolean;
                support = supportBoolean;
                tower.setVisible(false);
            }
        });
    }


    //
    public void updateToolTips(){
        fireAbilityToolTip = "Deals "+ damage.getFireDamage() + " Damage against 1 Enemy";
        thunderAbilityToolTip = "Deals "+ damage.getThunderDamage() + " Damage to all enemies";
        abilityButtonArray.get(0).addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(0).addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(1).addListener(new TextTooltip(thunderAbilityToolTip, toolTipManager, uiSkin));
    }
    public void checkFireAbilityCollision(){
        if(!(fireBallAbility == null)) {
            Iterator<PathfindingEnemy> abilityIterator = ability.iterator();
            //Gdx.app.log("Array Index",ability.toString());
            for (Iterator<PathfindingEnemy> iterator = scorpionLinkedList.iterator(); iterator.hasNext(); ) {
                if(abilityIterator.hasNext()) {
                    PathfindingEnemy enemy = iterator.next();
                    if (enemy.getBoundingRectangle().overlaps(ability.get(0).getBoundingRectangle())) {
                        enemy.setLifeCount(enemy.getLifeCount() - damage.getFireDamage());
                        enemy.timeOfDmgTaken = enemy.timeAlive;
                        Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                        ability.removeValue(ability.get(0),true);
                    }
                    if (enemy.getLifeCount() <= 0) {
                        iterator.remove();
                    }
                }
                else{
                    break;
                }
            }
        }
    }
    public void dealThunderDamage(){
        for (Iterator<PathfindingEnemy> iterator = scorpionLinkedList.iterator(); iterator.hasNext(); ) {
                PathfindingEnemy enemy = iterator.next();
                enemy.setLifeCount(enemy.getLifeCount() - damage.getThunderDamage());
                enemy.timeOfDmgTaken = enemy.timeAlive;
                Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                if (enemy.getLifeCount() <= 0) {
                    iterator.remove();
                }
            }
        }

    public void drawCircle(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,0.5f);
        shapeRenderer.circle(Gdx.input.getX(), 720 - Gdx.input.getY(), 50f);
        shapeRenderer.end();
    }
    //TODO outsource Abilities to their own files
    public void createAbility(){
        for(ImageButton imageButton: abilityButtonArray){
            if(imageButton.isChecked()){
                Gdx.app.log("Create Ability", String.valueOf(imageButton));
                fireBall = new Ability();
            }
        }
    }
    public Array<Vector2> abilityMovementPath(float x, float y){
        abilityPath = new Array<Vector2>();
        abilityPath.add(new Vector2(x, y));
        return abilityPath;
    }
    public void setUpAbility(float x, float y){
        ability = new Array<>();
        fireBallAbility = new PathfindingEnemy(fireBall.idleFrame(), abilityMovementPath(x, y));
        fireBallAbility.setPosition(Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.65f);
        ability.add(fireBallAbility);
    }
    public void createAllEnemies(){
        scorpion = new Scorpion();
        scorpion.setSize(90, 90);
        wizard = new Wizard();
    }
    public void drawAllEntites(){
        for(PathfindingEnemy drawEnemy: enemyList){
            drawEnemy.draw(batch);
        }
        for(PathfindingEnemy drawAbility: ability){
            drawAbility.draw(batch);
        }
    }
    public void updateAllEntities(){
        for(PathfindingEnemy updateAbility: ability){
            updateAbility.updateAbility();
        }
    }


    public void makeEnemiesMove(float delta){
        for(PathfindingEnemy s: scorpionLinkedList){
            s.preDraw();
            s.update(batch, LevelOne.levelOnePath(), delta);
            s.postDraw();


        }
    }


    public void spawnEnemyScorpions(float deltaTime){
        enemySpawnTimer += deltaTime;
        if(enemySpawnTimer > timeBetweenEnemySpawns){
            scorpionLinkedList.add(new PathfindingEnemy(scorpion.idleFrame(), 20));
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
        wizard.getStage().dispose();
        wizardEnemy.getTexture().dispose();
        level.dispose();
    }
}