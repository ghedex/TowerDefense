package levels.menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
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

public class mainMenu extends ApplicationAdapter{

    public String BACKGROUNDPATH = "menuAssets/mainMenuAssets/placeholder_mainMenuBackground.png";
    Texture menuBackground;
    SpriteBatch batch;
    Sprite img;

    public void createBackground() {
        batch = new SpriteBatch();
        menuBackground = new Texture(Gdx.files.internal((BACKGROUNDPATH)));
        img = new Sprite(menuBackground, menuBackground.getWidth(), menuBackground.getHeight());
    }
    public void createBackground(String path) {
        batch = new SpriteBatch();
        menuBackground = new Texture(Gdx.files.internal((path)));
        img = new Sprite(menuBackground, menuBackground.getWidth(), menuBackground.getHeight());
    }

    public void renderBackground() {
        super.render();
        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void disposeBackground() {
        batch.dispose();
        menuBackground.dispose();
    }
}