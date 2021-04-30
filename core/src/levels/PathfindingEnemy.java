package levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import enemy.scorpionEntity.Scorpion;

public class PathfindingEnemy extends Sprite {

    private Vector2 velocity = new Vector2();
    Scorpion scorpion;
    private boolean remove = false;
    private float speed = 100, tolerance = 3, abilitySpeed = 350;
    private TextureRegion entity;
    public Array<Vector2> getPath() {
        return path;
    }

    private Array<Vector2> path;

    private int waypoint = 0;
    private float lifeCount;

    public PathfindingEnemy(TextureRegion entity, Array<Vector2> path){
        super(entity);
        this.path = path;
    }
    public PathfindingEnemy(TextureRegion entity){
        super(entity);
    }

    public PathfindingEnemy(TextureRegion entity, Array<Vector2> path, float lifeCount){
        super(entity);
        this.path = path;
        this.lifeCount = lifeCount;
    }


    public void update(SpriteBatch batch, Array<Vector2> path){
        super.draw(batch);
        this.path = path;
        float angle = (float) Math.atan2(path.get(waypoint).y - getY(), path.get(waypoint).x - getX());
        velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);

        setPosition(getX() + velocity.x * Gdx.graphics.getDeltaTime(), getY() + velocity.y * Gdx.graphics.getDeltaTime());

        if(isWaypointReached()){
            setPosition(path.get(waypoint).x, path.get(waypoint).y);
            if(waypoint + 1 >= path.size){
                waypoint = 0;
            }
            else{
                waypoint++;
            }
        }

    }
    public void setPosition(){
        setPosition(getX() + velocity.x * Gdx.graphics.getDeltaTime(), getY() + velocity.y * Gdx.graphics.getDeltaTime());
    }
    public boolean isWaypointReached(){
        return path.get(waypoint).x - getX() <= speed / tolerance * Gdx.graphics.getDeltaTime() && path.get(waypoint).y - getY() <= speed / tolerance * Gdx.graphics.getDeltaTime() ;
    }
    public float getLifeCount() {
        return lifeCount;
    }

    public void setLifeCount(float lifeCount) {
        this.lifeCount = lifeCount;
    }
    public void remove(){
        this.remove = true;
    }
}
