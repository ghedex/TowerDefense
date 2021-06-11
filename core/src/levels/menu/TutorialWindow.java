package levels.menu;

import MainRef.Assets;
import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import levels.levelOneGenerator;


public class TutorialWindow implements Screen {
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

    SpriteBatch batch;
    public TutorialWindow(final TowerDefense game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        menu = new mainMenu();
        resourceHandler.loadSound("menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3", "buttonClickSound");
        Gdx.input.setInputProcessor(stage);
        final ImageButtonStyle backButtonStyle = new ImageButtonStyle();
        final ImageButtonStyle forwardButtonStyle = new ImageButtonStyle();
        backButtonStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_left"));
        backButtonStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_left_over"));

        forwardButtonStyle.imageUp = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_right"));
        forwardButtonStyle.imageOver = new TextureRegionDrawable(Assets.manager.get(Assets.menuSkin, TextureAtlas.class).findRegion("button_right_over"));

        ImageButton backButton = new ImageButton(backButtonStyle);
        backButton.setPosition(Gdx.graphics.getWidth()/100*25, Gdx.graphics.getHeight()/100*20);
        backButton.setSize(100, 100);
        ImageButton forwardButton = new ImageButton(forwardButtonStyle);
        forwardButton.setPosition(Gdx.graphics.getWidth()/100*76, Gdx.graphics.getHeight()/100*20);
        forwardButton.setSize(100, 100);
        batch = new SpriteBatch();
        goBack(backButton);
        nextPage(forwardButton);
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

        font.draw(batch, "1. Start one level you like.", 380, 500);
        font.draw(batch, "2. Once you click on a highlighted field, the \n tower menu opens. \n ", 380, 460);
        font.draw(batch, "3. The tower menu provides always three \n different towers:", 380, 390);
        font.draw(batch, "-Magic Tower: Deals ?? damage\n-Arrow Tower: Deals ?? damage \n-SupportTower: Deals ?? damage ", 400, 320);
        //font.draw(batch, "fuckin retard, go and play the game", 380, 385);
        //Gdx.gl.glClearColor(0, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    public void goBack(ImageButton button){
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

    public void nextPage(ImageButton btn){
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
