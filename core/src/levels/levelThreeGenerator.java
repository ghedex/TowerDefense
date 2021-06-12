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
import levels.menu.testActor;
import levels.menu.MainMenuScreen;
import java.util.LinkedList;
import java.util.*;

public class levelThreeGenerator implements Screen {
        final TowerDefense game;
        testActor pauseButtonActor, abilityButtonActor, upgradeAbilityButtonActor;
        Window pause, abilityList, tower, gameOverWindow, victoryWindow;
        Stage stage;
        TooltipManager toolTipManager;
        ShapeRenderer shapeRenderer, towerAttackRange;
        LinkedList<Circle> pillarAttackCircle;
        LinkedList<Circle> towerAttackCircle;
        ClickListener placementListener, towerPlacementListener, towerListener;
        SpriteBatch batch;
        LevelThree level;
        PathfindingEnemy fireBallAbility, fireBallAbility2;
        Ability fireBall, damage = new Ability();
        Explosion explosion;
        private Array<Vector2> abilityPath;
        private boolean isPaused;
        private LinkedList<PathfindingEnemy> impLinkedList;
        private float enemySpawnTimer, timeBetweenEnemySpawns = 3f;
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
        private boolean isBossAlive = false;
        BitmapFont font12;
        //TODO
        LinkedList<ImageButton> towerList = new LinkedList<>();
        LinkedList<PathfindingEnemy> enemyList = new LinkedList<>();
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
            final ImageButtonStyle styleTowerPlacementArcher = new ImageButtonStyle();
            final ImageButtonStyle styleTowerPlacementMagician = new ImageButtonStyle();
            final ImageButtonStyle styleTowerPlacementSupport = new ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_up"));
            style.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_over"));
            style.imageChecked = new TextureRegionDrawable(Assets.manager.get(Assets.fireAbilityPack, TextureAtlas.class).findRegion("fire_checked"));

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
                                if(fireAbility.isChecked() && coins >= 50) {
                                    super.clicked(event, x, y);
                                    coins -= 50;

                                    createAbility();
                                    setUpAbility(Gdx.input.getX() - fireBall.getWIDTH() / 2f, 720 - Gdx.input.getY() - fireBall.getHEIGHT() / 2f);
                                    Gdx.app.log("Mouse_X", String.valueOf(Gdx.input.getX()));
                                    Gdx.app.log("Mouse_Y", String.valueOf(Gdx.input.getY()));
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
            thunderAbility.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    if(thunderAbility.isChecked() && coins > 10){
                        coins -= 10;
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
                        Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                        Gdx.app.log("towerList: ", String.valueOf(finalI));
                    }
                });
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

            tower.padTop(64);
            tower.setPosition(stage.getWidth() / 2 - tower.getWidth() / 2, stage.getHeight() / 2 - tower.getHeight() / 2);
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
                pillarArcherTower.get(i).addListener(towerPlacementListener = new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                        if(pillarArcherTower.get(finalI).isChecked()) {
                            pillarArcherTower.get(finalI).setChecked(false);
                            tower.setVisible(!tower.isVisible());
                            Gdx.app.log("towerList: ", String.valueOf(finalI));
                            if(towerList.get(0).isChecked()){
                                towerList.get(0).setChecked(false);
                                pillarArcherTower.get(finalI).setStyle(styleTowerPlacementArcher);
                                pillarArcherTower.get(finalI).setPosition(pillarLocation_x[finalI], pillarLocation_y[finalI] + 16);
                                pillarArcherTower.get(finalI).clearListeners();
                                pillarArcherTower.get(finalI).addListener(towerListener);
                                pillerCircleBool.set(finalI, true);
                                pillarAttackCircle.get(finalI).set(pillarLocation_x[finalI] + 48f, pillarLocation_y[finalI] + 48f, 175f);
                            }
                            if(towerList.get(1).isChecked()){
                                towerList.get(1).setChecked(false);
                                pillarArcherTower.get(finalI).setStyle(styleTowerPlacementMagician);
                                pillarArcherTower.get(finalI).setPosition(pillarLocation_x[finalI], pillarLocation_y[finalI] + 16);
                                pillarArcherTower.get(finalI).clearListeners();
                                pillarArcherTower.get(finalI).addListener(towerListener);
                                pillarArcherTower.get(finalI).setName("MagicTower " + String.valueOf(finalI));
                                pillerCircleBool.set(finalI, true);
                                pillarAttackCircle.get(finalI).set(pillarLocation_x[finalI] + 54f, pillarLocation_y[finalI] + 32f, 175f);
                            }
                            if(towerList.get(2).isChecked()){
                                towerList.get(2).setChecked(false);
                                pillarArcherTower.get(finalI).setStyle(styleTowerPlacementSupport);
                                pillarArcherTower.get(finalI).setPosition(pillarLocation_x[finalI], pillarLocation_y[finalI] + 16);
                                pillarArcherTower.get(finalI).clearListeners();
                                pillarArcherTower.get(finalI).addListener(towerListener);
                                pillarArcherTower.get(finalI).setName("SupportTower " + String.valueOf(finalI));
                                Gdx.app.log("x", String.valueOf(pillarLocation_x[finalI]));
                                Gdx.app.log("Y", String.valueOf(pillarLocation_y[finalI]));
                                pillerCircleBool.set(finalI, true);
                                pillarAttackCircle.get(finalI).set(pillarLocation_x[finalI] + 54f, pillarLocation_y[finalI] + 32f, 175f);
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
                            if(towerList.get(0).isChecked()){
                                towerList.get(0).setChecked(false);
                                pillarTower.get(finalI).setStyle(styleTowerPlacementArcher);
                                pillarTower.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI] + 16);
                                pillarTower.get(finalI).clearListeners();
                                pillarTower.get(finalI).addListener(towerListener);
                                towerCircleBool.set(finalI, true);
                                towerAttackCircle.get(finalI).set(towerLocation_x[finalI] + 48f, towerLocation_y[finalI] + 48f, 325f);
                            }
                            if(towerList.get(1).isChecked()){
                                towerList.get(1).setChecked(false);
                                pillarTower.get(finalI).setStyle(styleTowerPlacementMagician);
                                pillarTower.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI] + 16);
                                pillarTower.get(finalI).clearListeners();
                                pillarTower.get(finalI).addListener(towerListener);
                                pillarTower.get(finalI).setName("MagicTower " + String.valueOf(finalI));
                                towerCircleBool.set(finalI, true);
                                pillarAttackCircle.get(finalI).set(towerLocation_x[finalI] + 54f, towerLocation_y[finalI] + 32f, 325f);
                            }
                            if(towerList.get(2).isChecked()){
                                towerList.get(2).setChecked(false);
                                pillarTower.get(finalI).setStyle(styleTowerPlacementSupport);
                                pillarTower.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI] + 16);
                                pillarTower.get(finalI).clearListeners();
                                pillarTower.get(finalI).addListener(towerListener);
                                pillarTower.get(finalI).setName("SupportTower " + String.valueOf(finalI));
                                Gdx.app.log("x", String.valueOf(towerLocation_x[finalI]));
                                Gdx.app.log("Y", String.valueOf(towerLocation_x[finalI]));
                                towerCircleBool.set(finalI, true);
                                pillarAttackCircle.get(finalI).set(towerLocation_x[finalI] + 54f, towerLocation_y[finalI] + 32f, 325f);
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
            //stage.addActor(upgradeAbilityButtonActor);
            stage.addActor(pause);
            stage.addActor(abilityList);
            stage.addActor(tower);
            stage.addActor(victoryWindow);

            impLinkedList = new LinkedList<>();
            createAllEnemies();
            updateToolTips();
            //Gdx.app.log("ArrBoolSi", towerCircleBool.toString());
        }



        @Override
        public void render(float delta) {
            level.renderBackground();
            elapsed_time += Gdx.graphics.getDeltaTime();
            stage.act(Gdx.graphics.getDeltaTime());
            if(pillarArcherTower.get(0).isPressed()) {
                updateToolTips();
            }
            batch.begin();
            if (!isPaused){
                checkPillarRange(delta);
                checkTowerRange();
                if(enemyCount > 0) {

                }
                else{
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
                //font12.draw(batch, "Boss HP: " + (int)bossPath.getLifeCount(), 25, 510);
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
                    shape.setColor(1,1,1,0.35f);
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
                    shape.setColor(1,1,1,0.35f);
                    shape.circle(circle_x + 48f, circle_y + 48f, 325f);
                    shape.end();
                }
            }
        }
    }
        //DO NOT TOUCH; DANGER IMMINENT
        public void checkPillarRange(float delta){
            for(Iterator<Circle> circleIterator = pillarAttackCircle.iterator(); circleIterator.hasNext();) {
                Circle circle = circleIterator.next();
                for (Iterator<PathfindingEnemy> iterator = impLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy enemy = iterator.next();
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle())) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
                    }
                }
            }
        }
        public void checkTowerRange(){
            for(Iterator<Circle> circleIterator = towerAttackCircle.iterator(); circleIterator.hasNext();) {
                Circle circle = circleIterator.next();
                for (Iterator<PathfindingEnemy> iterator = impLinkedList.iterator(); iterator.hasNext(); ) {
                    PathfindingEnemy enemy = iterator.next();
                    if (Intersector.overlaps(circle, enemy.getBoundingRectangle())) {
                        enemy.setLifeCount(enemy.getLifeCount() - 0.05f);
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
            towerList.get(0).addListener(new TextTooltip(archerTowerToolTip, toolTipManager, uiSkin));
            towerList.get(1).addListener(new TextTooltip(magicianTowerToolTip, toolTipManager, uiSkin));
            towerList.get(2).addListener(new TextTooltip(supportTowerToolTip, toolTipManager, uiSkin));
        }
        public void checkAbilityCollision(PathfindingEnemy abl, float damage, int coin) {
            if (!(abl == null)) {
                Iterator<PathfindingEnemy> abilityIterator = ability.iterator();
                //Gdx.app.log("Array Index",ability.toString());
                for (Iterator<PathfindingEnemy> iterator = impLinkedList.iterator(); iterator.hasNext(); ) {
                    if (abilityIterator.hasNext()) {
                        PathfindingEnemy enemy = iterator.next();
                        if (enemy.getBoundingRectangle().overlaps(ability.get(0).getBoundingRectangle())) {
                            enemy.setLifeCount(enemy.getLifeCount() - damage);
                            enemy.timeOfDmgTaken = enemy.timeAlive;
                            Gdx.app.log(String.valueOf(enemy), String.valueOf(enemy.getLifeCount()));
                            ability.removeValue(ability.get(0), true);
                        }
                        if (enemy.getLifeCount() <= 0) {
                            iterator.remove();
                            coins += coin;
                            enemyCount -= 1;
                        }
                    } else {
                        break;
                    }
                }/*
            if(enemyCount == 0){
                if(abilityIterator.hasNext()) {
                    if (bossPath.getBoundingRectangle().overlaps(ability.get(0).getBoundingRectangle())) {
                        bossPath.setLifeCount(bossPath.getLifeCount() - fireBall.getFireDamage());
                        ability.removeValue(ability.get(0),true);
                    }
                }
            }*/
            }
        }
        public void dealThunderDamage(){
            for (Iterator<PathfindingEnemy> iterator = impLinkedList.iterator(); iterator.hasNext(); ) {
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
            //imp = new Imp();
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

        public void makeEnemiesMove(float delta) {
            for (Iterator<PathfindingEnemy> iterator = impLinkedList.iterator(); iterator.hasNext(); ) {
                PathfindingEnemy imp = iterator.next();
                //imp.updateAnimation(batch, LevelTwo.levelTwoTopRightPath(), delta, currentFrame);
                //remove entity if life is less than 0, and add 100 coins
                if (imp.getLifeCount() <= 0 ) {
                    iterator.remove();
                    coins += 100;
                    enemyCount -= 1;
                }
                //remove entity if is out of bounds, and get player damage
                if(imp.getX() > Gdx.graphics.getWidth()){
                    iterator.remove();
                    health -= 1;
                    enemyCount -= 1;
                }
            }
        }/*
        public void moveBoss(float delta){
            bossPath.updateBossAnimation(batch, LevelTwo.levelTwoTopPath(), delta, currentFrameBoss, 35);
            if(enemyCount == 0){
                if(bossPath.getLifeCount() <= 0 && !isBossAlive){
                    bossPath.setLifeCount(1000);
                }else if(bossPath.getLifeCount() <= 0 && isBossAlive){
                    isPaused = true;
                    bossPath.setLifeCount(0);
                    victoryWindow.setVisible(true);
                }else if(bossPath.getX() > Gdx.graphics.getWidth()){
                    health -= 50;
                    if(health > 0){
                        isPaused = true;
                        victoryWindow.setVisible(true);
                        Assets.manager.get(Assets.victorySound, Sound.class).play(2f);
                        Gdx.app.log("Game Won", "");
                    }
                }
            }
        }*/

        public void clearEnemies(){
            for (Iterator<PathfindingEnemy> impIterator = impLinkedList.iterator(); impIterator.hasNext();) {
                PathfindingEnemy imp = impIterator.next();
                impIterator.remove();
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
        /*
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
            bossPath = new PathfindingEnemy(boss.idleFrame(), 1000, LevelTwo.levelTwoTopPath());
        }*/

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

