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

import com.badlogic.gdx.graphics.Color;
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
import enemy.bossSkeleton.LevelThreeBoss;
import enemy.magicianEnemy.Magician;
import enemy.yetiEnemy.Yeti;
import levels.menu.testActor;
import levels.menu.MainMenuScreen;
import levels.utils.Coin;
import modularAssets.TowerGeneration;
import sun.awt.image.ImageWatched;

import java.security.Key;
import java.util.LinkedList;
import java.util.*;

public class levelThreeGenerator implements Screen {
    final TowerDefense game;
    TowerGeneration towerGeneration = new TowerGeneration(), pillarGeneration = new TowerGeneration();
    testActor pauseButtonActor, abilityButtonActor, upgradeAbilityButtonActor;
    Window pause, abilityList, gameOverWindow, victoryWindow, towerMenuList, pillarMenuList;
    Stage stage;
    TooltipManager toolTipManager;
    ShapeRenderer shapeRenderer;
    ClickListener placementListener;
    SpriteBatch batch;
    LevelThree level;
    LevelThreeBoss boss;
    Magician magician;
    Yeti yeti;
    PathfindingEnemy bossPath, fireBallAbility, fireBallAbility2;
    Ability fireBall = new Ability(), damage = new Ability();
    Explosion explosion;
    private Array<Vector2> abilityPath;
    private boolean isPaused;
    Array<Array> iceWarriorPath = new Array<>();
    private float enemySpawnTimer, enemySpawnTimer2, enemySpawnTimer3, timeBetweenEnemySpawns = 3f, timeBetweenEnemySpawns2 = 3f, timeBetweenEnemySpawns3 = 5f;
    private boolean rangeCircle = false;
    LinkedList<ShapeRenderer> pillarRangeShape;
    LinkedList<ShapeRenderer> towerRangeShape;
    private String pauseButton = "menuAssets/mainMenuAssets/buttonAssets/button_pause.png";
    private String abilityButton = "core/assets/abilities/abilitesSkin/btton_abilities.png";
    private String upgradeAbilityButton = "core/assets/abilities/abilitesSkin/upgradeButton.png";
    private Skin uiSkin, fireAbilitySkin, thunderAbilitySkin,  windowSkin, towerSkin, explosionAbilitySkin;
    private String backgroundGameHUD = "core/assets/normal_window.png";

    private int enemyCount = 50;
    BitmapFont font12;
    //TODO
    LinkedList<LinkedList> enemyList = new LinkedList<>();
    LinkedList<PathfindingEnemy>magicianLinkedList = new LinkedList<>();
    LinkedList<PathfindingEnemy>yetiLinkedList = new LinkedList<>();
    LinkedList<PathfindingEnemy>iceWarriorLinkedList = new LinkedList<>();
    LinkedList<ImageButton> towerList = new LinkedList<>();
    LinkedList<ImageButton> pillarList = new LinkedList<>();
    ArrayList<String> pillarName, towerName;
    Array<PathfindingEnemy> ability = new Array<>();
    Array<PathfindingEnemy> abilityExplosion = new Array<>();
    Array<PathfindingEnemy> explosions = new Array<>();
    Array<ImageButton> abilityButtonArray = new Array();
    Array<ImageButton> upgradeAbilityButtonArray = new Array();
    private String fireAbilityToolTip, thunderAbilityToolTip, explosionAbilityToolTip;
    //tower tooltips
    private String archerTowerToolTip = "Deals ?? Damage per second.\nCost: 100 OptiCoins";
    private String magicianTowerToolTip = "Deals ??? Damage per second.\nCost: 250 OptiCoins";
    private String supportTowerToolTip = "Deals ? Damage per second.\nCost: 500 OptiCoins";
    private String upgradeAbilityToolTip = "Upgrades every ability. Cost: 500 OptiCoins";

