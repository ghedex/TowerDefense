package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import enemy.scorpionEntity.Scorpion;
import com.badlogic.gdx.utils.Array;



public class levelGenerator extends ApplicationAdapter {
    private final String level;
    SpriteBatch batch;
    Texture img;
    private Animation<TextureRegion> animation;
    private float timePassed = 0;
    private TextureAtlas scorpionPackAtlas;
    Sprite scorp;
    private Array<AISprite> aiSprites;

    private Sprite sprite;
    public levelGenerator(String level){
        this.level = level;
    }

    Scorpion scorpionMob = new Scorpion();

    @Override
    public void create () {
        batch = new SpriteBatch();
        scorpionPackAtlas = new TextureAtlas(Gdx.files.internal("assetsPack/scorpions/scorpionRunning/scorpionPack.atlas"));
        animation = new Animation<TextureRegion>(1/30f, scorpionPackAtlas.getRegions());
        img = new Texture(level);

        scorp = new Sprite(new Texture(scorpionMob.entityFile));
        scorp.setSize(50, 50);
        scorp.setOrigin(0, 0);


        aiSprites = new Array<AISprite>();
        aiSprites.add(new AISprite(scorp, levelOnePath()));

        Gdx.graphics.setTitle("Tower Defense Game - JEM");

    }
    private Array<Vector2> levelOnePath(){
        Array<Vector2> path = new Array<Vector2>();
        path.add(new Vector2(0, 150));
        path.add(new Vector2(250, 150));
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
    public void render () {


        batch.begin();

        //last both params allow to scale image to window size
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //scorp.draw(batch);
        //sprite.draw(batch);
        timePassed += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(timePassed, true), 0, 0);

        for(AISprite aiSprite: aiSprites)
            aiSprite.draw(batch);
        batch.end();
        for(AISprite aiSprite: aiSprites){

            Vector2 previous = aiSprite.getPath().first();

            for(Vector2 waypoint: aiSprite.getPath()){
                previous = waypoint;
            }
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        scorp.getTexture().dispose();
        scorpionPackAtlas.dispose();
    }
}
