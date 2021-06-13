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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainMenuScreen implements Screen {
    final TowerDefense game;
    mainMenu menu;
    private ResourceHandler resourceHandler = new ResourceHandler();
    private Stage stage;
    public Prefs prefs = new Prefs();

    public MainMenuScreen(final TowerDefense game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
        Gdx.input.setInputProcessor(stage);
        final ImageButtonStyle startButtonStyle = new ImageButtonStyle();
        final ImageButtonStyle tutorialButtonStyle = new ImageButtonStyle();
        startButtonStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_play"));
        startButtonStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_play_over"));

        tutorialButtonStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("tutorialButton"));
        tutorialButtonStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("tutorialButton_over"));

        ImageButton startButton = new ImageButton(startButtonStyle);
        startButton.setPosition(Gdx.graphics.getWidth()/100*27, Gdx.graphics.getHeight()/3);
        ImageButton tutorialButton = new ImageButton(tutorialButtonStyle);
        tutorialButton.setPosition(Gdx.graphics.getWidth()/100*60, (float) (Gdx.graphics.getHeight()/2.5));
        tutorialButton.setSize(150, 100);
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
        menu.createBackground(Assets.manager.get(Assets.mainMenuBackground, Texture.class));
        stage.addActor(startButton);
        stage.addActor(tutorialButton);
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
