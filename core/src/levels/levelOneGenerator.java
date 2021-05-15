package levels;

import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import abilities.Ability;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import enemy.bossSkeleton.TreeBoss;
import enemy.scorpionEntity.Scorpion;
import enemy.wizardEntity.Wizard;
import levels.menu.testActor;
import levels.menu.MainMenuScreen;
import java.util.LinkedList;
import java.util.*;

public class levelOneGenerator implements Screen {
    final TowerDefense game;
    testActor pauseButtonActor, abilityButtonActor, spawnButtonActor, towerMenueActor;
    Window pause, abilityList, tower, gameOverWindow;
    Stage stage;
    TooltipManager toolTipManager;
    ShapeRenderer shapeRenderer, towerAttackRange;
    LinkedList<Circle> towerAttackCircle;
    ClickListener placementListener, towerPlacementListener, towerListener;
    private ResourceHandler resourceHandler = new ResourceHandler();
    SpriteBatch batch;
    LevelOne level;
    PathfindingEnemy scorpionEnemy, wizardEnemy, fireBallAbility;
    Scorpion scorpion;
    Wizard wizard;
    TreeBoss treeBoss;
    Ability fireBall, damage = new Ability(), explosion;
    private Array<Vector2> abilityPath;
    private boolean isPaused;
    private boolean gameOver;
    private LinkedList<PathfindingEnemy> scorpionLinkedList;
    private LinkedList<PathfindingEnemy> wizardLinkedList;
    private float enemySpawnTimer, timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer2, timeBetweenEnemySpawns2 = 3f;
    private boolean rangeCircle = false;
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/button_pause.png";
    private String abilityButton = "core/assets/abilities/abilitesSkin/btton_abilities.png";
    private String upgradeAbilityButton = "core/assets/abilities/abilitesSkin/upgradeButton.png";
    private Skin uiSkin, fireAbilitySkin, thunderAbilitySkin, fireBallSkin, windowSkin, towerPlacementSkin, archerTowerSkin, supportTowerSkin, magicianTowerSkin;

    private boolean towerIsPlaced;
    private float coins = 1000;
    BitmapFont font12;
    //TODO
    LinkedList<ImageButton> towerList = new LinkedList<>();
    LinkedList<PathfindingEnemy> enemyList = new LinkedList<>();
    Array<PathfindingEnemy> ability = new Array<>();
    Array<ImageButton> abilityButtonArray = new Array();

    Array<ImageButton> upgradeAbilityButtonArray = new Array();
    private String fireAbilityToolTip = "Deals "+ damage.getFireDamage() + " Damage against 1 Enemy";
    private String thunderAbilityToolTip = "Deals "+ damage.getThunderDamage() + " Damage to all enemies";
    private String upgradeAbilityToolTip = "Upgrades every ability. Cost: 500 OptiCoins";
    private float health = 100;
    float[] towerLocation_x = {
            Gdx.graphics.getWidth() * 0.035f,
            Gdx.graphics.getWidth() * 0.246f,
            Gdx.graphics.getWidth() * 0.636f,
            Gdx.graphics.getWidth() * 0.845f,
            Gdx.graphics.getWidth() * 0.379f,
            Gdx.graphics.getWidth() * 0.578f,
            Gdx.graphics.getWidth() * 0.498f,
            Gdx.graphics.getWidth() * 0.691f,
            Gdx.graphics.getWidth() * 0.864f,
    };
    float[] towerLocation_y = {
            Gdx.graphics.getHeight() * 0.033f,
            Gdx.graphics.getHeight() * 0.033f,
            Gdx.graphics.getHeight() * 0.033f,
            Gdx.graphics.getHeight() * 0.033f,
            Gdx.graphics.getHeight() * 0.338f,
            Gdx.graphics.getHeight() * 0.300f,
            Gdx.graphics.getHeight() * 0.546f,
            Gdx.graphics.getHeight() * 0.590f,
            Gdx.graphics.getHeight() * 0.513f,
    };



