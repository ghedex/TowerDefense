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
import enemy.bossSkeleton.LevelTwoBoss;
import enemy.impEntity.Imp;
import enemy.warriorEntity.Warrior;
import levels.menu.testActor;
import levels.menu.MainMenuScreen;
import levels.utils.Coin;
import modularAssets.TowerGeneration;

import java.util.LinkedList;
import java.util.*;

public class levelTwoGenerator implements Screen {
    final TowerDefense game;
    testActor pauseButtonActor, abilityButtonActor, upgradeAbilityButtonActor, towerMenueActor, backgroundHUD, backgroundHUD2;

    Window pause, abilityList, gameOverWindow, backgroundWindow, victoryWindow, towerMenuList, upgradeAbilityWindow;
    TowerGeneration towerGeneration = new TowerGeneration();
    Stage stage;
    TooltipManager toolTipManager;
    ShapeRenderer shapeRenderer;
    ClickListener placementListener;
    SpriteBatch batch;
    LevelTwo level;
    PathfindingEnemy impEnemy, warriorEnemy, bossPath, fireBallAbility, explosionAbility;
    Imp imp;
    Warrior warrior;
    LevelTwoBoss boss;
    Ability fireBall = new Ability(), damage = new Ability();
    Explosion explosion;
    private Array<Vector2> abilityPath;
    private boolean isPaused;
    private LinkedList<PathfindingEnemy> impLinkedList, warriorLinkedList, bossLinkedList;
    private float enemySpawnTimer, timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer2, timeBetweenEnemySpawns2 = 4f;
    private boolean rangeCircle = false;
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/button_pause.png";
    private String abilityButton = "core/assets/abilities/abilitesSkin/btton_abilities.png";
    private String upgradeAbilityButton = "core/assets/abilities/abilitesSkin/upgradeButton.png";
    private Skin uiSkin, fireAbilitySkin, thunderAbilitySkin,  windowSkin, towerPlacementSkin, towerSkin, explosionAbilitySkin, timeAbilitySkin;
    private String backgroundGameHUD = "core/assets/normal_window.png";
    private float coins = 1000;
    private int enemyCount = 50;
    private boolean isBossAlive = false;
    BitmapFont font12;
    //TODO
    LinkedList<ImageButton> towerList = new LinkedList<>();
    LinkedList<LinkedList> enemyList = new LinkedList<>();
    Array<PathfindingEnemy> ability = new Array<>();
    ArrayList<String> towerName;
    Array<PathfindingEnemy> abilityExplosion = new Array<>();
    Array<PathfindingEnemy> explosions = new Array<>();
    Array<ImageButton> abilityButtonArray = new Array();
    Array<ImageButton> improveAbilityButtonArray = new Array();

    Array<ImageButton> upgradeAbilityButtonArray = new Array();
    private String fireAbilityToolTip, thunderAbilityToolTip, explosionAbilityToolTip, timeAbilityToolTip;
    //tower tooltips
    private String archerTowerToolTip = "Deals ?? Damage per second.\nCost: 250 OptiCoins";
    private String magicianTowerToolTip = "Deals ??? Damage per second.\nCost: 500 OptiCoins";
    private String supportTowerToolTip = "Deals ? Damage per second.\nCost: 100 OptiCoins";
    private String upgradeAbilityToolTip = "Upgrades every ability. Cost: 500 OptiCoins";
    private String improveFireAbilityToolTip = "+ 15 Fire damage permanently. \nCost: 100 OPC";
    private String improveThunderAbilityTooltip = "+ 10 Thunder damage permanently. \nCost: 100 OPC";
    private String improveExplosionAbilityTooltip = "-500 overall cost. \nCost: 1000 OPC";
    private float health = 100;
    float[] towerLocation_x = {
            Gdx.graphics.getWidth() * 0.098f,
            Gdx.graphics.getWidth() * 0.135f,
            Gdx.graphics.getWidth() * 0.105f,
            Gdx.graphics.getWidth() * 0.804f,
            Gdx.graphics.getWidth() * 0.365f,
            Gdx.graphics.getWidth() * 0.701f,
            Gdx.graphics.getWidth() * 0.608f,
            Gdx.graphics.getWidth() * 0.529f,
            Gdx.graphics.getWidth() * 0.605f,
            Gdx.graphics.getWidth() * 0.338f,
    };
    float[] towerLocation_y = {
            Gdx.graphics.getHeight() * 0.582f,
            Gdx.graphics.getHeight() * 0.295f,
            Gdx.graphics.getHeight() * 0.005f,
            Gdx.graphics.getHeight() * 0.36f,
            Gdx.graphics.getHeight() * 0.624f,
            Gdx.graphics.getHeight() * 0.070f,
            Gdx.graphics.getHeight() * 0.365f,
            Gdx.graphics.getHeight() * 0.107f,
            Gdx.graphics.getHeight() * 0.675f,
            Gdx.graphics.getHeight() * 0.072f,
    };



