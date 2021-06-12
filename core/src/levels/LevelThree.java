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


    public static Array<Vector2> levelTwoStraightPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(275, -150));
        path.add(new Vector2(275, 100));
        path.add(new Vector2(105, 150));
        path.add(new Vector2(95, 300));
        path.add(new Vector2(120, 350));
        path.add(new Vector2(240, 350));
        path.add(new Vector2(280, 450));
        path.add(new Vector2(310, 550));
        path.add(new Vector2(640, 550));
        path.add(new Vector2(660, 500));
        path.add(new Vector2(690, 450));
        path.add(new Vector2(700, 300));
        path.add(new Vector2(710, 270));
        path.add(new Vector2(730, 190));
        path.add(new Vector2(1300, 180));
        return path;

    }

    public static Array<Vector2> levelTwoTopPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(-200, 250));
        path.add(new Vector2(45, 250));
        path.add(new Vector2(75, 300));
        path.add(new Vector2(130, 325));
        path.add(new Vector2(155, 350));
        path.add(new Vector2(275, 400));
        path.add(new Vector2(285, 555));
        path.add(new Vector2(650, 555));
        path.add(new Vector2(655, 400));
        path.add(new Vector2(875, 400));
        path.add(new Vector2(900, 180));
        path.add(new Vector2(1400, 180));
        return path;
    }
    public static Array<Vector2> levelTwoBottomPath(){
        path = new Array<Vector2>();
        path.add(new Vector2(275, -200));
        path.add(new Vector2(275, 125));
        path.add(new Vector2(325, 150));
        path.add(new Vector2(400, 175));
        path.add(new Vector2(1400, 165));
        return path;
    }
    public static Array<Vector2> levelTwoTopRightPath(){
        path = new Array<>();
        path.add(new Vector2(900, 800));
        path.add(new Vector2(925, 150));
        path.add(new Vector2(960, 165));
        path.add(new Vector2(1400, 165));
        return path;
    }


}