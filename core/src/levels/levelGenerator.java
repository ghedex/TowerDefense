package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import enemy.scorpionEntity.Scorpion;
import com.badlogic.gdx.utils.Array;





public class levelGenerator extends ApplicationAdapter {
    private final String level;

    private ShapeRenderer sr;

    SpriteBatch batch;
    Texture img;
    Sprite scorp;
    private Array<AISprite> aiSprites;

    private Sprite sprite;


    public levelGenerator(String level){
        this.level = level;
    }

    Scorpion scorpionMob = new Scorpion();

    @Override
    public void create () {
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        img = new Texture(level);
        scorp = new Sprite(new Texture(scorpionMob.entityFile));
        scorp.setSize(50, 50);
        scorp.setOrigin(0, 0);
        aiSprites = new Array<AISprite>();
        aiSprites.add(new AISprite(scorp, levelOnePath()));

    }
    private Array<Vector2> levelOnePath(){
        Array<Vector2> path = new Array<Vector2>();
        path.add(new Vector2(250, 150));
        path.add(new Vector2(400, 150));
        path.add(new Vector2(290, 563));
        path.add(new Vector2(369, 552));
        path.add(new Vector2(417, 498));
        path.add(new Vector2(436, 424));
        path.add(new Vector2(462, 373));
        path.add(new Vector2(541, 343));
        path.add(new Vector2(612, 351));
        path.add(new Vector2(667, 394));
        /*for(int i = 0; i < MathUtils.random(5, 10); i++){
            path.add(new Vector2(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight())));
        }
        */
        return path;
    }
    public void render () {

        batch.begin();
        //last both params allow to scale image to window size
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //scorp.draw(batch);
        for(AISprite aiSprite: aiSprites)
            aiSprite.draw(batch);
        batch.end();
        for(AISprite aiSprite: aiSprites){

            sr.setColor(Color.CYAN);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.line(new Vector2(aiSprite.getX(), aiSprite.getY()), aiSprite.getPath().get(aiSprite.getWaypoint()) );
            sr.end();

            Vector2 previous = aiSprite.getPath().first();
            for(Vector2 waypoint: aiSprite.getPath()){
                sr.setColor(Color.WHITE);
                sr.begin(ShapeRenderer.ShapeType.Line);
                sr.line(previous, waypoint);

                sr.end();
                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.circle(waypoint.x, waypoint.y, 5);

                sr.end();
                previous = waypoint;
            }

        }



        //scorp.setPosition(xPosition, 0);

    }

    @Override
    public void dispose () {
        batch.dispose();
        sr.dispose();
        img.dispose();
        sprite.getTexture().dispose();


    }
}
