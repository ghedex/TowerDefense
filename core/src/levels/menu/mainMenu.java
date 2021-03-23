package levels.menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class mainMenu extends ApplicationAdapter {

    public String BACKGROUNDPATH = "menuAssets/mainMenuAssets/placeholder_mainMenuBackground.png";
    // TODO
    Stage stage;
    TextButton button;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    // TODO
    Texture menuBackground;
    SpriteBatch batch;
    Sprite img;

    public void createBackground() {
        batch = new SpriteBatch();
        menuBackground = new Texture(Gdx.files.internal((BACKGROUNDPATH)));
        img = new Sprite(menuBackground, menuBackground.getWidth(), menuBackground.getHeight());
    }

    public void renderBackground() {
        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void disposeBackground() {
        batch.dispose();
        menuBackground.dispose();
    }

    /*
    @Override
    public void create(){
      stage = new Stage();
      Gdx.input.setInputProcessor(stage);
      font = new BitmapFont();
      skin = new Skin();
      buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/button.pack"));
      skin.addRegions(buttonAtlas);
      textButtonStyle = new TextButton.TextButtonStyle();
      textButtonStyle.font = font;
      textButtonStyle.up = skin.getDrawable("up-button");
      textButtonStyle.down = skin.getDrawable("down-button");
      textButtonStyle.checked = skin.getDrawable("checked-button");
      button = new TextButton("Button1", textButtonStyle);
      stage.addActor(button);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont();
        myFont.draw(batch, "JEM", 500, 250);
        labelStyle.font = myFont;
        labelStyle.fontColor = Color.NAVY;

    }*/
    /*
        private enum STATE {
            MENU,
            GAME
        };
        private STATE state = STATE.MENU;
     */
}