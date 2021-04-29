package enemy.scorpionEntity;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import enemy.Entity;


public class Scorpion extends Entity {
    Rectangle bounds;
    public static final int WIDTH = 75;
    public static final int HEIGHT = 75;


    public Scorpion() {
        super(WIDTH, HEIGHT, 50, 20, "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas");
        bounds = new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
    }
    public Rectangle getBounds(){
        return bounds;
    }
    private void setXY(float pX, float pY){
        setPosition(pX, pY);
        bounds.setX((int) pX);
        bounds.setY((int) pY);
    }




    //TO DO: Skorpion muss schon animiert sein, damit ich nur den Skorpion callen muss

}
