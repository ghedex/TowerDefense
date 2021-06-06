package levels.menu;

import MainRef.Assets;
import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import levels.levelOneGenerator;


public class TutorialWindowPageTwo implements Screen {
    final TowerDefense game;
    mainMenu menu;
    public Stage stage;
    private ResourceHandler resourceHandler = new ResourceHandler();
    //Skin skin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));
    private testActor backButton;
    private testActor forwardButton;
    private testActor levelOneActor;
    private testActor backStageButton;
    //private String tutorialBackground = "core/assets/menuAssets/mainMenuAssets/menuSkin/Tutorial/tutorialBackground.png";
    //private Button optiButton;
    //private AssetManager assetManager;
    //private String BACKBUTTON_PATH = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/button_close.png";
    //private String BACKSTAGEBUTTON_PATH = "menuAssets/mainMenuAssets/menuSkin/LevelSelection/button_left.png";
    private BitmapFont font;
    private final int FourTwentyBlazeIt = 420;
    SpriteBatch batch;
    public TutorialWindowPageTwo(final TowerDefense game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
        resourceHandler.loadSound("menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3", "buttonClickSound");
        Gdx.input.setInputProcessor(stage);
        backButton = new testActor(Assets.manager.get(Assets.menuReturnButton, Texture.class), Gdx.graphics.getWidth()/100*25, Gdx.graphics.getHeight()/100*20, 100, 100);
        forwardButton = new testActor(Assets.manager.get(Assets.menuRightButton, Texture.class), Gdx.graphics.getWidth()/100*76, Gdx.graphics.getHeight()/100*20, 100, 100);
        batch = new SpriteBatch();
        goBack(backButton);
        menu.createBackground(Assets.manager.get(Assets.tutorialBackground, Texture.class));
        stage.addActor(backButton);
        stage.addActor(forwardButton);
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/riffic-bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        generator.dispose();

    }

    @Override
    public void render(float delta) {
        menu.renderBackground();
        batch.begin();

        font.getData().setScale(1);
        font.draw(batch, "4. Choose one tower and click again \non your field.", 380, 500);
        font.draw(batch, "5. The HUD consists of an ability button and \nan upgrade button", 380, 430);

        //Gdx.gl.glClearColor(0, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
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
                game.setScreen(new TutorialWindow(game));
                //Gdx.app.exit();
            }
        });
    }

    public void nextPage(testActor btn){
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new TutorialWindowPageTwo(game));
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
