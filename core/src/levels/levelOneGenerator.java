package levels;

import MainRef.Assets;
import MainRef.Prefs;
import MainRef.TowerDefense;
import abilities.Ability;
import abilities.Explosion;
import enemy.bossSkeleton.LevelOneBoss;
import enemy.scorpionEntity.Scorpion;
import enemy.wizardEntity.Wizard;
import levels.menu.testActor;
import levels.menu.MainMenuScreen;
import modularAssets.TowerGeneration;

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
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.LinkedList;
import java.util.*;

public class levelOneGenerator implements Screen {
    final TowerDefense game;
    Prefs prefs = new Prefs();
    testActor pauseButtonActor, abilityButtonActor, upgradeAbilityButtonActor, backgroundHUD, backgroundHUD2;
    TowerGeneration towerGeneration = new TowerGeneration();
    Window pause, abilityList, tower, gameOverWindow, victoryWindow, towerMenuList, upgradeAbilityWindow;
    Stage stage;
    TooltipManager toolTipManager;
    ShapeRenderer shapeRenderer;
    ClickListener placementListener;
    SpriteBatch batch;
    LevelOne level;
    PathfindingEnemy fireBallAbility, fireBallAbility2;
    Scorpion scorpion;
    Wizard wizard;
    LevelOneBoss boss;
    Ability fireBall = new Ability(), damage = new Ability();
    Explosion explosion;
    private Array<Vector2> abilityPath;
    private boolean isPaused;
    private LinkedList<PathfindingEnemy> scorpionLinkedList, wizardLinkedList, treeLinkedList, saplingLinkedList;
    private float enemySpawnTimer, timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer2, timeBetweenEnemySpawns2 = 3f;
    private boolean rangeCircle = false;
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/button_pause.png";
    private String abilityButton = "core/assets/abilities/abilitesSkin/btton_abilities.png";
    private String upgradeAbilityButton = "core/assets/abilities/abilitesSkin/upgradeButton.png";
    private Skin uiSkin, thunderAbilitySkin, windowSkin, towerPlacementSkin, explosionAbilitySkin;
    private String backgroundGameHUD = "core/assets/normal_window.png";
    private int enemyCount = 50;
    private int saplingCount = 0;
    private int bossUpdate = 0;
    BitmapFont font12;
    //TODO
    LinkedList<ImageButton> towerList = new LinkedList<>();
    ArrayList<String> towerName;
    LinkedList<LinkedList> enemyList = new LinkedList<>();
    Array<Array> saplingBossPath = new Array<>();
    Array<PathfindingEnemy> ability = new Array<>();
    Array<ImageButton> abilityButtonArray = new Array();
    Array<ImageButton> improveAbilityButtonArray = new Array();
    private String fireAbilityToolTip, thunderAbilityToolTip, explosionAbilityToolTip;
    //tower tooltips
    private String archerTowerToolTip = "Deals ?? Damage per second.\nCost: 250 OptiCoins";
    private String magicianTowerToolTip = "Deals ??? Damage per second.\nCost: 500 OptiCoins";
    private String supportTowerToolTip = "Deals ? Damage per second.\nCost: 100 OptiCoins";
    private String upgradeAbilityToolTip = "Upgrades every ability. Cost: 500 OptiCoins";

    private String improveFireAbilityToolTip = "+ 15 Fire damage permanently. \nCost: 100 OPC";
    private String improveThunderAbilityTooltip = "+ 10 Thunder damage permanently. \nCost: 100 OPC";
    private String improveExplosionAbilityTooltip = "-500 overall cost. \nCost: 1000 OPC";

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
    private BitmapFont font;

