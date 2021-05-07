package levels.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class testActor extends Actor {
    private Sprite sprite;

    public testActor(String imagePath, Action action){
        sprite = new Sprite(new Texture(imagePath));
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        addAction(action);
    }

    public testActor(String imagePath, Action action, float x, float y, float width, float height){
        sprite = new Sprite(new Texture(imagePath));
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        addAction(action);
    }

    public testActor(String imagePath, float x, float y){
        sprite = new Sprite(new Texture(imagePath));
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }
    public testActor(String imagePath, float x, float y, float width, float height){
        sprite = new Sprite(new Texture(imagePath));
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    protected void positionChanged(){
        super.positionChanged();
        sprite.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }
}
