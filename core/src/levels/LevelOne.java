package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LevelOne extends ApplicationAdapter {

    private static Array<Vector2> path;
    public String LEVELPATH = "game_background_1.png";
    Texture levelBackground;
    SpriteBatch batch;
    Sprite img;
    private CatmullRomSpline<Vector2> scorpionPath;


    public void createBackground() {
        batch = new SpriteBatch();
        levelBackground = new Texture(Gdx.files.internal(LEVELPATH));
        // img = new Sprite(levelBackground, levelBackground.getWidth(), levelBackground.getHeight());
        img = new Sprite(levelBackground, levelBackground.getWidth(), levelBackground.getHeight());
    }

    public void renderBackground(){
        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void disposeBackground(){
        batch.dispose();
        levelBackground.dispose();
    }


    public static Array<Vector2> levelOneTopPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(-150, 150));
        path.add(new Vector2(360, 175));
        path.add(new Vector2(410, 225));
        path.add(new Vector2(440, 300));
        path.add(new Vector2(500, 360));
        path.add(new Vector2(525, 375));
        path.add(new Vector2(625, 350));
        path.add(new Vector2(700, 325));
        path.add(new Vector2(750, 325));
        path.add(new Vector2(800, 375));
        path.add(new Vector2(850, 500));
        path.add(new Vector2(900, 550));
        path.add(new Vector2(1000, 550));
        path.add(new Vector2(1100, 550));
        path.add(new Vector2(1200, 500));
        path.add(new Vector2(1400, 500));
        return path;

    }

    public static Array<Vector2> levelOneBottomPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(-150, 150));
        path.add(new Vector2(360, 175));
        path.add(new Vector2(410, 225));
        path.add(new Vector2(440, 300));
        path.add(new Vector2(500, 360));
        path.add(new Vector2(520, 365));
        path.add(new Vector2(530, 363));
        path.add(new Vector2(560, 350));
        path.add(new Vector2(570, 345));
        path.add(new Vector2(580, 325));
        path.add(new Vector2(590, 300));
        path.add(new Vector2(600, 250));
        path.add(new Vector2(675, 180));
        path.add(new Vector2(750, 165));
        path.add(new Vector2(800, 140));
        path.add(new Vector2(850, 140));
        path.add(new Vector2(900, 140));
        path.add(new Vector2(1000, 140));
        path.add(new Vector2(1100, 140));
        path.add(new Vector2(1200, 140));
        path.add(new Vector2(1400, 140));
        return path;
    }


}