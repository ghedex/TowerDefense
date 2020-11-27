package enemy.scorpionEntity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import enemy.AnimationEntity;
import enemy.Entity;



public class Scorpion extends Entity {
    AnimationEntity animation;

    public final String ATLASPATH = "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas";



    public Scorpion(Vector2 position, Vector2 size, String ATLASPATH){
        super(position, size, ATLASPATH);

    }
    public void create(){
        animation = new AnimationEntity(ATLASPATH);
        animation.createAnimation();
    }

    public void animate(){
        animation.renderAnimation(0, 0);


    }










}
