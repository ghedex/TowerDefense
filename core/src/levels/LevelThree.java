package levels;

import MainRef.Assets;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LevelThree extends ApplicationAdapter{

    private static Array<Vector2> path;
    Texture levelBackground;
    SpriteBatch batch;
    Sprite img;


    public void createBackground() {
        batch = new SpriteBatch();
        levelBackground = Assets.manager.get(Assets.levelThreeBackground, Texture.class);
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


    public static Array<Vector2> levelThreeLeftPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(-200, 300));
        path.add(new Vector2(100, 300));
        path.add(new Vector2(120, 350));
        path.add(new Vector2(700, 350));
        path.add(new Vector2(900, 340));
        path.add(new Vector2(910, 500));
        path.add(new Vector2(920, 550));
        path.add(new Vector2(1400, 550));
        return path;
    }
    public static Array<Vector2> levelThreeBossTopPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(700, 350));
        path.add(new Vector2(900, 340));
        path.add(new Vector2(910, 500));
        path.add(new Vector2(920, 550));
        path.add(new Vector2(1400, 550));
        return path;
    }
    public static Array<Vector2> levelThreeBossBottomPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(700, 350));
        path.add(new Vector2(700, 350));
        path.add(new Vector2(725, 50));
        path.add(new Vector2(900, 85));
        path.add(new Vector2(1400, 85));
        return path;
    }

    public static Array<Vector2> levelThreeTopPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(470, 800));
        path.add(new Vector2(475, 340));
        path.add(new Vector2(700, 340));
        path.add(new Vector2(900, 340));
        path.add(new Vector2(910, 500));
        path.add(new Vector2(920, 550));
        path.add(new Vector2(1400, 550));
        return path;
    }
    public static Array<Vector2> levelThreeBottomPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(525, -200));
        path.add(new Vector2(525, 30));
        path.add(new Vector2(475, 200));
        path.add(new Vector2(475, 350));
        path.add(new Vector2(700, 350));
        path.add(new Vector2(725, 50));
        path.add(new Vector2(900, 85));
        path.add(new Vector2(1400, 85));
        return path;
    }
}