    private static float FRAME_DURATION = 1 / 30f;
    private static float BOSS_FRAME_DURATION = 1 / 20f;
    private TextureAtlas impRunningAtlas = Assets.manager.get(Assets.impEnemy, TextureAtlas.class);
    private TextureAtlas warriorRunningAtlas = Assets.manager.get(Assets.warriorEnemy, TextureAtlas.class);
    private TextureAtlas bossRunningAtlas = Assets.manager.get(Assets.levelTwoBossCommander, TextureAtlas.class);
    private TextureRegion currentFrame, currentFrame2, currentFrameBoss;
    private Animation impRunningAnimation, warriorRunningAnimation, bossRunningAnimation;
    private float elapsed_time = 0f;
    Array<TextureAtlas.AtlasRegion> impRunningFrames = impRunningAtlas.findRegions("1_enemies_1_run");
    Array<TextureAtlas.AtlasRegion> warriorRunningFrames = warriorRunningAtlas.findRegions("1_enemies_1_RUN");
    Array<TextureAtlas.AtlasRegion> bossRunningFrames = bossRunningAtlas.findRegions("0_boss_walk");
    ArrayList<ImageButton> towers = new ArrayList<>();
    Array<Float> towerCircle_x = new Array<>();
    Array<Float> towerCircle_y = new Array<>();
    private ArrayList<Boolean> towerCircleBool;
    private BitmapFont font;

    public levelTwoGenerator(final TowerDefense game) {
        this.game = game;
    }
    //TODO Add Lightning Ability Image
    //TODO Add Lightning Ability Effect

    @Override
    public void show() {
        Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).play();
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
        timeAbilitySkin = new Skin(Gdx.files.internal("core/assets/abilities/abilitesSkin/time/timeAbilitySkin.json"), Assets.manager.get(Assets.timeAbilityPack, TextureAtlas.class));
        windowSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.atlas"));
        towerPlacementSkin = new Skin(Gdx.files.internal("background/tower/locations/towerPlacementLevelTwo.json"), new TextureAtlas("background/tower/locations/towerPlacementLevelTwo.atlas"));
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
        TextButton menuExitButton = new TextButton("Exit to Main Menu", uiSkin);
        TextButton gameOverExitButton = new TextButton("Exit to Main Menu", uiSkin);
        TextButton victoryExitButton = new TextButton("Exit to Main menu", uiSkin);
        menuExitButton.setSize(250f,250f);
        gameOverExitButton.setSize(250f,250f);
        victoryExitButton.setSize(500f, 500f);
        pause.add(continueButton).row();
        pause.add(menuExitButton);
        pause.pack();