    private static float FRAME_DURATION = 1 / 30f;
    private TextureAtlas runningAnimationAtlas = new TextureAtlas(Gdx.files.internal("assetsPack/scorpions/scorpionRunning/scorpionPack.atlas"));
    private TextureAtlas runningTreeAtlas = new TextureAtlas(Gdx.files.internal("core/assets/assetsPack/wizard/wizardRunning/wizardRunning.atlas"));
    private TextureAtlas explosionAtlas = new TextureAtlas(Gdx.files.internal("core/assets/abilities/abilitesSkin/explosion/explosion.atlas"));
    private TextureRegion currentFrame;
    private TextureRegion currentFrame2;
    private TextureRegion currentFrame3;
    private Animation runningAnimation;
    private Animation runningAnimationTree;
    private Animation explosionAbility;
    private float elapsed_time = 0f;
    Array<TextureAtlas.AtlasRegion> runningFrames = runningAnimationAtlas.findRegions("1_enemies_1_run");
    Array<TextureAtlas.AtlasRegion> runningFramesTree = runningTreeAtlas.findRegions("2_enemies_1_walk");
    Array<TextureAtlas.AtlasRegion> explosionFrames = explosionAtlas.findRegions("explosion");
    ArrayList<ImageButton> towers = new ArrayList<>();
    Array<Float> towerCircle_x = new Array<>();
    Array<Float> towerCircle_y = new Array<>();
    private ArrayList<Boolean> towerCircleBool;
    private BitmapFont font;

