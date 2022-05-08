package levels.menu;

import MainRef.Assets;
import MainRef.Configuration.GameState;
import MainRef.Prefs;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import levels.levelOneGenerator;
import levels.levelThreeGenerator;
import levels.levelTwoGenerator;


public class LevelSelectionScreen implements Screen {
    final TowerDefense game;
    mainMenu menu;
    public Stage stage;
    Prefs prefs = new Prefs();

    public LevelSelectionScreen(final TowerDefense game) {
        this.game = game;
    }
    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
        Gdx.input.setInputProcessor(stage);
        final ImageButtonStyle backButtonStyle = new ImageButtonStyle();
        final ImageButtonStyle levelOneStyle = new ImageButtonStyle();

        final ImageButtonStyle levelTwoLockedStyle = new ImageButtonStyle();
        final ImageButtonStyle levelTwoStyle = new ImageButtonStyle();

        final ImageButtonStyle levelThreeLockedStyle = new ImageButtonStyle();
        final ImageButtonStyle levelThreeStyle = new ImageButtonStyle();
        backButtonStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_close"));
        backButtonStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_close_over"));

        levelOneStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("levelOne"));
        levelOneStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("levelOne_over"));
        //Locked
        levelTwoLockedStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("levelTwoLocked"));
        //Unlocked
        levelTwoStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("levelTwo"));
        levelTwoStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("levelTwo_over"));
        //Locked
        levelThreeLockedStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("levelThreeLocked"));
        //Unlocked
        levelThreeStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("levelThree"));
        levelThreeStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("levelThree_over"));

        ImageButton backButton = new ImageButton(backButtonStyle);
        backButton.setPosition(Gdx.graphics.getWidth()/100*75, Gdx.graphics.getHeight()/100*75);
        backButton.setSize(100, 100);
        ImageButton levelOneButton = new ImageButton(levelOneStyle);
        levelOneButton.setPosition(Gdx.graphics.getWidth()/100*32, Gdx.graphics.getHeight()/100*45);
        ImageButton levelTwoButton = new ImageButton(levelTwoLockedStyle);
        if(prefs.getLevelsFinished() >= 1){
            levelTwoButton.setStyle(levelTwoStyle);
        }
        levelTwoButton.setPosition(Gdx.graphics.getWidth()/100*48, Gdx.graphics.getHeight()/100*45);
        ImageButton levelThreeButton = new ImageButton(levelThreeLockedStyle);
        if(prefs.getLevelsFinished() >= 2){
            levelTwoButton.setStyle(levelThreeStyle);
        }
        levelThreeButton.setPosition(Gdx.graphics.getWidth()/100*64, Gdx.graphics.getHeight()/100*45);
        Gdx.app.log("levelsFinished", String.valueOf(prefs.getLevelsFinished()));
        Assets.manager.get(Assets.levelOneBackgroundMusic, Music.class).setVolume(0.02f);
        Assets.manager.get(Assets.bossLevelOneMusic, Music.class).setVolume(0.2f);

        levelOneButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                Assets.loadLevelOne();
                while(!Assets.manager.update()){
                    System.out.println(Assets.manager.getProgress() * 100 + "%");
                }
                game.setScreen(new levelOneGenerator(game));
                Assets.menuDispose();
            }
        });
        if(prefs.getLevelsFinished() >= 1) {
            levelTwoButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    Assets.loadLevelTwo();
                    while (!Assets.manager.update()) {
                        System.out.println(Assets.manager.getProgress() * 100 + "%");
                    }
                    game.setScreen(new levelTwoGenerator(game));
                    Assets.menuDispose();
                }
            });
        }
        if(prefs.getLevelsFinished() >= 2){
            levelThreeButton.addListener(new ClickListener(){
                @Override
                public void clicked (InputEvent event, float x, float y){
                    super.clicked(event, x, y);
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    Assets.loadLevelThree();
                    while(!Assets.manager.update()){
                        System.out.println(Assets.manager.getProgress() * 100 + "%");
                    }
                    game.setScreen(new levelThreeGenerator(game));
                    Assets.menuDispose();
                }
            });
        }
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
        stage.addActor(levelOneButton);
        stage.addActor(levelTwoButton);
        stage.addActor(levelThreeButton);
    }

    @Override
    public void render(float delta) {
        menu.renderBackground();
        stage.act(Gdx.graphics.getDeltaTime());
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
