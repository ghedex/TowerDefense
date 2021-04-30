package enemy.wizardEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import components.Lifebar;
import enemy.Entity;

public class Wizard extends Sprite {

    public static final int WIDTH = 110;
    public static final int HEIGHT = 110;

    //Lifebar lifebar = new Lifebar();
    SpriteBatch batch;
    Texture tex;
    Sprite spr;
    public Wizard() {
        /*
        lifebar.setZIndex(100);
        lifebar.render();

         */
        super( new Texture( Gdx.files.internal( "core/assets/2_enemies_1_attack_000.png" ) ) );
        //spr = new Sprite(new Texture("core/assets/2_enemies_1_attack_000.png"));
        super.setSize(90, 90);
        //super.setPosition( posX, posY );
    }

    public void update(float delta){
        this.setPosition(30 * delta, 20 * delta);
    }

    /*
    public Sprite draw(SpriteBatch batch){
        batch.draw(spr);
        return spr;
    }

     */



}
