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
import com.badlogic.gdx.math.Intersector;
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
import java.util.LinkedList;
import java.util.*;

public class levelThreeGenerator implements Screen {
    final TowerDefense game;
    testActor pauseButtonActor, abilityButtonActor, upgradeAbilityButtonActor;
    Window pause, abilityList, tower, archerTower, gameOverWindow, victoryWindow;
    Stage stage;
    TooltipManager toolTipManager;
    ShapeRenderer shapeRenderer, towerAttackRange;
    LinkedList<Circle> pillarAttackCircle;
    LinkedList<Circle> towerAttackCircle;
    ClickListener placementListener, towerPlacementListener, towerListener, pillarPlacementListener, towerMenuListener;
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
    private LinkedList<PathfindingEnemy> magicianLinkedList, yetiLinkedList, iceWarriorLinkedList;
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
    private float coins = 1000;
    private int enemyCount = 50;
    BitmapFont font12;
    //TODO
    LinkedList<ImageButton> pillarList = new LinkedList<>();
    LinkedList<ImageButton> towerList = new LinkedList<>();
    LinkedList<PathfindingEnemy> enemyList = new LinkedList<>();
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
    ArrayList<ImageButton> pillarArcherTower = new ArrayList<>();
    ArrayList<ImageButton> pillarTower = new ArrayList<>();
    Array<Float> pillarCircle_x = new Array<>();
    Array<Float> pillarCircle_y = new Array<>();
    Array<Float> towerCircle_x = new Array<>();
    Array<Float> towerCircle_y = new Array<>();
    private ArrayList<Boolean> pillerCircleBool;
    private ArrayList<Boolean> towerCircleBool;
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
        TextButton exitButton = new TextButton("Exit to Main Menu", uiSkin);
        TextButton exitButton2 = new TextButton("Exit to Main Menu", uiSkin);
        TextButton victoryExitButton = new TextButton("Exit to Main menu", uiSkin);
        exitButton.setSize(250f,250f);
        exitButton2.setSize(250f,250f);
        victoryExitButton.setSize(500f, 500f);
        pause.add(continueButton).row();
        pause.add(exitButton);
        pause.pack();

