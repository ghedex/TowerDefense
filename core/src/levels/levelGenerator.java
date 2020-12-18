package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import enemy.AnimationEntity;
import enemy.scorpionEntity.Scorpion;


public class levelGenerator extends ApplicationAdapter {
    SpriteBatch batch;
    Scorpion scorpion;
    LevelOne level;
    Vector2 size = new Vector2(15, 15);
    private int waypoint = 0;
    private CatmullRomSpline<Vector2> levelPath;
    private ShapeRenderer shapeRenderer;
    PathfindingEnemy pathFinding;
    Texture tex;
    private float SPEED = 100;
    private float timePassed = 0;

    public levelGenerator() {
    }

    public void create(){
        batch = new SpriteBatch();
        level = new LevelOne();
        scorpion = new Scorpion(new Vector2(), size, "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas");
        level.createBackground();
        scorpion.createAnimation();

        timePassed += Gdx.graphics.getDeltaTime();
        pathFinding = new PathfindingEnemy(scorpion.idleFrame(timePassed), LevelOne.levelOnePath());

        //1. methode erstellen, die aus dem scorpion eine textur zurückgibt
        //2. als Argument in Zeile 41 übergeben

    }
    @Override
    public void render(){
        batch.begin();

        level.renderBackground();
        //scorpion.animate();
        //scorpion.getKeyframe();
        pathFinding.draw(batch);
        batch.end();

    }

    @Override
    public void dispose(){
        batch.dispose();
        scorpion.getTexture().dispose();
        pathFinding.getTexture().dispose();
        level.dispose();
    }



}
