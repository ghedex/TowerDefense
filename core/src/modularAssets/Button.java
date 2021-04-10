package modularAssets;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class Button extends Stage{
    Texture buttonImage;
    SpriteBatch batch;
    Sprite img;


    public void createButton(String IMAGEPATH) {
        batch = new SpriteBatch();
        buttonImage = new Texture(Gdx.files.internal(IMAGEPATH));
        img = new Sprite(buttonImage);

    }
    public void renderButton(int x, int y, int width, int height) {
        batch.begin();
        batch.draw(img, x, y, width, height);
        batch.end();
    }
}