        gameOverWindow = new Window("Game Over", uiSkin);
        gameOverWindow.add(exitButton2);
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
        exitButton.addListener(new ClickListener(){
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
        });
        exitButton2.addListener(new ClickListener(){
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
                Assets.manager.get(Assets.buttonClickSound, Sound.class).stop();
            }
        });
        victoryExitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                Assets.load();
                while(!Assets.manager.update()){
                    System.out.println(Assets.manager.getProgress() * 100 + "%");
                }
                game.setScreen(new MainMenuScreen(game));
                Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).stop();
                dispose();
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

        ImageButtonStyle[] towerSkins = {
                styleTowerPlacementArcher,
                styleTowerPlacementMagician,
                styleTowerPlacementSupport
        };

        ImageButtonStyle[] pillarSkins = {
                stylePillarPlacementArcherStandardMenu,
                stylePillarPlacementArcherStrongMenu,
                stylePillarPlacementCrossbowMenu
        };

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
        //fireAbility.addListener(new TextTooltip(fireAbilityToolTip, toolTipManager, uiSkin));
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
                if(thunderAbility.isChecked() && coins > damage.getThunderCost()){
                    coins -= damage.getThunderCost();
                    Gdx.app.log("Monetas", "Moneten: " + coins);
                    dealThunderDamage();
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
                tower.setVisible(false);
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
        archerTower = new Window("Choose an Archer for a tower", uiSkin);
        archerTower.setVisible(false);
        TextButton archerTowerContinueButton = new TextButton("Cancel", uiSkin);
        archerTowerContinueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("bin im pillar array");
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                archerTower.setVisible(!archerTower.isVisible());
            }
        });
        //Create Towers
        pillarList = new LinkedList<>();
        for(int i = 0; i <= 2; i++){
            //towerList.add(i, new ImageButton(fireAbilitySkin));
            pillarList.add(i, new ImageButton(pillarSkins[i]));
            final int finalI = i;
            pillarList.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    Gdx.app.log("pillarList: ", String.valueOf(finalI));
                }
            });
        }
        towerList = new LinkedList<>();
        for(int i = 0; i <= 2; i++){
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

        for(ImageButton towerImage : pillarList){
            archerTower.add(towerImage);
        }
        for(ImageButton towerImage : towerList){
            tower.add(towerImage);
        }
        pillerCircleBool = new ArrayList<>();
        for (int i = 0; i<= pillarLocation_x.length - 1; i++){
            pillerCircleBool.add(i, false);
        }
        for(int i = 0; i <= pillarLocation_x.length - 1; i++){
            pillarCircle_x.add(pillarLocation_x[i]);
            pillarCircle_y.add(pillarLocation_y[i]);
        }
        towerCircleBool = new ArrayList<>();
        for (int i = 0; i<= towerLocation_x.length - 1; i++){
            towerCircleBool.add(i, false);
        }
        for(int i = 0; i <= towerLocation_x.length - 1; i++){
            towerCircle_x.add(towerLocation_x[i]);
            towerCircle_y.add(towerLocation_y[i]);
        }

        //archerTower.padTop(64);
        //archerTower.setPosition(stage.getWidth() / 2 - archerTower.getWidth() / 2, stage.getHeight() / 2 - archerTower.getHeight() / 2);
        archerTower.add(archerTowerContinueButton);
        archerTower.pack();
        tower.add(continueButton2);
        tower.pack();
        Gdx.input.setInputProcessor(stage);

        towerListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                Gdx.app.log("towerListener","");
            }
        };
        pillarAttackCircle = new LinkedList<>();
        for(int i = 0; i <= pillarLocation_x.length - 1; i++){
            pillarAttackCircle.add(i, new Circle());
        }
        pillarRangeShape = new LinkedList<>();
        for(int i = 0; i <= pillarLocation_x.length - 1; i++){
            pillarRangeShape.add(i, new ShapeRenderer());
        }
        towerAttackCircle = new LinkedList<>();
        for(int i = 0; i <= towerLocation_x.length - 1; i++){
            towerAttackCircle.add(i, new Circle());
        }
        towerRangeShape = new LinkedList<>();
        for(int i = 0; i <= towerLocation_x.length - 1; i++){
            towerRangeShape.add(i, new ShapeRenderer());
        }

        //this loop is for the tower ground
        for (int i = 0; i <= pillarLocation_x.length - 1; i++){
            pillarArcherTower.add(i, new ImageButton(pillarPlacementStyle));
            pillarArcherTower.get(i).setPosition(pillarLocation_x[i], pillarLocation_y[i]);
            pillarArcherTower.get(i).setSize(85,85);
            //pillarArcherTower.get(i).setDebug(true);
            final int finalI = i;
            pillarArcherTower.get(i).addListener(pillarPlacementListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    if(pillarArcherTower.get(finalI).isChecked()) {
                        pillarArcherTower.get(finalI).setChecked(false);
                        archerTower.setVisible(!archerTower.isVisible());
                        Gdx.app.log("towerList: ", String.valueOf(finalI));
                        if(pillarList.get(0).isChecked() && coins >= 100){
                            pillarList.get(0).setChecked(false);
                            pillarArcherTower.get(finalI).setStyle(stylePillarPlacementArcherStandard);
                            pillarArcherTower.get(finalI).setPosition(pillarLocation_x[finalI], pillarLocation_y[finalI] + 16);
                            pillarArcherTower.get(finalI).clearListeners();
                            pillarArcherTower.get(finalI).addListener(towerListener);
                            pillarArcherTower.get(finalI).setName("ArcherStandard");
                            pillerCircleBool.set(finalI, true);
                            pillarAttackCircle.get(finalI).set(pillarLocation_x[finalI] + 48f, pillarLocation_y[finalI] + 48f, 175f);
                            coins -= 100;
                        }
                        if(pillarList.get(1).isChecked() && coins >= 250){
                            pillarList.get(1).setChecked(false);
                            pillarArcherTower.get(finalI).setStyle(stylePillarPlacementArcherStrong);
                            pillarArcherTower.get(finalI).setPosition(pillarLocation_x[finalI], pillarLocation_y[finalI] + 16);
                            pillarArcherTower.get(finalI).clearListeners();
                            pillarArcherTower.get(finalI).addListener(towerListener);
                            pillarArcherTower.get(finalI).setName("ArcherStrong");
                            pillerCircleBool.set(finalI, true);
                            pillarAttackCircle.get(finalI).set(pillarLocation_x[finalI] + 54f, pillarLocation_y[finalI] + 32f, 175f);
                            coins -= 250;
                        }
                        if(pillarList.get(2).isChecked() && coins >= 500){
                            pillarList.get(2).setChecked(false);
                            pillarArcherTower.get(finalI).setStyle(stylePillarPlacementCrossbow);
                            pillarArcherTower.get(finalI).setPosition(pillarLocation_x[finalI], pillarLocation_y[finalI] + 16);
                            pillarArcherTower.get(finalI).clearListeners();
                            pillarArcherTower.get(finalI).addListener(towerListener);
                            pillarArcherTower.get(finalI).setName("Crossbow");
                            Gdx.app.log("x", String.valueOf(pillarLocation_x[finalI]));
                            Gdx.app.log("Y", String.valueOf(pillarLocation_y[finalI]));
                            pillerCircleBool.set(finalI, true);
                            pillarAttackCircle.get(finalI).set(pillarLocation_x[finalI] + 54f, pillarLocation_y[finalI] + 32f, 175f);
                            coins -= 500;
                        }
                    }
                }
            });
        }
        for (int i = 0; i <= towerLocation_x.length - 1; i++){
            pillarTower.add(i, new ImageButton(towerPlacementStyle));
            pillarTower.get(i).setPosition(towerLocation_x[i], towerLocation_y[i]);
            pillarTower.get(i).setSize(140,140);
            //pillarArcherTower.get(i).setDebug(true);
            final int finalI = i;
            pillarTower.get(i).addListener(towerPlacementListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    if(pillarTower.get(finalI).isChecked()) {
                        pillarTower.get(finalI).setChecked(false);
                        tower.setVisible(!tower.isVisible());
                        Gdx.app.log("towerList: ", String.valueOf(finalI));
                        if(towerList.get(0).isChecked() && coins >= 100){
                            towerList.get(0).setChecked(false);
                            pillarTower.get(finalI).setStyle(styleTowerPlacementArcher);
                            pillarTower.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI] + 16);
                            pillarTower.get(finalI).clearListeners();
                            pillarTower.get(finalI).addListener(towerListener);
                            pillarTower.get(finalI).setName("ArcherTower");
                            towerCircleBool.set(finalI, true);
                            towerAttackCircle.get(finalI).set(towerLocation_x[finalI] + 48f, towerLocation_y[finalI] + 48f, 325f);
                            coins -= 100;
                        }
                        if(towerList.get(1).isChecked() && coins >= 250){
                            towerList.get(1).setChecked(false);
                            pillarTower.get(finalI).setStyle(styleTowerPlacementMagician);
                            pillarTower.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI] + 16);
                            pillarTower.get(finalI).clearListeners();
                            pillarTower.get(finalI).addListener(towerListener);
                            pillarTower.get(finalI).setName("MagicTower");
                            towerCircleBool.set(finalI, true);
                            pillarAttackCircle.get(finalI).set(towerLocation_x[finalI] + 54f, towerLocation_y[finalI] + 32f, 325f);
                            coins -= 250;
                        }
                        if(towerList.get(2).isChecked()  && coins >= 500){
                            towerList.get(2).setChecked(false);
                            pillarTower.get(finalI).setStyle(styleTowerPlacementSupport);
                            pillarTower.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI] + 16);
                            pillarTower.get(finalI).clearListeners();
                            pillarTower.get(finalI).addListener(towerListener);
                            pillarTower.get(finalI).setName("SupportTower");
                            Gdx.app.log("x", String.valueOf(towerLocation_x[finalI]));
                            Gdx.app.log("Y", String.valueOf(towerLocation_x[finalI]));
                            towerCircleBool.set(finalI, true);
                            towerAttackCircle.get(finalI).set(towerLocation_x[finalI] + 54f, towerLocation_y[finalI] + 32f, 325f);
                            coins -= 500;
                        }
                    }
                }
            });
        }
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
        addBuildingPlacesToStage();
        stage.addActor(pauseButtonActor);
        stage.addActor(abilityButtonActor);
        stage.addActor(upgradeAbilityButtonActor);
        stage.addActor(pause);
        stage.addActor(abilityList);
        stage.addActor(tower);
        stage.addActor(archerTower);
        stage.addActor(victoryWindow);

        magicianLinkedList = new LinkedList<>();
        yetiLinkedList = new LinkedList<>();
        iceWarriorLinkedList = new LinkedList<>();
        createAllEnemies();
        updateToolTips();
        spawnBoss();
        magicianRunngingAnimation = new Animation(FRAME_DURATION, magicianFrames, Animation.PlayMode.LOOP);
        yetiRunningAnimation = new Animation(FRAME_DURATION, yetiFrames, Animation.PlayMode.LOOP);

        //TODO
        yetiBossWalkingAnimation = new Animation(FRAME_DURATION, yetiBossFrames, Animation.PlayMode.LOOP);
        iceWarriorWalkingAnimation = new Animation(FRAME_DURATION, iceWarriorFrames, Animation.PlayMode.LOOP);
        //TODO

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
        if(pillarArcherTower.get(0).isPressed()) {
            updateToolTips();
        }
        batch.begin();
        if (!isPaused){
            checkPillarRange(delta);
            checkTowerRange();
            if(enemyCount > 0) {
                spawnEnemies(Gdx.graphics.getDeltaTime());
                makeT1EnemiesMove(delta);
                makeT2EnemiesMove(delta);
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
            checkAbilityCollision(fireBallAbility, damage.getFireDamage(), 50);
            checkAbilityCollision(fireBallAbility2, damage.getExplosionDamage(), 100);
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if(rangeCircle){
            drawCircle();
        }
        drawPillarCollisionCircle();
        drawTowerCollisionCircle();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        stage.draw();
        font12.draw(batch, "Coins: " + (int)coins, 25, 590);
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
    public void drawPillarCollisionCircle(){
        Iterator<Float> pillarCircleIterator_x = pillarCircle_x.iterator();
        Iterator<Float> pillarCircleIterator_y = pillarCircle_y.iterator();
        Iterator<Circle> circleIterator = pillarAttackCircle.iterator();
        Iterator<ShapeRenderer> pillarShapeRendererIterator = pillarRangeShape.iterator();
        Iterator<ImageButton> towerIterator = pillarArcherTower.iterator();
        for(Iterator<Boolean> iterator = pillerCircleBool.iterator(); iterator.hasNext();){
            if(circleIterator.hasNext()) {
                Boolean bool = iterator.next();
                Float circle_x = pillarCircleIterator_x.next();
                Float circle_y = pillarCircleIterator_y.next();
                Circle circle = circleIterator.next();
                ShapeRenderer shape = pillarShapeRendererIterator.next();
                if (bool) {
                    shape.begin(ShapeRenderer.ShapeType.Filled);
                    shape.setColor(1,1,1,0.1f);
                    shape.circle(circle_x + 48f, circle_y + 48f, 175f);
                    shape.end();
                }
            }
        }

    }
    public void drawTowerCollisionCircle(){
        Iterator<Float> towerCircleIterator_x = towerCircle_x.iterator();
        Iterator<Float> towerCircleIterator_y = towerCircle_y.iterator();
        Iterator<ShapeRenderer> towerShapeRendererIterator = towerRangeShape.iterator();
        Iterator<Circle> circleIterator = towerAttackCircle.iterator();
        for(Iterator<Boolean> iterator = towerCircleBool.iterator(); iterator.hasNext();){
            if(circleIterator.hasNext()) {
                Boolean bool = iterator.next();
                Float circle_x = towerCircleIterator_x.next();
                Float circle_y = towerCircleIterator_y.next();
                Circle circle = circleIterator.next();
                ShapeRenderer shape = towerShapeRendererIterator.next();
                if (bool) {
                    shape.begin(ShapeRenderer.ShapeType.Filled);
                    shape.setColor(1,1,1,0.1f);
                    shape.circle(circle_x + 48f, circle_y + 48f, 325f);
                    shape.end();
                }
            }
        }
    }
    //DO NOT TOUCH; DANGER IMMINENT
    public void checkPillarRange(float delta){
        Iterator<ImageButton> imageButton = pillarArcherTower.iterator();
        for(Iterator<Circle> circleIterator = pillarAttackCircle.iterator(); circleIterator.hasNext();) {
            ImageButton pillar = imageButton.next();
            Circle circle = circleIterator.next();
            if (imageButton.hasNext()) {
                for (Iterator<PathfindingEnemy> iterator = magicianLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy enemy = iterator.next();
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("ArcherStandard")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                        Gdx.app.log("standard archer", String.valueOf(enemy.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("ArcherStrong")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.03f);
                        Gdx.app.log("strong archer", String.valueOf(enemy.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("Crossbow")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 10f);
                        Gdx.app.log("crossbow", String.valueOf(enemy.getLifeCount()));
                    }
                }
                for (Iterator<PathfindingEnemy> iterator = yetiLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy enemy = iterator.next();
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("ArcherStandard")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.02f);
                        Gdx.app.log("standard archer", String.valueOf(enemy.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("ArcherStrong")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.03f);
                        Gdx.app.log("strong archer", String.valueOf(enemy.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("Crossbow")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                        Gdx.app.log("crossbow", String.valueOf(enemy.getLifeCount()));
                    }
                }
                if (enemyCount == 0) {
                    if (Intersector.overlaps(circle, bossPath.getBoundingRectangle()) && bossPath.getX() >= 0 && pillar.getName().equals("ArcherStandard")) {
                        bossPath.setLifeCount(bossPath.getLifeCount() - 0.02f);
                        Gdx.app.log("standard archer", String.valueOf(bossPath.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, bossPath.getBoundingRectangle()) && bossPath.getX() >= 0 && pillar.getName().equals("ArcherStrong")) {
                        bossPath.setLifeCount(bossPath.getLifeCount() - 0.03f);
                        Gdx.app.log("strong archer", String.valueOf(bossPath.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, bossPath.getBoundingRectangle()) && bossPath.getX() >= 0 && pillar.getName().equals("Crossbow")) {
                        bossPath.setLifeCount(bossPath.getLifeCount() - 0.05f);
                        Gdx.app.log("crossbow", String.valueOf(bossPath.getLifeCount()));
                    }
                    for (Iterator<PathfindingEnemy> iterator = iceWarriorLinkedList.iterator(); iterator.hasNext(); ) {
                        PathfindingEnemy enemy = iterator.next();
                        if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("ArcherStandard")) {
                            enemy.setLifeCount(enemy.getLifeCount() - 0.02f);
                            Gdx.app.log("standard archer", String.valueOf(enemy.getLifeCount()));
                        }
                        if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("ArcherStrong")) {
                            enemy.setLifeCount(enemy.getLifeCount() - 0.03f);
                            Gdx.app.log("strong archer", String.valueOf(enemy.getLifeCount()));
                        }
                        if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && pillar.getName().equals("Crossbow")) {
                            enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                            Gdx.app.log("crossbow", String.valueOf(enemy.getLifeCount()));
                        }
                    }
                }
            }
        }
    }
    public void checkTowerRange(){
        Iterator<ImageButton> imageButton = pillarTower.iterator();
        for(Iterator<Circle> circleIterator = towerAttackCircle.iterator(); circleIterator.hasNext();) {
            ImageButton tower = imageButton.next();
            Circle circle = circleIterator.next();
            if (imageButton.hasNext()) {
                for (Iterator<PathfindingEnemy> iterator = magicianLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy enemy = iterator.next();
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("ArcherTower")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.02f);
                        Gdx.app.log("archer", String.valueOf(enemy.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("MagicTower")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.03f);
                        Gdx.app.log("magic", String.valueOf(enemy.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("SupportTower")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                        Gdx.app.log("support", String.valueOf(enemy.getLifeCount()));
                    }
                }
                for (Iterator<PathfindingEnemy> iterator = yetiLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy enemy = iterator.next();
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("ArcherTower")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.02f);
                        Gdx.app.log("archer", String.valueOf(enemy.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("MagicTower")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.03f);
                        Gdx.app.log("magic", String.valueOf(enemy.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("SupportTower")) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                        Gdx.app.log("support", String.valueOf(enemy.getLifeCount()));
                    }
                }
                if (enemyCount == 0) {
                    if (Intersector.overlaps(circle, bossPath.getBoundingRectangle()) && bossPath.getX() >= 0 && tower.getName().equals("ArcherTower")) {
                        bossPath.setLifeCount(bossPath.getLifeCount() - 0.02f);
                        Gdx.app.log("archer", String.valueOf(bossPath.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, bossPath.getBoundingRectangle()) && bossPath.getX() >= 0 && tower.getName().equals("MagicTower")) {
                        bossPath.setLifeCount(bossPath.getLifeCount() - 0.03f);
                        Gdx.app.log("magic", String.valueOf(bossPath.getLifeCount()));
                    }
                    if (Intersector.overlaps(circle, bossPath.getBoundingRectangle()) && bossPath.getX() >= 0 && tower.getName().equals("SupportTower")) {
                        bossPath.setLifeCount(bossPath.getLifeCount() - 0.05f);
                        Gdx.app.log("support", String.valueOf(bossPath.getLifeCount()));
                    }
                    for (Iterator<PathfindingEnemy> iterator = iceWarriorLinkedList.iterator(); iterator.hasNext(); ) {
                        PathfindingEnemy enemy = iterator.next();
                        if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("ArcherTower")) {
                            enemy.setLifeCount(enemy.getLifeCount() - 0.02f);
                            Gdx.app.log("archer", String.valueOf(enemy.getLifeCount()));
                        }
                        if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("MagicTower")) {
                            enemy.setLifeCount(enemy.getLifeCount() - 0.03f);
                            Gdx.app.log("magic", String.valueOf(enemy.getLifeCount()));
                        }
                        if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals("SupportTower")) {
                            enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                            Gdx.app.log("support", String.valueOf(enemy.getLifeCount()));
                        }
                    }
                }
            }
        }
    }

    public void addBuildingPlacesToStage(){
        for(ImageButton placePillar: pillarArcherTower){
            stage.addActor(placePillar);
        }
        for(ImageButton placeTower: pillarTower){
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

        pillarList.get(0).addListener(new TextTooltip(pillarArcherStandardToolTip, toolTipManager, uiSkin));
        pillarList.get(1).addListener(new TextTooltip(pillarArcherStrongToolTip, toolTipManager, uiSkin));
        pillarList.get(2).addListener(new TextTooltip(pillarCrossbowToolTip, toolTipManager, uiSkin));

        towerList.get(0).addListener(new TextTooltip(archerTowerToolTip, toolTipManager, uiSkin));
        towerList.get(1).addListener(new TextTooltip(magicianTowerToolTip, toolTipManager, uiSkin));
        towerList.get(2).addListener(new TextTooltip(supportTowerToolTip, toolTipManager, uiSkin));
    }
    public void checkAbilityCollision(PathfindingEnemy abl, float damage, int coin) {
        for(Iterator<PathfindingEnemy> abilityIterator = ability.iterator(); abilityIterator.hasNext();){
            PathfindingEnemy fire = abilityIterator.next();
            for (Iterator<PathfindingEnemy> iterator = magicianLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy magicianEnemy = iterator.next();
                    if (magicianEnemy.getBoundingRectangle().overlaps(fire.getBoundingRectangle())) {
                        magicianEnemy.setLifeCount(magicianEnemy.getLifeCount() - damage);
                        magicianEnemy.timeOfDmgTaken = magicianEnemy.timeAlive;
                        Gdx.app.log(String.valueOf(magicianEnemy), String.valueOf(magicianEnemy.getLifeCount()));
                        abilityIterator.remove();
                    }
                    if (magicianEnemy.getLifeCount() <= 0) {
                        iterator.remove();
                        coins += coin;
                        enemyCount -= 1;
                    }
                }
            for (Iterator<PathfindingEnemy> iterator = yetiLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy yetiEnemy = iterator.next();
                    if (yetiEnemy.getBoundingRectangle().overlaps(fire.getBoundingRectangle())) {
                        yetiEnemy.setLifeCount(yetiEnemy.getLifeCount() - damage);
                        yetiEnemy.timeOfDmgTaken = yetiEnemy.timeAlive;
                        Gdx.app.log(String.valueOf(yetiEnemy), String.valueOf(yetiEnemy.getLifeCount()));
                        abilityIterator.remove();
                    }
                    if (yetiEnemy.getLifeCount() <= 0) {
                        iterator.remove();
                        coins += coin;
                        enemyCount -= 1;
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
    public void dealThunderDamage() {
        for (Iterator<PathfindingEnemy> iterator = magicianLinkedList.iterator(); iterator.hasNext(); ) {
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
        for (Iterator<PathfindingEnemy> iterator = yetiLinkedList.iterator(); iterator.hasNext(); ) {
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
        if (enemyCount == 0) {
            bossPath.setLifeCount(bossPath.getLifeCount() - 0.05f);
            for (Iterator<PathfindingEnemy> iterator = iceWarriorLinkedList.iterator(); iterator.hasNext(); ) {
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
    }
    public void drawCircle(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,0.5f);
        shapeRenderer.circle(Gdx.input.getX(), 720 - Gdx.input.getY(), 50f);
        shapeRenderer.end();
    }
    public void drawRange(int i, float x, float y){
        pillarAttackCircle.add(i, new Circle());
        pillarAttackCircle.get(i).set(x + 54f, y + 32f,100f);
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

    public void makeT1EnemiesMove(float delta) {
        for (Iterator<PathfindingEnemy> iterator = magicianLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy magician = iterator.next();
            magician.updateAnimation(batch, LevelThree.levelThreeBottomPath(), delta, magicianCurrentFrame);
            //remove entity if life is less than 0, and add 100 coins
            if (magician.getLifeCount() <= 0 ) {
                iterator.remove();
                coins += 100;
                enemyCount -= 1;
            }
            //remove entity if is out of bounds, and get player damage
            if(magician.getX() > Gdx.graphics.getWidth()){
                iterator.remove();
                health -= 1;
                enemyCount -= 1;
            }
        }
    }
    public void makeT2EnemiesMove(float delta) {
        for (Iterator<PathfindingEnemy> iterator = yetiLinkedList.iterator(); iterator.hasNext(); ) {
            PathfindingEnemy yeti = iterator.next();
            yeti.updateAnimation(batch, LevelThree.levelThreeTopPath(), delta, yetiCurrentFrame);
            //remove entity if life is less than 0, and add 100 coins
            if (yeti.getLifeCount() <= 0 ) {
                iterator.remove();
                coins += 100;
                enemyCount -= 1;
            }
            //remove entity if is out of bounds, and get player damage
            if(yeti.getX() > Gdx.graphics.getWidth()){
                iterator.remove();
                health -= 1;
                enemyCount -= 1;
            }
        }
    }
    public void moveBoss(float delta){
        bossPath.updateBossAnimation(batch, LevelThree.levelThreeLeftPath(), delta, yetiBossCurrentFrame, 35);
        if(bossPath.getLifeCount() <= 0){
            isPaused = true;
            bossPath.setLifeCount(0);
            victoryWindow.setVisible(true);
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
                coins += 1000;
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

