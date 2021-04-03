package levels.menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import levels.menu.mainMenu;

public class testMainMenu extends ApplicationAdapter {
    private Stage stage;
    private testActor actorTest;
    private testActor testActor2;

    @Override
    public void create()  {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        MoveByAction action = new MoveByAction();
        MoveByAction action1 = new MoveByAction();
        action.setAmount(200f, 50f);
        action.setDuration(2f);
        action1.setAmount(-150f, -100f);
        action1.setDuration(2f);
        actorTest = new testActor("menuAssets/mainMenuAssets/buttonAssets/placeholder_button.png", action, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        testActor2 = new testActor("menuAssets/mainMenuAssets/buttonAssets/placeholder_button_exit.png", action1,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        testActor2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("Game","You pressed the almighty Button");
                Gdx.app.exit();
            }
        });
        stage.addActor(actorTest);
        stage.addActor(testActor2);
    }

    @Override
    public void render(){
        super.render();
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose(){
        super.dispose();
        stage.dispose();
    }
}