    public levelOneGenerator(final TowerDefense game) {
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
        thunderAbilitySkin = new Skin(Gdx.files.internal("core/assets/abilities/abilitesSkin/thunder/thunderAbilitySkin.json") ,new TextureAtlas("core/assets/abilities/abilitesSkin/thunder/thunderAbility.atlas"));
        explosionAbilitySkin = new Skin(Gdx.files.internal("core/assets/abilities/abilitesSkin/explosion/explosionButton/explosionButton.json"), new TextureAtlas("core/assets/abilities/abilitesSkin/explosion/explosionButton/explosionButton.atlas"));
        windowSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.atlas"));
        towerPlacementSkin = new Skin(Gdx.files.internal("background/tower/locations/towerPlacement.json"), new TextureAtlas("background/tower/locations/towerPlacement.atlas"));
        //----------------------------------------------------------PauseMenu------------------------------------------------------//
        //TODO outsource to a different file
        WindowStyle windowStyle = new WindowStyle();
        windowStyle.background = windowSkin.getDrawable("default-window");
        pause = new Window("Pause", uiSkin);
        pause.setVisible(false);
        pause.padTop(64);
        pause.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);
        pause.setPosition(stage.getWidth() / 2 - pause.getWidth() / 2, stage.getHeight() / 2 - pause.getHeight() / 2);
        pause.setMovable(false);
        //----------------------------------------------------------PauseMenuButtons------------------------------------------------------//
        TextButton continueButton = new TextButton("Continue the Game",uiSkin);
        TextButton menuExitButton = new TextButton("Exit to Main Menu", uiSkin);
        TextButton gameOverExitButton = new TextButton("Exit to Main Menu", uiSkin);
        TextButton victoryExitButton = new TextButton("Exit to Main menu", uiSkin);
        continueButton.setSize(600f,600f);
        menuExitButton.setSize(250f,250f);
        gameOverExitButton.setSize(250f,250f);
        victoryExitButton.setSize(500f, 500f);
        pause.add(continueButton).row();
        pause.add(menuExitButton);

        gameOverWindow = new Window("Game Over", uiSkin);
        gameOverWindow.add(gameOverExitButton);
        //gameOverWindow.pack();
        gameOverWindow.setVisible(true);
        gameOverWindow.setPosition(stage.getWidth() * 0.32f, stage.getHeight() * 0.3f);
        gameOverWindow.setSize(stage.getWidth() / 2.5f, stage.getHeight() / 2.5f);

