package levels;

import MainRef.Assets;
import MainRef.TowerDefense;
import abilities.Ability;
import abilities.Explosion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import enemy.bossSkeleton.LevelOneBoss;
import enemy.scorpionEntity.Scorpion;
import enemy.wizardEntity.Wizard;
import levels.menu.testActor;
import levels.menu.MainMenuScreen;
import java.util.LinkedList;
import java.util.*;

public class levelOneGenerator implements Screen {
    final TowerDefense game;
    testActor pauseButtonActor, abilityButtonActor, upgradeAbilityButtonActor, towerMenueActor, backgroundHUD, backgroundHUD2;
    Window pause, abilityList, tower, gameOverWindow, backgroundWindow, victoryWindow;
    Stage stage;
    TooltipManager toolTipManager;
    ShapeRenderer shapeRenderer, towerAttackRange;
    LinkedList<Circle> towerAttackCircle;
    ClickListener placementListener, towerPlacementListener, towerListener;
    SpriteBatch batch;
    LevelOne level;
    PathfindingEnemy scorpionEnemy, wizardEnemy, bossPath, fireBallAbility, fireBallAbility2;
    Scorpion scorpion;
    Wizard wizard;
    LevelOneBoss boss;
    Ability fireBall, damage = new Ability();
    Explosion explosion;
    private Array<Vector2> abilityPath;
    private boolean isPaused;
    private boolean gameOver;
    private LinkedList<PathfindingEnemy> scorpionLinkedList, wizardLinkedList, saplingLinkedList;
    private float enemySpawnTimer, timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer2, timeBetweenEnemySpawns2 = 3f;
    private boolean rangeCircle = false;
    LinkedList<ShapeRenderer> towerRangeShape;
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/button_pause.png";
    private String abilityButton = "core/assets/abilities/abilitesSkin/btton_abilities.png";
    private String upgradeAbilityButton = "core/assets/abilities/abilitesSkin/upgradeButton.png";
    private Skin uiSkin, fireAbilitySkin, thunderAbilitySkin, fireBallSkin, windowSkin, towerPlacementSkin, towerSkin, explosionAbilitySkin;
    private String backgroundGameHUD = "core/assets/normal_window.png";
    private boolean towerIsPlaced;
    private float coins = 1000;
    private int enemyCount = 50;
    private int saplingCount = 0;
    private int bossUpdate = 0;
    BitmapFont font12;
    //TODO
    LinkedList<ImageButton> towerList = new LinkedList<>();
    LinkedList<PathfindingEnemy> enemyList = new LinkedList<>();
    Array<Array> saplingBossPath = new Array<>();
    Array<PathfindingEnemy> ability = new Array<>();
    Array<PathfindingEnemy> abilityExplosion = new Array<>();
    Array<PathfindingEnemy> explosions = new Array<>();
    Array<ImageButton> abilityButtonArray = new Array();

