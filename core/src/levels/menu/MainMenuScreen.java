package levels.menu;

import MainRef.Assets;
import MainRef.Prefs;
import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import levels.utils.Coin;


public class MainMenuScreen implements Screen {
    final TowerDefense game;
    mainMenu menu;
    TooltipManager toolTipManager;
    private ResourceHandler resourceHandler = new ResourceHandler();
    private Stage stage;
    Skin uiSkin;
    public Prefs prefs = new Prefs();

    public MainMenuScreen(final TowerDefense game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        menu = new mainMenu();
        uiSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));
        toolTipManager = new TooltipManager();
        toolTipManager.initialTime = 0.0f;
        toolTipManager.resetTime = 0.0f;
        toolTipManager.subsequentTime = 0.0f;
        toolTipManager.hideAll();
        toolTipManager.instant();
        Gdx.input.setInputProcessor(stage);
        final ImageButtonStyle startButtonStyle = new ImageButtonStyle();
        final ImageButtonStyle tutorialButtonStyle = new ImageButtonStyle();
        final ImageButtonStyle resetButtonStyle = new ImageButtonStyle();
        startButtonStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_play"));
        startButtonStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_play_over"));

        tutorialButtonStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("tutorialButton"));
        tutorialButtonStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("tutorialButton_over"));

        resetButtonStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_restart"));
        resetButtonStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_restart_hover"));

        ImageButton startButton = new ImageButton(startButtonStyle);
        startButton.setPosition(Gdx.graphics.getWidth()/100f*27, Gdx.graphics.getHeight()/3f);

        ImageButton tutorialButton = new ImageButton(tutorialButtonStyle);
        tutorialButton.setPosition(Gdx.graphics.getWidth()/100f*60, Gdx.graphics.getHeight()/2.5f);
        tutorialButton.setSize(150, 100);

        ImageButton resetButton = new ImageButton(resetButtonStyle);
        resetButton.setPosition(Gdx.graphics.getWidth()*0.02f, Gdx.graphics.getHeight() *0.02f);
        resetButton.setSize(100, 100);
        resetButton.addListener(new TextTooltip("Reset level progression", toolTipManager, uiSkin));

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new LevelSelectionScreen(game));
            }
        });
        tutorialButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new TutorialWindow(game));
            }
        });
        resetButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                prefs.setLevelsFinished(0);
                Gdx.app.log("levelsFinished",String.valueOf(prefs.getLevelsFinished()));
            }
        });
        menu.createBackground(Assets.manager.get(Assets.mainMenuBackground, Texture.class));
        stage.addActor(startButton);
        stage.addActor(tutorialButton);
        stage.addActor(resetButton);
        Gdx.app.log("levelsFinished",String.valueOf(prefs.getLevelsFinished()));
    }

    @Override
    public void render(float delta) {
        menu.renderBackground();
        stage.act(Gdx.graphics.getDeltaTime()/2.5f);
        stage.draw();
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
        dispose();
    }

    @Override
    public void dispose(){
        stage.dispose();
        menu.disposeBackground();
    }
    public void playMusic(){
        if(prefs.isSoundOn()){
            //backgroundMusic.play();
            resourceHandler.getMusic("backgroundMusic").play();
        }else{
            //backgroundMusic.pause();
            resourceHandler.getMusic("backgroundMusic").pause();
        }
    }
}