    private String pillarArcherStandardToolTip = "Deals ? Damage per seond.\nCost: 100 OptiCoins";
    private String pillarArcherStrongToolTip = "Deals ?? Damage per seond.\nCost: 250 OptiCoins";
    private String pillarCrossbowToolTip = "Deals ??? Damage per seond.\nCost: 500 OptiCoins";
    private float health = 100;
    float[] pillarLocation_x = {
            Gdx.graphics.getWidth() * 0.267f,
            Gdx.graphics.getWidth() * 0.484f,
            Gdx.graphics.getWidth() * 0.463f,
            Gdx.graphics.getWidth() * 0.800f,
            Gdx.graphics.getWidth() * 0.783f,
    };
    float[] towerLocation_x = {
            Gdx.graphics.getWidth() * 0.173f,
            Gdx.graphics.getWidth() * 0.214f,
            Gdx.graphics.getWidth() * 0.556f,
            Gdx.graphics.getWidth() * 0.651f,
            Gdx.graphics.getWidth() * 0.852f,
    };
    float[] pillarLocation_y = {
            Gdx.graphics.getHeight() * 0.703f,
            Gdx.graphics.getHeight() * 0.745f,
            Gdx.graphics.getHeight() * 0.375f,
            Gdx.graphics.getHeight() * 0.367f,
            Gdx.graphics.getHeight() * 0.618f,
    };
    float[] towerLocation_y = {
            Gdx.graphics.getHeight() * 0.785f,
            Gdx.graphics.getHeight() * 0.318f,
            Gdx.graphics.getHeight() * 0.739f,
            Gdx.graphics.getHeight() * 0.350f,
            Gdx.graphics.getHeight() * 0.598f,
    };

    private static float FRAME_DURATION = 1 / 30f;
    private static float BOSS_FRAME_DURATION = 1 / 20f;
    private float elapsed_time = 0f;
    private TextureAtlas magicianRunningAtlas = Assets.manager.get(Assets.magicianEnemy, TextureAtlas.class);
    private TextureAtlas yetiRunningAlkas = Assets.manager.get(Assets.yetiEnemy, TextureAtlas.class);
    private TextureAtlas yetiBossAtlas = Assets.manager.get(Assets.yetiBossEnemy, TextureAtlas.class);
    private TextureAtlas iceWarriorAtlas = Assets.manager.get(Assets.iceWarriorBossEnemy, TextureAtlas.class);
    private TextureRegion magicianCurrentFrame, yetiCurrentFrame, yetiBossCurrentFrame, iceWarriorCurrentFrame;
    private Animation magicianRunngingAnimation, yetiRunningAnimation, yetiBossWalkingAnimation, iceWarriorWalkingAnimation;

    Array<TextureAtlas.AtlasRegion> magicianFrames = magicianRunningAtlas.findRegions("1_enemies_1_RUN");
    Array<TextureAtlas.AtlasRegion> yetiFrames = yetiRunningAlkas.findRegions("2_enemies_1_RUN");
    Array<TextureAtlas.AtlasRegion> yetiBossFrames = yetiBossAtlas.findRegions("0_boss_walk");
    Array<TextureAtlas.AtlasRegion> iceWarriorFrames = iceWarriorAtlas.findRegions("0_specialty_walk");
    private BitmapFont font;