        victoryWindow = new Window("VICTORY", uiSkin);
        if(prefs.getLevelsFinished() < 1){
            victoryWindow.add("Unlocked Level 2");
        }
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
        abilityList = new Window("Abilities", uiSkin);
        abilityList.setVisible(false);
        abilityList.padBottom(5);
        abilityList.setPosition(stage.getWidth() / 2f, stage.getHeight());
        abilityList.setMovable(true);
        upgradeAbilityWindow = new Window("Upgrade an ability", uiSkin);
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
        ImageButtonStyle placementStyle = new ImageButtonStyle();
        placementStyle.imageUp = towerPlacementSkin.getDrawable("placement_up");
        placementStyle.imageOver = towerPlacementSkin.getDrawable("placement_hover");
        styleTowerPlacementArcher.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("archerTower_default"));
        styleTowerPlacementMagician.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("magicianTower_default"));
        styleTowerPlacementSupport.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("supportTower_default"));

        style2.imageUp = thunderAbilitySkin.getDrawable("thunder_up");
        style2.imageOver = thunderAbilitySkin.getDrawable("thunder_over");

        styleExplosionAbility.imageUp = explosionAbilitySkin.getDrawable("explosionButton");
        styleExplosionAbility.imageOver = explosionAbilitySkin.getDrawable("explosionButtonHover");


        ImageButtonStyle[] towerSkins = {
                styleTowerPlacementSupport,
                styleTowerPlacementArcher,
                styleTowerPlacementMagician
        };
        LinkedList<ImageButtonStyle> towerSkinsLinkedList = new LinkedList<>();
        towerSkinsLinkedList.add(styleTowerPlacementSupport);
        towerSkinsLinkedList.add(styleTowerPlacementArcher);
        towerSkinsLinkedList.add(styleTowerPlacementMagician);


        final ImageButton fireAbility = new ImageButton(style);
        final ImageButton thunderAbility = new ImageButton(style2);
        final ImageButton explosionAbilityArray = new ImageButton(styleExplosionAbility);

        final ImageButton upgradeFireAbiltyButton = new ImageButton(style);
        final ImageButton upgradeThunderAbilityButton = new ImageButton(style2);
        final ImageButton upgradeExplosionAbilityButton = new ImageButton(styleExplosionAbility);

        final ImageButton towerPlacementArcher = new ImageButton(styleTowerPlacementArcher);
        final ImageButton towerPlacementMagician = new ImageButton(styleTowerPlacementMagician);
        final ImageButton towerPlacementSupport = new ImageButton(styleTowerPlacementSupport);

        improveAbilityButtonArray.add(upgradeFireAbiltyButton);
        improveAbilityButtonArray.add(upgradeThunderAbilityButton);
        improveAbilityButtonArray.add(upgradeExplosionAbilityButton);

        abilityButtonArray.add(fireAbility);
        abilityButtonArray.add(thunderAbility);
        abilityButtonArray.add(explosionAbilityArray);

        /*
        upgradeAbilityButtonArray.add(towerPlacementArcher);
        upgradeAbilityButtonArray.add(towerPlacementMagician);
        upgradeAbilityButtonArray.add(towerPlacementSupport);

         */

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
                            if(fireAbility.isChecked() && towerGeneration.getCoins() >= damage.getFireCost()) {
                                super.clicked(event, x, y);
                                towerGeneration.setCoins(towerGeneration.getCoins() - damage.getFireCost());
                                createAbility(Gdx.input.getX() - fireBall.getWIDTH() / 2f, 720 - Gdx.input.getY() - fireBall.getHEIGHT() / 2f);
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

        upgradeFireAbiltyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(upgradeFireAbiltyButton.isChecked() && towerGeneration.getCoins() >= 100){
                    towerGeneration.setCoins(towerGeneration.getCoins() - 100);
                    damage.setFireDamage(damage.getFireDamage() + 15);
                    updateToolTips();
                    upgradeFireAbiltyButton.setChecked(false);
                }
            }
        });
        thunderAbility.addListener(new TextTooltip(thunderAbilityToolTip, toolTipManager, uiSkin));
        thunderAbility.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(thunderAbility.isChecked() && towerGeneration.getCoins()>= damage.getThunderCost()){
                    towerGeneration.setCoins(towerGeneration.getCoins() - damage.getThunderCost());
                    dealThunderDamage();
                    Gdx.app.log("Ability", abilityButtonArray.get(1).toString());
                    thunderAbility.setChecked(false);
                }
            }
        });
        upgradeThunderAbilityButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(upgradeThunderAbilityButton.isChecked() && towerGeneration.getCoins() >= 100){
                    towerGeneration.setCoins(towerGeneration.getCoins() - 100);
                    damage.setThunderDamage(damage.getThunderDamage() + 10);
                    updateToolTips();
                    upgradeThunderAbilityButton.setChecked(false);
                }
            }
        });

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
                            if(explosionAbilityArray.isChecked() && towerGeneration.getCoins()>= damage.getExplosionCost()) {
                                super.clicked(event, x, y);
                                towerGeneration.setCoins(towerGeneration.getCoins() - damage.getExplosionCost());
                                //createAbility();
                                createExplosionAbility();
                                setUpAbilityTwo(Gdx.input.getX() - 40, 720 - Gdx.input.getY() - 50);
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
        upgradeExplosionAbilityButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                if(upgradeExplosionAbilityButton.isChecked() && towerGeneration.getCoins() >= 1000){
                    towerGeneration.setCoins(towerGeneration.getCoins() - 1000);
                    damage.setExplosionCost(damage.getExplosionCost() - 500);
                    updateToolTips();
                    upgradeExplosionAbilityButton.setVisible(false);
                }
                upgradeExplosionAbilityButton.setChecked(false);
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
                upgradeAbilityWindow.setVisible(false);
            }
        });

        upgradeAbilityButtonActor = new testActor(upgradeAbilityButton, Gdx.graphics.getWidth()*0.21f, Gdx.graphics.getHeight()*0.865f, 90,90);

        upgradeAbilityWindow.setVisible(false);
        upgradeAbilityWindow.setMovable(false);
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

        ArrayList<Integer> coinCost = new ArrayList<>();
        coinCost.add(100);
        coinCost.add(250);
        coinCost.add(500);
        towerName = new ArrayList<>();
        towerName.add("SupportTower");
        towerName.add("ArcherTower");
        towerName.add("MagicTower");

        towerMenuList = towerGeneration.TowerMenuWindow(towerList, "towerList", towerSkins, stage);
        towerGeneration.setTowerLocation(towerLocation_x, towerLocation_y);
        towerGeneration.setTowerStyle(placementStyle);
        towerGeneration.setTowerMenuList(towerList);
        towerGeneration.createTowers(towerMenuList, 123, 70, coinCost, towerName, towerSkinsLinkedList, stage, 150f);
        ArrayList<Float> towerDamage = new ArrayList<>();
        towerDamage.add(0.02f);
        towerDamage.add(0.05f);
        towerDamage.add(0.07f);
        towerGeneration.setTowerDamage(towerDamage);

        upgradeAbilityWindow.setSize(300, 100);
        upgradeAbilityWindow.setPosition(stage.getWidth() / 2 - tower.getWidth() / 2, 720 - 50);
        Gdx.input.setInputProcessor(stage);

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
        stage.addActor(pauseButtonActor);
        stage.addActor(abilityButtonActor);
        stage.addActor(upgradeAbilityButtonActor);
        stage.addActor(backgroundHUD);
        stage.addActor(pause);
        stage.addActor(abilityList);
        stage.addActor(tower);
        stage.addActor(victoryWindow);
        stage.addActor(upgradeAbilityWindow);
        scorpionLinkedList = new LinkedList<>();
        wizardLinkedList = new LinkedList<>();
        treeLinkedList = new LinkedList<>();
        saplingLinkedList = new LinkedList<>();
        enemyList.add(scorpionLinkedList);
        enemyList.add(wizardLinkedList);
        enemyList.add(treeLinkedList);
        enemyList.add(saplingLinkedList);
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
            towerGeneration.checkTowerRange(enemyList, towerName);
            if(enemyCount > 0) {
                spawnEnemies(Gdx.graphics.getDeltaTime());
                makeEnemiesMove(delta, scorpionLinkedList, LevelOne.levelOneBottomPath(), currentFrame, 3);
                makeEnemiesMove(delta, wizardLinkedList, LevelOne.levelOneTopPath(), currentFrame2, 5);
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
        showRangeCircle();
        stage.draw();
        font12.draw(batch, "Coins: " + towerGeneration.getCoins(), 25, 590);
        font12.draw(batch, "Health: " + health, 25, 550);
        if(enemyCount == 0){
            Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).stop();
            Assets.manager.get(Assets.bossLevelOneMusic, Music.class).play();
            if(bossUpdate == 0){
                font12.draw(batch, "Boss HP: " + (int)treeLinkedList.get(0).getLifeCount(), 25, 510);
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
public void showRangeCircle(){
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    if(rangeCircle){
        drawCircle();
    }
    towerGeneration.drawTowerCollisionCircle(150f);
    Gdx.gl.glDisable(GL20.GL_BLEND);
}
    public void updateToolTips(){
        fireAbilityToolTip = "Deals "+ damage.getFireDamage() + " Damage against 1 Enemy\nCost: "+ damage.getFireCost() + " OptiCoins";
        thunderAbilityToolTip = "Deals "+ damage.getThunderDamage() + " Damage to all enemies\nCost: "+ damage.getThunderCost() + " OptiCoins";
        explosionAbilityToolTip = "Immediately kills one enemy.\nCost: " + damage.getExplosionCost() + " OptiCoins";
        abilityButtonArray.get(0).addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(1).addListener(new TextTooltip(thunderAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(2).addListener(new TextTooltip(explosionAbilityToolTip, toolTipManager, uiSkin));
        improveAbilityButtonArray.get(0).addListener(new TextTooltip(improveFireAbilityToolTip, toolTipManager, uiSkin));
        improveAbilityButtonArray.get(1).addListener(new TextTooltip(improveThunderAbilityTooltip, toolTipManager, uiSkin));
        improveAbilityButtonArray.get(2).addListener(new TextTooltip(improveExplosionAbilityTooltip, toolTipManager, uiSkin));
        towerGeneration.updateTowerToolTips(supportTowerToolTip, archerTowerToolTip, magicianTowerToolTip, toolTipManager);

    }
    public void checkAbilityCollision(PathfindingEnemy abl, float damage, int coin) {
        for (Iterator<PathfindingEnemy> abilityIterator = ability.iterator(); abilityIterator.hasNext(); ) {
            PathfindingEnemy fire = abilityIterator.next();
            for (Iterator<PathfindingEnemy> iterator = scorpionLinkedList.iterator(); iterator.hasNext(); ) {
                PathfindingEnemy enemy = iterator.next();
                if (enemy.getBoundingRectangle().overlaps(fire.getBoundingRectangle())) {
                    enemy.setLifeCount(enemy.getLifeCount() - damage);
                    enemy.timeOfDmgTaken = enemy.timeAlive;
                    Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                    abilityIterator.remove();
                }
                if (enemy.getLifeCount() <= 0) {
                    iterator.remove();
                    towerGeneration.setCoins(towerGeneration.getCoins() + coin);
                    enemyCount -= 1;
                }
            }
            for (Iterator<PathfindingEnemy> iterator = wizardLinkedList.iterator(); iterator.hasNext(); ) {
                PathfindingEnemy enemy = iterator.next();
                if (enemy.getBoundingRectangle().overlaps(fire.getBoundingRectangle())) {
                    enemy.setLifeCount(enemy.getLifeCount() - damage);
                    enemy.timeOfDmgTaken = enemy.timeAlive;
                    Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                    abilityIterator.remove();
                }
                if (enemy.getLifeCount() <= 0) {
                    iterator.remove();
                    towerGeneration.setCoins(towerGeneration.getCoins() + coin);
                    enemyCount -= 1;
                }
            }
            if(enemyCount == 0){
                if(treeLinkedList.get(0).getBoundingRectangle().overlaps(fire.getBoundingRectangle())){
                    treeLinkedList.get(0).setLifeCount(treeLinkedList.get(0).getLifeCount() - (damage + 10));
                    abilityIterator.remove();
                }
                for (Iterator<PathfindingEnemy> iterator = saplingLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy sapling = iterator.next();
                    if (sapling.getBoundingRectangle().overlaps(fire.getBoundingRectangle())) {
                        sapling.setLifeCount(sapling.getLifeCount() - (damage + 195));
                        Gdx.app.log(String.valueOf(sapling), String.valueOf(sapling.getLifeCount()));
                        abilityIterator.remove();
                    }
                    if (sapling.getLifeCount() <= 0) {
                        iterator.remove();
                        saplingCount += 1;
                        towerGeneration.setCoins(towerGeneration.getCoins() + coin);
                    }
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
                towerGeneration.setCoins(towerGeneration.getCoins() + 10);
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
                towerGeneration.setCoins(towerGeneration.getCoins() + 10);
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
                towerGeneration.setCoins(towerGeneration.getCoins() + 10);
            }
        }

        for (Iterator<PathfindingEnemy> iterator = wizardLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy enemy = iterator.next();
            enemy.setLifeCount(enemy.getLifeCount() - damage.getExplosionDamage());
            enemy.timeOfDmgTaken = enemy.timeAlive;
            Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
            if (enemy.getLifeCount() <= 0) {
                iterator.remove();
                towerGeneration.setCoins(towerGeneration.getCoins() + 10);
            }
        }
        for (Iterator<PathfindingEnemy> iterator = saplingLinkedList.iterator(); iterator.hasNext();){
            PathfindingEnemy sapling = iterator.next();
            sapling.setLifeCount(sapling.getLifeCount() - (damage.getThunderDamage() + 50));
            if (sapling.getLifeCount() <= 0) {
                iterator.remove();
                towerGeneration.setCoins(towerGeneration.getCoins() + 10);
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

    public void createAbility(float x, float y){
        for(ImageButton imageButton: abilityButtonArray){
            if(imageButton.isChecked()){
                Gdx.app.log("Create Ability", abilityButtonArray.toString());
                ability.add(new PathfindingEnemy(fireBall.idleFrame(), abilityMovementPath(x,y),0, Gdx.graphics.getHeight() * 0.8f));
            }
        }
    }
    public void checkFireAbility(float x, float y) {
        for (Iterator<PathfindingEnemy> abilityIterator = ability.iterator(); abilityIterator.hasNext(); ) {
            PathfindingEnemy fire = abilityIterator.next();
            if(fire.getX() == x && fire.getY() == y){
                abilityIterator.remove();
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
        abilityPath = new Array<>();
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

    public void makeEnemiesMove(float delta, LinkedList<PathfindingEnemy> enemy ,Array<Vector2> path,TextureRegion frame, int damage) {
        for (Iterator<PathfindingEnemy> iterator = enemy.iterator(); iterator.hasNext();) {
            PathfindingEnemy monster = iterator.next();
            monster.updateAnimation(batch, path, delta, frame);
            //remove entity if life is less than 0, and add 100 coins
            if (monster.getLifeCount() <= 0) {
                iterator.remove();
                towerGeneration.setCoins(towerGeneration.getCoins() + 100);
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
        if(treeLinkedList.get(0).getX() > 500 || treeLinkedList.get(0).getLifeCount() <= 0){
            treeLinkedList.get(0).setBossPosition(-250f, 0);
            saplingLinkedList.add(new PathfindingEnemy(boss.idleFrame(), 600, LevelOne.levelOneTopBossPath()));
            saplingLinkedList.add(new PathfindingEnemy(boss.idleFrame(), 600, LevelOne.levelOneBottomBossPath()));
            saplingBossPath.add(LevelOne.levelOneTopBossPath());
            saplingBossPath.add(LevelOne.levelOneBottomBossPath());
            bossUpdate +=1;
        }
        else {
            treeLinkedList.get(0).updateBossAnimation(batch, LevelOne.levelOneTopPath(), delta, currentFrameBoss, 35);
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
                towerGeneration.setCoins(towerGeneration.getCoins() + 1000);
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
            prefs.setLevelsFinished(1);
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
        treeLinkedList.add(new PathfindingEnemy(boss.idleFrame(), 1000, LevelOne.levelOneTopPath()));
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