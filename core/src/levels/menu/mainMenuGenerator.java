package levels.menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import modularAssets.Button;

public class mainMenuGenerator extends Game {
    Stage stage;
    SpriteBatch batch;
    mainMenu menu;
    Button button;
    Button buttonExit;
    final private int WINDOW_HEIGHT = 720;
    final private int WINDOW_WIDTH = 1280;
    final private int size_width = 100;
    final private int size_height = 50;
    private int pos_start_x = (WINDOW_WIDTH/100*45) - size_width/2;
    private int pos_y = 260 - size_height/2;
    private int pos_exit_x = (WINDOW_WIDTH/100*60) - size_width/2;
    private java.lang.String IMAGEPATH_START = "menuAssets/mainMenuAssets/buttonAssets/placeholder_button.png";
    private java.lang.String IMAGEPATH_EXIT = "menuAssets/mainMenuAssets/buttonAssets/placeholder_button_exit.png";

    public mainMenuGenerator() {
    }

    public void create() {
        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        menu = new mainMenu();
        button = new Button();
        buttonExit = new Button();
        menu.createBackground();
        button.createButton(IMAGEPATH_START);
        buttonExit.createButton(IMAGEPATH_EXIT);
        //menu.create();
    }

    @Override
    public void render() {
        batch.begin();
        menu.renderBackground();
        if (Gdx.input.getX() < pos_start_x + size_width && Gdx.input.getX() > pos_start_x && WINDOW_HEIGHT - Gdx.input.getY() < pos_y + size_height && WINDOW_HEIGHT - Gdx.input.getY() > pos_y){
            buttonExit.renderButton(pos_start_x, pos_y, size_width, size_height);
        } else {
            button.renderButton(pos_start_x, pos_y, size_width, size_height);
        }
        if (Gdx.input.getX() < pos_exit_x + size_width && Gdx.input.getX() > pos_exit_x && WINDOW_HEIGHT - Gdx.input.getY() < pos_y + size_height && WINDOW_HEIGHT - Gdx.input.getY() > pos_y) {
            button.renderButton(pos_exit_x, pos_y, size_width, size_height);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            buttonExit.renderButton(pos_exit_x, pos_y, size_width, size_height);
        }
        batch.end();
    }
}
