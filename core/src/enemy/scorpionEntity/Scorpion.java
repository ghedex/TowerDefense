package enemy.scorpionEntity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import enemy.AnimationEntity;
import enemy.Entity;



public class Scorpion extends Entity {

    AnimationEntity animation; //die hier entfernen

    private Animation<TextureRegion> animation2;
    private TextureAtlas entityAtlas;
    private float timePassed = 0;
    SpriteBatch batch;


    public final String ATLASPATH = "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas";



    public Scorpion(Vector2 position, Vector2 size, String ATLASPATH){
        super(position, size, ATLASPATH);

    }
    public void create(){
        animation = new AnimationEntity(ATLASPATH);
        animation.createAnimation();
    }

    public void animate(){
        animation.renderAnimation();

    }


    public AnimationEntity getEnemy() {
        animation.renderAnimation();
        return animation;
    }

}
