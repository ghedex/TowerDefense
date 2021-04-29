package enemy.scorpionEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import enemy.Entity;
import levels.LevelOne;
import levels.PathfindingEnemy;


public class Scorpion extends Sprite {

    Vector2 first;

    Vector2 second;

    public Scorpion() {

        super( new Texture( Gdx.files.internal( "core/assets/assetsPack/scorpions/scorpionRunning/1_enemies_1_run_000.png" ) ) );

        super.setSize(90, 90);
        super.setPosition(LevelOne.levelOnePath().first().x, LevelOne.levelOnePath().first().y);
        //super.setPosition(posX, posY);


    }

    public void move(){
        this.translateX(1);
    }


    public void draw(SpriteBatch batch, float x, float y){
        this.draw(batch, x, y);

    }

    //TO DO: Skorpion muss schon animiert sein, damit ich nur den Skorpion callen muss

}