    public levelOneGenerator(final TowerDefense game) {
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
        resourceHandler.loadSound("core/assets/menuAssets/mainMenuAssets/music/gameOver.mp3", "gameOverSound");
        uiSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));
        fireAbilitySkin = new Skin(Gdx.files.internal("abilities/abilitesSkin/fire/fireAbilitySkin.json"), new TextureAtlas("abilities/abilitesSkin/fire/fireAbilitySkin.atlas"));
        archerTowerSkin = new Skin(Gdx.files.internal("core/assets/background/tower/towerImages/archerTower.json"), new TextureAtlas("core/assets/background/tower/towerImages/archerTower.atlas"));
        supportTowerSkin = new Skin(Gdx.files.internal("core/assets/background/tower/towerImages/supportTower.json"), new TextureAtlas("core/assets/background/tower/towerImages/supportTower.atlas"));
        magicianTowerSkin = new Skin(Gdx.files.internal("core/assets/background/tower/towerImages/magicianTower.json"), new TextureAtlas("core/assets/background/tower/towerImages/magicianTower.atlas"));
        thunderAbilitySkin = new Skin(Gdx.files.internal("core/assets/abilities/abilitesSkin/thunder/thunderAbilitySkin.json") ,new TextureAtlas("core/assets/abilities/abilitesSkin/thunder/thunderAbility.atlas"));
        //thunderAbilitySkin = new Skin(Gdx.files.internal("abilities/abilitesSkin/fire/fireAbilitySkin.json"), new TextureAtlas("abilities/abilitesSkin/fire/fireAbilitySkin.atlas"));
        windowSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.atlas"));
        towerPlacementSkin = new Skin(Gdx.files.internal("background/tower/locations/towerPlacement.json"), new TextureAtlas("background/tower/locations/towerPlacement.atlas"));
        Skin[] towerSkins = {
                archerTowerSkin, magicianTowerSkin, supportTowerSkin
        };
        //----------------------------------------------------------PauseMenu------------------------------------------------------//
        //TODO outsource to a different file
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.background = windowSkin.getDrawable("default-window");
        pause = new Window("Pause", uiSkin);
        pause.setVisible(false);
        pause.padTop(64);
        pause.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);
        pause.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
        //----------------------------------------------------------PauseMenuButtons------------------------------------------------------//
        TextButton continueButton = new TextButton("Continue the Game",uiSkin);
        TextButton exitButton = new TextButton("Exit to Main Menu", uiSkin);
        exitButton.setSize(250f,250f);
        pause.add(continueButton).row();
        pause.add(exitButton);
        pause.pack();

        gameOverWindow = new Window("Game Over", uiSkin);
        gameOverWindow.add(exitButton);
        gameOverWindow.pack();
        gameOverWindow.setVisible(true);
        gameOverWindow.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
        gameOverWindow.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);


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
        final ImageButtonStyle style = new ImageButtonStyle();
        final ImageButtonStyle style2 = new ImageButtonStyle();
        final ImageButtonStyle styleExplosionAbility = new ImageButtonStyle();
        final ImageButtonStyle styleTowerPlacementArcher = new ImageButtonStyle();
        final ImageButtonStyle styleTowerPlacementMagician = new ImageButtonStyle();
        final ImageButtonStyle styleTowerPlacementSupport = new ImageButtonStyle();
        style.imageUp = fireAbilitySkin.getDrawable("fire_up");
        style.imageOver = fireAbilitySkin.getDrawable("fire_over");
        style.imageChecked = fireAbilitySkin.getDrawable("fire_checked");

        styleTowerPlacementArcher.imageUp = archerTowerSkin.getDrawable("archerTower");
        styleTowerPlacementMagician.imageUp = magicianTowerSkin.getDrawable("magicianTower");
        styleTowerPlacementSupport.imageUp = supportTowerSkin.getDrawable("supportTower");

        style2.imageUp = thunderAbilitySkin.getDrawable("thunder_up");
        style2.imageOver = thunderAbilitySkin.getDrawable("thunder_over");

        styleExplosionAbility.imageUp = fireAbilitySkin.getDrawable("fire_up");



        //TODO

        styleTowerPlacementArcher.imageUp = archerTowerSkin.getDrawable("archerTower");
        styleTowerPlacementMagician.imageUp = magicianTowerSkin.getDrawable("magicianTower");
        styleTowerPlacementSupport.imageUp = supportTowerSkin.getDrawable("supportTower");
        //styleTowerPlacementMenu.imageUp = towerSkins.getDrawable("supportTower");
        /*
        styleTowerPlacementMenu.supportTower = towerSkins.getDrawable("supportTower");
        styleTowerPlacementMenu.magicianTower = towerSkins.getDrawable("magicianTower");


         */



        final ImageButton fireAbility = new ImageButton(style);
        final ImageButton thunderAbility = new ImageButton(style2);
        final ImageButton explosionAbilityArray = new ImageButton(styleExplosionAbility);
        final ImageButton towerPlacementArcher = new ImageButton(styleTowerPlacementArcher);
        final ImageButton towerPlacementMagician = new ImageButton(styleTowerPlacementMagician);
        final ImageButton towerPlacementSupport = new ImageButton(styleTowerPlacementSupport);

        abilityButtonArray.add(fireAbility);
        abilityButtonArray.add(thunderAbility);
        abilityButtonArray.add(explosionAbilityArray);
        upgradeAbilityButtonArray.add(towerPlacementArcher);
        upgradeAbilityButtonArray.add(towerPlacementMagician);
        upgradeAbilityButtonArray.add(towerPlacementSupport);

        //--------------------------------------------------------AbilityMenuButtonFunctionality----------------------------------------------------//
        fireAbility.addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
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
                            if(fireAbility.isChecked() && coins >= 50) {
                                super.clicked(event, x, y);
                                coins -= 50;

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
                if(thunderAbility.isChecked() && coins > 10){
                    coins -= 10;
                    Gdx.app.log("Monetas", "Moneten: " + coins);
                    dealThunderDamage();
                    Gdx.app.log("Ability", abilityButtonArray.get(1).toString());
                    thunderAbility.setChecked(false);
                }
            }
        });

        /*
        TODO
        -change Icon of ability
        -set Position of explosion on clicked coordinate

         */
        /*
        explosionAbilityArray.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                if(explosionAbilityArray.isChecked()){
                    rangeCircle = true;
                    stage.addListener(placementListener = new ClickListener(Input.Buttons.LEFT) {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(explosionAbilityArray.isChecked() && coins >= 50) {
                                super.clicked(event, x, y);
                                coins -= 50;

                                createAbility();
                                //createExplosionAbility();
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

         */




        for (ImageButton imgButton : abilityButtonArray){
            abilityList.add(imgButton);
        }

        abilityList.pack();
        //----------------------------------------------------------GameplayButtons------------------------------------------------------//
        pauseButtonActor = new testActor(pauseButton, Gdx.graphics.getWidth()/100*1f, Gdx.graphics.getHeight()/100*89f, 90, 90);
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
        //exitButton.addListener(new ClickListener(){});
        abilityButtonActor = new testActor(abilityButton, Gdx.graphics.getWidth()*0.11f, Gdx.graphics.getHeight()*0.865f, 90,90);
        abilityButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                abilityList.setVisible(!abilityList.isVisible());
            }
        });
        //towerMenue = new testActor(towerMenueIcon, Gdx.graphics.getWidth()/100*11f, Gdx.graphics.getHeight()/100*89f, 90f, 90f);

        spawnButtonActor = new testActor(upgradeAbilityButton, Gdx.graphics.getWidth()*0.21f, Gdx.graphics.getHeight()*0.865f, 90,90);
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
        tower.setVisible(false);
        TextButton continueButton2 = new TextButton("Cancel", uiSkin);
        continueButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("bin im tower array");
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                tower.setVisible(!tower.isVisible());
                /*
                HIER INPUT PAUSE MENÜ ENTFERNEN


                 */
            }
        });
        //Create Towers
        towerList = new LinkedList<>();

        //towers in opened menu
        for(int i = 0; i <= 2; i++){
            //towerList.add(i, new ImageButton(fireAbilitySkin));
            towerList.add(i, new ImageButton(towerSkins[i]));
            final int finalI = i;


            towerList.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    resourceHandler.getSound("buttonClickSound").play(0.5f);
                    Gdx.app.log("towerList: ", String.valueOf(finalI));
                }
            });
        }


        for(ImageButton towerImage : towerList){
            tower.add(towerImage);
        }
        towerCircleBool = new ArrayList<>();
        for (int i = 0; i<= 8; i++){
            towerCircleBool.add(i, false);
        }
        for(int i = 0; i <= towerLocation_x.length - 1; i++){
            towerCircle_x.add(towerLocation_x[i]);
            towerCircle_y.add(towerLocation_y[i]);
        }

        tower.padTop(64);
        tower.setPosition(stage.getWidth() / 2 - tower.getWidth() / 2, stage.getHeight() / 2 - tower.getHeight() / 2);
        tower.add(continueButton2);
        tower.pack();
        Gdx.input.setInputProcessor(stage);

        ImageButtonStyle placementStyle = new ImageButtonStyle();
        placementStyle.imageUp = towerPlacementSkin.getDrawable("placement_up");
        placementStyle.imageOver = towerPlacementSkin.getDrawable("placement_hover");

        towerListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                Gdx.app.log("towerListener","");
            }
        };


        towerAttackCircle = new LinkedList<>();
        for(int i = 0; i <= towerLocation_x.length - 1; i++){
            towerAttackCircle.add(i, new Circle());
        }

        //this loop is for the tower ground
        for (int i = 0; i <= 8; i++){
            towers.add(i, new ImageButton(placementStyle));
            towers.get(i).setPosition(towerLocation_x[i], towerLocation_y[i]);
            towers.get(i).setSize(123.5f,70f);

            //towers.get(i).setDebug(true);
            final int finalI = i;
            towers.get(i).addListener(towerPlacementListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    resourceHandler.getSound("buttonClickSound").play(0.5f);
                    if(towers.get(finalI).isChecked()) {
                        towers.get(finalI).setChecked(false);
                        tower.setVisible(!tower.isVisible());
                        Gdx.app.log("towerList: ", String.valueOf(finalI));

                        if(towerList.get(0).isChecked()){
                            towerList.get(0).setChecked(false);
                            towers.get(finalI).setStyle(styleTowerPlacementArcher);
                            towers.get(finalI).setPosition(towerLocation_x[finalI] + 32, towerLocation_y[finalI] + 32);
                            towers.get(finalI).clearListeners();
                            towers.get(finalI).addListener(towerListener);
                            towers.get(finalI).setName("ArcherTower " + String.valueOf(finalI));
                            towerCircleBool.set(finalI, true);
                            towerAttackCircle.get(finalI).set(towerLocation_x[finalI] + 54f, towerLocation_y[finalI] + 32f, 150f);
                            coins -= 250;
                        }
                        if(towerList.get(1).isChecked() && coins >= 500){
                            towerList.get(1).setChecked(false);
                            towers.get(finalI).setStyle(styleTowerPlacementMagician);
                            towers.get(finalI).setPosition(towerLocation_x[finalI] + 32, towerLocation_y[finalI]);
                            towers.get(finalI).clearListeners();
                            towers.get(finalI).addListener(towerListener);
                            towers.get(finalI).setName("MagicTower " + String.valueOf(finalI));
                            towerCircleBool.set(finalI, true);
                            towerAttackCircle.get(finalI).set(towerLocation_x[finalI] + 54f, towerLocation_y[finalI] + 32f, 150f);
                            coins -= 500;
                        }
                        if(towerList.get(2).isChecked() && coins >= 100){
                            towerList.get(2).setChecked(false);
                            towers.get(finalI).setStyle(styleTowerPlacementSupport);
                            towers.get(finalI).clearListeners();
                            towers.get(finalI).addListener(towerListener);
                            towers.get(finalI).setName("SupportTower " + String.valueOf(finalI));
                            towerCircleBool.set(finalI, true);
                            towerAttackCircle.get(finalI).set(towerLocation_x[finalI] + 54f, towerLocation_y[finalI] + 32f, 150f);
                            coins -= 100;
                        }
                    }
                }
            });
        }
        //font for coins, health etc.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/riffic-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        font12 = generator.generateFont(parameter);
        generator.dispose();
        font = new BitmapFont();
        font.getData().setScale(2);
        batch = new SpriteBatch();
        level = new LevelOne();
        level.createBackground();
        addBuildingPlacesToStage();
        stage.addActor(pauseButtonActor);
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
        wizardLinkedList = new LinkedList<>();
        createAllEnemies();
        updateToolTips();
        //Gdx.app.log("ArrBoolSi", towerCircleBool.toString());
        runningAnimation = new Animation(FRAME_DURATION, runningFrames, Animation.PlayMode.LOOP);
        runningAnimationTree = new Animation(FRAME_DURATION, runningFramesTree, Animation.PlayMode.LOOP);
        explosionAbility = new Animation(FRAME_DURATION, explosionFrames, Animation.PlayMode.LOOP);
        /*for(int i = 0; i<= 8; i++){

        }*/
    }



    @Override
    public void render(float delta) {
        level.renderBackground();
        stage.draw();
        elapsed_time += Gdx.graphics.getDeltaTime();

        currentFrame = (TextureRegion) runningAnimation.getKeyFrame(elapsed_time);
        currentFrame2 = (TextureRegion) runningAnimationTree.getKeyFrame(elapsed_time);
        currentFrame3 = (TextureRegion) explosionAbility.getKeyFrame(elapsed_time);
        stage.act(Gdx.graphics.getDeltaTime());
        if(towers.get(0).isPressed()) {
            updateToolTips();
        }


        batch.begin();
        if (!isPaused){
            checkTowerRange(delta);
            spawnEnemies(Gdx.graphics.getDeltaTime());
            updateAllEntities();
            makeT2EnemiesMove(delta);
            makeT1EnemiesMove(delta);
            checkFireAbilityCollision();
            checkHealth();
            drawAllEntities();
        }

        font12.draw(batch, "Coins: " + coins, 10, 600);
        font12.draw(batch, "Health: " + health, 10, 550);

        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if(rangeCircle){
            drawCircle();
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    public void setExplosionAbility(){

        //setze Position für die explosion
        currentFrame2 = (TextureRegion) explosionAbility.getKeyFrame(elapsed_time);
    }

    public void drawCollCircl(){
        Iterator<Float> circleIterator_x = towerCircle_x.iterator();
        Iterator<Float> circleIterator_y = towerCircle_y.iterator();
        Iterator<Circle> shapeRendererIterator = towerAttackCircle.iterator();
        Iterator<ImageButton> towerIterator = towers.iterator();
        for(Iterator<Boolean> iterator = towerCircleBool.iterator(); iterator.hasNext();){
            if(shapeRendererIterator.hasNext()) {
                Boolean bool = iterator.next();
                Float circle_x = circleIterator_x.next();
                Float circle_y = circleIterator_y.next();
                Circle circle = shapeRendererIterator.next();
                if (bool) {
                    circle.set(circle_x + 54f, circle_y + 32f, 150);
                }
            }
        }
    }
    //DO NOT TOUCH; DANGER IMMINENT
    public void checkTowerRange(float delta){
        for(Iterator<Circle> circleIterator = towerAttackCircle.iterator(); circleIterator.hasNext();){
            Circle circle = circleIterator.next();
            for(Iterator<PathfindingEnemy> iterator = scorpionLinkedList.iterator(); iterator.hasNext();){
                PathfindingEnemy enemy = iterator.next();
                if (Intersector.overlaps(circle, enemy.getBoundingRectangle())) {
                    /*
                    Gdx.app.log(String.valueOf(enemy), String.valueOf(Intersector.overlaps(circle, enemy.getBoundingRectangle())));
                    Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));

                     */
                    enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                    //enemy.timeOfDmgTaken = enemy.timeAlive;
                }
            }
            for(Iterator<PathfindingEnemy> iterator = wizardLinkedList.iterator(); iterator.hasNext();){
                PathfindingEnemy enemy2 = iterator.next();
                if (Intersector.overlaps(circle, enemy2.getBoundingRectangle())) {
                    //Gdx.app.log(String.valueOf(enemy2), String.valueOf(enemy2.getLifeCount()));

                    enemy2.setLifeCount(enemy2.getLifeCount() - 0.05f);
                    //enemy.timeOfDmgTaken = enemy.timeAlive;
                }
            }
        }
    }


    public void addBuildingPlacesToStage(){
        for(ImageButton placeTower: towers){
            stage.addActor(placeTower);
        }
    }
    public void updateToolTips(){
        fireAbilityToolTip = "Deals "+ damage.getFireDamage() + " Damage against 1 Enemy";
        thunderAbilityToolTip = "Deals "+ damage.getThunderDamage() + " Damage to all enemies";
        abilityButtonArray.get(0).addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(1).addListener(new TextTooltip(thunderAbilityToolTip, toolTipManager, uiSkin));
        //abilityButtonArray.get(2).addListener(new TextTooltip(upgradeAbilityToolTip, toolTipManager, uiSkin));
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
                        coins += 50;
                    }
                }
                else{
                    break;
                }
            }
            for (Iterator<PathfindingEnemy> iterator = wizardLinkedList.iterator(); iterator.hasNext(); ) {
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
                        coins += 50;
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
                coins += 10;
            }
        }

        for (Iterator<PathfindingEnemy> iterator = wizardLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy enemy = iterator.next();
            enemy.setLifeCount(enemy.getLifeCount() - damage.getThunderDamage());
            enemy.timeOfDmgTaken = enemy.timeAlive;
            Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
            if (enemy.getLifeCount() <= 0) {
                iterator.remove();
                coins += 10;
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
    public void drawRange(int i, float x, float y){
        towerAttackCircle.add(i, new Circle());
        towerAttackCircle.get(i).set(x + 54f, y + 32f,100f);
        /*towerAttackRange.begin(ShapeRenderer.ShapeType.Filled);
        towerAttackRange.setColor(1,1,1,0.2f);
        //towerAttackRange.rect(x,y,150,150);
        towerAttackRange.circle(x + 54f, y + 32f,150f);
        towerAttackRange.end();
        */
    }
    //TODO outsource Abilities to their own files
    public void createAbility(){
        for(ImageButton imageButton: abilityButtonArray){
            if(imageButton.isChecked()){
                Gdx.app.log("Create Ability", abilityButtonArray.toString());
                fireBall = new Ability();
            }
        }
    }

    public void createExplosionAbility(){
        for(ImageButton imageButton: abilityButtonArray){
            if(imageButton.isChecked()){
                Gdx.app.log("Create Ability", abilityButtonArray.toString());
                explosion = new Ability();
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
        fireBallAbility.setPosition(0, Gdx.graphics.getHeight() * 0.80f);
        ability.add(fireBallAbility);
    }
    public void createAllEnemies(){
        treeBoss = new TreeBoss();
        scorpion = new Scorpion();
        wizard = new Wizard();
    }
    public void drawAllEntities(){
        for(PathfindingEnemy drawAbility: ability){
            drawAbility.draw(batch);
        }
    }
    public void updateAllEntities(){
        for(PathfindingEnemy updateAbility: ability){
            updateAbility.updateAbility();
        }
    }

    public void makeT2EnemiesMove(float delta) {

        for (Iterator<PathfindingEnemy> iterator = scorpionLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy s = iterator.next();

            s.updateAnimation(batch, LevelOne.levelOneBottomPath(), delta, currentFrame);
            //remove entity if life is less than 0, and add 100 coins
            if (s.getLifeCount() <= 0 ) {
                iterator.remove();
                coins += 100;
            }
            //remove entity if is out of bounds, and get player damage
            if(s.getX() > Gdx.graphics.getWidth()){
                iterator.remove();
                health -= 1;
            }

        }
    }

    public void makeT1EnemiesMove(float delta){
        for (Iterator<PathfindingEnemy> iteratorBoss = wizardLinkedList.iterator(); iteratorBoss.hasNext(); ) {
            PathfindingEnemy wizard = iteratorBoss.next();
            //s.preDraw();
            //s.update(batch, LevelOne.levelOnePath(), delta);
            //s.postDraw();
            wizard.updateAnimation(batch, LevelOne.levelOneTopPath(), delta, currentFrame2);

            //remove entity if life is less than 0, and add 100 coins
            if (wizard.getLifeCount() <= 0 ) {
                iteratorBoss.remove();
                coins += 100;
            }
            //remove entity if is otu of bounds, and get player damage
            if(wizard.getX() > Gdx.graphics.getWidth()){
                iteratorBoss.remove();
                health -= 5;
            }
        }
    }

    public void checkHealth(){
        if(health <= 0){
            health = 0;
            stage.addActor(gameOverWindow);
            resourceHandler.getSound("gameOverSound").play(0.10f);
            isPaused = true;
        }
    }

    // TODO
    /*
    BUG: wizards werden zu schnell gespawnt (vielleicht eine weitere if in spawnEnemies)
    FEATURE: Tower Shooting
    FEATURE: Click & Set Explosion (inkl. Animation)
    FEATURE: Click & Set Poison Rain (inkl. Animation)
    FEATURE: Anpassen von der Feuer Fähigkeit (Animation)
    FEATURE: Level 2 -new Music; -new BG; -new Paths, Monsters
     */


    public void spawnEnemies(float deltaTime){
        enemySpawnTimer += deltaTime;
        enemySpawnTimer2 += deltaTime;
        if(enemySpawnTimer > timeBetweenEnemySpawns){
            scorpionLinkedList.add(new PathfindingEnemy(scorpion.idleFrame(), 20));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
        if(enemySpawnTimer2 > timeBetweenEnemySpawns2){
            wizardLinkedList.add(new PathfindingEnemy(treeBoss.idleFrame(), 100));
            enemySpawnTimer2 -= timeBetweenEnemySpawns2;
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
