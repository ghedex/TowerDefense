package enemy;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Sprite {

    Vector2 position, size;
    Texture monster;
    Rectangle bounds;
    SpriteBatch batch;
    String atlasPath;

    private Animation<TextureRegion> animation;
    private TextureAtlas entityAtlas;
    private float timePassed = 0;


    public Entity(Vector2 position, Vector2 size, String atlasPath) {
        this.position = position;
        this.size = size;
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        this.atlasPath = atlasPath;
    }

    public void update () {
        bounds.set(position.x, position.y, size.x, size.y);
    }

    public void draw (SpriteBatch batch){
        batch.draw(monster, position.x, position.y, size.x, size.y);
    }
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition() {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Texture getMonster() {
        return monster;
    }

    public void setMonster(Texture monster) {
        this.monster = monster;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void createAnimation(){
        entityAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        animation = new Animation<TextureRegion>(1/40f, entityAtlas.getRegions());
    }
    public void renderAnimation(){
        batch = new SpriteBatch();
        timePassed += Gdx.graphics.getDeltaTime();
        TextureRegion currentIdleFrame = animation.getKeyFrame(timePassed, true);

    }

    public TextureRegion idleFrame(float time){
        return animation.getKeyFrame(time, true);
    }

    public void disposeAnimation(){
        entityAtlas.dispose();
        batch.dispose();
    }


}
