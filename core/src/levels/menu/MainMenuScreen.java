package levels.menu;

import MainRef.Prefs;
import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
    Timer timer = new Timer();
    public Prefs prefs = new Prefs();
    private testActor startButtonActor, exitButtonActor, soundButtonActor;

    //private Button optiButton;
    private Music backgroundMusic;
    //private AssetManager assetManager;
    private String BACKGROUNDPATH = "menuAssets/mainMenuAssets/bg2.png";
    private String IMAGEPATH_START = "menuAssets/mainMenuAssets/buttonAssets/button_play.png";
    private String IMAGEPATH_EXIT = "menuAssets/mainMenuAssets/buttonAssets/placeholder_button_exit.png";
    private String IMAGEPATH_SOUNDBUTTON = "menuAssets/mainMenuAssets/buttonAssets/button_sound.png";
    final private int WINDOW_WIDTH = 1280;
    private long id;

    public MainMenuScreen(final TowerDefense game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
       /* skin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"));
        skin.add("optiButton(FINAL_VERSION)", skin);
        optiButton = new TextButton("Opti", skin);
        optiButton.setPosition(Gdx.graphics.getWidth()/100*50, Gdx.graphics.getHeight()/3);
        optiButton.setColor(1,0,0,1);
        optiButton.setSize(156,61);*/
        //assetManager = new AssetManager();
        Gdx.input.setInputProcessor(stage);
        MoveByAction start = new MoveByAction();
        MoveByAction exit = new MoveByAction();
        resourceHandler.loadSound("menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3", "buttonClickSound");
        if (resourceHandler.getMusic("backgroundMusic") == null){
            resourceHandler.loadMusic("menuAssets/mainMenuAssets/music/audio.mp3","backgroundMusic");
            resourceHandler.getMusic("backgroundMusic").setVolume(0.003125f);
        }

        //backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("menuAssets/mainMenuAssets/music/audio.mp3"));
        //backgroundMusic.setVolume(0.003125f);


        /*action.setAmountX((WINDOW_WIDTH/100*45) - Gdx.graphics.getWidth()/2f); //-250f
        action.setDuration(2f);
        action1.setAmountX((WINDOW_WIDTH/100*65) - Gdx.graphics.getWidth()/2f); //200f
        action1.setDuration(2f);*/
        startButtonActor = new testActor(IMAGEPATH_START, start, Gdx.graphics.getWidth()/100*43, Gdx.graphics.getHeight()/3, 200, 200);
        //exitButtonActor = new testActor(IMAGEPATH_EXIT, exit,Gdx.graphics.getWidth()/100*65, Gdx.graphics.getHeight()/2,100, 100);
        soundButtonActor = new testActor(IMAGEPATH_SOUNDBUTTON, 10, 50, 100, 100);
        startButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new LevelSelectionScreen(game));
            }
        });
        /*
        exitButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                Gdx.app.exit();
            }
        });

         */

        soundButtonActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                prefs.setSound(!prefs.isSoundOn());
            }
        });


        menu.createBackground(BACKGROUNDPATH);
        stage.addActor(startButtonActor);
        //stage.addActor(exitButtonActor);
        stage.addActor(soundButtonActor);
    }

    @Override
    public void render(float delta) {
        menu.renderBackground();
        //Gdx.gl.glClearColor(0, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime()/2.5f);
        stage.draw();
        playMusic();
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
        stage.dispose();
    }

    @Override
    public void dispose(){
        stage.dispose();
        backgroundMusic.dispose();
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
