package enemy;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import levels.AISprite;
import levels.LevelOne;

public class Entity extends Sprite {

    String atlasPath;
    private Animation<TextureRegion> animation;
    private TextureAtlas entityAtlas;
    private float timePassed = 0;
    SpriteBatch batch;
    private Vector2 velocity = new Vector2();
    private float speed = 100, tolerance = 3;
    private int waypoint = 0;
    private Array<Vector2> path;
    private Vector2 pos = new Vector2(0,0);




    public Entity(String atlasPath){
        this.atlasPath = atlasPath;
    }

    public void createImage(){

        // path for scorpion atlas file: "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas"
        entityAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        animation = new Animation<TextureRegion>(1/40f, entityAtlas.getRegions());
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
    }

    public void renderImage(){
        batch = new SpriteBatch();
        TextureRegion currentIdleFrame = animation.getKeyFrame(timePassed, true);
        pos.x += speed * Gdx.graphics.getDeltaTime();
        batch.begin();
        timePassed += Gdx.graphics.getDeltaTime();
        batch.draw(currentIdleFrame, pos.x, pos.y, 90, 90);
        /*
        for(Vector2 path: path){

        }

         */




        batch.end();
        /*
        for(AISprite aiSprite: entityArr){
            aiSprite.draw(batch);
        }
        for(AISprite aiSprite: entityArr){

            Vector2 previous = aiSprite.getPath().first();

            for(Vector2 waypoint: aiSprite.getPath()){
                previous = waypoint;
            }
        }

         */
    }

    public void disposeImage(){
        entityAtlas.dispose();
    }




}
