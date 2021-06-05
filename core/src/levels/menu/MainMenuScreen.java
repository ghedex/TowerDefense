package levels.menu;

import MainRef.Assets;
import MainRef.Prefs;
import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainMenuScreen implements Screen {
    final TowerDefense game;
    mainMenu menu;
    private ResourceHandler resourceHandler = new ResourceHandler();
    private Stage stage;
    public Prefs prefs = new Prefs();
    private testActor startButtonActor, soundButtonActor, tutorialButtonActor;

    public MainMenuScreen(final TowerDefense game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
        Gdx.input.setInputProcessor(stage);
        startButtonActor = new testActor(Assets.manager.get(Assets.menuStartButton, Texture.class), Gdx.graphics.getWidth()/100*27, Gdx.graphics.getHeight()/3, 200, 200);
        soundButtonActor = new testActor(Assets.manager.get(Assets.menuSoundButton, Texture.class), 10, 50, 100, 100);
        startButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new LevelSelectionScreen(game));
            }
        });

        tutorialButtonActor = new testActor(Assets.manager.get(Assets.menuTutorialButton, Texture.class), Gdx.graphics.getWidth()/100*60, (float) (Gdx.graphics.getHeight()/2.5), 150, 100);
        tutorialButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new TutorialWindow(game));
            }
        });
        menu.createBackground(Assets.manager.get(Assets.mainMenuBackground, Texture.class));
        stage.addActor(startButtonActor);
        stage.addActor(tutorialButtonActor);
        stage.addActor(soundButtonActor);
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
