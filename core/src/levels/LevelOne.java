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
        path.add(new Vector2(-250, 0));
        path.add(new Vector2(160, 0));
        path.add(new Vector2(170, 5));
        path.add(new Vector2(180, 15));
        path.add(new Vector2(190, 30));
        path.add(new Vector2(200, 45));
        path.add(new Vector2(210, 60));
        path.add(new Vector2(220, 75));
        path.add(new Vector2(230, 90));
        path.add(new Vector2(240, 105));
        path.add(new Vector2(250, 120));
        path.add(new Vector2(260, 135));
        path.add(new Vector2(270, 150));
        path.add(new Vector2(280, 165));
        path.add(new Vector2(290, 180));
        path.add(new Vector2(300, 195));
        path.add(new Vector2(310, 210));
        path.add(new Vector2(320, 225));
        path.add(new Vector2(330, 230));
        path.add(new Vector2(340, 245));
        path.add(new Vector2(350, 245));
        path.add(new Vector2(360, 245));
        path.add(new Vector2(370, 240));
        path.add(new Vector2(380, 235));
        path.add(new Vector2(600, 235));
        path.add(new Vector2(620, 305));
        path.add(new Vector2(630, 335));
        path.add(new Vector2(640, 375));
        path.add(new Vector2(650, 400));
        path.add(new Vector2(900, 400));
        path.add(new Vector2(1200, 360));

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