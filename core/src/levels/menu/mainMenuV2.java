package levels.menu;

import MainRef.ResourceHandler;
import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import levels.levelGenerator;


public class mainMenuV2 implements Screen {
    final TowerDefense game;
    private Stage stage;
    private ResourceHandler resourceHandler = new ResourceHandler();
    private testActor optiButton;
    private testActor levelOneActor;
    //private Button optiButton;
    //private AssetManager assetManager;
    private String optionButton = "menuAssets/mainMenuAssets/buttonAssets/optiButton(FINAL_VERSION).png";
    private String levelOneButton = "menuAssets/mainMenuAssets/buttonAssets/levelOneButton.png";

    public mainMenuV2(final TowerDefense game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        resourceHandler.loadSound("menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3", "buttonClickSound");
        Gdx.input.setInputProcessor(stage);
        optiButton = new testActor(optionButton, Gdx.graphics.getWidth()/100*50, Gdx.graphics.getHeight()/3);
        levelOneActor = new testActor(levelOneButton,Gdx.graphics.getWidth()/100*50, Gdx.graphics.getHeight()/2);
        optiButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new testMainMenu(game));
                //Gdx.app.exit();
            }
        });
        levelOneActor.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                super.clicked(event, x, y);
                resourceHandler.getSound("buttonClickSound").play(0.5f);
                game.setScreen(new levelGenerator(game));
            }
        });

        stage.addActor(optiButton);
        stage.addActor(levelOneActor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
    }
}
