package levels.menu;

import MainRef.Assets;
import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import levels.levelOneGenerator;
import levels.levelTwoGenerator;


public class LevelSelectionScreen implements Screen {
    final TowerDefense game;
    mainMenu menu;
    public Stage stage;
    private testActor backButton, levelOneActor, levelTwoActor;
    public LevelSelectionScreen(final TowerDefense game) {
        this.game = game;
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
        Gdx.input.setInputProcessor(stage);
        backButton = new testActor(Assets.manager.get(Assets.menuCloseButton, Texture.class), Gdx.graphics.getWidth()/100*75, Gdx.graphics.getHeight()/100*75, 100, 100);
        levelOneActor = new testActor(Assets.manager.get(Assets.levelOneButton, Texture.class),Gdx.graphics.getWidth()/100*38, Gdx.graphics.getHeight()/100*45);
        levelTwoActor = new testActor(Assets.manager.get(Assets.levelTwoButton, Texture.class),Gdx.graphics.getWidth()/100*58, Gdx.graphics.getHeight()/100*45);
        Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).setVolume(0.05f);
        Assets.manager.get(Assets.bossLevelOneMusic, Music.class).setVolume(0.05f);

        levelOneActor.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                Assets.loadLevelOne();
                while(!Assets.manager.update()){
                    System.out.println(Assets.manager.getProgress() * 100 + "%");
                }
                game.setScreen(new levelOneGenerator(game));


            }
        });

        levelTwoActor.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new levelTwoGenerator(game));
                Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).play();
            }
        });
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                game.setScreen(new MainMenuScreen(game));
            }
        });

        menu.createBackground(Assets.manager.get(Assets.levelSelectionBackground, Texture.class));
        stage.addActor(backButton);
        stage.addActor(levelOneActor);
        stage.addActor(levelTwoActor);
    }

    @Override
    public void render(float delta) {
        menu.renderBackground();
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
    }

    @Override
    public void dispose(){
        stage.dispose();
        menu.disposeBackground();
    }
}
