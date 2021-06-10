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

public class LevelTwo extends ApplicationAdapter {

    private static Array<Vector2> path;
    public String LEVELPATH = "game_background_2.png";
    Texture levelBackground;
    SpriteBatch batch;
    Sprite img;
    private CatmullRomSpline<Vector2> scorpionPath;


    public void createBackground() {
        batch = new SpriteBatch();
        levelBackground = Assets.manager.get(Assets.levelTwoBackground, Texture.class);
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
        path.add(new Vector2(-200, 200));
        path.add(new Vector2(90, 250));
        path.add(new Vector2(120, 300));
        path.add(new Vector2(130, 350));
        path.add(new Vector2(140, 400));
        path.add(new Vector2(150, 440));
        path.add(new Vector2(520, 430));
        path.add(new Vector2(535, 300));
        path.add(new Vector2(550, 210));
        path.add(new Vector2(570, 150));
        path.add(new Vector2(580, 120));
        path.add(new Vector2(590, 100));
        path.add(new Vector2(600, 90));
        path.add(new Vector2(700, 90));
        path.add(new Vector2(760, 150));
        path.add(new Vector2(770, 600));
        /*
        path.add(new Vector2(80, 350));
        path.add(new Vector2(110, 400));
        path.add(new Vector2(310, 550));


         */
        return path;
    }


}