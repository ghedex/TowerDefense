package levels.menu;

import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import levels.levelGenerator;


public class LevelSelectionScreen implements Screen {
    final TowerDefense game;
    mainMenu menu;
    public Stage stage;
    private ResourceHandler resourceHandler = new ResourceHandler();
    //Skin skin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));
    private testActor backButton;
    private testActor levelOneActor;
    private testActor backStageButton;
    private String levelSelectionBackground = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/bgLevelSelection.png";
    //private Button optiButton;
    //private AssetManager assetManager;
    private String BACKBUTTON_PATH = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/button_close.png";
    private String BACKSTAGEBUTTON_PATH = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/button_left.png";
    private String LEVELONEBUTTON_PATH = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/level1.png";
    public LevelSelectionScreen(final TowerDefense game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
        resourceHandler.loadSound("menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3", "buttonClickSound");

        Gdx.input.setInputProcessor(stage);
        backStageButton = new testActor(BACKSTAGEBUTTON_PATH,Gdx.graphics.getWidth()/100*25, Gdx.graphics.getHeight()/100*20, 100, 100);
        backButton = new testActor(BACKBUTTON_PATH, Gdx.graphics.getWidth()/100*75, Gdx.graphics.getHeight()/100*75, 100, 100);
        levelOneActor = new testActor(LEVELONEBUTTON_PATH,Gdx.graphics.getWidth()/100*48, Gdx.graphics.getHeight()/100*45);

        /*
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new MainMenuScreen(game));
                //Gdx.app.exit();
            }
        });

         */
        levelOneActor.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new levelGenerator(game));
            }
        });


        goBack(backButton);
        goBack(backStageButton);
        menu.createBackground(levelSelectionBackground);
        stage.addActor(backButton);
        stage.addActor(levelOneActor);
        stage.addActor(backStageButton);
    }

    @Override
    public void render(float delta) {
        menu.renderBackground();
        //Gdx.gl.glClearColor(0, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    public void goBack(testActor button){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new MainMenuScreen(game));
                //Gdx.app.exit();
            }
        });
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
}
