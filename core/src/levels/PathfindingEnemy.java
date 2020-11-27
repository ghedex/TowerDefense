package levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import enemy.Entity;

public class PathfindingEnemy extends Sprite {

    private Vector2 velocity = new Vector2();
    private float speed = 100, tolerance = 3;



    public Array<Vector2> getPath() {
        return path;
    }

    private Array<Vector2> path;

    private int waypoint = 0;

    public PathfindingEnemy(Sprite sprite, Array<Vector2> path){
        super(sprite);
        this.path = path;
    }

    public void draw(SpriteBatch spriteBatch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }
    public void update(float delta){

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
    public boolean isWaypointReached(){
        return path.get(waypoint).x - getX() <= speed / tolerance * Gdx.graphics.getDeltaTime() && path.get(waypoint).y - getY() <= speed / tolerance * Gdx.graphics.getDeltaTime() ;
    }
}