    Array<ImageButton> upgradeAbilityButtonArray = new Array();
    private String fireAbilityToolTip, thunderAbilityToolTip, explosionAbilityToolTip;
    //tower tooltips
    private String archerTowerToolTip = "Deals ?? Damage per second.\nCost: 250 OptiCoins";
    private String magicianTowerToolTip = "Deals ??? Damage per second.\nCost: 500 OptiCoins";
    private String supportTowerToolTip = "Deals ? Damage per second.\nCost: 100 OptiCoins";
    private String upgradeAbilityToolTip = "Upgrades every ability. Cost: 500 OptiCoins";
    private int health = 100;
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
    private TextureAtlas runningAnimationAtlas = Assets.manager.get(Assets.scorpionEnemy, TextureAtlas.class);
    private TextureAtlas runningWizardAtlas = Assets.manager.get(Assets.wizardEnemy, TextureAtlas.class);
    private TextureAtlas levelOneBossWalking = Assets.manager.get(Assets.levelOneBossWalk, TextureAtlas.class);
    private TextureAtlas levelOneBossSapling = Assets.manager.get(Assets.levelOneBossSapling, TextureAtlas.class);
    private TextureAtlas explosionAtlas = new TextureAtlas(Gdx.files.internal("core/assets/abilities/abilitesSkin/explosion/explosion.atlas"));
    private TextureRegion currentFrame, currentFrame2, currentFrame3, currentFrameBoss, currentFrameSapling;
    private Animation runningAnimation, runningAnimationWizard, explosionAbility, bossWalkingAnimation, bossSaplingAnimation;
    private float elapsed_time = 0f;
    Array<TextureAtlas.AtlasRegion> runningFrames = runningAnimationAtlas.findRegions("1_enemies_1_run");
    Array<TextureAtlas.AtlasRegion> runningFramesWizard = runningWizardAtlas.findRegions("2_enemies_1_walk");
    Array<TextureAtlas.AtlasRegion> bossSaplingFrames = levelOneBossSapling.findRegions("0_boss_specialty_2");
    Array<TextureAtlas.AtlasRegion> bossWalkingFrames = levelOneBossWalking.findRegions("0_boss_walk");
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
        Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).play();
        //Assets.manager.get(Assets.bossLevelOneMusic, Music.class).play();
        stage = new Stage(new ScreenViewport());
        toolTipManager = new TooltipManager();
        toolTipManager.initialTime = 0.0f;
        toolTipManager.resetTime = 0.0f;
        toolTipManager.subsequentTime = 0.0f;
        toolTipManager.hideAll();
        toolTipManager.instant();
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));
        fireAbilitySkin = new Skin(Gdx.files.internal("abilities/abilitesSkin/fire/fireAbilitySkin.json"), Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class));
        towerSkin = new Skin(Gdx.files.internal("core/assets/background/tower/towerPack/towerPack.json"), Assets.manager.get(Assets.towerPack, TextureAtlas.class));
        thunderAbilitySkin = new Skin(Gdx.files.internal("core/assets/abilities/abilitesSkin/thunder/thunderAbilitySkin.json") ,new TextureAtlas("core/assets/abilities/abilitesSkin/thunder/thunderAbility.atlas"));
        explosionAbilitySkin = new Skin(Gdx.files.internal("core/assets/abilities/abilitesSkin/explosion/explosionButton/explosionButton.json"), new TextureAtlas("core/assets/abilities/abilitesSkin/explosion/explosionButton/explosionButton.atlas"));
        //thunderAbilitySkin = new Skin(Gdx.files.internal("abilities/abilitesSkin/fire/fireAbilitySkin.json"), new TextureAtlas("abilities/abilitesSkin/fire/fireAbilitySkin.atlas"));
        windowSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.atlas"));
        towerPlacementSkin = new Skin(Gdx.files.internal("background/tower/locations/towerPlacement.json"), new TextureAtlas("background/tower/locations/towerPlacement.atlas"));
        //----------------------------------------------------------PauseMenu------------------------------------------------------//
        //TODO outsource to a different file
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        Window.WindowStyle windowStyle2 = new Window.WindowStyle();
        windowStyle.background = windowSkin.getDrawable("default-window");
        pause = new Window("Pause", uiSkin);
        pause.setVisible(false);
        pause.padTop(64);
        pause.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);
        pause.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
        pause.setMovable(false);
        //----------------------------------------------------------PauseMenuButtons------------------------------------------------------//
        TextButton continueButton = new TextButton("Continue the Game",uiSkin);
        TextButton exitButton = new TextButton("Exit to Main Menu", uiSkin);
        TextButton exitButton2 = new TextButton("Exit to Main Menu", uiSkin);
        TextButton victoryExitButton = new TextButton("Exit to Main menu", uiSkin);
        continueButton.setSize(600f,600f);
        exitButton.setSize(250f,250f);
        exitButton2.setSize(250f,250f);
        victoryExitButton.setSize(500f, 500f);
        pause.add(continueButton).row();
        pause.add(exitButton);

        gameOverWindow = new Window("Game Over", uiSkin);
        gameOverWindow.add(exitButton2);
        //gameOverWindow.pack();
        gameOverWindow.setVisible(true);
        gameOverWindow.setPosition(stage.getWidth() * 0.32f, stage.getHeight() * 0.3f);
        gameOverWindow.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);

        victoryWindow = new Window("VICTORY", uiSkin);
        victoryWindow.add(victoryExitButton);
        victoryWindow.setVisible(false);
        victoryWindow.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
        victoryWindow.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);
        victoryWindow.setMovable(false);


        //----------------------------------------------------------PauseMenuButtonFunctionality------------------------------------------------------//
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                isPaused = !isPaused;
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                pause.setVisible(false);
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new MainMenuScreen(game));
                Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).stop();
                dispose();
            }
        });
        exitButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new MainMenuScreen(game));
                Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).stop();
                dispose();
            }
        });
        victoryExitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new MainMenuScreen(game));
                Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).stop();
                dispose();
            }
        });
        //--------------------------------------------------------AbilityMenu----------------------------------------------------//
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
        style.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_up"));
        style.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_over"));
        style.imageChecked = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_checked"));
        //new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("archerTower_default"));

        styleTowerPlacementArcher.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("archerTower_default"));
        styleTowerPlacementMagician.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("magicianTower_default"));
        styleTowerPlacementSupport.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("supportTower_default"));

        style2.imageUp = thunderAbilitySkin.getDrawable("thunder_up");
        style2.imageOver = thunderAbilitySkin.getDrawable("thunder_over");

        styleExplosionAbility.imageUp = explosionAbilitySkin.getDrawable("explosionButton");
        styleExplosionAbility.imageOver = explosionAbilitySkin.getDrawable("explosionButtonHover");


        ImageButtonStyle[] towerSkins = {
                styleTowerPlacementArcher,
                styleTowerPlacementMagician,
                styleTowerPlacementSupport
        };

        //styleExplosionAbility.imageUp = fireAbilitySkin.getDrawable("fire_up");
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
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(fireAbility.isChecked()){
                    rangeCircle = true;
                    stage.addListener(placementListener = new ClickListener(Input.Buttons.LEFT) {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(fireAbility.isChecked() && coins >= damage.getFireCost()) {
                                super.clicked(event, x, y);
                                coins -= damage.getFireCost();
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
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(thunderAbility.isChecked() && coins >= damage.getThunderCost()){
                    coins -= damage.getThunderCost();
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
        explosionAbilityArray.addListener(new TextTooltip(explosionAbilityToolTip, toolTipManager, uiSkin));
        explosionAbilityArray.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if(explosionAbilityArray.isChecked()){
                    rangeCircle = true;
                    stage.addListener(placementListener = new ClickListener(Input.Buttons.LEFT) {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(explosionAbilityArray.isChecked() && coins >= damage.getExplosionCost()) {
                                super.clicked(event, x, y);
                                coins -= damage.getExplosionCost();
                                //createAbility();
                                createExplosionAbility();
                                setUpAbilityTwo(Gdx.input.getX() - 40, 720 - Gdx.input.getY() - 50, Gdx.graphics.getDeltaTime());
                                dealExplosionDamage();
                                explosionAbilityArray.setChecked(false);
                                stage.removeListener(placementListener);
                                rangeCircle = !rangeCircle;
                            }
                            else{
                                rangeCircle = !rangeCircle;
                                explosionAbilityArray.setChecked(false);
                                stage.removeListener(placementListener);
                            }
                        }
                    });
                }
            }
        });


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
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                isPaused = !isPaused;
                pause.setVisible(!pause.isVisible());
            }
        });
        backgroundHUD = new testActor(backgroundGameHUD, Gdx.graphics.getWidth()*0.01f, Gdx.graphics.getHeight()*0.62f, 270,175);
        backgroundHUD2 = new testActor(backgroundGameHUD, Gdx.graphics.getWidth()*0.01f, Gdx.graphics.getHeight()*0.58f, 270,200);
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                tower.setVisible(false);
            }
        });
        abilityButtonActor = new testActor(abilityButton, Gdx.graphics.getWidth()*0.11f, Gdx.graphics.getHeight()*0.865f, 90,90);
        abilityButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                abilityList.setVisible(!abilityList.isVisible());
            }
        });
        //towerMenue = new testActor(towerMenueIcon, Gdx.graphics.getWidth()/100*11f, Gdx.graphics.getHeight()/100*89f, 90f, 90f);

        upgradeAbilityButtonActor = new testActor(upgradeAbilityButton, Gdx.graphics.getWidth()*0.21f, Gdx.graphics.getHeight()*0.865f, 90,90);
        upgradeAbilityButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                damage.setThunderDamage(damage.getThunderDamage() + 5f);
                thunderAbilityToolTip = "Deals "+ (int)damage.getThunderDamage() + " Damage to all enemies\nCost: "+ (int)damage.getThunderCost();
                enemyCount = 0;
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
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                tower.setVisible(!tower.isVisible());

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
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
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
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                Gdx.app.log("towerListener","");
            }
        };


        towerAttackCircle = new LinkedList<>();
        for(int i = 0; i <= towerLocation_x.length - 1; i++){
            towerAttackCircle.add(i, new Circle());
        }

        towerRangeShape = new LinkedList<>();
        for(int i = 0; i <= towerLocation_x.length - 1; i++){
            towerRangeShape.add(i, new ShapeRenderer());
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
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    if(towers.get(finalI).isChecked()) {
                        towers.get(finalI).setChecked(false);
                        tower.setVisible(!tower.isVisible());
                        Gdx.app.log("towerList: ", String.valueOf(finalI));

                        if(towerList.get(0).isChecked()){
                            towerList.get(0).setChecked(false);
                            towers.get(finalI).setStyle(styleTowerPlacementArcher);
                            towers.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI]);
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
                            towers.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI]);
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

        towerListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                for(int i = 0; i <= towerLocation_x.length - 1; i++){
                    if(towers.get(i).getStyle() == style){
                        towerCircleBool.set(i, !towerCircleBool.get(i));
                    }
                }
            };
        };


        //font for coins, health etc.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/riffic-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font12 = generator.generateFont(parameter);
        generator.dispose();
        font = new BitmapFont();
        font.getData().setScale(1);
        batch = new SpriteBatch();
        level = new LevelOne();
        level.createBackground();
        addBuildingPlacesToStage();
        stage.addActor(pauseButtonActor);
        stage.addActor(abilityButtonActor);
        stage.addActor(upgradeAbilityButtonActor);
        stage.addActor(backgroundHUD);
        stage.addActor(pause);
        stage.addActor(abilityList);
        stage.addActor(tower);
        stage.addActor(victoryWindow);

        scorpionLinkedList = new LinkedList<>();
        wizardLinkedList = new LinkedList<>();
        saplingLinkedList = new LinkedList<>();
        createAllEnemies();
        updateToolTips();
        //Gdx.app.log("ArrBoolSi", towerCircleBool.toString());
        runningAnimation = new Animation(FRAME_DURATION, runningFrames, Animation.PlayMode.LOOP);
        runningAnimationWizard = new Animation(FRAME_DURATION, runningFramesWizard, Animation.PlayMode.LOOP);
        explosionAbility = new Animation(FRAME_DURATION, explosionFrames, Animation.PlayMode.LOOP);

        //TODO
        bossWalkingAnimation = new Animation(FRAME_DURATION, bossWalkingFrames, Animation.PlayMode.LOOP);
        bossSaplingAnimation = new Animation(FRAME_DURATION, bossSaplingFrames, Animation.PlayMode.LOOP);
        //TODO
        spawnBoss();
    }



    @Override
    public void render(float delta) {
        level.renderBackground();
        elapsed_time += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) runningAnimation.getKeyFrame(elapsed_time);
        currentFrame2 = (TextureRegion) runningAnimationWizard.getKeyFrame(elapsed_time);
        currentFrame3 = (TextureRegion) explosionAbility.getKeyFrame(elapsed_time);
        //boss frames
        currentFrameBoss = (TextureRegion) bossWalkingAnimation.getKeyFrame(elapsed_time);
        currentFrameSapling = (TextureRegion) bossSaplingAnimation.getKeyFrame(elapsed_time);

        stage.act(Gdx.graphics.getDeltaTime());
        /*if(upgradebutton is Pressed/Checked) {
            updateToolTips();
        }*/
        batch.begin();
        if (!isPaused){
            checkTowerRange(delta);
            if(enemyCount > 0) {
                spawnEnemies(Gdx.graphics.getDeltaTime());
                makeT1EnemiesMove(delta);
                makeT2EnemiesMove(delta);
            }
            else{
                clearEnemies();
                if(bossUpdate == 0){
                    moveBoss(delta);
                }else{
                    moveSaplingBoss(delta);
                }
            }
                updateAllEntities();

                checkHealth();
                drawAllEntities();
                checkAbilityCollision(fireBallAbility, damage.getFireDamage(), 50);
                checkAbilityCollision(fireBallAbility2, damage.getExplosionDamage(), 100);
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if(rangeCircle){
            drawCircle();
        }
        drawCollCircl();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        stage.draw();
        font12.draw(batch, "Coins: " + (int)coins, 25, 590);
        font12.draw(batch, "Health: " + health, 25, 550);
        if(enemyCount == 0){
            Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).stop();
            Assets.manager.get(Assets.bossLevelOneMusic, Music.class).play();
            if(bossUpdate == 0){
                font12.draw(batch, "Boss HP: " + (int)bossPath.getLifeCount(), 25, 510);
            }else{
                int y = 510;
                stage.addActor(backgroundHUD2);
                for(Iterator<PathfindingEnemy> saplingIterator = saplingLinkedList.iterator(); saplingIterator.hasNext();){
                    PathfindingEnemy sapling = saplingIterator.next();
                    backgroundHUD.remove();
                    font12.draw(batch, "Sapling HP: " + (int)sapling.getLifeCount(), 25, y);
                    y -= 40;
                }
            }
        }else{
            font12.draw(batch, "Enemies left: " + enemyCount, 25, 510);
        }
        batch.end();
    }

    public void setExplosionAbility(){
        //setze Position für die explosion
        currentFrame2 = (TextureRegion) explosionAbility.getKeyFrame(elapsed_time);

    }

    public void drawCollCircl(){
        Iterator<Float> circleIterator_x = towerCircle_x.iterator();
        Iterator<Float> circleIterator_y = towerCircle_y.iterator();
        Iterator<Circle> circleIterator = towerAttackCircle.iterator();
        Iterator<ShapeRenderer> shapeRendererIterator = towerRangeShape.iterator();
        Iterator<ImageButton> towerIterator = towers.iterator();
        for(Iterator<Boolean> iterator = towerCircleBool.iterator(); iterator.hasNext();){
            if(circleIterator.hasNext()) {
                Boolean bool = iterator.next();
                Float circle_x = circleIterator_x.next();
                Float circle_y = circleIterator_y.next();
                Circle circle = circleIterator.next();
                ShapeRenderer shape = shapeRendererIterator.next();
                if (bool) {
                    shape.begin(ShapeRenderer.ShapeType.Filled);
                    shape.setColor(1,1,1,0.2f);
                    shape.circle(circle_x + 54f, circle_y + 32f, 150f);
                    shape.end();
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
                if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && enemy.getX() >= 0) {
                    enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                }
            }
            for(Iterator<PathfindingEnemy> iterator = wizardLinkedList.iterator(); iterator.hasNext();){
                PathfindingEnemy enemy2 = iterator.next();
                if (Intersector.overlaps(circle, enemy2.getBoundingRectangle()) && enemy2.getX() >= 0) {
                    //Gdx.app.log(String.valueOf(enemy2), String.valueOf(enemy2.getLifeCount()));
                    enemy2.setLifeCount(enemy2.getLifeCount() - 0.07f);
                    //enemy.timeOfDmgTaken = enemy.timeAlive;
                }
            }if(enemyCount == 0){
                if(bossUpdate == 0){
                    if(Intersector.overlaps(circle, bossPath.getBoundingRectangle())) {
                        bossPath.setLifeCount(bossPath.getLifeCount() - 0.07f);
                    }
                }else{
                    for (Iterator<PathfindingEnemy> iterator = saplingLinkedList.iterator(); iterator.hasNext(); ) {
                        PathfindingEnemy sapling = iterator.next();
                        if(Intersector.overlaps(circle, sapling.getBoundingRectangle())) {
                            sapling.setLifeCount(sapling.getLifeCount() - 0.05f);
                        }
                    }
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
        fireAbilityToolTip = "Deals "+ (int)damage.getFireDamage() + " Damage against 1 Enemy\nCost: "+ (int)damage.getFireCost() + " OptiCoins";
        thunderAbilityToolTip = "Deals "+ (int)damage.getThunderDamage() + " Damage to all enemies\nCost: "+ (int)damage.getThunderCost() + " OptiCoins";
        explosionAbilityToolTip = "Immediately kills one enemy.\nCost: " + (int)damage.getExplosionCost() + " OptiCoins";
        abilityButtonArray.get(0).addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(1).addListener(new TextTooltip(thunderAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(2).addListener(new TextTooltip(explosionAbilityToolTip, toolTipManager, uiSkin));
        towerList.get(0).addListener(new TextTooltip(archerTowerToolTip, toolTipManager, uiSkin));
        towerList.get(1).addListener(new TextTooltip(magicianTowerToolTip, toolTipManager, uiSkin));
        towerList.get(2).addListener(new TextTooltip(supportTowerToolTip, toolTipManager, uiSkin));
    }
    public void checkAbilityCollision(PathfindingEnemy abl, float damage, int coin){
        if(!(abl == null)) {
            Iterator<PathfindingEnemy> abilityIterator = ability.iterator();
            //Gdx.app.log("Array Index",ability.toString());
            for (Iterator<PathfindingEnemy> iterator = scorpionLinkedList.iterator(); iterator.hasNext(); ) {
                if(abilityIterator.hasNext()) {
                    PathfindingEnemy enemy = iterator.next();
                    if (enemy.getBoundingRectangle().overlaps(ability.get(0).getBoundingRectangle())) {
                        enemy.setLifeCount(enemy.getLifeCount() - damage);
                        enemy.timeOfDmgTaken = enemy.timeAlive;
                        Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                        ability.removeValue(ability.get(0),true);
                    }
                    if (enemy.getLifeCount() <= 0) {
                        iterator.remove();
                        coins += coin;
                        enemyCount -= 1;
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
                        enemy.setLifeCount(enemy.getLifeCount() - damage);
                        enemy.timeOfDmgTaken = enemy.timeAlive;
                        Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                        ability.removeValue(ability.get(0),true);
                    }
                    if (enemy.getLifeCount() <= 0) {
                        iterator.remove();
                        coins += coin;

                        enemyCount -= 1;
                    }
                }
                else{
                    break;
                }
            }
            for (Iterator<PathfindingEnemy> iterator = saplingLinkedList.iterator(); iterator.hasNext();){
                if(abilityIterator.hasNext()) {
                    PathfindingEnemy sapling = iterator.next();
                    if (sapling.getBoundingRectangle().overlaps(ability.get(0).getBoundingRectangle())) {
                        sapling.setLifeCount(sapling.getLifeCount() - (damage + 195));
                        Gdx.app.log(String.valueOf(sapling), String.valueOf(sapling.getLifeCount()));
                        ability.removeValue(ability.get(0),true);
                    }
                    if (sapling.getLifeCount() <= 0) {
                        iterator.remove();
                        saplingCount += 1;
                        coins += coin;
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
                enemyCount -= 1;
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
                enemyCount -= 1;
            }
        }
    }

    public void dealExplosionDamage(){
        for (Iterator<PathfindingEnemy> iterator = scorpionLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy enemy = iterator.next();
            enemy.setLifeCount(enemy.getLifeCount() - damage.getExplosionDamage());
            enemy.timeOfDmgTaken = enemy.timeAlive;
            Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
            if (enemy.getLifeCount() <= 0) {
                iterator.remove();
                coins += 10;
            }
        }

        for (Iterator<PathfindingEnemy> iterator = wizardLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy enemy = iterator.next();
            enemy.setLifeCount(enemy.getLifeCount() - damage.getExplosionDamage());
            enemy.timeOfDmgTaken = enemy.timeAlive;
            Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
            if (enemy.getLifeCount() <= 0) {
                iterator.remove();
                coins += 10;
            }
        }
        for (Iterator<PathfindingEnemy> iterator = saplingLinkedList.iterator(); iterator.hasNext();){
            PathfindingEnemy sapling = iterator.next();
            sapling.setLifeCount(sapling.getLifeCount() - (damage.getThunderDamage() + 50));
            if (sapling.getLifeCount() <= 0) {
                iterator.remove();
                coins += 10;
                saplingCount += 1;
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
                explosion = new Explosion();
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
        fireBallAbility.setPosition(0, Gdx.graphics.getHeight() * 0.8f);
        ability.add(fireBallAbility);
    }
    public void setUpAbilityTwo(float x, float y, float delta){
        ability = new Array<>();
        fireBallAbility2 = new PathfindingEnemy(explosion.idleFrame(), abilityMovementPath(x, y));
        fireBallAbility2.setPosition(0, Gdx.graphics.getHeight() * 0.8f);
        ability.add(fireBallAbility2);
    }

    public void createAllEnemies(){
        scorpion = new Scorpion();
        wizard = new Wizard();
        boss = new LevelOneBoss();
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
                enemyCount -= 1;
            }
            //remove entity if is out of bounds, and get player damage
            if(s.getX() > Gdx.graphics.getWidth()){
                iterator.remove();
                health -= 1;
                enemyCount -= 1;
            }

        }
    }
    public void makeT1EnemiesMove(float delta){
        for (Iterator<PathfindingEnemy> iteratorBoss = wizardLinkedList.iterator(); iteratorBoss.hasNext(); ) {
            PathfindingEnemy wizard = iteratorBoss.next();
            wizard.updateAnimation(batch, LevelOne.levelOneTopPath(), delta, currentFrame2);
            //remove entity if life is less than 0, and add 100 coins
            if (wizard.getLifeCount() <= 0 ) {
                iteratorBoss.remove();
                coins += 100;
                enemyCount -= 1;
            }
            //remove entity if is otu of bounds, and get player damage
            else if(wizard.getX() >= Gdx.graphics.getWidth()){

                iteratorBoss.remove();
                health -= 5;
                enemyCount -= 1;
            }
        }
    }
    public void moveBoss(float delta){
        if(bossPath.getX() > 500 || bossPath.getLifeCount() <= 0){
                bossPath.setBossPosition(-250f, 0);
                saplingLinkedList.add(new PathfindingEnemy(boss.idleFrame(), 600, LevelOne.levelOneTopBossPath()));
                saplingLinkedList.add(new PathfindingEnemy(boss.idleFrame(), 600, LevelOne.levelOneBottomBossPath()));
                saplingBossPath.add(LevelOne.levelOneTopBossPath());
                saplingBossPath.add(LevelOne.levelOneBottomBossPath());
                bossUpdate +=1;
        }
        else {
            bossPath.updateBossAnimation(batch, LevelOne.levelOneTopPath(), delta, currentFrameBoss, 35);
        }
    }
    public void moveSaplingBoss(float delta){
        Iterator<Array> pathIterator = saplingBossPath.iterator();
        for (Iterator<PathfindingEnemy> iterator = saplingLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy sapling = iterator.next();
            Array path = pathIterator.next();
            sapling.updateBossAnimation(batch, path, delta, currentFrameSapling, 25);
            if (sapling.getLifeCount() <= 0) {
                iterator.remove();
                pathIterator.remove();
                coins += 1000;
                saplingCount += 1;
            }
            if (sapling.getX() > Gdx.graphics.getWidth()) {
                iterator.remove();
                pathIterator.remove();
                health -= 50;
                saplingCount += 1;
            }
        }
        if(saplingCount == 2 && health > 0){
            Assets.manager.get(Assets.bossLevelOneMusic, Music.class).stop();
            isPaused = true;
            victoryWindow.setVisible(true);
            Assets.manager.get(Assets.victorySound, Sound.class).play(2f);
            Gdx.app.log("Game Won", "Sapling");
        }else{
            checkHealth();
        }
    }
    public void clearEnemies(){
        for (Iterator<PathfindingEnemy> scorpionIterator = scorpionLinkedList.iterator(); scorpionIterator.hasNext(); ) {
            PathfindingEnemy scorpion = scorpionIterator.next();
            scorpionIterator.remove();
        }
        for(Iterator<PathfindingEnemy> wizardIterator = wizardLinkedList.iterator(); wizardIterator.hasNext(); ) {
            PathfindingEnemy wizard = wizardIterator.next();
            wizardIterator.remove();
        }
    }

    public void checkHealth(){
        if(health <= 0){
            health = 0;
            stage.addActor(gameOverWindow);
            Assets.manager.get(Assets.gameOverSound, Sound.class).play(0.05f);
            isPaused = true;
        }
    }

    public void spawnEnemies(float deltaTime){
        enemySpawnTimer += deltaTime;
        enemySpawnTimer2 += deltaTime;
        if(enemyCount >= 2){
            if(enemySpawnTimer > timeBetweenEnemySpawns){
                scorpionLinkedList.add(new PathfindingEnemy(scorpion.idleFrame(), 20, LevelOne.levelOneBottomPath()));
                enemySpawnTimer -= timeBetweenEnemySpawns;
            }
            if(enemySpawnTimer2 > timeBetweenEnemySpawns2){
                wizardLinkedList.add(new PathfindingEnemy(wizard.idleFrame(), 100, LevelOne.levelOneTopPath()));
                enemySpawnTimer2 -= timeBetweenEnemySpawns2;
            }
        }
    }
    public void spawnBoss(){
        bossPath = new PathfindingEnemy(boss.idleFrame(), 1000, LevelOne.levelOneTopPath());
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
        stage.dispose();
        level.dispose();
        Assets.levelOneDispose();
    }
}
