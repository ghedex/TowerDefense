package levels.menu;

import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private ResourceHandler resourceHandler = new ResourceHandler();
    //Skin skin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));
    private testActor backButton;
    private testActor levelOneActor, levelTwoActor;
    private testActor backStageButton;
    private String levelSelectionBackground = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/bgLevelSelection.png";
    //private Button optiButton;
    //private AssetManager assetManager;
    private String BACKBUTTON_PATH = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/button_close.png";
    private String BACKSTAGEBUTTON_PATH = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/button_left.png";
    private String LEVELONEBUTTON_PATH = "core/assets/menuAssets/mainMenuAssets/menuSkin/LevelSelection/level1.png";
    private String LEVELTWOBUTTON_PATH = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/level2ButtonImage.png";
    public LevelSelectionScreen(final TowerDefense game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
        resourceHandler.loadSound("menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3", "buttonClickSound");
        resourceHandler.loadSound("core/assets/menuAssets/mainMenuAssets/music/levelOneBackgroundMusic.mp3", "levelOneBackgroundMusic");
        Gdx.input.setInputProcessor(stage);
        backStageButton = new testActor(BACKSTAGEBUTTON_PATH,Gdx.graphics.getWidth()/100*25, Gdx.graphics.getHeight()/100*20, 100, 100);
        backButton = new testActor(BACKBUTTON_PATH, Gdx.graphics.getWidth()/100*75, Gdx.graphics.getHeight()/100*75, 100, 100);
        levelOneActor = new testActor(LEVELONEBUTTON_PATH,Gdx.graphics.getWidth()/100*38, Gdx.graphics.getHeight()/100*45);
        levelTwoActor = new testActor(LEVELTWOBUTTON_PATH,Gdx.graphics.getWidth()/100*58, Gdx.graphics.getHeight()/100*45);
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
                game.setScreen(new levelOneGenerator(game));
                resourceHandler.getSound("levelOneBackgroundMusic").play(0.02f);
            }
        });

        levelTwoActor.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new levelTwoGenerator(game));
                resourceHandler.getSound("levelOneBackgroundMusic").play(0.02f);
            }
        });




        //levelListener(levelOneActor);
        //levelListener(levelTwoActor);
        levelOneActor.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new levelOneGenerator(game));
                resourceHandler.getSound("levelOneBackgroundMusic").play(0.02f);
            }
        });

        levelTwoActor.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new levelTwoGenerator(game));
                resourceHandler.getSound("levelOneBackgroundMusic").play(0.02f);
            }
        });


        goBack(backButton);
        goBack(backStageButton);
        menu.createBackground(levelSelectionBackground);
        stage.addActor(backButton);
        stage.addActor(levelOneActor);
        stage.addActor(levelTwoActor);
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

    /*
    public void levelListener(testActor clicker){
        clicker.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new Screen(Game game));
                resourceHandler.getSound("levelOneBackgroundMusic").play(0.02f);
            }
        });
    }

     */

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
