package enemy;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {

    Vector2 position, size;
    Texture monster;
    Rectangle bounds;
    SpriteBatch batch;
    String atlasPath;

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

}