        gameOverWindow = new Window("Game Over", uiSkin);
        gameOverWindow.add(gameOverExitButton);
        //gameOverWindow.pack();
        gameOverWindow.setVisible(true);
        gameOverWindow.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
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
        ClickListener exitListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                Assets.load();
                while(!Assets.manager.update()){
                    System.out.println(Assets.manager.getProgress() * 100 + "%");
                }
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        };
        menuExitButton.addListener(exitListener);
        gameOverExitButton.addListener(exitListener);
        victoryExitButton.addListener(exitListener);
        //--------------------------------------------------------AbilityMenu----------------------------------------------------//
        //TODO outsource to individual file
        abilityList = new Window("Abilities", uiSkin);
        abilityList.setVisible(false);
        abilityList.padBottom(5);
        abilityList.setPosition(stage.getWidth() / 2f, stage.getHeight());
        abilityList.setMovable(true);
        upgradeAbilityWindow = new Window("Upgrade an ability", uiSkin);

        //--------------------------------------------------------AbilityMenuButtons----------------------------------------------------//
        final ImageButtonStyle fireAbilityStyle = new ImageButtonStyle();
        final ImageButtonStyle thunderAbilityStyle = new ImageButtonStyle();
        final ImageButtonStyle timeStyle = new ImageButtonStyle();
        final ImageButtonStyle explosionAbilityStyle = new ImageButtonStyle();
        final ImageButtonStyle styleTowerPlacementArcher = new ImageButtonStyle();
        final ImageButtonStyle styleTowerPlacementMagician = new ImageButtonStyle();
        final ImageButtonStyle styleTowerPlacementSupport = new ImageButtonStyle();
        fireAbilityStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_up"));
        fireAbilityStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_over"));
        fireAbilityStyle.imageChecked = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_checked"));

        styleTowerPlacementArcher.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("archerTower_default"));
        styleTowerPlacementMagician.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("magicianTower_default"));
        styleTowerPlacementSupport.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("supportTower_default"));

        thunderAbilityStyle.imageUp = thunderAbilitySkin.getDrawable("thunder_up");
        thunderAbilityStyle.imageOver = thunderAbilitySkin.getDrawable("thunder_over");

        explosionAbilityStyle.imageUp = explosionAbilitySkin.getDrawable("explosionButton");
        explosionAbilityStyle.imageOver = explosionAbilitySkin.getDrawable("explosionButtonHover");

        timeStyle.imageUp = timeAbilitySkin.getDrawable("time_up");
        timeStyle.imageOver = timeAbilitySkin.getDrawable("time_over");

        ImageButtonStyle[] towerSkinsArray = {
                styleTowerPlacementSupport,
                styleTowerPlacementArcher,
                styleTowerPlacementMagician
        };
        LinkedList<ImageButtonStyle> towerSkins = new LinkedList<>();
        towerSkins.add(styleTowerPlacementSupport);
        towerSkins.add(styleTowerPlacementArcher);
        towerSkins.add(styleTowerPlacementMagician);

        final ImageButton fireAbility = new ImageButton(fireAbilityStyle);
        final ImageButton thunderAbility = new ImageButton(thunderAbilityStyle);
        final ImageButton explosionAbilityArray = new ImageButton(explosionAbilityStyle);

        final ImageButton fireAbility2 = new ImageButton(fireAbilityStyle);
        final ImageButton thunderAbility2 = new ImageButton(thunderAbilityStyle);
        final ImageButton explosionAbilityArray2 = new ImageButton(explosionAbilityStyle);
        final ImageButton timeAbility = new ImageButton(timeStyle);
        final ImageButton towerPlacementArcher = new ImageButton(styleTowerPlacementArcher);
        final ImageButton towerPlacementMagician = new ImageButton(styleTowerPlacementMagician);
        final ImageButton towerPlacementSupport = new ImageButton(styleTowerPlacementSupport);

        abilityButtonArray.add(fireAbility);
        abilityButtonArray.add(thunderAbility);
        abilityButtonArray.add(explosionAbilityArray);
        abilityButtonArray.add(timeAbility);

        improveAbilityButtonArray.add(fireAbility2);
        improveAbilityButtonArray.add(thunderAbility2);
        improveAbilityButtonArray.add(explosionAbilityArray2);
        //--------------------------------------------------------AbilityMenuButtonFunctionality----------------------------------------------------//
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
                            if(fireAbility.isChecked() && Coin.COINS >= damage.getFireCost()) {
                                super.clicked(event, x, y);
                                Coin.COINS -= damage.getFireCost();
                                createAbility(Gdx.input.getX() - fireBall.getWIDTH() / 2f, 720 - Gdx.input.getY() - fireBall.getHEIGHT() / 2f);
                                Gdx.app.log("Mouse_X", String.valueOf(Gdx.input.getX()));
                                Gdx.app.log("Mouse_Y", String.valueOf(Gdx.input.getY()));
                                Gdx.app.log("Ability", abilityButtonArray.get(0).toString());
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
        fireAbility2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(fireAbility2.isChecked() && coins >= 100){
                    coins -= 100;
                    damage.setFireDamage(damage.getFireDamage() + 15);
                    updateToolTips();
                    //Gdx.app.log("Ability", abilityButtonArray.get(1).toString());
                    fireAbility2.setChecked(false);
                }
            }
        });
        thunderAbility.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(thunderAbility.isChecked() && Coin.COINS >= damage.getThunderCost()){
                    Coin.COINS -= damage.getThunderCost();
                    Gdx.app.log("Monetas", "Moneten: " + Coin.COINS);
                    dealThunderDamage(enemyList);
                    Gdx.app.log("Ability", abilityButtonArray.get(1).toString());
                    thunderAbility.setChecked(false);
                }
            }
        });
        thunderAbility2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(thunderAbility2.isChecked() && coins >= 100){
                    coins -= 100;
                    damage.setThunderDamage(damage.getThunderDamage() + 10);
                    updateToolTips();
                    //Gdx.app.log("Ability", abilityButtonArray.get(1).toString());
                    thunderAbility2.setChecked(false);
                }
            }
        });
        explosionAbilityArray.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(explosionAbilityArray.isChecked()){
                    rangeCircle = true;
                    stage.addListener(placementListener = new ClickListener(Input.Buttons.LEFT) {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(explosionAbilityArray.isChecked() && Coin.COINS >= damage.getExplosionCost()) {
                                super.clicked(event, x, y);
                                Coin.COINS -= damage.getExplosionCost();
                                createExplosionAbility();
                                setUpAbilityTwo(Gdx.input.getX() - explosion.getWIDTH() / 2f, 720 - Gdx.input.getY() - explosion.getHEIGHT() / 2f);
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
        timeAbility.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(timeAbility.isChecked() && Coin.COINS >= damage.getTimeCost()){
                    Coin.COINS -= damage.getThunderCost();
                    manipulateTime(enemyList);
                    timeAbility.setChecked(false);
                }
            }
        });
        for (ImageButton imgButton : abilityButtonArray){
            abilityList.add(imgButton);
        }

        for (ImageButton imgButton2 : improveAbilityButtonArray){
            upgradeAbilityWindow.add(imgButton2);
        }
        upgradeAbilityWindow.pack();
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

            }
        });
        //exitButton.addListener(new ClickListener(){}
        abilityButtonActor = new testActor(abilityButton, Gdx.graphics.getWidth()*0.11f, Gdx.graphics.getHeight()*0.865f, 90,90);
        abilityButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                abilityList.setVisible(!abilityList.isVisible());
                upgradeAbilityWindow.setVisible(false);

            }
        });
        //towerMenue = new testActor(towerMenueIcon, Gdx.graphics.getWidth()/100*11f, Gdx.graphics.getHeight()/100*89f, 90f, 90f);
        upgradeAbilityWindow.setVisible(false);

        upgradeAbilityButtonActor = new testActor(upgradeAbilityButton, Gdx.graphics.getWidth()*0.21f, Gdx.graphics.getHeight()*0.865f, 90,90);
        upgradeAbilityButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                /*
                damage.setThunderDamage(damage.getThunderDamage() + 5f);
                thunderAbilityToolTip = "Deals "+ (int)damage.getThunderDamage() + " Damage to all enemies\nCost: "+ (int)damage.getThunderCost();
                enemyCount = 0;
                Gdx.app.log("Thunder Damage", String.valueOf(damage.getThunderDamage()));

                 */
                upgradeAbilityWindow.setVisible(!upgradeAbilityWindow.isVisible());
                abilityList.setVisible(false);

            }
        });
        


        upgradeAbilityWindow.setSize(300, 100);
        upgradeAbilityWindow.setPosition(stage.getWidth() / 2 - tower.getWidth() / 2, 720 - 50);
        Gdx.input.setInputProcessor(stage);

        ImageButtonStyle placementStyle = new ImageButtonStyle();
        placementStyle.imageUp = towerPlacementSkin.getDrawable("placement_up");
        placementStyle.imageOver = towerPlacementSkin.getDrawable("placement_hover");


        ArrayList<Integer> coinCost = new ArrayList<>();
        coinCost.add(100);
        coinCost.add(250);
        coinCost.add(500);
        towerName = new ArrayList<>();
        towerName.add("SupportTower");
        towerName.add("ArcherTower");
        towerName.add("MagicTower");

        towerMenuList = towerGeneration.TowerMenuWindow(towerList, "towerList", towerSkinsArray, stage);
        towerGeneration.setTowerLocation(towerLocation_x, towerLocation_y);
        towerGeneration.setTowerStyle(placementStyle);
        towerGeneration.setTowerMenuList(towerList);
        towerGeneration.createTowers(towerMenuList, 123, 70, coinCost, towerName, towerSkins, stage, 150f);
        ArrayList<Float> towerDamage = new ArrayList<>();
        towerDamage.add(0.02f);
        towerDamage.add(0.05f);
        towerDamage.add(0.07f);
        towerGeneration.setTowerDamage(towerDamage);

        //font for coins, health etc.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/riffic-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font12 = generator.generateFont(parameter);
        generator.dispose();
        font = new BitmapFont();
        font.getData().setScale(2);
        batch = new SpriteBatch();
        level = new LevelTwo();
        level.createBackground();
        stage.addActor(pauseButtonActor);
        stage.addActor(abilityButtonActor);
        stage.addActor(upgradeAbilityButtonActor);
        stage.addActor(backgroundHUD);
        stage.addActor(pause);
        stage.addActor(abilityList);
        stage.addActor(victoryWindow);
        stage.addActor(upgradeAbilityWindow);

        impLinkedList = new LinkedList<>();
        warriorLinkedList = new LinkedList<>();
        bossLinkedList = new LinkedList<>();
        enemyList.add(impLinkedList);
        enemyList.add(warriorLinkedList);
        enemyList.add(bossLinkedList);
        createAllEnemies();
        updateToolTips();
        impRunningAnimation = new Animation(FRAME_DURATION, impRunningFrames, Animation.PlayMode.LOOP);
        warriorRunningAnimation = new Animation(BOSS_FRAME_DURATION, warriorRunningFrames, Animation.PlayMode.LOOP);

        bossRunningAnimation = new Animation(BOSS_FRAME_DURATION, bossRunningFrames, Animation.PlayMode.LOOP);

        spawnBoss();
    }



    @Override
    public void render(float delta) {
        level.renderBackground();
        elapsed_time += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) impRunningAnimation.getKeyFrame(elapsed_time);
        currentFrame2 = (TextureRegion) warriorRunningAnimation.getKeyFrame(elapsed_time);
        currentFrameBoss = (TextureRegion) bossRunningAnimation.getKeyFrame(elapsed_time);

        stage.act(Gdx.graphics.getDeltaTime());
        /*if(upgradeAbilityButtonActor.isPressed()) {
            updateToolTips();
        }*/
        batch.begin();
        if (!isPaused){
            towerGeneration.checkTowerRange(enemyList, towerName);
            if(enemyCount > 0) {
                spawnEnemies(Gdx.graphics.getDeltaTime());
                makeEnemiesMove(delta, impLinkedList, LevelTwo.levelTwoTopRightPath(), currentFrame, 3);
                makeEnemiesMove(delta, warriorLinkedList, LevelTwo.levelTwoBottomPath(), currentFrame2, 5);
            }
            else{
                clearEnemies();
                moveBoss(delta);
            }
            updateAllEntities();
            checkHealth();
            drawAllEntities();
            checkAbilityCollision(fireBallAbility, enemyList,damage.getFireDamage(), 50);
            checkAbilityCollision(explosionAbility, enemyList,damage.getExplosionDamage(), 100);
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if(rangeCircle){
            drawCircle();
        }
        towerGeneration.drawTowerCollisionCircle(150f);
        Gdx.gl.glDisable(GL20.GL_BLEND);
        stage.draw();
        font12.draw(batch, "Coins: " + Coin.COINS, 25, 590);
        font12.draw(batch, "Health: " + health, 25, 550);
        if(enemyCount == 0){
            Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).stop();
            Assets.manager.get(Assets.bossLevelOneMusic, Music.class).play();
            font12.draw(batch, "Boss HP: " + (int)bossLinkedList.get(0).getLifeCount(), 25, 510);
        }else{
            font12.draw(batch, "Enemies left: " + enemyCount, 25, 510);
        }
        batch.end();
    }
    //DO NOT TOUCH; DANGER IMMINENT

    public void updateToolTips(){
        fireAbilityToolTip = "Deals "+ (int)damage.getFireDamage() + " Damage against 1 Enemy\nCost: "+ (int)damage.getFireCost() + " OptiCoins";
        thunderAbilityToolTip = "Deals "+ (int)damage.getThunderDamage() + " Damage to all enemies\nCost: "+ (int)damage.getThunderCost() + " OptiCoins";
        explosionAbilityToolTip = "Immediately kills one enemy.\nCost: " + (int)damage.getExplosionCost() + " OptiCoins";
        timeAbilityToolTip = "Manipulates Time, dealing "+ (int)damage.getTimeDamage() +" Damage.\nPosses a hidden ability.\nCost: " + (int)damage.getTimeCost() + " OptiCoins";
        abilityButtonArray.get(0).addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(1).addListener(new TextTooltip(thunderAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(2).addListener(new TextTooltip(explosionAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(3).addListener(new TextTooltip(timeAbilityToolTip, toolTipManager, uiSkin));
        towerGeneration.updateTowerToolTips(supportTowerToolTip, archerTowerToolTip, magicianTowerToolTip, toolTipManager);

        improveAbilityButtonArray.get(0).addListener(new TextTooltip(improveFireAbilityToolTip, toolTipManager, uiSkin));
        improveAbilityButtonArray.get(1).addListener(new TextTooltip(improveThunderAbilityTooltip, toolTipManager, uiSkin));
        improveAbilityButtonArray.get(2).addListener(new TextTooltip(improveExplosionAbilityTooltip, toolTipManager, uiSkin));
    }
    public void checkAbilityCollision(PathfindingEnemy abl, LinkedList<LinkedList> enemyList,float damage, int coin) {
        for (Iterator<PathfindingEnemy> abilityIterator = ability.iterator(); abilityIterator.hasNext(); ) {
            PathfindingEnemy fire = abilityIterator.next();
            for(Iterator<LinkedList> enemyIterator = enemyList.iterator(); enemyIterator.hasNext();) {
                LinkedList list = enemyIterator.next();
                for (Iterator<PathfindingEnemy> iterator = list.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy enemy = iterator.next();
                    if (enemy.getBoundingRectangle().overlaps(fire.getBoundingRectangle())) {
                        enemy.setLifeCount(enemy.getLifeCount() - damage);
                        enemy.timeOfDmgTaken = enemy.timeAlive;
                        Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                        abilityIterator.remove();
                    }
                    if (enemy.getLifeCount() <= 0) {
                        iterator.remove();
                        Coin.COINS += coin;
                        if(enemyCount > 0){
                            enemyCount -= 1;
                        }
                    }
                }
            }
        }
    }

    public void dealThunderDamage(LinkedList<LinkedList> enemyList) {
        for(Iterator<LinkedList> enemyIterator = enemyList.iterator(); enemyIterator.hasNext();) {
            LinkedList list = enemyIterator.next();
            for (Iterator<PathfindingEnemy> iterator = list.iterator(); iterator.hasNext(); ) {
                PathfindingEnemy enemy = iterator.next();
                enemy.setLifeCount(enemy.getLifeCount() - damage.getThunderDamage());
                enemy.timeOfDmgTaken = enemy.timeAlive;
                Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                if (enemy.getLifeCount() <= 0) {
                    iterator.remove();
                    Coin.COINS += 10;
                }
            }
        }
    }
    public void manipulateTime(LinkedList<LinkedList> enemyList){
        for(Iterator<LinkedList> enemyIterator = enemyList.iterator(); enemyIterator.hasNext();) {
            LinkedList list = enemyIterator.next();
            for (Iterator<PathfindingEnemy> iterator = list.iterator(); iterator.hasNext(); ) {
                PathfindingEnemy enemy = iterator.next();
                enemy.setLifeCount(enemy.getLifeCount() - damage.getTimeDamage());
                enemy.timeOfDmgTaken = enemy.timeAlive;
                Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                if(enemyCount == 0){
                    enemy.setLifeCount(enemy.getLifeCount() - damage.getTimeDamage());
                    isBossAlive = true;
                }
                if (enemy.getLifeCount() <= 0) {
                    iterator.remove();
                    Coin.COINS += 10;
                    if(enemyCount > 0){
                        enemyCount -= 1;
                    }
                }
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
    public void createAbility(float x, float y){
        for(ImageButton imageButton: abilityButtonArray){
            if(imageButton.isChecked()){
                Gdx.app.log("Create Ability", abilityButtonArray.toString());
                ability.add(new PathfindingEnemy(fireBall.idleFrame(), abilityMovementPath(x,y),0, Gdx.graphics.getHeight() * 0.8f));
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
    public void setUpAbilityTwo(float x, float y){
        ability = new Array<>();
        explosionAbility = new PathfindingEnemy(explosion.idleFrame(), abilityMovementPath(x, y));
        explosionAbility.setPosition(x, y);
        ability.add(explosionAbility);
    }
    public void createAllEnemies(){
        imp = new Imp();
        warrior = new Warrior();
        boss = new LevelTwoBoss();
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
    public void makeEnemiesMove(float delta, LinkedList<PathfindingEnemy> enemy ,Array<Vector2> path,TextureRegion frame, int damage) {
        for (Iterator<PathfindingEnemy> iterator = enemy.iterator(); iterator.hasNext();) {
            PathfindingEnemy monster = iterator.next();
            monster.updateAnimation(batch, path, delta, frame);
            //remove entity if life is less than 0, and add 100 coins
            if (monster.getLifeCount() <= 0) {
                iterator.remove();
                Coin.COINS += 100;
                enemyCount -= 1;
            }
            //remove entity if is out of bounds, and get player damage
            if(monster.getX() > Gdx.graphics.getWidth()){
                iterator.remove();
                health -= damage;
                enemyCount -= 1;
            }
        }
    }
    public void moveBoss(float delta){
        bossLinkedList.get(0).updateBossAnimation(batch, LevelTwo.levelTwoTopPath(), delta, currentFrameBoss, 35);
        if(enemyCount == 0){
            if(bossLinkedList.get(0).getLifeCount() <= 0 && !isBossAlive){
                bossLinkedList.get(0).setLifeCount(1000);
            }else if(bossLinkedList.get(0).getLifeCount() <= 0 && isBossAlive){
                isPaused = true;
                bossLinkedList.get(0).setLifeCount(0);
                victoryWindow.setVisible(true);
            }else if(bossLinkedList.get(0).getX() > Gdx.graphics.getWidth()){
                health -= 50;
                if(health > 0){
                    isPaused = true;
                    victoryWindow.setVisible(true);
                    Assets.manager.get(Assets.victorySound, Sound.class).play(2f);
                    Gdx.app.log("Game Won", "");
                }
            }
        }
    }

    public void clearEnemies(){
        for (Iterator<PathfindingEnemy> impIterator = impLinkedList.iterator(); impIterator.hasNext();) {
            PathfindingEnemy imp = impIterator.next();
            impIterator.remove();
        }
        for(Iterator<PathfindingEnemy> warriorIterator = warriorLinkedList.iterator(); warriorIterator.hasNext();) {
            PathfindingEnemy warrior = warriorIterator.next();
            warriorIterator.remove();
        }
    }
    public void checkHealth(){
        if(health <= 0){
            health = 0;
            stage.addActor(gameOverWindow);
            Assets.manager.get(Assets.gameOverSound, Sound.class).play(0.10f);
            isPaused = true;
        }
    }

    public void spawnEnemies(float deltaTime){
        enemySpawnTimer += deltaTime;
        enemySpawnTimer2 += deltaTime;
        if(enemyCount >= 2){
            if(enemySpawnTimer > timeBetweenEnemySpawns){
                impLinkedList.add(new PathfindingEnemy(imp.idleFrame(), 20, LevelTwo.levelTwoTopRightPath()));
                enemySpawnTimer -= timeBetweenEnemySpawns;
            }
            if(enemySpawnTimer2 > timeBetweenEnemySpawns2){
                warriorLinkedList.add(new PathfindingEnemy(warrior.idleFrame(), 100, LevelTwo.levelTwoBottomPath()));
                enemySpawnTimer2 -= timeBetweenEnemySpawns2;
            }
        }
    }
    public void spawnBoss(){
        bossLinkedList.add(new PathfindingEnemy(boss.idleFrame(), 1000, LevelTwo.levelTwoTopPath()));
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
        Assets.levelTwoDispose();
    }
}
