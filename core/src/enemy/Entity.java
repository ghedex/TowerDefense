package enemy;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Entity extends Actor {


    Vector2 position, size;
    Texture monster;
    Rectangle bounds;
    SpriteBatch batch;
    String atlasPath;

    private Animation<TextureRegion> animation;


    private TextureAtlas entityAtlas;
    private float timePassed = 0;

    private float lifeCount;
    private int WIDTH;
    private int HEIGHT;



    private float velocity;


    public Entity(int WIDTH, int HEIGHT, float lifeCount, String atlasPath) {

        super.setSize(WIDTH, HEIGHT);

        this.velocity = velocity;
        //Anzahl Leben, später benötigt für den Schaden
        this.lifeCount = lifeCount;
        //Rectangle für Collision Detection
        //bounds = new Rectangle(position.x, position.y, size.x, size.y);
        //Atlas für die Darstellung der Entity
        entityAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        //Animationsdarstellung bzw. Darstellung des Sprites
        animation = new Animation(1 / 30f, entityAtlas.getRegions());


    }
    public Entity(int WIDTH, int HEIGHT, float lifeCount, TextureAtlas textureAtlas) {

        super.setSize(WIDTH, HEIGHT);

        this.velocity = velocity;
        //Anzahl Leben, später benötigt für den Schaden
        this.lifeCount = lifeCount;
        //Rectangle für Collision Detection
        //bounds = new Rectangle(position.x, position.y, size.x, size.y);
        //Atlas für die Darstellung der Entity
        entityAtlas = textureAtlas;
        //Animationsdarstellung bzw. Darstellung des Sprites
        animation = new Animation(1 / 30f, entityAtlas.getRegions());

    }
    public float getLifeCount() {
        return lifeCount;
    }

    public void setLifeCount(float lifeCount) {
        this.lifeCount = lifeCount;
    }
    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public TextureAtlas getEntityAtlas() {
        return entityAtlas;
    }
/*
    public FileHandle returnPath(FileHandle PATH){
        return Gdx.files.internal(PATH);
    }

 */
    /*
    public TextureRegion getKeyFrame(float time, boolean loop){
        return this.getKeyFrame(time, loop);
    }

     */


    public void update() {
        bounds.set(position.x, position.y, size.x, size.y);
    }

    public void draw(SpriteBatch batch) {
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

    public void createAnimation() {
        entityAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        animation = new Animation<TextureRegion>(1 / 40f, entityAtlas.getRegions());
    }

    public TextureRegion idleFrame() {
        batch = new SpriteBatch();
        timePassed += Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(timePassed, true);
    }

    public void disposeAnimation() {
        entityAtlas.dispose();
        batch.dispose();
    }
}