    public levelThreeGenerator(final TowerDefense game) {
        this.game = game;
    }

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
        //fireAbilitySkin = new Skin(Gdx.files.internal("abilities/abilitesSkin/fire/fireAbilitySkin.json"), Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class));
        towerSkin = new Skin(Gdx.files.internal("core/assets/background/tower/towerPack/towerPack.json"), Assets.manager.get(Assets.towerPack, TextureAtlas.class));
        thunderAbilitySkin = new Skin(Gdx.files.internal("core/assets/abilities/abilitesSkin/thunder/thunderAbilitySkin.json") ,new TextureAtlas("core/assets/abilities/abilitesSkin/thunder/thunderAbility.atlas"));
        explosionAbilitySkin = new Skin(Gdx.files.internal("core/assets/abilities/abilitesSkin/explosion/explosionButton/explosionButton.json"), new TextureAtlas("core/assets/abilities/abilitesSkin/explosion/explosionButton/explosionButton.atlas"));
        windowSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/testWindowSkin/windowStyle.atlas"));
        ImageButtonStyle pillarPlacementStyle = new ImageButtonStyle();
        ImageButtonStyle towerPlacementStyle = new ImageButtonStyle();
        pillarPlacementStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.snowTowerPack, TextureAtlas.class).findRegion("pillarArcher"));
        pillarPlacementStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.snowTowerPack, TextureAtlas.class).findRegion("pillarArcher_over"));
        towerPlacementStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.snowTowerPack, TextureAtlas.class).findRegion("pillarTower"));
        towerPlacementStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.snowTowerPack, TextureAtlas.class).findRegion("pillarTower_over"));
        //----------------------------------------------------------PauseMenu------------------------------------------------------//
        //TODO outsource to a different file
        WindowStyle windowStyle = new WindowStyle();
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
        //--------------------------------------------------------AbilityMenuButtons----------------------------------------------------//
        final ImageButtonStyle style = new ImageButtonStyle();
        final ImageButtonStyle style2 = new ImageButtonStyle();
        final ImageButtonStyle styleExplosionAbility = new ImageButtonStyle();

        final ImageButtonStyle stylePillarPlacementArcherStandardMenu = new ImageButtonStyle();
        final ImageButtonStyle stylePillarPlacementArcherStandard = new ImageButtonStyle();
        final ImageButtonStyle stylePillarPlacementArcherStrongMenu = new ImageButtonStyle();
        final ImageButtonStyle stylePillarPlacementArcherStrong = new ImageButtonStyle();
        final ImageButtonStyle stylePillarPlacementCrossbowMenu = new ImageButtonStyle();
        final ImageButtonStyle stylePillarPlacementCrossbow = new ImageButtonStyle();

        final ImageButtonStyle styleTowerPlacementArcher = new ImageButtonStyle();
        final ImageButtonStyle styleTowerPlacementMagician = new ImageButtonStyle();
        final ImageButtonStyle styleTowerPlacementSupport = new ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_up"));
        style.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_over"));
        style.imageChecked = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_checked"));

        stylePillarPlacementArcherStandardMenu.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_standard_menu"));
        stylePillarPlacementArcherStandardMenu.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_standard_menu_over"));
        stylePillarPlacementArcherStandard.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_standard"));

        stylePillarPlacementArcherStrongMenu.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_strong_menu"));
        stylePillarPlacementArcherStrongMenu.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_strong_menu_over"));
        stylePillarPlacementArcherStrong.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_strong"));

        stylePillarPlacementCrossbowMenu.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_crossbow_menu"));
        stylePillarPlacementCrossbowMenu.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_crossbow_menu_over"));
        stylePillarPlacementCrossbow.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.snowPillarArcherPack, TextureAtlas.class).findRegion("archer_crossbow_standard"));

        styleTowerPlacementArcher.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("archerTower_default"));
        styleTowerPlacementMagician.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("magicianTower_default"));
        styleTowerPlacementSupport.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.towerPack, TextureAtlas.class).findRegion("supportTower_default"));

        style2.imageUp = thunderAbilitySkin.getDrawable("thunder_up");
        style2.imageOver = thunderAbilitySkin.getDrawable("thunder_over");

        styleExplosionAbility.imageUp = explosionAbilitySkin.getDrawable("explosionButton");
        styleExplosionAbility.imageOver = explosionAbilitySkin.getDrawable("explosionButtonHover");

        ImageButtonStyle[] towerSkinsArray = {
                styleTowerPlacementSupport,
                styleTowerPlacementArcher,
                styleTowerPlacementMagician
        };

        LinkedList<ImageButtonStyle> towerSkins = new LinkedList<>();
        towerSkins.add(styleTowerPlacementSupport);
        towerSkins.add(styleTowerPlacementArcher);
        towerSkins.add(styleTowerPlacementMagician);

        ImageButtonStyle[] pillarSkinsArray = {
                stylePillarPlacementArcherStandardMenu,
                stylePillarPlacementArcherStrongMenu,
                stylePillarPlacementCrossbowMenu
        };

        LinkedList<ImageButtonStyle> pillarStyleSkins = new LinkedList<>();
        pillarStyleSkins.add(stylePillarPlacementArcherStandard);
        pillarStyleSkins.add(stylePillarPlacementArcherStrong);
        pillarStyleSkins.add(stylePillarPlacementCrossbow);

        final ImageButton fireAbility = new ImageButton(style);
        final ImageButton thunderAbility = new ImageButton(style2);
        final ImageButton explosionAbilityArray = new ImageButton(styleExplosionAbility);

        final ImageButton pillarPlacementMenuArcherStandard = new ImageButton(stylePillarPlacementArcherStandardMenu);
        final ImageButton pillarPlacementMenuArcherStrong = new ImageButton(stylePillarPlacementArcherStrongMenu);
        final ImageButton pillarPlacementMenuCrossbowMenu = new ImageButton(stylePillarPlacementCrossbowMenu);

        final ImageButton towerPlacementArcher = new ImageButton(styleTowerPlacementArcher);
        final ImageButton towerPlacementMagician = new ImageButton(styleTowerPlacementMagician);
        final ImageButton towerPlacementSupport = new ImageButton(styleTowerPlacementSupport);

        abilityButtonArray.add(fireAbility);
        abilityButtonArray.add(thunderAbility);
        abilityButtonArray.add(explosionAbilityArray);
        upgradeAbilityButtonArray.add(pillarPlacementMenuArcherStandard);
        upgradeAbilityButtonArray.add(pillarPlacementMenuArcherStrong);
        upgradeAbilityButtonArray.add(pillarPlacementMenuCrossbowMenu);
        upgradeAbilityButtonArray.add(towerPlacementArcher);
        upgradeAbilityButtonArray.add(towerPlacementMagician);
        upgradeAbilityButtonArray.add(towerPlacementSupport);
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
                            if(fireAbility.isChecked() && Coin.COINS  >= damage.getFireCost()) {
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
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                //tower.setVisible(false);
            }
        });
        /*
        TODO optimize ClickListener -> reduce code redundancy
            Windows like abilityList -> all in a LinkedList -> e.g. for each booleanList .setVisible = false
         */
        abilityButtonActor = new testActor(abilityButton, Gdx.graphics.getWidth()*0.11f, Gdx.graphics.getHeight()*0.865f, 90,90);
        abilityButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                abilityList.setVisible(!abilityList.isVisible());
            }
        });
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
        ArrayList<Integer> coinCost = new ArrayList<>();
        coinCost.add(100);
        coinCost.add(250);
        coinCost.add(500);
        towerName = new ArrayList<>();
        towerName.add("SupportTower");
        towerName.add("ArcherTower");
        towerName.add("MagicTower");

        pillarName = new ArrayList<>();
        pillarName.add("ArcherStandard");
        pillarName.add("ArcherStrong");
        pillarName.add("Crossbow");

        //----------------------------------------------------------TowerMenuList/Tower Generation------------------------------------------------------//
        towerMenuList = towerGeneration.TowerMenuWindow(towerList, "towerList", towerSkinsArray, stage);
        towerGeneration.setTowerLocation(towerLocation_x, towerLocation_y);
        towerGeneration.setTowerStyle(towerPlacementStyle);
        towerGeneration.setTowerMenuList(towerList);
        towerGeneration.createTowers(towerMenuList, 140, 140, coinCost, towerName, towerSkins, stage, 325f);

        pillarMenuList = pillarGeneration.TowerMenuWindow(pillarList, "pillarList", pillarSkinsArray, stage);
        pillarGeneration.setTowerLocation(pillarLocation_x, pillarLocation_y);
        pillarGeneration.setTowerStyle(pillarPlacementStyle);
        pillarGeneration.setTowerMenuList(pillarList);
        pillarGeneration.createTowers(pillarMenuList, 85, 85, coinCost, pillarName, pillarStyleSkins, stage, 175f);
        ArrayList<Float> pillarDamage = new ArrayList<>();
        pillarDamage.add(0.02f);
        pillarDamage.add(0.03f);
        pillarDamage.add(0.05f);
        towerGeneration.setTowerDamage(pillarDamage);
        pillarGeneration.setTowerDamage(pillarDamage);

        Gdx.input.setInputProcessor(stage);

        //font for coins, health etc.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/riffic-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.color = Color.RED;
        font12 = generator.generateFont(parameter);
        generator.dispose();
        font = new BitmapFont();
        font.getData().setScale(2);
        batch = new SpriteBatch();
        level = new LevelThree();
        level.createBackground();
        //addBuildingPlacesToStage();
        stage.addActor(pauseButtonActor);
        stage.addActor(abilityButtonActor);
        stage.addActor(upgradeAbilityButtonActor);
        stage.addActor(pause);
        stage.addActor(abilityList);
        stage.addActor(victoryWindow);
        enemyList.add(magicianLinkedList);
        enemyList.add(yetiLinkedList);
        enemyList.add(iceWarriorLinkedList);
        createAllEnemies();
        updateToolTips();
        spawnBoss();

        magicianRunngingAnimation = new Animation(FRAME_DURATION, magicianFrames, Animation.PlayMode.LOOP);
        yetiRunningAnimation = new Animation(FRAME_DURATION, yetiFrames, Animation.PlayMode.LOOP);

        //TODO
        yetiBossWalkingAnimation = new Animation(FRAME_DURATION, yetiBossFrames, Animation.PlayMode.LOOP);
        iceWarriorWalkingAnimation = new Animation(FRAME_DURATION, iceWarriorFrames, Animation.PlayMode.LOOP);
    }



    @Override
    public void render(float delta) {
        level.renderBackground();
        elapsed_time += Gdx.graphics.getDeltaTime();
        magicianCurrentFrame = (TextureRegion) magicianRunngingAnimation.getKeyFrame(elapsed_time);
        yetiCurrentFrame = (TextureRegion) yetiRunningAnimation.getKeyFrame(elapsed_time);
        yetiBossCurrentFrame = (TextureRegion) yetiBossWalkingAnimation.getKeyFrame(elapsed_time);
        iceWarriorCurrentFrame = (TextureRegion) iceWarriorWalkingAnimation.getKeyFrame(elapsed_time);
        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        if (!isPaused){
            towerGeneration.checkTowerRange(enemyList, towerName);
            pillarGeneration.checkTowerRange(enemyList, pillarName);
            if(enemyCount > 0) {
                spawnEnemies(Gdx.graphics.getDeltaTime());
                makeEnemiesMove(delta, magicianLinkedList, LevelThree.levelThreeBottomPath(), magicianCurrentFrame, 3);
                makeEnemiesMove(delta, yetiLinkedList, LevelThree.levelThreeTopPath(), yetiCurrentFrame, 5);
            }
            else{
                clearEnemies();
                moveBoss(delta);
                if(bossPath.getX() >= 550){
                    spawnIceWarrior(delta);
                    moveIceWarriorBoss(delta);
                }
            }
            updateAllEntities();
            checkHealth();
            drawAllEntities();
            checkAbilityCollision(fireBallAbility, enemyList, damage.getFireDamage(), 50);
            //checkAbilityCollision(fireBallAbility, yetiLinkedList, damage.getExplosionDamage(), 100);
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if(rangeCircle){
            drawCircle();
        }
        towerGeneration.drawTowerCollisionCircle(325f); // Towers
        pillarGeneration.drawTowerCollisionCircle(175f); // Pillars
        Gdx.gl.glDisable(GL20.GL_BLEND);
        stage.draw();
        font12.draw(batch, "Coins: " + Coin.COINS, 25, 590);
        font12.draw(batch, "Health: " + health, 25, 550);
        if(enemyCount == 0){
            Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).stop();
            Assets.manager.get(Assets.bossLevelOneMusic, Music.class).play();
            font12.draw(batch, "Boss HP: " + (int)bossPath.getLifeCount(), 25, 510);
        }else{
            font12.draw(batch, "Enemies left: " + enemyCount, 25, 510);
        }
        batch.end();
    }
    public void updateToolTips(){
        fireAbilityToolTip = "Deals "+ (int)damage.getFireDamage() + " Damage against 1 Enemy\nCost: "+ (int)damage.getFireCost() + " OptiCoins";
        thunderAbilityToolTip = "Deals "+ (int)damage.getThunderDamage() + " Damage to all enemies\nCost: "+ (int)damage.getThunderCost() + " OptiCoins";
        explosionAbilityToolTip = "Immediately kills one enemy.\nCost: " + (int)damage.getExplosionCost() + " OptiCoins";
        abilityButtonArray.get(0).addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(1).addListener(new TextTooltip(thunderAbilityToolTip, toolTipManager, uiSkin));
        abilityButtonArray.get(2).addListener(new TextTooltip(explosionAbilityToolTip, toolTipManager, uiSkin));

        towerGeneration.updateTowerToolTips(archerTowerToolTip, magicianTowerToolTip,supportTowerToolTip, toolTipManager);
        pillarGeneration.updateTowerToolTips(pillarArcherStandardToolTip, pillarArcherStrongToolTip, pillarCrossbowToolTip, toolTipManager);
    }
    public void checkAbilityCollision(PathfindingEnemy abl, LinkedList<LinkedList> enemyList, float damage, int coin) {
        for(Iterator<PathfindingEnemy> abilityIterator = ability.iterator(); abilityIterator.hasNext();){
            PathfindingEnemy fire = abilityIterator.next();
            for(Iterator<LinkedList> enemyIterator = enemyList.iterator(); enemyIterator.hasNext();){
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
                    }
                }
            }
            if(enemyCount == 0){
                if (bossPath.getBoundingRectangle().overlaps(fire.getBoundingRectangle())) {
                    bossPath.setLifeCount(bossPath.getLifeCount() - (fireBall.getFireDamage() + 100));
                    abilityIterator.remove();
                }
                for (Iterator<PathfindingEnemy> iterator = iceWarriorLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy iceWarrior = iterator.next();
                    if (iceWarrior.getBoundingRectangle().overlaps(fire.getBoundingRectangle())) {
                        iceWarrior.setLifeCount(0);
                        abilityIterator.remove();
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
            if (enemyCount == 0) {
                bossPath.setLifeCount(bossPath.getLifeCount() - 0.05f);
                for (Iterator<PathfindingEnemy> iterator = iceWarriorLinkedList.iterator(); iterator.hasNext(); ) {
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
    }
    public void drawCircle(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,0.5f);
        shapeRenderer.circle(Gdx.input.getX(), 720 - Gdx.input.getY(), 50f);
        shapeRenderer.end();
    }
    //TODO outsource Abilities to their own files
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
    public void setUpAbilityTwo(float x, float y, float delta){
        ability = new Array<>();
        fireBallAbility2 = new PathfindingEnemy(explosion.idleFrame(), abilityMovementPath(x, y));
        fireBallAbility2.setPosition(0, Gdx.graphics.getHeight() * 0.8f);
        ability.add(fireBallAbility2);
    }
    public void createAllEnemies(){
        magician = new Magician();
        yeti = new Yeti();
        boss = new LevelThreeBoss();
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
        bossPath.updateBossAnimation(batch, LevelThree.levelThreeLeftPath(), delta, yetiBossCurrentFrame, 35);
        if(bossPath.getLifeCount() <= 0){
            isPaused = true;
            victoryWindow.setVisible(true);
            bossPath.setLifeCount(0);
        }else if(bossPath.getX() > Gdx.graphics.getWidth()){
            health -= 75;
            if(health > 0){
                isPaused = true;
                victoryWindow.setVisible(true);
                Assets.manager.get(Assets.victorySound, Sound.class).play(2f);
                Gdx.app.log("Game Won", "");
            }
        }
    }
    public void spawnIceWarrior(float delta){
        enemySpawnTimer3 += delta;
        if(enemySpawnTimer3 > timeBetweenEnemySpawns3) {
            iceWarriorLinkedList.add(new PathfindingEnemy(boss.idleFrame(), 600, LevelThree.levelThreeBossTopPath()));
            iceWarriorLinkedList.add(new PathfindingEnemy(boss.idleFrame(), 600, LevelThree.levelThreeBossBottomPath()));
            iceWarriorPath.add(LevelThree.levelThreeBossTopPath());
            iceWarriorPath.add(LevelThree.levelThreeBossBottomPath());
            enemySpawnTimer3 -= timeBetweenEnemySpawns3;
        }
    }
    public void moveIceWarriorBoss(float delta){
        Iterator<Array> pathIterator = iceWarriorPath.iterator();
        for (Iterator<PathfindingEnemy> iterator = iceWarriorLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy iceWarrior = iterator.next();
            Array path = pathIterator.next();
            iceWarrior.updateBossAnimation(batch, path, delta, iceWarriorCurrentFrame, 25);
            if (iceWarrior.getLifeCount() <= 0) {
                iterator.remove();
                pathIterator.remove();
                Coin.COINS += 1000;
            }
            if (iceWarrior.getX() > Gdx.graphics.getWidth()) {
                iterator.remove();
                pathIterator.remove();
                health -= 50;
            }
        }
    }

    public void clearEnemies(){
        for (Iterator<PathfindingEnemy> magicianIterator = magicianLinkedList.iterator(); magicianIterator.hasNext();) {
            PathfindingEnemy magician = magicianIterator.next();
            magicianIterator.remove();
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
                magicianLinkedList.add(new PathfindingEnemy(magician.idleFrame(), 30, LevelThree.levelThreeBottomPath()));
                enemySpawnTimer -= timeBetweenEnemySpawns;
            }
            if(enemySpawnTimer2 > timeBetweenEnemySpawns2){
                yetiLinkedList.add(new PathfindingEnemy(yeti.idleFrame(), 100, LevelThree.levelThreeTopPath()));
                enemySpawnTimer2 -= timeBetweenEnemySpawns2;
            }
        }
    }
    public void spawnBoss(){
        bossPath = new PathfindingEnemy(boss.idleFrame(), 3000, LevelThree.levelThreeLeftPath());
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
        Assets.levelThreeDispose();
    }
}