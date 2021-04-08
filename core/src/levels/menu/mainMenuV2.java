package levels.menu;

import MainRef.TowerDefense;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class mainMenuV2 implements Screen {
    final TowerDefense game;
    private Stage stage;
    private  testActor optiButton;
    //private Button optiButton;
    //private AssetManager assetManager;
    private String optionButton = "menuAssets/mainMenuAssets/buttonAssets/optiButton(FINAL_VERSION).png";

    public mainMenuV2(final TowerDefense game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        MoveByAction action = new MoveByAction();
        MoveByAction action1 = new MoveByAction();
        optiButton = new testActor(optionButton, Gdx.graphics.getWidth()/100*50, Gdx.graphics.getHeight()/3);
        optiButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new testMainMenu(game));
                //Gdx.app.exit();
            }
        });
        stage.addActor(optiButton);
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
